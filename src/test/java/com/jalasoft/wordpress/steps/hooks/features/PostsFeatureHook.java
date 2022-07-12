package com.jalasoft.wordpress.steps.hooks.features;

import api.methods.APICommentsMethods;
import api.methods.APIPostsMethods;
import api.http.HttpResponse;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.Objects;

public class PostsFeatureHook {
    private final HttpResponse response;
    private String id = null;

    public PostsFeatureHook(HttpResponse response) {
        this.response = response;
    }

    @Before("@RetrieveAPost or @UpdateAPost or @DeleteAPost")
    public void beforeRetrieveAPostFeature() {
        Response requestResponse = APIPostsMethods.create();

        if (Objects.nonNull(requestResponse)) {
            response.setResponse(requestResponse);
            id = response.getResponse().jsonPath().getString("id");
        } else {
            Assert.fail("post was not created");
        }
    }

    @After("@CreateAPost or @RetrieveAPost or @UpdateAPost or @DeleteAPost")
    public void afterCreateAPostFeature() {
        if (id != null) {
            Boolean deleted = APIPostsMethods.deleteById(id);
            Assert.assertTrue(deleted, "post was not deleted");
        } else if (Objects.nonNull(response.getResponse().jsonPath().getString("id"))) {
            String id = response.getResponse().jsonPath().getString("id");
            Boolean deleted = APIPostsMethods.deleteById(id);

            Assert.assertTrue(deleted, "post was not deleted");
        }
    }
}
