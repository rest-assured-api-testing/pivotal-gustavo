import api.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.CommentInStory;
import entities.Epic;
import entities.Story;
import org.testng.Assert;
import org.testng.annotations.*;

public class CommentInStoryTest extends ProjectDefault {
    private String idStoryReference;
    private String idCommentToTest;
    private CommentInStory commentInStoryToTest;

    /**
     * Setup the end point to get Comment to story
     * Setup parameters of project and story in which test work
     *
     * @return IBuilderApiResponse in order to permit to set that testes need.
     */
    public IBuilderApiResponse baseRequestComment() {
        return baseRequest()
                .endpoint(ParametersDefault.END_POINT_COMMENT_IN_STORY)
                .pathParams(ParametersDefault.PROJECT_ID, idProject)
                .pathParams(ParametersDefault.STORY_ID, idStoryReference);
    }

    @BeforeClass(dependsOnMethods = "createProjectReference")
    public void createStoryReference() throws JsonProcessingException {
        Story story=new Story();
        story.setName("Story-to-test");
        ApiRequest apiRequest = baseRequest()
                .endpoint(ParametersDefault.END_POINT_STORY)
                .pathParams(ParametersDefault.PROJECT_ID, idProject)
                .body(new ObjectMapper().writeValueAsString(story))
                .method(ApiMethod.POST).build();
        idStoryReference = ApiManager.execute(apiRequest).getBody(Story.class).getId().toString();
    }

    @BeforeMethod(onlyForGroups={"getCommentStory","verifyCommentStory","deleteComment","putComment"})
    public void createCommentToTest() throws JsonProcessingException {
        CommentInStory commentInStory =new CommentInStory();
        commentInStory.setText("Test-comment");
        ApiRequest apiRequest = baseRequestComment()
                .body(new ObjectMapper().writeValueAsString(commentInStory))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        commentInStoryToTest =apiResponse.getBody(CommentInStory.class);
        idCommentToTest = apiResponse.getBody(CommentInStory.class).getId().toString();
    }

    @AfterMethod(onlyForGroups = {"getCommentStory","verifyCommentStory","putComment"})
    public void deleteCommentCreated() {
        ApiRequest apiRequest = baseRequestComment()
                .endpoint(ParametersDefault.END_POINT_COMMENT_TO_MODIFY_IN_STORY)
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

    @Test(groups = "getCommentStory")
    public void createDefaultThatKindIsStory_successful_200() {
        String actual = commentInStoryToTest.getKind();
        String expected = "comment";
        Assert.assertEquals(actual, expected);
    }

    @Test(groups = "getCommentStory")
    public void createDefaultHasCorrectIDStory_successful_Epic() {
        String actual = commentInStoryToTest.getStory_id().toString();
        String expected = idStoryReference;
        Assert.assertEquals(actual, expected);
    }

    @Test(groups = "getCommentStory")
    public void getCommentStory_successful_200() {
        ApiRequest apiRequest = baseRequestComment()
                .endpoint(ParametersDefault.END_POINT_COMMENT_TO_MODIFY_IN_STORY)
                .pathParams(ParametersDefault.COMMENT_ID, idCommentToTest)
                .method(ApiMethod.GET).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test(groups = "verifyCommentStory")
    public void verifySchemaToComment() {
        ApiRequest apiRequest = baseRequestComment()
                .endpoint(ParametersDefault.END_POINT_COMMENT_TO_MODIFY_IN_STORY)
                .pathParams(ParametersDefault.COMMENT_ID, idCommentToTest)
                .method(ApiMethod.GET).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        apiResponse.validateBodySchema("schemas/commentStory.json");
    }

    @Test
    public void createCommentInProject_successful_200() throws JsonProcessingException {
        CommentInStory commentInStory =new CommentInStory();
        commentInStory.setText("Test-comment-to-story");
        ApiRequest apiRequest = baseRequestComment()
                .body(new ObjectMapper().writeValueAsString(commentInStory))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }


    @Test(groups = "deleteComment")
    public void deleteComment_successful_204() {
        ApiRequest apiRequest = baseRequestComment()
                .endpoint(ParametersDefault.END_POINT_COMMENT_TO_MODIFY_IN_STORY)
                .pathParams(ParametersDefault.COMMENT_ID, idCommentToTest)
                .method(ApiMethod.DELETE).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
    }

    @Test(groups = "putComment")
    public void putStory_changeName_200() throws JsonProcessingException {
        CommentInStory commentInStory =new CommentInStory();
        commentInStory.setText("Change-The-Comment-In-Story");

        ApiRequest apiRequest = baseRequestComment()
                .endpoint(ParametersDefault.END_POINT_COMMENT_TO_MODIFY_IN_STORY)
                .pathParams(ParametersDefault.COMMENT_ID, idCommentToTest)
                .body(new ObjectMapper().writeValueAsString(commentInStory))
                .method(ApiMethod.PUT).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test
    public void createComment_withTextEmpty_400() throws JsonProcessingException {
        CommentInStory commentInStory =new CommentInStory();
        commentInStory.setText("");
        ApiRequest apiRequest = baseRequestComment()
                .body(new ObjectMapper().writeValueAsString(commentInStory))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createComment_withNameHasSpace_400() throws JsonProcessingException {
        CommentInStory commentInStory =new CommentInStory();
        commentInStory.setText(" ");
        ApiRequest apiRequest = baseRequestComment()
                .body(new ObjectMapper().writeValueAsString(commentInStory))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createCommentInStory_withNullName_400() throws JsonProcessingException {
        CommentInStory commentInStory =new CommentInStory();
        commentInStory.setText(null);
        ApiRequest apiRequest = baseRequestComment()
                .body(new ObjectMapper().writeValueAsString(commentInStory))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createCommentInStory_withWrongProject_403() throws JsonProcessingException {
        CommentInStory commentInStory =new CommentInStory();
        commentInStory.setText("Test wrong Project");
        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/4565/stories/{storiesId}/comments")
                .pathParams(ParametersDefault.STORY_ID, idStoryReference)
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
                .endpoint("projects/4e5dsg65/stories/{storiesId}/comments")
                .pathParams(ParametersDefault.STORY_ID, idStoryReference)
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
                .endpoint("projects/{projectId}/stories/{storiesId}/comment")
                .pathParams(ParametersDefault.STORY_ID, idStoryReference)
                .body(new ObjectMapper().writeValueAsString(commentInStory))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 404);
    }
}
