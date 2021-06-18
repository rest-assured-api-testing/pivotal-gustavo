import api.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Epic;
import entities.Project;
import org.testng.Assert;
import org.testng.annotations.*;

public class EpicTest {
    private String idProject;
    private String idEpic;
    private String idEpicDelete;
    private String idEpicPut;

    public IBuilderApiResponse baseRequest() {
        return new ApiRequestBuilder()
                .baseUri(ParametersDefault.URL_BASE)
                .headers(ParametersDefault.KEY_VALUE, ParametersDefault.VALUE_KEY);
    }

    @BeforeClass
    public void createProjectReference() throws JsonProcessingException {
        Project projectCreate = new Project();
        projectCreate.setName("Project to test epic 2");
        ApiRequest apiRequest = baseRequest()
                .endpoint("/projects")
                .method(ApiMethod.POST)
                .body(new ObjectMapper().writeValueAsString(projectCreate))
                .build();
        idProject = ApiManager.execute(apiRequest).getBody(Project.class).getId().toString();
    }

    @BeforeMethod(onlyForGroups = "postEpic-Duplicate")
    public void createEpicToNegativeTest() throws JsonProcessingException {
        Epic epic = new Epic();
        epic.setName("Test-Epic-Duplicate");
        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/{projectId}/epics/")
                .pathParams("projectId", idProject)
                .body(new ObjectMapper().writeValueAsString(epic))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
    }

    @BeforeMethod(onlyForGroups = "getEpic")
    public void createEpic() throws JsonProcessingException {
        Epic epic = new Epic();
        epic.setName("Test-Epic-to-test-GET");
        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/{projectId}/epics/")
                .pathParams("projectId", idProject)
                .body(new ObjectMapper().writeValueAsString(epic))
                .method(ApiMethod.POST).build();
        idEpic = ApiManager.execute(apiRequest).getBody(Epic.class).getId().toString();
    }

    @BeforeMethod(onlyForGroups = "deleteEpic")
    public void createEpicToDelete() throws JsonProcessingException {
        Epic epic = new Epic();
        epic.setName("Test-Epic-to-test-GET");
        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/{projectId}/epics/")
                .pathParams("projectId", idProject)
                .body(new ObjectMapper().writeValueAsString(epic))
                .method(ApiMethod.POST).build();
        idEpicDelete = ApiManager.execute(apiRequest).getBody(Epic.class).getId().toString();
    }

    @BeforeMethod(onlyForGroups = "putEpic")
    public void createEpicToPut() throws JsonProcessingException {
        Epic epic = new Epic();
        epic.setName("Test-Epic-to-test-PUT");
        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/{projectId}/epics/")
                .pathParams("projectId", idProject)
                .body(new ObjectMapper().writeValueAsString(epic))
                .method(ApiMethod.POST).build();
        idEpicPut = ApiManager.execute(apiRequest).getBody(Epic.class).getId().toString();
    }

    @AfterClass
    public void deleteProjectReference() {
        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/{projectId}")
                .pathParams("projectId", idProject)
                .method(ApiMethod.DELETE)
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
    }

    @Test
    public void getAllEpicAProject_successful_200() {
        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/{projectId}/epics")
                .pathParams("projectId", idProject)
                .method(ApiMethod.GET).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test
    public void createEpic_successful_200() throws JsonProcessingException {
        Epic epic = new Epic();
        epic.setName("Test-Epic");
        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/{projectId}/epics/")
                .pathParams("projectId", idProject)
                .body(new ObjectMapper().writeValueAsString(epic))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test(groups = "getEpic")
    public void getEpic_successful_200() {
        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/{projectId}/epics/{epicId}")
                .pathParams("projectId", idProject)
                .pathParams("epicId", idEpic)
                .method(ApiMethod.GET).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test(groups = "deleteEpic")
    public void deleteEpic_successful_203() {
        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/{projectId}/epics/{epicId}")
                .pathParams("projectId", idProject)
                .pathParams("epicId", idEpicDelete)
                .method(ApiMethod.DELETE).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
    }

    @Test(groups = "putEpic")
    public void putEpic_changeName_200() throws JsonProcessingException {
        Epic epic = new Epic();
        epic.setName("Change the name epic");

        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/{projectId}/epics/{epicId}")
                .pathParams("projectId", idProject)
                .pathParams("epicId", idEpicPut)
                .body(new ObjectMapper().writeValueAsString(epic))
                .method(ApiMethod.PUT).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test(groups = "postEpic-Duplicate")
    public void createEpic_withNameSameNameThanOtherEpic_400() throws JsonProcessingException {
        Epic epic = new Epic();
        epic.setName("Test-Epic-Duplicate");
        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/{projectId}/epics/")
                .pathParams("projectId", idProject)
                .body(new ObjectMapper().writeValueAsString(epic))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createEpic_withNameEmpty_400() throws JsonProcessingException {
        Epic epic = new Epic();
        epic.setName("");
        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/{projectId}/epics/")
                .pathParams("projectId", idProject)
                .body(new ObjectMapper().writeValueAsString(epic))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createEpic_withNameHasSpace_400() throws JsonProcessingException {
        Epic epic = new Epic();
        epic.setName(" ");
        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/{projectId}/epics/")
                .pathParams("projectId", idProject)
                .body(new ObjectMapper().writeValueAsString(epic))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }
}
