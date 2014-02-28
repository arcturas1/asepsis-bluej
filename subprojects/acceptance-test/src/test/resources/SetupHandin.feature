Feature: Setup hand-in
  In order to hand-in to the correct assignment
  As a user
  I want be able to set and change the assignment to which I hand in a BlueJ project

  Background:
    Given I create a new project

  Scenario: Initial state
    Then the ASEPSiS sidebar should say "Not setup"

  Scenario: Accept setup
    When I ask ASEPSiS to setup the project with a course and an assignment
    And I accept the setup
    Then the ASEPSiS sidebar should say "Setup"

  Scenario: Cancel setup
    When I ask ASEPSiS to setup the project with a course and an assignment
    But I cancel the setup
    Then the ASEPSiS sidebar should say "Not setup"