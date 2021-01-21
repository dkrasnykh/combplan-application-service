package com.orioninc.combplanapplicationservice.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDto extends AbstractDto {
    private String login;
    private String password;
    private String email;
    private String fullName;
    private Set<Role> roles;

    public UserDto() {
    }

    public UserDto(Long id) {
        this.setId(id);
    }
}
