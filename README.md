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

| Propriedade | Valor |
|-------------|--------|
| Host | localhost |
| Porta | 5432 |
| Banco | camara_votacao |
| Usuário | camara |
| Senha | camara123 |

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

(dependendo da versão do SpringDoc utilizada)

---

## Decisões de arquitetura

- Utilização da OpenAPI Generator: 
- Chaves no Banco de dados: Pensando e uma aplicação distribuida e de alta performance foi optado por usar um UUID ao inves de um serial pois permite geração descentralizada