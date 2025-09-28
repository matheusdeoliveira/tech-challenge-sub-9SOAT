# Reseller Motors – API de Revenda de Veículos

API REST para revenda de veículos construída com Spring Boot 3, seguindo DDD e Arquitetura Hexagonal, documentada com OpenAPI/Swagger. O objetivo é oferecer endpoints para cadastrar, editar, consultar, vender veículos e acompanhar o histórico de mudanças.

- API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## Sumário
- O que é o projeto
- Como foi implementado (Arquitetura)
- Como rodar localmente (com e sem Docker)
- Como testar
- Endpoints principais (exemplos)
- Variáveis de ambiente
- OpenAPI

---

## O que é o projeto
Um serviço HTTP para gerenciamento de veículos de uma revenda, contemplando:
- Cadastro e edição de veículos
- Venda de veículo (com validações)
- Consulta paginada por status (disponível/vendido)
- Consulta por ID e histórico de alterações

A modelagem utiliza um domínio rico (entidades, VOs e casos de uso) e separa claramente as regras de negócio das camadas externas (HTTP e persistência).

## Como foi implementado (Arquitetura)
- Linguagem/Runtime: Java 21
- Framework: Spring Boot 3.5
- Padrões: DDD + Arquitetura Hexagonal (Ports & Adapters)
- Persistência: Spring Data JPA + PostgreSQL
- Documentação: Springdoc OpenAPI (Swagger UI)
- Seed de dados: opcional via variáveis `APP_SEED` e `APP_SEED_RESET`

Estrutura (alto nível):
- domain: entidades (Vehicle, VehicleHistory), VOs (CPF), e regras de negócio.
- application: casos de uso (Register/Edit/Sell/List/Get Vehicle[s]).
- infrastructure: adapters HTTP (controller, DTOs), persistência (JPA + mappers), config e seed.

Arquitetura Hexagonal:
- Ports (interfaces) no domínio (`VehicleRepositoryPort`, `VehicleHistoryRepositoryPort`).
- Adapters na infraestrutura (`VehicleRepositoryAdapter`, `VehicleHistoryRepositoryAdapter`).
- Casos de uso orquestram operações, isolando o domínio de detalhes técnicos.

Principais referências no código:
- Controller: `infrastructure/http/VehiclesController.java`
- Casos de Uso: `application/usecases/*`
- Domínio: `domain/model/*`, `domain/vo/*`
- Persistência: `infrastructure/persistence/*` (JPA + mappers)
- OpenAPI: `src/main/resources/openapi.yaml`

---

## Como rodar localmente

Pré-requisitos (sem Docker):
- Java 21 instalado
- PostgreSQL acessível (local ou remoto)

Variáveis com defaults (em `application.yml`):
- `DATABASE_URL`: jdbc:postgresql://localhost:5432/autos
- `DATABASE_USERNAME`: autos
- `DATABASE_PASSWORD`: autos
- `APP_SEED`: false (defina true para popular dados)
- `APP_SEED_RESET`: false (defina true para resetar antes de popular)

### Opção A) Usando Docker (recomendado)
1. Suba tudo com Docker Compose:
   ```bash
   docker compose up -d --build
   ```
2. Para remover:
   ```bash
   docker compose down -v
   ```
3. Acesse:
   - API: http://localhost:8080
   - Swagger UI: http://localhost:8080/swagger-ui

O Compose levanta um PostgreSQL com credenciais padrão e inicia a aplicação com `APP_SEED=true` (por padrão neste repositório), facilitando testes locais.

### Opção B) Sem Docker (rodando com Gradle)
1. Garanta um PostgreSQL rodando e crie o banco `autos` (ou ajuste a URL).
2. Exporte variáveis se necessário (exemplos no PowerShell):
   ```powershell
   $env:DATABASE_URL = "jdbc:postgresql://localhost:5432/autos"
   $env:DATABASE_USERNAME = "autos"
   $env:DATABASE_PASSWORD = "autos"
   $env:APP_SEED = "true"        # opcional
   $env:APP_SEED_RESET = "false"  # opcional
   ```
3. Rode a aplicação:
   ```bash
   ./gradlew bootRun
   ```
4. Acesse a documentação em http://localhost:8080/swagger-ui

### Build de artefato (jar)
```bash
./gradlew clean build
java -jar build/libs/*.jar
```

---

## Como testar
- Testes unitários/integrados:
  ```bash
  ./gradlew test
  ```
- Para ver logs de debug de testes, adapte/adicione prints conforme necessário.

Dicas:
- Se estiver usando Docker, os testes usam o driver do PostgreSQL runtime apenas se você subir a app; para testes de unidade padrões, o banco não é necessário.

---

## Endpoints principais (exemplos)
- `POST   /api/vehicles`
- `PATCH  /api/vehicles/{id}`
- `POST   /api/vehicles/{id}/sell`
- `GET    /api/vehicles?status=available&page=1&pageSize=20`
- `GET    /api/vehicles?status=sold&page=1&pageSize=20`
- `GET    /api/vehicles/{id}`
- `GET    /api/vehicles/{id}/history`

Exemplo cURL (criar veículo):
```bash
curl -X POST http://localhost:8080/api/vehicles \
  -H "Content-Type: application/json" \
  -d '{
        "make": "Fiat",
        "model": "Pulse",
        "year": 2023,
        "price": 89990.0,
        "ownerCpf": "12345678901"
      }'
```

---

## Variáveis de ambiente
- `DATABASE_URL` (default: `jdbc:postgresql://localhost:5432/autos`)
- `DATABASE_USERNAME` (default: `autos`)
- `DATABASE_PASSWORD` (default: `autos`)
- `APP_SEED` (habilita seed) / `APP_SEED_RESET` (reseta dados antes de semear)

---

## OpenAPI
- Arquivo: `src/main/resources/openapi.yaml`
- Swagger UI: `http://localhost:8080/swagger-ui`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

---

## Solução de problemas (FAQ)
- Porta 8080 ocupada: pare o serviço que usa a porta ou altere `server.port`.
- Erro de conexão no banco: valide `DATABASE_URL`, usuário/senha e se o Postgres está aceitando conexões.
- Java incompatível: confirme se está usando Java 21 (veja `build.gradle`).