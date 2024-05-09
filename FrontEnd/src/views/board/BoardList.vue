<template>
  <div class="board-list">
    <div class="common-buttons">
      <button type="button" class="w3-button w3-round w3-blue-gray" @click="BoardWrite">등록</button>
    </div>
    <table class="w3-table-all">
      <thead>
      <tr>
        <th>No</th><th>제목</th><th>작성자</th><th>등록일시</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(row, idx) in list" :key="idx" @click="StudyDetailView(row.id)" class="table-row">
        <td>{{ row.id }}</td>
        <td>{{ row.title }}</td>
        <td>{{ row.author }}</td>
        <td>{{ row.createdAt }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  data() { //변수생성
    return {
      list: [

      ] //리스트 데이터
    }
  },
  created() {
    this.StudyBoardGet()
  },

  methods: {
    StudyBoardGet() {
      axios.get(this.$serverUrl + "/api/study-articles").then((response) => {
        this.list = response.data.sort((a, b) => b.id - a.id);
      }).catch((err) => {
        if (err.message.indexOf('Network Error') > -1) {
          alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
        }
      })
    },

    StudyDetailView(id) {
      this.$router.push({ name: 'BoardDetail', params: { id: id } })
    },

    BoardWrite() {
      this.$router.push({ name: 'BoardWrite' })
    }
  }
}
</script>

<style lang="scss" scoped>
.table-row:hover {
  background-color: #73d095; /* 마우스 오버 시 배경색 변경 */
  cursor: pointer; /* 마우스 오버 시 커서 모양 변경 */
}
</style>
