<template>
  <div class="my-page">
    <UserProfile/>
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

    <UserRatingList/>
  </div>
</template>

<script>
import UserProfile from '@/components/Profile/UserProfile.vue';
import StudyList from '@/components/Profile/StudyList.vue';
import StudyMemberList from '@/components/Profile/StudyMemberList.vue';
import UserRatingList from '@/components/Profile/UserRatingList.vue';
import { mapState, mapActions } from 'vuex';
import {SET_USER_INFO} from "@/Vuex/mutation_types";

export default {
  name: 'MyPage',
  components: {
    UserProfile,
    StudyList,
    StudyMemberList,
    UserRatingList,
  },
  computed: {
    ...mapState(['userInfo']),
  },
  data() {
    return {
      activeStudies: this.$store.state.userInfo.availableStudyList,
      completedStudies: this.$store.state.userInfo.unavailableStudyList,
      selectedStudy: null,
      selectedMember: null,
    };
  },
  created(){
    this.getUserInfo(this.$route.params.userId);
  },

  methods: {
    ...mapActions(['getUserInfo']),
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
