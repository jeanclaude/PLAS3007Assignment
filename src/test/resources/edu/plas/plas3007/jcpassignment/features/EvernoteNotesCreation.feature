
Feature: Testing the creation of new notes


#  Scenario: Testing delete notes
#    Given I navigate to "https://www.evernote.com/Login.action"
#    And I am logged into Evernote
#    Then delete all notes


  Scenario: Create a new note
    Given I navigate to "https://www.evernote.com/Login.action"
    And I am logged into Evernote
    When I create a new note
    Then I can see the new note


  Scenario: Create a new note and read it after logging out and back in
    Given I navigate to "https://www.evernote.com/Login.action"
    And I am logged into Evernote
    When I create a new note
    And I log out from Evernote
    And I navigate to "https://www.evernote.com/Login.action"
    And I log into Evernote with correct credentials
    Then I can see the new note

  @CreateNotes @DeleteNotesWhenDone
  Scenario: Create a new note, mark it as favourite and check that it is listed under shortcuts
    Given I navigate to "https://www.evernote.com/Login.action"
    And I am logged into Evernote
    When I create a new note
    And I mark it as favourite
    Then I can see the new note listed under shortcuts

  Scenario: Create a new note with a 3x3 table inside it