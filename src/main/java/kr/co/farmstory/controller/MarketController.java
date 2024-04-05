package kr.co.farmstory.controller;

import kr.co.farmstory.dto.MarketPageRequestDTO;
import kr.co.farmstory.dto.MarketPageResponseDTO;
import kr.co.farmstory.dto.ProductDTO;
import kr.co.farmstory.dto.UserDTO;
import kr.co.farmstory.entity.Product;
import kr.co.farmstory.repository.MarketRepository;
import kr.co.farmstory.service.MarketService;
import kr.co.farmstory.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MarketController {

    private final MarketService marketService;
    private final UserService userService;

    // 장보기 글목록 페이지 매핑 (cate, pg, type, keyword 받음)
    @GetMapping("/market/list")
    public String marketList(Model model, MarketPageRequestDTO marketPageRequestDTO){

        MarketPageResponseDTO pageResponseDTO = marketService.selectProducts(marketPageRequestDTO);
        log.info("pageResponseDTO : " + pageResponseDTO.toString());
        model.addAttribute(pageResponseDTO);
        return "/market/list";
    }

    // 장보기 글보기 페이지 매핑 (cate, pg, type, keyword 받음)
    @GetMapping("/market/view")
    public String marketView(Model model, MarketPageRequestDTO marketPageRequestDTO, int prodno){
        ProductDTO productDTO = marketService.selectProduct(prodno);
        model.addAttribute(productDTO);
        model.addAttribute(marketPageRequestDTO);
        return "/market/view";
    }

    // 장바구니 목록 페이지 매핑
    @GetMapping("/market/cart")
    public String marketCart(Model model, String uid){
        log.info("marketCartController1");
        List<ProductDTO> productDTO = marketService.selectCartForMarket(uid);
        log.info("marketCartController2 : " + productDTO.toString());
        model.addAttribute("productDTO", productDTO);
        return "/market/cart";
    }

    // 주문하기 페이지 매핑
    @GetMapping("/market/order")
    public String marketOrder(Model model, List<Integer> cart_prodNo, String uid){
        // 주문자와 포인트 정보 가져오기
        UserDTO userDTO = userService.selectUserForOrder(uid);
        model.addAttribute("userDTO", userDTO);
        // 상품 정보 가져오기


        return "/market/order";
    }
}
