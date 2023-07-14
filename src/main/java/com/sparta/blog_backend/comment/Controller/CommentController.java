package com.sparta.blog_backend.comment.Controller;

import com.sparta.blog_backend.comment.Service.CommentService;
import com.sparta.blog_backend.comment.dto.CommentRequestDto;
import com.sparta.blog_backend.comment.dto.CommentResponseDto;
import com.sparta.blog_backend.security.UserDetailsImpl;
import com.sparta.blog_backend.user.dto.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments")
    public ResponseEntity<CommentResponseDto> createComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentRequestDto requestDto) {
        CommentResponseDto result = commentService.createComment(requestDto, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<ApiResponseDto> updateComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @RequestBody CommentRequestDto requestDto) {
        try {
            CommentResponseDto result = commentService.updateComment(id, requestDto, userDetails.getUser());
            return ResponseEntity.ok().body(result);
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("작성자만 수정 할 수 있습니다", HttpStatus.BAD_REQUEST.value()));
        }
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<ApiResponseDto> deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        try {
            commentService.deleteComment(id, userDetails.getUser());
            return ResponseEntity.ok().body(new ApiResponseDto("댓글 삭제 성공", HttpStatus.OK.value()));
        } catch (RejectedExecutionException e){
            return ResponseEntity.badRequest().body(new ApiResponseDto("작성자만 삭제 할 수 있습니다",HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 좋아요 누르기
    @PostMapping("/comments/{id}/like")
    public ResponseEntity<ApiResponseDto> upLike(@PathVariable Long id,@AuthenticationPrincipal UserDetailsImpl userDetails ){
        commentService.upLike(id,userDetails.getUser().getId());

        return ResponseEntity.ok().body(new ApiResponseDto("게시글에 좋아요를 눌렀습니다!",HttpStatus.OK.value()));
    }

    // 종아요 조회
    @GetMapping("/comments/{id}/like")
    public boolean isLiked(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.isLiked(id, userDetails.getUser().getId());
    }
}
