<!--StudyList.vue-->
<template>
  <div class="study-list section">
    <h4>{{ title }}</h4>
    <ul>
      <li v-for="study in studies" :key="study.studyId" class="study-item" :class="{ selected: study.studyId === selectedStudy?.studyId }">
        <div @click="selectStudy(study)">
          {{ study.studyName }}
        </div>
        <button @click="goToStudy(study.studyId)" class="study-button">게시글로 이동</button>
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

export default {
  name: 'StudyList',
  components: {
    StudyMemberList,
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
  background: #f9f9f9;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.study-list ul {
  list-style-type: none;
  padding: 0;
}

.study-list li {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  padding: 10px;
  border-bottom: 1px solid #ddd;
  transition: background-color 0.3s;
}

.study-list li:hover {
  background-color: #e0e0e0;
}

.study-list li.selected {
  background-color: #d0e4fe;
}

.study-button {
  background-color: #008CBA;
  color: white;
  border: none;
  border-radius: 4px;
  padding: 5px 10px;
  cursor: pointer;
  align-self: flex-end;
}

.study-button:hover {
  background-color: #005f6b;
}

.member-list-box {
  width: 100%;
  margin-top: 10px;
}
</style>
