import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Project;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProjectsTest {

    @Test
    public void getAllProjects() {
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.addHeaders("X-TrackerToken", "1d24b2ee47d04c09615c6811a19fba0a");
        apiRequest.setBaseUri("https://www.pivotaltracker.com/services/v5");
        apiRequest.setEndpoint("/projects");
        apiRequest.setMethod(ApiMethod.GET);

        Response response = ApiManager.execute(apiRequest);
        response.then().assertThat().statusCode(200);
    }

    @Test
    public void getAllProjects3() {
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.setBaseUri("https://www.pivotaltracker.com/services/v5");
        apiRequest.addHeaders("X-TrackerToken", "1d24b2ee47d04c09615c6811a19fba0a");
        apiRequest.setEndpoint("/projects/{projectId}");
        apiRequest.addPathParams("projectId", "2504059");
        apiRequest.setMethod(ApiMethod.GET);

        ApiResponse apiResponse = new ApiResponse(ApiManager.execute(apiRequest));
        Project project = apiResponse.getBody(Project.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        Assert.assertEquals(project.getId(), 2504059);
        apiResponse.validateBodySchema("schemas/project.json");
    }

    @Test
    public void getAllProjects4() {
        ApiRequest apiRequest = new ApiRequestBuilder()
                .baseUri("https://www.pivotaltracker.com/services/v5")
                .headers("X-TrackerToken", "1d24b2ee47d04c09615c6811a19fba0a")
                .endpoint("/projects/{projectId}")
                .pathParams("projectId", "2504059")
                .method(ApiMethod.GET)
                .build();

        ApiResponse apiResponse = new ApiResponse(ApiManager.execute(apiRequest));
        Project project = apiResponse.getBody(Project.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        Assert.assertEquals(project.getName(), "Executioner test");
        Assert.assertEquals(project.getCreated_at(), "2021-06-14T02:20:00Z");
        apiResponse.validateBodySchema("schemas/project.json");
    }

    @Test
    public void postCreateProject() throws JsonProcessingException {
        Project project = new Project();
        project.setName("Executioner test 34 xzd222333");

        ApiRequest apiRequest= new ApiRequest();
        apiRequest.setBaseUri("https://www.pivotaltracker.com/services/v5");
        apiRequest.addHeaders("X-TrackerToken","1d24b2ee47d04c09615c6811a19fba0a");
        apiRequest.setEndpoint("/projects");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.setBody(new ObjectMapper().writeValueAsString(project));

        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        apiResponse.getResponse().then().log().body();
        Assert.assertEquals(apiResponse.getStatusCode(),200);
    }
}
