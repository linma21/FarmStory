package kr.co.farmstory.service;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import kr.co.farmstory.dto.UserDTO;
import kr.co.farmstory.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;


@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;


    public int selectCountUser(String type,String value){

        return userMapper.selectCountUser(type,value);
    }

    public void insertUser(UserDTO userDTO){

        String encoded = passwordEncoder.encode(userDTO.getPass());

        userDTO.setPass(encoded);

        userMapper.insertUser(userDTO);
    }


    @Value("${spring.mail.username}")
    private String sender;

    public void sendEmailCode(HttpSession session, String receiver){

        log.info("sender : " + sender);

        // MimeMessage 생성
        MimeMessage message = javaMailSender.createMimeMessage();

        // 인증코드 생성 후 세션 저장
        int code = ThreadLocalRandom.current().nextInt(100000, 1000000);
        session.setAttribute("code", String.valueOf(code));

        String title = "farmstory 인증코드 입니다.";
        String content = "<h1>인증코드는 " + code + "입니다.</h1>";

        try {
            message.setSubject(title);
            message.setFrom(new InternetAddress(sender, "보내는 사람", "UTF-8"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            message.setSubject(title);
            message.setContent(content, "text/html;charset=UTF-8");

            javaMailSender.send(message);

        }catch(Exception e){
            log.error("sendEmailConde : " + e.getMessage());
        }

    }


    // 아이디 비밀번호 확인후 로그인
    public boolean selectUser(String uid, String pass){
        UserDTO user = userMapper.selectUserByUid(uid);

        if(user != null){
            return passwordEncoder.matches(pass, user.getPass());
        }
        return false;
    }


    // 아이디/비밀번호 찾기
    public String findUserIdByNameAndEmail(String name, String email){
        UserDTO user = userMapper.selectUserByNameAndEmail(name, email);
        return user != null ? user.getUid() : null;
    }

    public void updateUserPassword(String uid, String newPassword){

        String encodedPass = passwordEncoder.encode(newPassword);
        userMapper.updateUserPassword(uid, encodedPass);

    }


}
