# BTH3 - Product Management Service

## Architecture

- Auth Service (Practice 2): http://localhost:8080
- Product Service (Practice 3): http://localhost:8081
- Product Database: BTH3_PRODUCT
- Authentication endpoint used by Product Service: GET /auth
- Client sends: Authorization: Bearer <JWT>

## REST API

- GET /products
- GET /products/{id}
- POST /products
- PUT /products/{id}
- DELETE /products/{id}

## Before running

1. Run `database.sql` in SQL Server.
2. Edit `src/main/resources/application.properties` and set SQL Server password.
3. Start Practice 2 Auth Service on port 8080.
4. Start this Product Service on port 8081.
5. Login through Practice 2 to get a JWT.
6. For every `/products` request, set `Authorization: Bearer <JWT>`.

## Sample product JSON

```json
{
  "name": "Laptop Dell Inspiron",
  "description": "Laptop for study and programming",
  "price": 18500000.00,
  "quantity": 10
}
```
