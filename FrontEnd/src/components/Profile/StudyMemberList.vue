<!-- StudyMemberList.vue -->
<template>
  <div class="study-member-list">
    <ul>
      <li v-for="member in members" :key="member.id" class="member-item">
        <div @click="selectMember(member)">
          {{ member.username }}
        </div>
        <button v-if="showRateButton && (member.userId !== userData.userId)"
                @click="openRatingModal(member)" class="rate-button">평가하기</button>
      </li>
    </ul>

    <!-- 평가 모달 -->
    <div v-if="showRatingModal" class="modal">
      <div class="modal-content">
        <span class="close" @click="closeRatingModal">&times;</span>
        <h4>{{ ratingMember.username }}님 평가</h4>
        <input v-model.number="ratingScore" type="number" min="0" max="5" placeholder="점수 (0-5)" @input="validateScore" class="input-field"/>
        <textarea v-model="ratingContent" placeholder="평가 내용을 입력하세요" class="input-field"></textarea>
        <button @click="rateMember" class="submit-button">제출</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { mapState } from 'vuex';

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
      ratingMember: null, // 평가할 멤버 정보
      ratingScore: '',
      ratingContent: '',
    };
  },
  computed: {
    ...mapState(['userData']), // map `this.getUserId` to `this.$store.getters.getUserId`
  },
  methods: {
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
    rateMember() {
      if (this.ratingScore < 0 || this.ratingScore > 5) {
        alert("점수는 0과 5 사이여야 합니다.");
        return;
      }
      const accessToken = localStorage.getItem('accessToken'); // 로컬스토리지에서 토큰 가져오기
      const ratingData = {
        userId: this.ratingMember.userId, // 평가할 대상의 ID
        studyId: this.studyId,
        score: this.ratingScore,
        content: this.ratingContent,
      };
      console.log('서버에 보낼 데이터', ratingData);
      axios.post(
          `${this.$serverUrl}/api/user-ratings`, // API 엔드포인트 URL
          ratingData,
          {
            headers: {
              'Authorization': `Bearer ${accessToken}`, // Authorization 헤더에 토큰 추가
            },
          }
      ).then((response) => {
        console.log(response);
        this.closeRatingModal(); // 모달 창 닫기 및 입력란 초기화
      }).catch((err) => {
        console.error('평가 저장 에러 발생:', err);
      });
    },
  },
};
</script>

<style scoped>
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
</style>
