<template>
	<v-card>
		<!--  :loading="loading" -->
		<v-row dense>
			<v-col v-for="(likeApt, i) in likeAptList" :key="likeApt" cols="3">
				<v-card class="mt-5 ma-2">
					<v-toolbar color="primary">
						<v-card-title class="pa-1">
							{{ likeApt.aptName }}
						</v-card-title>
						<v-spacer></v-spacer>
						<v-btn @click="likeAptDelete(likeApt.no, i)" icon><v-icon>mdi-close</v-icon></v-btn>
					</v-toolbar>
					<v-card-title class="pb-1">
						<div>
							<div style="white-space: nowrap; font-size: 15px; width: auto">
								{{ likeApt.sidoName }} {{ likeApt.gugunName }} {{ likeApt.dongName }}
							</div>
							<b> {{ likeApt.dealAmount | prettyMoney }} </b>
						</div>
					</v-card-title>
					<v-card-text class="pa-1 ml-3">
						<v-icon>mdi-home-clock</v-icon>
						Build.{{ likeApt.buildyear }}
					</v-card-text>

					<v-card-text class="pa-1 ml-3">
						<v-icon>mdi-stairs</v-icon>
						{{ likeApt.floor }}층
					</v-card-text>

					<v-card-text class="pa-1 ml-3">
						<v-icon>mdi-texture-box</v-icon>
						{{ likeApt.area }}m²
					</v-card-text>
				</v-card>
			</v-col>
		</v-row>
	</v-card>
</template>

<script>
import http from "@/util/http";
import { mapState } from "vuex";

const memberStore = "memberStore";
export default {
	name: "MainLike",
	data() {
		return {
			likeAptList: null,
		};
	},
	created() {
		http.post(`/like/list`, this.userInfo.userid).then(({ data }) => {
			this.likeAptList = data;
		});
	},

	computed: {
		...mapState(memberStore, ["isLogin", "userInfo"]),
	},
	methods: {
		likeAptDelete(aptNo, i) {
			http.delete(`/like`, {
				data: {
					no: aptNo,
					userid: this.userInfo.userid,
				},
			});
			this.likeAptList.splice(i, 1);
		},
	},
};
</script>

<style></style>
