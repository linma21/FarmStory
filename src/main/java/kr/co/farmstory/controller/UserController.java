package kr.co.farmstory.controller;


import kr.co.farmstory.dto.TermsDTO;
import kr.co.farmstory.entity.Terms;
import kr.co.farmstory.repository.TermsRepository;
import kr.co.farmstory.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {

    private final TermsRepository termsRepository;


    @GetMapping("/user/login")
    public String loginPage() {
        return "/user/login";
    }

    @GetMapping("/user/findId")
    public String findId(){
        return "user/findId";
    }


    @GetMapping("/user/findPassword")
    public String findPassword(){
        return "user/findPassword";
    }


    // DB에서 약관정보 조회
    @GetMapping("/user/terms")
    public String terms(Model model) {

        Terms terms = termsRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("약관 정보를 찾을 수 없습니다1."));

        TermsDTO termsDTO = TermsDTO.builder()
                .terms(terms.getTerms())
                .privacy(terms.getPrivacy())
                .build();

        model.addAttribute("termsDTO", termsDTO);

        return "/user/terms";
    }

    @GetMapping("/user/register")
    public String register(){

        return "/user/register";

    }

}
