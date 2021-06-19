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
import org.apache.http.HttpStatus;
import org.testng.Assert;

public class projectGetSteps {
    private ApiRequest apiRequest = new ApiRequest();
    private ApiResponse apiResponse;
    Project project = new Project();

    private String valueToken = ParametersDefault.VALUE_TOKEN;
    private String keyToken = ParametersDefault.KEY_TOKEN;
    private String baseUri = ParametersDefault.URL_BASE;


    @Before
    public void createProject() throws JsonProcessingException {
        Project projectTemp = new Project();
        projectTemp.setName("Task 2 teg");
        ApiRequest apiRequest = new ApiRequestBuilder()
                .baseUri(baseUri)
                .headers(keyToken, valueToken)
                .endpoint("projects")
                .method(ApiMethod.valueOf("POST"))
                .body(new ObjectMapper().writeValueAsString(projectTemp))
                .build();
        project = ApiManager.execute(apiRequest).getBody(Project.class);
    }

    @Given("I build {string} request")
    public void iBuildRequest(String method) {
        apiRequest.setBaseUri(baseUri);
        apiRequest.addHeaders(keyToken, valueToken);
        apiRequest.setMethod(ApiMethod.valueOf(method));
    }

    @When("I execute {string} request")
    public void iExecuteRequest(String endpoint) {
        apiRequest.setEndpoint(endpoint);
        apiRequest.addPathParams("projectId", project.getId().toString());
        apiResponse = ApiManager.execute(apiRequest);
    }

    @Then("the response status code should be {string}")
    public void theResponseStatusCodeShouldBe(String statusCode) {
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
        apiResponse.getResponse().then().log().body();
    }

    @After
    public void cleanRepository() {
        ApiRequest apiRequest = new ApiRequestBuilder()
                .baseUri(ParametersDefault.URL_BASE)
                .headers(ParametersDefault.KEY_TOKEN, ParametersDefault.VALUE_TOKEN)
                .endpoint("/projects/{projectId}")
                .pathParams("projectId", project.getId().toString())
                .method(ApiMethod.DELETE).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
    }
}

