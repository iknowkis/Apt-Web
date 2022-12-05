<template>
	<v-card>
		<v-toolbar elevation="0" color="white">
			<v-toolbar-title>공지사항 작성</v-toolbar-title>
			<v-spacer></v-spacer>
			<v-btn icon to="list"><v-icon>mdi-arrow-left</v-icon></v-btn>
		</v-toolbar>
		<v-divider></v-divider>
		<v-card-text>
			<v-form ref="form" v-model="valid">
				<v-text-field
					color="info"
					id="subject"
					v-model="article.subject"
					:rules="subRule"
					hide-details="auto"
					name="subject"
					label="제목"
					type="text"
					clearable
				></v-text-field>
				<v-textarea
					id="content"
					v-model="article.content"
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

export default {
	name: "BoardNoticeWrite",
	data() {
		return {
			valid: true,
			snackbar: false,
			msg: "",
			timeout: 3000,
			article: {
				articleno: 0,
				userid: "admin",
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
	created() {},
	methods: {
		validate() {
			this.$refs.form.validate();
		},
		registArticle() {
			let _this = this;
			http
				.post(`/notice`, {
					subject: _this.article.subject,
					content: _this.article.content,
				})
				.then(({ data }) => {
					_this.msg = "글 작성 실패, 서버 에러가 발생하였습니다.";
					if (data === "success") {
						_this.msg = "글 작성이 완료되었습니다.";
					}
					_this.snackbar = true;
					_this.moveList();
				});
		},
		moveList() {
			this.$router.push({ name: "noticeList" });
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
