<template>
	<v-card>
		<v-toolbar elevation="0" color="white">
			<v-text-field
				class="mr-5"
				v-model="search"
				label="질문을 검색해보세요"
				single-line
				hide-details
				clearable
			></v-text-field>
			<v-divider inset vertical></v-divider>
			<v-btn class="ml-2" text @click="writeItem">새 질문 등록</v-btn>
		</v-toolbar>
		<v-divider></v-divider>
		<v-data-table :headers="headers" :items="articles" :search="search" @click:row="viewDetail">
			<template v-slot:no-data>
				<h4>게시판이 비어있습니다.</h4>
			</template>
		</v-data-table>
	</v-card>
</template>

<script>
import http from "@/util/http";

export default {
	name: "BoardQnaList",
	data: () => ({
		dialog: false,
		search: "",
		headers: [
			{
				text: "No",
				align: "center",
				value: "articleno",
			},
			{
				text: "제목",
				align: "left",
				value: "subject",
			},
			{
				text: "작성자",
				value: "userid",
			},
			{
				text: "조회수",
				value: "hit",
			},
		],
		articles: [],
		editedIndex: -1,
		editedItem: {
			subject: "",
			articleno: 0,
			hit: 0,
			userid: "",
		},
		defaultItem: {
			subject: "",
			articleno: 0,
			hit: 0,
			userid: "",
		},
	}),

	computed: {
		formTitle() {
			return this.editedIndex === -1 ? "New Item" : "Edit Item";
		},
	},

	watch: {
		dialog(val) {
			val || this.close();
		},
	},

	created() {
		this.initialize();
	},

	methods: {
		initialize() {
			http.get(`/qna`).then(({ data }) => {
				this.articles = data;
			});
		},
		writeItem() {
			this.$router.push({ name: "qnaWrite" });
		},

		viewDetail(event) {
			this.$router.push({ path: "" + event.articleno });
		},
	},
};
</script>

<style scoped>
.v-text-field label {
	font-size: 12px;
}
.v-data-table-header > tr {
	background-color: #29307d;
}
</style>
