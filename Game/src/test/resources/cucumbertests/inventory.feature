Feature: Inventory Feature

  Background:
    Given a player with name "Toto" joins the game

  Scenario: Check Number of Gold
    When "Toto" requests his number of gold he gets 3

  Scenario: Check Victory Points from Number of Gold
    When "Toto" gets 1 victory points from his gold

