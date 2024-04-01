package kr.co.farmstory.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name="user")
public class User {

    @Id
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

    @CreationTimestamp
    private LocalDateTime regDate;
    private LocalDateTime leaveDate;
    private String provider;

}
