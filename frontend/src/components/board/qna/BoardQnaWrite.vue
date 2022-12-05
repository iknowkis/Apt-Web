<template>
  <v-card>
    <v-toolbar elevation="0" color="white">
      <v-toolbar-title>질문 등록</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn icon to="list"><v-icon>mdi-arrow-left</v-icon></v-btn>
    </v-toolbar>
    <v-divider></v-divider>
    <v-card-text>
      <v-form ref="form" v-model="valid">
        <v-text-field
          color="info"
          id="subject"
          v-model="qnaArticle.subject"
          :rules="subRule"
          hide-details="auto"
          name="subject"
          label="제목"
          type="text"
          clearable
        ></v-text-field>
        <v-card-title class="pa-0 mt-3">
          <v-avatar size="50px">
            <v-img :src="require(`@/assets/profile/${qnaWriteImgItems[userInfo.img]}.gif`)"></v-img>
          </v-avatar>
          {{ userInfo.username }}
        </v-card-title>
        <v-textarea
          id="content"
          v-model="qnaArticle.content"
          :rules="contRule"
          hide-details="auto"
          name="content"
          label="내용"
          type="text"
          rows="7"
          clearable
        ></v-textarea>
      </v-form>
    </v-card-text>
    <v-divider></v-divider>
    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn text to="list">이전으로</v-btn>
      <v-btn :disabled="!valid" color="primary" text @click="registArticle">작성완료</v-btn>
    </v-card-actions>
    <v-snackbar top v-model="snackbar" :timeout="timeout" text color="light-blue">
      <template v-slot:action="{ attrs }">
        <v-btn color="blue" icon v-bind="attrs" @click="snackbar = false">
          <v-icon> mdi-close</v-icon>
        </v-btn>
      </template>
    </v-snackbar>
  </v-card>
</template>

<script>
import http from "@/util/http";
import { mapState } from "vuex";

const memberStore = "memberStore";
export default {
  name: "BoardQnaWrite",
  data() {
    return {
      qnaWriteImgItems: ["profile1", "profile2", "profile3", "profile4", "profile5"],
      valid: true,
      snackbar: false,
      msg: "",
      timeout: 3000,
      qnaArticle: {
        userid: "",
        subject: "",
        content: "",
      },

      subRule: [
        (value) => !!value || "필수 입력 정보 입니다",
        (value) => (value && value.length >= 2) || "제목은 최소 2글자 이상입니다",
      ],
      contRule: [(value) => !!value || "필수 입력 정보 입니다"],
    };
  },
  created() {
    this.qnaArticle.userid = this.userInfo.userid;
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
  },
  methods: {
    validate() {
      this.$refs.form.validate();
    },
    registArticle() {
      http
        .post(`/qna`, {
          userid: this.qnaArticle.userid,
          subject: this.qnaArticle.subject,
          content: this.qnaArticle.content,
        })
        .then(({ data }) => {
          this.msg = "글 작성 실패, 서버 에러가 발생하였습니다.";
          if (data === "success") {
            this.msg = "글 작성이 완료되었습니다.";
          }
          this.snackbar = true;
          this.moveList();
        });
    },
    moveList() {
      this.$router.push({ name: "qnaList" });
    },
  },
};
</script>

<style scoped>
#subject {
  font-size: 30px;
  margin-top: 15px;
}
</style>
