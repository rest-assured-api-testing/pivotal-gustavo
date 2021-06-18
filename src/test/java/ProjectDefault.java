import api.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Project;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class ProjectDefault {
    protected String idProject;

    public IBuilderApiResponse baseRequest() {
        return new ApiRequestBuilder()
                .baseUri(ParametersDefault.URL_BASE)
                .headers(ParametersDefault.KEY_VALUE, ParametersDefault.VALUE_KEY);
    }

    @BeforeClass
    public void createProjectReference() throws JsonProcessingException {
        Project projectCreate = new Project();
        projectCreate.setName("Project to test");
        ApiRequest apiRequest = baseRequest()
                .endpoint("/projects")
                .method(ApiMethod.POST)
                .body(new ObjectMapper().writeValueAsString(projectCreate))
                .build();
        idProject = ApiManager.execute(apiRequest).getBody(Project.class).getId().toString();
    }

    @AfterClass
    public void deleteProjectReference() {
        ApiRequest apiRequest = baseRequest()
                .endpoint("projects/{projectId}")
                .pathParams("projectId", idProject)
                .method(ApiMethod.DELETE)
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
    }
}
