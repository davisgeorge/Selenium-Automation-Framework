package Base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Properties;


//This class is used to read from .properties files. Change .properties file below to login or use properties of the specified environment

public class Config
{
	private static Config manager;

    FileInputStream fileStream;

    private static final Properties prop = new Properties();

    public Config() throws IOException
    {

        String fileLocation;
        fileLocation = System.getProperty("user.dir");

        File location = new File(fileLocation+"/Config/configUAT.properties");
                
        fileStream = new FileInputStream(location);
        
        prop.load(fileStream);
    }

    public static Config getInstance()
    {
        if(manager == null)
        {
            synchronized (Config.class)
            {
                try
                {
                    manager = new Config();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        return manager;
    }
    
    public String getAdminUser() 
    {
		return prop.getProperty("admin.user");
	}
	
	public String getAdminPassword() 
	{
		return prop.getProperty("admin.password");
	}

    public String getString(String key)
    {
        return System.getProperty(key, prop.getProperty(key));
    }
    
	
	public String getSetting(String property) {
		
		return prop.getProperty(property);
	}
	
}
