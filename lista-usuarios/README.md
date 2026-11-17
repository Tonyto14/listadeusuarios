# Lista de Usuários - CRUD
Spring Boot 3 + Thymeleaf + ActiveJDBC + SQLite

## Requisitos
- Java 17+
- Maven 3.9+

## Executar
    mvn clean spring-boot:run

Acesse: http://localhost:8080

O arquivo `usuarios.db` é criado automaticamente na raiz do projeto,
com a tabela `users` e 3 registros de exemplo (DataInitializer).

## Observações
- O `activejdbc-instrumentation` (pom.xml) roda em `process-classes`.
  Na IDE, rode `mvn process-classes` após alterar os models; não use
  `spring-boot-devtools` (conflita com a instrumentação).
- `DbConnectionFilter` abre/fecha a conexão (Base.open/close) a cada requisição.
