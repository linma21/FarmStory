package kr.co.farmstory.service;

import com.querydsl.core.Tuple;
import kr.co.farmstory.dto.CommentDTO;
import kr.co.farmstory.entity.Comment;
import kr.co.farmstory.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    // 댓글 목록 조회
    public ResponseEntity<List<CommentDTO>> selectComments(int ano){
        log.info("selectComments...1" + ano);
        // 조인 결과값 받아오기
        List<Tuple> results = commentRepository.selectComments(ano);
        log.info("selectComments...2");
        // DTO List로 변환
        List<CommentDTO> dtoList = results.stream()
                .map(tuple ->{
                    log.info("selectComments...3 tuple :" + tuple);
                    Comment comment = tuple.get(0, Comment.class);
                    String nick = tuple.get(1, String.class);
                    comment.setNick(nick);
                    log.info("selectComments...4" + comment);
                    return modelMapper.map(comment, CommentDTO.class);
                })
                .toList();

        return ResponseEntity.ok().body(dtoList);
    }
}
