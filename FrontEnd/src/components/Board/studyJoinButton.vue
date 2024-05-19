<template>
  <button type="button" class="w3-button w3-round w3-green" @click="fnJoin">참여하기</button>
</template>

<script>
import {mapState} from "vuex";
export default {
  data() {
    return {}
  },

  computed: {
    ...mapState(['userData'])
  },

  methods: {
    fnJoin() {
      if (confirm('참가신청 하시겠습니까?')) {
        const id = this.$route.params.id;
        this.$axios.post(this.$serverUrl + `/api/study-articles/${id}/apply`,
            this.userData.userId).then((res) => {
          alert('참가신청 되었습니다.')
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
