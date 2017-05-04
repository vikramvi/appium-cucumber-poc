package com.wunderkinder.wunderlistandroid.utils;

import static com.wunderkinder.wunderlistandroid.AppiumServerBuilder.AppiumServerController.*;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Helpers {

    //Only works when pre declared element is passed and not when xpath or other identifier is used at run time
    public boolean isElementPresent(WebElement elementName, int timeout){
	//https://github.com/appium/java-client/issues/617
	/*try{
	        WebDriverWait wait = new WebDriverWait(getDriver(), timeout);
	        wait.until(ExpectedConditions.visibilityOf(elementName));
	        return true;
	}catch(Exception e){
	    return false;
	}*/
	try{
	       int counter = 0;
	       while(counter < timeout){
		   Thread.sleep(1000);
		   counter++;
        		       try{
                        		   if(elementName.isDisplayed()){ 
        			           //if(ExpectedConditions.visibilityOf(elementName) !=null ){
                        		       return true;
                        		   }else{
                        		       continue;
                        		   }
        		       }catch(Exception e){
        			   continue;
        		       }
	       }
	       System.out.println("ELEMENT NOT FOUND  -  " + elementName  + "  AFTER  " +  timeout  );
	       return false;
	}catch(Exception e){
	    return false;
	}
    }
    
}
