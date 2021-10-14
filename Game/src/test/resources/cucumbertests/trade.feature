Feature: Trade Feature

  Background:
    Given a trade with 3 resource WOOD is created

  Scenario: We want to know the resource of the trade
    When You ask for the resource WOOD then the quantity should be 3