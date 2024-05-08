import { createRouter, createWebHistory } from 'vue-router'
import PageHome from '@/views/PageHome.vue'
import BoardList from '@/views/board/BoardList.vue'
import BoardDetail from '@/views/board/BoardDetail.vue'
import BoardWrite from '@/views/board/BoardWrite.vue'
import Login from '@/views/Login.vue'
import store from "@/Vuex/store";
const routes = [
  {
    path: '/',
    name: 'PageHome',
    component: PageHome
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/MyPage',
    name: 'MyPage',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/MyPage.vue'),
    meta: { requiresAuth: true } // 로그인이 필요한 페이지임을 명시
  },
  {
    path: '/board/list',
    name: 'BoardList',
    component: BoardList,
    meta: { requiresAuth: true } // 로그인이 필요한 페이지임을 명시
  },
  {
    path: '/board/detail',
    name: 'BoardDetail',
    component: BoardDetail
  },
  {
    path: '/board/write',
    name: 'BoardWrite',
    component: BoardWrite
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach((to, from, next) => {
  // 로그인이 필요한 페이지에 접근하려고 할 때
  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (!store.getters.getIsAuth) { // Vuex 스토어에서 로그인 상태 확인
      alert('로그인이 필요합니다.');
      console.log(store.getters.getIsAuth)
      next({ name: 'Login' }); // 로그인 페이지로 리디렉션
    } else {
      next(); // 로그인 상태면 요청한 페이지로 이동
    }
  } else {
    next(); // 로그인이 필요 없는 페이지는 그대로 진행
  }
});

export default router
