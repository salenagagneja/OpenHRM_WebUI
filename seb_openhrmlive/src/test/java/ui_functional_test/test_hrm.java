package ui_functional_test;

import java.sql.Date;
import java.text.SimpleDateFormat;
//import page_obj;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.WebDriver;
//import org.testng.annotations.Test;
//import org.testng.annotations.BeforeTest;
import org.testng.annotations.*;
//import org.testng.annotations.AfterGroups;
//import org.testng.annotations.Parameters;
//import org.testng.annotations.DataProvider;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.Test;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.raybiztech.commonexcelreport.ExcelReportGenerator;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Keys;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.module.ModuleDescriptor.Exports;
//import System;

public class test_hrm {
	private static WebDriver driver = null;
	private excel_utils dd = null;
	private excel_utils dd_reportStep = null;
	private static excel_utils dd_reportSummary = null;
	static final Logger logger = LogManager.getLogger(test_hrm.class.getName());
	private WebElement page_ele = null;
	private WebElement ele = null;
	private String tc_name =null;
	private int test_sno = 0;
	//private String tc_name = "login";
	
	private ExtentReports extent;
	private ExtentTest extent_log;
	
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~`Before Each Test~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~`*/	
@Parameters("browser") 
@BeforeClass
  public void beforeClass (String browser) throws Exception 
  
  {
	System.out.println("BeforeSuite");
	/*		 dd = new excel_utils ("D:\\SEB\\Assignment\\Test_Report\\Execution_Report.xlsx","Summary","Write");
			 dd.report_TCSummary("Login","Fail");
	  int x=1;
	  if(x==1) {
		  return;
	  }*/
	  //System.out.println(System.getProperty("user.dir") +"/test-output/STMExtentReport.html");
		tc_name = "login";
	  extent = new ExtentReports (System.getProperty("user.dir") +"/test-output/STMExtentReport.html", true);
	  extent
      .addSystemInfo("Host Name", "Lets Check")
      .addSystemInfo("Environment", "Automation Testing")
      .addSystemInfo("User Name", "Salena Gagneja");
	  
	  extent.loadConfig(new File(System.getProperty("user.dir")+"/extent-config.xml"));
	  
	  DOMConfigurator.configure("log4j.xml");
	  System.out.println("Test Execution Started with Before Suite Started");
	  //Start logging in the log file
	  
	  logger.info("# # # # # # # # # # # # # # # # # # # # # # # # # # # ");
	  logger.info("Test Suite Execution Has started has Started");
	  
	  
	  //Kill Chrrome Driver tasks if any
	   Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");
	   
	   //Get the objects for Excel Handling
		  try
		  {
			dd = new excel_utils ("D:\\SEB\\Assignment\\src\\main\\java\\TestData\\Input.xlsx","Sheet1","Read");
			//Reporter.log("TestData -> Input.xlsx - Successful",true);
			logger.info("TestData -> Input.xlsx - Successful");
			Reporter.log("TestData -> Input.xlsx - Successful", true);
		  } catch (Exception e) {
			  //Reporter.log("Exception " + e , false);
			  logger.info("TestData -> Input.xlsx - Unsuccessful. Exception :" +e.getMessage());
			  Reporter.log("TestData -> Input.xlsx - Successful", true);
		  }	  
		 try
			  {
			 dd_reportSummary = new excel_utils ("D:\\SEB\\Assignment\\Test_Report\\Execution_Report.xlsx","Summary","Write");
				Reporter.log("Test_Report -> Execution_Report.xlsx - Successful",true);
				logger.info("TestData -> Execution_Report.xlsx - Successful");
				
				
	    } catch (Exception e) {
				  Reporter.log("Exception " + e.getMessage() , true);
				  logger.info("TestData -> Execution_Report.xlsx - Unsuccessful. Exception :" +e.getMessage());
			  }
		 if(dd_reportSummary==null) {
			 System.out.println("Summary issue");
		 }
		 try
		  {
			 dd_reportStep = new excel_utils ("D:\\SEB\\Assignment\\Test_Report\\Execution_Report.xlsx","Execution_StepReport","Write");
			Reporter.log("Test_Report -> Execution_StepReport - Successful",true);
			logger.info("Test_Report -> Execution_StepReport - Successful");
			//dd_reportSummary.report_StepStaus("Summary", "", "");

		  } catch (Exception e) {
			  Reporter.log("Exception " + e , true);
			  logger.info("Test_Report -> Execution_StepReport- Unsuccessful. Exception :" +e.getMessage());
		  }
		 if(dd_reportStep==null) {
			 System.out.println("Summary issue");
		 }
		  
		//Get the Chrome Driver
			if(browser.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver","\\D:\\SEB\\WebDrivers\\ChromeDriver.exe");
				  try {
					  driver = new ChromeDriver();  
					  driver.manage().deleteAllCookies();
					  }
				  catch (Exception e) {
					  extent_log.log(LogStatus.FAIL, "Not able to create driver for "+ browser +"\n Exception Cause : "+ e.getCause() + "Exception Message : "+ e.getMessage(), "fail");
					  Reporter.log("Not able to create driver for "+ browser +"\n Exception Cause : "+ e.getCause() + "Exception Message : "+ e.getMessage(),true);
					  logger.info("Not able to create driver for "+ browser +"\n Exception Cause : "+ e.getCause() + "Exception Message : "+ e.getMessage());
				  }
			}	  
				
			driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
			driver.get("https://opensource-demo.orangehrmlive.com/index.php");
			driver.manage().window().maximize();
			System.out.println(driver.getTitle());
				

}

@BeforeMethod
public void setUp(final ITestContext testContext) {
	System.out.println("Setup");
		test_sno = test_sno +1;
		
	  
	  System.out.println("Test Name " + tc_name);
	  //String tc_name1 = testContext.getAttribute("description");
	  //System.out.println(testContext.getName());
	  logger.info("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ");
	  logger.info("Execution Log for TC : " +tc_name);
	  logger.info("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ");
	  
	  Reporter.log("Execution Log for TC : " +tc_name,true);
	  
	  extent_log = extent.startTest(tc_name, tc_name);
	  extent_log.assignCategory("Functional");
	  
	  //dd_reportStep.tc_name = tc_name;
	  //dd_reportStep.report_StepStatus("", "new_testcase", "");
	  
	  tc_name = testContext.getName();
	  

		
}
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Login Credentials Invalid Test~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~`*/


/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Login Credentials Invalid Test~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~`*/
@Test(description="Login Function")
@Parameters({"user_id","password"})
  
  public void login(String user_id, String password) {
	  System.out.println("login");
	  ele = null;
	  driver.get("https://opensource-demo.orangehrmlive.com/index.php/dashboard");
	  driver.manage().window().maximize();
	 // extent_log = extent.startTest("passTest");
	  //report_log("Launch URL", "URL :https://opensource-demo.orangehrmlive.com/index.php/dashboard","Pass");
	  //logger.info("Launched URL :https://opensource-demo.orangehrmlive.com/index.php/dashboard" );
	 // Reporter.log("Launched URL", false);
	  //extent_log.log(LogStatus.PASS, "Launched URL in WebDriver", "URL: https://opensource-demo.orangehrmlive.com/index.php/dashboard");
	 try {
		  page_obj.txtbox_user_name(driver).sendKeys("");
		  page_obj.txtbox_user_name(driver).sendKeys(user_id);
		  report_log("Input User name","Set value for username :" + user_id ,"Pass");
	 }catch(Exception e) {
		 report_log("Input User name","Exception\n Cause: " +e.getCause()+ "Message : " +e.getMessage(),"Fail");
		 
	 }
	 try {
	  page_obj.txtbox_password(driver).sendKeys("");
	  page_obj.txtbox_password(driver).sendKeys(password);
	  page_obj.btn_login(driver).click();
	  report_log("Set Input Password","Value for Password Set.","Pass");
	  }
	 catch(Exception e) {
		 report_log("Input User name","Exception\n Cause:" +e.getCause()+ "Message :" +e.getMessage(),"Fail"); 	
	 }


		ele = driver.findElement(By.xpath(".//*[@id = 'welcome']"));  
		Assert.assertEquals(true, ele.isDisplayed(),"Element is not displayd");
		//Reporter.log("Successfully Logged");
		//Reporter.log("Logged in as : " + ele.getText());
		report_log("Welcome Page Displayed","Welcome message displayed","Pass");
		
		ele = driver.findElement(By.xpath(".//*[@id='menu_pim_viewPimModule']")); 
		Assert.assertEquals(true, ele!=null,"Element PIM module not present");
		ele.click();

	  //dd_reportStep.report_StepStatus("", "end_testcase", "");
	  extent_log.log(LogStatus.INFO, "End of Testcase.");
	  
  } 
@Test(description ="Verify Add Employee")
void verify_addnewemployee() throws Exception {
	//String page_title = null;
	System.out.println("verify");
	ele = null;
	ele = driver.findElement(By.xpath(".//*[@id='menu_pim_viewPimModule']"));
	ele = func_findWebElement_byxpath(driver,".//*[@id='menu_pim_viewPimModule']","PIM Module");
	Assert.assertEquals(true, ele!=null,"Element PIM module not present ");
	ele.click();
	report_log("Menu Item PIM Module","Clicked PIM module on Home Page ","Pass");
	
	ele = func_findWebElement_byxpath(driver,".//a[@id='menu_pim_addEmployee']","Add Employee");
	ele.click();
	driver.manage().timeouts().pageLoadTimeout(150, TimeUnit.SECONDS);
	driver.get("https://opensource-demo.orangehrmlive.com/index.php/pim/addEmployee");
	driver.manage().window().maximize();
	//page_title = driver.getTitle();
	
	//ele = func_findWebElement_byxpath(driver,".//div[@id='content']//div[@class='head']/h1[@text()='Add Employee']","Add Employee Page");
	//Assert.assertEquals(true, ele!=null,"Add Employee Page not visible");
	//report_log("Menu Item PIM Module","Clicked PIM module on Home Page ","Pass");
	
	
	//Employee Basic Information
	ele = func_findWebElement_byxpath(driver,".//div[@id='addEmployeeTbl']//input[@id='firstName']","Input Box First Name ");
	Assert.assertEquals(true, ele!=null,"Employee First Name Textbox");
	
	String value_excel = null;
	try {
		 value_excel = dd.getCellDataasstring(1,4);
		ele.sendKeys(value_excel);
		report_log("Textbox First Name","First Name Set value :  "+ value_excel,"Pass");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		report_log("Textbox First Name","Exception message :" +e.getMessage(),"Fail");
		e.printStackTrace();
	}
	ele = func_findWebElement_byxpath(driver,".//div[@id='addEmployeeTbl']//input[@id='lastName']","Input Box Last Name ");
	Assert.assertEquals(true, ele!=null,"Employee Last Name Textbox");
	try {
		value_excel = dd.getCellDataasstring(1,6);
		ele.sendKeys(value_excel);
		report_log("Textbox First Name","Last Name Set value :  "+ value_excel,"Pass");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		report_log("Textbox Last Name","Exception message :" +e.getMessage(),"Fail");
	}
	
	ele = func_findWebElement_byxpath(driver,".//input[@value='Save']","Save Button");
	Assert.assertEquals(true, ele!=null,"Add Employee. Save Button");
	ele.click();
	
	
	//Add personal details
	
	ele = func_findWebElement_byxpath(driver,".//div[@class='personalDetails']", "Personal Details Page");
	Assert.assertEquals(true, ele!=null,"Employee Personal Details Page");
	ele.click();
	//ele = func_findWebElement_byxpath(driver,".//div[@id='pdMainContainer']","Main Container");
	JavascriptExecutor js = (JavascriptExecutor) driver;
	js.executeScript("window.scrollBy(0,250)", "");
	//ele.sendKeys(Keys.ARROW_DOWN);
	ele = func_findWebElement_byxpath(driver,".//input[@id='btnSave']","Edit Button");
	Assert.assertEquals(true, ele!=null,"Edit Employee Personal Details Page.");
	ele.click();
	
	ele = func_findWebElement_byxpath(driver,".//input[@id='personal_txtNICNo']","SSN Number");
	Assert.assertEquals(true, ele!=null,"Edit Employee Personal Details Page.");
	value_excel = dd.getCellDataasstring(1,7);
	ele.sendKeys(value_excel);
	
	if(dd.getCellDataasstring(1, 8).equalsIgnoreCase("Male")) {
		ele = func_findWebElement_byxpath(driver,".//label[@for='personal_optGender_1']","Radio Button:Male");//.//input[@id='personal_optGender_1']","Radio Button Gender:Male");
	}
	else {
		ele = func_findWebElement_byxpath(driver,".//input[@id='personal_optGender_2']","Radio Button Gender : Female");
	}
	
	Assert.assertEquals(true, ele!=null,"Radio Button Emloyee Gender.");
		ele.click();
		
	//ele = func_findWebElement_byxpath(driver,"//input[@type='button'and @id='btnSave'and @value='Save']","Save Personal Details");
	ele = driver.findElement(By.cssSelector("input#btnSave"));
		if(ele==null) {
			report_log("Employee Personal Details Save Button","Not found Personal Details save button","Fail");
		}
		ele.click();
		report_log("Employee Personal Details Save Button","Clicked on on Personal Details save button","Pass");
		
		//Add Contact Details
		ele =null;
		String xpath = "//div[@id='sidebar']/ul[@id='sidenav']/li/a[text()='Contact Details'] "; 
		ele = func_findWebElement_byxpath(driver,xpath,"Contact Details");
		//highLighterMethod(driver,ele);
		Assert.assertEquals(true, ele!=null,"Side Nav ->  Employee Contact Details");
		ele.click();
		js.executeScript("window.scrollBy(0,900)", "");
		
		ele = driver.findElement(By.cssSelector("input#btnSave"));
		Assert.assertEquals(true, ele!=null,"Employee Contact Details -> Edit Details button");
		ele.click();
		
		Select dropdown = new Select(driver.findElement(By.id("contact_country")));
		dropdown.selectByValue(dd.getCellDataasstring(1,10));
		
		ele = driver.findElement(By.cssSelector("input#btnSave"));
		
			Assert.assertEquals(true, ele!=null,"Employee Contact Details -> Edit Details button");
	
		ele.click();
		
		
		//Add Emergency Contacts
		xpath = "//div[@id='sidebar']/ul[@id='sidenav']/li//a[text()='Emergency Contacts'] "; 
		ele = func_findWebElement_byxpath(driver,xpath,"Emergency Contacts Page");
		//highLighterMethod(driver,ele);
		Assert.assertEquals(true, ele!=null,"Side Nav -> Emergency Contact Details");
		ele.click();
		
		ele = driver.findElement(By.cssSelector("input#btnAddContact"));
		Assert.assertEquals(true, ele!=null,"Emergency Contacts -> Add");
		report_log("Add Emergency Contact Button","Add Emergency Cotact button visible","Pass");
		ele.click();

		ele = func_findWebElement_byxpath(driver,".//input[@id='emgcontacts_name']","Add Emergency Contact Name Details");
		//highLighterMethod(driver,ele);
		Assert.assertEquals(true, ele!=null,"Emergency Contacts -> Contact Name");
		ele.click();
		ele.sendKeys(dd.getCellDataasstring(1, 11));

		ele = func_findWebElement_byxpath(driver,".//input[@id='emgcontacts_relationship']","Emergency Contact Relationship Details");
		//highLighterMethod(driver,ele);
		Assert.assertEquals(true, ele!=null,"Emergency Contacts -> Relationship");
		ele.sendKeys(dd.getCellDataasstring(1, 12));
		
		ele = func_findWebElement_byxpath(driver,".//input[@id='emgcontacts_mobilePhone']","Emergency Contact Mobile Number Details");
		//highLighterMethod(driver,ele);
		Assert.assertEquals(true, ele!=null,"Emergency Contacts -> Mobile Number");
		ele.sendKeys(dd.getCellDataasstring(1, 13));
		
		ele = driver.findElement(By.cssSelector("input#btnSaveEContact"));
		Assert.assertEquals(true, ele!=null,"Emergency Contacts -> Save");
		report_log("Employee Personal Details Save Button","Save Emergency Contact","Pass");
		ele.click();
		
		//Add Dependents Information
		
		xpath = "//div[@id='sidebar']/ul[@id='sidenav']/li//a[text()='Dependents'] "; 
		ele = func_findWebElement_byxpath(driver,xpath,"Dependents Page");
		//highLighterMethod(driver,ele);
		Assert.assertEquals(true, ele!=null,"Side Nav -> Dependants");
		ele.click();
		
		ele = driver.findElement(By.cssSelector("input#btnAddDependent"));
		Assert.assertEquals(true, ele!=null,"Dependents Page -> Add Dependent");
		ele.click();
		
		ele = func_findWebElement_byxpath(driver,".//input[@id='dependent_name']","Dependent Name");
		//highLighterMethod(driver,ele);
		Assert.assertEquals(true, ele!=null,"Dependents Page -> Add Dependent ->Name");
		ele.sendKeys(dd.getCellDataasstring(1, 14));
		
		dropdown = new Select(driver.findElement(By.id("dependent_relationshipType")));
		dropdown.selectByValue(dd.getCellDataasstring(1,15).toLowerCase());

		report_log("Dependent Relationship","Dropdown value : " +  dd.getCellDataasstring(1,15)+" selected." ,"Pass");
		
		
		ele = func_findWebElement_byxpath(driver,".//input[@id='dependent_dateOfBirth']","Dependent Date Of Birth Details");
		//highLighterMethod(driver,ele);
		Assert.assertEquals(true, ele!=null,"Dependents Page -> Add Dependent ->DOB");
		ele.clear();
		ele.sendKeys(dd.getCellDataasstring(1, 16));	
		
		
		ele = driver.findElement(By.cssSelector("input#btnSaveDependent"));
		Assert.assertEquals(true, ele!=null,"Dependents Page -> Add Dependent ->Save");
		ele.click();
		
		
		//Add Immigration Details
		xpath = "//div[@id='sidebar']/ul[@id='sidenav']/li//a[text()='Immigration'] "; 
		ele = func_findWebElement_byxpath(driver,xpath,"Immigration Details Page");
		//highLighterMethod(driver,ele);
		Assert.assertEquals(true, ele!=null,"Side nav  -> Immigration");
		ele.click();
		
		ele = driver.findElement(By.cssSelector("input#btnAdd"));
		Assert.assertEquals(true, ele!=null,"Immigration ->Add Details");
		ele.click();
		report_log("Immigration ->Add Details","Add Immigration Details" ,"Pass");
		
		
		if(dd.getCellDataasstring(1,17)=="Visa") { 
			ele = func_findWebElement_byxpath(driver,"//input[@id='immigration_type_flag_2']","Immigration Type :Visa"); 
		}
		else {
			ele = func_findWebElement_byxpath(driver,"//input[@id='immigration_type_flag_1']","Immigration Type :Passport"); 
		}
		
		Assert.assertEquals(true, ele!=null,"Immigration ->Add Details ->"+ dd.getCellDataasstring(1,17) +" not found");
		ele.click();
		
		ele = func_findWebElement_byxpath(driver,"//input[@id='immigration_number']","Immigration Document Number"); 
		Assert.assertEquals(true, ele!=null,"Immigration ->Add Details -> Document Number");
		ele.sendKeys(dd.getCellDataasstring(1, 18));
		
		ele = driver.findElement(By.cssSelector("input#btnSave"));
		Assert.assertEquals(true, ele!=null,"Immigration ->Add Details -> Save");
		ele.click();
		report_log("Immigration ->Add Details -> Save","Clicked on Save " ,"Pass");
		
		//Add job details
		xpath = "//div[@id='sidebar']/ul[@id='sidenav']/li//a[text()='Job'] "; 
		ele = func_findWebElement_byxpath(driver,xpath,"Job Page");
		//highLighterMethod(driver,ele);
		Assert.assertEquals(true, ele!=null,"Side Nav -> Job");

		ele.click();
		
		
		ele = driver.findElement(By.cssSelector("input#btnSave"));
		Assert.assertEquals(true, ele!=null,"Job Details -> Add");
		ele.click();
		report_log("Job Details ->Add Details","Clicked on Add button " ,"Pass");
		
		dropdown = new Select(driver.findElement(By.id("job_job_title")));
		dropdown.selectByVisibleText(dd.getCellDataasstring(1, 19));
		report_log("Job Details ->Add Details -> Title","Selected Title: "+ dd.getCellDataasstring(1, 19) ,"Pass");
		
		ele = driver.findElement(By.cssSelector("input#btnSave"));
		Assert.assertEquals(true, ele!=null,"Job Details ->Add Details -> Save");
		ele.click();
		
		//add salary details
		xpath = "//div[@id='sidebar']/ul[@id='sidenav']/li//a[text()='Salary'] "; 
		ele = func_findWebElement_byxpath(driver,xpath,"Salary Details  Page");
		//highLighterMethod(driver,ele);
		Assert.assertEquals(true, ele!=null,"Side Nav -> Salary Page");
		ele.click();
		
		
		ele = driver.findElement(By.cssSelector("input#addSalary"));
		Assert.assertEquals(true, ele!=null,"Salary Page -> Salary Detail Add");
		ele.click();
		
		ele = func_findWebElement_byxpath(driver,"//input[@id='salary_salary_component']","Salary Component"); 
		Assert.assertEquals(true, ele!=null,"Salary Page -> Salary Detail Add ->Salary Component");
		ele.sendKeys(dd.getCellDataasstring(1, 20));

		
		ele = func_findWebElement_byxpath(driver,"//input[@id='salary_basic_salary']","Salary Amount"); 
		Assert.assertEquals(true, ele!=null,"Salary Page -> Salary Detail Add ->Salary Component");
		ele.sendKeys(dd.getCellDataasstring(1, 21));
		
		dropdown = new Select(driver.findElement(By.id("salary_currency_id")));
		try {
			dropdown.selectByVisibleText(dd.getCellDataasstring(1, 22));
			report_log("Salary ->Add Details -> Salary Currency","Selected Currency: "+ dd.getCellDataasstring(1, 22) ,"Pass");}
		catch(Exception e) {
			report_log("Salary ->Add Details -> Salary Currency","Not able to selectSelected Currency: "+ dd.getCellDataasstring(1, 22) ,"Fail");
		}
		
		ele = driver.findElement(By.cssSelector("input#btnSalarySave"));
		Assert.assertEquals(true, ele!=null,"Salary Page -> Salary Detail Add ->Save");
		ele.click();
		
		//Add for Tax Exemption
		
		xpath = "//div[@id='sidebar']/ul[@id='sidenav']/li//a[text()='Tax Exemptions'] "; 
		ele = func_findWebElement_byxpath(driver,xpath,"Tax Exemptions  Page");
		
		ele = driver.findElement(By.cssSelector("input#btnSave.savebutton"));
		Assert.assertEquals(true, ele!=null,"Exemption Page -> Exemption Details -> Edit");
		ele.click();
		//highLighterMethod(driver,ele);
		Assert.assertEquals(true, ele!=null,"Side Nav -> Tax Exemption");
		ele.click();
		
		
		dropdown = new Select(driver.findElement(By.id("tax_federalStatus"))); 
		dropdown.selectByVisibleText(dd.getCellDataasstring(1, 23));
		report_log("Tax Exemption ->Add Details -> Status","Selected Status: "+ dd.getCellDataasstring(1, 23) ,"Pass");
		
		ele = func_findWebElement_byxpath(driver,"//input[@id='tax_federalExemptions']"," Exemption"); 
		Assert.assertEquals(true, ele!=null,"Tax Exemption ->Exemptions");
		ele.sendKeys(dd.getCellDataasstring(1, 24));
		
		
		ele = driver.findElement(By.cssSelector("input#addSave"));
		Assert.assertEquals(true, ele!=null,"Tax Exemption ->Save");
		ele.click();
		
		//Add Report to details

		xpath = "//div[@id='sidebar']/ul[@id='sidenav']/li//a[text()='Report-to'] "; 
		ele = func_findWebElement_byxpath(driver,xpath,"SideNav ->Report-to page");
		//highLighterMethod(driver,ele);
		Assert.assertEquals(true, ele!=null,"Report- to page");
		ele.click();
		//ele.click();
		ele = driver.findElement(By.cssSelector("input#btnAddSupervisorDetail"));
		Assert.assertEquals(true, ele!=null,"Report-to page - > Add Supervisor Details");
		ele.click();
		
		ele = func_findWebElement_byxpath(driver,"//input[@id='reportto_supervisorName_empName']","Exemption ->Add Supervisor -> Supervisor Name");
		Assert.assertEquals(true, ele!=null,"Exemption ->Add Supervisor -> Supervisor Name");
		ele.sendKeys(dd.getCellDataasstring(1, 25));
		
		dropdown = new Select(driver.findElement(By.id("reportto_reportingMethodType"))); 
		dropdown.selectByVisibleText(dd.getCellDataasstring(1, 26));
		report_log("Report to ->Add Details -> Method","Selected Method: "+ dd.getCellDataasstring(1, 26) ,"Pass");
		ele = driver.findElement(By.cssSelector("input#btnSaveReportTo"));
		Assert.assertEquals(true, ele!=null,"Report to ->Save");
		ele.click();
		
		
		// Add Membership Details
		xpath = "//div[@id='sidebar']/ul[@id='sidenav']/li//a[text()='Memberships'] "; 
		ele = func_findWebElement_byxpath(driver,xpath,"Memberships Page");
		//highLighterMethod(driver,ele);
		Assert.assertEquals(true, ele!=null,"Side Nav -> Membership Page");
		ele.click();
		
		ele = driver.findElement(By.cssSelector("input#btnAddMembershipDetail"));
		Assert.assertEquals(true, ele!=null,"Memberships-> Add Details");
		ele.click();
		
		
		dropdown = new Select(driver.findElement(By.id("membership_membership"))); 
		dropdown.selectByVisibleText(dd.getCellDataasstring(1, 27));
		report_log("Report to ->Add Details -> Method","Selected Method: "+ dd.getCellDataasstring(1, 27) ,"Pass");
		
		
}

/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~``Function~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
/*Function name : afterTest
 *Purpose : Runs after every method"/
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/ 
@AfterMethod(alwaysRun=true)
protected void afterMethod(ITestResult result, ITestContext testContext) throws Exception{
	System.out.println("AfterMethod, Result" + result.getStatus());
	//System.out.println("Hello ji." + testContext);
    if (result.getStatus() == ITestResult.FAILURE) {
		extent_log.log(LogStatus.FAIL, "Test Case Failed is "+result.getName());
		extent_log.log(LogStatus.FAIL, "Test Case Failed is "+result.getThrowable().getCause());
    	//dd_reportSummary.report_TCSummary(tc_name, "Fail");
    } else {
    	//dd_reportSummary.report_TCSummary(tc_name, "Pass");
    	extent_log.log(LogStatus.PASS, "Test Case Execution Status");
    }
	 extent.endTest(extent_log);
	 extent.flush();

}
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~``Function~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
/*Function name : afterTest
 *Purpose : Runs after every test"/
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/ 
  @AfterClass
  public void afterTest() {
	  System.out.println("AfterClass");

	  extent.close();
		//driver.close();
		//driver.quit();
		}
 
  /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~``Function~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	/*Function name : highLighterMethod
	 *Purpose :Executes javascript to highlights the object"/
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/ 

	public void highLighterMethod(WebDriver driver, WebElement element){
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
	}
	
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~``Function~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	/*Function name : func_findWebElement_byxpath
	 *Purpose : Function finds the element based on xpath"/
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	private WebElement func_findWebElement_byxpath(WebDriver driver,String xpath, String objname) {
		
		WebElement ele = null;
		
		try {
			ele = driver.findElement(By.xpath(xpath));
			//System.out.println("Hello");
			logger.info("WebElement : " + objname +" available.");
			Reporter.log("WebElement : "+ objname +" available." );
			report_log("WebElement : "+ objname,"WebElement : "+ objname +"Exists","Pass");
			
		}
		catch (Exception e){
			report_log("WebElement : "+ objname,"WebElement : "+ objname +"does not Exist","Fail");
			ele = null;
			return ele;
		}
		return ele;
	}
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~End Function~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	private void report_log(String step_name, String description, String Status) {
		  logger.info("Step Name : \n"+step_name+ ", ---Description :" + description + ", --- Step Status : " + Status );
		  Reporter.log("Step Name : \n"+step_name+ ", ---Description :" + description + ", --- Step Status : " + Status , false);
		  if (Status.equalsIgnoreCase("Pass")) {
			  extent_log.log(LogStatus.PASS,step_name, description); 
		  }
		  else {
				  extent_log.log(LogStatus.FAIL,step_name, description); 
			  }  
		  }
		  
	
	}

