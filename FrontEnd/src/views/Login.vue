<template>
  <body>
    <div class="container" :class="{ active: isActive }">
      <div class="form-container sign-up">
        <form @submit.prevent="handleSignUp">
          <h1>Create Account</h1><br>
          <input type="tel" placeholder="PhoneNumber" v-model="signUpData.userId"> <!--전화번호 -->
          <input type="text" placeholder="Name" v-model="signUpData.username">
          <input type="email" placeholder="Email" v-model="signUpData.email">
          <input type="date" placeholder="Birth 2000-01-01" v-model="signUpData.birth">
          <input type="password" placeholder="Password" v-model="signUpData.password">
          <input type="password" placeholder="Confirm Password" v-model="signUpData.confirmPassword">
          <button type="submit" @click="activateContainer">Sign Up</button>
        </form>
      </div>
      <div class="form-container sign-in">
        <form @submit.prevent="handleLogin">
          <h1>Sign In</h1><br>
          <span>use your ID password</span>
          <input type="tel" placeholder="Enter your PhoneNumber" v-model="LoginData.userId">
          <input type="password" placeholder="Password" v-model="LoginData.userPw">
<!--          <a href="#">Forget Your Password?</a>-->
          <button type="submit" @click="deactivateContainer">Sign In</button>
        </form>
      </div>
      <div class="toggle-container">
        <div class="toggle">
          <div class="toggle-panel toggle-left">
            <h1>Welcome!</h1>
            <p>Enter your personal details to use all of site features</p>
            <button class="hidden" id="login" @click="deactivateContainer">Sign In</button>
          </div>
          <div class="toggle-panel toggle-right">
            <h1>StudyConnection</h1>
            <p>Register with your personal details to use all of site features</p>
            <button class="hidden" id="register" @click="activateContainer">Sign Up</button>
          </div>
        </div>
      </div>
    </div>
  </body>
</template>

<script>
import {mapActions, mapGetters, mapState} from 'vuex'
import {SET_ERROR_STATE} from "@/Vuex/mutation_types";
import router from "@/router";   //vuex 추가

export default {
  computed: {
    ...mapState(['errorState','userData']),   //vuex/state에 있는 errorState, userData
  },

  data() {
    return {
      LoginData: {
        userId: '',
        userPw: '',
      },

      signUpData: {
        userId: '',
        username: '',
        email: '',
        birth: '',
        password: '',
        confirmPassword: ''
      },
    }
  },
  methods: {
    ...mapActions(['login', 'signUp']),     //vuex/actions에 있는 login 함수

    async handleLogin() {       //async 함수로 변경
      if (this.LoginData.userId === '') {
        alert('ID를 입력하세요.')
        return
      }

      if (this.LoginData.userPw === '') {
        alert('비밀번호를 입력하세요.')
        return
      }

      //로그인 API 호출
      try {
        const loginResult = await this.login(this.LoginData);
        if (loginResult) {
          alert(this.$store.state.userData.username + '님 환영합니다.');
          this.$router.push('/');
        } else {
          alert('로그인 실패. 다시 시도해주세요.');
        }
      } catch (err) {
        if (err.message.indexOf('Network Error') > -1) {
          alert('서버에 접속할 수 없습니다. 상태를 확인해주세요.')
        } else {
          alert('로그인 정보를 확인할 수 없습니다.')
          window.location.reload();
        }
      }
    },

    async handleSignUp() {
      if (this.signUpData.userId === '' ||
          this.signUpData.username === '' ||
          this.signUpData.email === '' ||
          this.signUpData.birth === '' ||
          this.signUpData.password === ''){
        alert('모든 정보를 입력하세요.');
        return;
      }

      if (this.signUpData.password !== this.signUpData.confirmPassword) {
        alert('비밀번호가 일치하지 않습니다.');
        return;
      }
      try {
        const result = await this.signUp(this.signUpData);
        console.log('회원가입 결과:', result);
        if (result) {
          alert('회원가입 성공!');
          window.location.reload();
        } else {
          if(this.errorState === 409)
            alert('이미 가입된 회원입니다.');
          else
            alert('회원가입 실패. 다시 시도해주세요.');
        }
      } catch (error) {
        alert('회원가입 중 오류가 발생했습니다.');
      }
    }
  },
}
</script>

<script setup>
import { ref } from 'vue';

const isActive = ref(false);

const activateContainer = () => {
  isActive.value = true;
};

const deactivateContainer = () => {
  isActive.value = false;
};


</script>

<style lang ="scss" scoped>
@import url('https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;500;600;700&display=swap');

*{
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: 'Montserrat', sans-serif;
  z-index: 1;
  overflow: hidden;
}

body{
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  height: 75vh;
}

.container{
  background-color: #fff;
  border-radius: 30px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.35);
  position: relative;
  overflow: hidden;
  width: 768px;
  max-width: 100%;
  min-height: 480px;
}

.container p{
  font-size: 14px;
  line-height: 20px;
  letter-spacing: 0.3px;
  margin: 20px 0;
}

.container span{
  font-size: 12px;
}

.container a{
  color: #333;
  font-size: 13px;
  text-decoration: none;
  margin: 15px 0 10px;
}

.container button{
  background-color: var(--primary-alt);
  color: #fff;
  font-size: 12px;
  padding: 10px 45px;
  border: 1px solid transparent;
  border-radius: 8px;
  font-weight: 600;
  letter-spacing: 0.5px;
  text-transform: uppercase;
  margin-top: 10px;
  cursor: pointer;
}

.container button.hidden{
  background-color: transparent;
  border-color: #fff;
}

.container form{
  background-color: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  padding: 0 40px;
  height: 100%;
}

.container input{
  background-color: #eee;
  border: none;
  margin: 8px 0;
  padding: 10px 15px;
  font-size: 13px;
  border-radius: 8px;
  width: 100%;
  outline: none;
}

.form-container{
  position: absolute;
  top: 0;
  height: 100%;
  transition: all 0.6s ease-in-out;
}

.sign-in{
  left: 0;
  width: 50%;
  z-index: 2;
}

.container.active .sign-in{
  transform: translateX(100%);
}

.sign-up{
  left: 0;
  width: 50%;
  opacity: 0;
  z-index: 1;
}

.container.active .sign-up{
  transform: translateX(100%);
  opacity: 1;
  z-index: 5;
  animation: move 0.6s;
}

@keyframes move{
  0%, 49.99%{
    opacity: 0;
    z-index: 1;
  }
  50%, 100%{
    opacity: 1;
    z-index: 5;
  }
}

.social-icons{
  margin: 20px 0;
}

.social-icons a{
  border: 1px solid #ccc;
  border-radius: 20%;
  display: inline-flex;
  justify-content: center;
  align-items: center;
  margin: 0 3px;
  width: 40px;
  height: 40px;
}

.toggle-container{
  position: absolute;
  top: 0;
  left: 50%;
  width: 50%;
  height: 100%;
  overflow: hidden;
  transition: all 0.6s ease-in-out;
  border-radius: 150px 0 0 100px;
  z-index: 1000;
}

.container.active .toggle-container{
  transform: translateX(-100%);
  border-radius: 0 150px 100px 0;
}

.toggle{
  background-color: var(--primary-alt);
  height: 100%;
  background: linear-gradient(288deg, var(--primary), #043873);
  color: #fff;
  position: relative;
  left: -100%;
  height: 100%;
  width: 200%;
  transform: translateX(0);
  transition: all 0.6s ease-in-out;
}

.container.active .toggle{
  transform: translateX(50%);
}

.toggle-panel{
  position: absolute;
  width: 50%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  padding: 0 30px;
  text-align: center;
  top: 0;
  transform: translateX(0);
  transition: all 0.6s ease-in-out;
}

.toggle-left{
  transform: translateX(-200%);
}

.container.active .toggle-left{
  transform: translateX(0);
}

.toggle-right{
  right: 0;
  transform: translateX(0);
}

.container.active .toggle-right{
  transform: translateX(200%);
}
</style>
