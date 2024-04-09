package kr.co.farmstory.controller;

import kr.co.farmstory.dto.*;
import jakarta.servlet.http.HttpSession;
import kr.co.farmstory.dto.MarketPageRequestDTO;
import kr.co.farmstory.dto.MarketPageResponseDTO;
import kr.co.farmstory.dto.ProductDTO;
import kr.co.farmstory.dto.UserDTO;
import kr.co.farmstory.entity.OrderDetail;
import kr.co.farmstory.entity.Orders;
import kr.co.farmstory.entity.Product;
import kr.co.farmstory.service.MarketService;
import kr.co.farmstory.service.ReviewService;
import kr.co.farmstory.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

import static kr.co.farmstory.entity.QProduct.product;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MarketController {

    private final MarketService marketService;
    private final ReviewService reviewService;
    private final UserService userService;

    // 장보기 글목록 페이지 매핑 (cate, pg, type, keyword 받음)
    @GetMapping("/market/newlist")
    public String marketList(Model model, MarketPageRequestDTO marketPageRequestDTO){

        MarketPageResponseDTO pageResponseDTO = marketService.selectProducts(marketPageRequestDTO);
        log.info("pageResponseDTO : " + pageResponseDTO.toString());
        model.addAttribute(pageResponseDTO);
        return "/market/newlist";
    }

    // 장보기 글보기 페이지 매핑 (cate, pg, type, keyword 받음)
    @GetMapping("/market/newview")
    public String marketView(Model model, MarketPageRequestDTO marketPageRequestDTO, int prodno){
        // 상품 조회
        ProductDTO productDTO = marketService.selectProduct(prodno);
        // 리뷰 조회
        ReviewPageResponseDTO reviewPage = reviewService.selectReviews(prodno);
        log.info("장보기 글보기 Cont "+ reviewPage.toString());
        // 리뷰 별점 - 평균, 비율 구하기
        ReviewRatioDTO reviewRatioDTO = reviewService.selectForRatio(prodno);

        model.addAttribute(productDTO);
        model.addAttribute(marketPageRequestDTO);
        model.addAttribute("reviewPage", reviewPage);
        return "/market/newview";
    }


    // 주문목록 페이지 매핑
    @GetMapping("/market/orderList")
    public String getOrderDetails(Model model, String uid, PageRequestDTO pageRequestDTO) {

        PageResponseDTO pageResponseDTO = marketService.findOrderListByUid(uid, pageRequestDTO);
        log.info("getOrderDetails Cont : " + pageResponseDTO);
        model.addAttribute("pageResponseDTO", pageResponseDTO);

        return "/market/orderList";
    }

    // 장바구니 목록 페이지 매핑
    @GetMapping("/market/newcart")
    public String marketCart(Model model, String uid){
        log.info("marketCartController1");
        //marketService.insertProduct(uid, prodno);

        List<ProductDTO> productDTO = marketService.selectCartForMarket(uid);
        log.info("marketCartController2 : " + productDTO.toString());
        model.addAttribute("productDTO", productDTO);
        return "/market/newcart";
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

    // 장바구니에서 선택 상품 삭제
    @PostMapping("/market/deleteCart")
    public ResponseEntity<?> deleteCart(@RequestBody Map<String, int[]> requestData){
        int[] cart_prodNos = requestData.get("cart_prodNo");
        log.info("controller-cart_prodNos : " + Arrays.toString(cart_prodNos));
        return marketService.deleteCart(cart_prodNos);
    }

    // 주문하기 페이지 매핑
    @GetMapping("/market/neworder")
    public String marketOrder(HttpSession httpSession,
                              Model model){
        // Post(/market/order)에서 redirectAttributes 로 보낸 데이터 접근
        UserDTO userDTO = (UserDTO) httpSession.getAttribute("userDTO");
        List<ProductDTO> productDTOs = (List<ProductDTO>) httpSession.getAttribute("productDTO");

        log.info("marketOrder GET : " + productDTOs.toString());
        log.info("marketOrder GET : " + userDTO.toString());

        // View 출력을 위해 데이터 넘겨주기
        model.addAttribute("userDTO", userDTO);
        model.addAttribute("productDTOs", productDTOs);
        return "/market/neworder";
    }

    // 주문하기 페이지 매핑 - 장바구니에서 주문 정보 받기
    @PostMapping("/market/order")
    public ResponseEntity<?> marketOrder(HttpSession httpSession,
                                         @RequestBody Map<String, Object> requestMap) {
        // 요청 본문에서 uid와 cart_prodNo를 추출합니다.
        String uid = (String) requestMap.get("uid");
        List<Integer> cart_prodNoList = (List<Integer>) requestMap.get("cart_prodNo");
        log.info("marketOrder Cont : " + uid);
        log.info("cart_prodNoList : " + cart_prodNoList.toString());

        // 주문자와 포인트 정보 가져오기
        UserDTO userDTO = userService.selectUserForOrder(uid);
        log.info("주문하기 페이지 Cont 1 : " + userDTO.toString());

        // 상품 정보 가져오기 - 장바구니 목록 불러오기와 같음
        List<ProductDTO> productDTO = marketService.selectCartForMarket(uid);
        log.info("주문하기 페이지 Cont 2 : " + productDTO.toString());
        // session에 데이터 저장
        httpSession.setAttribute("userDTO", userDTO);
        httpSession.setAttribute("productDTO", productDTO);

        Map<String, String> response = new HashMap<>();
        if (userDTO != null && productDTO != null){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/product/details")
    public String getProductDetails(Model model) {
        Product product = new Product(); // 여기서 실제로는 제품 정보를 데이터베이스나 다른 소스에서 가져와야 합니다.
        product.setPrice((int) 123456.78); // 예시 가격 설정

        // 숫자 포맷팅
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.KOREA);
        String formattedPrice = numberFormat.format(product.getPrice());

        // 모델에 포맷팅된 가격 추가
        model.addAttribute("formattedPrice", formattedPrice);

        return "productDetails"; // 뷰 이름 반환
    }


    @PostMapping("/market/orders")
    public ResponseEntity<?> orders(@RequestBody OrderDTO orderDTO){

        log.info("여기까지는 들어오나?");

        log.info("orderDTO : "+ orderDTO);

        marketService.orders(orderDTO);

        Map<String, String> response = new HashMap<>();

        response.put("result","1");

        return ResponseEntity.ok().body(response);
    }

    //결제 후 장바구니에 있는 상품들을 삭제
    @PostMapping("/market/cartProdDelete")
    public ResponseEntity<?> cartDelete(@RequestBody List<Map<String, Integer>> productList) {
        //productList에 담긴 값을 가져와서 int로 변환

        log.info("여기로 들어오는지 확인");

        int[] prodNos = productList.stream()
                .mapToInt(map -> (map.get("prodNo")))
                .toArray();

        log.info("controller-cart_prodNos(내 컨트롤러) : " + Arrays.toString(prodNos));

        return marketService.deleteCart(prodNos);
    }


    @GetMapping("/market/orderNo/{uid}")
    @ResponseBody
    public int orderNo(@PathVariable("uid")String uid){
        //orderNo값을 들고왔음!

        int orderNo= marketService.selectOrderNo(uid);

        log.info("orderNo "+orderNo);

        return orderNo;

    }

    @PostMapping("/market/saveOrderDetail")
    public ResponseEntity<?> orderDetails(@RequestBody List<Map<String, Object>> requestData){


        int[] count = requestData.stream()
                .mapToInt(map -> (int) map.get("count"))
                .toArray();

        int[] detailNo = requestData.stream()
                .mapToInt(map -> (int) map.get("detailNos"))
                .toArray();

        int order = requestData.stream()
                .mapToInt(map -> (int) map.get("orders"))
                .findFirst()
                .orElse(0);

        log.info("여기로 들어오는지 확인");
        log.info("controller-cart_prodNos(지금 테스트 )1 : " + Arrays.toString(count));
        log.info("controller-cart_prodNos(지금 테스트 )2 : " + Arrays.toString(detailNo));
        log.info("controller-cart_prodNos(지금 테스트 )3 : " + order);


        Map<String, String> response = new HashMap<>();

        response.put("result","1");

        return ResponseEntity.ok().body(response);

    }

    // market/view에서 market/order로 바로 넘어가는 controller
    @PostMapping("/market/moveOrder")
    public ResponseEntity<?> moveOrder(HttpSession httpSession,
                          @RequestBody Map<String, Object> requestMap) {
        log.info("requestMap : " + requestMap.toString());
        String uid = (String) requestMap.get("uid");
        int prodno = Integer.parseInt((String) requestMap.get("prodno"));
        int prodCount = Integer.parseInt((String) requestMap.get("prodCount"));
        log.info("uid : " + uid);
        log.info("prodno : " + prodno);

        // 주문자와 포인트 정보 가져오기
        UserDTO userDTO = userService.selectUserForOrder(uid);
        log.info("주문하기 페이지 Cont 1 : " + userDTO.toString());

        List<ProductDTO> productDTO = new ArrayList<>();
        ProductDTO product = marketService.selectProduct(prodno);
        product.setCount(prodCount);
        productDTO.add(product);

        log.info("주문하기 페이지 Cont 2 : " + productDTO);
        // session에 데이터 저장
        httpSession.setAttribute("userDTO", userDTO);
        httpSession.setAttribute("productDTO", productDTO);

        Map<String, String> response = new HashMap<>();
        if (userDTO != null && productDTO != null){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // market/view에서 장바구니에 품목 추가
    @PostMapping("/market/addCart")
    public ResponseEntity<?> addCart(@RequestBody Map<String, Object> requestMap){
        log.info("requestMap : " + requestMap.toString());
        String uid = (String) requestMap.get("uid");
        int prodno = Integer.parseInt((String) requestMap.get("prodno"));
        int prodCount = Integer.parseInt((String) requestMap.get("prodCount"));

        return marketService.addProductForCart(uid, prodno, prodCount);
    }
}

