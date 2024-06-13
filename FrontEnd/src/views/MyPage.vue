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
            { id: 1, name: '조성현', rating: 4.2 },
            { id: 2, name: '김한이', rating: 3.8 },
          ],
        },
        {
          id: 2,
          name: '스터디 B',
          members: [
            { id: 3, name: '박지성', rating: 4.9 },
            { id: 4, name: '손흥민', rating: 4.7 },
          ],
        },
      ],
      completedStudies: [
        {
          id: 4,
          name: '스터디 C',
          members: [
            { id: 5, name: '김영수', rating: 3.2 },
            { id: 6, name: '이정희', rating: 4.1 },
          ],
        },
      ],
      selectedStudy: null,
      selectedMember: null,
    };
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
