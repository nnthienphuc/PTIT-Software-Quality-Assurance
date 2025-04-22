package com.nnthienphuc.intelligentbookstoreecommercewebsite.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class CartId implements java.io.Serializable {
    private static final long serialVersionUID = -8581557963012485754L;
    @Column(name = "user_id", nullable = false, length = 50)
    private String userId;

    @Column(name = "isbn", nullable = false, length = 13)
    private String isbn;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CartId entity = (CartId) o;
        return Objects.equals(this.isbn, entity.isbn) &&
                Objects.equals(this.userId, entity.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, userId);
    }

}