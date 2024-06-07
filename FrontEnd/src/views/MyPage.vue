<template>
  <div class="my-page">
    <UserProfile :user="user" />
    <StudyList
        :studies="activeStudies"
        title="참여중인 스터디 목록"
        @select-study="selectStudy"
        @go-to-study="goToStudy"
        :selected-study="selectedStudy"
        :showRateButton="false"
    />

    <StudyList
        :studies="completedStudies"
        title="종료된 스터디 목록"
        @select-study="selectStudy"
        @go-to-study="goToStudy"
        :selected-study="selectedStudy"
        :showRateButton="true"
    />
  </div>
</template>

<script>
import UserProfile from '@/components/Profile/UserProfile.vue';
import StudyList from '@/components/Profile/StudyList.vue';
import StudyMemberList from '@/components/Profile/StudyMemberList.vue';

export default {
  name: 'MyPage',
  components: {
    UserProfile,
    StudyList,
    StudyMemberList,
  },
  data() {
    return {
      user: {
        id: this.$route.params.userId,
        name: this.$store.state.userData.username,
        rating: 4.5,
      },
      activeStudies: [
        {
          id: 3,
          name: '프로그래밍 언어 스터디',
          members: [
            { id: 1, name: '조성현'},
            { id: 2, name: '김한이'},
          ],
        },
        {
          id: 2,
          name: '스터디 B',
          members: [
            { id: 3, name: '박지성'},
            { id: 4, name: '손흥민'},
          ],
        },
      ],
      completedStudies: [
        {
          id: 4,
          name: '스터디 C',
          members: [
            { id: 5, name: '김영수'},
            { id: 6, name: '이정희'},
          ],
        },
      ],
      selectedStudy: null,
      selectedMember: null,
    };
  },
  mounted(){
    this.getUserStudyInfo();
  },

  methods: {
    selectStudy(study) {
      this.selectedStudy = study;
      this.selectedMember = null;
    },
    selectMember(member) {
      this.selectedMember = member;
    },
    goToStudy(studyId) {
      this.$router.push({ path: `/board/${studyId}` });
    },

    getUserStudyInfo(){ // 유저가 참여하고, 참여했던 스터디 정보 가져오기.
      const userId = this.$route.params.userId;
      this.$axios.get(this.$serverUrl + `/api/userinfo/${userId}`)
          .then((res) => {
            console.log(res);
            this.activeStudies = res.data.availableStudyList;
            this.email = res.data.email;
          }).catch((err) => {
        console.log(err)
      },)
    }

  },
};
</script>

<style scoped>
.my-page {
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
}

.my-page h2 {
  margin-bottom: 10px;
}

.my-page .section {
  margin-bottom: 20px;
}
</style>
