package com.jalasoft.wordpress.steps.hooks.features;

import api.http.HttpResponse;
import api.methods.APICommentsMethods;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.Objects;

public class CommentsFeatureHook {
    private final HttpResponse response;
    private String id = null;

    public CommentsFeatureHook(HttpResponse response) {
        this.response = response;
    }

    @Before("@RetrieveAComment or @UpdateAComment or @DeleteAComment")
    public void beforeRetrieveACommentFeature() {
        Response requestResponse = APICommentsMethods.create();

        if (Objects.nonNull(requestResponse)) {
            response.setResponse(requestResponse);
            id = response.getResponse().jsonPath().getString("id");
        } else {
            Assert.fail("comment was not created");
        }
    }

    @After("@CreateAComment or @RetrieveAComment or @UpdateAComment or @DeleteAComment")
    public void afterCreateACommentFeature() {
        if (id != null) {
            Boolean deleted = APICommentsMethods.deleteById(id);
            Assert.assertTrue(deleted, "comment was not deleted");
        } else if (Objects.nonNull(response.getResponse().jsonPath().getString("id"))) {
            String id = response.getResponse().jsonPath().getString("id");
            Boolean deleted = APICommentsMethods.deleteById(id);

            Assert.assertTrue(deleted, "comment was not deleted");
        }
    }
}
