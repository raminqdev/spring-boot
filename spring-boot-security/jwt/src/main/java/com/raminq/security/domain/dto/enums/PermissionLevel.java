package com.raminq.security.domain.dto.enums;

public enum PermissionLevel {
    PANEL_ADMIN(1),
    READ(2),
    WRITE(3),
    DELETE(4),
    UPDATE(5);

    private final Integer value;

    PermissionLevel(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }

    private static final PermissionLevel[] VALUES = PermissionLevel.values();

    public static PermissionLevel getByValue(Integer value) {
        for (PermissionLevel e : VALUES) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        return null;
    }

}
