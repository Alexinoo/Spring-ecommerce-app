package com.example.ecommerce.response;

public record CustomerResponse(
    String id,
    String firstname,
    String lastname,
    String email
) {
}
