@CommentsCrud @Comments @Regression
Feature: Comments

  @GetAllComments @GetAllCommentsCrud @Smoke
  Scenario Outline: A user with proper role should be able to retrieve all comments
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve all comments
    Then response should be "<Status Line>"
    And response should be valid and have a body
    And response should have proper amount of comments

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |
      | subscriber    | HTTP/1.1 200 OK |

  @CreateAComment @CreateACommentCrud @Smoke
  Scenario Outline: A user with proper role should be able to create a comment
    Given I am authorized with a user with "<User Role>" role
    When I make a request to create a comment with the following query params
      | content                   | post |
      | Test WAPI Comment Content | 1    |
    Then response should be "<Status Line>"
    And response should be valid and have a body
    And comment content should be correct
    And comment post id should be correct
    And comment status should be correct

    Examples:
      | User Role     | Status Line          |
      | administrator | HTTP/1.1 201 Created |

  @RetrieveAComment @RetrieveACommentCrud @Smoke
  Scenario Outline: A user with proper role should be able to retrieve a comment
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve a comment
    Then response should be "<Status Line>"
    And response should be valid and have a body
    And proper comment id should be returned
    And comment content should be correct
    And comment post id should be correct
    And comment status should be correct

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |
      | subscriber    | HTTP/1.1 200 OK |

  @UpdateAComment @UpdateACommentCrud @Smoke
  Scenario Outline: A user with proper role should be able to update a comment
    Given I am authorized with a user with "<User Role>" role
    When I make a request to update a comment with the following query params
      | content                             | post |
      | Test WAPI Comment Content UPDATED!! | 1    |
    Then response should be "<Status Line>"
    And response should be valid and have a body
    And proper comment id should be returned
    And comment content should be correct
    And comment post id should be correct
    And comment status should be correct

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |

  @DeleteAComment @DeleteACommentCrud @Smoke
  Scenario Outline: A user with proper role should be able to delete a comment
    Given I am authorized with a user with "<User Role>" role
    When I make a request to delete a comment
    Then response should be "<Status Line>"
    And response should be valid and have a body
    And comment should be deleted
    And proper comment id should be returned

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |