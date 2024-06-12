<template>
  <div class="board-detail">
    <div class="common-buttons">
      <study-end-button v-if="sameAuthor && !recruitment"></study-end-button>
      <study-join-button v-if="!sameAuthor && recruitment"></study-join-button>
      <study-confirm-button class="common-button" v-if="sameAuthor && recruitment"></study-confirm-button>
      <button v-if="sameAuthor" type="button" class="common-button w3-blue-gray" @click="BoardUpdate">수정</button>
      <button v-if="sameAuthor" type="button" class="common-button w3-red" @click="BoardDelete">삭제</button>
      <button type="button" class="common-button w3-gray" @click="MoveBoardList">목록</button>
    </div>
    <div class="board-wrapper">
      <div class="board-contents">
        <h3 v-if="recruitment" class="status-open">[모집중]</h3>
        <h3 v-else class="status-closed">[모집완료]</h3>
        <h3>제목: {{ title }}</h3>
        <div>
          <strong class="author">작성자: {{ author }}</strong>
          <br>
          <span>작성일: {{ createdAt }}</span>
        </div>
      </div>
      <div class="board-contents">
        <p>{{ content }}</p>
      </div>
    </div>
    <br>
    <comment/>
  </div>
</template>

<script>
import axios from "axios";
import { mapState } from "vuex";
import comment from "@/components/Board/comment.vue";
import StudyJoinButton from "@/components/Board/studyJoinButton.vue";
import StudyConfirmButton from "@/components/Board/studyConfirmButton.vue";
import StudyEndButton from "@/components/Board/studyEndButton.vue";

export default {
  components: {
    StudyEndButton,
    StudyConfirmButton,
    StudyJoinButton,
    comment
  },
  data() {
    return {
      title: '',
      author: '',
      createdAt: '',
      content: '',
      recruitment: false,
    };
  },
  computed: {
    ...mapState(['userData']),
    sameAuthor() {
      return this.userData.username === this.author;
    }
  },

  created() {
    this.fetchArticle();
  },

  methods: {
    fetchArticle() {
      const articleId = this.$route.params.id; // 라우터에서 id 매개변수 추출
      axios.get(`${this.$serverUrl}/api/study-articles/${articleId}`).then((response) => {
        const { title, author, createdAt, content, recruitment } = response.data;
        this.title = title;
        this.author = author;
        this.createdAt = createdAt;
        this.content = content;
        this.recruitment = recruitment;
        console.log(response);
      }).catch((err) => {
        console.error('에러 발생:', err);
        if (err.message.indexOf('Network Error') > -1) {
          alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.');
        }
      });
    },

    MoveBoardList() {
      this.$router.push({
        name: 'BoardList'
      })
    },

    BoardUpdate() {
      this.$router.push({
        name: 'BoardEdit',
        params: { id: this.$route.params.id }
      })
    },

    BoardDelete() {
      if (confirm('삭제하시겠습니까?')) {
        axios.delete(`${this.$serverUrl}/api/study-articles/${this.$route.params.id}`)
            .then(() => {
              alert('삭제되었습니다.');
              this.$router.push({
                name: 'BoardList'
              })
            })
            .catch((err) => {
              alert('삭제에 실패했습니다.');
              console.error(err);
            });
      }
    },
    goToProfile(authorId) {
      this.$router.push({ name: 'MyPage', params: { authorId } });
    },
  }
}
</script>

<style scoped>
.board-wrapper {
  border: 1px solid #ddd;
  border-radius: 10px;
  padding: 20px;
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.board-contents h3 {
  margin-bottom: 10px;
}

.board-contents p {
  margin: 0;
  font-size: 1.1em;
  line-height: 1.6;
}

.status-open {
  color: green;
}

.status-closed {
  color: red;
}

.author {
  font-size: 1.2em;
  font-weight: bold;
}

.common-buttons {
  justify-content: flex-end;
}

.common-button {
  margin-right : 10px;
  padding: 8px 15px;
  border-radius: 5px;
  cursor: pointer;
  color: white;
  font-weight: bold;
}

.common-button:hover {
  opacity: 0.8;
}
</style>
