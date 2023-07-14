package com.sparta.blog_backend.user.entity;

import com.sparta.blog_backend.Llke.entity.Like;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
@EqualsAndHashCode
public class User {

    @Id // 기본키(pk) 매핑
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 자동 생성, IDENTITY => 키 생성을 DB에 위임
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Like> likes = new ArrayList<>();


    public User(String username, String password, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // 유저가 해당 좋아요를 눌렀는지 확인
    public void mappingLike(Like like){
        this.likes.add(like);
    }
}
