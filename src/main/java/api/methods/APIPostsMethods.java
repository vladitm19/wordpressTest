package api.methods;

import api.APIManager;
import constants.DomainAppEnums;
import framework.CredentialsManager;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import utils.LoggerManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class APIPostsMethods {
    public static final LoggerManager log = LoggerManager.getInstance();
    public static final APIManager apiManager = APIManager.getInstance();
    public static final CredentialsManager credentialsManager = CredentialsManager.getInstance();

    public static Boolean deleteById(String postId) {
        String userRole = DomainAppEnums.UserRole.ADMINISTRATOR.getUserRole();
        Header header = APIAuthorizationMethods.getAuthHeader(userRole);
        Headers authHeaders = new Headers(header);

        String postsByIdEndpoint = credentialsManager.getPostsByIdEndpoint().replace("<id>", postId);

        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("force", "true");

        Response response = apiManager.delete(postsByIdEndpoint, authHeaders, jsonAsMap);

        if (response.jsonPath().getBoolean("deleted")) {
            return response.jsonPath().getBoolean("deleted");
        } else {
            return false;
        }
    }

    public static Response create() {
        String userRole = DomainAppEnums.UserRole.ADMINISTRATOR.getUserRole();
        Header header = APIAuthorizationMethods.getAuthHeader(userRole);
        Headers authHeaders = new Headers(header);

        String postsEndpoint = credentialsManager.getPostsEndpoint();

        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("content", "Test WAPI Post content");
        jsonAsMap.put("title", "Test WAPI Title");
        jsonAsMap.put("excerpt", "Test WAPI Excerpt");

        Response response = apiManager.post(postsEndpoint, jsonAsMap, authHeaders);

        if (response.jsonPath().getString("id") == null) {
            return null;
        } else {
            return response;
        }
    }
}
