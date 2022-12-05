<template>
  <v-container class="mb-15">
    <v-toolbar class="pt-3" elevation="0" color="rgba(0,0,0,0)">
      <v-toolbar-title>
        <v-img class="ms-15 mt-0" @click="goMain" contain height="100" width="200" src="@/assets/logo.png"></v-img>
      </v-toolbar-title>

      <v-btn class="mt-7 ml-4" x-large color="white" text @click="goApt"> 아파트 매물 정보 </v-btn>

      <v-spacer></v-spacer>

      <v-btn class="mt-5" color="white" text @click="goNotice"> 공지사항 </v-btn>
      <v-btn class="mt-5" color="white" text @click="goQnA"> Q&A </v-btn>
      <v-menu offset-y open-on-hover v-if="userInfo" style="z-index: 150">
        <template v-slot:activator="{ on, attrs }">
          <v-btn class="mt-5 ml-3 me-15" icon v-bind="attrs" v-on="on"
            ><v-avatar size="30">
              <v-img v-if="userInfo.userid == 'admin'" src="@/assets/profile/admin.gif"></v-img>
              <v-img v-else :src="require(`@/assets/profile/${headerImgItems[userInfo.img]}.gif`)"></v-img>
            </v-avatar>
          </v-btn>
        </template>
        <v-card width="256" class="mx-auto">
          <v-list-item>
            <v-list-item-content>
              <v-list-item-title class="text-h6">
                <strong>{{ userInfo.username }}</strong
                ><span style="font-size: 15px">님, 환영합니다</span>
              </v-list-item-title>
              <v-list-item-subtitle> {{ userInfo.email }} </v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>

          <v-divider></v-divider>

          <v-list dense nav>
            <v-list-item v-if="userInfo.userid != 'admin'" link @click="goUserModify">
              <v-list-item-icon>
                <v-icon>mdi-account-edit</v-icon>
              </v-list-item-icon>
              <v-list-item-content>
                <v-list-item-title>내 정보 수정</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
            <v-list-item link @click="onClickLogout">
              <v-list-item-icon>
                <v-icon>mdi-logout-variant</v-icon>
              </v-list-item-icon>
              <v-list-item-content>
                <v-list-item-title>로그아웃</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list>
        </v-card>
      </v-menu>

      <v-btn class="mt-5 me-15" color="white" text @click="goLogin" v-if="!userInfo">
        로그인 후 이용하세요
        <v-icon class="ml-3">mdi-login-variant</v-icon>
      </v-btn>
    </v-toolbar>
  </v-container>
</template>

<script>
import { mapState, mapGetters, mapActions } from "vuex";

const memberStore = "memberStore";

export default {
  data() {
    return {
      headerImgItems: ["profile1", "profile2", "profile3", "profile4", "profile5"],
    };
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
    ...mapGetters(["checkUserInfo"]),
  },
  methods: {
    ...mapActions(memberStore, ["userLogout"]),
    goMain() {
      this.$router.push({ name: "main" });
    },
    goLogin() {
      this.$router.push({ name: "login" });
    },
    goApt() {
      this.$router.push({ name: "aptlist" });
    },
    goNotice() {
      this.$router.push({ name: "notice" });
    },
    goQnA() {
      this.$router.push({ name: "qna" });
    },
    goUserModify() {
      this.$router.push({ name: "userModify" });
    },
    onClickLogout() {
      this.userLogout(this.userInfo.userid);
      sessionStorage.removeItem("access-token"); //저장된 토큰 없애기
      sessionStorage.removeItem("refresh-token"); //저장된 토큰 없애기
      if (this.$route.path != "/") this.$router.push({ name: "main" });
    },
  },
};
</script>

<style>
#logo-font {
  font-size: 25px;
}
</style>
