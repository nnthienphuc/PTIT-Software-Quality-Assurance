package com.nnthienphuc.intelligentbookstoreecommercewebsite.util;

public enum OrderStatus {
    PENDING("Chờ xác nhận"),
    CONFIRMED("Đã xác nhận"),
    IN_PROGRESS("Đang giao"),
    DELIVERED("Đã giao"),
    CANCELED("Đã hủy");

    private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}