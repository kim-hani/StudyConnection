<template>
  <button type="button" class="w3-button w3-green" @click="fnEnd">스터디 종료</button>
</template>

<script>
export default {
  name: 'studyEndButton',
  methods: {
    fnEnd() {
      if (confirm('스터디를 종료하시겠습니까?')) {
        const id = this.$route.params.id;
        this.$axios.put(this.$serverUrl + `/api/study-end/${id}`)
            .then((res) => {
              alert('스터디가 종료되었습니다.')
            }).catch((err) => {
          console.log(err)
        })
      }
    }
  }
}
</script>

<style scoped>
  button {
    margin-right: 10px;
  }
</style>
