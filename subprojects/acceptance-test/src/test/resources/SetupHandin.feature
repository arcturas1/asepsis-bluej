Feature: Setup hand-in
  In order to hand-in to the correct assignment
  As a user
  I want be able to set and change the assignment to which I hand in a BlueJ project

  Background:
    Given I create a new project

  Scenario: Project is not setup initially
    Then ASEPSiS should say "Not setup"

    @wip
  Scenario: Accepting setup sets up the project
    When I ask ASEPSiS to setup the project with a course and an assignment
    And I accept the setup
    Then ASEPSiS should say "Setup"

  Scenario: Canceling setup doesn't setup the project
    When I ask ASEPSiS to setup the project with a course and an assignment
    But I cancel the setup
    Then ASEPSiS should say "Not setup"

  Scenario: Cannot setup with no project
    When I close the project
    Then I should not be able to setup the project

  Scenario: Setup status is local to project
    When I ask ASEPSiS to setup the project with a course and an assignment
    And I accept the setup
    And I create another new project
    Then ASEPSiS should say "Not setup"