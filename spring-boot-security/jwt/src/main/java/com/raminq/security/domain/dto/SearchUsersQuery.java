package com.raminq.security.domain.dto;

import lombok.Data;

@Data
public class SearchUsersQuery {

    private Long id;
    private String username;
    private String fullName;
    private Boolean enabled;

}
