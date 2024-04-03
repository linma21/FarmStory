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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

        //여기는 상품현황 시작
        log.info("AdminController - adminIndex-product : 들어옴");

        List<ProductDTO> products= adminService.products();

        log.info("AdminController - adminIndex : "+products);

        model.addAttribute("products",products);

        //상품현황 끝

        //여기는 회원현황 시작
        log.info("AdminController-adminIndex-User : 들어옴");

        List<UserDTO> users = userService.allUser();

        log.info("AdminController-adminIndex-User :"+users);

        model.addAttribute("users",users);


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
        UserDTO userDTO = userService.getUserByUid(uid);//유저 정보를 가져옴
        log.info("userDTO :"+userDTO);
        model.addAttribute("user", userDTO);
        return "/admin/user/view";
    }

    @PostMapping("/admin/user/update")
    public String updateUser(@RequestBody UserDTO userDTO, RedirectAttributes redirectAttributes){
        log.info("여기는 들어오니?admin/user/update controller");
        //들어온거는 userDTO 변경된 사항이 들어옴

        UserDTO user = userService.getUserByUid(userDTO.getUid());
        log.info("받아온 아이디로 userDTO를 불러와봤음 : "+user);

        userService.updateUser(userDTO);
        log.info("userDTO : " + userDTO);

        return "/admin/user/list";
    }


    @PostMapping("/admin/user/delete")
    public String deleteUser(@RequestParam("uid") String uid){
        userService.deleteUser(uid);
        return "redirect:/admin/user/list";
    }

}

