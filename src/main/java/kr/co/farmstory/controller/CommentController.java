package kr.co.farmstory.controller;

import kr.co.farmstory.dto.CommentDTO;
import kr.co.farmstory.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    // 댓글 목록 조회
    @GetMapping("/comment/{ano}")
    public ResponseEntity<List<CommentDTO>> commentList(@PathVariable("ano") int ano){
        log.info("commentList : "+ano);
        return commentService.selectComments(ano);
    }
}
