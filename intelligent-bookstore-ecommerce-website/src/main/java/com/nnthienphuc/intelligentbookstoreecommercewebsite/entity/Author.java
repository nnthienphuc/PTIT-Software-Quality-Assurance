package com.nnthienphuc.intelligentbookstoreecommercewebsite.entity;
import java.util.LinkedHashSet;
import java.util.Set;

import org.hibernate.annotations.Nationalized;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "Author")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id", nullable = false)
    private Short authorId;

    @Column(name = "author_name", nullable = false, length = 50)
    private String authorName;

    @OneToMany(mappedBy = "authorId", fetch = FetchType.LAZY)
    private Set<Book> books = new LinkedHashSet<>();
}