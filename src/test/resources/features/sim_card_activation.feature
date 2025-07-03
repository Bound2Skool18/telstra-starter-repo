Feature: SIM Card Activation

  Scenario: Successful SIM card activation
    Given I have a SIM card with ICCID "1255789453849037777" and customer email "success@example.com"
    When I activate the SIM card
    Then the activation should be successful
    And the SIM card record with ID 1 should be active

  Scenario: Failed SIM card activation
    Given I have a SIM card with ICCID "8944500102198304826" and customer email "fail@example.com"
    When I activate the SIM card
    Then the activation should fail
    And the SIM card record with ID 2 should not be active
