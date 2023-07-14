package com.sparta.blog_backend.blog.entity;

import com.sparta.blog_backend.Llke.entity.Like;
import com.sparta.blog_backend.blog.dto.BlogRequestDto;
import com.sparta.blog_backend.comment.entity.Comment;
import com.sparta.blog_backend.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "blog") // 매핑용 테이블 이름
@NoArgsConstructor // 파라미터가 없는 기본 생성자를 생성
public class Blog extends Timestamped { // createdAt, modifiedAt은 부모 클래스인 Timestamped에 있음

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title; //제목

    @Column(nullable = false)
    private String content; // 내용

    @Column
    private Long likeCount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "blog", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @OneToMany(mappedBy = "blog")
    private List<Like> LikeList = new ArrayList<>();

    public Blog(BlogRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUser(User user){
        this.user = user;
    }

    // 좋아요 목록에서 삭제
    public void subLikeCount(Like like) {
        this.LikeList.remove(like);
    }

    // 좋아요 갯수 확인
    public void updateLikeCount() {
        this.likeCount = (long)this.LikeList.size();
    }

    // 좋아요 추가
    public void mappingFeedLike(Like like) {
        this.LikeList.add(like);
    }
}
