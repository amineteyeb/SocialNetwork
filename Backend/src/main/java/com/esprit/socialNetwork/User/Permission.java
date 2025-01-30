package com.esprit.socialNetwork.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    SELLER_READ("management:read"),
    SELLER_UPDATE("management:update"),
    SELLER_CREATE("management:create"),
    SELLER_DELETE("management:delete"),
    CLIENT_READ("management:read"),
    CLIENT_UPDATE("management:update"),
    CLIENT_CREATE("management:create"),
    CLIENT_DELETE("management:delete"),
    HYBRID_READ("management:read"),
    HYBRID_UPDATE("management:update"),
    HYBRID_CREATE("management:create"),
    HYBRID_DELETE("management:delete"),
    ;

    @Getter
    private final String permission;
}
