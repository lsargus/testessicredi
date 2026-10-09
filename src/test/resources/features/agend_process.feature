Feature: Processamento de votação

  Background:
    Given estou autenticado como administrador
    And existem usuários associados para votação

  Scenario: Processar uma reunião completa

    When envio uma requisição para criar uma pauta
    Then o status da resposta deve ser 201
    And a pauta deve existir no banco
    And a openingDate deve ser nula

    When abro a pauta
    Then o status da resposta deve ser 201
    And o status da pauta deve ser "OPEN"
    And a openingDate não deve ser nula

    When registro 3 votos "SIM", "NÃO", "SIM"
    Then os 3 votos devem estar persistidos

    When envio uma requisição de login com email "admin@teste.com" e senha "admin123"

    When o período da pauta é encerrado

    When encerro a pauta
    Then o status da resposta deve ser 201
    And os votos devem ter sido processados
    And o resultado deve ser "APPROVED"
    And o status da pauta deve ser "COMPLETED"