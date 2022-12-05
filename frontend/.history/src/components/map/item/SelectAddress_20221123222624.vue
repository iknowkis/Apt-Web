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
    <v-chip class="ma-2" close color="#036635" text-color="white" v-if="searchObj.starbucks">
      <v-avatar left>
        <v-icon>mdi-coffee-outline</v-icon>
      </v-avatar>
      스타벅스
    </v-chip>
    <v-chip class="ma-2" close color="red" text-color="white" v-if="searchObj.shcool">
      <v-avatar left>
        <v-icon>mdi-school-outline</v-icon>
      </v-avatar>
      학교
    </v-chip>
    <v-chip class="ma-2" close color="green" text-color="white" v-if="searchObj.cafe">
      <v-avatar left>
        <v-icon>mdi-cup-water </v-icon>
      </v-avatar>
      카페
    </v-chip>
    <v-chip class="ma-2" close color="blue" text-color="white" v-if="searchObj.store">
      <v-avatar left>
        <v-icon>mdi-cart</v-icon>
      </v-avatar>
      편의점
    </v-chip>
    
  <v-chip class="ma-2" color="#FF9800" text-color="white"
    v-for="tag, i in searchObj.etc"
    :key="i"
    close
  >
    {{ tag }}
  </v-chip> 

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
      searchObj: {
        starbucks: false,
        school: false,
        cafe: false,
        store: true,
        etc: [
          "wd", "qwe"
        ],
      },
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
        this.$emit("PassSido", -1);
      } else {
        this.$emit("PassSido", 0);
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
  min-width: 120px;
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
