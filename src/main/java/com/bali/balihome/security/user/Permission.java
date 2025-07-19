package com.bali.balihome.security.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    // Customer permissions
    CUSTOMER_READ("customer:read"),
    CUSTOMER_WRITE("customer:write"),

    // Employee permissions
    EMPLOYEE_READ("employee:read"),
    EMPLOYEE_WRITE("employee:write"),

    // Manager permissions
    MANAGER_READ("manager:read"),
    MANAGER_WRITE("manager:write"),
    MANAGER_DELETE("manager:delete"),

    // Admin permissions (full access)
    ADMIN_READ("admin:read"),
    ADMIN_WRITE("admin:write"),
    ADMIN_DELETE("admin:delete"),
    ADMIN_MANAGE_USERS("admin:manage_users"),

    // Product management
    PRODUCT_CREATE("product:create"),
    PRODUCT_UPDATE("product:update"),
    PRODUCT_DELETE("product:delete"),

    // Order management
    ORDER_VIEW_ALL("order:view_all"),
    ORDER_MANAGE("order:manage"),

    // Inventory management
    INVENTORY_READ("inventory:read"),
    INVENTORY_WRITE("inventory:write"),

    // Store management
    STORE_READ("store:read"),
    STORE_WRITE("store:write");

    @Getter
    private final String permission;


}
