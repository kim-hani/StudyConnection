<template>
  <div class="user-rating-list">
    <h3>{{ userInfo.username }}님이 받은 평가</h3>
    <div v-if="userRatings.length" class="ratings-grid">
      <div v-for="rating in userRatings" :key="rating.studyId" class="rating-item">
        <p><strong></strong> {{ rating.studyName }}</p>
        <p><strong>점수:</strong>
          <span v-for="n in rating.score" :key="n">⭐</span>
        </p>
        <p><strong>내용:</strong> {{ rating.content }}</p>
      </div>
    </div>
    <div v-else>
      <p>아직 평가가 없습니다.</p>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters, mapState } from 'vuex';

export default {
  name: 'UserRatingList',

  created() {
    this.fetchUserRatings(this.$route.params.userId);
  },
  computed: {
    ...mapState(['userRatings', 'userInfo']),
    ...mapGetters(['getUserId']),
  },
  methods: {
    ...mapActions(['fetchUserRatings']),
  },
};
</script>

<style scoped>
.user-rating-list {
  padding: 20px;
  background-color: #ffffff;
  border-radius: 15px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  margin-top: 20px;
}

.user-rating-list h3 {
  margin-bottom: 20px;
  font-family: 'Roboto', sans-serif;
  font-size: 1.5em;
}

.ratings-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
}

.rating-item {
  background: #f9f9f9;
  padding: 15px;
  border: 1px solid #ddd;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s;
}

.rating-item:hover {
  transform: translateY(-2px);
}

.rating-item p {
  margin: 5px 0;
}

.rating-item span {
  color: #FFD700;
}
</style>



