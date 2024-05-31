<template>
  <div class="board-detail">
    <div class="common-buttons">
      <study-join-button v-if = !sameAuthor></study-join-button>
      <study-apply-button v-if= sameAuthor></study-apply-button>
      <button v-if = sameAuthor type="button" class="w3-button w3-round w3-blue-gray" @click="BoardUpdate">수정</button>&nbsp;
      <button v-if = sameAuthor type="button" class="w3-button w3-round w3-red" @click="BoardDelete">삭제</button>&nbsp;
      <button type="button" class="w3-button w3-round w3-gray" @click="MoveBoardList">목록</button>
    </div>
    <div class="board-wrapper">
      <div class="board-contents">
        <h3>제목: {{ title }}</h3>
        <div>
          <strong class="w3-large">작성자: {{ author }}</strong>
          <br>
          <span>작성일: {{ createdAt }}</span>
        </div>
      </div>
      <div class="board-contents">
        <span>{{ content }}</span>
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
import StudyApplyButton from "@/components/Board/studyApplyButton.vue";

export default {
  components: {
    StudyApplyButton,
    StudyJoinButton,
    comment
  },
  data() {
    return {
      title: '',
      author: '',
      createdAt: '',
      content: '',
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
        const { title, author, createdAt, content } = response.data;
        this.title = title;
        this.author = author;
        this.createdAt = createdAt;
        this.content = content;
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
        name : 'BoardList'
      })
    },

    BoardUpdate() {
      this.$router.push({
        name : 'BoardEdit',
        params : { id : this.$route.params.id }
      })
    },

    BoardDelete() {
      if (confirm('삭제하시겠습니까?')) {
        axios.delete(`${this.$serverUrl}/api/study-articles/${this.$route.params.id}`)
            .then(() => {
              alert('삭제되었습니다.');
              this.$router.push({
                name : 'BoardList'
              })
            })
            .catch((err) => {
              alert('삭제에 실패했습니다.');
              console.error(err);
            });
      }
    }
  }
}
</script>
<style scoped>
.board-wrapper {

  border: 1px solid #ddd;
  border-radius: 10px;
}

</style>
