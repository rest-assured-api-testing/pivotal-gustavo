import io.restassured.http.Header;
import io.restassured.http.Headers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiRequest<T> {
    private String baseUri;
    private String endpoint;
    private String body;
    private Enum<ApiMethod> method;
    private List<Header> headers;
    private Map<String, String> queryParams;
    private Map<String, String> pathParams;

    public ApiRequest() {
        headers = new ArrayList<>();
        queryParams = new HashMap<>();
        pathParams = new HashMap<>();
    }

    public String getBaseUri() {
        return baseUri;
    }

    public void setBaseUri(String baseUri) {
        this.baseUri = baseUri;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getBody() {
        if (body==null) {
            return "";
        }
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Enum<ApiMethod> getMethod() {
        return method;
    }

    public void setMethod(Enum<ApiMethod> method) {
        this.method = method;
    }

    public void addHeaders(final String header, final String value) {
        headers.add(new Header(header, value));
    }

    public Headers getHeaders() {
        return new Headers(headers);
    }

    public void addQueryParams(final String param, final String value) {
        queryParams.put(param, value);
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public void addPathParams(final String param, final String value) {
        pathParams.put(param, value);
    }

    public Map<String, String> getPathParams() {
        return pathParams;
    }
}
