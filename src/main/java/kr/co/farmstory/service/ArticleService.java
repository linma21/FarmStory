package kr.co.farmstory.service;

import com.querydsl.core.Tuple;
import kr.co.farmstory.dto.ArticleDTO;
import kr.co.farmstory.dto.PageRequestDTO;
import kr.co.farmstory.dto.PageResponseDTO;
import kr.co.farmstory.entity.Article;
import kr.co.farmstory.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;
    // 기본 글 목록 조회
    public PageResponseDTO selectArticles(PageRequestDTO pageRequestDTO){

        log.info("selectArticles...1");
        Pageable pageable = pageRequestDTO.getPageable("no");

        log.info("selectArticles...2");
        String cate = pageRequestDTO.getCate();

        Page<Tuple> pageArticles = articleRepository.selectArticles(pageRequestDTO, pageable);

        log.info("selectArticles...3" + pageArticles);

        // Page<Tuple>을 List<ArticleDTO>로 변환
        List<ArticleDTO> dtoList = pageArticles.getContent().stream()
                .map(tuple ->{
                    log.info("tuple : "+ tuple);
                    Article article = tuple.get(0, Article.class);
                    String nick = tuple.get(1, String.class);
                    article.setNick(nick);

                    log.info("article : "+ article);

                    return modelMapper.map(article, ArticleDTO.class);
                })
                .toList();
        log.info("selectArticles...4" +dtoList );
        int total = (int) pageArticles.getTotalElements();

        // List<articleDTO>와 page 정보 리턴
        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();
    }
    // 검색 글 목록 조회
    public PageResponseDTO searchArticles(PageRequestDTO pageRequestDTO){

        Pageable pageable = pageRequestDTO.getPageable("no");

        Page<Tuple> pageArticles = articleRepository.searchArticles(pageRequestDTO, pageable);

        List<ArticleDTO> dtoList = pageArticles.getContent().stream()
                .map(tuple ->{
                    log.info("tuple : "+ tuple);
                    Article article = tuple.get(0, Article.class);
                    String nick = tuple.get(1, String.class);
                    article.setNick(nick);
                    log.info("article : "+ article);
                    return modelMapper.map(article, ArticleDTO.class);
                })
                .toList();

        int total = (int) pageArticles.getTotalElements();

        // List<articleDTO>와 page 정보 리턴
        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();
    }
    // 글 상세 조회, 글 조회수 ++ 트랜잭션
    @Transactional
    public ArticleDTO selectArticle(int ano){
        Optional<Article> optArticle = articleRepository.findById(ano);
        log.info("selectArticle ... 1 : " + optArticle.toString());

        ArticleDTO articleDTO = null;

        if(optArticle.isPresent()){
            Article article = optArticle.get();
            log.info("selectArticle ... 2 : " + article.toString());
            articleDTO = modelMapper.map(article, ArticleDTO.class);
            log.info("selectArticle ... 3 articleDTO1 : " + articleDTO.toString());

            // Article hit ++
            articleRepository.incrementHitByAno(ano);
        }
        log.info("selectArticle ... 4 articleDTO2 : " + articleDTO.toString());
        return articleDTO;
    }

}