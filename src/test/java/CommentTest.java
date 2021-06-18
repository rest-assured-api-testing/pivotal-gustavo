import api.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Comment;
import entities.Project;
import entities.Story;
import org.testng.Assert;
import org.testng.annotations.*;

public class CommentTest extends ProjectDefault {
    private String idStoryReference;
    private String idCommentToTest;

    public IBuilderApiResponse baseRequestComment() {
        return baseRequest()
                .endpoint(ParametersDefault.END_POINT_COMMENT)
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

    @Test
    public void getAllCommentInStoryOfProject_successful_200() {
        ApiRequest apiRequest = baseRequestComment()
                .method(ApiMethod.GET).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);

    }

    @Test
    public void createCommentInProject_successful_200() throws JsonProcessingException {
        Comment comment=new Comment();
        comment.setText("Test-comment");
        ApiRequest apiRequest = baseRequestComment()
                .body(new ObjectMapper().writeValueAsString(comment))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @BeforeMethod(onlyForGroups={"verifyCommentStory","deleteComment","putComment"})
    public void createCommentToTest() throws JsonProcessingException {
        Comment comment=new Comment();
        comment.setText("Test-comment");
        ApiRequest apiRequest = baseRequestComment()
                .body(new ObjectMapper().writeValueAsString(comment))
                .method(ApiMethod.POST).build();
        idCommentToTest = ApiManager.execute(apiRequest).getBody(Comment.class).getId().toString();
    }

    @AfterMethod(onlyForGroups = {"verifyCommentStory","putComment"})
    public void deleteCommentCreated() {
        ApiRequest apiRequest = baseRequestComment()
                .endpoint(ParametersDefault.END_POINT_COMMENT_TO_MODIFY)
                .pathParams(ParametersDefault.COMMENT_ID, idCommentToTest)
                .method(ApiMethod.DELETE).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
    }

    @Test(groups = "verifyCommentStory")
    public void verifySchemaToComment() {
        ApiRequest apiRequest = baseRequestComment()
                .endpoint(ParametersDefault.END_POINT_COMMENT_TO_MODIFY)
                .pathParams(ParametersDefault.COMMENT_ID, idCommentToTest)
                .method(ApiMethod.GET).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        apiResponse.validateBodySchema("schemas/comment.json");
    }

    @Test(groups = "deleteComment")
    public void deleteComment_successful_204() {
        ApiRequest apiRequest = baseRequestComment()
                .endpoint(ParametersDefault.END_POINT_COMMENT_TO_MODIFY)
                .pathParams(ParametersDefault.COMMENT_ID, idCommentToTest)
                .method(ApiMethod.DELETE).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
    }

    @Test(groups = "putComment")
    public void putStory_changeName_200() throws JsonProcessingException {
        Comment comment=new Comment();
        comment.setText("Change-The-Comment-In-Story");

        ApiRequest apiRequest = baseRequestComment()
                .endpoint(ParametersDefault.END_POINT_COMMENT_TO_MODIFY)
                .pathParams(ParametersDefault.COMMENT_ID, idCommentToTest)
                .body(new ObjectMapper().writeValueAsString(comment))
                .method(ApiMethod.PUT).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test
    public void createComment_withTextEmpty_400() throws JsonProcessingException {
        Comment comment=new Comment();
        comment.setText("");
        ApiRequest apiRequest = baseRequestComment()
                .body(new ObjectMapper().writeValueAsString(comment))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createComment_withNameHasSpace_400() throws JsonProcessingException {
        Comment comment=new Comment();
        comment.setText(" ");
        ApiRequest apiRequest = baseRequestComment()
                .body(new ObjectMapper().writeValueAsString(comment))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }
}
