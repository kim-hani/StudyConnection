<template>
  <div class="app">
    <div class="side-bar-wrapper">
      <Sidebar/><!--Sidebar-->
    </div>
    <div class="main-view-wrapper">
      <div class="main-view-header">
        <router-view/>  <!-- 페이지 이동이 표시될 곳 -->
      </div>
    </div>
  </div>
</template>
<script>
import PageHeader from '@/components/PageHeader'
import PageFooter from '@/components/PageFooter'
import Sidebar from '@/components/Sidebar.vue'

export default {
  name: 'App',
  components: {
    PageFooter,
    PageHeader,
    Sidebar
  }
}
</script>
<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}

:root {
  --primary: #4ade80;
  --primary-alt: #22c55e;
  --grey: #64748b;
  --dark: #1e293b;
  --dark-alt: #334155;
  --light: #f1f5f9;
  --sidebar-width: 300px;
}

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: 'Fira sans', sans-serif;
}

body {
  background: var(--light);
}

button {
  cursor: pointer;
  appearance: none;
  border: none;
  outline: none;
  background: none;
}

.app {
  display: flex;
  width: 100%;
  height: 100vh;
  overflow: hidden;

  .main-view-wrapper{
    width: 100%;
    overflow-y: auto;
  }

  .main-view-header{
    padding: 5rem;
  }

  main {
    flex: 1 1 0;
    padding: 2rem;
    @media (max-width: 1024px) {
      padding-left: 6rem;
    }
  }
}
</style>
