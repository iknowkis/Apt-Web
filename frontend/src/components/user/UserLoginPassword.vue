<template>
  <v-dialog v-model="dialog" width="500">
    <template v-slot:activator="{ attrs }">
      <v-btn text class="mr-2" v-bind="attrs" @click="showFindPage">비밀번호 찾기</v-btn>
    </template>
    <v-card>
      <v-toolbar elevation="5" color="white">
        <v-toolbar-title>비밀번호 찾기</v-toolbar-title>
        <v-spacer></v-spacer>
        <v-btn
          icon
          @click="
            {
              dialog = false;
            }
          "
          ><v-icon>mdi-close</v-icon></v-btn
        >
      </v-toolbar>
      <v-card-title>등록된 아이디와 이메일 입력해주세요</v-card-title>
      <v-card-text>회원가입 시 입력한 이메일로 비밀번호를 전송합니다</v-card-text>
      <v-divider></v-divider>
      <v-card-text>
        <v-form v-model="findInfoValid" class="mt-5">
          <v-text-field
            prepend-icon="mdi-account"
            :rules="findIdRule"
            hide-details="auto"
            label="아이디"
            type="text"
            v-model="findUser.userid"
          ></v-text-field>
          <v-row no-gutters>
            <v-col class="d-flex" cols="12" sm="6">
              <v-text-field
                prepend-icon="mdi-email"
                :rules="findEmailRule"
                hide-details="auto"
                label="이메일 아이디"
                type="text"
                v-model="findUser.emailId"
              ></v-text-field>
            </v-col>
            <v-col class="d-flex" cols="12" sm="1">
              <v-icon>mdi-at</v-icon>
            </v-col>
            <v-col class="d-flex" cols="12" sm="5">
              <v-select
                label="이메일 도메인"
                :rules="[(v) => !!v || '이메일 도메인을 선택해주세요']"
                :items="findEmailItems"
                v-model="findUser.emailDomain"
              ></v-select>
            </v-col>
          </v-row>
        </v-form>
      </v-card-text>

      <v-divider></v-divider>

      <v-card-actions>
        <v-btn text @click="returnLogin">로그인 페이지 돌아가기</v-btn>
        <v-spacer></v-spacer>
        <v-btn text color="primary" :disabled="!findInfoValid" @click="sendPassword">비밀번호 전송</v-btn>
      </v-card-actions>
    </v-card>
    <v-snackbar top v-model="findSnackbar" :timeout="timeout" text :color="findSnackColor">
      {{ findInfoMsg }}

      <template v-slot:action="{ attrs }">
        <v-btn :color="findSnackColor" icon v-bind="attrs" @click="findSnackbar = false">
          <v-icon> mdi-close</v-icon>
        </v-btn>
      </template>
    </v-snackbar>
  </v-dialog>
</template>

<script>
import { apiInstance } from "@/api/index.js";

const api = apiInstance();
export default {
  data() {
    return {
      findUser: {
        userid: "",
        emailId: "",
        emailDomain: "",
      },
      findInfoMsg: "",
      findInfoValid: false,
      findSnackbar: false,
      findSnackColor: "",
      dialog: false,
      timeout: 3000,
      findIdRule: [
        (value) => !!value || "아이디를 입력해주세요",
        (value) => (value && value.length >= 5) || "아이디는 최소 5글자 이상입니다",
      ],
      findEmailRule: [(value) => !!value || "이메일 아이디를 입력해주세요"],
      findEmailItems: ["gmail.com", "naver.com", "daum.net"],
    };
  },
  methods: {
    showFindPage() {
      this.dialog = true;
    },
    sendPassword() {
      api
        .post(`/user/find`, {
          userid: this.findUser.userid,
          email: this.findUser.emailId + "@" + this.findUser.emailDomain,
        })
        .then(({ data }) => {
          if (data === "okay") {
            this.findInfoMsg = "비밀번호가 전송되었습니다. 이메일을 확인해주세요.";
            this.findSnackColor = "light-blue";
            this.findSnackbar = true;
          } else if (data === "no") {
            this.findInfoMsg = "회원정보를 찾을 수 없습니다. 아이디와 이메일을 다시 확인해주세요.";
            this.findSnackColor = "orange";
            this.findSnackbar = true;
          } else {
            this.findInfoMsg = "서버 에러 발생";
            this.findSnackColor = "error";
            this.findSnackbar = true;
          }
        });
    },
    returnLogin() {
      this.dialog = false;
    },
  },
};
</script>

<style></style>
