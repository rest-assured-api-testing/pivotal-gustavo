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

public class projectPutSteps {
    private ApiRequest apiRequest = new ApiRequest();
    private ApiResponse apiResponseStory;
    private Project project = new Project();

    @Before
    public void createProject() throws JsonProcessingException {
        Project projectTemp = new Project();
        projectTemp.setName("Project to test PUT ");
        ApiRequest apiRequest = new ApiRequestBuilder()
                .baseUri(ParametersDefault.URL_BASE)
                .headers(ParametersDefault.KEY_TOKEN, ParametersDefault.VALUE_TOKEN)
                .endpoint(ParametersDefault.END_POINT_PROJECT)
                .method(ApiMethod.POST)
                .body(new ObjectMapper().writeValueAsString(projectTemp))
                .build();
        project = ApiManager.execute(apiRequest).getBody(Project.class);
    }

    @Given("I build {string} request to change name to {string}")
    public void iBuildRequest(String method,String newName) throws JsonProcessingException {
        Project ProjectNewName = new Project();
        ProjectNewName.setName(newName);
        apiRequest.setBody(new ObjectMapper().writeValueAsString(ProjectNewName));
        apiRequest.setBaseUri(ParametersDefault.URL_BASE);
        apiRequest.addHeaders(ParametersDefault.KEY_TOKEN, ParametersDefault.VALUE_TOKEN);
        apiRequest.setMethod(ApiMethod.valueOf(method));
    }

    @When("I execute {string} request to be put a new name in a project")
    public void iExecuteRequest(String endpoint){
        apiRequest.setEndpoint(endpoint);
        apiRequest.addPathParams(ParametersDefault.PROJECT_ID, project.getId().toString());
        apiResponseStory = ApiManager.execute(apiRequest);
    }

    @Then("The response status code should be successful {string} and project has new name")
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
