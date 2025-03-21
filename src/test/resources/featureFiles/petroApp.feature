Feature: Test

  Scenario: Test
    Given User Authenticate with valid Username "user1" and Password "password123" get the sessionID
    And User uses the sessionID to Fetch the Car Details
    And Verify the response status code 200
    Then Validate on the response body "fetchCarsResponseUser1"

  Scenario: Test 2
    Given User Authenticate with valid Username "user2" and Password "password456" get the sessionID
    And User uses the sessionID to Fetch the Car Details
    And Verify the response status code 200
    Then Validate on the response body "fetchCarsResponseUser2"

  Scenario: Test 3
    Given User Authenticate with valid Username "admin" and Password "adminpass" get the sessionID
    And User uses the sessionID to Fetch the Car Details
    And Verify the response status code 200
    Then Validate on the response body "fetchCarsResponseAdmin"

  Scenario Outline: Test 4
    Given User enters Invalid Endpoint "<EndPoint>" to Authenticate
    And Verify the response status code 200
    Then Validate on the response body "fetchCarsResponseWrongEndpoint"
    Examples:
      | EndPoint                |
      | ?endpoint=authenticatee |
      | ?endpoint=authenticates |
      | ?endpoint=authentiicate |
      | ?7amadaaaa              |

  Scenario Outline: Test 5
    Given User enters Invalid Username "<username>" and Password "<password>" to Authenticate
    And Verify the response status code 200
    Then Validate on the response body "<JsonResponse>"
    Examples:
      | username | password  | JsonResponse                               |
      | user1    | pass1234  | fetchCarsResponseWrongUsernameOrPassword   |
      | user2    | pass4567  | fetchCarsResponseWrongUsernameOrPassword   |
      | adminss  | adminpass | fetchCarsResponseWrongUsernameOrPassword   |
      | 7amadaaa | 7amadaaa  | fetchCarsResponseWrongUsernameOrPassword   |
      | user1    |           | fetchCarsResponseMissingUserNameOrPassword |
      |          | pass1234  | fetchCarsResponseMissingUserNameOrPassword |

  Scenario Outline: Test 6
    Given User enters Invalid sessionID "<session>"
    And Verify the response status code 200
    Then Validate on the response body "<JsonResponse>"
    Examples:
      | session                           | JsonResponse                      |
      | 123456                            | fetchCarsResponseWrongSessionID   |
      | 098f6bcd4621d373cade4e832627b4f6s | fetchCarsResponseWrongSessionID   |
      | teeeeeeeeeeeeeeeeet               | fetchCarsResponseWrongSessionID   |
      |                                   | fetchCarsResponseMissingSessionID |
