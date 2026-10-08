Feature: Agenda

  Background:
    Given estou autenticado

  Scenario: Criar uma pauta

    When envio uma requisição para criar uma pauta

    Then o status da resposta deve ser 201

    And a pauta deve existir no banco