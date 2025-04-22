package com.nnthienphuc.intelligentbookstoreecommercewebsite.repository;


import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.Author;
import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.Book;
import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.Author;
import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    // Tìm kiếm theo categoryName chứa keyword (không phân biệt hoa thường)
    List<Book> findByTitleContainingIgnoreCase(String keyword);

    @Query("SELECT b FROM Book b WHERE b.authorId.authorId = :authorId")
    List<Book> getBooksByAuthorID(@Param("authorId") Integer authorId);

    @Query("SELECT b FROM Book b WHERE b.categoryId.categoryId = :categoryId")
    List<Book> getBooksByCategoryID(@Param("categoryId") Integer categoryId);

    @Query("SELECT b FROM Book b WHERE b.publisherId.publisherId = :publisherId")
    List<Book> getBooksByPublisherID(@Param("publisherId") Integer publisherId);

    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Book> findByCategoryId(Category category, Pageable pageable);

    Page<Book> findByAuthorId(Author author, Pageable pageable);

    Page<Book> findByTitleContainingIgnoreCaseAndCategoryId(String title, Category category, Pageable pageable);

    Page<Book> findByTitleContainingIgnoreCaseAndAuthorId(String title, Author author, Pageable pageable);

    Page<Book> findByCategoryIdAndAuthorId(Category category, Author author, Pageable pageable);

    Page<Book> findByTitleContainingIgnoreCaseAndCategoryIdAndAuthorId(
        String title, Category category, Author author, Pageable pageable);

    // Tìm sách theo title hoặc theo tên tác giả
//    List<Book> findByTitleContainingIgnoreCase(String title);
}