Feature: Authentication

  Scenario: Administrador realiza login com sucesso

    Given existe um usuário administrador

    When envio uma requisição de login com email "admin@teste.com" e senha "admin123"

    Then o status da resposta deve ser 200

    And deve retornar um token JWT