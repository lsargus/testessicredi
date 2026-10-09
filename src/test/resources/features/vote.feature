Feature: Vote

  Background:

    Given estou autenticado como administrador
    When envio uma requisição para criar uma pauta
    And abro a pauta

  Scenario: Associado não pode votar duas vezes

    When envio uma requisição para criar uma pauta
    And abro a pauta
    And registrei um voto
    Then o status da resposta deve ser 201

    When registrei um voto
    Then o status da resposta deve ser 409