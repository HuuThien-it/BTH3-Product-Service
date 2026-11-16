-- Run once in MySQL Workbench (or use the Docker Compose configuration).
CREATE DATABASE IF NOT EXISTS BTH3_PRODUCT
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE BTH3_PRODUCT;

CREATE TABLE IF NOT EXISTS products (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(2000) NULL,
    price DECIMAL(15, 2) NOT NULL,
    quantity INT NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL
);

-- To verify:
-- SELECT * FROM BTH3_PRODUCT.products;
