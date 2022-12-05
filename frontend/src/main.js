import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import vuetify from "./plugins/vuetify";

Vue.config.productionTip = false;

Vue.filter("prettyMoney", function (value) {
	return parseInt(value.replace(",", "")) >= 10000
		? parseInt(parseInt(value.replace(",", "")) / 10000) +
				"억 " +
				(parseInt(value.replace(",", "")) % 10000 == 0
					? ""
					: (parseInt(value.replace(",", "")) % 10000) + "만원")
		: parseInt(value.replace(",", "")) + "만원";
});

new Vue({
	router,
	store,
	vuetify,
	render: (h) => h(App),
}).$mount("#app");
