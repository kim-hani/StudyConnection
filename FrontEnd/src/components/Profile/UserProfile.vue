<!-- UserProfile.vue -->
<template>
  <div class="user-profile section">
    <h2>{{ userInfo.username }}의 정보</h2>
    <p>나이: <strong>{{ userInfo.age }}</strong></p>
    <p>email: <strong>{{ userInfo.email }}</strong></p>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex';
export default {
  name: 'UserProfile',
  computed: {
    ...mapState(['userInfo']),
  },
};
</script>

<style scoped>
.user-profile {
  text-align: center;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.user-profile h2 {
  margin-bottom: 10px;
}

.user-profile p {
  margin: 0;
  font-size: 1.2em;
}
</style>
