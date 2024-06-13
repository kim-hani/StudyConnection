<!-- PageHome.vue -->
<template>
  <main>
 <div class="home">
   <div>
     <img class="main-image" alt="main-img" src="../assets/img.png">
   </div>
   <div>
     <hr>
     <HelloWorld msg="Welcome to StudyConnection"/>
   </div>
  </div>
  </main>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'PageHome',
  components: {
    HelloWorld
  }
}
</script>

<style scoped>
.main-image {
  width: 50vw;
  height: 30vh;
  object-fit: cover;
}
</style>
