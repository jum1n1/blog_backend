package com.sparta.blog_backend.blog.repository;

import com.sparta.blog_backend.blog.entity.Blog;
import com.sparta.blog_backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog,Long> {

    Optional<Blog> findByUser(User user);

}

