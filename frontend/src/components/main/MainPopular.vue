<template>
	<v-card>
		<v-toolbar dark>
			<v-spacer></v-spacer>
			<v-card-title style="text-align: center">인기 매물 TOP 4</v-card-title>
			<v-spacer></v-spacer>
		</v-toolbar>
		<!--  :loading="loading" -->
		<v-row dense>
			<v-col v-for="popularApt in popularAptList" :key="popularApt" cols="3">
				<v-card class="ma-2">
					<!-- 로드뷰 -->
					<v-img height="200px" src="@/assets/apt.jpg"></v-img>
					<!-- 상세 정보 -->
					<v-card-title class="pb-1">
						<div>
							<div style="white-space: nowrap; font-size: 15px; width: auto">
								{{ popularApt.aptName }} 아파트<br />
							</div>
							<b>{{ popularApt.recentPrice | prettyMoney }}</b>
						</div>
					</v-card-title>
					<v-card-text class="pa-1 ml-3 mb-2"> 조회수 {{ popularApt.hit }}회 </v-card-text>
					<v-card-text class="pa-1 ml-3">
						<v-icon>mdi-home-clock</v-icon>
						Build.{{ popularApt.buildYear }}
					</v-card-text>
					<v-card-text class="pa-1 ml-3">
						<v-icon>mdi-calendar-range</v-icon>
						Deal.{{ popularApt.dealYear }}.{{ popularApt.dealMonth }}.{{ popularApt.dealDay }}
					</v-card-text>
					<v-card-text class="pa-1 ml-3">
						<v-icon>mdi-stairs</v-icon>
						{{ popularApt.floor }}층
					</v-card-text>
					<v-card-text class="pa-1 ml-3">
						<v-icon>mdi-home-outline</v-icon>
						Zip code.{{ popularApt.jibun }}
					</v-card-text>
					<v-card-text class="pa-1 ml-3">
						<v-icon>mdi-texture-box</v-icon>
						{{ popularApt.area }}m²
					</v-card-text>
				</v-card>
			</v-col>
		</v-row>
	</v-card>
</template>

<script>
import http from "@/util/http";

export default {
	name: "MainPopular",
	data() {
		return {
			popularAptList: null,
			roadviewList: ["roadview1", "roadview2", "roadview3", "roadview4"],
		};
	},
	mounted() {
		if (window.kakao && window.kakao.maps) {
			this.viewRoadmap();
		} else {
			const script = document.createElement("script");
			/* global kakao */
			script.onload = () => kakao.maps.load(this.initMap);

			script.src =
				"//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=956eacd3c2cb4eccc009f58ff2bd3933";
			document.head.appendChild(script);
		}
	},
	created() {
		http.get(`/like/popular`).then(({ data }) => {
			this.popularAptList = data;
		});
	},
	methods: {
		viewRoadmap() {
			let roadviewContainer = document.getElementById(this.roadviewList[0]); //로드뷰를 표시할 div
			let roadview = new kakao.maps.Roadview(roadviewContainer); //로드뷰 객체
			let roadviewClient = new kakao.maps.RoadviewClient(); //좌표로부터 로드뷰 파노ID를 가져올 로드뷰 helper객체

			let position = new kakao.maps.LatLng(this.popularAptList[0].lat, this.popularAptList[0].lng);

			// 특정 위치의 좌표와 가까운 로드뷰의 panoId를 추출하여 로드뷰를 띄운다.
			roadviewClient.getNearestPanoId(position, 50, function (panoId) {
				roadview.setPanoId(panoId, position); //panoId와 중심좌표를 통해 로드뷰 실행
			});
		},
	},
};
</script>

<style></style>
