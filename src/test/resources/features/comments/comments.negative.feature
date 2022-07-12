@CommentsNegative @Comments @Regression
Feature: Comments

  @GetAllComments @GetAllCommentsNegative @Smoke
  Scenario Outline: A user with proper role should not be able to retrieve all comments without authentication
    Given I make a request to retrieve all comments
    Then response should be "<Status Line>"
    And response should be failure and have a body
    And response of unauthorized body should have the following keys and values
      | status    | error   | code   |
      | <Status>  | <Error> | <Code> |

    Examples:
      | Status Line               | Status | Error                        | Code |
      | HTTP/1.1 401 Unauthorized | error  | MISSING_AUTHORIZATION_HEADER | 401  |

  @CreateAComment @CreateACommentNegative @Smoke
  Scenario Outline: A user with proper role should be able to create a comment
    Given I am authorized with a user with "<User Role>" role
    When I make a request to create a comment without parameters
    Then response should be "<Status Line>"
    And response should be failure and have a body
    And response of Forbidden or Not Found body should have the following keys and values
      | code    | message   | status        |
      | <Code>  | <Message> | <Data Status> |

    Examples:
      | User Role     | Status Line            | Code                          | Message                                                           | Data Status |
      | administrator | HTTP/1.1 403 Forbidden | rest_comment_invalid_post_id  | Sorry, you are not allowed to create this comment without a post. | 403         |

  @RetrieveACommentNegative @RetrieveAComment @Smoke @Test
  Scenario Outline: A user with proper role shouldn't be able to retrieve a comment with invalid id
    Given I am authorized with a user with "<User Role>" role
    When With the following invalid id I make a request to retrieve a comment
      | id     |
      | 123456 |
    Then response should be "<Status Line>"
    And response should be failure and have a body
    And response of Forbidden or Not Found body should have the following keys and values
      | code    | message   | status        |
      | <Code>  | <Message> | <Data Status> |

    Examples:
      | User Role     | Status Line            | Code                    | Message             | Data Status |
      | administrator | HTTP/1.1 404 Not Found | rest_comment_invalid_id | Invalid comment ID. | 404         |

  @UpdateACommentNegative @UpdateAComment @Smoke @Test
  Scenario Outline: A user with proper role shouldn't be able to update a comment with invalid id
    Given I am authorized with a user with "<User Role>" role
    When I make a request to update a invalid comment with the following query params
      | id     | content                        |
      | 123456 | Test WAPI Post Content Updated |
    Then response should be "<Status Line>"
    And response should be failure and have a body
    And response of Forbidden or Not Found body should have the following keys and values
      | code    | message   | status        |
      | <Code>  | <Message> | <Data Status> |

    Examples:
      | User Role     | Status Line            | Code                    | Message             | Data Status |
      | administrator | HTTP/1.1 404 Not Found | rest_comment_invalid_id | Invalid comment ID. | 404         |

  @DeleteACommentNegative @DeleteAComment @Smoke @Test
  Scenario Outline: A user with proper role shouldn't be able to delete a comment with invalid id
    Given I am authorized with a user with "<User Role>" role
    When I make a request to delete a comment with the following invalid id
      | id     |
      | 123456 |
    Then response should be "<Status Line>"
    And response should be failure and have a body
    And response of Forbidden or Not Found body should have the following keys and values
      | code    | message   | status        |
      | <Code>  | <Message> | <Data Status> |

    Examples:
      | User Role     | Status Line            | Code                    | Message             | Data Status |
      | administrator | HTTP/1.1 404 Not Found | rest_comment_invalid_id | Invalid comment ID. | 404         |