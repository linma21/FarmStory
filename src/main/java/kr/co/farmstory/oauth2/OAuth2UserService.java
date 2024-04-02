package kr.co.farmstory.oauth2;


import kr.co.farmstory.entity.User;
import kr.co.farmstory.repository.UserRepository;
import kr.co.farmstory.security.MyUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        String accessToken = userRequest.getAccessToken().getTokenValue();
        log.info("loadUser...1"+accessToken);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        log.info("loadUser...2"+provider);

        OAuth2User oAuth2User= super.loadUser(userRequest);
        log.info("loadUser...3"+oAuth2User);

        Map<String, Object> attributes =  oAuth2User.getAttributes();
        log.info("loadUser...4"+attributes);


        String email=null;
        String name = null;

        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");


        //프로바이더에 따라서 다르게 email과 name에 들어감
        if ("google".equals(provider)) {
            email = (String) attributes.get("email");
            name = (String) attributes.get("name");
            // 구글 속성 사용
        } else if ("kakao".equals(provider)) {
            email = (String) kakaoAccount.get("email");
            name = (String) profile.get("nickname");
            // 카카오 속성 사용
        }

        /*

        Map<String,Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");


        //회원가입 처리
        //String email = (String) attributes.get("email");
        //String name = (String) attributes.get("name");
        String email = (String) kakaoAccount.get("email");
        String name = (String)attributes.get("profile_nickname");
        String uid = email.substring(0,email.lastIndexOf("@"));


*/

        User user = userRepository.findById(email)
                .orElse(User.builder()
                        .uid(email)
                        .email(email)
                        .name(name)
                        .nick(name)
                        .role("USER")
                        .provider(provider)
                        .build());

        //저장 or 수정
            userRepository.save(user);


            //SecurityContextHolder principal(사용자 인증 객체)로 저장
        return MyUserDetails.builder()
                .user(user)
                .build();
    }
}
