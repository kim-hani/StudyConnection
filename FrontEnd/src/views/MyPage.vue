<!-- MyPage.vue -->
<template>
  <div>
    <div v-if="isAuth">
      <h2>안녕하세요, {{ userData.username || userData.userName }}님</h2> <!-- userData.username이 없을 경우 userData.userName을 사용 백엔드 수정 예정 -->
    </div>
    <div v-else>
      <div>로그인이 필요합니다.</div>
        <div> {{ userData.userName }}</div>
        <div> {{ userData.userId }}</div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex';

export default {
  computed: {
    ...mapState(['userData', 'isAuth'])
  },

  data() {
    return {
    };
  }
}

</script>

<style scoped>
.about{
  font-size: 1rem;
  color: darkgray;
}
</style>

