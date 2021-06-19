import api.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BlockerTest extends ProjectDefault{
    private String idStoryReference;
    private String idBlockerReference;

    public IBuilderApiResponse baseRequestBlocker() {
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

    @Test
    public void getAllBlockerInStory_successful_200(){
        ApiRequest apiRequest = baseRequestBlocker()
                .endpoint(ParametersDefault.END_POINT_BLOCKER_IN_STORY)
                .method(ApiMethod.GET).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test
    public void createBlockerInStory_successful_200() throws JsonProcessingException {
        Blockers blockers=new Blockers();
        blockers.setDescription("Test-description-in-Blocker-to-Story");
        ApiRequest apiRequest = baseRequestBlocker()
                .endpoint(ParametersDefault.END_POINT_BLOCKER_IN_STORY)
                .body(new ObjectMapper().writeValueAsString(blockers))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @BeforeMethod(onlyForGroups = {"deleteBlocker","getBlocker","putBlocker","verifyBlockerStory"})
    public void createBlockerInStoryHowUtility() throws JsonProcessingException {
        Blockers blockers=new Blockers();
        blockers.setDescription("Test-Blocker-to-Story");
        ApiRequest apiRequest = baseRequestBlocker()
                .endpoint(ParametersDefault.END_POINT_BLOCKER_IN_STORY)
                .body(new ObjectMapper().writeValueAsString(blockers))
                .method(ApiMethod.POST).build();
        idBlockerReference = ApiManager.execute(apiRequest).getBody(Blockers.class).getId().toString();
    }

    @AfterMethod(onlyForGroups = {"getBlocker","putBlocker","verifyBlockerStory"})
    public void deleteBlockerCreated() {
        ApiRequest apiRequest = baseRequestBlocker()
                .endpoint(ParametersDefault.END_POINT_BLOCKER_TO_MODIFY_IN_STORY)
                .pathParams(ParametersDefault.BLOCKER_ID, idBlockerReference)
                .method(ApiMethod.DELETE).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
    }


    @Test(groups = "getBlocker")
    public void getBlocker_successful_200() {
        ApiRequest apiRequest = baseRequestBlocker()
                .endpoint(ParametersDefault.END_POINT_BLOCKER_TO_MODIFY_IN_STORY)
                .pathParams(ParametersDefault.BLOCKER_ID, idBlockerReference)
                .method(ApiMethod.GET).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test(groups = "deleteBlocker")
    public void deleteBlocker_successful_204() {
        ApiRequest apiRequest = baseRequestBlocker()
                .endpoint(ParametersDefault.END_POINT_BLOCKER_TO_MODIFY_IN_STORY)
                .pathParams(ParametersDefault.BLOCKER_ID, idBlockerReference)
                .method(ApiMethod.DELETE).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
    }

    @Test(groups = "putBlocker")
    public void putStory_changeName_200() throws JsonProcessingException {
        Blockers blockers=new Blockers();
        blockers.setDescription("Change-The-description-In-Blocker");

        ApiRequest apiRequest = baseRequestBlocker()
                .endpoint(ParametersDefault.END_POINT_BLOCKER_TO_MODIFY_IN_STORY)
                .pathParams(ParametersDefault.BLOCKER_ID, idBlockerReference)
                .body(new ObjectMapper().writeValueAsString(blockers))
                .method(ApiMethod.PUT).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test(groups = "verifyBlockerStory")
    public void verifySchemaToBlocker() {
        ApiRequest apiRequest = baseRequestBlocker()
                .endpoint(ParametersDefault.END_POINT_BLOCKER_TO_MODIFY_IN_STORY)
                .pathParams(ParametersDefault.BLOCKER_ID, idBlockerReference)
                .method(ApiMethod.GET).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        apiResponse.validateBodySchema("schemas/blockers.json");
    }

    @Test
    public void createBlocker_withTextEmpty_400() throws JsonProcessingException {
        Blockers blockers=new Blockers();
        blockers.setDescription("");
        ApiRequest apiRequest = baseRequestBlocker()
                .endpoint(ParametersDefault.END_POINT_BLOCKER_IN_STORY)
                .body(new ObjectMapper().writeValueAsString(blockers))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createBlocker_withNameHasSpace_400() throws JsonProcessingException {
        Blockers blockers=new Blockers();
        blockers.setDescription(" ");
        ApiRequest apiRequest = baseRequestBlocker()
                .endpoint(ParametersDefault.END_POINT_BLOCKER_IN_STORY)
                .body(new ObjectMapper().writeValueAsString(blockers))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }
}
