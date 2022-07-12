package com.jalasoft.wordpress.steps;

import api.APIManager;
import api.http.HttpHeaders;
import api.http.HttpResponse;
import framework.CredentialsManager;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentsSteps {
    private static final CredentialsManager credentialsManager = CredentialsManager.getInstance();
    private static  final APIManager apiManager = APIManager.getInstance();
    private final HttpHeaders headers;
    private final HttpResponse response;
    private Map<String, Object> queryParams;

    public CommentsSteps(HttpHeaders headers, HttpResponse response) {
        this.headers = headers;
        this.response = response;
    }

    @Given("^(?:I make|the user makes) a request to retrieve all comments")
    public void getAllComments() {
        String commentsEndpoint = credentialsManager.getCommentsEndpoint();
        Response requestResponse = apiManager.get(commentsEndpoint, headers.getHeaders());
        response.setResponse(requestResponse);
    }
    @Given("^I make a request to create a comment with the following query params$")
    public void createAComment(DataTable table) {
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        queryParams = queryParamsList.get(0);

        String commentsEndpoint = credentialsManager.getCommentsEndpoint();

        Response requestResponse = apiManager.post(commentsEndpoint, queryParams, headers.getHeaders());
        response.setResponse(requestResponse);
    }
    @Given("^I make a request to retrieve a comment")
    public void getCommentById() {
        String id = response.getResponse().jsonPath().getString("id");
        String content = response.getResponse().jsonPath().getString("content.rendered");
        String post = response.getResponse().jsonPath().getString("post");
        String status = response.getResponse().jsonPath().getString("status");

        queryParams = new HashMap<>();
        queryParams.put("id", id);
        queryParams.put("content", content);
        queryParams.put("post", post);
        queryParams.put("status", status);

        String commentByIdEndpoint = credentialsManager.getCommentsByIdEndpoint().replace("<id>", id);
        Headers authHeaders = headers.getHeaders();

        Response requestResponse = apiManager.get(commentByIdEndpoint, authHeaders);
        response.setResponse(requestResponse);
    }
    @Given("^I make a request to update a comment with the following query params$")
    public void updateCommentById(DataTable table) {
        String id = response.getResponse().jsonPath().getString("id");

        queryParams = new HashMap<>();
        queryParams.put("id", id);

        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        queryParams.putAll(queryParamsList.get(0));

        String commentByIdEndpoint = credentialsManager.getCommentsByIdEndpoint().replace("<id>", id);
        Headers authHeaders = headers.getHeaders();

        Response requestResponse = apiManager.put(commentByIdEndpoint, queryParamsList.get(0), authHeaders);
        response.setResponse(requestResponse);
    }
    @Given("^I make a request to delete a comment$")
    public void deleteCommentById() {
        String id = response.getResponse().jsonPath().getString("id");

        queryParams = new HashMap<>();
        queryParams.put("id", id);
        queryParams.put("status", "trash");

        String commentByIdEndpoint = credentialsManager.getCommentsByIdEndpoint().replace("<id>", id);
        Headers authHeaders = headers.getHeaders();

        Response requestResponse = apiManager.delete(commentByIdEndpoint, authHeaders);
        response.setResponse(requestResponse);
    }
    @Given("^I make a request to create a comment without parameters$")
    public void createACommentWithoutParameters() {
        String commentsEndpoint = credentialsManager.getCommentsEndpoint();

        Response requestResponse = apiManager.post(commentsEndpoint, headers.getHeaders());
        response.setResponse(requestResponse);
    }
    @Given("^With the following invalid id I make a request to retrieve a comment$")
    public void getCommentByInvalidId(DataTable table) {
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        queryParams = queryParamsList.get(0);

        String id = queryParams.get("id").toString();

        String commentsByIdEndpoint = credentialsManager.getCommentsByIdEndpoint().replace("<id>", id);
        Headers authHeaders = headers.getHeaders();

        Response requestResponse = apiManager.get(commentsByIdEndpoint, authHeaders);
        response.setResponse(requestResponse);
    }
    @Given("^I make a request to update a invalid comment with the following query params$")
    public void updateACommentByInvalidId(DataTable table) {
        List<Map<String, Object>> queryParamsListId = table.asMaps(String.class, Object.class);
        String id = queryParamsListId.get(0).get("id").toString();

        List<Map<String, Object>> queryParamsList = table.subTable(0,1).asMaps(String.class, Object.class);

        String commentByIdEndpoint = credentialsManager.getCommentsByIdEndpoint().replace("<id>", id);
        Headers authHeaders = headers.getHeaders();

        Response requestResponse = apiManager.put(commentByIdEndpoint, queryParamsList.get(0), authHeaders);
        response.setResponse(requestResponse);
    }
    @Given("^I make a request to delete a comment with the following invalid id$")
    public void deleteCommentByInvalidId(DataTable table) {
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        queryParams = queryParamsList.get(0);

        String id = queryParams.get("id").toString();

        String commentByIdEndpoint = credentialsManager.getCommentsByIdEndpoint().replace("<id>", id);
        Headers authHeaders = headers.getHeaders();

        Response requestResponse = apiManager.delete(commentByIdEndpoint, authHeaders);
        response.setResponse(requestResponse);
    }

    @Then("^response should have proper amount of comments$")
    public void verifyCommentsAmount() {
        int expectAmountOfComments = Integer.parseInt(response.getResponse().getHeaders().getValue("X-WP-Total"));
        Assert.assertEquals(response.getResponse().jsonPath().getList("$").size(), expectAmountOfComments, "wrong amount of posts returned");
    }
    @Then("^comment content should be correct$")
    public void verifyContent() {
        String expectedContent = null;
        if (queryParams.get("content").toString().contains("<p>")) {
            expectedContent = queryParams.get("content").toString();
        } else {
            expectedContent = "<p>" + queryParams.get("content") + "</p>\n";
        }
        Assert.assertEquals(response.getResponse().jsonPath().getString("content.rendered"), expectedContent, "wrong content value returned");
    }
    @Then("^comment post id should be correct$")
    public void verifyPostId() {
        Assert.assertEquals(response.getResponse().jsonPath().getString("post"), queryParams.get("post"), "wrong post id value returned");
    }
    @Then("^comment status should be correct$")
    public void verifyStatus() {
        Assert.assertEquals(response.getResponse().jsonPath().getString("status"), "approved", "wrong comment id value returned");
    }
    @Then("^proper comment id should be returned$")
    public void verifyCommentId() {
        Assert.assertEquals(response.getResponse().jsonPath().getString("id"), queryParams.get("id"), "wrong id value returned");
    }
    @Then("^comment should be deleted$")
    public void verifyCommentIsDeleted() {
        Assert.assertEquals(response.getResponse().jsonPath().getString("status"), queryParams.get("status"), "comment was not deleted");
    }

    @Then("^response of unauthorized body should have the following keys and values$")
    public void verifyCommentUnauthorizedBody(DataTable table) {
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        queryParams = queryParamsList.get(0);

        Assert.assertEquals(response.getResponse().jsonPath().get("status"), queryParams.get("status"), "wrong status value returned");
        Assert.assertEquals(response.getResponse().jsonPath().get("error"), queryParams.get("error"), "wrong error value returned");
        Assert.assertEquals(response.getResponse().jsonPath().get("code"), queryParams.get("code"), "wrong code value returned");
    }
    @Then("^response of Forbidden or Not Found body should have the following keys and values$")
    public void verifyCommentForbiddenNotFoundBody(DataTable table) {
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        queryParams = queryParamsList.get(0);

        Assert.assertEquals(response.getResponse().jsonPath().get("code"), queryParams.get("code"), "wrong status value returned");
        Assert.assertEquals(response.getResponse().jsonPath().get("message"), queryParams.get("message"), "wrong error value returned");
        Assert.assertEquals(response.getResponse().jsonPath().get("data.status").toString(), queryParams.get("status"), "wrong code value returned");
    }
}
