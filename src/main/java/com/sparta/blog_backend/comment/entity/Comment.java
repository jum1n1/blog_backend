package com.sparta.blog_backend.comment.entity;

import com.sparta.blog_backend.Llke.entity.Like;
import com.sparta.blog_backend.blog.entity.Blog;
import com.sparta.blog_backend.blog.entity.Timestamped;
import com.sparta.blog_backend.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "comment")
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String body;

    @Column
    private Long likeCount;

    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "comment")
    private List<Like> LikeList = new ArrayList<>();

    public Comment(String body){
        this.body = body;
    }

    public void setBody(String body){
        this.body = body;
    }

    public void setUser(User user){
        this.user = user;
    }

    public void setBlog(Blog blog){
        this.blog = blog;
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
