Feature: Requests for story endpoint to create story in a project

  @CreateProject
  Scenario: Create a story in a Project
    Given I build "POST" request with ID of project with name "Test-Story"
    When I execute "projects/{projectId}/stories/" request to be create in a project
    Then The response status code should be successful "ok"