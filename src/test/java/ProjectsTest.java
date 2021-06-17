import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Project;
import org.testng.Assert;
import org.testng.annotations.*;

public class ProjectsTest {
    private static Project project = new Project();

    @BeforeClass
    public void setupProject() throws JsonProcessingException {
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
    public void cleanRepository(){
        ApiRequest apiRequest = new ApiRequestBuilder()
                .baseUri(Parameters.URL_BASE)
                .headers(Parameters.KEY_VALUE, Parameters.VALUE_KEY)
                .endpoint("/projects/{projectId}")
                .pathParams("projectId", project.getId().toString())
                .method(ApiMethod.DELETE)
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        System.out.println(apiResponse.getStatusCode());
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
}
