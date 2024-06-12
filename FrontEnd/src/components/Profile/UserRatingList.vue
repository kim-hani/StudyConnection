<template>
  <div class="user-rating-list">
    <h3>{{ user.name }}님이 받은 평가</h3>
    <div v-if="ratings.length">
      <div v-for="rating in ratings" :key="rating.studyId" class="rating-item">
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
import axios from 'axios';
import { mapGetters } from 'vuex';

export default {
  name: 'UserRatingList',
  props: {
    user: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      ratings: [],
    };
  },
  created() {
    this.fetchUserRatings();
  },
  computed: {
    ...mapGetters(['getUserId']),
  },
  methods: {
    fetchUserRatings() {
      const userId = this.$route.params.userId;
      axios.get(this.$serverUrl + `/api/user-ratings/${userId}`)
          .then(response => {
            this.ratings = response.data;
            console.log('평가 데이터 불러오기 성공', response.data);
          })
          .catch(error => {
            console.error('평가 데이터를 불러오는 중 오류 발생:', error);
          });
    },
  },
};
</script>

<style scoped>
.user-rating-list {
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  margin-top: 20px;
}

.user-rating-list h3 {
  margin-bottom: 20px;
}

.rating-item {
  background: #fff;
  padding: 15px;
  border: 1px solid #ddd;
  border-radius: 5px;
  margin-bottom: 10px;
}

.rating-item p {
  margin: 5px 0;
}

.rating-item span {
  color: gold;
}
</style>

