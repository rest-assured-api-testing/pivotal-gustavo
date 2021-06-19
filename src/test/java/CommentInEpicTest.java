import api.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.CommentInEpic;
import entities.CommentInStory;
import entities.Epic;
import generalSetting.ParametersDefault;
import generalSetting.ProjectDefault;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CommentInEpicTest extends ProjectDefault {
    private String idEpicReference;
    private String idCommentToTest;
    private CommentInEpic commentInEpicToTest;

    /**
     * Setup the end point to get Comment to Epic
     * Setup parameters of project and Epic in which test work
     *
     * @return IBuilderApiResponse in order to permit to set that testes need.
     */
    public IBuilderApiResponse baseRequestComment() {
        return baseRequest()
                .endpoint(ParametersDefault.END_POINT_COMMENT_IN_EPIC)
                .pathParams(ParametersDefault.PROJECT_ID, idProject)
                .pathParams(ParametersDefault.EPIC_ID, idEpicReference);
    }

    @BeforeClass(dependsOnMethods = "createProjectReference")
    public void createEpicReference() throws JsonProcessingException {
        Epic epic = new Epic();
        epic.setName("Epic-to-test");
        ApiRequest apiRequest = baseRequest()
                .endpoint(ParametersDefault.END_POINT_EPIC)
                .pathParams(ParametersDefault.PROJECT_ID, idProject)
                .body(new ObjectMapper().writeValueAsString(epic))
                .method(ApiMethod.POST).build();
        idEpicReference = ApiManager.execute(apiRequest).getBody(Epic.class).getId().toString();
    }

    @BeforeMethod(onlyForGroups={"getCommentTest","verifyCommentEpic","deleteCommentEpic","putCommentEpic"})
    public void createCommentToTest() throws JsonProcessingException {
        CommentInEpic commentInEpic =new CommentInEpic();
        commentInEpic.setText("Test-comment");
        ApiRequest apiRequest = baseRequestComment()
                .body(new ObjectMapper().writeValueAsString(commentInEpic))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        commentInEpicToTest =apiResponse.getBody(CommentInEpic.class);
        idCommentToTest = apiResponse.getBody(CommentInEpic.class).getId().toString();
    }

    @AfterMethod(onlyForGroups = {"getCommentTest","verifyCommentEpic","putCommentEpic"})
    public void deleteCommentCreated() {
        ApiRequest apiRequest = baseRequestComment()
                .endpoint(ParametersDefault.END_POINT_COMMENT_IN_EPIC_TO_INTERACT)
                .pathParams(ParametersDefault.COMMENT_ID, idCommentToTest)
                .method(ApiMethod.DELETE).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
    }

    @Test
    public void getAllCommentInStoryOfProject_successful_200() {
        ApiRequest apiRequest = baseRequestComment()
                .method(ApiMethod.GET).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);

    }

    @Test(groups = "getCommentTest")
    public void createDefaultThatKindIsComment_successful_200() {
        String actual = commentInEpicToTest.getKind();
        String expected = "comment";
        Assert.assertEquals(actual, expected);
    }

    @Test(groups = "getCommentTest")
    public void createDefaultHasCorrectIDEpic_successful_Epic() {
        String actual = commentInEpicToTest.getEpic_id().toString();
        String expected = idEpicReference;
        Assert.assertEquals(actual, expected);
    }

    @Test(groups = "getCommentTest")
    public void getCommentTest_successful_200() {
        ApiRequest apiRequest = baseRequestComment()
                .endpoint(ParametersDefault.END_POINT_COMMENT_IN_EPIC_TO_INTERACT)
                .pathParams(ParametersDefault.COMMENT_ID, idCommentToTest)
                .method(ApiMethod.GET).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test
    public void createCommentInProject_successful_200() throws JsonProcessingException {
        CommentInEpic commentInEpic = new CommentInEpic();
        commentInEpic.setText("Test-comment-Epic");
        ApiRequest apiRequest = baseRequestComment()
                .body(new ObjectMapper().writeValueAsString(commentInEpic))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test(groups = "verifyCommentEpic")
    public void verifySchemaToComment() {
        ApiRequest apiRequest = baseRequestComment()
                .endpoint(ParametersDefault.END_POINT_COMMENT_IN_EPIC_TO_INTERACT)
                .pathParams(ParametersDefault.COMMENT_ID, idCommentToTest)
                .method(ApiMethod.GET).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        apiResponse.validateBodySchema("schemas/commentEpic.json");
    }

    @Test(groups = "deleteCommentEpic")
    public void deleteComment_successful_204() {
        ApiRequest apiRequest = baseRequestComment()
                .endpoint(ParametersDefault.END_POINT_COMMENT_IN_EPIC_TO_INTERACT)
                .pathParams(ParametersDefault.COMMENT_ID, idCommentToTest)
                .method(ApiMethod.DELETE).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
    }

    @Test(groups = "putCommentEpic")
    public void putStory_changeName_200() throws JsonProcessingException {
        CommentInEpic commentInEpic =new CommentInEpic();
        commentInEpic.setText("Change-The-Comment-In-Story");

        ApiRequest apiRequest = baseRequestComment()
                .endpoint(ParametersDefault.END_POINT_COMMENT_IN_EPIC_TO_INTERACT)
                .pathParams(ParametersDefault.COMMENT_ID, idCommentToTest)
                .body(new ObjectMapper().writeValueAsString(commentInEpic))
                .method(ApiMethod.PUT).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test
    public void createComment_withTextEmpty_400() throws JsonProcessingException {
        CommentInEpic commentInEpic =new CommentInEpic();
        commentInEpic.setText("");
        ApiRequest apiRequest = baseRequestComment()
                .body(new ObjectMapper().writeValueAsString(commentInEpic))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createComment_withNameHasSpace_400() throws JsonProcessingException {
        CommentInEpic commentInEpic =new CommentInEpic();
        commentInEpic.setText(" ");
        ApiRequest apiRequest = baseRequestComment()
                .body(new ObjectMapper().writeValueAsString(commentInEpic))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createCommentInStory_withNullName_400() throws JsonProcessingException {
        CommentInEpic commentInEpic =new CommentInEpic();
        commentInEpic.setText(null);
        ApiRequest apiRequest = baseRequestComment()
                .body(new ObjectMapper().writeValueAsString(commentInEpic))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createCommentInStory_withWrongProject_403() throws JsonProcessingException {
        CommentInStory commentInStory =new CommentInStory();
        commentInStory.setText("Test wrong Project");
        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/4565/epics/{epicId}/comments")
                .pathParams(ParametersDefault.EPIC_ID, idEpicReference)
                .body(new ObjectMapper().writeValueAsString(commentInStory))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 403);
    }

    @Test
    public void createCommentInStory_withWrongIdProjectNotExist_404() throws JsonProcessingException {
        CommentInStory commentInStory =new CommentInStory();
        commentInStory.setText("Test wrong Project");
        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/4e5dsg65/epics/{epicId}/comments")
                .pathParams(ParametersDefault.EPIC_ID, idEpicReference)
                .body(new ObjectMapper().writeValueAsString(commentInStory))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 404);
    }

    @Test
    public void createEpic_withWrongEndpoint_404() throws JsonProcessingException {
        CommentInStory commentInStory =new CommentInStory();
        commentInStory.setText("Test wrong Project");
        ApiRequest apiRequest = baseRequestComment()
                .endpoint("projects/{projectId}/epics/{epicId}/comment")
                .pathParams(ParametersDefault.EPIC_ID, idEpicReference)
                .body(new ObjectMapper().writeValueAsString(commentInStory))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 404);
    }
}
