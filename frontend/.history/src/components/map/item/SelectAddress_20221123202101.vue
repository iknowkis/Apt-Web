<template>
	<v-toolbar>
		<v-btn class="hover-underline-animation" @click="toggleFilter">
			<v-icon>mdi-filter-variant </v-icon>
			<h5>필터 옵션</h5>
		</v-btn>

		<v-divider class="ml-3 mr-3" inset vertical></v-divider>

		<v-select
      class="selectAddress mx-7"
			@change="getGungunList"
			:items="sidoList"
			item-value="sidoCode"
			item-text="sidoName"
			v-model="sido"
			label="시도 선택"
		>
		</v-select>

		<v-select
      class="selectAddress mx-7"
			@change="getDongList"
			:items="gugunList"
			item-value="gugunCode"
			item-text="gugunName"
			label="구군 선택"
			>></v-select
		>

		<v-select
      class="selectAddress mx-7"
			@change="getDongCode"
			:items="dongList"
			item-value="dongCode"
			item-text="dongName"
			label="동 선택"
		></v-select>
    <v-chip
      class="ma-2"
      close
      color="#036635"
      text-color="white"
    >
      <v-avatar left>
        <v-icon>mdi-coffee-outline</v-icon>
      </v-avatar>
      스타벅스
    </v-chip>
    <v-chip
      class="ma-2"
      close
      color="red"
      text-color="white"
    >
      학교
    </v-chip>
    <v-chip
      class="ma-2"
      close
      color="blue"
      text-color="white"
    >
      카페
    </v-chip>
    <v-chip
      class="ma-2"
      close
      color="green"
      text-color="white"
    >
      <!-- @click:close="chip2 = false" -->
      병원
    </v-chip>
		<v-spacer></v-spacer>

    <v-text-field
      class="searchField"
      v-model="search"
      v-on:keyup.enter="searchPlaces"
      prepend-icon="mdi-home-search-outline "
      label="지역, 아파트, 지하철 검색"
      hide-details
      single-line
      clearable
    ></v-text-field>

    <v-icon @click="alert('전체화면 지도 기능 넣으려고')">mdi-fullscreen</v-icon>
	</v-toolbar>
</template>

<script>
import http from "@/util/http";

export default {
  name: "SelectAddress",
  components: {},
  data() {
    return {
      sendObj: "",
      sidoList: [],
      gugunList: [],
      dongList: [],
      sido: "",
      search: "",
    };
  },
  created() {
    this.getSidoList();
    this.getGungunList("11");
  },
  methods: {
    alert(resp) {
      alert(resp);
    },
    toggleFilter() {
      this.$emit("toggleFilter");
    },
    getSidoList() {
      http("/address/sido")
        .then(({ data }) => {
          this.sidoList = [];
          for (let i = 0; i < data.length; i++) {
            this.sidoList.push(data[i]);
          }
        })
        .then(() => (this.sido = this.sidoList[0]));
    },
    getGungunList(sido) {
      http("/address/gugun?sido=" + sido).then(({ data }) => {
        this.gugunList = [];
        for (var i = 0; i < data.length; i++) {
          this.gugunList.push(data[i]);
        }
      });

      if (sido == "11") {
        this.$emit("polySeoulGugun", -1);
      } else {
        this.$emit("polySeoulGugun", 0);
      }
    },
    getDongList(gugun) {
      http("/address/dong?gugun=" + gugun).then(({ data }) => {
        this.dongList = [];
        for (var i = 0; i < data.length; i++) {
          this.dongList.push(data[i]);
        }
      });
    },
    getDongCode(dong) {
      this.$emit("PassDongcode", dong);
    },
    searchPlaces() {
      this.$emit("PassSearch", this.search);
    },
  },
};
</script>

<style scoped>
@import "@/sass/map.css";

.selectAddress {
  max-width: 150px;
}
.searchField {
  max-width: 15%;
  float: right;
}
.v-text-field label {
  font-size: 12px;
}

.v-select {
  width: 60px;
  height: 35px;
}
.v-select >>> .v-text-field__details {
  display: none !important;
}
</style>
