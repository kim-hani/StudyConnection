<template>
  <div class="comment-section">
    <h4>Comments</h4>

    <div v-if="comments.length" class="comments-box">
      <div v-for="comment in comments" :key="comment.id" class="comment">
        <div class="comment-wrapper">
          <p v-if="comment.author">
            <strong>{{ comment.author }}</strong>
            <button class="reply-to-comment-button" @click="replyComment(comment.id)">대댓글</button>
            <button v-if="sameCommentAuthor(comment.author)" class="comment-edit-button" @click="editComment(comment.id)">수정</button>
            <button v-if="sameCommentAuthor(comment.author)" class="comment-delete-button" @click="deleteComment(comment.id)">삭제</button>
          </p>
          <p v-else class="deleted-comment">삭제된 댓글입니다.</p>
          <p v-if="comment.author">{{ comment.content }}</p>
        </div>

        <div class="replies" v-if="comment.replies.length">
          <div v-for="reply in comment.replies" :key="reply.id" class="reply">
            <p v-if="reply.author">
              <strong>{{ reply.author }}</strong>
              <button v-if="sameCommentAuthor(reply.author)" class="comment-edit-button" @click="editComment(reply.id)">수정</button>
              <button v-if="sameCommentAuthor(reply.author)" class="comment-delete-button" @click="deleteComment(reply.id)">삭제</button>
            </p>
            <p v-else class="deleted-comment">삭제된 댓글입니다.</p>
            <p v-if="reply.author">{{ reply.content }}</p>
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
          <label for="content">{{ isReplying ? '대댓글작성' : '댓글작성' }}</label>
          <textarea class="comment-textarea" v-model="content" id="content" required></textarea>
          <button class="comment-submit-button" type="submit">Submit</button>
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
      comments: [],
      author: '',
      content: '',
      isReplying: false,
      replyToCommentId: null
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
        authorId: this.userData.userId,
        content: this.content
      };
      const accessToken = localStorage.getItem('accessToken');
      let url = `${this.$serverUrl}/api/comment/${articleId}`;
      if (this.isReplying) {
        url = `${this.$serverUrl}/api/reply/${this.replyToCommentId}`;
      }
      axios.post(
          url, { content: this.content },
          {
            headers: {
              'Authorization': `Bearer ${accessToken}`
            }
          }
      ).then((response) => {
        console.log(response);
        this.content = '';
        this.isReplying = false;
        this.replyToCommentId = null;
        this.fetchComments();
      }).catch((err) => {
        console.error('댓글 저장 에러 발생:', err);
      });
    },
    editComment(commentId) {
      const newContent = prompt("Enter new content:");
      if (newContent) {
        const accessToken = localStorage.getItem('accessToken');
        axios.patch(`${this.$serverUrl}/api/comment/${commentId}`, { content: newContent },
            {
              headers: {
                'Authorization': `Bearer ${accessToken}`,
              }
            }
        ).then(response => {
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
        axios.delete(`${this.$serverUrl}/api/comment/${commentId}`,
            {
              headers: {
                'Authorization': `Bearer ${accessToken}`
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
    replyComment(commentId) {
      this.isReplying = true;
      this.replyToCommentId = commentId;
      this.$nextTick(() => {
        document.getElementById('content').focus();
      });
    }
  }
};
</script>

<style scoped>
.comment-section {
  margin-top: 20px;
  border: 1px solid #ddd;
  border-radius: 10px;
  padding: 20px;
  background-color: #f9f9f9;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.comments-box {
  margin-bottom: 20px;
}

.comment {
  padding: 10px;
  border-bottom: 1px solid #ddd;
}

.comment-wrapper {
  background-color: rgba(128, 128, 128, 0.16);
  border-radius: 10px;
  padding: 10px;
}

.reply-to-comment-button {
  color: darksalmon;
  margin-left: 10px;
}

.comment-edit-button {
  color: #007bff;
  margin-left: 10px;
}

.comment-delete-button {
  color: #dc3545;
  margin-left: 10px;
}

.replies {
  background: rgba(128, 128, 128, 0.16);
  border-radius: 10px;
  margin-left: 20px;
  border-left: 2px solid #ddd;
  padding-left: 10px;
  margin-top: 10px;
}

.comment-form {
  margin-top: 20px;
}

.form-group {
  margin-bottom: 40px;
}

.comment-submit-button {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  margin-top: 10px;
  float: right;
}

.comment-textarea {
  width: 100%;
  height: 50px;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ddd;
}

.deleted-comment {
  color: gray;
  font-style: italic;
}
</style>
