<template>
  <v-flex xs12 sm3 md3 style="width: auto; max-width: 400px">
    <v-card class="elevation-25" max-width="280px" style="height:560px;">
      <v-card-text>
        <v-list>
          <v-card-actions>
            <!-- <v-btn text @click="menu = false"> Cancel </v-btn> -->
            <v-icon class="ml-3" @click="toggleFilter">mdi-close</v-icon>
          </v-card-actions>
        <v-spacer></v-spacer>

          <v-list-item>
            매물 정보 표시&nbsp;&nbsp;
            <v-switch
              class="ml-4"
              v-model="isShowDataInfo"
              @change="toggleShowDataInfo"
              color="warning"
              inset
            ></v-switch>
          </v-list-item>

          <v-list-item>
            교통 정보 표시&nbsp;&nbsp;
            <v-switch
              class="ml-4"
              v-model="isShowRoadInfo"
              @change="toggleShowRoadInfo"
              color="warning"
              inset
            ></v-switch>
          </v-list-item>

          <v-list-item class="my-4">
            <v-col>
              <v-row> 매물 금액 범위 </v-row>
              <v-row>
                <v-list-item-action min-width="100%">
                  <v-card flat color="transparent">
                    <v-card-text>
                      <v-row style="width: 200px">
                        <v-range-slider
                          v-model="limitMoneyRange"
                          max="1000"
                          min="1"
                          hide-details
                          thumb-label
                          class="align-center"
                          @change="PassMoneyRange"
                        >
                        </v-range-slider>
                        </v-row>
                      <v-row>
                        <p class="my-1">{{ limitMoneyRange[0] >= 10 ? parseInt(moneyRange[0]/10)+"억"+(moneyRange[0]%10 == 0 ? "" : limitMoneyRange[0]%10+"천만원") : limitMoneyRange[0]+"천만원" }}</p>
                        <v-spacer></v-spacer>

                        <p class="my-1">{{ limitMoneyRange[1] >= 10 ? parseInt(moneyRange[1]/10)+"억"+(moneyRange[1]%10 == 0 ? "" : limitMoneyRange[1]%10+"천만원") : limitMoneyRange[1]+"천만원" }}</p>
                      
                      </v-row>
                    </v-card-text>
                  </v-card>
                </v-list-item-action>
              </v-row>
            </v-col>
          </v-list-item>
        </v-list>
      </v-card-text>
    </v-card>
  </v-flex>
</template>

<script>
export default {
  name: "FilterApt",
  props: ["showDataInfo", "showRoadInfo", "moneyRange"],
  data() {
    return {
      message: false,
      isShowDataInfo: this.showDataInfo,
      isShowRoadInfo: this.showRoadInfo,
      limitMoneyRange: this.moneyRange,
    };
  },
  mounted() {},
  watch: {
    showDataInfo() {
      this.isShowDataInfo = this.showDataInfo;
    },
    showRoadInfo() {
      this.isShowRoadInfo = this.showRoadInfo;
    },
  },
  methods: {
    toggleFilter() {
      this.$emit("toggleFilter");
    },
    toggleShowDataInfo() {
      this.$emit("PassShowDataInfo");
    },
    toggleShowRoadInfo() {
      this.$emit("PassShowRoadInfo");
    },
    PassMoneyRange() {
      this.$emit("PassMoneyRange", this.limitMoneyRange);
    },
  },
};
</script>

<style scoped>
@import "@/sass/map.css";
</style>
