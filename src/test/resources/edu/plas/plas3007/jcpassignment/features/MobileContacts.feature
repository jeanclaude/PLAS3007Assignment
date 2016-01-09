
Feature: Test the Contacts book on an Android phone

  @Android
  Scenario: Create a new contact
    Given I log into the Contacts app
    When I create a new contact with name "My test contact", mobile number "35679123456", home number "21888888" and email address "helloworld@assignment.com"
    Then The displayed contact has name "My test contact", mobile number "35679123456", home number "21888888" and email address "helloworld@assignment.com"
    When I return from the contact to the address book
    Then There is a contact with name "My test contact"

  @Android
  Scenario: Search for contact
    Given I log into the Contacts app
    When I create a new contact with name "John Lennon", mobile number "35679799779", home number "21122112" and email address "johnlennon@beetles.com"
    And I return from the contact to the address book
    And I select the contact with name "John Lennon"
    And I change the name and mobile number of the selected contact to "Ringo Star" and "35679356356"
    Then The displayed contact has name "Ringo Star", mobile number "35679356356", home number "21122112" and email address "johnlennon@beetles.com"

  @Android
  Scenario: Favourite Contact
    Given I log into the Contacts app
    When I create a new contact with name "Lewis Hamilton", mobile number "35679444444", home number "21444444" and email address "lewis@worldchampion.com"
    And I flag the displayed contact as favourite
    And I return from the contact to the address book
    And I go to the favourites view
    Then There is a contact with name "Lewis Hamilton"


# Below scenario was created for test purposes only to help check that deletion of contacts works correctly
#  @Android
#  Scenario: Delete all contacts
#    Given I log into the Contacts app
#    When I delete all contacts


