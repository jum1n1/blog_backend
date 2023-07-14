package com.sparta.blog_backend.comment.Service;

import com.sparta.blog_backend.Llke.entity.Like;
import com.sparta.blog_backend.Llke.repository.LikeRepository;
import com.sparta.blog_backend.blog.entity.Blog;
import com.sparta.blog_backend.blog.service.BlogService;
import com.sparta.blog_backend.comment.Repository.CommentRepository;
import com.sparta.blog_backend.comment.dto.CommentRequestDto;
import com.sparta.blog_backend.comment.dto.CommentResponseDto;
import com.sparta.blog_backend.comment.entity.Comment;
import com.sparta.blog_backend.user.entity.User;
import com.sparta.blog_backend.user.entity.UserRoleEnum;
import com.sparta.blog_backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final BlogService blogService;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

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

    // 좋아요 누르기
    public void upLike(Long comments_id, Long user_id) {
        Comment comment = commentRepository.findById(comments_id).orElseThrow(() ->
                new IllegalArgumentException("없는 댓글입니다."));

        User user = userRepository.findById(user_id).orElseThrow(() ->
                new IllegalArgumentException("로그인 정보기 없습니다."));

        Optional<Like> isLike = likeRepository.findByCommentAndUser(comment,user);

        isLike.ifPresentOrElse(
                like -> {
                    likeRepository.delete(like);
                    comment.subLikeCount(like);
                    comment.updateLikeCount();
                },
                () ->{
                    Like like = new Like(user,comment);

                    comment.mappingFeedLike(like);
                    user.mappingLike(like);
                    comment.updateLikeCount();

                    likeRepository.save(like);
                }
        );
    }

    // 종아요 조회
    public boolean isLiked(Long comments_id, Long user_id) {
        Comment comment = commentRepository.findById(comments_id).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 댓글입니다"));

        User user = userRepository.findById(user_id).orElse(new User());
        Optional<Like> isLike = likeRepository.findByCommentAndUser(comment,user);
        boolean isLiked = Like.isLiked(isLike);
        return isLiked;
    }
}
