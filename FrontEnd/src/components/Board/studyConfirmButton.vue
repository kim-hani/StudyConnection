<template>
  <button id="button" type="button" class="w3-button w3-round w3-green" @click="showModal">신청자 조회</button>
  <div v-if="isModalVisible" class="modal-overlay">
    <div class="modal-content">
      <div class="modal-header">
        <h3>신청자 목록</h3>
        <button @click="hideModal" class="close-button">&times;</button>
      </div>
      <div class="modal-body">
        <ul>
          <li v-for="applicant in applicants" :key="applicant.userId" class="applicant-item">
            <label>
              <input type="checkbox" v-model="members" :value="applicant.userId" />
              <span>{{ applicant.username }} {{ applicant.birth }}</span>
            </label>
            <button @click="goToProfile(applicant.userId)" class="profile-button">프로필로 이동</button>
          </li>
        </ul>
        <div class="input-group">
          <label for="studyTitle">스터디 이름:</label>
          <input type="text" v-model="studyTitle" id="studyTitle" placeholder="ex) Java 프로그래밍 스터디">
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="w3-button w3-round w3-blue" :disabled="!studyTitle || members.length === 0" @click="confirmApplicants">확정</button>
        <button type="button" class="w3-button w3-round w3-red" @click="hideModal">닫기</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'StudyApplyButton',
  data() {
    return {
      applicants: [],
      members: [],
      studyTitle: '',
      isModalVisible: false,
    };
  },
  methods: {
    showModal() {
      const articleId = this.$route.params.id;
      this.$axios.get(this.$serverUrl + `/api/study-articles/${articleId}/apply`)
          .then((res) => {
            this.applicants = res.data.applicants;
            console.log(res);
            this.isModalVisible = true;
          })
          .catch((err) => {
            if (err.response && err.response.status === 403) {
              alert("이미 확정된 스터디 입니다. error 403");
            } else {
              console.log(err);
            }
          });
    },

    hideModal() {
      this.isModalVisible = false;
    },
    confirmApplicants() {
      const articleId = this.$route.params.id;
      const userId = localStorage.getItem('userId');
      const selectedMembers = [...this.members, userId];
      console.log(selectedMembers);
      this.$axios.post(this.$serverUrl + `/api/study-articles/${articleId}/study-confirm`, {
        members: selectedMembers,
        studyTitle: this.studyTitle
      }).then((res) => {
        console.log(res);
        alert('신청자를 확정했습니다.');
      }).catch((err) => {
        console.log(err);
      });
      console.log('Selected applicants:', selectedMembers);
      this.hideModal();
    },
    goToProfile(userId) {
      this.$router.push({ name: 'MyPage', params: { userId } });
    },
  },
};
</script>

<style scoped>
#button {
  margin-right: 10px;
  margin-bottom: 15px;
}
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.7);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 9999;
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 8px;
  width: 500px;
  max-width: 90%;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
  z-index: 10000;
}

.modal-header, .modal-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.modal-header {
  border-bottom: 1px solid #ddd;
  padding-bottom: 10px;
}

.modal-footer {
  border-top: 1px solid #ddd;
  padding-top: 10px;
  margin-top: 20px;
}

.close-button {
  background: none;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  margin: 10px 0;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

button {
  margin-top: 10px;
}

.applicant-item label {
  display: flex;
  align-items: center;
}

.applicant-item input {
  margin-right: 10px;
}

.profile-button {
  background-color: #008CBA;
  color: white;
  border: none;
  border-radius: 4px;
  padding: 5px 10px;
  cursor: pointer;
  font-size: 0.9rem;
}

.profile-button:hover {
  background-color: #005f6b;
}

.input-group {
  margin-top: 20px;
}

.input-group label {
  display: block;
  margin-bottom: 5px;
}

.input-group input {
  width: 100%;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
}
</style>
