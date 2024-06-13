<template>
  <div class="study-list section">
    <h4>{{ title }}</h4>
    <ul>
      <li v-for="study in studies" :key="study.studyId" class="study-item" :class="{ selected: study.studyId === selectedStudy?.studyId }">
        <div class="study-header">
          <div class="study-title" @click="selectStudy(study)">
            {{ study.studyName }}
          </div>
          <button @click="goToStudy(study.studyId)" class="study-button">게시글로 이동</button>
        </div>
        <div v-if="study.studyId === selectedStudy?.studyId" class="member-list-box">
          <StudyMemberList
              :members="study.participants"
              :showRateButton="showRateButton"
              :studyId="study.studyId"
              @select-member="selectMember" />
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
import StudyMemberList from '@/components/Profile/StudyMemberList.vue';
import { mapState } from "vuex";

export default {
  name: 'StudyList',
  components: {
    StudyMemberList,
  },
  computed: {
    ...mapState(['userInfo']),
  },
  props: {
    studies: {
      type: Array,
      required: true,
    },
    title: {
      type: String,
      required: true,
    },
    selectedStudy: {
      type: Object,
      default: null,
    },
    showRateButton: {
      type: Boolean,
      default: true,
    },
  },
  methods: {
    selectStudy(study) {
      this.$emit('select-study', study);
    },
    goToStudy(studyId) {
      this.$emit('go-to-study', studyId);
    },
    selectMember(member) {
      this.$emit('select-member', member);
    },
  },
};
</script>

<style scoped>
.study-list {
  background: linear-gradient(135deg, #ffffff, #e0e0e0);
  border-radius: 15px;
  padding: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.study-list h4 {
  font-family: 'Roboto', sans-serif;
  font-size: 1.5em;
  margin-bottom: 20px;
}

.study-list ul {
  list-style-type: none;
  padding: 0;
}

.study-list li {
  padding: 10px;
  border-bottom: 1px solid #ddd;
  transition: background-color 0.3s, transform 0.3s;
  border-radius: 10px;
  margin-bottom: 10px;
}

.study-list li:hover {
  background-color: #f0f8ff;
  transform: translateY(-2px);
}

.study-list li.selected {
  background-color: #f0f8ff;
}

.study-header {
  display: flex;
  justify-content: space-between;
  align-items: center; /* 세로 정렬 */
}

.study-title {
  flex: 1;
  cursor: pointer;
  font-size: 1.2em; /* 글씨 크기 */
  font-weight: bold; /* 글씨 두께 */
  color: #333; /* 글씨 색상 */
  font-family: 'Arial', sans-serif; /* 원하는 폰트 설정 */
}

.study-button {
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 20px;
  padding: 10px 20px;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.3s;
}

.study-button:hover {
  background-color: #45a049;
  transform: translateY(-2px);
}

.member-list-box {
  width: 100%;
  margin-top: 10px;
}
</style>
