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
        :currentUserId="getUserId"
    />

    <StudyList
        :studies="completedStudies"
        title="종료된 스터디 목록"
        @select-study="selectStudy"
        @go-to-study="goToStudy"
        :selected-study="selectedStudy"
        :showRateButton="true"
        :currentUserId="getUserId"
    />

    <UserRatingList :user="user" />
  </div>
</template>

<script>
import UserProfile from '@/components/Profile/UserProfile.vue';
import StudyList from '@/components/Profile/StudyList.vue';
import StudyMemberList from '@/components/Profile/StudyMemberList.vue';
import UserRatingList from '@/components/Profile/UserRatingList.vue';
import { mapGetters } from 'vuex';

export default {
  name: 'MyPage',
  components: {
    UserProfile,
    StudyList,
    StudyMemberList,
    UserRatingList,
  },

  data() {
    return {
      user: {
        id: this.$route.params.userId,
        name: '',
        rating: 4.5,
      },
      activeStudies: [],
      completedStudies: [],
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
      console.log('userId',userId);
      this.$axios.get(this.$serverUrl + `/api/userinfo/${userId}`)
          .then((res) => {
            console.log('getUserStudyInfo',res);
            const { username, availableStudyList, unavailableStudyList } = res.data;  // 수정된 부분
            this.user.name = username;
            this.activeStudies = availableStudyList;
            this.completedStudies = unavailableStudyList;
          }).catch((err) => {
        console.log(err);
      });
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
