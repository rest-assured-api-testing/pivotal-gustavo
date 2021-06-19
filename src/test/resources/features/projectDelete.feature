Feature: Requests for projects endpoint to delete a project

  @CreateProject
  Scenario: Delete a Project
    Given I build "DELETE" request with ID of project
    When I execute "projects/{projectId}" request to be delete a project
    Then the response status code should equal "203"