<template>
  <v-card class="elevation-0">
    <v-img class="white--text align-end" height="300px" src="@/assets/notice.png"> </v-img>
    <v-card-text>
      <v-row no-gutters>
        <v-col cols="1">
          <v-img width="80px" src="@/assets/profile/admin.gif"></v-img>
        </v-col>
        <v-col cols="10">
          <v-card-title style="font-weight: bold">
            {{ article.subject }}
          </v-card-title>
          <v-card-subtitle> 관리자 </v-card-subtitle>
        </v-col>
      </v-row>
      <v-card-text>
        {{ article.content }}
      </v-card-text>
    </v-card-text>
    <v-divider></v-divider>
    <v-card-actions>
      <v-btn icon to="/board/notice/list"><v-icon>mdi-arrow-left</v-icon></v-btn>
      <v-spacer></v-spacer>
      <v-btn v-if="userInfo && userInfo.userid === article.userid" text @click="moveModifyArticle"> 수정 </v-btn>
      <v-btn v-if="userInfo && userInfo.userid === article.userid" color="#DC6E6E" text @click="deleteArticle">
        삭제
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import http from "@/util/http";
import { mapState } from "vuex";

const memberStore = "memberStore";

export default {
  name: "BoardNoticeDetail",
  data() {
    return {
      article: {},
    };
  },
  created() {
    http.get(`/notice/${this.$route.params.no}`).then(({ data }) => {
      this.article = data;
    });
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
  },
  methods: {
    moveModifyArticle() {
      this.$router.replace({
        name: "noticeModify",
        params: { articleno: this.article.articleno },
      });
      //   this.$router.push({ path: `/board/modify/${this.article.articleno}` });
    },
    deleteArticle() {
      if (confirm("정말로 삭제?")) {
        this.$router.replace({
          name: "noticeDelete",
          params: { no: this.article.articleno },
        });
      }
    },
    moveList() {
      this.$router.push({ name: "boardlist" });
    },
  },
};
</script>

<style></style>
