<template>
	<v-container style="width: 85%" class="mt-5">
		<v-card elevation="25">
			<v-toolbar style="z-index: 100" elevation="5" color="white">
				<v-tabs centered color="primary">
					<v-tab class="tab-font" @click="changeMain(0)"> 실시간 뉴스 </v-tab>
					<v-tab class="tab-font" @click="changeMain(1)"> 인기 매물 </v-tab>
					<v-tab class="tab-font" v-if="userInfo && userInfo != 'admin'" @click="changeMain(2)">
						관심 매물
					</v-tab>
				</v-tabs>
			</v-toolbar>
			<v-slide-y-transition mode="out-in">
				<router-view></router-view>
			</v-slide-y-transition>
		</v-card>
	</v-container>
</template>

<script>
import { mapState } from "vuex";

const memberStore = "memberStore";
export default {
	name: "AppMain",
	data() {
		return {
			pageNames: ["news", "popular", "like"],
		};
	},
	methods: {
		changeMain(i) {
			this.$router.push({ name: this.pageNames[i] });
		},
	},
	computed: {
		...mapState(memberStore, ["isLogin", "userInfo"]),
	},
};
</script>

<style scoped>
.tab-font {
	font-size: 18px;
}
.v-tab--active {
	font-weight: bold;
}
</style>
