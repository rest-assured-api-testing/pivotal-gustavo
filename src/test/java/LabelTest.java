import api.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Label;
import generalSetting.ParametersDefault;
import generalSetting.ProjectDefault;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LabelTest extends ProjectDefault {
    private Label labelTest;
    private String idLabelTest;

    /**
     * Setup the end point to get label
     * Setup parameters of project in which test work
     *
     * @return IBuilderApiResponse in order to permit to set that testes need.
     */
    public IBuilderApiResponse baseRequestLabel() {
        return baseRequest()
                .endpoint(ParametersDefault.END_POINT_LABEL)
                .pathParams(ParametersDefault.PROJECT_ID, idProject);
    }

    @BeforeMethod(onlyForGroups = {"getLabel","putLabel","verifySchemaLabel","postTwoLabel-sameName","deleteLabel"})
    public void createLabel() throws JsonProcessingException {
        Label label=new Label();
        label.setName("Test-Label-to-test");
        ApiRequest apiRequest = baseRequestLabel()
                .body(new ObjectMapper().writeValueAsString(label))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        labelTest =apiResponse.getBody(Label.class);
        idLabelTest = apiResponse.getBody(Label.class).getId().toString();
    }

    @AfterMethod(onlyForGroups = {"getLabel","putLabel","verifySchemaLabel","postTwoLabel-sameName"})
    public void deleteLabelTest() {
        ApiRequest apiRequest = baseRequestLabel()
                .endpoint(ParametersDefault.END_POINT_LABEL_TO_INTERACT)
                .pathParams(ParametersDefault.LABEL_ID, idLabelTest)
                .method(ApiMethod.DELETE)
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
    }

    @Test
    public void getAllLabelOfProject_successful_200() {
        ApiRequest apiRequest = baseRequestLabel()
                .method(ApiMethod.GET).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test
    public void createLabel_successful_200() throws JsonProcessingException {
        Label label=new Label();
        label.setName("Test-Label");
        ApiRequest apiRequest = baseRequestLabel()
                .body(new ObjectMapper().writeValueAsString(label))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test(groups = "getLabel")
    public void createDefaultThatKindIsLabel_successful_200() {
        String actual = labelTest.getKind();
        String expected = "label";
        Assert.assertEquals(actual, expected);
    }

    @Test(groups = "getLabel")
    public void createDefaultHasCorrectIDProject_successful_Epic() {
        int actual = labelTest.getProject_id();
        int expected = Integer.parseInt(idProject);
        Assert.assertEquals(actual, expected);
    }

    @Test(groups = "getLabel")
    public void getLabel_successful_200() {
        ApiRequest apiRequest = baseRequestLabel()
                .endpoint(ParametersDefault.END_POINT_LABEL_TO_INTERACT)
                .pathParams(ParametersDefault.LABEL_ID, idLabelTest)
                .method(ApiMethod.GET).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

   @Test(groups = "verifySchemaLabel")
    public void verifySchemaInLabel() {
        ApiRequest apiRequest = baseRequestLabel()
                .endpoint(ParametersDefault.END_POINT_LABEL_TO_INTERACT)
                .pathParams(ParametersDefault.LABEL_ID, idLabelTest)
                .method(ApiMethod.GET).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        apiResponse.validateBodySchema("schemas/label.json");
    }

    @Test(groups = "deleteLabel")
    public void deleteLabel_successful_203() {
        ApiRequest apiRequest = baseRequestLabel()
                .endpoint(ParametersDefault.END_POINT_LABEL_TO_INTERACT)
                .pathParams(ParametersDefault.LABEL_ID, idLabelTest)
                .method(ApiMethod.DELETE).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
    }

    @Test(groups = "putLabel")
    public void putLabel_changeName_200() throws JsonProcessingException {
        Label label=new Label();
        label.setName("Change-The-Name-Label");

        ApiRequest apiRequest = baseRequestLabel()
                .endpoint(ParametersDefault.END_POINT_LABEL_TO_INTERACT)
                .pathParams(ParametersDefault.LABEL_ID, idLabelTest)
                .body(new ObjectMapper().writeValueAsString(label))
                .method(ApiMethod.PUT).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test(groups = "postTwoLabel-sameName")
    public void createLabel_withTwoLabelWithSameName_400() throws JsonProcessingException {
        Label label=new Label();
        label.setName(labelTest.getName());
        ApiRequest apiRequest = baseRequestLabel()
                .body(new ObjectMapper().writeValueAsString(label))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createLabel_withNameEmpty_400() throws JsonProcessingException {
        Label label=new Label();
        label.setName("");
        ApiRequest apiRequest = baseRequestLabel()
                .body(new ObjectMapper().writeValueAsString(label))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createLabel_withNameHasSpace_400() throws JsonProcessingException {
        Label label=new Label();
        label.setName(" ");
        ApiRequest apiRequest = baseRequestLabel()
                .body(new ObjectMapper().writeValueAsString(label))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createLabel_withNameIsNull_400() throws JsonProcessingException {
        Label label=new Label();
        label.setName(null);
        ApiRequest apiRequest = baseRequestLabel()
                .body(new ObjectMapper().writeValueAsString(label))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createLabel_withNameHas260Characters_400() throws JsonProcessingException {
        Label label=new Label();
        label.setName("From this output you can see much more detailed information about the Account object, " +
                "such as its field attributes and child relationships. Now you have enough information to construct " +
                "useful queries and updates for the Account objects in your organization,gg.");
        ApiRequest apiRequest = baseRequestLabel()
                .body(new ObjectMapper().writeValueAsString(label))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createEpic_withWrongIdProjectNotExist_404() throws JsonProcessingException {
        Label label=new Label();
        label.setName("Test wrongProject");
        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/9a4f791/labels/")
                .body(new ObjectMapper().writeValueAsString(label))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 404);
    }

    @Test
    public void createEpic_withWrongEndpoint_404() throws JsonProcessingException {
        Label label=new Label();
        label.setName("Test wrongProject");
        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/{projectId}/label/")
                .pathParams(ParametersDefault.PROJECT_ID, idProject)
                .body(new ObjectMapper().writeValueAsString(label))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 404);
    }
}
