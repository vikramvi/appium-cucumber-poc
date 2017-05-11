package com.wunderkinder.wunderlistandroid.cucumber.steps.scenarios;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;

import static com.wunderkinder.wunderlistandroid.AppiumServerBuilder.AppiumServerController.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.wunderkinder.wunderlistandroid.cucumber.pages.*;

public class LandingPageScenariosSteps {

    protected WLLandingPage HomePage;
    protected WLSignInPage SignInPage;
    
    String validEmailId = "vikram@test.com";
    
    @Before
    //Why to pass Scenario object ??
    public void setUp(Scenario scenario) throws Exception{	
	HomePage =   new WLLandingPage(getDriver());
	SignInPage = new WLSignInPage(getDriver());
    }
    
  //------- Given 
    
    @Given("^User is on Sign In Page$")
    public void gotoSignInPage(){
	assertThat( HomePage.gotoSignInScreen()).isTrue();
    }
        
    @Given("^User taps Forgot your password link$")
    public void clickForgotYourPasswordLink(){
	assertThat( SignInPage.clickWLForgotYourPasswordLink() ).isTrue();
    }
    
  //------- When
    
    @When("^User enters valid email id$")
    public void enterEmailId(){
	SignInPage.enterEmailId(validEmailId);
    }
    
    @When("^User keeps password field empty$")
    public void enterPassword(){
	SignInPage.enterPassword("");
    }
        
    @When("^User completes reset password actions$")
    public void doResetPasswordActions(){
	assertThat( SignInPage.completeResetPasswordFlow(validEmailId) ).isTrue();
    }
   //---------- Then
    
    @Then("^Sign In button remains in disabled state$")
    public void verifySignInButtonStatus(){
	assertThat( SignInPage.isSignInButtonEnabled() ).isFalse();
    }
    
    @Then("^User goes back to Sign In Page$")
    public void verifyUserIsTakenBackToLoginScreen(){
	assertThat(SignInPage.isSignInScreen() ).isTrue();
    }
        
}
