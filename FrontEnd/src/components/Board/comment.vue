<template>
  <div class="comment-section">
    <h4>Comments</h4>


    <div v-if="comments.length">
      <div v-for="comment in comments" :key="comment.id" class="comment">
        <div class ="comment-warpper">
        <p><strong>{{ comment.author }}</strong>
          <button class ="reply-to-comment-button" @click="replyComment">대댓글</button>
          <button v-if="sameCommentAuthor(comment.author)" class ="comment-edit-button" @click="editComment(comment.id)">수정</button>
          <button v-if="sameCommentAuthor(comment.author)" class ="comment-delete-button" @click="deleteComment(comment.id)">삭제</button>
        </p>
        <p>{{ comment.content }}</p>
        </div>

        <div class="replies" v-if="comment.replies.length">
          <div v-for="reply in comment.replies" :key="reply.id" class="reply">
            <p><strong>{{ reply.author }}</strong>
            <button v-if="sameCommentAuthor(reply.author)" class ="comment-edit-button" @click="editComment(comment.id)">수정</button>
            <button v-if="sameCommentAuthor(reply.author)" class ="comment-delete-button" @click="deleteComment(comment.id)">삭제</button>
            </p>
            <p>{{ reply.content }}</p>
          </div>
        </div>
      </div>
    </div>

    <div v-else>
      <p>No comments yet.</p>
    </div>

    <div class="comment-form">
      <form @submit.prevent="submitComment">
        <div class="form-group">
          <label for="content">댓글작성</label>
          <textarea class ="comment-textarea" v-model="content" id="content" required></textarea>
          <button class="commnet-submit-button" type="submit">Submit</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { mapState } from "vuex";
export default {
  name: 'CommentSection',
  data() {
    return {
      comments: [ //임시 데이터셋
        {
          id: 1,
          author: "김단국",
          content: "안녕하세요 댓글입니다",
          replies: [
            {
              id: 1,
              author: "김종빈",
              content: "대댓글 입니다. 반갑습니다."
            },
            {
              id: 2,
              author: "조성현",
              content: "대댓글 입니다. 반가워요."
            }
          ]
        },
        {
          id: 2,
          author: "홍길동",
          content: "두번째 댓글입니다. 반갑습니다. 댓글을 달아주세요.",
          replies: [
            {
              id: 3,
              author: "김한이",
              content: "알겠습니다."
            }
          ]
        },
      ],
      author: '',
      content: ''
    };
  },
  computed: {
    ...mapState(['userData'])
  },
  created() {
    this.fetchComments();
  },

  methods: {
    fetchComments() {
      // 댓글 목록을 서버에서 가져오는 로직
      const articleId = this.$route.params.id;
      axios.get(`${this.$serverUrl}/api/comments/${articleId}`).then((response) => {
        console.log(response);
        this.comments = response.data;
      }).catch((err) => {
        console.error('에러 발생:', err);
      });
    },

    submitComment() {
      const articleId = this.$route.params.id;
      const comment = {
        authorId: this.userData.userId, // 로그인한 사용자의 ID
        content: this.content
      };

      const accessToken = localStorage.getItem('accessToken'); // 로컬스토리지에서 토큰 가져오기

      // 서버에 댓글을 저장하는 로직
      axios.post(
          `${this.$serverUrl}/api/comment/${articleId}`,
          comment,
          {
            headers: {
              'Authorization': `Bearer ${accessToken}` // Authorization 헤더에 토큰 추가
            }
          }
      ).then((response) => {
        console.log(response);
        this.content = ''; // 댓글 작성란 초기화
        this.fetchComments(); // 댓글 목록 다시 불러오기
      }).catch((err) => {
        console.error('댓글 저장 에러 발생:', err);
      });
    },


    editComment(commentId) {
      const newContent = prompt("Enter new content:");
      if (newContent) {
        axios.patch(`${this.$serverUrl}/api/comments/${commentId}`, { content: newContent })
            .then(response => {
              this.fetchComments();
            })
            .catch(err => {
              console.error('댓글 수정 에러 발생:', err);
            });
      }
    },
    deleteComment(commentId) {
      if (confirm("정말 삭제하시겠습니까?")) {
        const accessToken = localStorage.getItem('accessToken');
        axios.delete(`${this.$serverUrl}/api/comments/${commentId}`,
            {
              headers: {
                'Authorization': `Bearer ${accessToken}` // Authorization 헤더에 토큰 추가
              }
            }
        ).then(response => {
              this.fetchComments();
            })
            .catch(err => {
              console.error('댓글 삭제 에러 발생:', err);
            });
      }
    },
    sameCommentAuthor(author) {
      return this.userData.username === author;
    },
  }
};
</script>

<style scoped>
.comment {
  padding: 10px;
  border-bottom: 1px solid #ddd;
  margin-bottom: 10px; /* 댓글 목록 사이에 공백 추가 */
}

.comment-warpper {
  background-color: rgba(128, 128, 128, 0.16);
  border-radius: 20px;
  padding: 10px;
}
.reply-to-comment-button{
  color: darksalmon;
  margin-left: 10px;
}
.comment-edit-button{
  color: #007bff;
  margin-left: 10px;
}
.comment-delete-button{
  color: #dc3545;
  margin-left: 10px;
}


.replies {
  background: rgba(128, 128, 128, 0.16);
  border-radius: 20px;
  margin-left: 20px;
  border-left: 2px solid #ddd;
  padding-left: 10px;
  margin-top: 10px; /* 답글 목록 사이에 공백 추가 */
}

.comment-form {
  margin-top: 20px;
}

.form-group {
  margin-bottom: 10px; /* 폼 요소 사이에 공백 추가 */
}

.commnet-submit-button{
  background-color: #007bff;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  margin-top: 10px;
  margin-left: 90%;
}

.comment-textarea{
  width: 100%;
  height: 50px;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ddd;
}
</style>
