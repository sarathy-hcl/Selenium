
package com.hcl.taf;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import com.hcl.atf.taf.ConsoleWriter;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.hcl.atf.taf.testdata.TestDataManagerFactory;
import com.hcl.atf.taf.objectrepository.ObjectRepositoryManagerFactory;
import com.hcl.atf.taf.testdata.TAFTestDataManager;
import com.hcl.atf.taf.objectrepository.TAFObjectRepositoryManager;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.hcl.atf.taf.util.EvidenceCreation;

import java.util.logging.Level;
public class Main {
private java.io.File fileObj;
private java.util.HashMap hashMapObj;
private java.util.Map mapObj;
private com.hcl.atf.taf.testdata.TAFTestDataManager tafTestDataManagerFactoryObj;
private com.hcl.atf.taf.objectrepository.TAFObjectRepositoryManager tafObjectRepositoryManagerFactoryObj;
static java.lang.String devicePlatform;
static java.lang.String evidencePath;
static java.lang.String devicePort;
static java.lang.String runListId;
static java.lang.String projectPath;
static java.lang.String webURL;
static org.openqa.selenium.WebDriver driver;
public org.openqa.selenium.WebDriver webDriver;
public org.openqa.selenium.ie.InternetExplorerDriver internetExplorerDriver;
public org.openqa.selenium.firefox.FirefoxDriver firefoxDriver;
public org.openqa.selenium.chrome.ChromeDriver chromeDriver;
public org.openqa.selenium.safari.SafariDriver safariDriver;
public org.openqa.selenium.logging.LogType logType;
public org.openqa.selenium.logging.LoggingPreferences logPrefs;
public org.openqa.selenium.remote.CapabilityType capabilityType;
public org.openqa.selenium.remote.DesiredCapabilities caps;
public com.hcl.atf.taf.util.EvidenceCreation evidenceRefs;
String chromePath;
static String dependentFilesPath;
static java.util.Properties prop;
static String browserName;
static com.hcl.atf.taf.testdata.TAFTestDataManager tafTestDataManager;
static com.hcl.atf.taf.objectrepository.TAFObjectRepositoryManager tafObjectRepositoryManager;
public java.util.logging.Level levelRef;
public java.util.concurrent.TimeUnit timeUnitRef;
static Main main;
public static final String TEST_EXECUTION_ON_FAILURE_POLICY_CONTNUE = "Continue Execution";
public static final String TEST_EXECUTION_ON_FAILURE_POLICY_ABORT_JOB = "Abort Job";
public static final String TEST_EXECUTION_ON_FAILURE_POLICY_SKIP_TEST_STEPS = "Skip Tests";
public static final String TEST_EXECUTION_ON_FAILURE_POLICY_ABORT_WORKPACKAGE = "Abort Workpackage";
public static final String TEST_EXECUTION_ON_FAILURE_POLICY = TEST_EXECUTION_ON_FAILURE_POLICY_CONTNUE;
public static final String EVIDENCE_SCREENSHOT_POLICY_FAILED_ONLY = "FAILED";
public static final String EVIDENCE_SCREENSHOT_POLICY_PASSED_ONLY = "PASSED";
public static final String EVIDENCE_SCREENSHOT_POLICY_ALL = "ALL";
public static final String EVIDENCE_SCREENSHOT_POLICY = EVIDENCE_SCREENSHOT_POLICY_ALL;
static ChromeDriverService chromeDriverService;
//Setup method for the TestSuite/TestRun
@Test
public void setUp() {
  try {
	devicePlatform = System.getProperty("devicePlatform");
	System.out.println("DevicePlatform >>> "+devicePlatform);
	evidencePath=System.getProperty("EVIDENCE_PATH");
	runListId=System.getProperty("TESTRUNLIST_ID");
	EvidenceCreation.setdefaultTafEvidencePath(evidencePath);
	EvidenceCreation.setrunListId(runListId);
	EvidenceCreation.setdeviceId(devicePlatform);
	chromePath = System.getProperty("chrome");
	dependentFilesPath = System.getProperty("DEPENDENT_FILES_PATH");
	webURL=System.getProperty("webAppURL");
	  
	browserName = devicePlatform.split("-")[1];
	System.out.println("BrowserName : "+browserName);
	projectPath=Main.class.getProtectionDomain().getCodeSource().getLocation().getPath().substring(1).replace("/", java.io.File.separator);
	if(projectPath.endsWith(".jar"))projectPath=projectPath.substring(0,projectPath.lastIndexOf(java.io.File.separator)+1);
	//work around for local execution
	if(projectPath==null || projectPath.endsWith("null")) projectPath=new java.io.File(".").getAbsolutePath();
	
	  try{
            String receivedTestDataFilePath=System.getProperty("TESTDATA");
            String[] testDataFileNames = receivedTestDataFilePath.split(",");
            String testDataFilesPath="";
            for(String testDataFile : testDataFileNames)
            {
                if(testDataFile.length()>0)
                    testDataFilesPath = testDataFilesPath + ',' + projectPath +  File.separator + testDataFile;
            }
            System.out.println("Test Data Files :"+ testDataFilesPath);
            if(testDataFilesPath.length() > 1 )
            {
                testDataFilesPath = testDataFilesPath.trim().substring(1);
                Map <String, String> testdata_DataSourceProperties = new HashMap<String, String>();
                testdata_DataSourceProperties.put(TestDataManagerFactory.DATA_PROPERTIES_FILENAME_KEY, testDataFilesPath);
                testdata_DataSourceProperties.put(TestDataManagerFactory.DATA_PROPERTIES_WORKSHEET_KEY, "Test-Data");
                tafTestDataManager = TestDataManagerFactory.getTestDataManager(TestDataManagerFactory.DATA_SOURCE_EXCEL, testdata_DataSourceProperties);
            }
            else
            {
                tafTestDataManager=null;
            }
        }
        catch(Exception ex){
            System.out.println("Could not load test data" + ex.getMessage());
            throw ex;
        }
        try{
            String receivedTestObjectRepositoryFilePath=System.getProperty("OBJ_REPOSITORY");
            String[] testObjectRepositoryFileNames = receivedTestObjectRepositoryFilePath.split(",");
            String testObjectRepositoryFilesPath="";
            for(String testObjectRepositoryFile : testObjectRepositoryFileNames)
            {
                testObjectRepositoryFilesPath = testObjectRepositoryFilesPath + ',' + projectPath +  File.separator + testObjectRepositoryFile;
            }
            testObjectRepositoryFilesPath = testObjectRepositoryFilesPath.trim().substring(1);
            System.out.println("Object Repository Files :"+ testObjectRepositoryFilesPath);
            Map <String, String> objectRepository_DataSourceProperties = new HashMap<String, String>();
            objectRepository_DataSourceProperties.put(ObjectRepositoryManagerFactory.PROPERTIES_FILENAME_KEY, testObjectRepositoryFilesPath);
            objectRepository_DataSourceProperties.put(ObjectRepositoryManagerFactory.PROPERTIES_WORKSHEET_KEY, "Object-Repository");
            objectRepository_DataSourceProperties.put(ObjectRepositoryManagerFactory.PROPERTIES_APPLICATION_TYPE_KEY, "Web");
            objectRepository_DataSourceProperties.put(ObjectRepositoryManagerFactory.PROPERTIES_BROWSER_KEY, browserName);
            tafObjectRepositoryManager = ObjectRepositoryManagerFactory.getObjectRepositoryManager(ObjectRepositoryManagerFactory.DATA_SOURCE_EXCEL, objectRepository_DataSourceProperties);
        }
        catch(Exception ex){
            System.out.println("Could not load object repository" + ex.getMessage());
            throw ex;
    }
	
	switch(browserName)
		{
		case "FIREFOX":
			caps = DesiredCapabilities.firefox();
			logPrefs = new LoggingPreferences();
			logPrefs.enable(LogType.BROWSER, Level.ALL);
			caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);			
			driver=new FirefoxDriver(caps);
			break;
		case "IE":
			System.setProperty("webdriver.ie.driver","IEDriverServer.exe");
			System.setProperty("webdriver.ie.driver.loglevel","DEBUG");
			caps = DesiredCapabilities.internetExplorer();
			logPrefs = new LoggingPreferences();
			logPrefs.enable(LogType.BROWSER, Level.ALL);
			caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
           caps.setCapability("ie.ensureCleanSession",true);
           InternetExplorerDriverService ieDriverService = InternetExplorerDriverService.createDefaultService();
            int port = ieDriverService.getUrl().getPort();
            int driverProcessID = 0;
			int browserProcessID = 0;
			driver=new InternetExplorerDriver(ieDriverService, caps);
			System.out.println("Starting IEDriver on port " + port);
			try {
				int chromeDriverProcessID = getChromeDriverProcessID(port);
				System.out.println("Process ID : "+chromeDriverProcessID);				
			    System.out.println("Detected IE process id " + getChromeProcesID(chromeDriverProcessID));
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			break;
		case "CHROME":
			System.setProperty("webdriver.chrome.driver","chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			/*options.addArguments("headless");
            options.addArguments("window-size=1200x600");
			options.addArguments("disable-gpu");*/
			//options.addArguments("window-size=1920,1080");
			/*options.addArguments("--disable-dev-shm-usage");
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-features=VizDisplayCompositor");*/
			
			caps = DesiredCapabilities.chrome();
			logPrefs = new LoggingPreferences();
			logPrefs.enable(LogType.BROWSER, Level.ALL);
			caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
			caps.setCapability("chromeOptions",options);
			chromeDriverService = ChromeDriverService.createDefaultService();		
		    port = chromeDriverService.getUrl().getPort();
		    driver = new ChromeDriver(options);
		    /*System.out.println("starting chromedriver on port " + port);
			try {
				int chromeDriverProcessID = getChromeDriverProcessID(port);
				System.out.println("Process ID : "+chromeDriverProcessID);
			} catch (Exception e) {
				
				e.printStackTrace();
			}	*/
			break;
		case "SAFARI":
			caps = DesiredCapabilities.safari();
			logPrefs = new LoggingPreferences();
			logPrefs.enable(LogType.BROWSER, Level.ALL);
			caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
			driver=new SafariDriver(caps);
			break;	
		
		case "FIREFOXGECKO":
			System.setProperty("webdriver.gecko.driver","geckodriver.exe");
			driver = new FirefoxDriver();
			break;	
	}
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  ConsoleWriter.setMain(this);
  ConsoleWriter.setTestExecutionOnFailurePolicy(TEST_EXECUTION_ON_FAILURE_POLICY);
  }  catch (Exception e) {
		System.out.println("initialization failed."+e.getMessage());
		tearDown();
  }
}
//Teardown method for the TestSuite/TestRun                         
@AfterSuite
public void tearDown() {
	try {
		System.out.println("teardown selenium");
		driver.quit();
		
		
   }  catch (Exception e) {
		System.out.println("Exception occured while quitting from driver during tear down .");
   }
		System.out.println(ConsoleWriter.TAF.END);
}
public static Main getMain()
{
  if(main==null)main=new Main();
   return main;
}
public static java.lang.String getWebURL()
{
   return webURL;
}
public static org.openqa.selenium.WebDriver getDriver()
{
  return driver;
}
public static void setDriver(WebDriver driver){
	Main.driver = driver;
}
public static void setDriver()
{
   Main.driver = driver; 
}
public static java.lang.String getDevicePlatform()
{
  return devicePlatform;
}
public static void setDevicePlatform()
{
   Main.devicePlatform = devicePlatform;
}
public static java.lang.String getProjectPath()
{
  return projectPath;
}
public static void setProjectPath()
{
   Main.projectPath = projectPath;
}
public static java.lang.String getRunlistId()
{
  return runListId;
}
public static void setrunListId()
{
  Main.runListId = runListId;
}
public static java.lang.String getdependentFilesPath()
{
  return dependentFilesPath;
}
public static void setdependentFilesPath()
{
   Main.dependentFilesPath = dependentFilesPath;
}
public static com.hcl.atf.taf.testdata.TAFTestDataManager getTafTestDataManager()
{
 return tafTestDataManager;
}
public static com.hcl.atf.taf.objectrepository.TAFObjectRepositoryManager getTafObjectRepositoryManager()
{
  return tafObjectRepositoryManager;
}
public static java.lang.String getBrowserName()
{
  return browserName; 
}
public org.openqa.selenium.WebDriver reinstantiateDriver()
{
   switch(browserName)
		{
		case "FIREFOX":
			caps = DesiredCapabilities.firefox();
			logPrefs = new LoggingPreferences();
			logPrefs.enable(LogType.BROWSER, Level.ALL);
			caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
			driver=new FirefoxDriver(caps);
			break;
		case "IE":
			System.setProperty("webdriver.ie.driver",dependentFilesPath+"\\IEDriverServer.exe");
			System.setProperty("webdriver.ie.driver.loglevel","DEBUG");
			caps = DesiredCapabilities.internetExplorer();
			logPrefs = new LoggingPreferences();
			logPrefs.enable(LogType.BROWSER, Level.ALL);
			caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
			caps.setCapability("ie.ensureCleanSession",true);
			driver=new InternetExplorerDriver(caps);
			break;
		case "CHROME":
			System.setProperty("webdriver.chrome.driver",dependentFilesPath+"\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-dev-shm-usage");
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-features=VizDisplayCompositor");
			caps = DesiredCapabilities.chrome();
			caps.setBrowserName(browserName);
			logPrefs = new LoggingPreferences();
			logPrefs.enable(LogType.BROWSER, Level.ALL);
			caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
			caps.setCapability("chromeOptions",options);
			ChromeDriverService chromeDriverService = ChromeDriverService.createDefaultService();
		    int port = chromeDriverService.getUrl().getPort();
		    driver = new ChromeDriver(chromeDriverService, caps);
		    System.out.println("starting chromedriver on port " + port);
			try {
				int chromeDriverProcessID = getChromeDriverProcessID(port);
				System.out.println("Process ID : "+chromeDriverProcessID);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			break;
		case "SAFARI":
			caps = DesiredCapabilities.safari();
			logPrefs = new LoggingPreferences();
			logPrefs.enable(LogType.BROWSER, Level.ALL);
			caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
			driver=new SafariDriver(caps);
			break;	
		
		case "FIREFOXGECKO":
			System.setProperty("webdriver.gecko.driver",dependentFilesPath+"\\geckodriver.exe");
			driver = new FirefoxDriver();
			break;	
	}
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
return driver;
}


public static String getEvidencePath()
{
 return evidencePath; 
}
public static void setEvidencePath()
{
  Main.evidencePath = evidencePath;
}
public static java.util.Properties getIdentifiersPropertyFile()
{
  return prop; 
}

private static int getChromeDriverProcessID(int aPort) throws Exception
{
  String[] commandArray = new String[3];

	  commandArray[0] = "cmd";
    commandArray[1] = "/c";
    commandArray[2] = "netstat -aon | findstr LISTENING | findstr " + aPort;
  
	System.out.println("running command " + commandArray[2]);

  Process p = Runtime.getRuntime().exec(commandArray);
  p.waitFor();

  BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

  StringBuilder sb = new StringBuilder();
  String line = "";
  while ((line = reader.readLine()) != null)
  {
    sb.append(line + "\n");
  }

  String result = sb.toString().trim();

  System.out.println("parse command response line:");
  System.out.println(result);

  return parseChromeDriverWindows(result);
}

private static int getChromeProcesID(int chromeDriverProcessID) throws Exception
{
	 String[] commandArray = new String[3];    
   commandArray[0] = "cmd";
   commandArray[1] = "/c";
   commandArray[2] = "wmic process get processid,parentprocessid,executablepath | find \"chrome.exe\" |find \"" + chromeDriverProcessID + "\"";
  
  System.out.println("running command " + commandArray[2]);

  Process p = Runtime.getRuntime().exec(commandArray);
  p.waitFor();

  BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

  StringBuilder sb = new StringBuilder();
  String line = "";
  while ((line = reader.readLine()) != null)
  {
		sb.append(line + "\n");
  }

  String result = sb.toString().trim();

  System.out.println("parse command response line:");
  System.out.println(result);

  return parseChromeWindows(result);
}

private static int parseChromeDriverWindows(String result)
{
  String[] pieces = result.split("\\s+");    
  return Integer.parseInt(pieces[pieces.length - 1]);
}

private static int parseChromeWindows(String netstatResult)
{
  String[] pieces = netstatResult.split("\\s+");
  // TCP 127.0.0.1:26599 0.0.0.0:0 LISTENING 22828
  return Integer.parseInt(pieces[pieces.length - 1]);
}
public static ChromeDriverService getChromeDriverService() {
	return chromeDriverService;
}
public static void setChromeDriverService(
		ChromeDriverService chromeDriverService) {
	Main.chromeDriverService = chromeDriverService;
}
}
