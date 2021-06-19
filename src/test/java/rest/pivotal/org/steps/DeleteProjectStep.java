package rest.pivotal.org.steps;

import api.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Project;
import generalSetting.ParametersDefault;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.testng.CucumberOptions;
import org.apache.http.HttpStatus;
import org.testng.Assert;

@CucumberOptions( features = "src/test/resources/features/projectDelete.feature")
public class DeleteProjectStep {
    private ApiRequest apiRequest = new ApiRequest();
    private ApiResponse apiResponse;
    Project project = new Project();

    private String valueToken = ParametersDefault.VALUE_KEY;
    private String keyToken = ParametersDefault.KEY_VALUE;
    private String baseUri = ParametersDefault.URL_BASE;


    @Before
    public void createProject() throws JsonProcessingException {
        Project projectTemp = new Project();
        projectTemp.setName("Project to delete");
        ApiRequest apiRequest = new ApiRequestBuilder()
                .baseUri(baseUri)
                .headers(keyToken, valueToken)
                .endpoint("projects")
                .method(ApiMethod.POST)
                .body(new ObjectMapper().writeValueAsString(projectTemp))
                .build();
        project = ApiManager.execute(apiRequest).getBody(Project.class);
    }

    @Given("I build {string} request with ID of project")
    public void iBuildRequest(String method) {
        apiRequest.setBaseUri(baseUri);
        apiRequest.addHeaders(keyToken, valueToken);
        apiRequest.setMethod(ApiMethod.valueOf(method));
    }

    @When("I execute {string} request to be delete a project")
    public void iExecuteRequest(String endpoint) {
        apiRequest.setEndpoint(endpoint);
        apiRequest.addPathParams("projectId", project.getId().toString());
        apiResponse = ApiManager.execute(apiRequest);
    }

    @Then("the response status code should equal {string}")
    public void theResponseStatusCodeShouldBe(String statusCode) {
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NO_CONTENT);
        apiResponse.getResponse().then().log().body();
    }
}
