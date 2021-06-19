import api.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Epic;
import org.testng.Assert;
import org.testng.annotations.*;

public class EpicTest extends ProjectDefault {
    private String idEpic;
    private Epic epicTest;

    /**
     * Setup end point to get epic in test and path parameters the project in which test work
     *
     * @return IBuilderApiResponse in order to permit to set that testes need.
     */
    public IBuilderApiResponse baseRequestEpic() {
        return baseRequest()
                .endpoint(ParametersDefault.END_POINT_EPIC)
                .pathParams(ParametersDefault.PROJECT_ID, idProject);
    }

    @BeforeMethod(onlyForGroups = {"getEpic","postEpic-Duplicate","verifySchemaEpic","putEpic","deleteEpic"})
    public void createEpic() throws JsonProcessingException {
        Epic epic = new Epic();
        epic.setName("Test-Epic-to-test-GET");
        ApiRequest apiRequest = baseRequestEpic()
                .body(new ObjectMapper().writeValueAsString(epic))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        epicTest =apiResponse.getBody(Epic.class);
        idEpic = apiResponse.getBody(Epic.class).getId().toString();
    }

    @AfterMethod(onlyForGroups = {"getEpic","postEpic-Duplicate","verifySchemaEpic","putEpic","createEpic"})
    public void deleteEpicReference() {
        ApiRequest apiRequest = baseRequestEpic()
                .endpoint(ParametersDefault.END_POINT_EPIC_TO_MODIFY)
                .pathParams(ParametersDefault.EPIC_ID, idEpic)
                .method(ApiMethod.DELETE)
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
    }

    @Test
    public void getAllEpicOfProject_successful_200() {
        ApiRequest apiRequest = baseRequestEpic()
                .method(ApiMethod.GET).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test(groups = "getEpic")
    public void createDefaultThatKindIsEpic_successful_200() {
        String actual = epicTest.getKind();
        String expected = "epic";
        Assert.assertEquals(actual, expected);
    }

    @Test(groups = "getEpic")
    public void createDefaultHasCorrectIDProject_successful_Epic() {
        int actual = epicTest.getProject_id();
        int expected = Integer.parseInt(idProject);
        Assert.assertEquals(actual, expected);
    }

    @Test(groups= "createEpic")
    public void createEpic_successful_200() throws JsonProcessingException {
        Epic epic = new Epic();
        epic.setName("Test-Epic");
        ApiRequest apiRequest = baseRequestEpic()
                .body(new ObjectMapper().writeValueAsString(epic))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        idEpic = apiResponse.getBody(Epic.class).getId().toString();
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test(groups = "getEpic")
    public void getEpic_successful_200() {
        ApiRequest apiRequest = baseRequestEpic()
                .endpoint(ParametersDefault.END_POINT_EPIC_TO_MODIFY)
                .pathParams(ParametersDefault.EPIC_ID, idEpic)
                .method(ApiMethod.GET).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test(groups = "verifySchemaEpic")
    public void verifySchemaInEpic() {
        ApiRequest apiRequest = baseRequestEpic()
                .endpoint(ParametersDefault.END_POINT_EPIC_TO_MODIFY)
                .pathParams(ParametersDefault.EPIC_ID, idEpic)
                .method(ApiMethod.GET).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        apiResponse.validateBodySchema("schemas/epic.json");
    }

    @Test(groups = "deleteEpic")
    public void deleteEpic_successful_203() {
        ApiRequest apiRequest = baseRequestEpic()
                .endpoint(ParametersDefault.END_POINT_EPIC_TO_MODIFY)
                .pathParams(ParametersDefault.EPIC_ID, idEpic)
                .method(ApiMethod.DELETE).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
    }

    @Test(groups = "putEpic")
    public void putEpic_changeName_200() throws JsonProcessingException {
        Epic epic = new Epic();
        epic.setName("Change the name epic");
        ApiRequest apiRequest = baseRequestEpic()
                .endpoint(ParametersDefault.END_POINT_EPIC_TO_MODIFY)
                .pathParams(ParametersDefault.EPIC_ID, idEpic)
                .body(new ObjectMapper().writeValueAsString(epic))
                .method(ApiMethod.PUT).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test(groups = "postEpic-Duplicate")
    public void createEpic_withNameSameNameThanOtherEpic_400() throws JsonProcessingException {
        Epic epic = new Epic();
        epic.setName(epicTest.getName());
        ApiRequest apiRequest = baseRequestEpic()
                .body(new ObjectMapper().writeValueAsString(epic))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createEpic_withNameEmpty_400() throws JsonProcessingException {
        Epic epic = new Epic();
        epic.setName("");
        ApiRequest apiRequest = baseRequestEpic()
                .body(new ObjectMapper().writeValueAsString(epic))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createEpic_withNameHasSpace_400() throws JsonProcessingException {
        Epic epic = new Epic();
        epic.setName(" ");
        ApiRequest apiRequest = baseRequestEpic()
                .body(new ObjectMapper().writeValueAsString(epic))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createEpic_withNullName_400() throws JsonProcessingException {
        Epic epic = new Epic();
        epic.setName(null);
        ApiRequest apiRequest = baseRequestEpic()
                .body(new ObjectMapper().writeValueAsString(epic))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createEpic_withWrongProject_404() throws JsonProcessingException {
        Epic epic = new Epic();
        epic.setName("Test wrongProject");
        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/49416872/epics/")
                .body(new ObjectMapper().writeValueAsString(epic))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 404);
    }

    @Test
    public void createEpic_withWrongIdProjectNotExist_404() throws JsonProcessingException {
        Epic epic = new Epic();
        epic.setName("Test wrongProject");
        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/49asfer732/epics/")
                .body(new ObjectMapper().writeValueAsString(epic))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 404);
    }

    @Test
    public void createEpic_withWrongEndpoint_404() throws JsonProcessingException {
        Epic epic = new Epic();
        epic.setName(null);
        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/{projectId}/epic/")
                .pathParams(ParametersDefault.PROJECT_ID, idProject)
                .body(new ObjectMapper().writeValueAsString(epic))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 404);
    }
}
