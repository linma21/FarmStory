package kr.co.farmstory.controller;

import kr.co.farmstory.dto.*;
import kr.co.farmstory.entity.User;
import kr.co.farmstory.dto.MarketPageRequestDTO;
import kr.co.farmstory.dto.MarketPageResponseDTO;
import kr.co.farmstory.dto.ProductDTO;
import kr.co.farmstory.entity.Product;
import kr.co.farmstory.service.AdminService;
import kr.co.farmstory.service.MarketService;
import kr.co.farmstory.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.Console;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminController {

    private final AdminService adminService;
    private final MarketService marketService;
    private final UserService userService;

    // admin 메인 페이지
    @GetMapping("/admin/index")
    public String adminIndex(Model model){

        log.info("AdminController - adminIndex : 들어옴");

        List<ProductDTO> products= adminService.products();

        log.info("AdminController - adminIndex : "+products);

        model.addAttribute("products",products);
        //상품현황(limit 3)->더보기를 누르면 product list로 넘어가도록

        return "/admin/index";
    }


    ////ADMIN-PRODUCT////
    // admin 페이지 상품 목록
    @GetMapping("/admin/product/list")
    public String prodList(Model model, MarketPageRequestDTO marketPageRequestDTO) {
        MarketPageResponseDTO pageResponseDTO = marketService.selectProducts(marketPageRequestDTO);
        log.info("pageResponseDTO : " + pageResponseDTO.toString());
        model.addAttribute(pageResponseDTO);
        return "/admin/product/list";
    }

    // admin 페이지 상품 등록
    @GetMapping("/admin/product/register")
    public String prodRegister() {

        return "/admin/product/register";
    }

    // admin 페이지 상품 등록
    @PostMapping("/admin/product/register")
    public String prodRegister(ProductDTO productDTO,
                               @RequestParam("thumb120") MultipartFile thumb120,
                               @RequestParam("thumb240") MultipartFile thumb240,
                               @RequestParam("thumb750") MultipartFile thumb750) {
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


    ////ADMIN-User////
    @GetMapping("/admin/user/list")
    public String userList(Model model, PageRequestDTO pageRequestDTO) {
        List<UserDTO> userDTOList = userService.getUserList(pageRequestDTO);
        int total = userService.getTotalCount(pageRequestDTO); // 이 메소드는 전체 사용자 수를 가져오는 구현이 필요합니다.

        UserResponseDTO userResponseDTO = new UserResponseDTO(pageRequestDTO, userDTOList, total);
        model.addAttribute("userResponseDTO", userResponseDTO);

        return "/admin/user/list";
    }

    // 상세정보
    @GetMapping("/admin/user/view")
    public String userview(@RequestParam("uid") String uid, Model model){
        UserDTO userDTO = userService.getUserByUid(uid);
        model.addAttribute("user", userDTO);
        return "/admin/user/view";
    }

    @PostMapping("/admin/user/update")
    public String updateUser(UserDTO userDTO, RedirectAttributes redirectAttributes){
        userService.updateUser(userDTO);
        log.info("userDTO : " + userDTO);
        redirectAttributes.addFlashAttribute("message", "사용자 정보가 성공적으로 업데이트 되었습니다.");
        return "redirect:/admin/user/list";
    }

    @PostMapping("/admin/user/delete")
    public String deleteUser(@RequestParam("uid") String uid){
        userService.deleteUser(uid);
        return "redirect:/admin/user/list";
    }

}

