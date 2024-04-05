package kr.co.farmstory.controller;

import groovy.lang.Tuple;
import kr.co.farmstory.dto.*;
import kr.co.farmstory.entity.Orders;
import kr.co.farmstory.service.MarketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MarketController {

    private final MarketService marketService;
    
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

    // 주문목록
    @GetMapping("/market/orderList")
    public String getOrderDetails(Model model, @RequestParam(required = false) String userId) {
        if (userId == null) {
            userId = "devUser"; // 개발 단계의 임시 사용자 ID
        }
        List<OrderDetailProductDTO> details = marketService.getOrderDetailsWithProductByUserId(userId);
        log.info("details!! : " + details);
        model.addAttribute("details", details);
        return "/market/orderList";
    }
}