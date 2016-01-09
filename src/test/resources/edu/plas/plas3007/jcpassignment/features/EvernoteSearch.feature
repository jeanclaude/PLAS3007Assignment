Feature: Testing the Search facilities of Evernote


  Scenario: Create 3 notes, search by note title and ensure that correct note is shown
    Given I navigate to "https://www.evernote.com/Login.action"
    And I am logged into Evernote
    When I create a note with title "Once upon a time" and description "They lived happily ever after"
    And I create a note with title "Happy New Year to all" and description "All the best for 2016"
    And I create a note with title "Gherkin and Cucumber are cool" and description "And so is Selenium"
    And I search for "New Year"
    Then I can see the note with title "Happy New Year to all"

  @SearchNotes @DeleteNotesWhenDone
  Scenario: Create 3 notes, search by note title and ensure that correct note is shown
    Given I navigate to "https://www.evernote.com/Login.action"
    And I am logged into Evernote
    When I create a note with title "Once upon a time" and description "They lived happily ever after"
    And I create a note with title "Happy New Year to all" and description "All the best for 2016"
    And I create a note with title "Gherkin and Cucumber are cool" and description "And so is Selenium"
    And I search for "2016"
    Then I can see the note with title "Happy New Year to all"

