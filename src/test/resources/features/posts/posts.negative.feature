@Posts @Negative @Regression
Feature: Posts Negative Tests

  @GetAllPosts
  Scenario Outline: A user without authorization should not be able to retrieve all posts
    Given I am using a token with "<Token Value>"
    When I make a request to retrieve all posts
    Then response should be "<Status Line>"
    And response should be invalid and have a body with the following keys and values
      | status    | error   | code   |  error_description  |
      | <Status>  | <Error> | <Code> | <Error description> |

    Examples:
      | Token Value           | Status Line               | Status | Error             | Code | Error description         |
      | invalid JWT signature | HTTP/1.1 401 Unauthorized | error  | INVALID_SIGNATURE | 401  | JWT Signature is invalid. |
      | incorrect JWT format  | HTTP/1.1 401 Unauthorized | error  | SEGMENT_FAULT     | 401  | Incorrect JWT Format.     |

  @CreateAPost
  Scenario Outline: A user without authorization should not be able to create a post
    Given I am using a token with "<Token Value>"
    When I make a request to create a post with the following query params
      | content                | title           | excerpt           |
      | Test WAPI Post Content | Test WAPI Title | Test WAPI Excerpt |
    Then response should be "<Status Line>"
    And response should be invalid and have a body with the following keys and values
      | status    | error   | code   |  error_description  |
      | <Status>  | <Error> | <Code> | <Error description> |

    Examples:
      | Token Value           | Status Line               | Status | Error             | Code | Error description         |
      | invalid JWT signature | HTTP/1.1 401 Unauthorized | error  | INVALID_SIGNATURE | 401  | JWT Signature is invalid. |
      | incorrect JWT format  | HTTP/1.1 401 Unauthorized | error  | SEGMENT_FAULT     | 401  | Incorrect JWT Format.     |

  @RetrieveAPost
  Scenario Outline: A user without authorization should not be able to retrieve a post
    Given I am using a token with "<Token Value>"
    When I make a request to retrieve a post
    Then response should be "<Status Line>"
    And response should be invalid and have a body with the following keys and values
      | status    | error   | code   |  error_description  |
      | <Status>  | <Error> | <Code> | <Error description> |

    Examples:
      | Token Value           | Status Line               | Status | Error             | Code | Error description         |
      | invalid JWT signature | HTTP/1.1 401 Unauthorized | error  | INVALID_SIGNATURE | 401  | JWT Signature is invalid. |
      | incorrect JWT format  | HTTP/1.1 401 Unauthorized | error  | SEGMENT_FAULT     | 401  | Incorrect JWT Format.     |

  @UpdateAPost
  Scenario Outline: A user without authorization should not be able to update a post
    Given I am using a token with "<Token Value>"
    When I make a request to update a post with the following query params
      | content                        | title                   | excerpt                   |
      | Test WAPI Post Content Updated | Test WAPI Title Updated | Test WAPI Excerpt Updated |
    Then response should be "<Status Line>"
    And response should be invalid and have a body with the following keys and values
      | status    | error   | code   |  error_description  |
      | <Status>  | <Error> | <Code> | <Error description> |

    Examples:
      | Token Value           | Status Line               | Status | Error             | Code | Error description         |
      | invalid JWT signature | HTTP/1.1 401 Unauthorized | error  | INVALID_SIGNATURE | 401  | JWT Signature is invalid. |
      | incorrect JWT format  | HTTP/1.1 401 Unauthorized | error  | SEGMENT_FAULT     | 401  | Incorrect JWT Format.     |

  @DeleteAPost
  Scenario Outline: A user without authorization should not be able to delete a post
    Given I am using a token with "<Token Value>"
    When I make a request to delete a post
    Then response should be "<Status Line>"
    And response should be invalid and have a body with the following keys and values
      | status    | error   | code   |  error_description  |
      | <Status>  | <Error> | <Code> | <Error description> |

    Examples:
      | Token Value           | Status Line               | Status | Error             | Code | Error description         |
      | invalid JWT signature | HTTP/1.1 401 Unauthorized | error  | INVALID_SIGNATURE | 401  | JWT Signature is invalid. |
      | incorrect JWT format  | HTTP/1.1 401 Unauthorized | error  | SEGMENT_FAULT     | 401  | Incorrect JWT Format.     |