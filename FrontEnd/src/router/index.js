import { createRouter, createWebHistory } from 'vue-router'
import PageHome from '@/views/PageHome.vue'
import BoardList from '@/views/board/BoardList.vue'
import BoardDetail from '@/views/board/BoardDetail.vue'
import BoardWrite from '@/views/board/BoardWrite.vue'
import Login from '@/views/Login.vue'
import store from "@/Vuex/store";
import BoardEdit from "@/views/board/BoardEdit.vue";
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
    path: '/MyPage/:userId',
    name: 'MyPage',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/MyPage.vue'),
    meta: { requiresAuth: true } // 로그인이 필요한 페이지임을 명시
  },
  {
    path: '/board',
    name: 'BoardList',
    component: BoardList,
    meta: { requiresAuth: true } // 로그인이 필요한 페이지임을 명시
  },
  {
    path: '/board/:id',
    name: 'BoardDetail',
    component: BoardDetail
  },
  {
    path: '/board/write',
    name: 'BoardWrite',
    component: BoardWrite
  },
  {
    path: '/board/:id/edit',
    name: 'BoardEdit',
    component: BoardEdit
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

router.beforeEach((to, from, next) => {
 //경로에 접근하면 로그아웃 시키기
  if (to.path === '/login') {
    if (store.getters.getIsAuth) { // 로그인 상태 확인
      if(confirm('로그아웃 하시겠습니까?')) {
        store.dispatch('logout').then(() => { // 로그아웃 처리
          alert('로그아웃 되었습니다.');
        });
      } else {
        next(false); // 취소하면 로그인 페이지로 리디렉션 취소
      }
      next({ name: 'Login' }); // 로그아웃 후 로그인 페이지로 리디렉션}
    } else {
      next(); // 이미 로그아웃 상태라면 로그인 페이지로 리디렉션
    }
  } else {
    next();
  }
});

export default router
