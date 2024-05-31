<template>
  <div class="study-member-list">
    <ul>
      <li v-for="member in members" :key="member.id" class="member-item">
        <div @click="selectMember(member)">
          {{ member.name }}
        </div>
        <button v-if="showRateButton" @click="rateMember(member.id)" class="rate-button">평가하기</button>
      </li>
    </ul>
  </div>
</template>

<script>
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
  },
  methods: {
    selectMember(member) {
      this.$emit('select-member', member);
    },
    rateMember(memberId) {
      alert(`평가하기: ${memberId}`);
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
</style>
