Feature: This test script is to Verify the API functionality of the PetroApp website
  Authenticate and Fetch Car Details
  Handle errors and exceptions

  Scenario: Authenticate and Fetch Car Details for User1 and verify the response status code 200
    Given User Authenticate with valid Username "user1" and Password "password123" get the sessionID
    And User uses the sessionID to Fetch the Car Details
    And Verify the response status code 200
    Then Validate on the response body "fetchCarsResponseUser1"

  Scenario: Authenticate and Fetch Car Details for User2 and verify the response status code 200
    Given User Authenticate with valid Username "user2" and Password "password456" get the sessionID
    And User uses the sessionID to Fetch the Car Details
    And Verify the response status code 200
    Then Validate on the response body "fetchCarsResponseUser2"

  Scenario: Authenticate and Fetch Car Details for Admin and verify the response status code 200
    Given User Authenticate with valid Username "admin" and Password "adminpass" get the sessionID
    And User uses the sessionID to Fetch the Car Details
    And Verify the response status code 200
    Then Validate on the response body "fetchCarsResponseAdmin"

  Scenario Outline: Using Invalid Endpoints to Authenticate
    Given User enters Invalid Endpoint "<EndPoint>" to Authenticate
    And Verify the response status code 200
    Then Validate on the response body "wrongEndpoint"
    Examples:
      | EndPoint                |
      | ?endpoint=authenticatee |
      | ?endpoint=authenticates |
      | ?endpoint=authentiicate |
      | ?7amadaaaa              |

  Scenario Outline: Using Invalid Username and Password to Authenticate | Using missing Username and Password to Authenticate
    Given User enters Invalid Username "<username>" and Password "<password>" to Authenticate
    And Verify the response status code 200
    Then Validate on the response body "<JsonResponse>"
    Examples:
      | username | password  | JsonResponse              |
      | user1    | pass1234  | wrongUsernameOrPassword   |
      | user2    | pass4567  | wrongUsernameOrPassword   |
      | adminss  | adminpass | wrongUsernameOrPassword   |
      | 7amadaaa | 7amadaaa  | wrongUsernameOrPassword   |
      | user1    |           | missingUserNameOrPassword |
      |          | pass1234  | missingUserNameOrPassword |

  Scenario Outline: Using Invalid sessionID to Fetch Car Details | Setting missing sessionID to Fetch Car Details
    Given User enters Invalid sessionID "<session>"
    And Verify the response status code 200
    Then Validate on the response body "<JsonResponse>"
    Examples:
      | session                           | JsonResponse     |
      | 123456                            | wrongSessionID   |
      | 098f6bcd4621d373cade4e832627b4f6s | wrongSessionID   |
      | teeeeeeeeeeeeeeeeet               | wrongSessionID   |
      |                                   | missingSessionID |