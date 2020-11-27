package com.hcl.taf;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.hcl.atf.taf.objectrepository.TAFObjectRepositoryManager;
import com.hcl.taf.Identity;
import com.hcl.atf.taf.testdata.TAFTestDataManager;
//import com.hcl.ers.atsg.script.utils.Identity;



import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Util {
    public TAFObjectRepositoryManager tafObjectRepositoryManagerFld;
    public TAFTestDataManager tafTestDataManagerReferenceFld;
    public WebDriver driver;
    public WebDriverWait wtDriver;
    public String browserName;
    Hashtable<String,String> ht = new Hashtable<String,String>();
    
    public Util()
    {
    	tafTestDataManagerReferenceFld = Main.getMain().getTafTestDataManager();
        tafObjectRepositoryManagerFld = Main.getMain().getTafObjectRepositoryManager();
        browserName = Main.getMain().getBrowserName().toUpperCase();
        System.out.println(browserName);
        driver = Main.getMain().getDriver();
        
    }
	
	public Util(TAFTestDataManager tafTestDataManager, TAFObjectRepositoryManager tafObjectRepositoryManager, WebDriver webdriver)
    {    	    
		browserName = Main.getMain().getBrowserName();
        driver = webdriver;
  		tafTestDataManagerReferenceFld = tafTestDataManager;
        tafObjectRepositoryManagerFld = tafObjectRepositoryManager;
    }
	
	public Util( WebDriver webdriver){	
        driver = webdriver;
        this.browserName = Main.getMain().browserName;
        tafTestDataManagerReferenceFld = Main.getMain().getTafTestDataManager();
        tafObjectRepositoryManagerFld = Main.getMain().getTafObjectRepositoryManager();		
    }
    
    /*public Identity getIdentity(String id) {
        try { 
        	Identity identity = new Identity();
        	if(id.startsWith("#E:")){ 
        	id=id.substring(3);} 
        	
        	identity.Identifier = tafObjectRepositoryManagerFld.getWebElementId(id, browserName).id; 
        	identity.IdentifierType = tafObjectRepositoryManagerFld.getWebElementId(id, browserName).idType;
         	identity.Identifier2 = tafObjectRepositoryManagerFld.getWebElementId(id, browserName).id; 
        	identity.IdentifierType2 = tafObjectRepositoryManagerFld.getWebElementId(id, browserName).idType;
  
        	return identity; 	
        }catch(Exception ex){ 
        	return null; 
        }
    }*/ 

    /*public Identity getIdentity(String id, String applicationType) {
        try { 
        	Identity identity = new Identity();
        	if(id.startsWith("#E:")){ 
        	id=id.substring(3);} 
        	
        	identity.Identifier = tafObjectRepositoryManagerFld.getWebElementId(id, browserName).id; 
        	identity.IdentifierType = tafObjectRepositoryManagerFld.getWebElementId(id, browserName).idType;
         	identity.Identifier2 = tafObjectRepositoryManagerFld.getWebElementId(id, browserName).id; 
        	identity.IdentifierType2 = tafObjectRepositoryManagerFld.getWebElementId(id, browserName).idType;
  
        	return identity; 	
        }catch(Exception ex){ 
        	return null; 
        }
    }*/ 
    
 // For performing Run time variable Operation -  Prasanna.B
    public String processStringVariableData(String varname) throws Exception {
    	
		//String retrieveddata="";
	
 		if(varname.startsWith("#V:"))
 		{
 			
 		   varname = varname.substring(3);
 		   return varname;
 		
 		}
 		else
 		{
 		   return varname;
 			
 		}
 	
}
    
    // For performing Run time variable Operation -  Prasanna.B
    public String StoreRuntimeVariableVal(String Myvarname,String MyVarnameValue)
    {	
      	if(ht.size() > 0 ){
    		for(Map.Entry<String,String> name : ht.entrySet()){
    			String hashTablevariablename = name.getKey();
    			if(hashTablevariablename.equals(Myvarname))
    				return Myvarname +" is already existed in the entry";
    		}
    	}
    	ht.put(Myvarname, MyVarnameValue);	    	
    	return Myvarname +" is added";	    	
    }    
    
    
    
   // For performing Run time variable Operation -  Prasanna.B
    public String getRuntimeVariableVal(String Myvarname)
    {
    	String MyvarnameValue = ht.get(Myvarname);
    	return MyvarnameValue;
    	
    }
    
    public Identity getIdentity(String id)
	{
		try
		{
			Identity identity = new Identity();  

			if(id.startsWith("#E:"))
			{
				id=id.substring(3);
			}

			identity.Identifier = tafObjectRepositoryManagerFld.getWebElementId(id, browserName).id;
			identity.IdentifierType = tafObjectRepositoryManagerFld.getWebElementId(id, browserName).idType;

			return identity;
		}
		catch(Exception ex)
		{
			return null;
		}
	}


	public String processStringData(String data) throws Exception {

		String retrieveddata="";

		if(data.startsWith("#D:"))
		{
			if(tafTestDataManagerReferenceFld == null)
			{
				throw new Exception("Invalid test data manager. It is null");
			}

			data = data.substring(3);


			retrieveddata = tafTestDataManagerReferenceFld.getTextData(data);
			if(retrieveddata == null) {
				throw new Exception("Could not retrieve data for key: "+data);
			}
			return retrieveddata;
		}
		else
		{
			if(tafTestDataManagerReferenceFld == null)
			{
				return data;
			}

			try
			{
				retrieveddata = tafTestDataManagerReferenceFld.getTextData(data);
				if(retrieveddata == null) {
					return data;
				}
			}
			catch(Exception ex)
			{
				return data;
			}

			return retrieveddata;
		}

	}

	public Integer processIntData(String data) throws Exception{

		int retrieveddata=0;

		if(data.startsWith("#D:"))
		{
			if(tafTestDataManagerReferenceFld == null)
			{
				throw new Exception("Invalid test data manager. It is null");
			}

			data = data.substring(3);

			retrieveddata = tafTestDataManagerReferenceFld.getIntData(data);
			if(retrieveddata == -1) 
			{
				throw new Exception("Could not retrieve data for key: "+data);	
			}
		}
		else
		{
			if(tafTestDataManagerReferenceFld == null)
			{
				return Integer.parseInt(data);	
			}

			try
			{
				retrieveddata = tafTestDataManagerReferenceFld.getIntData(data);
				if(retrieveddata == -1) {
					return Integer.parseInt(data);
				}
			}
			catch(Exception ex)
			{
				return Integer.parseInt(data);
			}
		}

		return retrieveddata;
	}

	public Double processDoubleData(String data) throws Exception{

		double retrieveddata=0;

		if(data.startsWith("#D:"))
		{
			if(tafTestDataManagerReferenceFld == null)
			{
				throw new Exception("Invalid test data manager. It is null");
			}

			data = data.substring(3);

			retrieveddata = tafTestDataManagerReferenceFld.getDoubleData(data);
			if(retrieveddata == -1) 
			{
				throw new Exception("Could not retrieve data for key: "+data);	
			}
		}
		else
		{
			if(tafTestDataManagerReferenceFld == null)
			{
				return Double.parseDouble(data);	
			}

			try
			{
				retrieveddata = tafTestDataManagerReferenceFld.getDoubleData(data);
				if(retrieveddata == -1) {
					return Double.parseDouble(data);
				}
			}
			catch(Exception ex)
			{
				return Double.parseDouble(data);
			}
		}

		return retrieveddata;

	}

	public Date processDateData(String data) throws Exception {

		Date retrieveddata=null;


		if(data.startsWith("#D:"))
		{
			if(tafTestDataManagerReferenceFld == null)
			{
				throw new Exception("Invalid test data manager. It is null");
			}

			data = data.substring(3);

			retrieveddata = tafTestDataManagerReferenceFld.getDateData(data);
			if(retrieveddata == null) {
				throw new Exception("Could not retrieve data for key: "+data);
			}
		}
		else
		{
			if(tafTestDataManagerReferenceFld == null)
			{
				try
				{
					DateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss z", Locale.ENGLISH);
					Date retData = format.parse(data);
					return retData;
				}
				catch(Exception ex)
				{
					throw new Exception("Given data "+data+" is not a valid date. Date should be in format dd-MMM-yyyy HH:mm:ss z Eg: 06-Apr-2016 15:30:20 IST. Conversion resulted in following exception: "+ ex.getMessage());
				}
			}


			try
			{
				retrieveddata = tafTestDataManagerReferenceFld.getDateData(data);
				if(retrieveddata == null) {
					try
					{
						DateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss z", Locale.ENGLISH);
						Date retData = format.parse(data);
						return retData;
					}
					catch(Exception ex)
					{
						throw new Exception("Given data "+data+" is not a valid date. Date should be in format dd-MMM-yyyy HH:mm:ss z Eg: 06-Apr-2016 15:30:20 IST. Conversion resulted in following exception: "+ ex.getMessage());
					}
				}
			}
			catch(Exception ex)
			{
				try
				{
					DateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss z", Locale.ENGLISH);
					Date retData = format.parse(data);
					return retData;
				}
				catch(Exception ex1)
				{
					throw new Exception("Given data "+data+" is not a valid date. Date should be in format dd-MMM-yyyy HH:mm:ss z Eg: 06-Apr-2016 15:30:20 IST. Conversion resulted in following exception: "+ ex1.getMessage());
				}
			}
		}

		return retrieveddata;
	}
   
  
    public WebElement findElement(String identifierType, String identifier) {
        WebElement element = null;	
        switch(identifierType) { 
        	case "id":element = driver.findElement(By.id(identifier)); 
        	break; 
        	case "className": element = driver.findElement(By.className(identifier)); 
        	break; 
        	case "cssSelector": element = driver.findElement(By.cssSelector(identifier)); 
        	break; 
        	case "linkText": element = driver.findElement(By.linkText(identifier)); 
        	break; 
        	case "partialLinkText": element = driver.findElement(By.partialLinkText(identifier)); 
        	break; 
        	case "name": element = driver.findElement(By.name(identifier)); 
        	break; 
        	case "tagName": element = driver.findElement(By.tagName(identifier)); 
        	break; 
        	case "xpath": element = driver.findElement(By.xpath(identifier)); 
        	break; 
        } 
        return element;
    }
    
    
   // key Strokes 
    /*  public void SendKeyStrokes(String keyStrokes, String id){
    	
     	WebElement element;
        Robot robot = new Robot();
        Identity identity = getIdentity(id);
        			switch(keyStrokes){			        			
        			case "ADD":
        				element.sendKeys(Keys.ADD);
        				break;
        			case "ALT":
    					element.sendKeys(Keys.ALT);			        					
        				break;	
        			case "ARROW_LEFT":
        				element.sendKeys(Keys.ARROW_LEFT);
        				break;	
        			case "ARROW_RIGHT":
        				element.sendKeys(Keys.ARROW_RIGHT);
        				break;
        			case "ARROW_UP":
        				element.sendKeys(Keys.ARROW_UP);
        				break;
        			case "ARROW_DOWN":
        				element.sendKeys(Keys.ARROW_DOWN);
        				break;
        			case "BACK_SPACE":
        				element.sendKeys(Keys.BACK_SPACE);
        				break;
        			case "CANCEL":
        				element.sendKeys(Keys.CANCEL);
        				break;
        			case "CLEAR":
        				//element.sendKeys(Keys.CLEAR);
        				element.clear();
        				//robot.keyPress(KeyEvent.VK_CLEAR);
        				break;
        			case "COMMAND":
        				element.sendKeys(Keys.COMMAND);
        				break;
        			case "CONTROL":
        				element.sendKeys(Keys.CONTROL);
        				break;
        			case "DECIMAL":
        				element.sendKeys(Keys.DECIMAL);
        				break;
        			case "DELETE":
        				element.sendKeys(Keys.DELETE);
        				break;
        			case "DIVIDE":
        				element.sendKeys(Keys.DIVIDE);
        				break;
        			case "DOWN":
        				element.sendKeys(Keys.DOWN);
        				break;
        			case "END":
        				element.sendKeys(Keys.END);
        				break;
        			case "ENTER":
        				element.sendKeys(Keys.ENTER);
        				break;
        			case "EQUALS":
        				element.sendKeys(Keys.EQUALS);
        				break;
        			case "ESCAPE":
        				//element.sendKeys(Keys.ESCAPE);
        				robot.keyPress(KeyEvent.VK_ESCAPE);
        				break;
        			case "F1":
        				robot.keyPress(KeyEvent.VK_F1);
        				break;
        			case "F2":
        				robot.keyPress(KeyEvent.VK_F2);
        				break;
        			case "F3":
        				robot.keyPress(KeyEvent.VK_F3);
        				break;
        			case "F4":
        				robot.keyPress(KeyEvent.VK_F4);
        				break;
        			case "F5":
        				//element.sendKeys(Keys.F5);
        				robot.keyPress(KeyEvent.VK_F5);
        				break;
        			case "F6":
        				robot.keyPress(KeyEvent.VK_F6);
        				break;
        			case "F7":
        				robot.keyPress(KeyEvent.VK_F7);
        				break;
        			case "F8":
        				robot.keyPress(KeyEvent.VK_F8);
        				break;
        			case "F9":
        				robot.keyPress(KeyEvent.VK_F9);
        				break;
        			case "F10":
        				robot.keyPress(KeyEvent.VK_F10);
        				break;
        			case "F11":
        				
        				robot.keyPress(KeyEvent.VK_F11);
        				break;
        			case "F12":
        				robot.keyPress(KeyEvent.VK_F12);
        				break;
        			case "HELP":
        				element.sendKeys(Keys.HELP);
        				break;
        			case "HOME":
        				element.sendKeys(Keys.HOME);
        				break;
        			case "INSERT":
        				//element.sendKeys(Keys.INSERT);
        				robot.keyPress(KeyEvent.VK_INSERT);
        				break;
        			case "LEFT":
        				element.sendKeys(Keys.LEFT);
        				break;
        			case "LEFT_ALT":
        				element.sendKeys(Keys.LEFT_ALT);
        				break;
        			case "LEFT_CONTROL":
        				element.sendKeys(Keys.LEFT_CONTROL);
        				break;
        			case "LEFT_SHIFT":
        				element.sendKeys(Keys.LEFT_SHIFT);
        				break;
        			case "META":
        				element.sendKeys(Keys.META);
        				break;
        			case "MULTIPLY":
        				element.sendKeys(Keys.MULTIPLY);
        				break;
        			case "NULL":
        				element.sendKeys(Keys.NULL);
        				break;
        			case "NUMPAD0":
        				element.sendKeys(Keys.NUMPAD0);
        				break;
        			case "FNUMPAD1":
        				element.sendKeys(Keys.NUMPAD1);
        				break;
        			case "NUMPAD2":
        				element.sendKeys(Keys.NUMPAD2);
        				break;
        			case "NUMPAD3":
        				element.sendKeys(Keys.NUMPAD3);
        				break;
        			case "NUMPAD4":
        				element.sendKeys(Keys.NUMPAD4);
        				break;
        			case "NUMPAD5":
        				element.sendKeys(Keys.NUMPAD5);
        				break;
        			case "NUMPAD6":
        				element.sendKeys(Keys.NUMPAD6);
        				break;
        			case "NUMPAD7":
        				element.sendKeys(Keys.NUMPAD7);
        				break;
        			case "NUMPAD8":
        				element.sendKeys(Keys.NUMPAD8);
        				break;
        			case "NUMPAD9":
        				element.sendKeys(Keys.NUMPAD9);
        				break;
        			case "PAGE_DOWN":
        				element.sendKeys(Keys.PAGE_DOWN);
        				break;
        			case "PAGE_UP":
        				element.sendKeys(Keys.PAGE_UP);
        				break;
        			case "PAUSE":
        				element.sendKeys(Keys.PAUSE);
        				break;
        			case "RETURN":
        				element.sendKeys(Keys.RETURN);
        				break;
        			case "RIGHT":
        				element.sendKeys(Keys.RIGHT);
        				break;
        			case "SEMICOLON":
        				element.sendKeys(Keys.SEMICOLON);
        				break;
        			case "SEPARATOR":
        				element.sendKeys(Keys.SEPARATOR);
        				break;
        			case "SHIFT":
        				element.sendKeys(Keys.SHIFT);
        				break;
        			case "SPACE":
        				element.sendKeys(Keys.SPACE);
        				break;
        			case "SUBTRACT":		
        				element.sendKeys(Keys.SUBTRACT);
        				break;
        			case "TAB":
        				element.sendKeys(Keys.TAB);
        				break;
        			case "UP":
        				element.sendKeys(Keys.UP);
        				break;
        			case "values":
        				element.sendKeys(Keys.values());
        				break;
        			case "ZENKAKU_HANKAKU":
        				element.sendKeys(Keys.ZENKAKU_HANKAKU);
        				break;
        			/*default:
        				//element.sendKeys(arrkeyStrokes.get(i));
        				break;*/
        			
    
    	
    // For List operations 
    public List<WebElement> findElements(String identifierType2, String identifier2)
    {
    	List<WebElement> Listelement = null;
    	
    	switch(identifierType2)
		{
		
			case "id":
				Listelement = driver.findElements(By.id(identifier2));
				break;
			
			case "className":
				Listelement = driver.findElements(By.className(identifier2));
				break;
			
			case "cssSelector":	
				Listelement = driver.findElements(By.cssSelector(identifier2));
				break;
			
			case "linkText":
				Listelement = driver.findElements(By.linkText(identifier2));
				break;
			
			case "partialLinkText":	
				Listelement = driver.findElements(By.partialLinkText(identifier2));
				break;
			
			case "name":
				Listelement = driver.findElements(By.name(identifier2));
				break;
			
			case "tagName":	
				Listelement = driver.findElements(By.tagName(identifier2));
				break;
			
			case "xpath":
				Listelement = driver.findElements(By.xpath(identifier2));
				break;
		}
    	
    	return Listelement;
    }

    
    
    public WebElement getWebelementValue(Identity identity, Integer waitTime) {
        	WebElement element=null;
        	switch(identity.IdentifierType){
        		case "id":
        		element = (new WebDriverWait(driver, waitTime)).until(ExpectedConditions.presenceOfElementLocated(By.id(identity.Identifier)));
        		break;
        		case "className":
        		element = (new WebDriverWait(driver, waitTime)).until(ExpectedConditions.presenceOfElementLocated(By.className(identity.Identifier)));
        		break;
        		case "cssSelector":
        		element = (new WebDriverWait(driver, waitTime)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(identity.Identifier)));
        		break;
        		case "linkText":
        		element = (new WebDriverWait(driver, waitTime)).until(ExpectedConditions.presenceOfElementLocated(By.linkText(identity.Identifier)));
        		break;
        		case "partialLinkText":
        		element = (new WebDriverWait(driver, waitTime)).until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(identity.Identifier)));
        		break;
        		case "name":
        		element = (new WebDriverWait(driver, waitTime)).until(ExpectedConditions.presenceOfElementLocated(By.name(identity.Identifier)));
        		break;
        		case "tagName":
        		element = (new WebDriverWait(driver, waitTime)).until(ExpectedConditions.presenceOfElementLocated(By.tagName(identity.Identifier)));
        		break;
        		case "xpath":
        		element = (new WebDriverWait(driver, waitTime)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(identity.Identifier)));
        		break;
        	}
        	return element;
    }
    
    public WebElement WaitForElementToBeClickable(Identity identity, Integer waitTime) {
        WebElement element=null;
        switch(identity.IdentifierType){
               case "id":
               element = (new WebDriverWait(driver, waitTime)).until(ExpectedConditions.elementToBeClickable(By.id(identity.Identifier)));
               break;
               case "className":
               element = (new WebDriverWait(driver, waitTime)).until(ExpectedConditions.elementToBeClickable(By.className(identity.Identifier)));
               break;
               case "cssSelector":
               element = (new WebDriverWait(driver, waitTime)).until(ExpectedConditions.elementToBeClickable(By.cssSelector(identity.Identifier)));
               break;
               case "linkText":
               element = (new WebDriverWait(driver, waitTime)).until(ExpectedConditions.elementToBeClickable(By.linkText(identity.Identifier)));
               break;
               case "partialLinkText":
               element = (new WebDriverWait(driver, waitTime)).until(ExpectedConditions.elementToBeClickable(By.partialLinkText(identity.Identifier)));
               break;
               case "name":
               element = (new WebDriverWait(driver, waitTime)).until(ExpectedConditions.elementToBeClickable(By.name(identity.Identifier)));
               break;
               case "tagName":
               element = (new WebDriverWait(driver, waitTime)).until(ExpectedConditions.elementToBeClickable(By.tagName(identity.Identifier)));
               break;
               case "xpath":
               element = (new WebDriverWait(driver, waitTime)).until(ExpectedConditions.elementToBeClickable(By.xpath(identity.Identifier)));
               break;
        }
        return element;
 }
 
 public WebElement WaitForElementToBeVisible(Identity identity, Integer waitTime) {
    	WebElement element=null;
    	switch(identity.IdentifierType){
    		case "id":
    		element = (new WebDriverWait(driver, waitTime)).until(ExpectedConditions.visibilityOfElementLocated(By.id(identity.Identifier)));
    		break;
    		case "className":
    		element = (new WebDriverWait(driver, waitTime)).until(ExpectedConditions.visibilityOfElementLocated(By.className(identity.Identifier)));
    		break;
    		case "cssSelector":
    		element = (new WebDriverWait(driver, waitTime)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(identity.Identifier)));
    		break;
    		case "linkText":
    		element = (new WebDriverWait(driver, waitTime)).until(ExpectedConditions.visibilityOfElementLocated(By.linkText(identity.Identifier)));
    		break;
    		case "partialLinkText":
    		element = (new WebDriverWait(driver, waitTime)).until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(identity.Identifier)));
    		break;
    		case "name":
    		element = (new WebDriverWait(driver, waitTime)).until(ExpectedConditions.visibilityOfElementLocated(By.name(identity.Identifier)));
    		break;
    		case "tagName":
    		element = (new WebDriverWait(driver, waitTime)).until(ExpectedConditions.visibilityOfElementLocated(By.tagName(identity.Identifier)));
    		break;
    		case "xpath":
    		element = (new WebDriverWait(driver, waitTime)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(identity.Identifier)));
    		break;
    	}
    	return element;
}


public Boolean WaitForElementToBeInVisible(Identity identity, Integer waitTime) {
		Boolean e=null;
    	switch(identity.IdentifierType){
    		case "id":
    		e = (new WebDriverWait(driver, waitTime)).until(ExpectedConditions.invisibilityOfElementLocated(By.id(identity.Identifier)));
    		break;
    		case "className":
    		e = (new WebDriverWait(driver, waitTime)).until(ExpectedConditions.invisibilityOfElementLocated(By.className(identity.Identifier)));
    		break;
    		case "cssSelector":
    		e = (new WebDriverWait(driver, waitTime)).until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(identity.Identifier)));
    		break;
    		case "linkText":
    		e = (new WebDriverWait(driver, waitTime)).until(ExpectedConditions.invisibilityOfElementLocated(By.linkText(identity.Identifier)));
    		break;
    		case "partialLinkText":
    		e = (new WebDriverWait(driver, waitTime)).until(ExpectedConditions.invisibilityOfElementLocated(By.partialLinkText(identity.Identifier)));
    		break;
    		case "name":
    		e = (new WebDriverWait(driver, waitTime)).until(ExpectedConditions.invisibilityOfElementLocated(By.name(identity.Identifier)));
    		break;
    		case "tagName":
    		e = (new WebDriverWait(driver, waitTime)).until(ExpectedConditions.invisibilityOfElementLocated(By.tagName(identity.Identifier)));
    		break;
    		case "xpath":
    		e = (new WebDriverWait(driver, waitTime)).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(identity.Identifier)));
    		break;
    	}
    	return e;
}

	public static String truncateMessage(String message) {    
    	String msg = message;
    	try{
    		if(msg != null && msg != ""){	    			
    			if(msg.length() <=100){	    				
    				msg  = msg;
    			} else {
    				msg = msg.substring(0,100)+"...";	    				    				
    			}
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
		return msg;
    }

}
