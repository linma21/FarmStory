package kr.co.farmstory.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="user")
public class User {
    @Id
    private String uid;
    private String pass;
    private String name;
    private String email;
    private String nick;
    private String hp;
    private String role;
    private String level;
    private String zip;
    private String addr1;
    private String addr2;
    private String regip;
    @CreationTimestamp
    private LocalDateTime regDate;
    private LocalDateTime leaveDate;
    private String provider;

}
