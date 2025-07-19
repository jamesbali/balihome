package com.bali.balihome.security.user;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Role {

    CUSTOMER(Set.of(
            Permission.CUSTOMER_READ,
            Permission.CUSTOMER_WRITE
    )),

    EMPLOYEE(Set.of(
            Permission.CUSTOMER_READ,
            Permission.EMPLOYEE_READ,
            Permission.EMPLOYEE_WRITE,
            Permission.PRODUCT_CREATE,
            Permission.PRODUCT_UPDATE,
            Permission.INVENTORY_READ,
            Permission.ORDER_MANAGE
    )),

    MANAGER(Set.of(
            Permission.CUSTOMER_READ,
            Permission.EMPLOYEE_READ,
            Permission.EMPLOYEE_WRITE,
            Permission.MANAGER_READ,
            Permission.MANAGER_WRITE,
            Permission.MANAGER_DELETE,
            Permission.PRODUCT_CREATE,
            Permission.PRODUCT_UPDATE,
            Permission.PRODUCT_DELETE,
            Permission.INVENTORY_READ,
            Permission.INVENTORY_WRITE,
            Permission.ORDER_VIEW_ALL,
            Permission.ORDER_MANAGE,
            Permission.STORE_READ,
            Permission.STORE_WRITE
    )),

    ADMIN(Set.of(
            Permission.ADMIN_READ,
            Permission.ADMIN_WRITE,
            Permission.ADMIN_DELETE,
            Permission.ADMIN_MANAGE_USERS,
            Permission.CUSTOMER_READ,
            Permission.CUSTOMER_WRITE,
            Permission.EMPLOYEE_READ,
            Permission.EMPLOYEE_WRITE,
            Permission.MANAGER_READ,
            Permission.MANAGER_WRITE,
            Permission.MANAGER_DELETE,
            Permission.PRODUCT_CREATE,
            Permission.PRODUCT_UPDATE,
            Permission.PRODUCT_DELETE,
            Permission.INVENTORY_READ,
            Permission.INVENTORY_WRITE,
            Permission.ORDER_VIEW_ALL,
            Permission.ORDER_MANAGE,
            Permission.STORE_READ,
            Permission.STORE_WRITE
    ));

    @Getter
    private final Set<Permission> permissions;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = permissions.stream()
                .map(p -> new SimpleGrantedAuthority(p.getPermission()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }

}
