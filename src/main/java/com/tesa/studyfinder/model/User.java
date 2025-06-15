package com.tesa.studyfinder.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private String email;
    private String passwordHash;
}
