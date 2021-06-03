package com.raminq.security.domain.entity.security;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String authority;

    @ManyToMany(mappedBy = "authorities")
    private Set<User> users = new HashSet<>();

    @Singular
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "authority_permission",
            joinColumns = {@JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "PERMISSION_ID", referencedColumnName = "ID")})
    private Set<Permission> permissions = new HashSet<>();

    @Override
    public String getAuthority() {
        return authority;
    }
}
