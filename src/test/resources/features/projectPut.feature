Feature: Requests for project endpoint to change its name

  @CreateProject
  Scenario: Change name of a Project
    Given I build "PUT" request to change name to "Name is changed"
    When I execute "projects/{projectId}" request to be put a new name in a project
    Then The response status code should be successful "ok" and project has new name