package com.sparta.blog_backend.blog.entity;

import com.sparta.blog_backend.blog.dto.BlogRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
//@Setter
@Table(name = "blog") // 매핑용 테이블 이름
@NoArgsConstructor // 파라미터가 없는 기본 생성자를 생성
public class Blog extends Timestamped { // createdAt, modifiedAt은 부모 클래스인 Timestamped에 있음

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title; //제목

    @Column(nullable = false)
    private String name; // 이름

    @Column(nullable = false)
    private String content; // 내용

    @Column(nullable = false)
    private String password; // 비밀번호

    public Blog(BlogRequestDto blogrequestDto) {
        this.title = blogrequestDto.getTitle();
        this.name = blogrequestDto.getName();
        this.content = blogrequestDto.getContent();
        this.password = blogrequestDto.getPassword();
    }

    // Setter => class 위에 붙여도 상관없으나 중요한 데이터이니 따로 set 선언
    public void setTitle(String title){
        this.title = title;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setContent(String content){
        this.content = content;
    }

    // 비밀번호 체크
    public void checkPassword(String inputPassword) {
        if(!password.equals(inputPassword)){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
