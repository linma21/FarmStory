package kr.co.farmstory.controller;

import kr.co.farmstory.dto.MarketPageRequestDTO;
import kr.co.farmstory.dto.MarketPageResponseDTO;
import kr.co.farmstory.dto.ProductDTO;
import kr.co.farmstory.service.AdminService;
import kr.co.farmstory.service.MarketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminController {

    private final AdminService adminService;
    private final MarketService marketService;

    // admin 메인 페이지
    @GetMapping("/admin/index")
    public String adminIndex(){

        return "/admin/index";
    }
    
    
////ADMIN-PRODUCT////
    // admin 페이지 상품 목록
    @GetMapping("/admin/product/list")
    public String prodList(Model model, MarketPageRequestDTO marketPageRequestDTO){
        MarketPageResponseDTO pageResponseDTO = marketService.selectProducts(marketPageRequestDTO);
        log.info("pageResponseDTO : " + pageResponseDTO.toString());
        model.addAttribute(pageResponseDTO);
        return "/admin/product/list";
    }

    // admin 페이지 상품 등록
    @GetMapping("/admin/product/register")
    public String prodRegister(){

        return "/admin/product/register";
    }

    // admin 페이지 상품 등록
    @PostMapping("/admin/product/register")
    public String prodRegister(ProductDTO productDTO,
                               @RequestParam("thumb120") MultipartFile thumb120,
                               @RequestParam("thumb240") MultipartFile thumb240,
                               @RequestParam("thumb750") MultipartFile thumb750){
        LocalDateTime rdate = LocalDateTime.now();
        productDTO.setRdate(rdate);
        log.info("prodRegister");
        log.info("productDTO : " + productDTO.toString());
        log.info("thumb120 : " + thumb120);
        log.info("thumb240 : " + thumb240);
        log.info("thumb750 : " + thumb750);

        // 상품 등록 service
        adminService.productRegister(productDTO, thumb120, thumb240, thumb750);


        return "/admin/product/register";
    }
}
