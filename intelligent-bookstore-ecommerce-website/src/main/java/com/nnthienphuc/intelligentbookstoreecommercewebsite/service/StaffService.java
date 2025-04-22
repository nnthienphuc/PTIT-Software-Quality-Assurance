package com.nnthienphuc.intelligentbookstoreecommercewebsite.service;

import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.Author;
import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.Category;
import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.Role;
import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.Staff;
import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.User;
import com.nnthienphuc.intelligentbookstoreecommercewebsite.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffService {
    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;

    public Optional<Staff> findById(String staffId) {
        return staffRepository.findById(staffId);
    }

    public Staff findByEmail(String email) {
        return staffRepository.findByEmail(email);
    }

    public List<Staff> findAll() {
        return staffRepository.findAll();
    }

    public Staff create(Staff staff) throws Exception {
        if(staffRepository.existsById(staff.getStaffId())) {
            throw new Exception("Username has exist!");
        }

        Role role = roleService.findRoleByName("staff");

        staff.setRole(role);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        staff.setPwd(passwordEncoder.encode(staff.getPwd()));

        return staffRepository.save(staff);
    }

    public Staff resetpass(Staff staff) {
    	 PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
         staff.setPwd(passwordEncoder.encode("123456"));

         return staffRepository.save(staff);
    }
    public Staff update(Staff staff) {
        return staffRepository.save(staff);
    }

    public void delete(String staffId) {
        staffRepository.deleteById(staffId);
    }

    public Staff registerStaff(Staff staff) {
        // Mã hóa mật khẩu trước khi lưu vào database
        staff.setPwd(passwordEncoder.encode(staff.getPwd()));
        return staffRepository.save(staff);
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    // Thêm phương thức tìm kiếm người dùng theo fullName hoặc một phần tên
    public List<Staff> findByFullNameContainingIgnoreCase(String fullName) {
        return staffRepository.findByFullNameContainingIgnoreCase(fullName);
    }
    
    public Page<Staff> searchStaffs(String keyword,Pageable pageable) {
    	Page<Staff> StaffPage;
    	if (keyword != null && !keyword.trim().isEmpty()) {
       	 try {
       	        // Gọi phương thức getStaffById(), nếu không tìm thấy sẽ ném lỗi
       	        Staff Staff = getStaffById(keyword);  // Có thể sẽ ném RuntimeException nếu không tìm thấy StaffId
       	        
       	        // Nếu tìm thấy StaffId hợp lệ, thực hiện tìm kiếm theo StaffId
       	        StaffPage = staffRepository.findByStaffId(keyword, pageable);
       	    } catch (RuntimeException e) {
       	        // Nếu không tìm thấy StaffId, tìm kiếm theo fullName (tên khách hàng)
       	    	StaffPage = staffRepository.findByFullNameContainingIgnoreCase(keyword.trim(),pageable);
       	    }
       	
       } else {
           // Lấy tất cả có phân trang
       	StaffPage = getAllStaffsPagging(pageable);
       }
    	return StaffPage;
    }
 
    public Page<Staff> getAllStaffsPagging(Pageable pageable) {
        return staffRepository.findAll(pageable);
    }
    public Staff getStaffById(String id) {
        return staffRepository.findByStaffId(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên với id: " + id));
    }


    public Staff saveStaff(Staff staff) {
        return staffRepository.save(staff);
    }


    public void deleteStaff(String id) {
        staffRepository.deleteById(id);
    }
}
