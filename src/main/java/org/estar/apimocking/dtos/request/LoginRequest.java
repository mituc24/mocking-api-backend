package org.estar.apimocking.dtos.request;

import lombok.Data;

@Data
public class LoginRequest {
    String username;
    String password;
}
