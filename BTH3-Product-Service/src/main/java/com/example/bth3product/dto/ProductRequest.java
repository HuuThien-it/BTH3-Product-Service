package com.example.bth3product.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductRequest(
        @NotBlank(message = "Ten san pham khong duoc de trong")
        @Size(max = 255, message = "Ten san pham toi da 255 ky tu")
        String name,

        @Size(max = 2000, message = "Mo ta toi da 2000 ky tu")
        String description,

        @NotNull(message = "Gia khong duoc de trong")
        @DecimalMin(value = "0.00", message = "Gia phai lon hon hoac bang 0")
        @Digits(integer = 13, fraction = 2, message = "Gia toi da 13 chu so va 2 so thap phan")
        BigDecimal price,

        @NotNull(message = "So luong khong duoc de trong")
        @Min(value = 0, message = "So luong phai lon hon hoac bang 0")
        Integer quantity
) { }
