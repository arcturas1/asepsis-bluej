Feature: Extension is loaded
  Scenario: The extension is loaded successfully
    Given the extension is placed in the extensions folder
    When I open the installed extensions window
    Then I should see the extension listed