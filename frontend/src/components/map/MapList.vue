<template>
	<div>
		<v-container fluid>
			<v-layout>
				<v-slide-y-transition mode="out-in" v-if="showFilter">
					<v-slide-x-transition mode="out-in">
						<filter-apt
							:showDataInfo="showDataInfo"
							:showRoadInfo="showRoadInfo"
							:moneyRange="moneyRange"
							v-on:toggleFilter="toggleFilter"
							v-on:PassShowDataInfo="PassShowDataInfo"
							v-on:PassShowRoadInfo="PassShowRoadInfo"
							v-on:PassMoneyRange="PassMoneyRange"
						></filter-apt>
					</v-slide-x-transition>
				</v-slide-y-transition>
				<v-divider class="mx-3"></v-divider>

				<v-flex xs12 sm12 md12>
					<v-card class="elevation-25">
						<!-- 주소 검색 컴포넌트 -->
						<select-address
							v-on:PassDongcode="PassDongcode"
							v-on:PassSearch="PassSearch"
							v-on:PassCloseStr="PassCloseStr"
							v-on:PassSido="PassSido"
							v-on:toggleFilter="toggleFilter"
						></select-address>

						<!-- 지도 컴포넌트 -->
						<view-map
							:dongcode="dongcode"
							:search="search"
							:closeStr="closeStr"
							:sido="sido"
							:receivedSelected="selected"
							:showDataInfo="showDataInfo"
							:showRoadInfo="showRoadInfo"
							:moneyRange="moneyRange"
							v-on:PassSelectedData="PassSelectedData"
						></view-map>
					</v-card>
				</v-flex>
				<v-divider class="mx-3"></v-divider>

				<!-- 매물 상세 컴포넌트 -->
				<v-slide-y-transition mode="out-in" v-if="selected.aptCode">
					<v-slide-x-transition mode="out-in">
						<view-apt-detail :selected="selected"></view-apt-detail>
					</v-slide-x-transition>
				</v-slide-y-transition>
			</v-layout>

			<!-- 매물 리스트 컴포넌트 -->
			<v-slide-y-transition mode="out-in" v-if="dongcode">
				<view-apt-list
					:dongcode="dongcode"
					:moneyRange="moneyRange"
					v-on:PassSelectedData="PassSelectedData"
				></view-apt-list>
					<!-- :receivedSelected="selected" -->
			</v-slide-y-transition>
		</v-container>
	</div>
</template>

<script>
import ViewMap from "@/components/map/item/ViewMap";
import SelectAddress from "@/components/map/item/SelectAddress";
import ViewAptDetail from "@/components/map/item/ViewAptDetail";
import ViewAptList from "@/components/map/item/ViewAptList";
import FilterApt from "@/components/map/item/FilterApt";

export default {
  name: "MapList",
  data() {
    return {
      dongcode: String,
      search: String,
      closeStr: String,
      selected: Object,
      sido: Number,
      showFilter: false,
      showDataInfo: true,
      showRoadInfo: false,
      moneyRange: [1, 1000],
    };
  },
  components: {
    ViewMap,
    SelectAddress,
    ViewAptDetail,
    ViewAptList,
    FilterApt,
  },
  methods: {
    viewAlert(str) {
      alert(str);
    },
    PassDongcode(dongcode) {
      this.dongcode = dongcode;
    },
    PassSearch(search) {
      this.search = search;
    },
    PassCloseStr(str) {
      this.closeStr = str;
    },
    PassSelectedData(selected) {
      this.selected = selected;
    },
    PassSido(idx) {
      this.sido = idx;
    },
    toggleFilter() {
      this.showFilter = !this.showFilter;
    },
    PassShowDataInfo() {
      this.showDataInfo = !this.showDataInfo;
    },
    PassShowRoadInfo() {
      this.showRoadInfo = !this.showRoadInfo;
    },
    PassMoneyRange(moneyRange) {
      this.moneyRange = moneyRange;
    },
  },
};
</script>

<style></style>
