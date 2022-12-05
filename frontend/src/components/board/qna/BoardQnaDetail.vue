<template>
	<v-card class="elevation-0">
		<v-card class="ma-5" elevation="0" outlined>
			<v-card-text>
				<div class="mb-3" style="font-size: 20px">
					<v-card-title style="font-size: 30px">
						Q : {{ article.subject }}
						<v-spacer></v-spacer>
						질문자 : {{ article.userid }}
					</v-card-title>
				</div>
				<v-card-text>
					{{ article.content }}
				</v-card-text>
			</v-card-text>
		</v-card>
		<v-card class="ma-5" elevation="0" outlined>
			<v-card-text>
				<v-card-title v-if="article.answer" style="font-size: 20px; font-weight: bold">
					<v-icon class="mr-5">mdi-arrow-right-bottom-bold</v-icon>
					{{ article.answer }}
				</v-card-title>
				<v-row
					no-gutters
					v-else-if="!article.answer && userInfo.userid == 'admin'"
					class="pl-5 pr-5"
				>
					<v-col align-self="center" cols="1">
						<v-icon class="ml-5">mdi-arrow-right-bottom-bold</v-icon>
					</v-col>
					<v-col cols="9">
						<v-text-field v-model="articleAnswer" label="답변 입력"></v-text-field>
					</v-col>
					<v-col align-self="center" cols="2">
						<v-btn text class="ml-5" @click="enrollAnswer" outlined>답변 작성</v-btn>
					</v-col>
				</v-row>
				<v-card-title v-else style="font-size: 20px"> 답변을 기다리는 중입니다.. </v-card-title>
			</v-card-text>
		</v-card>
		<v-divider></v-divider>
		<v-card-actions>
			<v-btn text @click="moveList"> 뒤로 가기 </v-btn>
			<v-spacer></v-spacer>

			
		</v-card-actions>
	</v-card>
</template>

<script>
import http from "@/util/http";
import { mapState } from "vuex";

const memberStore = "memberStore";
export default {
  name: "BoardQnaDetail",
  data() {
    return {
      qnaDetailImgItems: [
        "profile1",
        "profile2",
        "profile3",
        "profile4",
        "profile5",
      ],
      article: {},
      articleAnswer: "",
    };
  },
  created() {
    http.get(`/qna/${this.$route.params.no}`).then(({ data }) => {
      this.article = data;
    });
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
  },
  methods: {
    moveModifyArticle() {
      this.$router.replace({
        name: "qnaModify",
        params: { articleno: this.article.articleno },
      });
    },
    deleteArticle() {
      if (confirm("정말로 삭제?")) {
        this.$router.replace({
          name: "qnaDelete",
          params: { no: this.article.articleno },
        });
      }
    },
    moveList() {
      this.$router.push({ name: "qnaList" });
    },
    enrollAnswer() {
      http
        .post(`/qna/answer`, {
          articleno: this.article.articleno,
          answer: this.articleAnswer,
        })
        .then(({ data }) => {
          if (data === "success") {
            alert("답변 등록 완료!");
          } else {
            alert("답변 등록 실패..");
          }
        });
    },
  },
};
</script>

<style></style>
