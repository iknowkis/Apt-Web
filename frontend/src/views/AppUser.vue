<template>
	<v-container style="width: 60%" class="mt-5">
		<v-slide-x-transition mode="out-in">
			<router-view></router-view>
		</v-slide-x-transition>
	</v-container>
</template>

<script>
export default {
	name: "AppUser",
};
</script>

<style></style>
