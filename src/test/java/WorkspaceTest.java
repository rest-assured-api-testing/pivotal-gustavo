import api.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Workspace;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class WorkspaceTest {
    private String idWorkspaceToDelete;
    private String idWorkspaceCreated;

    public IBuilderApiResponse baseRequest() {
        return new ApiRequestBuilder()
                .baseUri(ParametersDefault.URL_BASE)
                .headers(ParametersDefault.KEY_VALUE, ParametersDefault.VALUE_KEY)
                .endpoint(ParametersDefault.END_POINT_WORKSPACE);
    }

    @Test
    public void getAllWorkspaceACount_successful_200() {
        ApiRequest apiRequest = baseRequest()
                .method(ApiMethod.GET).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test(groups = "postWorkspace")
    public void createWorkspace_successful_200() throws JsonProcessingException {
        Workspace workspace=new Workspace();
        workspace.setName("Test-Workspace");
        ApiRequest apiRequest = baseRequest()
                .body(new ObjectMapper().writeValueAsString(workspace))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        idWorkspaceToDelete =apiResponse.getBody(Workspace.class).getId().toString();
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }


    @AfterMethod(onlyForGroups = "postWorkspace")
    public void deleteWorkspaceCreate() {
        ApiRequest apiRequest = baseRequest()
                .endpoint(ParametersDefault.END_POINT_WORKSPACE_TO_MODIFY)
                .pathParams(ParametersDefault.WORKSPACE_ID, idWorkspaceToDelete)
                .method(ApiMethod.DELETE).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
    }

    @BeforeMethod(onlyForGroups = {"verifySchemaWorkspace","deleteWorkspace","putWorkspace"})
    public void createWorkspaceToVerifySchemaInWorkspace() throws JsonProcessingException {
        Workspace workspace=new Workspace();
        workspace.setName("Test-Workspace");
        ApiRequest apiRequest = baseRequest()
                .body(new ObjectMapper().writeValueAsString(workspace))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        idWorkspaceCreated =apiResponse.getBody(Workspace.class).getId().toString();
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test(groups = "verifySchemaWorkspace")
    public void verifySchemaInWorkspace() {
        ApiRequest apiRequest = baseRequest()
                .endpoint(ParametersDefault.END_POINT_WORKSPACE_TO_MODIFY)
                .pathParams(ParametersDefault.WORKSPACE_ID, idWorkspaceCreated)
                .method(ApiMethod.GET).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        apiResponse.validateBodySchema("schemas/workspace.json");
    }

    @AfterMethod(onlyForGroups = {"verifySchemaWorkspace","putWorkspace"})
    public void deleteWorkspaceCreateToVerifySchemaInWorkspace() {
        ApiRequest apiRequest = baseRequest()
                .endpoint(ParametersDefault.END_POINT_WORKSPACE_TO_MODIFY)
                .pathParams(ParametersDefault.WORKSPACE_ID, idWorkspaceCreated)
                .method(ApiMethod.DELETE).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
    }

    @Test(groups = "deleteWorkspace")
    public void deleteWorkspace_successful_204() {
        ApiRequest apiRequest = baseRequest()
                .endpoint(ParametersDefault.END_POINT_WORKSPACE_TO_MODIFY)
                .pathParams(ParametersDefault.WORKSPACE_ID, idWorkspaceCreated)
                .method(ApiMethod.DELETE).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
    }

    @Test(groups = "putWorkspace")
    public void putWorkspace_changeName_400() throws JsonProcessingException {
        Workspace workspace=new Workspace();
        workspace.setName("Change-The-Name-Workspace-to-PUT");

        ApiRequest apiRequest = baseRequest()
                .endpoint(ParametersDefault.END_POINT_WORKSPACE_TO_MODIFY)
                .pathParams(ParametersDefault.WORKSPACE_ID, idWorkspaceCreated)
                .body(new ObjectMapper().writeValueAsString(workspace))
                .method(ApiMethod.PUT).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createWorkspace_withNameEmpty_400() throws JsonProcessingException {
        Workspace workspace=new Workspace();
        workspace.setName("");
        ApiRequest apiRequest = baseRequest()
                .endpoint(ParametersDefault.END_POINT_WORKSPACE)
                .body(new ObjectMapper().writeValueAsString(workspace))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test
    public void createWorkspace_withNameHasSpace_400() throws JsonProcessingException {
        Workspace workspace=new Workspace();
        workspace.setName("");
        ApiRequest apiRequest = baseRequest()
                .endpoint(ParametersDefault.END_POINT_WORKSPACE)
                .body(new ObjectMapper().writeValueAsString(workspace))
                .method(ApiMethod.POST).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

}
