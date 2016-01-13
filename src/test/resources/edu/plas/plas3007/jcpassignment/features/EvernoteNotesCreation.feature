@AllEvernoteTests
Feature: Testing the creation of new notes


#  Scenario: Testing delete notes
#    Given I navigate to "https://www.evernote.com/Login.action"
#    And I am logged into Evernote
#    Then delete all notes

  @CreateNotes @DeleteNotesWhenDone
  Scenario: Create a new note
    Given I navigate to "https://www.evernote.com/Login.action"
    And I am logged into Evernote
    When I create a new note
    Then I can see the new note

  @CreateNotes @DeleteNotesWhenDone
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

  @CreateNotes @DeleteNotesWhenDone @SeleniumPresentation
  Scenario: Create a new note with a 3x3 table inside it
    Given I navigate to "https://www.evernote.com/Login.action"
    And I am logged into Evernote
    When I create a new note with a 3x3 table inside it
    Then I can see the new note
    And The selected note has a 3x3 table inside it

  @CreateNotes @DeleteNotesWhenDone
  Scenario: Create 3 notes and ensure that they are sorted correctly
    Given I navigate to "https://www.evernote.com/Login.action"
    And I am logged into Evernote
    When I create some test notes for sorting
    Then The notes are sorted correctly by "Date Created (oldest first)"
    And The notes are sorted correctly by "Date Created (newest first)"
    And The notes are sorted correctly by "Title (ascending)"
    And The notes are sorted correctly by "Title (descending)"



