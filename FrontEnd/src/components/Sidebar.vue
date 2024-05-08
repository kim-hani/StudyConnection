<template>
  <aside :class="`${is_expanded ? 'is-expanded' : ''}`">
    <div class="logo">
      <span class="material-symbols-outlined">handshake</span>
      <!--img :src="logoURL" alt="Vue" /-->
    </div>

    <div class="menu-toggle-wrap">
      <button class="menu-toggle" @click="ToggleMenu">
        <span class="material-icons">keyboard_double_arrow_right</span>
      </button>
    </div>

    <h3>Menu</h3>
    <div class="menu">
      <router-link to="/" class="button">
        <span class="material-symbols-outlined">home</span>
        <span class="text">Main Page</span>
      </router-link>
      <router-link to="/login" class="button">
        <span class="material-symbols-outlined">login</span>
        <span class="text">Sign in</span>
      </router-link>
      <router-link to="/board/list" class="button">
        <span class="material-symbols-outlined">description</span>
        <span class="text">Study Broad</span>
      </router-link>
      <router-link to="/contact" class="button">
        <span class="material-symbols-outlined">chat</span>
        <span class="text">Chat</span>
      </router-link>
      <router-link to="/MyPage" class="button">
        <span class="material-symbols-outlined">person</span>
        <span class="text">My Page</span>
      </router-link>

    </div>

    <div class="flex"></div>

    <div class="menu">
      <router-link to="/settings" class="button">
        <span class="material-symbols-outlined">ecg_heart</span>
        <span class="text" style="letter-spacing: 2px;">&nbsp;HeartBeat.Team</span>
      </router-link>
    </div>
  </aside>
</template>

<script>
import { ref } from 'vue';
import { mapState } from "vuex";

export default {
  setup() {
    // 로컬 스토리지에서 상태를 불러와 반응형 참조로 저장
    const is_expanded = ref(localStorage.getItem("is_expanded") === "true");

    // 메뉴 토글 메서드
    function ToggleMenu() {
      is_expanded.value = !is_expanded.value;
      localStorage.setItem("is_expanded", is_expanded.value.toString()); // Boolean 값을 문자열로 변환하여 저장
    }

    return {
      is_expanded,
      ToggleMenu
    };
  },
  computed: {
    ...mapState({
      isAuth: state => state.isAuth
    })
  }
}
</script>

<style lang="scss" scoped>
aside {
  display: flex;
  flex-direction: column;

  background-color: var(--dark);
  color: var(--light);

  width: calc(2rem + 32px);
  overflow: hidden;
  min-height: 100vh;
  padding: 1rem;

  transition: 0.2s ease-in-out;

  .flex {
    flex: 1 1 0%;
  }

  .logo {
    margin-bottom: 1rem;

    img {
      width: 2rem;
    }
  }

  .menu-toggle-wrap {
    display: flex;
    justify-content: flex-end;
    margin-bottom: 1rem;

    position: relative;
    top: 0;
    transition: 0.2s ease-in-out;

    .menu-toggle {
      transition: 0.2s ease-in-out;
      .material-icons, .material-symbols-outlined {
        font-size: 2rem;
        color: var(--light);
        transition: 0.2s ease-out;
      }

      &:hover {
        .material-icons, .material-symbols-outlined {
          color: var(--primary);
          transform: translateX(0.5rem);
        }
      }
    }
  }

  h3, .button .text {
    opacity: 0;
    transition: opacity 0.3s ease-in-out;
  }

  h3 {
    color: var(--grey);
    font-size: 0.875rem;
    margin-bottom: 0.5rem;
    text-transform: uppercase;
  }

  .menu {
    margin: 0 -1rem;

    .button {
      display: flex;
      align-items: center;
      text-decoration: none;

      transition: 0.2s ease-in-out;
      padding: 0.5rem 1rem;

      .material-icons, .material-symbols-outlined {
        font-size: 2rem;
        color: var(--light);
        transition: 0.2s ease-in-out;
      }
      .text {
        color: var(--light);
        transition: 0.2s ease-in-out;
      }

      &:hover {
        background-color: var(--dark-alt);

        .material-icons, .material-symbols-outlined, .text {
          color: var(--primary);
        }
      }

      &.router-link-exact-active {
        background-color: var(--dark-alt);
        border-right: 5px solid var(--primary);

        .material-icons, .material-symbols-outlined, .text {
          color: var(--primary);
        }
      }
    }
  }

  .footer {
    opacity: 0;
    transition: opacity 0.3s ease-in-out;

    p {
      font-size: 0.875rem;
      color: var(--grey);
    }
  }

  &.is-expanded {
    width: var(--sidebar-width);

    .menu-toggle-wrap {
      top: -3rem;

      .menu-toggle {
        transform: rotate(-180deg);
      }
    }

    h3, .button .text {
      opacity: 1;
    }

    .button {
      .material-icons, .material-symbols-outlined {
        margin-right: 1rem;
      }
    }

    .footer {
      opacity: 0;
    }
  }

  @media (max-width: 1024px) {
    position: absolute;
    z-index: 99;
  }
}
</style>
