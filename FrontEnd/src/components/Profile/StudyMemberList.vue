<!-- StudyMemberList.vue -->
<template>
  <div class="study-member-list">
    <ul>
      <li v-for="member in members" :key="member.id" class="member-item">
        <div @click="selectMember(member)">
          <strong class="member-name" @click.stop="goToProfile(member.userId)">{{ member.username }}</strong>
        </div>
        <button v-if="showRateButton && (userData.userId === userInfo.userId) &&
        (member.userId !== userData.userId) && !isRated(member.userId, studyId)"
                @click="openRatingModal(member)" class="rate-button">평가하기</button>
        <button v-if="isRated(member.userId, studyId) && (member.userId !== userData.userId)" class="rate-done-button">평가완료</button>
      </li>
    </ul>

    <!-- 평가 모달 -->
    <div v-if="showRatingModal" class="modal">
      <div class="modal-content">
        <span class="close" @click="closeRatingModal">&times;</span>
        <h4>{{ ratingMember.username }}님 평가</h4>
        <input v-model.number="ratingScore" type="number" min="0" max="5" placeholder="점수 (0-5)" @input="validateScore" class="input-field"/>
        <textarea v-model="ratingContent" placeholder="평가 내용을 입력하세요" class="input-field"></textarea>
        <button @click="rateMemberAction" class="submit-button">제출</button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex';

export default {
  name: 'StudyMemberList',
  props: {
    members: {
      type: Array,
      required: true,
    },
    showRateButton: {
      type: Boolean,
      default: true,
    },
    studyId: {
      type: Number,
      required: true,
    },
  },
  data() {
    return {
      showRatingModal: false,
      ratingMember: null,
      ratingScore: '',
      ratingContent: '',
    };
  },
  computed: {
    ...mapState(['userData', 'ratedUsers','userInfo']),
  },
  methods: {
    ...mapActions(['rateMember']),
    selectMember(member) {
      this.$emit('select-member', member);
    },
    openRatingModal(member) {
      this.ratingMember = member;
      this.showRatingModal = true;
    },
    closeRatingModal() {
      this.showRatingModal = false;
      this.ratingScore = '';
      this.ratingContent = '';
    },
    validateScore() {
      if (this.ratingScore < 0) {
        this.ratingScore = 0;
      } else if (this.ratingScore > 5) {
        this.ratingScore = 5;
      }
    },
    rateMemberAction() {
      if (this.ratingScore < 0 || this.ratingScore > 5) {
        alert("점수는 0과 5 사이여야 합니다.");
        return;
      }
      const ratingData = {
        userId: this.ratingMember.userId,
        studyId: this.studyId,
        score: this.ratingScore,
        content: this.ratingContent,
      };
      console.log('서버에 보낼 데이터', ratingData);
      this.rateMember(ratingData)
          .then(() => {
            alert('평가가 완료되었습니다.');
            this.closeRatingModal();
          })
          .catch((err) => {
            alert('평가 저장 에러 발생. 다시 시도해주세요.');
          });
    },
    goToProfile(userId) {
      console.log(this.$route);
      this.$router.push({ name: 'MyPage', params: { userId } });
    },
    isRated(userId, studyId) {
      return this.ratedUsers.some(
          (rated) => rated.userId === userId && rated.studyId === studyId
      );
    },
  },
};
</script>

<style lang="scss">
.study-member-list {
  background: #f9f9f9;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  margin-top: 10px;
}

.study-member-list ul {
  list-style-type: none;
  padding: 0;
}

.study-member-list li {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  border-bottom: 1px solid #ddd;
  transition: background-color 0.3s;
}

.study-member-list li:hover {
  background-color: #e0e0e0;
}

.rate-button {
  background-color: #FF6347;
  color: white;
  border: none;
  border-radius: 4px;
  padding: 5px 10px;
  cursor: pointer;
}

.rate-done-button {
  background-color: gray;
  color: white;
  border: none;
  border-radius: 4px;
  padding: 5px 10px;
}

.rate-button:hover {
  background-color: #c74333;
}

.modal {
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgba(0,0,0,0.4);
}

.modal-content {
  background-color: #fff;
  padding: 40px;
  border-radius: 12px;
  width: 90%;
  max-width: 400px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  animation: slide-down 0.3s ease-out;
}

@keyframes slide-down {
  from {
    transform: translateY(-10%);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
}

.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
  cursor: pointer;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
}

.input-field {
  width: 100%;
  padding: 10px;
  margin: 10px 0;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.submit-button {
  width: 100%;
  padding: 10px;
  background-color: #28a745;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.submit-button:hover {
  background-color: #218838;
}

.member-name:hover{
  cursor: pointer;
}
</style>
