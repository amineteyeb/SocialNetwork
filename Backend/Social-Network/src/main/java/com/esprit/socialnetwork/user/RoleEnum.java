package com.esprit.socialnetwork.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.esprit.socialnetwork.user.Permission.*;


@RequiredArgsConstructor
public enum RoleEnum {


    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE,
                    SELLER_CREATE,
                    SELLER_READ,
                    SELLER_DELETE,
                    SELLER_UPDATE,
                    CLIENT_CREATE,
                    CLIENT_READ,
                    CLIENT_UPDATE,
                    CLIENT_DELETE
            ))
    ,
    SELLER(
            Set.of(
                    SELLER_CREATE,
                    SELLER_READ,
                    SELLER_DELETE,
                    SELLER_UPDATE
            )
    ),
    CLIENT(
            Set.of(
                    CLIENT_CREATE,
                    CLIENT_READ,
                    CLIENT_UPDATE,
                    CLIENT_DELETE
            ))
            ,
    HYBRID(
            Set.of(
                    HYBRID_CREATE,
                    HYBRID_READ,
                    HYBRID_UPDATE,
                    HYBRID_DELETE
            ))
    ,
    ;

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
