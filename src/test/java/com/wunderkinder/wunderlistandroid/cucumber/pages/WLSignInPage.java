package com.wunderkinder.wunderlistandroid.cucumber.pages;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.AndroidFindBy;

import java.util.concurrent.TimeUnit;

import com.wunderkinder.wunderlistandroid.utils.Helpers;

public class WLSignInPage extends Helpers {
    //-------------------------------------------------------------------------------------------
    //Mobile Elements Identifiers
    //-------------------------------------------------------------------------------------------
    
    @AndroidFindBy(id="com.wunderkinder.wunderlistandroid:id/login_email_edittext")
    MobileElement WLEmailField;
    
    @AndroidFindBy(id="com.wunderkinder.wunderlistandroid:id/login_password_edittext")
    MobileElement WLPasswordField;
    
    @AndroidFindBy(id="com.wunderkinder.wunderlistandroid:id/login_button")
    MobileElement WLSignInButton;
    
    
    //-------------------------------------------------------------------------------------------
    //Methods to do actions in this Page Object 
    //-------------------------------------------------------------------------------------------
    protected final AppiumDriver driver;
    
    public WLSignInPage(AppiumDriver driver){
   	this.driver = driver;
   	PageFactory.initElements(new AppiumFieldDecorator(driver, 1, TimeUnit.SECONDS), this);
     }
    
    public void enterEmailId(String validEmail){
	WLEmailField.sendKeys(validEmail);
    }
    
    public void enterPassword(String validPassword){
	WLPasswordField.sendKeys(validPassword);
    }
    
    public boolean isSignInButtonEnabled(){
    	if(WLSignInButton.isEnabled()){
    	    return true;
    	}else{
    	    return false;
    	}
    }
    
}
