package kr.co.farmstory.controller;

import kr.co.farmstory.dto.TermsDTO;
import kr.co.farmstory.entity.Terms;
import kr.co.farmstory.repository.TermsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TermsController {

    private TermsRepository termsRepository;

    @Autowired
    public TermsController(TermsRepository termsRepository) {
        this.termsRepository = termsRepository;
    }

    // DB에서 약관정보 조회
    @GetMapping("/terms")
    public String terms(Model model) {
        Terms terms = termsRepository.findById("terms").orElseThrow(
                () -> new IllegalArgumentException("약관 정보를 찾을 수 없습니다.")
        );

        // 조회한 약관정보 DTO로 변환
        TermsDTO termsDTO = TermsDTO.builder()
                .terms(terms.getTerms())
                .privacy(terms.getPrivacy())
                .build();

        // 모델에 약관정보 추가
        model.addAttribute("terms", termsDTO);

        // 뷰 이름 반환
        return "terms";

    }
}
