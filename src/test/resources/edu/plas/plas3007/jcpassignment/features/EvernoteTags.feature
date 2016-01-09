Feature: Testing the Tags feature of Evernote

  @EvernoteTags @DeleteNotesWhenDone @DeleteTestTag
  Scenario: Create notes with tags and make sure they are listed correctly in the tag view
    Given I navigate to "https://www.evernote.com/Login.action"
    And I am logged into Evernote
    When I create the following notes:
      | title | description |
      | In fourteen hundred and ninety two | Columbus sailed the ocean blue |
      | Richard of York | Gave battle in vain |
      | NaMgAlSiPSClAr | Helps you remember the first line of the periodic table |
    And Tag the following notes with the test tag:
      | In fourteen hundred and ninety two |
      | NaMgAlSiPSClAr |
    Then The test tag view contains only the following notes:
      | In fourteen hundred and ninety two |
      | NaMgAlSiPSClAr |
