package api;

public interface IBuilderApiResponse {
    ApiRequest build();

    ApiRequestBuilder body(String body);

    ApiRequestBuilder method(Enum<ApiMethod> method);

    ApiRequestBuilder headers(final String header, final String value);

    ApiRequestBuilder pathParams(final String param, final String value);

    ApiRequestBuilder endpoint(String endpoint);
}
