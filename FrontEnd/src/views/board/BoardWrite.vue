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
        authorId: localStorage.getItem('username'),
        limitOfParticipants: 5
      }
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
    MoveBoardList() {
      if (confirm('목록으로 이동하시겠습니까? 작성중인 내용은 저장되지 않습니다.')) {
        this.$router.push({
          name: 'BoardList'
        })
      }
    }
  }
}
</script>
<style scoped>

</style>
