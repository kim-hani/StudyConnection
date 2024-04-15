<!-- PageHeader.vue -->
<template>
  <header class ="Side-bar">
    <div class="nav">
      <ul class="ui-menu-item-wrapper">
        <li><router-link to="/">Home</router-link></li>
        <li><router-link to="/about">About</router-link></li>
        <li><router-link to="/board/list">게시판</router-link></li>
      </ul>
    </div>
  </header>
  <hr/>
</template>
<script>
export default {

}
</script>

<style scoped>
  .ui-menu-item-wrapper{
    list-style-type: none;
  }
  .Side-bar{
    margin : 0;
    padding: 0;
    display: flex;
  }

  .nav{

  }
</style>
