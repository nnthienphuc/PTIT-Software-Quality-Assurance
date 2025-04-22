package com.nnthienphuc.intelligentbookstoreecommercewebsite.service;

import java.util.List;

import com.nnthienphuc.intelligentbookstoreecommercewebsite.repository.AuthorRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.Author;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository AuthorRepository;
    public List<Author> getAllAuthors() {
        return AuthorRepository.findAll();
    }

    public Page<Author> getAllAuthors(Pageable pageable) {
        return AuthorRepository.findAll(pageable);
    }

    public List<Author> getAllAuthorsNoPaging() {
        return AuthorRepository.findAll();
    }
    
    public Page<Author> searchAuthors(String keyword, Pageable pageable) {
        return AuthorRepository.findByAuthorNameContainingIgnoreCase(keyword, pageable);
    }
 

    public Author getAuthorById(Long id) {
        return AuthorRepository.findByAuthorId(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục với id: " + id));
    }


    public Author saveAuthor(Author Author) {
        return AuthorRepository.save(Author);
    }


    public void deleteAuthor(Long id) {
        AuthorRepository.deleteById(id);
    }
    
}