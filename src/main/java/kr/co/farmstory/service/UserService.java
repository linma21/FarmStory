package kr.co.farmstory.service;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.Id;
import jakarta.servlet.http.HttpSession;
import kr.co.farmstory.dto.*;
import kr.co.farmstory.entity.Product;
import kr.co.farmstory.entity.User;
import kr.co.farmstory.mapper.UserMapper;
import kr.co.farmstory.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;


    //회원 등록이 되어 있는지 확인하는 기능(0또는 1)
    public int selectCountUser(String type,String value){

        return userMapper.selectCountUser(type,value);
    }

    //회원 가입 기능
    public void insertUser(UserDTO userDTO){

        String encoded = passwordEncoder.encode(userDTO.getPass());

        userDTO.setPass(encoded);

        userMapper.insertUser(userDTO);
    }


    @Value("${spring.mail.username}")//이메일 보내는 사람 주소
    private String sender;
    //이메일 보내기 기능
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

    public UserDTO findById(String uid){
        return userMapper.findById(uid);
    }


    public void updateUserPassword(String uid, String newPassword){

        String encodedPass = passwordEncoder.encode(newPassword);
        userMapper.updateUserPassword(uid, encodedPass);

    }


    // 회원목록
    public List<UserDTO> getUserList(PageRequestDTO pageRequestDTO) {
        // UserService 내 getUserList 메소드 수정
        Pageable pageable = pageRequestDTO.getPageable("regDate");
        Page<User> result = userRepository.findAll(pageable);

        return result.getContent().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    public int getTotalCount(PageRequestDTO pageRequestDTO) {
        // 조건에 맞는 사용자 수를 반환하는 로직을 구현합니다.
        // 예시에서는 간단히 모든 사용자 수를 반환합니다.
        return (int) userRepository.count();
    }

    // 상세페이지(id로 그 유저의 정보를 가져온다)
    public UserDTO getUserByUid(String uid){
        User user = userRepository.findById(uid).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UserDTO(user.getUid(), user.getPass(), user.getName(), user.getEmail(), user.getNick(), user.getHp(), user.getRole(), user.getLevel(), user.getZip(), user.getAddr1(), user.getAddr2(), user.getRegip(), user.getRegDate(), user.getLeaveDate(), user.getProvider());
    }

    public void updateUser(UserDTO userDTO) {
        log.info("updateUser....1");
        User user = userRepository.findById(userDTO.getUid())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userDTO.getUid()));
        log.info("updateUser....2" + user.toString());
        user.setLevel(userDTO.getLevel());
        user.setRole(userDTO.getRole());
        // 여기에 추가적으로 업데이트 해야 할 필드가 있다면 추가합니다.
        log.info("updateUser....3" + user.toString());
        userRepository.save(user); // 변경된 사용자 정보 저장
        log.info("updateUser....4 save");

    }


    public void deleteUser(String uid) {
        // userRepository를 사용하여 사용자를 삭제합니다.


        userRepository.deleteById(uid);
    }

    public List<UserDTO> allUser(){

        List<User> users = userRepository.findAll();

        return users.stream().map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

}

