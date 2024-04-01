package kr.co.farmstory.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.co.farmstory.dto.UserDTO;
import kr.co.farmstory.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/user/login")
    public String login(){

        return "/user/login";
    }


    @GetMapping("/user/register")
    public String register(){

        return "/user/register";

    }

    //타입에 따라 db에 있는지 중복확인을 시켜줌.만약 type이 email이라면 이메일을 보내주는 역할
    @ResponseBody
    @GetMapping("/user/{type}/{value}")
    public ResponseEntity<?> checkUser(HttpSession session,
                                       @PathVariable("type") String type,
                                       @PathVariable("value") String value) {

        int count = userService.selectCountUser(type,value);

        log.info("count : " + count);

        if (type.equals("email") && count<=0) {
            log.info("email : " + value);
            userService.sendEmailCode(session, value);
        }

        // Json 생성
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", count);

        return ResponseEntity.ok().body(resultMap);
    }

    // 이메일 인증 코드 검사
    @ResponseBody
    @GetMapping("/email/{code}")
    public ResponseEntity<?> checkEmail(HttpSession session, @PathVariable("code") String code) {

        String sessionCode = (String) session.getAttribute("code");

        if (sessionCode.equals(code)) {
            // Json 생성
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("result", true);

            return ResponseEntity.ok().body(resultMap);
        } else {
            // Json 생성
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("result", false);

            return ResponseEntity.ok().body(resultMap);
        }
    }


    @PostMapping("/user/register")
    public String register(HttpServletRequest req, UserDTO userDTO) {

        String regip = req.getRemoteAddr();
        userDTO.setRegip(regip);

        log.info(userDTO.toString());

        userService.insertUser(userDTO);

        return "redirect:/user/register?success=200";
    }

}
