package com.hcl.taf;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;
import com.hcl.atf.taf.ConsoleWriter;

public class TearDownTestNG {
	
	@AfterSuite
	public void tearDown(){
		try {
			ConsoleWriter.echo("TEARDOWN started");

			Main.getMain().tearDown();
			
			//Run custom tea down activity
			boolean customSetupActivity = customTeardownActivity();
			if (customSetupActivity) {
				ConsoleWriter.echo("Custom tear down activity completed");
			} else {
				ConsoleWriter.echo("Custom tear down activity failed");
				return;
			}
			ConsoleWriter.echo("TEARDOWN completed");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test(){
		
	}
	
	private boolean customTeardownActivity() {
		ConsoleWriter.echo("Custom tear down activity started");
		try{			
			ConsoleWriter.echo("No custom teardown action.");
			Process pro = Runtime.getRuntime().exec("taskkill /F /IM winowrd.exe");
			pro.waitFor();
			//TODO : Add your custom teardown logic below, if needed
		} catch(Exception e) {
			ConsoleWriter.echo("Custom tear down activity failed");
			e.printStackTrace();
			return false;
		}
		//Do whatever is needed for setup process
		return true;
	}

}