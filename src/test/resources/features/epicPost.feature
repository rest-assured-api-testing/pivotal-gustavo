
Feature: Requests for epic endpoint to create a epic

  @CreateProject
  Scenario: Create Epic
    Given I build "POST" request with id of project to create epic with name "Test-Epic-to-test"
    When I execute "projects/{projectId}/epics/" request to create a epic in a project
    Then the response status code should be successful in order to be "200"