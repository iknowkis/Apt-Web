<template>
	<v-container>
		<v-btn class="hover-underline-animation" dark :to="{ name: 'maplist' }"> KakaoMap </v-btn>
		<v-btn class="hover-underline-animation" dark :to="{ name: 'tablelist' }"> Table </v-btn>

		<v-slide-x-transition mode="out-in">
			<router-view></router-view>
		</v-slide-x-transition>
	</v-container>
</template>

<script>
export default {
	name: "AppMap",
};
</script>

<style scoped>
@import "@/sass/map.css";

.hover-underline-animation::after {
	background-color: #ffffff;
}
</style>
