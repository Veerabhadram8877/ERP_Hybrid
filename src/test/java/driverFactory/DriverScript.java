package driverFactory;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript extends FunctionLibrary {
	String inputpath = "./FileInput/Dataengine.xlsx";
	String outputpath = "./FileOutput/HydRes.xlsx";
	ExtentReports reports;
	ExtentTest test;
	public void startTest() throws Throwable
	{
		String ModuleStatus ="";
		//call excel sheet
	ExcelFileUtil xl=new ExcelFileUtil(inputpath);
	//iterate mastercase sheet
	for(int i=1;i<=xl.rowCount("MasterTestCases");i++)
	{
		
		if(xl.getCellData("MasterTestCases", i, 2).equalsIgnoreCase("Y"))
		{
			//store a sheet into variable
			String TCModule = xl.getCellData("MasterTestCases", i, 1);
			
			//iterate all rows in TCMODULE
			for(int j=1;j<=xl.rowCount(TCModule);j++)
			{
				//call all cells
				String Description = xl.getCellData(TCModule, j, 0);
				String ObjectType=xl.getCellData(TCModule, j, 1);
				String LocatorType=xl.getCellData(TCModule, j, 2);
				String LocatorValue=xl.getCellData(TCModule, j, 3);
				String TestData=xl.getCellData(TCModule, j, 4);
				try {
					if(ObjectType.equalsIgnoreCase("startBrowser"))
					{
						driver = FunctionLibrary.startBrowser();
					}
					else if(ObjectType.equalsIgnoreCase("openUrl"))
					{
						FunctionLibrary.openUrl(driver);
					}
					else if(ObjectType.equalsIgnoreCase("waitforElement"))
					{
						FunctionLibrary.waitforElement(driver, LocatorType, LocatorValue, TestData);
					}
					else if(ObjectType.equalsIgnoreCase("typeAction"))
					{
						FunctionLibrary.typeAction(driver, LocatorType, LocatorValue, TestData);
					}
					else if(ObjectType.equalsIgnoreCase("clickAction"))
					{
						FunctionLibrary.clickAction(driver, LocatorType, LocatorValue);
					}
					else if(ObjectType.equalsIgnoreCase("validateTitle"))
					{
						FunctionLibrary.validateTitle(driver, TestData);
					}
					else if(ObjectType.equalsIgnoreCase("closeBrowser"))
					{
						FunctionLibrary.closeBrowser(driver);
					}
					else if(ObjectType.equalsIgnoreCase("mouseClick"))
					{
						FunctionLibrary.mouseClick(driver);
					}
					else if(ObjectType.equalsIgnoreCase("categoryTable"))
					{
						FunctionLibrary.categoryTable(driver, TestData);
					}
					else if(ObjectType.equalsIgnoreCase("capureSnumber"))
					{
						FunctionLibrary.capureSnumber(driver, LocatorType, LocatorValue);
					}
					else if(ObjectType.equalsIgnoreCase("supplierTable"))
					{
						FunctionLibrary.supplierTable(driver);
					}
					else if(ObjectType.equalsIgnoreCase("captureCnumber"))
					{
						FunctionLibrary.captureCnumber(driver, LocatorType, LocatorValue);
					}
					else if(ObjectType.equalsIgnoreCase("customerTable"))
					{
						FunctionLibrary.customerTable(driver);
					}
					//write as pass into tcmodule
					xl.setCellData(TCModule, j, 5, "pass", outputpath);
					ModuleStatus="true";
					
				}catch(Exception e)
				{
					System.out.println(e.getMessage());
					xl.setCellData(TCModule, j, 5, "fail", outputpath);
					ModuleStatus="false";
				}
				if(ModuleStatus.equalsIgnoreCase("true"))
				{
					xl.setCellData("MasterTestCases", i, 3, "pass", outputpath);
				}else
				{
					xl.setCellData("MasterTestCases", i, 3, "fail", outputpath);
				}
					
				
			}
		}else
		{
			//write flag to N
			xl.setCellData("MasterTestCases", i, 3, "Blocked", outputpath);
		}
	}
	
	
	
	}

}
