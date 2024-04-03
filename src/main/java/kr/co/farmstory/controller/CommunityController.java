package kr.co.farmstory.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.farmstory.dto.ArticleDTO;
import kr.co.farmstory.dto.PageRequestDTO;
import kr.co.farmstory.dto.PageResponseDTO;
import kr.co.farmstory.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CommunityController {

    private final ArticleService articleService;

    // 글 목록 조회
    @GetMapping("/community/list")
    public String community(Model model, String cate, PageRequestDTO pageRequestDTO){
        PageResponseDTO pageResponseDTO = null;

        if(pageRequestDTO.getKeyword() == null) {
            // 일반 글 목록 조회
            pageResponseDTO = articleService.selectArticles(pageRequestDTO);
        }else {
            // 검색 글 목록 조회
            pageResponseDTO = articleService.searchArticles(pageRequestDTO);
        }
        log.info("pageResponseDTO : " + pageResponseDTO);

        model.addAttribute(pageResponseDTO);
        return "/community/list";
    }
    // 글 상세 보기
    @GetMapping("/community/view")
    public String communityView(Model model, String cate,int ano, PageRequestDTO pageRequestDTO){
        log.info("communityView...1 : " + ano);
        // 글 조회
        ArticleDTO article = articleService.selectArticle(ano);

        log.info("communityView...2 : " + article.toString());

        // 페이지 정보 build
        PageResponseDTO pageResponseDTO = PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .build();

        model.addAttribute("pageResponseDTO", pageResponseDTO);
        model.addAttribute("article", article);

        return "/community/view";
    }
    // 글 쓰기
    @GetMapping("/community/write")
    public String write(Model model, @ModelAttribute("cate") String cate, PageRequestDTO pageRequestDTO){

        PageResponseDTO pageResponseDTO = PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .build();

        model.addAttribute(pageResponseDTO);
        return "/community/write";
    }

    @PostMapping("/community/write")
    public String write(ArticleDTO articleDTO){

        articleService.insertArticle(articleDTO);
        return "redirect:/community/list?cate="+articleDTO.getCate();
    }
    // 글 수정
    @PostMapping("/community/modify")
    public ResponseEntity<?> modify(ArticleDTO articleDTO) {

        log.info("modifyArticle : " + articleDTO.toString());
        return articleService.updateArticle(articleDTO);
    }
    @DeleteMapping("/community/{ano}")
    public ResponseEntity<?> deleteArticle(@PathVariable("ano") int ano){
        return articleService.deleteArticle(ano);
    }
}
