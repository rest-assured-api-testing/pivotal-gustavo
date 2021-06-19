import api.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Story;
import generalSetting.ParametersDefault;
import generalSetting.ProjectDefault;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class StoryTest extends ProjectDefault {
    private String idStory;
    private Story storyTest;

    /**
     * Setup end point to get Story
     * Set up path parameters the project in which test work
     *
     * @return IBuilderApiResponse in order to permit to set that testes need
     */
    public IBuilderApiResponse baseRequestLabel() {
        return baseRequest()
                .endpoint(ParametersDefault.END_POINT_STORY)
                .pathParams(ParametersDefault.PROJECT_ID, idProject);
    }

    @BeforeMethod(onlyForGroups = {"verifySchemaStory", "deleteStory", "putStory","getStory"})
    public void createStory() throws JsonProcessingException {
        Story story = new Story();
        story.setName("Test-Story");
        ApiRequest apiRequest = baseRequestLabel()
                .body(new ObjectMapper().writeValueAsString(story))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        storyTest = apiResponse.getBody(Story.class);
        idStory = apiResponse.getBody(Story.class).getId().toString();
    }

    @AfterMethod(onlyForGroups = {"verifySchemaStory", "putStory","getStory"})
    public void deleteStoryReference() {
        ApiRequest apiRequest = baseRequestLabel()
                .endpoint(ParametersDefault.END_POINT_STORY_TO_INTERACT)
                .pathParams(ParametersDefault.STORY_ID, idStory)
                .method(ApiMethod.DELETE)
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
    }

    @Test
    public void getAllStoryAProject_successful_200() {
        ApiRequest apiRequest = baseRequestLabel()
                .method(ApiMethod.GET).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test(groups = "getStory")
    public void createDefaultThatKindIsStory_successful_200() {
        String actual = storyTest.getKind();
        String expected = "story";
        Assert.assertEquals(actual, expected);
    }

    @Test(groups = "getStory")
    public void createDefaultHasCorrectIDProject_successful_Epic() {
        String actual = storyTest.getProject_id().toString();
        String expected = idProject;
        Assert.assertEquals(actual, expected);
    }

    @Test(groups = "getStory")
    public void getEpic_successful_200() {
        ApiRequest apiRequest = baseRequestLabel()
                .endpoint(ParametersDefault.END_POINT_STORY_TO_INTERACT)
                .pathParams(ParametersDefault.STORY_ID, idStory)
                .method(ApiMethod.GET).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test
    public void createStoryInProject_successful_200() throws JsonProcessingException {
        Story story = new Story();
        story.setName("Test-Story");
        ApiRequest apiRequest = baseRequestLabel()
                .body(new ObjectMapper().writeValueAsString(story))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test(groups = "deleteStory")
    public void deleteStory_successful_204() {
        ApiRequest apiRequest = baseRequestLabel()
                .endpoint(ParametersDefault.END_POINT_STORY_TO_INTERACT)
                .pathParams(ParametersDefault.STORY_ID, idStory)
                .method(ApiMethod.DELETE).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
    }

    @Test(groups = "putStory")
    public void putStory_changeName_200() throws JsonProcessingException {
        Story story2 = new Story();
        story2.setName("Change-The-Name-Story-to-PUT");

        ApiRequest apiRequest = baseRequestLabel()
                .endpoint(ParametersDefault.END_POINT_STORY_TO_INTERACT)
                .pathParams(ParametersDefault.STORY_ID, idStory)
                .body(new ObjectMapper().writeValueAsString(story2))
                .method(ApiMethod.PUT).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test
    public void createStory_withNameEmpty_400() throws JsonProcessingException {
        Story story = new Story();
        story.setName("");
        ApiRequest apiRequest = baseRequestLabel()
                .body(new ObjectMapper().writeValueAsString(story))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createStory_withNameHasSpace_400() throws JsonProcessingException {
        Story story = new Story();
        story.setName(" ");
        ApiRequest apiRequest = baseRequestLabel()
                .body(new ObjectMapper().writeValueAsString(story))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createStory_withNullName_400() throws JsonProcessingException {
        Story story = new Story();
        story.setName(null);
        ApiRequest apiRequest = baseRequestLabel()
                .body(new ObjectMapper().writeValueAsString(story))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createStory_withWrongProject_403() throws JsonProcessingException {
        Story story = new Story();
        story.setName("Test wrong Project");
        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/2872/stories/")
                .body(new ObjectMapper().writeValueAsString(story))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 403);
    }

    @Test
    public void createStory_withWrongIdProjectNotExist_404() throws JsonProcessingException {
        Story story = new Story();
        story.setName("Test wrong Project");
        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/9ef97w419/stories/")
                .body(new ObjectMapper().writeValueAsString(story))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 404);
    }

    @Test
    public void createStory_withWrongEndpoint_404() throws JsonProcessingException {
        Story story = new Story();
        story.setName(null);
        ApiRequest apiRequest = baseRequestLabel()
                .endpoint("projects/{projectId}/storie/")
                .pathParams(ParametersDefault.PROJECT_ID, idProject)
                .body(new ObjectMapper().writeValueAsString(story))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 404);
    }
}
