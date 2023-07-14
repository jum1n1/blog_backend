package com.sparta.blog_backend.comment.Service;

import com.sparta.blog_backend.blog.entity.Blog;
import com.sparta.blog_backend.blog.service.BlogService;
import com.sparta.blog_backend.comment.Repository.CommentRepository;
import com.sparta.blog_backend.comment.dto.CommentRequestDto;
import com.sparta.blog_backend.comment.dto.CommentResponseDto;
import com.sparta.blog_backend.comment.entity.Comment;
import com.sparta.blog_backend.user.entity.User;
import com.sparta.blog_backend.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final BlogService blogService;
    private final CommentRepository commentRepository;

    public CommentResponseDto createComment(CommentRequestDto requestDto, User user){
        Blog blog = blogService.findBlog(requestDto.getPostId());
        Comment comment = new Comment(requestDto.getBody());
        comment.setUser(user);
        comment.setBlog(blog);

        var savedComment = commentRepository.save(comment);

        return new CommentResponseDto(savedComment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto, User user){
        Comment comment = commentRepository.findById(id).orElseThrow();

        if(!user.getRole().equals(UserRoleEnum.ADMIN) && !comment.getUser().equals(user)){
            throw new RejectedExecutionException();
        }

        comment.setBody(requestDto.getBody());

        return new CommentResponseDto(comment);
    }

    public void deleteComment(Long id, User user){
        Comment comment = commentRepository.findById(id).orElseThrow();

        if( !user.getRole().equals(UserRoleEnum.ADMIN) || !comment.getUser().equals(user)){
            throw new RejectedExecutionException();
        }
        commentRepository.delete(comment);
    }
}
