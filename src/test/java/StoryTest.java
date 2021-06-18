import api.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class StoryTest extends ProjectDefault{
    private String idStoryToDelete;
    private String idStoryPut;
    private String idStoryToVerifySchema;

    public IBuilderApiResponse baseRequestLabel() {
        return baseRequest()
                .endpoint(ParametersDefault.END_POINT_STORY)
                .pathParams(ParametersDefault.PROJECT_ID, idProject);
    }

    @BeforeMethod(onlyForGroups = "verifySchemaStory")
    public void createStoryToVerifySchemaStory() throws JsonProcessingException {
        Story story=new Story();
        story.setName("Test-to-verify-Story-Schema");
        ApiRequest apiRequest = baseRequestLabel()
                .body(new ObjectMapper().writeValueAsString(story))
                .method(ApiMethod.POST).build();
        idStoryToVerifySchema = ApiManager.execute(apiRequest).getBody(Story.class).getId().toString();
    }

    @BeforeMethod(onlyForGroups = "deleteStory")
    public void createStoryToDelete() throws JsonProcessingException {
        Story story=new Story();
        story.setName("Test-Story-to-test-DELETE");
        ApiRequest apiRequest = baseRequestLabel()
                .body(new ObjectMapper().writeValueAsString(story))
                .method(ApiMethod.POST).build();
        idStoryToDelete = ApiManager.execute(apiRequest).getBody(Story.class).getId().toString();
    }

    @BeforeMethod(onlyForGroups = "putStory")
    public void createLabelToPut() throws JsonProcessingException {
        Story story=new Story();
        story.setName("Test-Label-to-test-PUT");
        ApiRequest apiRequest = baseRequestLabel()
                .body(new ObjectMapper().writeValueAsString(story))
                .method(ApiMethod.POST).build();
        idStoryPut = ApiManager.execute(apiRequest).getBody(Story.class).getId().toString();
    }

    @Test(groups = "verifySchemaStory")
    public void verifySchemaInStory() {
        ApiRequest apiRequest = baseRequestLabel()
                .endpoint(ParametersDefault.END_POINT_STORY_TO_MODIFY)
                .pathParams(ParametersDefault.STORY_ID, idStoryToVerifySchema)
                .method(ApiMethod.GET).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        apiResponse.validateBodySchema("schemas/story.json");
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test
    public void getAllStoryAProject_successful_200() {
        ApiRequest apiRequest = baseRequestLabel()
                .method(ApiMethod.GET).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test
    public void createStoryInProject_successful_200() throws JsonProcessingException {
        Story story=new Story();
        story.setName("Test-Story");
        ApiRequest apiRequest = baseRequestLabel()
                .body(new ObjectMapper().writeValueAsString(story))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test(groups = "deleteStory")
    public void deleteStory_successful_203() {
        ApiRequest apiRequest = baseRequestLabel()
                .endpoint(ParametersDefault.END_POINT_STORY_TO_MODIFY)
                .pathParams(ParametersDefault.STORY_ID, idStoryToDelete)
                .method(ApiMethod.DELETE).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
    }

    @Test(groups = "putStory")
    public void putStory_changeName_200() throws JsonProcessingException {
        Story story2=new Story();
        story2.setName("Change-The-Name-Story-to-PUT");

        ApiRequest apiRequest = baseRequestLabel()
                .endpoint(ParametersDefault.END_POINT_STORY_TO_MODIFY)
                .pathParams(ParametersDefault.STORY_ID, idStoryPut)
                .body(new ObjectMapper().writeValueAsString(story2))
                .method(ApiMethod.PUT).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test
    public void createStory_withNameEmpty_400() throws JsonProcessingException {
        Story story=new Story();
        story.setName("");
        ApiRequest apiRequest = baseRequestLabel()
                .body(new ObjectMapper().writeValueAsString(story))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createStory_withNameHasSpace_400() throws JsonProcessingException {
        Story story=new Story();
        story.setName(" ");
        ApiRequest apiRequest = baseRequestLabel()
                .body(new ObjectMapper().writeValueAsString(story))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }
}
