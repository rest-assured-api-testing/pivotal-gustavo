import api.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Project;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ProjectCreateAndDeleteTest {
    private Project projectToDelete = new Project();
    private Project projectToDeleteRequest = new Project();
    private Project project = new Project();

    @BeforeMethod(onlyForGroups = "projectWithSameName")
    public void createProjectReference() throws JsonProcessingException {
        Project projectCreate = new Project();
        projectCreate.setName("Project to test");

        ApiRequest apiRequest = new ApiRequestBuilder()
                .baseUri(ParametersDefault.URL_BASE)
                .headers(ParametersDefault.KEY_VALUE, ParametersDefault.VALUE_KEY)
                .endpoint("/projects")
                .method(ApiMethod.POST)
                .body(new ObjectMapper().writeValueAsString(projectCreate))
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        project = apiResponse.getBody(Project.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @BeforeMethod(onlyForGroups = "deleteRequest")
    public void createProjectToDelete() throws JsonProcessingException {
        Project projectCreate = new Project();
        projectCreate.setName("Project_create_in_test_delete");

        ApiRequest apiRequest = new ApiRequestBuilder()
                .baseUri(ParametersDefault.URL_BASE)
                .headers(ParametersDefault.KEY_VALUE, ParametersDefault.VALUE_KEY)
                .endpoint("/projects")
                .method(ApiMethod.POST)
                .body(new ObjectMapper().writeValueAsString(projectCreate))
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        projectToDeleteRequest = apiResponse.getBody(Project.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @AfterMethod(onlyForGroups = "postRequest")
    public void deleteProjectCreated() {
        ApiRequest apiRequest = new ApiRequestBuilder()
                .baseUri(ParametersDefault.URL_BASE)
                .headers(ParametersDefault.KEY_VALUE, ParametersDefault.VALUE_KEY)
                .endpoint("/projects/{projectId}")
                .pathParams("projectId", projectToDelete.getId().toString())
                .method(ApiMethod.DELETE)
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
    }

    @AfterMethod(onlyForGroups = "projectWithSameName")
    public void deleteProjectReference() {
        ApiRequest apiRequest = new ApiRequestBuilder()
                .baseUri(ParametersDefault.URL_BASE)
                .headers(ParametersDefault.KEY_VALUE, ParametersDefault.VALUE_KEY)
                .endpoint("/projects/{projectId}")
                .pathParams("projectId", project.getId().toString())
                .method(ApiMethod.DELETE)
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
    }

    @Test(groups = "projectWithSameName")
    public void createProject_nameWithSameNameTheOther_400() throws JsonProcessingException {
        Project projectCreate = new Project();
        projectCreate.setName(project.getName());

        ApiRequest apiRequest = new ApiRequestBuilder()
                .baseUri(ParametersDefault.URL_BASE)
                .headers(ParametersDefault.KEY_VALUE, ParametersDefault.VALUE_KEY)
                .endpoint("/projects")
                .method(ApiMethod.POST)
                .body(new ObjectMapper().writeValueAsString(projectCreate))
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createProject_nameIsEmpty_400() throws JsonProcessingException {
        Project projectCreate = new Project();
        projectCreate.setName("");
        ApiRequest apiRequest = new ApiRequestBuilder()
                .baseUri(ParametersDefault.URL_BASE)
                .headers(ParametersDefault.KEY_VALUE, ParametersDefault.VALUE_KEY)
                .endpoint("/projects")
                .method(ApiMethod.POST)
                .body(new ObjectMapper().writeValueAsString(projectCreate))
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void deleteProject_removeByOtherUser_404() {
        ApiRequest apiRequest = new ApiRequestBuilder()
                .baseUri(ParametersDefault.URL_BASE)
                .headers(ParametersDefault.KEY_VALUE, ParametersDefault.VALUE_KEY)
                .endpoint("/projects/{projectId}")
                .pathParams("projectId", "441794316")
                .method(ApiMethod.DELETE)
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 404);
    }

    @Test(groups = "postRequest")
    public void createProject_successful_200() throws JsonProcessingException {
        Project projectCreate = new Project();
        projectCreate.setName("Project_create_in_test");

        ApiRequest apiRequest = new ApiRequestBuilder()
                .baseUri(ParametersDefault.URL_BASE)
                .headers(ParametersDefault.KEY_VALUE, ParametersDefault.VALUE_KEY)
                .endpoint("/projects")
                .method(ApiMethod.POST)
                .body(new ObjectMapper().writeValueAsString(projectCreate))
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        projectToDelete = apiResponse.getBody(Project.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test(groups = "deleteRequest")
    public void deleteProject_successful_204() {
        ApiRequest apiRequest = new ApiRequestBuilder()
                .baseUri(ParametersDefault.URL_BASE)
                .headers(ParametersDefault.KEY_VALUE, ParametersDefault.VALUE_KEY)
                .endpoint("/projects/{projectId}")
                .pathParams("projectId", projectToDeleteRequest.getId().toString())
                .method(ApiMethod.DELETE)
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
    }
}
