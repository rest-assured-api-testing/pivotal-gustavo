import api.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Epic;
import entities.Label;
import entities.Project;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LabelTest {
    private String idProject;
    private String idLabelPut;
    private String idLabelToDelete;
    private String idLabelToVerifySchema;
    private String idLabelToGet;

    public IBuilderApiResponse baseRequest() {
        return new ApiRequestBuilder()
                .baseUri(ParametersDefault.URL_BASE)
                .headers(ParametersDefault.KEY_VALUE, ParametersDefault.VALUE_KEY);
    }

    @BeforeClass
    public void createProjectReference() throws JsonProcessingException {
        Project projectCreate = new Project();
        projectCreate.setName("Project to test Label");
        ApiRequest apiRequest = baseRequest()
                .endpoint("/projects")
                .method(ApiMethod.POST)
                .body(new ObjectMapper().writeValueAsString(projectCreate))
                .build();
        idProject = ApiManager.execute(apiRequest).getBody(Project.class).getId().toString();
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
    public void getAllLabelAProject_successful_200() {
        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/{projectId}/labels/")
                .pathParams("projectId", idProject)
                .method(ApiMethod.GET).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test
    public void createLabel_successful_200() throws JsonProcessingException {
        Label label=new Label();
        label.setName("Test-Label");
        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/{projectId}/labels/")
                .pathParams("projectId", idProject)
                .body(new ObjectMapper().writeValueAsString(label))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @BeforeMethod(onlyForGroups = "getLabel")
    public void createLabelToGet() throws JsonProcessingException {
        Label label=new Label();
        label.setName("Test-Label-to-GET");
        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/{projectId}/labels/")
                .pathParams("projectId", idProject)
                .body(new ObjectMapper().writeValueAsString(label))
                .method(ApiMethod.POST).build();
        idLabelToGet = ApiManager.execute(apiRequest).getBody(Epic.class).getId().toString();
    }

    @BeforeMethod(onlyForGroups = "verifySchemaLabel")
    public void createLabelToVerifySchemaLabel() throws JsonProcessingException {
        Label label=new Label();
        label.setName("Test-Label-to-verify-Schema");
        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/{projectId}/labels/")
                .pathParams("projectId", idProject)
                .body(new ObjectMapper().writeValueAsString(label))
                .method(ApiMethod.POST).build();
        idLabelToVerifySchema = ApiManager.execute(apiRequest).getBody(Epic.class).getId().toString();
    }

    @Test(groups = "getLabel")
    public void getEpic_successful_200() {
        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/{projectId}/labels/{labelsId}")
                .pathParams("projectId", idProject)
                .pathParams("labelsId", idLabelToGet)
                .method(ApiMethod.GET).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

   @Test(groups = "verifySchemaLabel")
    public void verifySchemaInLabel() {
        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/{projectId}/labels/{labelsId}")
                .pathParams("projectId", idProject)
                .pathParams("labelsId", idLabelToVerifySchema)
                .method(ApiMethod.GET).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        apiResponse.validateBodySchema("schemas/label.json");
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @BeforeMethod(onlyForGroups = "deleteLabel")
    public void createEpicToDelete() throws JsonProcessingException {
        Label label=new Label();
        label.setName("Test-Label-to-test-DELETE");
        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/{projectId}/labels/")
                .pathParams("projectId", idProject)
                .body(new ObjectMapper().writeValueAsString(label))
                .method(ApiMethod.POST).build();
        idLabelToDelete = ApiManager.execute(apiRequest).getBody(Epic.class).getId().toString();
    }

    @Test(groups = "deleteLabel")
    public void deleteEpic_successful_203() {
        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/{projectId}/labels/{labelsId}")
                .pathParams("projectId", idProject)
                .pathParams("labelsId", idLabelToDelete)
                .method(ApiMethod.DELETE).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
    }

    @BeforeMethod(onlyForGroups = "putLabel")
    public void createEpicToPut() throws JsonProcessingException {
        Label label=new Label();
        label.setName("Test-Label-to-test-PUT");
        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/{projectId}/labels/")
                .pathParams("projectId", idProject)
                .body(new ObjectMapper().writeValueAsString(label))
                .method(ApiMethod.POST).build();
        idLabelPut = ApiManager.execute(apiRequest).getBody(Epic.class).getId().toString();
    }

    @Test(groups = "putLabel")
    public void putLabel_changeName_200() throws JsonProcessingException {
        Label label=new Label();
        label.setName("Change-The-Name-Label");

        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/{projectId}/labels/{labelsId}")
                .pathParams("projectId", idProject)
                .pathParams("labelsId", idLabelPut)
                .body(new ObjectMapper().writeValueAsString(label))
                .method(ApiMethod.PUT).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @BeforeMethod(onlyForGroups = "postTwoLabel-sameName")
    public void createLabelToNegativeTest() throws JsonProcessingException {
        Label label=new Label();
        label.setName("Test-Label-Duplicate");
        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/{projectId}/labels/")
                .pathParams("projectId", idProject)
                .body(new ObjectMapper().writeValueAsString(label))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
    }

    @Test(groups = "postTwoLabel-sameName")
    public void createEpic_withTwoLabelWithSameName_400() throws JsonProcessingException {
        Label label=new Label();
        label.setName("Test-Label-Duplicate");
        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/{projectId}/labels/")
                .pathParams("projectId", idProject)
                .body(new ObjectMapper().writeValueAsString(label))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createLabel_withNameEmpty_400() throws JsonProcessingException {
        Label label=new Label();
        label.setName("");
        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/{projectId}/labels/")
                .pathParams("projectId", idProject)
                .body(new ObjectMapper().writeValueAsString(label))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createLabel_withNameHasSpace_400() throws JsonProcessingException {
        Label label=new Label();
        label.setName(" ");
        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/{projectId}/labels/")
                .pathParams("projectId", idProject)
                .body(new ObjectMapper().writeValueAsString(label))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }
}
