# Reseller Motors – Spring Boot (DDD + Hexagonal + OpenAPI)

API para revenda de veículos com DDD + Arquitetura Hexagonal + OpenAPI/Swagger (springdoc).

## Rodar com Docker
```bash
docker compose up -d --build
```
## Derrubar Docker
```bash
docker compose down -v
```

- API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## Endpoints principais
- `POST   /api/vehicles`
- `PATCH  /api/vehicles/{id}`
- `POST   /api/vehicles/{id}/sell`
- `GET    /api/vehicles?status=available&page=1&pageSize=20`
- `GET    /api/vehicles?status=sold&page=1&pageSize=20`
- `GET    /api/vehicles/{id}`
- `GET    /api/vehicles/{id}/history`

## Variáveis
- `DATABASE_URL` (default: `jdbc:postgresql://localhost:5432/autos`)
- `DATABASE_USERNAME` (default: `autos`) / `DATABASE_PASSWORD` (default: `autos`)
- `APP_SEED` (seed automático) / `APP_SEED_RESET`


## OpenAPI YAML
- Arquivo está em `src/main/resources/openapi.yaml`.
- Swagger UI: `http://localhost:8080/swagger-ui`
- JSON: `http://localhost:8080/v3/api-docs`