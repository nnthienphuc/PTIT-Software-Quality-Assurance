package com.nnthienphuc.intelligentbookstoreecommercewebsite.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.Author;
import com.nnthienphuc.intelligentbookstoreecommercewebsite.service.AuthorService;

@Controller
@RequestMapping("/admin/author")
public class AuthorController {

    @Autowired
    private AuthorService AuthorService; // Giả sử bạn có AuthorService

    @GetMapping
    public String showAuthors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            Model model) {
        
        try {
            Page<Author> authorPage;
            
            if (keyword != null && !keyword.trim().isEmpty()) {
                // Tìm kiếm có phân trang
                authorPage = AuthorService.searchAuthors(keyword.trim(), PageRequest.of(page, size));
            } else {
                // Lấy tất cả có phân trang
                authorPage = AuthorService.getAllAuthors(PageRequest.of(page, size));
            }
            
            model.addAttribute("authors", authorPage);
            model.addAttribute("keyword", keyword);
            
            return "admin/author";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
    @GetMapping("/{id}")
    @ResponseBody
    public Author getAuthorById(@PathVariable Long id) {
        return AuthorService.getAuthorById(id);
    }

    @PostMapping("/add")
    public String addAuthor(@ModelAttribute Author author, RedirectAttributes redirectAttributes) {
        try {
            AuthorService.saveAuthor(author);
            // Thêm thông báo thành công
            redirectAttributes.addFlashAttribute("success", "add");
            return "redirect:/admin/author";
        } catch (Exception e) {
            // Thêm thông báo thất bại
            redirectAttributes.addFlashAttribute("error", "add");
            return "redirect:/admin/author";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        AuthorService.deleteAuthor(id);
        return "redirect:/admin/author";
    }

    @PostMapping("/edit")
    public String editAuthor(@ModelAttribute Author author, RedirectAttributes redirectAttributes) {
        try {
            AuthorService.saveAuthor(author);
            redirectAttributes.addFlashAttribute("success", "edit");
            return "redirect:/admin/author";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "edit");
            return "redirect:/admin/author";
        }
    }
    
}