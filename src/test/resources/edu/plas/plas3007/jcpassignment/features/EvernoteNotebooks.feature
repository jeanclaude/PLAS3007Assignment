Feature: Testing the creation and use of notebooks in Evernote


  @NotebookTests @DeleteNotesWhenDone @DeleteAllNotebooksWhenDone
  Scenario: Create a notebook, put a note in it and ensure that the created note is visible in that notebook and not in the default notebook
    Given I navigate to "https://www.evernote.com/Login.action"
    And I am logged into Evernote
    When I create a notebook called "Assignment notebook"
    And I create a note in notebook "Assignment notebook" with title "A man a plan a canal Panama" and description "A very long palindrome"
    Then I can see the note in notebook "Assignment notebook" with title "A man a plan a canal Panama"
    And I cannot see the note with title "A man a plan a canal Panama" in the default notebook
