
# Gestão Beleza

Sistema de gerenciamento para estabelecimentos do setor de beleza, desenvolvido com Java, Spring Boot e PostgreSQL.

## Sobre o projeto

O Gestão Beleza é uma API REST para auxiliar salões de beleza, barbearias e outros estabelecimentos a organizar clientes, profissionais, serviços e agendamentos.

O projeto está em desenvolvimento e tem como objetivo oferecer uma solução prática de gestão, com possibilidade de expansão para uma plataforma comercial.

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- Spring Security
- PostgreSQL 17
- Flyway
- Docker e Docker Compose
- Maven

## Funcionalidades

### Clientes
- Cadastrar clientes
- Listar clientes
- Buscar clientes por ID
- Atualizar informações
- Excluir clientes

### Profissionais
- Cadastrar profissionais
- Listar profissionais
- Atualizar informações
- Excluir profissionais
- Controlar status ativo/inativo

### Serviços
- Cadastrar serviços
- Definir preços e duração
- Atualizar informações
- Excluir serviços

### Agendamentos
- Cadastrar agendamentos
- Consultar agendamentos
- Associar clientes, profissionais e serviços
- Impedir conflitos de horários
- Cancelar agendamentos
- Reutilizar horários cancelados

## Estrutura do projeto

- `controller/` — Endpoints REST
- `model/` — Entidades JPA
- `repository/` — Acesso ao banco de dados
- `config/` — Configurações de segurança
- `db/migration/` — Migrações SQL gerenciadas pelo Flyway

## Como executar

Requisitos:
- Java 21
- Docker Desktop
- Git

1. Clone o repositório.
2. Copie `compose.yaml.example` para `compose.yaml`.
3. Configure a variável `DB_PASSWORD` em um arquivo `.env`.
4. Execute `docker compose up -d`.
5. Copie `src/main/resources/application.properties.example` para `application.properties` na mesma pasta.
6. Configure a variável `DB_PASSWORD` para a execução do Spring Boot.
7. Execute `./mvnw spring-boot:run` no Linux/macOS ou `.\mvnw.cmd spring-boot:run` no Windows.

A API estará disponível em `http://localhost:8080`.

## Status do projeto

Em desenvolvimento.

Próximas melhorias:
- Autenticação e autorização de usuários
- Validação avançada de agendamentos
- Gerenciamento de estabelecimentos
- Interface web para administração
- Dashboard e relatórios

## Autor

Gabriel Pedro Francio

GitHub: https://github.com/Francio2005
