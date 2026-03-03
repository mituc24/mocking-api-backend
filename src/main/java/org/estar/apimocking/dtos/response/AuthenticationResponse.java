package org.estar.apimocking.dtos.response;

import lombok.Data;

@Data
public class AuthenticationResponse {

    public AuthenticationResponse(String token, ResponseUser user)
    {
        this.token = token;
        this.user = user;
    }

    private String token;
    private ResponseUser user;
}
