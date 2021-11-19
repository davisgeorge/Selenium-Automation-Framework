package Utilities;


import java.util.concurrent.TimeUnit;
import org.testng.ITestListener;
import org.testng.ITestResult;
import Base.Base;


//This class defines ITestListener activities. It can be used to control test specific acitivites at different stages of the test


public class CustomListener extends Base implements ITestListener
{
	
	public long startTime;
	public long endTime;

	@Override
	public void onTestStart(ITestResult result)
	{
		System.out.println("Test is starting...");
		startTime = System.currentTimeMillis();
	}
	
	
	@Override
	public void onTestSuccess(ITestResult result)
	{
		System.out.println("Test Passed!");
		endTime = System.currentTimeMillis();
		long executionTime = endTime - startTime;
		System.out.println("TEST EXECUTION TIME:  "+TimeUnit.MILLISECONDS.toMinutes(executionTime)+" Minutes");
	}
	
	@Override
	public void onTestFailure(ITestResult result)
	{
		System.out.println("Test Failed!");
		takeScreenshot();
		endTime = System.currentTimeMillis();
		long executionTime = endTime - startTime;
		System.out.println("TEST EXECUTION TIME:  "+TimeUnit.MILLISECONDS.toMinutes(executionTime)+" Minutes");
	}
}
