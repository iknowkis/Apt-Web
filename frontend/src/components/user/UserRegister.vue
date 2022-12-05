<template>
  <v-card elevation="25">
    <v-toolbar style="z-index: 100" elevation="5" color="white">
      <v-toolbar-title>회원가입</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn icon to="/"><v-icon>mdi-close</v-icon></v-btn>
    </v-toolbar>
    <v-flex d-flex
      ><v-img height="550px" width="50%" src="@/assets/registerDog.jpg"></v-img>
      <v-card width="50%">
        <v-card-title>아직 회원이 아니신가요?</v-card-title>
        <v-divider></v-divider>
        <v-card-text>
          <v-stepper v-model="e1">
            <v-stepper-header>
              <template v-for="n in steps">
                <v-stepper-step :key="`${n}-step`" :complete="e1 > n" :step="n"> </v-stepper-step>

                <v-divider v-if="n !== steps" :key="n"></v-divider>
              </template>
            </v-stepper-header>

            <v-stepper-items>
              <v-stepper-content no-gutters step="1">
                <v-card elevation="0">
                  <v-card-title> Happy House </v-card-title>
                  <v-card-subtitle class="mb-15"> 회원가입을 통해 유용한 기능을 만나보세요</v-card-subtitle>
                  <v-card-actions style="margin-top: 150px">
                    <v-spacer></v-spacer>
                    <v-btn color="primary" x-large text @click="nextStep(1)"> 회원가입 시작하기! </v-btn>
                  </v-card-actions>
                </v-card>
              </v-stepper-content>
              <!-- 아이디 이름 입력 -->
              <v-stepper-content step="2">
                <v-card elevation="0">
                  <v-card-title> 기본정보 설정 </v-card-title>
                  <v-card-subtitle> 기본 정보를 입력합니다</v-card-subtitle>
                  <v-form v-model="defaultValid">
                    <v-row no-gutters>
                      <v-text-field
                        prepend-icon="mdi-account"
                        :rules="registerIdRule"
                        :disabled="checkId"
                        hide-details="auto"
                        label="아이디"
                        v-model="registerUser.userid"
                        type="text"
                        detail="아이디"
                      ></v-text-field>
                      <v-btn style="margin-top: 15px" text @click="idCheck"> {{ checkBtn }} </v-btn>
                    </v-row>
                    <v-alert
                      class="mt-3"
                      v-model="checkAlert"
                      outlined
                      dismissible
                      border="left"
                      transition="scale-transition"
                      :type="checkAlertType"
                      elevation="0"
                    >
                      {{ checkAlertMsg }}
                    </v-alert>
                    <v-text-field
                      class="mb-4"
                      prepend-icon="mdi-account-edit"
                      :rules="registerNameRule"
                      hide-details="auto"
                      v-model="registerUser.username"
                      label="이름"
                      type="text"
                    ></v-text-field>
                  </v-form>
                  <v-divider></v-divider>
                  <v-card-actions class="mt-2">
                    <v-btn text @click="beforeStep(2)"> 이전 </v-btn>
                    <v-spacer></v-spacer>
                    <v-btn color="primary" :disabled="!defaultValid" text @click="nextStep(2)"> 다음 </v-btn>
                  </v-card-actions>
                </v-card>
              </v-stepper-content>
              <!-- 비밀번호 설정 -->
              <v-stepper-content step="3">
                <v-card elevation="0">
                  <v-card-title> 비밀번호 설정 </v-card-title>
                  <v-card-subtitle> 사용할 비밀번호를 설정합니다</v-card-subtitle>
                  <v-form v-model="passValid">
                    <v-text-field
                      prepend-icon="mdi-lock"
                      :rules="registerPwRule"
                      hide-details="auto"
                      label="비밀번호"
                      v-model="registerUser.userpwd"
                      type="password"
                    ></v-text-field>
                    <v-text-field
                      class="mb-4"
                      prepend-icon="mdi-lock-check"
                      :rules="registerPwCheckRule"
                      hide-details="auto"
                      label="비밀번호 확인"
                      v-model="registerUser.userpwdCheck"
                      type="password"
                    ></v-text-field>
                  </v-form>
                  <v-divider></v-divider>
                  <v-card-actions class="mt-2">
                    <v-btn text @click="beforeStep(3)"> 이전 </v-btn>
                    <v-spacer></v-spacer>
                    <v-btn color="primary" :disabled="!passValid" text @click="nextStep(3)"> 다음 </v-btn>
                  </v-card-actions>
                </v-card>
              </v-stepper-content>
              <v-stepper-content step="4">
                <v-card elevation="0">
                  <v-card-title> 이메일 등록 </v-card-title>
                  <v-form v-model="emailValid">
                    <v-row no-gutters>
                      <v-col class="d-flex" cols="12" sm="6">
                        <v-text-field
                          prepend-icon="mdi-email"
                          :rules="registerEmailIdRule"
                          hide-details="auto"
                          v-model="registerUser.emailId"
                          label="이메일 아이디"
                          type="text"
                        ></v-text-field>
                      </v-col>
                      <v-col class="d-flex" cols="12" sm="1">
                        <v-icon>mdi-at</v-icon>
                      </v-col>
                      <v-col class="d-flex" cols="12" sm="5">
                        <v-select
                          :items="registerEmailItems"
                          :rules="[(v) => !!v || '이메일 도메인을 선택해주세요']"
                          label="이메일 도메인"
                          v-model="registerUser.emailDomain"
                        ></v-select>
                      </v-col>
                    </v-row>
                  </v-form>
                  <v-row no-gutters
                    ><v-spacer></v-spacer
                    ><v-btn :disabled="!emailValid" @click="verifySend" color="primary" text>{{ sendBtn }}</v-btn
                    ><v-spacer></v-spacer
                  ></v-row>
                  <v-row no-gutters>
                    <v-text-field
                      class="mb-3"
                      :disabled="!verifyValid"
                      prepend-icon="mdi-email-check"
                      hide-details="auto"
                      v-model="registerUser.verification"
                      label="인증번호 입력"
                      type="text"
                    ></v-text-field>
                    <v-btn :disabled="!verifyValid" style="margin-top: 15px" text @click="verifyCheck"> 인증번호 확인 </v-btn>
                  </v-row>

                  <v-divider></v-divider>
                  <v-card-actions class="mt-2">
                    <v-btn text @click="beforeStep(4)"> 이전 </v-btn>
                    <v-spacer></v-spacer>
                    <v-btn color="primary" :disabled="!totalValid" text @click="nextStep(4)"> 다음 </v-btn>
                  </v-card-actions>
                </v-card>
              </v-stepper-content>
              <v-stepper-content no-gutters step="5">
                <v-card elevation="0">
                  <v-card-title> {{ registerTitle }} </v-card-title>
                  <v-card-subtitle class="mb-15"> {{ registerContent }}</v-card-subtitle>
                  <v-card-actions class="mt-15">
                    <v-spacer></v-spacer>
                    <v-btn v-if="registerStatus" color="primary" x-large text to="/user/login">
                      로그인하러 갑시다!
                    </v-btn>
                  </v-card-actions>
                </v-card>
              </v-stepper-content>
            </v-stepper-items>
          </v-stepper>
        </v-card-text>
        <v-divider></v-divider>
        <v-card-actions class="mt-1">
          <v-spacer></v-spacer>
          <v-btn text :to="{ name: 'login' }">로그인 페이지 돌아가기</v-btn>
          <v-spacer></v-spacer>
        </v-card-actions>
      </v-card>
    </v-flex>
    <v-snackbar top v-model="sendSnackbar" :timeout="timeout" text color="primary">
      인증번호가 전송되었습니다. <strong>이메일</strong>을 확인해주세요.

      <template v-slot:action="{ attrs }">
        <v-btn color="primary" icon v-bind="attrs" @click="sendSnackbar = false">
          <v-icon> mdi-close</v-icon>
        </v-btn>
      </template>
    </v-snackbar>
    <v-snackbar top v-model="checkSnackbar" :timeout="timeout" text color="success">
      인증번호 확인이 완료되었습니다.

      <template v-slot:action="{ attrs }">
        <v-btn color="success" icon v-bind="attrs" @click="checkSnackbar = false">
          <v-icon> mdi-close</v-icon>
        </v-btn>
      </template>
    </v-snackbar>
    <v-snackbar top v-model="checkFailSnackbar" :timeout="timeout" text color="red">
      인증번호가 일치하지 않습니다. 다시 한 번 확인해주세요.

      <template v-slot:action="{ attrs }">
        <v-btn color="red" icon v-bind="attrs" @click="checkFailSnackbar = false">
          <v-icon> mdi-close</v-icon>
        </v-btn>
      </template>
    </v-snackbar>
    <v-snackbar top v-model="checkIdSnackbar" :timeout="timeout" text color="error">
      아이디 중복검사 하셨나요??

      <template v-slot:action="{ attrs }">
        <v-btn color="error" icon v-bind="attrs" @click="checkIdSnackbar = false">
          <v-icon> mdi-close</v-icon>
        </v-btn>
      </template>
    </v-snackbar>
  </v-card>
</template>

<script>
import { apiInstance } from "@/api/index.js";

const api = apiInstance();

export default {
  name: "UserRegister",
  data() {
    return {
      e1: 1,
      steps: 5,
      checkId: false,
      usedId: false,
      sendSnackbar: false,
      checkSnackbar: false,
      checkFailSnackbar: false,
      checkIdSnackbar: false,
      timeout: 3000,
      defaultValid: false,
      passValid: false,
      emailValid: false,
      verifyValid: false,
      totalValid: false,
      sendVerify: null,
      registerStatus: false,
      registerTitle: "",
      registerContent: "",
      checkAlert: false,
      checkAlertMsg: "",
      checkAlertType: "success",
      checkBtn: "아이디 중복 검사",
      sendBtn: "인증번호 전송",
      registerUser: {
        userid: null,
        userpwd: null,
        userpwdCheck: null,
        username: null,
        emailId: null,
        emailDomain: null,
        verification: null,
      },

      registerIdRule: [
        (value) => !!value || "필수 입력 정보 입니다",
        (value) =>
          (value && value.length >= 5) || "아이디는 최소 5글자 이상입니다",
      ],
      registerPwRule: [
        (value) => !!value || "필수 입력 정보 입니다",
        (value) =>
          (value && value.length >= 8) || "비밀번호는 최소 8글자 이상입니다",
      ],
      registerNameRule: [
        (value) => !!value || "필수 입력 정보 입니다",
        (value) =>
          (value && value.length >= 2) || "이름은 최소 2글자 이상입니다",
      ],
      registerEmailIdRule: [(value) => !!value || "필수 입력 정보 입니다"],
      registerPwCheckRule: [
        (value) =>
          value === this.registerUser.userpwd || "비밀번호가 일치하지 않습니다",
      ],
      registerEmailItems: ["gmail.com", "naver.com", "daum.net"],
    };
  },
  methods: {
    beforeStep(n) {
      this.e1 = n - 1;
    },
    nextStep(n) {
      if (n == 4) {
        this.enroll();
        this.e1 = n + 1;
      } else if (n == 2) {
        if (!this.checkId) {
          this.checkIdSnackbar = true;
        } else {
          this.e1 = n + 1;
        }
      } else {
        this.e1 = n + 1;
      }
    },
    enroll() {
      api
        .post(`/user/register`, {
          userid: this.registerUser.userid,
          userpwd: this.registerUser.userpwd,
          username: this.registerUser.username,
          email:
            this.registerUser.emailId + "@" + this.registerUser.emailDomain,
        })
        .then(({ data }) => {
          if (data.message === "success") {
            this.registerStatus = true;
            this.registerTitle = "회원가입 완료";
            this.registerContent = "로그인을 통해 HAPPY HOUSE를 이용해보세요!";
          } else {
            this.registerTitle = "회원가입 실패..";
            this.registerContent =
              "서버 오류가 발생했습니다.. 다시 시도해주세요!";
          }
        });
    },
    verifySend() {
      this.verifyValid = true;
      let tempEmail =
        this.registerUser.emailId + "@" + this.registerUser.emailDomain;
      api.post(`/user/verify`, tempEmail).then(({ data }) => {
        this.sendVerify = data;
      });
      this.sendSnackbar = true;
      this.sendBtn = "재전송";
    },
    verifyCheck() {
      if (this.registerUser.verification === this.sendVerify) {
        this.checkSnackbar = true;
        this.totalValid = true;
      } else {
        this.checkFailSnackbar = true;
        this.totalValid = false;
      }
    },
    idCheck() {
      if (this.checkId) {
        this.checkBtn = "아이디 중복 검사";
        this.checkId = false;
      } else {
        api.post(`/user/check`, this.registerUser.userid).then(({ data }) => {
          if (data === "okay") {
            this.checkId = true;
            this.checkAlertMsg = "사용가능한 아이디 입니다!!";
            this.checkAlertType = "success";

            this.checkBtn = "아이디 재설정";
          } else {
            this.checkAlertMsg = "이미 사용중인 아이디 입니다..";
            this.checkAlertType = "error";
          }
        });
        this.checkAlert = true;
      }
    },
  },
};
</script>

<style scoped>
.v-stepper__content {
  padding: 10px;
}
</style>
