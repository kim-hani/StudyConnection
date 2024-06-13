<template>
  <div class="board-detail">
    <div class="board-contents">
      <input type="text" v-model="Databody.title" class="w3-input w3-border" placeholder="제목을 입력해주세요.">
    </div>
    <div class="board-contents">
      <textarea id="" cols="30" rows="10" v-model="Databody.content" class="w3-input w3-border" style="resize: none;">
      </textarea>
    </div>
    <div class="common-buttons">
      <button type="button" class="w3-button w3-round w3-blue-gray" @click="BoardEdit">저장</button>&nbsp;
      <button type="button" class="w3-button w3-round w3-gray" @click="MoveBoardList">목록</button>
    </div>
  </div>
</template>

<script>
export default {
  data() { //변수생성
    return {
      Databody: {
        title: '',
        content: '',
      }
    }
  },
  beforeRouteLeave (to, from, next) {
    // 사용자에게 확인 메시지를 표시하여 페이지를 벗어나기 전에 확인을 요청합니다.
    const answer = window.confirm('이 페이지를 떠나시겠습니까? 변경 사항이 저장되지 않을 수 있습니다.')
    if (answer) {
      next() // 사용자가 확인했으면 라우트 이동을 허용합니다.
    } else {
      next(false) // 사용자가 취소했으면 라우트 이동을 막습니다.
    }
  },
  mounted() {
    this.fetchBoardData();
  },

  methods: {
    fetchBoardData(){
      this.$axios.get(`${this.$serverUrl}/api/study-articles/${this.$route.params.id}`)
          .then((res) => {
            this.Databody.title = res.data.title;
            this.Databody.content = res.data.content;
            console.log(res);
          })
          .catch((err) => {
            alert('에러 발생');
            if (err.message.indexOf('Network Error') > -1) {
              alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.');
            }
          })
    },

    BoardEdit() {
      if (confirm('수정하시겠습니까?')) {
        this.$axios.put(`${this.$serverUrl}/api/study-articles/${this.$route.params.id}`, this.Databody)
            .then(() => {
              alert('수정되었습니다.');
              this.$router.push({
                name: 'BoardList'
              })
            })
            .catch((err) => {
              alert('저장에 실패했습니다.')
              console.log(err)
            })
      }
    },
    MoveBoardList() {this.$router.push({name: 'BoardList'})}
    }
}
</script>
<style scoped>

</style>
