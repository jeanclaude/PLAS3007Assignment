Feature: Testing of the Evernote TrashCan feature

  Scenario: Create notes, delete them, check that they are in the trashcan and then empty the trash
    Given I navigate to "https://www.evernote.com/Login.action"
    And I am logged into Evernote
    When I create the following notes:
      | title | description |
      | In fourteen hundred and ninety two | Columbus sailed the ocean blue |
      | Richard of York | Gave battle in vain |
      | NaMgAlSiPSClAr | Helps you remember the first line of the periodic table |
    And delete all notes
    And I go to the TrashCan
    Then I can see the following notes:
      | In fourteen hundred and ninety two |
      | Richard of York |
      | NaMgAlSiPSClAr |
    When I empty the TrashCan
    Then The TrashCan is empty

  @TrashCan @DeleteNotesWhenDone
  Scenario: Testing the restore facility of the trash can
    Given I navigate to "https://www.evernote.com/Login.action"
    And I am logged into Evernote
    When I create a note with title "A note to delete" and description "Goodbye"
    And I delete the note with title "A note to delete"
    And I go to the TrashCan and restore the note with title "A note to delete"
    Then I can see the note with title "A note to delete" in the notes list
