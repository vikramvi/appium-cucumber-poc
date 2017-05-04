package com.wunderkinder.wunderlistandroid.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

import org.junit.runner.RunWith;

import com.wunderkinder.wunderlistandroid.AppiumServerBuilder.AppiumServerController;

import static com.wunderkinder.wunderlistandroid.AppiumServerBuilder.AppiumDriverController.createDriver;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;

@RunWith(Cucumber.class)
@CucumberOptions(features={"src/test/resources/WunderlistAndroid.feature"}, strict = false, format = { "pretty","json:target/cucumber.json" }, tags = { "~@ignore" })
public class WLSignIn {
    @BeforeClass
    public static void launchAppiumServer() throws MalformedURLException {        
	AppiumServerController.startAppiumServer();
	createDriver("ANDROID");
    }

    @AfterClass
    public static void killAppiumServer() throws IOException {
	AppiumServerController.stopDriver();
	AppiumServerController.stopAppiumServer();
    }
    
}
