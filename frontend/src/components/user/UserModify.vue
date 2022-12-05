<template>
  <v-card elevation="25">
    <v-toolbar style="z-index: 100" elevation="5" color="white">
      <v-toolbar-title>내 정보 수정</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn icon to="/"><v-icon>mdi-close</v-icon></v-btn>
    </v-toolbar>
    <v-flex d-flex>
      <v-card width="100%">
        <v-card class="mt-5 ml-3 mr-3" elevation="0" outlined>
          <v-card-title>기본 정보</v-card-title>
          <v-card-text>
            <v-row flex no-gutters>
              <v-col align-self="center" cols="1" class="mr-10">
                <v-avatar size="120px">
                  <v-img :src="require(`@/assets/profile/${modifyImgItems[modifyUser.img]}.gif`)"></v-img>
                </v-avatar>
                <v-menu offset-y style="z-index: 150">
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn text v-bind="attrs" v-on="on"> 이미지 수정 </v-btn>
                  </template>

                  <v-card class="mx-auto">
                    <v-list dense nav>
                      <v-list-item
                        v-for="(imgItem, i) in modifyImgItems"
                        :key="imgItem"
                        link
                        @click="modifyUser.img = i"
                      >
                        <v-img width="80px" :src="require(`@/assets/profile/${imgItem}.gif`)"></v-img>
                      </v-list-item>
                    </v-list>
                  </v-card>
                </v-menu>
              </v-col>
              <v-col align-self="center" cols="10">
                <v-form ref="loginForm" v-model="modifyValid">
                  <v-text-field
                    prepend-icon="mdi-account"
                    hide-details="auto"
                    label="아이디"
                    type="text"
                    v-model="modifyUser.userid"
                    disabled
                  ></v-text-field>
                  <v-text-field
                    prepend-icon="mdi-tag-text"
                    :rules="modifyNameRule"
                    hide-details="auto"
                    label="이름"
                    type="text"
                    v-model="modifyUser.username"
                  ></v-text-field>
                  <v-text-field
                    prepend-icon="mdi-email"
                    hide-details="auto"
                    label="이메일"
                    type="text"
                    v-model="modifyUser.email"
                    disabled
                  ></v-text-field>
                </v-form>
              </v-col>
            </v-row>
          </v-card-text>
        </v-card>
        <v-card class="mt-5 ml-3 mr-3 mb-5" elevation="0" outlined>
          <v-card-title>비밀번호</v-card-title>
          <v-card-text>
            <v-form v-model="modifyValid">
              <v-text-field
                prepend-icon="mdi-lock"
                :rules="modifyPwRule"
                hide-details="auto"
                label="새 비밀번호"
                type="password"
                v-model="modifyUser.userpwd"
              ></v-text-field>
              <v-text-field
                class="mb-4"
                prepend-icon="mdi-lock-check"
                :rules="modifyPwCheckRule"
                hide-details="auto"
                label="새 비밀번호 확인"
                type="password"
              ></v-text-field>
            </v-form>
          </v-card-text>
        </v-card>

        <v-divider></v-divider>
        <v-card-actions class="mt-2">
          <v-spacer></v-spacer>
          <v-btn text :disabled="!modifyValid" color="primary" @click="modify">수정하기</v-btn>
        </v-card-actions>
      </v-card>
    </v-flex>
    <v-snackbar top v-model="modifySnackbar" :timeout="modifyTimeout" text :color="modifySnackColor">
      {{ modifyMsg }}
      <template v-slot:action="{ attrs }">
        <v-btn :color="modifySnackColor" icon v-bind="attrs" @click="modifySnackbar = false">
          <v-icon> mdi-close</v-icon>
        </v-btn>
      </template>
    </v-snackbar>
  </v-card>
</template>

<script>
import { apiInstance } from "@/api/index.js";
import { mapState, mapActions } from "vuex";

const api = apiInstance();

const memberStore = "memberStore";
export default {
  name: "UserModify",
  data() {
    return {
      modifyImgItems: ["profile1", "profile2", "profile3", "profile4", "profile5"],
      modifyTimeout: 3000,
      modifySnackbar: false,
      modifyMsg: "",
      modifySnackColor: "success",
      modifyUser: {
        userid: null,
        username: null,
        email: null,
        userpwd: null,
        img: null,
      },
      modifyValid: false,
      modifyEmail: false,
      modifyPwRule: [
        (value) => !!value || "필수 입력 정보 입니다",
        (value) => (value && value.length >= 8) || "비밀번호는 최소 8글자 이상입니다",
      ],
      modifyNameRule: [
        (value) => !!value || "필수 입력 정보 입니다",
        (value) => (value && value.length >= 2) || "이름은 최소 2글자 이상입니다",
      ],
      modifyPwCheckRule: [(value) => value === this.modifyUser.userpwd || "비밀번호가 일치하지 않습니다"],
    };
  },
  created() {
    this.modifyUser.userid = this.userInfo.userid;
    this.modifyUser.username = this.userInfo.username;
    this.modifyUser.email = this.userInfo.email;
    this.modifyUser.img = this.userInfo.img;
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
  },
  methods: {
    ...mapActions(memberStore, ["userConfirm", "getUserInfo"]),
    async confirm() {
      await this.userConfirm(this.modifyUser);
      let token = sessionStorage.getItem("access-token");
      if (this.isLogin) {
        await this.getUserInfo(token);
      }
    },
    modify() {
      api
        .post(`/user/modify`, {
          userid: this.modifyUser.userid,
          userpwd: this.modifyUser.userpwd,
          username: this.modifyUser.username,
          email: this.modifyUser.email,
          img: this.modifyUser.img,
        })
        .then(({ data }) => {
          if (data === "success") {
            this.modifySnackbar = true;
            this.modifySnackColor = "success";
            this.confirm();
            this.modifyMsg = "회원정보가 수정 되었습니다.";
          } else {
            this.modifySnackbar = true;
            this.modifySnackColor = "error";
            this.modifyMsg = "회원정보 수정 중 에러 발생";
          }
        });
    },
  },
};
</script>

<style></style>
