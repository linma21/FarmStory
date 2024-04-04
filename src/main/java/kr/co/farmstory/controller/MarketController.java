package kr.co.farmstory.controller;

import kr.co.farmstory.dto.MarketPageRequestDTO;
import kr.co.farmstory.dto.MarketPageResponseDTO;
import kr.co.farmstory.dto.ProductDTO;
import kr.co.farmstory.entity.Product;
import kr.co.farmstory.repository.MarketRepository;
import kr.co.farmstory.service.MarketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    // 장바구니 목록 페이지 매핑
    @GetMapping("/market/cart")
    public String marketCart(Model model, String uid){
        log.info("marketCartController1");
        List<ProductDTO> productDTO = marketService.selectCartForMarket(uid);
        log.info("marketCartController2 : " + productDTO.toString());
        model.addAttribute("productDTO", productDTO);
        return "/market/cart";
    }

    // 장바구니에서 수량 변경 반영
    @PostMapping("/market/modCount")
    public ResponseEntity<?> modifyCount(@RequestBody Map<String, int[]> requestData) {
        int[] cart_prodNos = requestData.get("cart_prodNo");
        int[] counts = requestData.get("count");
        log.info(Arrays.toString(cart_prodNos));
        log.info(Arrays.toString(counts));
        return marketService.modifyCount(cart_prodNos, counts);
    }

    // 주문하기 페이지 매핑
    /*
    @GetMapping("/market/order")
    public String marketOrder(){

        return "/market/order";
    }
     */
    @PostMapping("market/order")
    public String marketOrder(@RequestBody Map<String, Object> requestMap) {
        // 요청 본문에서 uid와 cart_prodNo를 추출합니다.
        String uid = (String) requestMap.get("uid");
        List<Integer> cart_prodNoList = (List<Integer>) requestMap.get("cart_prodNo");

        log.info("uid : " + uid);
        log.info("cart_prodNoList : " + cart_prodNoList.toString());

        return "/market/order";
    }
}
