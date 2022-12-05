<template>
<div>
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
      @click:prepend="searchPlaces"
    ></v-text-field>
  </v-toolbar>
  <div>
      <v-chip class="ma-2" close color="#036635" text-color="white"
      v-if="searchObj.starbucks"
      @click:close="closeChip('스타벅스')">
        <v-avatar left>
          <v-icon>mdi-coffee-outline</v-icon>
        </v-avatar>
        스타벅스
      </v-chip>
      <v-chip class="ma-2" close color="red" text-color="white"
      v-if="searchObj.school"
      @click:close="closeChip('학교')">
        <v-avatar left>
          <v-icon>mdi-school-outline</v-icon>
        </v-avatar>
        학교
      </v-chip>
      <v-chip class="ma-2" close color="green" text-color="white"
      v-if="searchObj.cafe"
      @click:close="closeChip('카페')">
        <v-avatar left>
          <v-icon>mdi-cup-water </v-icon>
        </v-avatar>
        카페
      </v-chip>
      <v-chip class="ma-2" close color="blue" text-color="white"
      v-if="searchObj.store"
      @click:close="closeChip('편의점')">
        <v-avatar left>
          <v-icon>mdi-cart</v-icon>
        </v-avatar>
        편의점
      </v-chip>
      
      <div class="ma-2" color="#FF9800" text-color="white"
        style="display: inline-block"
        v-for="item, i in searchObj.etc"
        :key="i"
        close
      >
        <v-chip class="ma-2" close color="#FF9800" text-color="white"
        v-if="item"
        @click:close="closeChip(item)">
          {{item}}
        </v-chip>
      </div>
  </div>
</div> 
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
        store: false,
        etc: [],
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
      if (!this.search.replace(/^\s+|\s+$/g, "")) {
        alert("키워드를 입력해주세요!");
        return;
      }

      if (
        this.search.replace("스타벅스", "").length ==
        this.search.length - 4
      ) {
        this.searchObj.starbucks = true;
      } else if (
        this.search.replace("스벅", "").length ==
        this.search.length - 2
      ) {
        this.searchObj.starbucks = true;
      } else if (
        this.search.replace("카페", "").length ==
        this.search.length - 2
      ) {
        this.searchObj.cafe = true;
      } else if (
        this.search.replace("편의점", "").length ==
        this.search.length - 3
      ) {
        this.searchObj.store = true;
      } else if (
        this.search.replace("학교", "").length ==
        this.search.length - 2
      ) {
        this.searchObj.school = true;
      } else this.searchObj.etc.push(this.search);

      this.$emit("PassSearch", this.search);
      this.search = "";
    },
    closeChip(str) {
      if (str == "스타벅스") {
        this.searchObj.starbucks = false;
      } else if (str == "카페") {
        this.searchObj.cafe = false;
      } else if (str == "편의점") {
        this.searchObj.store = false;
      } else if (str == "학교") {
        this.searchObj.school = false;
      } else {
        for (let i = 0; i < this.searchObj.etc.length; i++) {
          if (this.searchObj.etc[i] == str) {
            this.searchObj.etc.splice(i, 1);
          }
        }
      }
      this.$emit("PassCloseStr", str);
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
