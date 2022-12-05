<template>
	<v-card>
		<v-tabs vertical elevation="5" color="info" fixed-tabs v-model="tabmodel">
			<v-tab v-for="(k, i) in keywords" :key="k" :href="`#tab-${i}`" @click="btnClick(k, i)"
				>{{ k }}
			</v-tab>
			<v-tabs-items v-model="tabmodel">
				<v-tab-item v-for="(k, i) in keywords" :key="k" :value="`tab-${i}`">
					<v-card
						flat
						v-for="article in articles"
						:key="article"
						:href="`${article.link}`"
						target="blank_"
					>
						<v-card-title v-html="article.title"></v-card-title>
						<v-card-text style="text-align: left" v-html="article.description"></v-card-text>
					</v-card>
				</v-tab-item>
			</v-tabs-items>
		</v-tabs>
	</v-card>
</template>

<script>
import http from "@/util/http";
export default {
	name: "MainNews",
	data() {
		return {
			tabmodel: "tab-2",
			keyword: "정치",
			articles: [],
			keywords: ["정치", "사회", "경제", "스포츠", "싸피", "문화", "예술"],
		};
	},
	created() {
		http.post("/news", `${this.keyword}`).then(({ data }) => (this.articles = data.items));
	},
	methods: {
		btnClick(k) {
			this.keyword = k;
			http.post("/news", `${this.keyword}`).then(({ data }) => (this.articles = data.items));
		},
	},
};
</script>

<style scoped>
.v-tab {
	font-size: 16px;
}
.v-tab--active {
	font-weight: bold;
}
</style>
