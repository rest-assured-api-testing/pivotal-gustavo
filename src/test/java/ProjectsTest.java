import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Project;
import org.testng.Assert;
import org.testng.annotations.*;

public class ProjectsTest {
    private static Project project = new Project();

    @BeforeClass
    public void setupBasicRequirement() throws JsonProcessingException {
        Project projectCreate = new Project();
        projectCreate.setName("Project to test");

        ApiRequest apiRequest = new ApiRequestBuilder()
                .baseUri(Parameters.URL_BASE)
                .headers(Parameters.KEY_VALUE, Parameters.VALUE_KEY)
                .endpoint("/projects")
                .method(ApiMethod.POST)
                .body(new ObjectMapper().writeValueAsString(projectCreate))
                .build();
        project = ApiManager.execute(apiRequest).getBody(Project.class);
    }

    @AfterClass
    public void cleanRepository() {
        ApiRequest apiRequest = new ApiRequestBuilder()
                .baseUri(Parameters.URL_BASE)
                .headers(Parameters.KEY_VALUE, Parameters.VALUE_KEY)
                .endpoint("/projects/{projectId}")
                .pathParams("projectId", project.getId().toString())
                .method(ApiMethod.DELETE).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
    }

    @Test
    public void getNameProjectsCreated() {
        String actual = project.getName();
        String expected = "Project to test";
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void createDefault_notPublic() {
        boolean actual = project.getPublicc();
        boolean expected = false;
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void verifySchemaInProject() {
        ApiRequest apiRequest = new ApiRequestBuilder()
                .baseUri(Parameters.URL_BASE)
                .headers(Parameters.KEY_VALUE, Parameters.VALUE_KEY)
                .endpoint("/projects/{projectId}")
                .pathParams("projectId", project.getId().toString())
                .method(ApiMethod.GET).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        apiResponse.validateBodySchema("schemas/project.json");
    }

    @Test
    public void getAProject_status_200() {
        ApiRequest apiRequest = new ApiRequestBuilder()
                .baseUri(Parameters.URL_BASE)
                .headers(Parameters.KEY_VALUE, Parameters.VALUE_KEY)
                .endpoint("/projects/{projectId}")
                .pathParams("projectId", project.getId().toString())
                .method(ApiMethod.GET).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test
    public void getAllProjects() {
        ApiRequest apiRequest2 = new ApiRequestBuilder()
                .baseUri(Parameters.URL_BASE)
                .headers(Parameters.KEY_VALUE, Parameters.VALUE_KEY)
                .endpoint("/projects")
                .method(ApiMethod.GET).build();
        ApiResponse apiResponse2 = ApiManager.execute(apiRequest2);
        Assert.assertEquals(apiResponse2.getStatusCode(), 200);
    }
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
    public void deleteProject_removeByOtherUser_404(){
        ApiRequest apiRequest = new ApiRequestBuilder()
                .baseUri(Parameters.URL_BASE)
                .headers(Parameters.KEY_VALUE, Parameters.VALUE_KEY)
                .endpoint("/projects/{projectId}")
                .pathParams("projectId", "441794316")
                .method(ApiMethod.DELETE)
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 404);
    }
}
