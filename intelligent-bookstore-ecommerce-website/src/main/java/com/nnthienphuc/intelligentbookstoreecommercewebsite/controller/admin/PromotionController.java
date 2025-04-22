package com.nnthienphuc.intelligentbookstoreecommercewebsite.controller.admin;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

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

import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.Promotion;
import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.User;
import com.nnthienphuc.intelligentbookstoreecommercewebsite.model.MailInfo;
import com.nnthienphuc.intelligentbookstoreecommercewebsite.repository.UserRepository;
import com.nnthienphuc.intelligentbookstoreecommercewebsite.service.MailService;
import com.nnthienphuc.intelligentbookstoreecommercewebsite.service.PromotionService;

@Controller
@RequestMapping("/admin/promotion")
public class PromotionController {

    @Autowired
    private PromotionService PromotionService; // Giả sử bạn có PromotionService 
    @Autowired
    private UserRepository userRespo; // Giả sử bạn có PromotionService
    @Autowired
    MailService mailService;
    @GetMapping("")
    public String showPromotions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            Model model) {
        
        try {
            Page<Promotion> promotionPage;
            
            if (keyword != null && !keyword.trim().isEmpty()) {
                // Tìm kiếm có phân trang
                promotionPage = PromotionService.searchPromotions(keyword.trim(), PageRequest.of(page, size));
            } else {
                // Lấy tất cả có phân trang
            	promotionPage = PromotionService.getAllPromotions(PageRequest.of(page, size));
            }
            
            model.addAttribute("promotions", promotionPage);
            model.addAttribute("keyword", keyword);
            
            return "admin/promotion";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
    @GetMapping("/{id}")
    @ResponseBody
    public Promotion getPromotionById(@PathVariable Long id) {
        return PromotionService.getPromotionById(id);
    }
    @PostMapping("/add")
    public String addPromotion(@ModelAttribute Promotion Promotion, RedirectAttributes redirectAttributes) {
        try {
            PromotionService.savePromotion(Promotion);
            // Thêm thông báo thành công
            redirectAttributes.addAttribute("success", "add");
            return "redirect:/admin/promotion";
        } catch (Exception e) {
            // Thêm thông báo thất bại
            redirectAttributes.addAttribute("error", "add");
            return "redirect:/admin/promotion";
        }
    }

    @PostMapping("/delete/{id}")
    public String deletePromotion(@PathVariable Long id) {
        PromotionService.deletePromotion(id);
        return "redirect:/admin/promotion";
    }

    @PostMapping("/edit")
    public String editPromotion(@ModelAttribute Promotion Promotion, RedirectAttributes redirectAttributes) {
        try {
            PromotionService.savePromotion(Promotion);
            redirectAttributes.addAttribute("success", "edit");
            return "redirect:/admin/promotion";
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", "edit");
            return "redirect:/admin/promotion";
        }
    }
    
    @GetMapping("/notification/{id}")
    @ResponseBody
    public String notification(@PathVariable Long id) {
    	
    	try {
    		Promotion promotion = PromotionService.getPromotionById(id);
    		List<User> users = userRespo.findAll();
    		BigDecimal amount = new BigDecimal(promotion.getCondition().toString());

            // Tạo định dạng VND
            NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            vndFormat.setMaximumFractionDigits(0); // Không hiển thị phần thập phân
            vndFormat.setMinimumFractionDigits(0);

            // Format chuỗi
            String formattedCondition = vndFormat.format(amount).replace("₫", "VNĐ").trim();			
			
			NumberFormat percentFormat = NumberFormat.getPercentInstance();
			percentFormat.setMinimumFractionDigits(0); // Không lấy số lẻ, hoặc set = 1,2 nếu muốn
			
			String formattedPercent = percentFormat.format(promotion.getDiscountPercent());
    		String from = "phucnaoto.buildweb@gmail.com";
            String to = "412ngan@gmail.com";
            String subject = "Thông báo khuyến mãi";
            
            String body = """
            	    <html>
            	        <body>
            	            <h2>🎉 Ưu đãi đặc biệt từ Online Book Store 🎉</h2>
            	            <p>Xin chào quý khách,</p>
            	            <p>Chúng tôi xin thông báo chương trình khuyến mãi mới:</p>
            	            <ul>
            	                <li><strong>Tên khuyến mãi:</strong> %s</li>
            	                <li><strong>Với đơn hàng :</strong> %s</li>
            	                <li><strong>Được giảm giá:</strong> %s</li>
            	              <li><strong>Áp dụng:</strong> từ ngày %s tới ngày %s</li>
            	            </ul>
            	            <p>Hãy nhanh tay truy cập <a href="http://localhost:8080/">tại đây</a> để nhận ưu đãi!</p>
            	            <p>Trân trọng,<br>Online Book Store Team</p>
            	        </body>
            	    </html>
            	""".formatted(
            	    promotion.getPromotionName(),
            	    formattedCondition,
            	    formattedPercent,
            	    promotion.getStartDate(),
            	    promotion.getEndDate()
            	);
            for(int i = 0; i < users.size(); i++) {
            	 MailInfo mail = new MailInfo(from, users.get(i).getEmail(), subject, body);
                 mailService.send(mail);  
            }
           
            return "Khuyến mãi đã được gửi thành công!";
    	}catch (Exception e) {
    		return "Lỗi khi gửi khuyến mãi: "+e.getMessage();
		}        
        
    }
}