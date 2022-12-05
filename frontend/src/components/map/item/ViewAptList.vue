<template>
	<div class="mx-auto" style="box-shadow: none" max-width="1230">
		<v-slide-group v-model="selected" center-active show-arrows>
          <v-row justify="center" align="center" class="my-1">
			<v-slide-item v-for="(data, n) in dataList" :key="n" v-slot="{ active, toggle }">
				<v-card
					:color="active ? '#6CB7FF' : 'white'"
					class="ma-4 text-center fill-height"
					:id="data.aptCode"
					style="flex-basis: content; height: auto; padding: 25px"
					align="center"
					justify="center"
					@click="toggle"
				>
					<v-scale-transition>
						<div>
							<div style="font-size: 14px">
								{{ data.aptName }}
							</div>
							<div>
								<b>
									{{data.recentPrice | prettyMoney}}
								</b>
							</div>
						</div>
					</v-scale-transition>
				</v-card>
			</v-slide-item>
          </v-row>
		</v-slide-group>
	</div>
</template>

<script>
import http from "@/util/http";

export default {
  name: "ViewAptList",
  props: ["dongcode", "receivedSelected", "moneyRange"],
  data() {
    return {
      dataList: [],
      selected: null,
      limitMoneyRange: [],
    };
  },
  watch: {
    dongcode() {
      this.makeList(this.dongcode);
    },
    selected() {
      this.$emit("PassSelectedData", this.dataList[this.selected]);
    },
    receivedSelected() {
      console.log("receivedSelected: " + this.receivedSelected.aptName);
      let card = document.getElementById(this.receivedSelected.aptCode);
      card.setAttribute(
        "style",
        "flex-basis: content; height: auto; padding: 25px; background-color: rgb(108, 183, 255) !important; border-color: rgb(108, 183, 255) !important;"
      );
      // for (let el of cardList) {
      //   if (el.aptCode == this.receivedSelected.aptCode) {
      //     console.log("AAAA:" + el.innerHTML);
      //   }
      // }
    },
    moneyRange() {
      this.limitMoneyRange = this.moneyRange;
    },
  },
  methods: {
    makeList(dongcode) {
      this.dataList = [];
      http("/address/apt?dong=" + dongcode).then(({ data }) => {
        for (let i = 0; i < data.length; i++) {
          let min = this.limitMoneyRange[0];
          let max = this.limitMoneyRange[1];
          let dealMoney = parseInt(
            parseInt(data[i].recentPrice.replace(",", "")) / 1000
          );
          if (dealMoney < min || dealMoney > max) continue;
          this.dataList.push(data[i]);
        }
      });
    },
  },
};
</script>

<style>
</style>
