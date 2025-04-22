package com.nnthienphuc.intelligentbookstoreecommercewebsite.controller.admin;

import com.nnthienphuc.intelligentbookstoreecommercewebsite.DTO.StaffUpdateDTO;
import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.Staff;
import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.User;
import com.nnthienphuc.intelligentbookstoreecommercewebsite.model.MailInfo;
import com.nnthienphuc.intelligentbookstoreecommercewebsite.repository.StaffRepository;
import com.nnthienphuc.intelligentbookstoreecommercewebsite.service.CookieService;
import com.nnthienphuc.intelligentbookstoreecommercewebsite.service.MailService;
import com.nnthienphuc.intelligentbookstoreecommercewebsite.service.StaffService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@RequestMapping("/admin")
@Controller
public class AdminController {
    @Autowired
    private StaffService staffService;

    @Autowired
    private HttpSession session;

    @Autowired
    private CookieService cookie;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    MailService mailService;
    @Autowired
    private StaffRepository staffRepository;

    @GetMapping("/account/login")
    public String loginForm(org.springframework.ui.Model model) {
        String staffId = String.valueOf(cookie.read("id"));
        String password = String.valueOf(cookie.read("pass"));
        if (staffId != null && password != null) {
            model.addAttribute("id", staffId);
            model.addAttribute("pwd", password);
        }
        return "admin/account/login";
    }

    @PostMapping("/account/login")
    public String login(
            RedirectAttributes redirectAttributes,
            @RequestParam("staffid") @NotEmpty(message = "Username is required!") String staffId,
            @RequestParam("pass") @NotEmpty(message = "Password is required!") String pwd,
            @RequestParam(value = "rm", defaultValue = "true") boolean rememberMe) {

        Optional<Staff> staffOptional = staffService.findById(staffId);
        if (staffOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Invalid username!");
            return "redirect:/admin/account/login";
        }

        Staff staff = staffOptional.get();
        staffService.resetpass(staff);
        if (!passwordEncoder.matches(pwd, staff.getPwd())) {
            redirectAttributes.addFlashAttribute("error", "Invalid password!");
            return "redirect:/admin/account/login";
        }

        if (!staff.getIsActive()) {
            redirectAttributes.addFlashAttribute("error", "Your account is Inactivated!");
            return "redirect:/admin/account/login";
        }

        redirectAttributes.addFlashAttribute("success", "Login successfully!");
        session.setAttribute("staff", staff);

        if (rememberMe) {
            cookie.create("staffid", staff.getStaffId(), 30);
            cookie.create("pass", staff.getPwd(), 30);
        } else {
            cookie.delete("staffid");
            cookie.delete("pass");
        }
        return "redirect:/admin/promotion";
    }


    @GetMapping("/account/register")
    public String registerForm(Model model) {
        model.addAttribute("staff", new Staff());
        return "admin/account/register";
    }

//    @PostMapping("/account/register")
//    public String register(
//            Model model,
//            @ModelAttribute("staff") Staff staff,
//            BindingResult error) throws Exception {
//
//        if (error.hasErrors()) {
//            model.addAttribute("message", "Please fill all the required fields!");
//            return "redirect:/user/account/register";
//        }
//
//        staff.setIsActive(false);
//        staffService.create(staff);
//
//
//        model.addAttribute("message", "Registration successful. Please check your email for activation.");
//
//        String from = "phucnaoto.buildweb@gmail.com";
//        String to = staff.getEmail();
//        String subject = "Let Active Your Account!";
//        String url = request.getRequestURL().toString().replace("register", "activate/" + staff.getStaffId());
//        String body = "Click <a href='" + url + "'> to active your account.</a>";
//        MailInfo mail = new MailInfo(from, to, subject, body);
//        mailService.send(mail);
//
//        return "redirect:/admin/account/login";
//    }

    @PostMapping("/account/register")
    public String register(
            Model model,
            @ModelAttribute("staff") Staff staff,
            BindingResult error) throws Exception {

        if (error.hasErrors()) {
            model.addAttribute("message", "Please fill all the required fields!");
            return "redirect:/admin/account/register";
        }

        staff.setIsActive(false);  // Đặt mặc định là chưa kích hoạt
        staffService.create(staff);  // Lưu thông tin nhân viên vào cơ sở dữ liệu

        model.addAttribute("message", "Registration successful. Please check your email for activation.");

        String from = "phucnaoto.buildweb@gmail.com";
        String to = staff.getEmail();
        String subject = "Activate Your Account!";
        String url = "http://localhost:8080/admin/account/active/" + staff.getStaffId(); // Địa chỉ URL đầy đủ
        // Đường dẫn kích hoạt
        String body = "Click <a href='" + url + "'>here</a> to active your account.";

        MailInfo mail = new MailInfo(from, to, subject, body);
        mailService.send(mail);  // Gửi email

        return "redirect:/admin/account/login";
    }

    @GetMapping("account/active/{id}")
    public String active(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        Optional<Staff> staff = staffService.findById(id);

        if (staff.isPresent()) {
            Staff currentStaff = staff.get();
            if (!currentStaff.getIsActive()) {
                currentStaff.setIsActive(true);
                staffService.update(currentStaff);
                redirectAttributes.addFlashAttribute("success", "Account activated successfully!");
            } else {
                redirectAttributes.addFlashAttribute("info", "Account is already active!");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Account not found!");
        }

        return "redirect:/admin/account/login";
    }

    @RequestMapping("/account/logout")
    public String logout() {
        session.removeAttribute("staff");
        session.removeAttribute("back-url");
        // Hủy session hoàn toàn
        session.invalidate();
        return "redirect:/admin/account/login";
    }

    @RequestMapping("/account/unauthorized")
    public String unauthorized() {
        return "admin/account/unauthorized";
    }

    @GetMapping("/account")
    public String account(Model model) {
        Staff staff = (Staff) session.getAttribute("staff");  // Lấy thông tin nhân viên từ dịch vụ
        model.addAttribute("staff", staff);  // Thêm thông tin vào model
        return "admin/account";  // Trả về tên của view
    }

    @PostMapping("/account")
    public String updateAccount(@ModelAttribute Staff updatedStaff, RedirectAttributes redirectAttributes) {
        Staff existingStaff = (Staff) session.getAttribute("staff");

        existingStaff.setFullName(updatedStaff.getFullName());
        existingStaff.setPhone(updatedStaff.getPhone());
        existingStaff.setGender(updatedStaff.getGender());
        existingStaff.setAddress(updatedStaff.getAddress());

        staffRepository.save(existingStaff);

        session.setAttribute("staff", existingStaff);

        redirectAttributes.addFlashAttribute("message", "Thông tin cá nhân đã được cập nhật thành công!");

        return "redirect:/admin/account";
    }

    @PostMapping("/account/change-password")
    public String changePassword(
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            RedirectAttributes redirectAttributes) {

        Staff staff = (Staff) session.getAttribute("staff");

        if (!passwordEncoder.matches(currentPassword, staff.getPwd())) {
            redirectAttributes.addFlashAttribute("message", "Mật khẩu cũ không đúng!");
            return "redirect:/admin/account";
        }

        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("message", "Mật khẩu mới và xác nhận không khớp!");
            return "redirect:/admin/account";
        }

        staff.setPwd(passwordEncoder.encode(newPassword));
        staffRepository.save(staff);

        session.invalidate();

        redirectAttributes.addFlashAttribute("message", "Đổi mật khẩu thành công! Vui lòng đăng nhập lại.");

        return "redirect:/admin/account/login";
    }





//    @PostMapping("/account/change-password")
//    public String changePassword(
//            @RequestParam("currentPassword") String currentPassword,
//            @RequestParam("newPassword") String newPassword,
//            @RequestParam("confirmPassword") String confirmPassword,
//            Model model) {
//
//        Staff staff = (Staff) session.getAttribute("staff");
//
//        // Kiểm tra mật khẩu cũ có đúng không
//        if (!passwordEncoder.matches(currentPassword, staff.getPwd())) {
//            model.addAttribute("message", "Mật khẩu cũ không đúng!");
//            return "redirect:/admin/account";
//        }
//
//        // Kiểm tra mật khẩu mới và xác nhận mật khẩu mới có khớp không
//        if (!newPassword.equals(confirmPassword)) {
//            model.addAttribute("message", "Mật khẩu mới và xác nhận không khớp!");
//            return "redirect:/admin/account";
//        }
//
//        // Mã hóa mật khẩu mới và cập nhật vào cơ sở dữ liệu
//        staff.setPwd(passwordEncoder.encode(newPassword));
//        staffRepository.save(staff); // Lưu lại mật khẩu mới vào database
//
//        session.setAttribute("staff", staff); // Cập nhật thông tin nhân viên trong session
//
//        model.addAttribute("message", "Đổi mật khẩu thành công!");
//        return "redirect:/admin/account";
//    }

}
