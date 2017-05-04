package com.wunderkinder.wunderlistandroid.AppiumServerBuilder;

import java.io.File;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class AppiumDriverController{
    public static AppiumDriver<?> appiumDriverControllerDriver;
        
    //https://github.com/NativeScript/functional-tests-core/blob/master/src/main/java/functional/tests/core/appium/Capabilities.java
    //https://github.com/NativeScript/functional-tests-core/blob/master/src/main/java/functional/tests/core/appium/Client.java
    public static void createDriver(String executionOS){
	try{
	        DesiredCapabilities capabilities;
	    
        	if (appiumDriverControllerDriver != null) {
                    return;
                }
                switch(executionOS){
                    case "ANDROID":
                        File classpathRoot = new File(System.getProperty("user.dir"));
                        File appDir = new File(classpathRoot, "");
                        File app = new File (appDir, "com.wunderkinder.wunderlistandroid.apk");
                        capabilities = new DesiredCapabilities();
                        //capabilities.setCapability("avd", "Android7");
                        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
                        //capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,"5.1");
                        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "placeholder string");
                        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60*5);
                        capabilities.setCapability("app", app.getAbsolutePath());
                        capabilities.setCapability("fullReset", false);
                        //capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.jayway.contacts");
                        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.wunderkinder.wunderlistandroid.activity.WLStartViewFragmentActivity");
                        appiumDriverControllerDriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
                        break;
                    case "IOS":
                        classpathRoot = new File(System.getProperty("user.dir"));
                        appDir = new File(classpathRoot, "");
                        app = new File(appDir, "Crew.app");
                        capabilities = new DesiredCapabilities();
                        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "ios");
                        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,"10.2");
                        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone SE");                
                        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
                        capabilities.setCapability("app", app.getAbsolutePath());
                        //capabilities.setCapability("noReset", true); //Investigate more on this
                        appiumDriverControllerDriver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
                        break;
                    case "Chrome":
                	capabilities = new DesiredCapabilities();
                	capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
                	capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "placeholder string");
                        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
                        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60*3);
                        appiumDriverControllerDriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
                        //When to use WebDriver / RemoteWebDriver object
                        appiumDriverControllerDriver.get("http://www.facebook.com");
                }
//        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);  //?? FIX ME ( Investigate & Fix )
	}catch(Exception e){
	    e.getMessage();
	}

    }
    
    public static void stopDriver(){
	if (appiumDriverControllerDriver != null){
            //http://stackoverflow.com/questions/37178914/how-to-close-kill-an-app-on-android-device-using-appium
	    appiumDriverControllerDriver.quit();
	    appiumDriverControllerDriver = null;
        }
    }
    
    public static AppiumDriver<?> getDriver(){
	return appiumDriverControllerDriver;
    }
    
}
