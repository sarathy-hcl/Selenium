package com.hcl.taf;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.hcl.atf.taf.ConsoleWriter;

public class SetUpTestNG {
	
	@BeforeSuite
	public void setUp() {
		try {
			ConsoleWriter.echo("SETUP started");
			
			//Run custom setup activity
			boolean customSetupActivity = customSetupActivity();
			if (customSetupActivity) {
				ConsoleWriter.echo("Custom setup activity completed");
			} else {
				ConsoleWriter.echo("Custom setup activity failed");
				Main.getMain().tearDown();
				return;
			}
			ConsoleWriter.echo("SETUP completed");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test(){
		
	}
	
	private boolean customSetupActivity() {	
		ConsoleWriter.echo("Custom setup activity started");
		try{
			ConsoleWriter.echo("No custom setup action");
			//TODO : Add your custom setup logic below, if needed			
		} catch(Exception e) {
			ConsoleWriter.echo("Custom setup activity failed");
			e.printStackTrace();
			return false;
		}
		return true;
	}

}