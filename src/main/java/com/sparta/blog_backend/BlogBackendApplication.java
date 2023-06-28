package com.sparta.blog_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // Jpa로 성성,수정 일시를 입력 할 수 있도록 Enable해주겠다./ Auditing : 데이터 생성, 수정
@SpringBootApplication // spring boot 임을 나타냅
public class BlogBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogBackendApplication.class, args);
    }

}
