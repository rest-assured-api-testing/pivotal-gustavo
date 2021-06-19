package rest.pivotal.org.steps;

import api.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Epic;
import entities.Project;
import generalSetting.ParametersDefault;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.HttpStatus;
import org.testng.Assert;

public class PostEpicSteps {
    private ApiRequest apiRequest = new ApiRequest();
    private ApiResponse apiResponse;
    Project project = new Project();

    private String valueToken = ParametersDefault.VALUE_KEY;
    private String keyToken = ParametersDefault.KEY_VALUE;
    private String baseUri = ParametersDefault.URL_BASE;


    @Before
    public void createProject() throws JsonProcessingException {
        Project projectTemp = new Project();
        projectTemp.setName("Project to create epic");
        ApiRequest apiRequest = new ApiRequestBuilder()
                .baseUri(baseUri)
                .headers(keyToken, valueToken)
                .endpoint(ParametersDefault.END_POINT_PROJECT)
                .method(ApiMethod.POST)
                .body(new ObjectMapper().writeValueAsString(projectTemp))
                .build();
        project = ApiManager.execute(apiRequest).getBody(Project.class);
    }

    @Given("I build {string} request with id of project to create epic with name {string}")
    public void iBuildRequest(String method, String nameEpic) throws JsonProcessingException {
        Epic epic = new Epic();
        epic.setName(nameEpic);
        apiRequest.setBody(new ObjectMapper().writeValueAsString(epic));
        apiRequest.setBaseUri(baseUri);
        apiRequest.addHeaders(keyToken, valueToken);
        apiRequest.setMethod(ApiMethod.valueOf(method));
    }

    @When("I execute {string} request to create a epic in a project")
    public void iExecuteRequest(String endpoint) {
        apiRequest.setEndpoint(endpoint);
        apiRequest.addPathParams("projectId", project.getId().toString());
        apiResponse = ApiManager.execute(apiRequest);
    }

    @Then("the response status code should be successful in order to be {string}")
    public void theResponseStatusCodeShouldBe(String statusCode) {
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
        apiResponse.getResponse().then().log().body();
    }

    @After
    public void cleanRepository() {
        ApiRequest apiRequest = new ApiRequestBuilder()
                .baseUri(ParametersDefault.URL_BASE)
                .headers(ParametersDefault.KEY_VALUE, ParametersDefault.VALUE_KEY)
                .endpoint(ParametersDefault.END_POINT_PROJECT_TO_INTERACT)
                .pathParams(ParametersDefault.PROJECT_ID, project.getId().toString())
                .method(ApiMethod.DELETE).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
    }
}
