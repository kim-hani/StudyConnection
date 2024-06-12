<template>
  <div class="board-list">
    <div class="common-buttons">
      <button type="button" class="w3-button w3-round w3-blue-gray" @click="BoardWrite">등록</button>
    </div>
    <table class="w3-table-all">
      <thead>
      <tr>
        <th>No</th><th>제목</th><th>작성자</th><th>등록일시</th><th>스터디 모집</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(row, idx) in list" :key="idx" @click="StudyDetailView(row.id)" class="table-row">
        <td>{{ row.id }}</td>
        <td>{{ row.title }}</td>
        <td>{{ row.authorName }}</td>
        <td>{{ row.createdAt }}</td>
        <td>
          <span :class="{'recruitment-true': row.recruitment === true || row.recruitment === 'true', 'recruitment-false': row.recruitment === false || row.recruitment === 'false'}">
            {{ row.recruitment ? '참여 가능' : '참여 불가' }}
          </span>
        </td>
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
        this.list = response.data
        console.log(response.data);
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

.recruitment-true {
  background-color: green;
  color: white; /* 텍스트 색상 변경 (선택 사항) */
  border-radius: 12px; /* 둥근 모서리 */
  padding: 5px 10px; /* 여백 */
  text-align: center; /* 텍스트 가운데 정렬 */
  display: inline-block; /* 인라인 블록 요소 */
}

.recruitment-false {
  background-color: grey;
  color: white; /* 텍스트 색상 변경 (선택 사항) */
  border-radius: 12px; /* 둥근 모서리 */
  padding: 5px 10px; /* 여백 */
  text-align: center; /* 텍스트 가운데 정렬 */
  display: inline-block; /* 인라인 블록 요소 */
}
</style>




