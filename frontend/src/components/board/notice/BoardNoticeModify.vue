<template>
  <v-card class="elevation-0">
    <v-img class="white--text align-end" height="300px" src="https://cdn.vuetifyjs.com/images/cards/docks.jpg">
      <v-card-title style="font-size: 30px">
        <v-text-field
          id="subject"
          v-model="article.subject"
          :rules="subRule"
          hide-details="auto"
          name="subject"
          label="제목"
          type="text"
        ></v-text-field>
      </v-card-title>
    </v-img>
    <v-card-text>
      <div class="mb-3" style="font-size: 20px">
        <v-icon x-large>mdi-account</v-icon>
        {{ article.userid }}
      </div>
      <v-card-text>
        <v-textarea
          id="content"
          v-model="article.content"
          :rules="contRule"
          hide-details="auto"
          name="content"
          label="내용"
          type="text"
        ></v-textarea>
      </v-card-text>
    </v-card-text>
    <v-divider></v-divider>
    <v-card-actions v-if="userInfo && userInfo.userid === article.userid">
      <v-spacer></v-spacer>
      <v-btn text @click="moveBefore"> 이전으로 </v-btn>
      <v-btn color="#DC6E6E" text @click="onSubmit"> 수정완료 </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import http from "@/util/http";

export default {
  name: "BoardNoticeModify",
  data() {
    return {
      article: {
        articleno: 0,
        userid: "",
        subject: "",
        content: "",
      },
      isUserid: false,

      idRule: [
        (value) => !!value || "필수 입력 정보 입니다",
        (value) => (value && value.length >= 2) || "사용자 이름은 최소 2글자 이상입니다",
      ],
      subRule: [
        (value) => !!value || "필수 입력 정보 입니다",
        (value) => (value && value.length >= 2) || "제목은 최소 2글자 이상입니다",
      ],
      contRule: [
        (value) => !!value || "필수 입력 정보 입니다",
        (value) => (value && value.length >= 5) || "내용은 최소 5글자 이상입니다",
      ],
    };
  },
  props: {
    type: { type: String },
  },
  created() {
    http.get(`/notice/${this.$route.params.articleno}`).then(({ data }) => {
      this.article = data;
    });
    this.isUserid = true;
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();

      let err = true;
      let msg = "";
      !this.article.userid && ((msg = "작성자 입력해주세요"), (err = false), this.$refs.userid.focus());
      err && !this.article.subject && ((msg = "제목 입력해주세요"), (err = false), this.$refs.subject.focus());
      err && !this.article.content && ((msg = "내용 입력해주세요"), (err = false), this.$refs.content.focus());

      if (!err) alert(msg);
      else this.modifyArticle();
      this.moveBefore();
    },
    onReset(event) {
      event.preventDefault();
      this.article.articleno = 0;
      this.article.subject = "";
      this.article.content = "";
      this.moveList();
    },
    modifyArticle() {
      http
        .put(`/board`, {
          articleno: this.article.articleno,
          userid: this.article.userid,
          subject: this.article.subject,
          content: this.article.content,
        })
        .then(({ data }) => {
          let msg = "수정 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "수정이 완료되었습니다.";
          }
          alert(msg);
          // 현재 route를 /list로 변경.
          this.moveList();
        });
    },
    moveBefore() {
      this.$router.push({ path: "" + this.article.articleno });
    },
  },
};
</script>

<style scoped>
.v-card__title .v-label.v-label--active.theme--light {
  color: lightgrey;
}
#subject {
  color: lightgrey;
  font-size: 30px;
  margin-top: 15px;
}
</style>
