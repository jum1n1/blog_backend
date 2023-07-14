package com.sparta.blog_backend.comment.entity;

import com.sparta.blog_backend.blog.entity.Blog;
import com.sparta.blog_backend.blog.entity.Timestamped;
import com.sparta.blog_backend.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

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


}
