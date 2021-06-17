import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Project;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProjectCreateAndDelete {
    private static Project projectToDelete =new Project();

    @Test
    public void createProject_nameIsEmpty_400() throws JsonProcessingException {
        Project projectCreate = new Project();
        projectCreate.setName("");

        ApiRequest apiRequest = new ApiRequestBuilder()
                .baseUri(Parameters.URL_BASE)
                .headers(Parameters.KEY_VALUE, Parameters.VALUE_KEY)
                .endpoint("/projects")
                .method(ApiMethod.POST)
                .body(new ObjectMapper().writeValueAsString(projectCreate))
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void deleteProject_removeByOtherUser_403(){
        ApiRequest apiRequest = new ApiRequestBuilder()
                .baseUri(Parameters.URL_BASE)
                .headers(Parameters.KEY_VALUE, Parameters.VALUE_KEY)
                .endpoint("/projects/{projectId}")
                .pathParams("projectId", "441794316")
                .method(ApiMethod.DELETE)
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 403);
    }

    @Test(dependsOnMethods = {"deleteProject_successful_204"})
    public void deleteProject_accessToWrongProject_403(){
        ApiRequest apiRequest = new ApiRequestBuilder()
                .baseUri(Parameters.URL_BASE)
                .headers(Parameters.KEY_VALUE, Parameters.VALUE_KEY)
                .endpoint("/projects/{projectId}")
                .pathParams("projectId", projectToDelete.getId().toString())
                .method(ApiMethod.DELETE)
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 403);
    }

    @Test(dependsOnMethods = {"createProject_successful_204"})
    public void createProject_nameWithSameNameTheOther_400() throws JsonProcessingException {
        Project projectCreate = new Project();
        projectCreate.setName("Project to test");

        ApiRequest apiRequest = new ApiRequestBuilder()
                .baseUri(Parameters.URL_BASE)
                .headers(Parameters.KEY_VALUE, Parameters.VALUE_KEY)
                .endpoint("/projects")
                .method(ApiMethod.POST)
                .body(new ObjectMapper().writeValueAsString(projectCreate))
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createProject_successful_204() throws JsonProcessingException {
        Project projectCreate = new Project();
        projectCreate.setName("Project to test");

        ApiRequest apiRequest = new ApiRequestBuilder()
                .baseUri(Parameters.URL_BASE)
                .headers(Parameters.KEY_VALUE, Parameters.VALUE_KEY)
                .endpoint("/projects")
                .method(ApiMethod.POST)
                .body(new ObjectMapper().writeValueAsString(projectCreate))
                .build();
        projectToDelete = ApiManager.execute(apiRequest).getBody(Project.class);
        Assert.assertEquals(ApiManager.getStatusResponse(), 200);
    }

    @Test(dependsOnMethods = {"createProject_successful_204","createProject_nameWithSameNameTheOther_400"})
    public void deleteProject_successful_204(){
        ApiRequest apiRequest = new ApiRequestBuilder()
                .baseUri(Parameters.URL_BASE)
                .headers(Parameters.KEY_VALUE, Parameters.VALUE_KEY)
                .endpoint("/projects/{projectId}")
                .pathParams("projectId", projectToDelete.getId().toString())
                .method(ApiMethod.DELETE)
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
    }
}
