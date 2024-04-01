package kr.co.farmstory.dto;

import lombok.*;
import lombok.experimental.StandardException;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Setter
@Getter
public class UserDTO {

    private String uid;
    private String name;
    private String pass;
    private String nick;
    private String email;
    private int level;
    private String hp;
    private String role;
    private String zip;
    private String addr1;
    private String addr2;
    private String regip;
    private LocalDateTime regDate;
    private LocalDateTime leaveDate;
    private String provider;

}
