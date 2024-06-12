<!-- MemberDetail.vue -->
<template>
  <div class="member-detail section">
    <h2>{{ member.username }}의 상세 정보</h2>
  </div>
</template>

<script>
export default {
  name: 'MemberDetail',
  props: {
    member: {
      type: Object,
      required: true,
    },
  },
};
</script>

<style scoped>
.member-detail {
  text-align: center;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.member-detail h2 {
  margin-bottom: 10px;
}

.member-detail p {
  margin: 0;
  font-size: 1.2em;
}
</style>
