public class ApiRequestBuilder implements IBuilderApiResponse {
    private ApiRequest apiRequest;

    public ApiRequestBuilder(){
        this.apiRequest=new ApiRequest();
    }

    public ApiRequestBuilder baseUri(String baseUri){
        this.apiRequest.setBaseUri(baseUri);
        return this;
    }

    public ApiRequestBuilder endpoint(String endpoint){
        this.apiRequest.setEndpoint(endpoint);
        return this;
    }

    public ApiRequestBuilder method(Enum<ApiMethod> method){
        this.apiRequest.setMethod(method);
        return this;
    }

    public ApiRequestBuilder headers(final String header, final String value){
        this.apiRequest.addHeaders(header,value);
        return this;
    }

    public ApiRequestBuilder pathParams(final String param, final String value){
        this.apiRequest.addPathParams(param,value);
        return this;
    }

    public ApiRequest build(){
        return apiRequest;
    }
}
