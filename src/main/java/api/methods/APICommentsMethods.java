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

public class APICommentsMethods implements APIDeleteMethods, APICreateMethods {
    public static final LoggerManager log = LoggerManager.getInstance();
    public static final APIManager apiManager = APIManager.getInstance();
    public static final CredentialsManager credentialsManager = CredentialsManager.getInstance();

    public static Boolean deleteById(String commentId) {
        String userRole = DomainAppEnums.UserRole.ADMINISTRATOR.getUserRole();
        Header header = APIAuthorizationMethods.getAuthHeader(userRole);
        Headers authHeaders = new Headers(header);

        String commentsByIdEndpoint = credentialsManager.getCommentsByIdEndpoint().replace("<id>", commentId);
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("force", "true");

        Response response = apiManager.delete(commentsByIdEndpoint, authHeaders, jsonAsMap);

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

        String commentsEndpoint = credentialsManager.getCommentsEndpoint();

        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("content", "Test WAPI Comment content");
        jsonAsMap.put("post", 1);

        Response response = apiManager.post(commentsEndpoint, jsonAsMap, authHeaders);

        if (response.jsonPath().getString("id") == null) {
            return null;
        } else {
            return response;
        }
    }
}
