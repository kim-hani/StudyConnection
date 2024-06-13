<template>
  <div class="user-profile section">
    <h2>{{ username }}의 정보</h2>
    <p>나이: <strong>{{ age }}</strong></p>
    <p>email: <strong>{{ email }}</strong></p>
    <p>Rating: <strong>{{ rating }}</strong></p>
  </div>
</template>

<script>
export default {
  name: 'UserProfile',
  data() {
    return {
     username : '',
      rating : 3.3,
      age : 10,
      email: '',
    }
  },
  created() {
    this.getUserInfo();
  },

  methods: {
    getUserInfo(){
      const userId = this.$route.params.userId;
      this.$axios.get(this.$serverUrl + `/api/userinfo/${userId}`)
        .then((res) => {
          this.username = res.data.username;
          this.rating = res.data.rating;
          this.age = res.data.age;
          this.email = res.data.email;
        }).catch((err) => {
          console.log(err)
        },)
    }
  }
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
