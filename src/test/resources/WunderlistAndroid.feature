Feature: Verify WL Sign In Page related user stories

Scenario: Verify Sign in button remains disabled state if only email id is entered
 Given User is on Sign In Page
 When User enters valid email id
 And   User keeps password field empty
 Then  Sign In button remains in disabled state

 Scenario: Verify user is shown reset password dialog after Password Reset button is tapped
 Given User is on Sign In Page
 And    User taps Forgot your password link
 When  User completes reset password actions
 Then   User goes back to Sign In Page