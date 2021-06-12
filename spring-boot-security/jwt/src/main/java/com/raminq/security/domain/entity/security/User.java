package com.raminq.security.domain.entity.security;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "`user`")
public class User implements UserDetails {

    @Id
    @SequenceGenerator(name = "myKeySeq", allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myKeySeq")

//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;
    private String fullName;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @Builder.Default
    private boolean enabled = true;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Role role;

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return ofNullable(role).map(Role::getPermissions)
                .map(p -> p.stream().map(Permission::getName).map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toSet()))
                .orElse(Set.of());
    }

}
