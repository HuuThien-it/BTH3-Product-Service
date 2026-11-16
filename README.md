# BTH3 - Product Management Service (SOA)

## 1. Yeu cau da trien khai

- Quan ly san pham: **Create, Read, Update, Delete** (5 REST endpoints).
- Chay **doc lap cong 8081**, khong can Auth Service / API Gateway / service khac.
- Su dung **MySQL database BTH3_PRODUCT rieng**, khong truy cap DB cua service khac.
- Cau truc: `Controller -> Service -> Repository -> MySQL`.
- Validate thong tin; loi `400` / `404`; HTTP `201` / `200` / `204` theo REST.
- Kiem thu API bang JUnit, MockMvc va H2 test database (khong can MySQL de chay unit/integration test).

## 2. Cong nghe

- JDK **21**
- Maven **3.9+**
- Spring Boot **3.5.7**
- Spring Web, Spring Data JPA, Bean Validation, MySQL Connector/J
- MySQL 8.x (Docker optional); H2 only in automated tests

## 3. Chay tren Windows / MySQL Workbench (khong can Docker)

1. Mo MySQL Workbench, chay `database.sql` de tao DB `BTH3_PRODUCT` va bang `products`.
2. Mo PowerShell tai thu muc project, dat dung user/password cua MySQL local:

   ```powershell
   $env:DB_PORT="3306"
   $env:DB_USER="root"
   $env:DB_PASSWORD="MAT_KHAU_MYSQL_CUA_BAN"
   mvn spring-boot:run
   ```

3. Khi log hien `Tomcat started on port 8081`, test `GET http://localhost:8081/products`.
4. Dung `api-tests.http` (VS Code REST Client) hoac Postman gui 5 request.

**Khong dua mat khau MySQL that vao repository.** `application.properties` doc password tu bien moi truong.

## 4. Cach khac: chay MySQL rieng bang Docker

```powershell
docker compose up -d
$env:DB_PORT="3307"
$env:DB_USER="product_user"
$env:DB_PASSWORD="product_pass"
mvn spring-boot:run
```

- Docker chi tao MySQL va DB `BTH3_PRODUCT`, map localhost:3307 (tranh trung MySQL Workbench 3306).
- Java Product Service chay rieng tai localhost:8081.
- Mat khau trong `docker-compose.yml` chi la thong tin phuc vu thuc hanh **local**, can thay neu deploy.
- Dung: `docker compose down` (khong dung `-v` neu muon giu du lieu).

## 5. REST API

| Method | URL | Ket qua |
|--|--|--|
| `POST` | `/products` | `201 Created` |
| `GET` | `/products` | `200 OK`, mang san pham |
| `GET` | `/products/{id}` | `200 OK`, `404` neu khong tim thay |
| `PUT` | `/products/{id}` | `200 OK`, `404` neu khong tim thay |
| `DELETE` | `/products/{id}` | `204 No Content`, `404` neu khong tim thay |

Alias `/api/products` duoc ho tro cho tat ca 5 API.

### JSON tao/cap nhat

```json
{
  "name": "Laptop Dell Inspiron",
  "description": "Laptop hoc tap va lap trinh",
  "price": 18500000.00,
  "quantity": 10
}
```

- `name`: khong rong, toi da 255 ky tu.
- `description`: tuy chon, toi da 2000 ky tu.
- `price`: >= 0, toi da 2 chu so thap phan.
- `quantity`: so nguyen >= 0.
- Server tu tao `id`, `createdAt`, `updatedAt`.

### Ma loi HTTP

- `400`: JSON / du lieu khong hop le.
- `404`: khong tim thay san pham.
- `201`: tao thanh cong, co header `Location`.
- `204`: xoa thanh cong.

## 6. Test tu dong

```powershell
mvn test
```

File `src/test/java/com/example/bth3product/ProductApiIntegrationTest.java` kiem tra:
- Tao va lay chi tiet san pham;
- Xem danh sach, cap nhat, xoa;
- Tu choi gia/so luong am, ten rong;
- Tra ve 404 neu ID khong ton tai.

Test su dung H2 in-memory nen khong can khoi dong MySQL.

## 7. Cau truc

```text
src/main/java/com/example/bth3product/
  Bth3ProductApplication.java
  controller/ProductController.java
  dto/ProductRequest.java
  dto/ProductResponse.java
  entity/Product.java
  exception/GlobalExceptionHandler.java
  exception/ProductNotFoundException.java
  repository/ProductRepository.java
  service/ProductService.java
src/main/resources/application.properties
src/test/java/.../ProductApiIntegrationTest.java
src/test/resources/application-test.properties
```

## 8. Luu y kien truc SOA

Product Service co API HTTP, cong va schema MySQL rieng. No khong chia se bang voi service khac va co the run doc lap. Neu sau nay tich hop Auth Service, nen cau hinh tuy chon theo profile, tranh bat buoc Auth Service khi test yeu cau doc lap cua bai TH3.
