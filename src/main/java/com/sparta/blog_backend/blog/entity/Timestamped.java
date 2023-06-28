package com.sparta.blog_backend.blog.entity;


import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Timestamped {
    // 상속 받은 클래스가 실행되얶을 때 날짜가 생성됨

    // 생성 날짜
    @CreatedDate
    @Column(updatable = false, name="created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createAt;

    // 수정 날짜 
    @LastModifiedDate
    @Column(name="modified_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedAt;
}