# blog_backend

Spring 4주차 과제 

- 추가 요구사항
  - 게시글 좋아요
  - 댓글 좋아요
  - AOP를 통한 예외 처리
 
- 작업하면서 어려웠거나 궁금했던 점
  - AOP의 이론적인 부분을 이해하였으나 실제 작성한 코드를 AOP로 바꾸는 방법이 어렵습니다.
    ex) BlogController에서 수정, 삭제할 때 작성자를 체크하는 부분을 ExceptionAop class에서 한번에 처리한다고 할 때
    2개의 코드 내용이 다르고 예외처리 뿐만 아니라 다른 행동을 하는 코드도 있는데 각 파일에 내용을 어떻게 입력해야할지 잘 모르겠습니다.
 
     
