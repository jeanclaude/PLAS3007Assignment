@Login
Feature: Testing Evernote Login

  @Active
  Scenario: Test getting to login page
    Given I navigate to "https://www.evernote.com/Login.action"

  Scenario: Logging into Evernote with correct credentials
    Given I navigate to "https://www.evernote.com/Login.action"
    When I log into Evernote with correct credentials
    Then The user is logged in

  Scenario: Logging into Evernote with no credentials
    Given I navigate to "https://www.evernote.com/Login.action"
    When I log into Evernote with no credentials
    Then An error indicating "No Credentials" is shown

  Scenario: Logging into Evernote with incorrect credentials
    Given I navigate to "https://www.evernote.com/Login.action"
    When I log into Evernote with incorrect credentials
    Then An error indicating "Incorrect Credentials" is shown

