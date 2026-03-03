package org.estar.apimocking.dtos.response;

import lombok.Data;
import org.estar.apimocking.models.Role;
import org.estar.apimocking.models.User;
import org.springframework.security.core.userdetails.UserDetails;

@Data
public class ResponseUser {
    private String username;
    private Role role;

    public ResponseUser(User user)
    {
        this.username = user.getUsername();
        this.role = user.getRole();
    }
}
