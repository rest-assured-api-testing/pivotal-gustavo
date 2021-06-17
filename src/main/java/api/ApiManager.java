package api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ApiManager {
    private static int status;

    public static ApiResponse execute(ApiRequest apiRequest) {
        Response response = buildRequest(apiRequest)
                .request(apiRequest.getMethod().name(),
                        apiRequest.getEndpoint());
        return new ApiResponse(response);
    }

    private static RequestSpecification buildRequest(ApiRequest apiRequest) {
        return given().headers(apiRequest.getHeaders())
                .queryParams(apiRequest.getQueryParams())
                .pathParams(apiRequest.getPathParams())
                .baseUri(apiRequest.getBaseUri())
                .body(apiRequest.getBody())
                .contentType(ContentType.JSON)
                .log().all();
    }
}
