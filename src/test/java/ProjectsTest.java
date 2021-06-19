import api.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Project;
import generalSetting.ParametersDefault;
import org.testng.Assert;
import org.testng.annotations.*;

public class ProjectsTest {
    private static Project project = new Project();


    /**
     * Setup token and main URL general in test
     *
     * @return IBuilderApiResponse in order to permit to set that testes need.
     */
    public IBuilderApiResponse baseRequest() {
        return new ApiRequestBuilder()
                .baseUri(ParametersDefault.URL_BASE)
                .headers(ParametersDefault.KEY_VALUE, ParametersDefault.VALUE_KEY);
    }

    @BeforeMethod(onlyForGroups={"getProject","verifySchemaProject","deleteProject","projectWithSameName"})
    public void setupBasicRequirement() throws JsonProcessingException {
        Project projectCreate = new Project();
        projectCreate.setName("Project to test");
        ApiRequest apiRequest = baseRequest()
                .endpoint(ParametersDefault.END_POINT_PROJECT)
                .method(ApiMethod.POST)
                .body(new ObjectMapper().writeValueAsString(projectCreate))
                .build();
        project = ApiManager.execute(apiRequest).getBody(Project.class);
    }

    @AfterMethod(onlyForGroups={"getProject","verifySchemaProject","createProject","projectWithSameName"})
    public void cleanRepository() {
        ApiRequest apiRequest = baseRequest()
                .endpoint(ParametersDefault.END_POINT_PROJECT_TO_INTERACT)
                .pathParams(ParametersDefault.PROJECT_ID, project.getId().toString())
                .method(ApiMethod.DELETE).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
    }

    @Test(groups = "getProject")
    public void getNameProjectsCreated() {
        String actual = project.getName();
        String expected = "Project to test";
        Assert.assertEquals(actual, expected);
    }

    @Test(groups = "getProject")
    public void createDefault_notPublic() {
        boolean actual = project.getPublicc();
        boolean expected = false;
        Assert.assertEquals(actual, expected);
    }

    @Test(groups = "getProject")
    public void createDefault_WeekStartDay_Monday() {
        String actual = project.getWeek_start_day();
        String expected = "Monday";
        Assert.assertEquals(actual, expected);
    }

    @Test(groups = "getProject")
    public void createDefault_kind_project() {
        String actual = project.getKind();
        String expected = "project";
        Assert.assertEquals(actual, expected);
    }

    @Test(groups ="verifySchemaProject")
    public void verifySchemaInProject() {
        ApiRequest apiRequest = baseRequest()
                .endpoint(ParametersDefault.END_POINT_PROJECT_TO_INTERACT)
                .pathParams(ParametersDefault.PROJECT_ID, project.getId().toString())
                .method(ApiMethod.GET).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        apiResponse.validateBodySchema("schemas/project.json");
    }

    @Test(groups = "getProject")
    public void getAProject_status_200() {
        ApiRequest apiRequest = baseRequest()
                .endpoint(ParametersDefault.END_POINT_PROJECT_TO_INTERACT)
                .pathParams(ParametersDefault.PROJECT_ID, project.getId().toString())
                .method(ApiMethod.GET).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test(groups = "deleteProject")
    public void deleteProject_successful_204() {
        ApiRequest apiRequest = baseRequest()
                .endpoint(ParametersDefault.END_POINT_PROJECT_TO_INTERACT)
                .pathParams(ParametersDefault.PROJECT_ID, project.getId().toString())
                .method(ApiMethod.DELETE)
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
    }

    @Test(groups = "createProject")
    public void createProject_successful_200() throws JsonProcessingException {
        Project projectCreate = new Project();
        projectCreate.setName("Project_create_in_test");
        ApiRequest apiRequest = baseRequest()
                .endpoint(ParametersDefault.END_POINT_PROJECT)
                .method(ApiMethod.POST)
                .body(new ObjectMapper().writeValueAsString(projectCreate))
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        project = apiResponse.getBody(Project.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test
    public void getAllProjects() {
        ApiRequest apiRequest2 =baseRequest()
                .endpoint(ParametersDefault.END_POINT_PROJECT)
                .method(ApiMethod.GET).build();
        ApiResponse apiResponse2 = ApiManager.execute(apiRequest2);
        Assert.assertEquals(apiResponse2.getStatusCode(), 200);
    }

    @Test
    public void createProject_nameIsEmpty_400() throws JsonProcessingException {
        Project projectCreate = new Project();
        projectCreate.setName("");
        ApiRequest apiRequest = baseRequest()
                .endpoint(ParametersDefault.END_POINT_PROJECT)
                .method(ApiMethod.POST)
                .body(new ObjectMapper().writeValueAsString(projectCreate))
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test(groups = "projectWithSameName")
    public void createProject_nameWithSameNameTheOther_400() throws JsonProcessingException {
        Project projectCreate = new Project();
        projectCreate.setName(project.getName());
        ApiRequest apiRequest = baseRequest()
                .endpoint(ParametersDefault.END_POINT_PROJECT)
                .method(ApiMethod.POST)
                .body(new ObjectMapper().writeValueAsString(projectCreate))
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void deleteProject_removeByOtherUser_404() {
        ApiRequest apiRequest = baseRequest()
                .endpoint(ParametersDefault.END_POINT_PROJECT_TO_INTERACT)
                .pathParams(ParametersDefault.PROJECT_ID, "441794316")
                .method(ApiMethod.DELETE)
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 404);
    }

    @Test
    public void createProject_wrongInPutDay_400 () throws JsonProcessingException {
        Project projectCreate = new Project();
        projectCreate.setName("Project to test 2");
        projectCreate.setWeek_start_day("December");
        ApiRequest apiRequest = baseRequest()
                .endpoint(ParametersDefault.END_POINT_PROJECT)
                .method(ApiMethod.POST)
                .body(new ObjectMapper().writeValueAsString(projectCreate))
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createProject_nameNULL_400 () throws JsonProcessingException {
        Project projectCreate = new Project();
        projectCreate.setName(null);
        ApiRequest apiRequest = baseRequest()
                .endpoint(ParametersDefault.END_POINT_PROJECT)
                .method(ApiMethod.POST)
                .body(new ObjectMapper().writeValueAsString(projectCreate))
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createProject_NotSupportNameWith60orMore_400 () throws JsonProcessingException {
        Project projectCreate = new Project();
        projectCreate.setName("Get the Salesforce version. Get the Salesforce version. Use the Salesforce v2");
        ApiRequest apiRequest = baseRequest()
                .endpoint(ParametersDefault.END_POINT_PROJECT)
                .method(ApiMethod.POST)
                .body(new ObjectMapper().writeValueAsString(projectCreate))
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }
}
