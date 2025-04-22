package com.nnthienphuc.intelligentbookstoreecommercewebsite.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "`User`")
public class User {
    @Id
    @Column(name = "user_id", nullable = false, length = 50)
    private String userId;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Nationalized
    @Column(name = "full_name", nullable = false, length = 60)
    private String fullName;

    @ColumnDefault("0")
    @Column(name = "gender", nullable = false)
    private Boolean gender = false;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Column(name = "pwd", nullable = false, length = 60)
    private String pwd;

    @ColumnDefault("0")
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Order> orders = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private Set<Cart> carts = new LinkedHashSet<>();

    @Nationalized
    @Lob
    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone", nullable = false, length = 10)
    private String phone;

//    @Nationalized
//    @Lob
//    @Column(name = "address", nullable = false)
//    private String address;
//
//    @Column(name = "phone", nullable = false, length = 10)
//    private String phone;

}
