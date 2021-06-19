package rest.pivotal.org.steps;

import api.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Project;
import entities.Story;
import generalSetting.ParametersDefault;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.HttpStatus;
import org.testng.Assert;

public class StoryPostSteps {
    private ApiRequest apiRequest = new ApiRequest();
    private ApiResponse apiResponseStory;
    private Project project = new Project();

    @Before
    public void createProject() throws JsonProcessingException {
        Project projectTemp = new Project();
        projectTemp.setName("Project to create Story");
        ApiRequest apiRequest = new ApiRequestBuilder()
                .baseUri(ParametersDefault.URL_BASE)
                .headers(ParametersDefault.KEY_TOKEN, ParametersDefault.VALUE_TOKEN)
                .endpoint(ParametersDefault.END_POINT_PROJECT)
                .method(ApiMethod.POST)
                .body(new ObjectMapper().writeValueAsString(projectTemp))
                .build();
        project = ApiManager.execute(apiRequest).getBody(Project.class);
    }

    @Given("I build {string} request with ID of project with name {string}")
    public void iBuildRequestWithIDOfProjectWithName(String method, String name) throws JsonProcessingException {
        Story story = new Story();
        story.setName(name);
        apiRequest.setBody(new ObjectMapper().writeValueAsString(story));
        apiRequest.setBaseUri(ParametersDefault.URL_BASE);
        apiRequest.addHeaders(ParametersDefault.KEY_TOKEN, ParametersDefault.VALUE_TOKEN);
        apiRequest.setMethod(ApiMethod.valueOf(method));
    }

    @When("I execute {string} request to be create in a project")
    public void iExecuteRequest(String endpoint){
        apiRequest.setEndpoint(endpoint);
        apiRequest.addPathParams(ParametersDefault.PROJECT_ID, project.getId().toString());
        apiResponseStory = ApiManager.execute(apiRequest);
    }

    @Then("The response status code should be successful {string}")
    public void theResponseStatusCodeShouldBe(String statusCode) {
        Assert.assertEquals(apiResponseStory.getStatusCode(), HttpStatus.SC_OK);
        apiResponseStory.getResponse().then().log().body();
    }

    @After
    public void cleanRepository() {
        ApiRequest apiRequest = new ApiRequestBuilder()
                .baseUri(ParametersDefault.URL_BASE)
                .headers(ParametersDefault.KEY_TOKEN, ParametersDefault.VALUE_TOKEN)
                .endpoint(ParametersDefault.END_POINT_PROJECT_TO_INTERACT)
                .pathParams(ParametersDefault.PROJECT_ID, project.getId().toString())
                .method(ApiMethod.DELETE).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
    }
}
