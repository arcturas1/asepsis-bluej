Feature: Setup hand-in
  Scenario: Creating new project
    When I create a new project
    Then the ASEPSiS sidebar should say "Not setup"