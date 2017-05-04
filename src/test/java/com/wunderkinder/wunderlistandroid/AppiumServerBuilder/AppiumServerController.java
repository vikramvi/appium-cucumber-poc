package com.wunderkinder.wunderlistandroid.AppiumServerBuilder;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;


import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class AppiumServerController extends AppiumDriverController{

    //public static String executionOS = "IOS"; //System.getProperty("platform"); ??How to read from pom.xml 

    public static AppiumDriverLocalService service;
        
    //https://github.com/appium/appium/issues/7866#issuecomment-279682304
    //https://github.com/NativeScript/functional-tests-core/blob/master/src/main/java/functional/tests/core/appium/Server.java
    //https://github.com/NativeScript/functional-tests-core/blob/26dea54c07bea4b077b1ab2abbef4058fbdca2fd/src/main/java/functional/tests/core/utils/OSUtils.java
    //https://github.com/tobecrazy/appiumDemo/blob/cbc48d1c2eac925c084828a0155d9e0f0223e9af/src/main/java/com/dbyl/appiumServer/AppiumServerUtils.java
    public static void startAppiumServer(){
	stopAlreadyRunningAppium();
        //LOGGER_BASE.info("Init appium server ...");

        // On Windows, when you force stop test run in the middle of execution,
        // test log file is locked by node.exe, so ... kill it!
        // Notes: This does not allow parallel test execution on Windows !!!
       // if (this.settings.os == OSType.Windows) {
         //   OSUtils.stopProcess("node.exe");
        //}

        // Create Appium server log file
        //File logFile = this.createLogFile();

        // Get Appium executable path
        File appiumExecutable = getAppiumExecutable();

        // Init AppiumServiceBuilder
        AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder()
                //.withLogFile(logFile)
                //.usingAnyFreePort()
        	.usingPort(Integer.parseInt("4723"))
                .withAppiumJS(appiumExecutable);

        // Set iOS specific Appium server arguments
        //if (this.settings.platform == PlatformType.iOS) {
           // serviceBuilder.withStartUpTimeOut(this.settings.deviceBootTimeout, TimeUnit.SECONDS);
        //}

        // Set Appium log level
        //if (this.settings.appiumLogLevel != null) {
          //  serviceBuilder.withArgument(GeneralServerFlag.LOG_LEVEL, this.settings.appiumLogLevel);
        //}

        // Start Appium server
        service = AppiumDriverLocalService.buildService(serviceBuilder);
        //LOGGER_BASE.info("Starting Appium server...");
        service.start();

        // Verify Appium server started
        if (service == null || !service.isRunning()) {
            String error = "Appium server not running!";
            //LOGGER_BASE.fatal(error);
            //throw new RuntimeException(error);
        } else {
            //LOGGER_BASE.info("Appium Server is up and running!");
        }
    }
    
    
    public static void stopAppiumServer(){
        if (service != null) {
            try {
                service.stop();
                //LOGGER_BASE.info("Appium server stopped.");
            }catch (Exception e){
        	e.printStackTrace();
                //LOGGER_BASE.fatal("Failed to fullStop appium server!");
            }
        } else {
           // LOGGER_BASE.info("Appium server already stopped.");
        }
    }
    
    private static File getAppiumExecutable(){
        // Find Appium path.
        String appiumPath;
        appiumPath = runProcess(true, 30, "which appium").trim();
       
        // Check if exists
        File appiumExecutable = new File(appiumPath);
        if (!appiumExecutable.exists()) {
            String error = "Appium does not exist at: " + appiumPath;
            System.out.println(error);
           // LOGGER_BASE.fatal(error);
            //throw new AppiumException(error);
        } else {
            System.out.println("Appium Executable: " + appiumPath);
            //LOGGER_BASE.info("Appium Executable: " + appiumPath);
        }

        // Return Appium executable file.
        return appiumExecutable;
    }
    
    
    public static final String[] OS_LINUX_RUNTIME = {"/bin/bash", "-l", "-c"};
    
    public static String[] concat(String[] first, String[] second) {
        String[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
    
    public static String runProcess(boolean waitFor, int timeOut, String... command) {
        String[] allCommand = null;

        String finalCommand = "";
        for (String s : command) {
            finalCommand = finalCommand + s;
        }

        try {
           /* if (Settings.os == OSType.Windows) {
                allCommand = concat(WIN_RUNTIME, command);
            } else {
                allCommand = concat(OS_LINUX_RUNTIME, command);
            }*/
            
            allCommand = concat(OS_LINUX_RUNTIME, command);
            ProcessBuilder pb = new ProcessBuilder(allCommand);
            Process p = pb.start();

            if (waitFor) {
                StringBuffer output = new StringBuffer();

                // Note: No idea why reader should be before p.waitFor(),
                //       but when it is after p.waitFor() execution of
                //       some adb command freeze on Windows
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(p.getInputStream()));

                String line = "";
                while ((line = reader.readLine()) != null) {
                    output.append(line + "\n");
                }

                p.waitFor(timeOut, TimeUnit.SECONDS);

                //LOGGER_BASE.debug("Execute command: " + finalCommand);
                //LOGGER_BASE.trace("Result: " + output.toString());

                return output.toString();
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    //http://stackoverflow.com/questions/7289398/is-there-a-way-to-find-a-port-being-used-by-process-given-its-process-id-usin
    //http://stackoverflow.com/questions/434718/sockets-discover-port-availability-using-java
    public static void stopAlreadyRunningAppium(){
	 try{
        	     String output = null;
        	     String PID  = null;
        	     Process process = null;
        	     BufferedReader stdInput = null;
        	     
        	     //Stop appium
        	     process = Runtime.getRuntime().exec("pgrep -f  appium");
        	  		 
        	     stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
        	     
        	     while ((output = stdInput.readLine()) != null) {
        	                System.out.println("Appium Server Found   " + output);
        	                PID = output;
        	                Runtime.getRuntime().exec("kill -9 " + PID );
        	     }
	 }catch(Exception e){
	     e.printStackTrace();
	 }
    }
       
}
