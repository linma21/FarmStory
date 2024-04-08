package kr.co.farmstory.controller;

import jakarta.servlet.http.HttpSession;
import kr.co.farmstory.dto.UserDTO;
import kr.co.farmstory.entity.User;
import kr.co.farmstory.security.MyUserDetails;
import kr.co.farmstory.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.info.BuildProperties;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MainController {

    private final BuildProperties buildProperties;
    private final UserService userService;

    // 메인화면
    @GetMapping(value = {"/", "/newindex"})
    public String index(Model model) {

        // 상단 BuildProperties 주입
        String appVersion = buildProperties.getVersion();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = null;

        log.info(appVersion);
        log.info("사용자가 로그인을 했는지 안했는지 띄워주기(MainController) : " + authentication);

        model.addAttribute("appVersion", appVersion);

        if (authentication != null && authentication.isAuthenticated()&& !(authentication instanceof AnonymousAuthenticationToken)) {//로그인이 되었을 때

            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                uid = ((UserDetails) principal).getUsername();
            } else {
                // 인증된 사용자가 UserDetails를 구현하지 않은 경우에 대한 처리
                uid = principal.toString();
            }

            log.info("uid 찍어보기 : "+uid);

            // 아이디값을 가진 사용자의 hp값을 들고오기
            UserDTO userDTO = userService.findById(uid);

            if (userDTO.getHp() == null || userDTO.getHp().isEmpty()) {// 만약에 hp가 null이면 사용자 정보 수정 페이지로 이동
                model.addAttribute("userDTO", userDTO);
                return "/test";
            } else {// hp가 null이 아니면 기본 페이지 띄워주기
                return "/newindex";
            }
        }

        // 로그인을 하지 않았을 때에 대한 처리
        return "/newindex";
    }


    //소셜로그인 후 추가정보를 입력
    @PostMapping("/user/social")
    public String social(UserDTO userDTO) {

        log.info("userDTO값 방출1 : " + userDTO.getUid());
        log.info("userDTO값 방출2 : " + userDTO.getHp());
        log.info("userDTO값 방출3 : " + userDTO.getZip());


        log.info("추가정보 입력후 : " + userDTO);

        userService.social(userDTO);


        return "/newindex";
    }


    // 준비중 페이지를 위한 메서드 추가
    @GetMapping("/notfound")
    public String notFound() {
        // "notfound.html" 템플릿으로 리다이렉트
        return "notfound";
    }

    // 팜스토리 소개
    @GetMapping("/introduction/hello")
    public String hello() {

        return "/introduction/hello";

    }

    // 찾아오는 길
    @GetMapping("/introduction/direction")
    public String direction() {

        return "/introduction/direction";
    }

}
