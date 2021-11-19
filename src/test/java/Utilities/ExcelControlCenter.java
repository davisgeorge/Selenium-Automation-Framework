package Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;



//This class is used by TestNG Data provider to read data from excel.


public class ExcelControlCenter 
{
        static String fileLocation;
        static XSSFSheet sheetName;
        static XSSFWorkbook workBook;
        static File location;
        static FileInputStream fileStream;
        
        public static Map<String, String> config;
        

        
        private static final Logger logger = LogManager.getLogger(ExcelControlCenter.class);
        
        public ExcelControlCenter(String dotXlsFileName, String sheetname)
        {
            try 
            {
                fileLocation = System.getProperty("user.dir");

                logger.info("Loading excel. "+fileLocation);
                
                System.out.println("File to load: "+fileLocation+"/payload/"+dotXlsFileName+".xlsx");
                location = new File(fileLocation+"/payload/"+dotXlsFileName+".xlsx");
                
                fileStream = new FileInputStream(location);
    
                workBook = new XSSFWorkbook(fileStream);
                sheetName = workBook.getSheet(sheetname);
                
                logger.info("Excel Loaded.");
                System.out.println("Excel Loaded.");
                
            } 
            catch (IOException e) 
            {
                Assert.fail("Invalid Excel.");
                System.out.println("Invalid Excel.");
                logger.info(e.getMessage());
                logger.info(e.getCause());
                e.printStackTrace();
            }
        }
        
    //GET ROW COUNT
        public int getRowCount() throws IOException
        {
            int rowCount = sheetName.getPhysicalNumberOfRows();
            logger.info("Total number of rows: "+rowCount);
            System.out.println("Total number of rows: "+rowCount);
            return rowCount;
        }
    
    //GET COLUMN COUNT
        public int getColCount() throws IOException
        {
            int colCount = sheetName.getRow(0).getPhysicalNumberOfCells();
            logger.info("Total number of coloumns: "+colCount);
            System.out.println("Total number of coloumns: "+colCount);
            return colCount;
        }
        
    //GET COLUMN COUNT FROM A SPECIFIC ROW
        public int getColCount(int rowCount) throws IOException
        {
            int colCount = sheetName.getRow(rowCount).getPhysicalNumberOfCells();
            logger.info("Total number of coloumns: "+colCount);
            System.out.println("Total number of coloumns: "+colCount);
            return colCount;
        }
        
    //GET SPECIFIC CELL DATA AS STRING - CONVERTS ANY TYPE OF CELL TO A STRING CELL.
        public String getCellDataString(int rowCount, int coloumnCount)
        {	
            //String cellData = sheetName.getRow(rowCount).getCell(coloumnCount).getStringCellValue();
            String cellData = new DataFormatter().formatCellValue(sheetName.getRow(rowCount).getCell(coloumnCount));
            return cellData;
        }
    
    //GET SPECIFIC CELL DATA AS NUMBER	
        public double getCellDataNumber(int rowCount, int coloumnCount)
        {
            double cellData = sheetName.getRow(rowCount).getCell(coloumnCount).getNumericCellValue();
            return cellData;
        }
        
        
    //DATA FEEDER TO DATA PROVIDER
        public Object[][] getTestData(String sheetName) throws IOException
        {
            
            int rowCount = getRowCount(); // get total rows
            int colCount = getColCount(); // get total columns
            
            Object data[][] = new Object [rowCount-1][colCount]; //row count should always be total -1
            
            for(int i=1;i<rowCount;i++)							//Iterate through all rows
            {													
                for(int j=0;j<colCount;j++)					//Get cell value from row 1, column i.
                {
                    String fullData = getCellDataString(i, j); //Get and Store all data
                    data[i-1][j] = fullData; 
                } 
            }
                logger.info("Returned all values.");
                System.out.println("Returned all values.");
                
                return data;

        }
        
        
        public Object[] getTestData2(String sheetName) throws IOException
        {
            
            int rowCount = getRowCount(); // get total rows
            int colCount = getColCount(); // get total columns
            
            List<Map<String,String>> maps = new ArrayList<Map<String,String>>();
            												
            Sheet sheet = workBook.getSheet(sheetName);
            FormulaEvaluator evaluator = workBook.getCreationHelper().createFormulaEvaluator();

            for(int i = 1; i < rowCount; i++)				
            {
                Map<String, String> m = new HashMap<String, String>();
                for (int j = 0; j < colCount; j++) {
                    String key = getCellDataString(0, j); 
                    String value = getCellDataString(i, j); 

                    Row row = sheet.getRow(i);
                    Cell cell = row.getCell(j);
 
 
                    if (cell.getCellType().toString() == "FORMULA") {
                        CellType ct = evaluator.evaluateFormulaCell(cell);
                        switch(ct) {
                            case NUMERIC:
                                Double d = Math.round(cell.getNumericCellValue() * 100.0) / 100.0; 
                                value = d.toString();
                                if (key.contains("percent")) {
                                    Integer in = (int)Math.round(cell.getNumericCellValue());
                                    value = in.toString();
                                }
                                break;
                            case STRING:
                                value = cell.getStringCellValue();
                                break;
                            default:
                                break;
                        }
                    }

                    m.put(key, value);
                } 
               
                    maps.add(m);
                System.out.println("Added to map.");
            }

               
                fileStream.close();
                workBook.close();

                return maps.toArray();

        }

        
        public Map<String, String> getConfig(String sheetName) throws IOException
        {
            
            int rowCount = getRowCount(); // get total rows
            
            Map<String, String> m = new HashMap<String, String>();
            
            for(int i = 0; i < rowCount; i++)				
            {
                String key = getCellDataString(i, 0); 
                String value = getCellDataString(i, 1); 

                m.put(key, value);
            }

            fileStream.close();
                workBook.close();


            return m;
    }
        
        
       
}
