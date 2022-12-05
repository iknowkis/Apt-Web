<template>
  <v-card elevation="25">
    <v-toolbar style="z-index: 100" elevation="5" color="white">
      <v-toolbar-title>로그인</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn icon to="/"><v-icon>mdi-close</v-icon></v-btn>
    </v-toolbar>
    <v-flex d-flex>
      <v-img height="520px" width="50%" src="@/assets/loginCat.jpg"></v-img>
      <v-card width="50%">
        <v-card-title style="color: orange" class="mt-5 mb-5">Welcome!</v-card-title>
        <v-card-text>로그인을 통해 HAPPY HOUSE 만의 다양한 기능을 활용해 보세요!</v-card-text>
        <v-divider></v-divider>
        <v-card-text>
          <v-form ref="loginForm" v-model="loginVaild">
            <v-text-field
              class="mt-15"
              prepend-icon="mdi-account"
              :rules="loginIdRule"
              hide-details="auto"
              label="아이디"
              type="text"
              v-model="user.userid"
            ></v-text-field>
            <v-text-field
              class="mt-5 mb-15"
              prepend-icon="mdi-lock"
              :rules="loginPwdRule"
              hide-details="auto"
              label="비밀번호"
              type="password"
              v-model="user.userpwd"
            ></v-text-field>
          </v-form>
        </v-card-text>
        <v-divider></v-divider>
        <v-card-actions class="mt-2">
          <user-login-password></user-login-password>
          <v-btn text :to="{ name: 'register' }">회원가입</v-btn>
          <v-spacer></v-spacer>
          <v-btn text :disabled="!loginVaild" color="primary" @click="confirm">로그인</v-btn>
        </v-card-actions>
      </v-card>
    </v-flex>
  </v-card>
</template>

<script>
import { mapState, mapActions } from "vuex";

const memberStore = "memberStore";

import UserLoginPassword from "@/components/user/UserLoginPassword.vue";
export default {
  name: "UserLogin",
  data() {
    return {
      loginVaild: false,

      user: {
        userid: null,
        userpwd: null,
      },
      loginIdRule: [(value) => !!value || "아이디를 입력해주세요"],
      loginPwdRule: [(value) => !!value || "비밀번호를 입력해주세요!"],
    };
  },
  components: {
    UserLoginPassword,
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "isLoginError", "userInfo"]),
  },
  methods: {
    ...mapActions(memberStore, ["userConfirm", "getUserInfo"]),
    async confirm() {
      await this.userConfirm(this.user);
      let token = sessionStorage.getItem("access-token");
      if (this.isLogin) {
        await this.getUserInfo(token);
        this.$router.push({ name: "main" });
      }
    },
    movePage() {
      this.$router.push({ name: "join" });
    },
  },
};
</script>

<style scoped>
.v-toolbar__title {
  font-weight: bold;
}
.v-card__title {
  font-weight: bold;
}
</style>
