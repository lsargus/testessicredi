# Câmara de Votação API

API REST desenvolvida em **Java 25** utilizando **Spring Boot** para gerenciamento de pautas, sessões e votação em uma câmara legislativa.

Este projeto foi desenvolvido como parte de um teste técnico, com foco em boas práticas de arquitetura, segurança, documentação e qualidade de código.

---

## Tecnologias

- Java 25
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA
- PostgreSQL
- Flyway
- Maven
- Lombok
- MapStruct
- OpenAPI (Swagger)
- JUnit 5
- Cucumber
- Testcontainers

---

## Requisitos

- Java 25
- Maven 3.9+
- Docker
- Docker Compose

---

## Executando o banco de dados

Na raiz do projeto execute:

```bash
docker compose up -d
```

O PostgreSQL ficará disponível em:

| Propriedade | Valor          |
|-------------|----------------|
| Host        | localhost      |
| Porta       | 5432           |
| Banco       | camara_votacao |
| Usuário     | camara         |
| Senha       | camara123      |

---

## Executando a aplicação

```bash
mvn spring-boot:run
```

ou

```bash
mvn clean install
java -jar target/*.jar
```

---

## Migrações do banco

O gerenciamento do banco é realizado pelo **Flyway**.

Todas as migrações ficam em:

```text
src/main/resources/db/migration
```

As migrations são executadas automaticamente na inicialização da aplicação.

---

## Testes

Executar todos os testes:

```bash
mvn test
```

---

## Documentação da API

Após iniciar a aplicação:

- Swagger UI

```text
http://localhost:8080/swagger-ui.html
```

ou

```text
http://localhost:8080/swagger-ui/index.html
```

---

## Decisões de arquitetura

## Decisões de arquitetura

### Autenticação com JWT

Embora o enunciado permita assumir que os usuários estejam autenticados, foi implementado um mecanismo simplificado de autenticação utilizando JWT (JSON Web Token).

Essa abordagem permite identificar de forma confiável o usuário responsável por cada voto, associando a operação ao seu identificador único. Também permite simular um cenário mais próximo de uma aplicação real.

### Contrato de API com OpenAPI Generator

Foi adotada a especificação OpenAPI como contrato da API, utilizando o OpenAPI Generator para gerar as interfaces e os modelos necessários à implementação.

Essa abordagem reduz a divergência entre a documentação e o código, favorece a padronização dos endpoints e facilita a evolução e a integração dos consumidores da API.

A especificação funciona como referência central do contrato, sendo necessário mantê-la atualizada junto às alterações funcionais.

### Identificadores UUID

Foi adotado UUID para identificar usuários e registros de votação, enquanto as pautas utilizam identificadores numéricos com geração por sequência no banco de dados.

O UUID permite gerar identificadores únicos sem depender de uma sequência centralizada, característica útil em cenários distribuídos. Para as pautas, a sequência oferece uma estratégia simples de geração de identificadores.

A escolha considera as necessidades específicas de cada entidade, sem impor um único formato de identificador a todo o sistema.

### Estratégia de testes

Foi adotada uma estratégia de testes em camadas, combinando testes de integração com Cucumber e testes unitários com JUnit e Mockito.

Os testes de integração cobrem os principais fluxos de negócio por meio de requisições HTTP reais, incluindo autenticação, persistência e processamento da votação.

Os testes unitários verificam regras específicas do domínio e comportamentos de serviços, permitindo cobrir cenários de sucesso e falha sem depender de infraestrutura externa.

Essa combinação busca equilibrar confiança, velocidade de execução e custo de manutenção da suíte de testes.

### Persistência e integridade dos dados

Foi adotado PostgreSQL como banco de dados relacional, utilizando Spring Data JPA para persistência das entidades.

As migrations são gerenciadas pelo Flyway, permitindo versionar a estrutura do banco de dados e reproduzir sua evolução em diferentes ambientes.

Restrições de integridade no banco complementam as validações da aplicação, protegendo a consistência dos dados mesmo quando existem múltiplas requisições concorrentes.

## Alta volumetria de votos

Para um cenário de alta volumetria, consideraria evoluir a solução para uma arquitetura distribuída, utilizando um sistema de mensageria (Apache Kafka, RabitMQ) para desacoplar o recebimento dos votos de seu processamento e permitir o escalonamento horizontal dos consumidores.

A API seria responsável por validar a autenticação, as condições da votação e a elegibilidade do voto. O processamento assíncrono permitiria absorver picos de requisições e distribuir a carga entre diferentes consumidores.

Para garantir a integridade dos dados, utilizaria restrições no banco de dados para impedir votos duplicados, além de mecanismos de idempotência para tratar possíveis reprocessamentos de mensagens. Também avaliaria o padrão Transactional Outbox para garantir a consistência entre a persistência dos votos e a publicação dos eventos.

O encerramento da votação exigiria um mecanismo de coordenação que impedisse a aceitação de novos votos após o fim do período e garantisse o processamento de todos os votos elegíveis antes da apuração. Não dependeria apenas do esvaziamento da fila Kafka, mas de um controle explícito dos votos aceitos e processados.

Por fim, a apuração seria realizada após a confirmação de que todos os votos elegíveis foram processados. A arquitetura seria dimensionada a partir de métricas de throughput, latência, tamanho das filas e tempo de processamento, permitindo ajustar o número de consumidores conforme a demanda.

## Estratégia de versionamento
Adotaria uma estratégia que contemplasse o versionamento da API, do código-fonte e do banco de dados.

Para a API, utilizaria versionamento explícito na URL, como /api/v1, mantendo a compatibilidade dos contratos existentes sempre que possível. Mudanças incompatíveis seriam introduzidas em uma nova versão, permitindo que os consumidores migrem de forma controlada. A especificação OpenAPI seria mantida como contrato de integração de cada versão.

Para o código-fonte, utilizaria Git, com branch principal protegida, pull requests, revisão de código e execução automatizada dos testes no pipeline de CI/CD. Para as entregas, adotaria versionamento semântico, diferenciando mudanças incompatíveis, novas funcionalidades compatíveis e correções.

Para o banco de dados, utilizaria Flyway para versionar e aplicar migrations de forma controlada. Em ambientes distribuídos, priorizaria alterações retrocompatíveis.
