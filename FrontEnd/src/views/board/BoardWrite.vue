<template>
  <div class="board-detail">
    <div class="board-contents">
      <input type="text" v-model="RequestBody.title" class="w3-input w3-border" placeholder="제목을 입력해주세요.">
    </div>
    <div class="board-contents">
      <textarea id="" cols="30" rows="10" v-model="RequestBody.content" class="w3-input w3-border" style="resize: none;">
      </textarea>
    </div>
    <div class="common-buttons">
      <button type="button" class="w3-button w3-round w3-blue-gray" v-on:click="BoardSave">저장</button>&nbsp;
      <button type="button" class="w3-button w3-round w3-gray" @click="MoveBoardList">목록</button>
    </div>
  </div>
</template>

<script>
export default {
  data() { //변수생성
    return {
      RequestBody: {
        title: '',
        content: '',
        authorId: localStorage.getItem('userId'),
        authorName: localStorage.getItem('username'),
        limitOfParticipants: 5
      }
    }
  },
  beforeRouteLeave (to, from, next) {
    // 사용자에게 확인 메시지를 표시하여 페이지를 벗어나기 전에 확인을 요청
    const answer = window.confirm('이 페이지를 떠나시겠습니까? 변경 사항이 저장되지 않을 수 있습니다.')
    if (answer) {
      next() // 사용자가 확인했으면 라우트 이동을 허용
    } else {
      next(false) // 사용자가 취소했으면 라우트 이동을 막음.
    }
  },
  methods: {
    BoardSave() {
      if (confirm('저장하시겠습니까?')) {
        this.$axios.post(this.$serverUrl + '/api/study-articles', this.RequestBody)
            .then(() => {
              alert('저장되었습니다.');
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
