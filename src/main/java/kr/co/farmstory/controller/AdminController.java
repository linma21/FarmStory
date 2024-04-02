package kr.co.farmstory.controller;

import kr.co.farmstory.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminController {

    // admin 메인 페이지
    @GetMapping("/admin/index")
    public String adminIndex(){

        return "/admin/index";
    }
    
    
////ADMIN-PRODUCT////
    // admin 페이지 상품 목록
    @GetMapping("/admin/product/list")
    public String prodList(){

        return "/admin/product/list";
    }

    // admin 페이지 상품 등록
    @GetMapping("/admin/product/register")
    public String prodRegister(){

        return "/admin/product/register";
    }

    // admin 페이지 상품 등록
    @PostMapping("/admin/product/register")
    public String prodRegister(ProductDTO productDTO){
        log.info("productDTO : " + productDTO);
        return "/admin/product/register";
    }
}
