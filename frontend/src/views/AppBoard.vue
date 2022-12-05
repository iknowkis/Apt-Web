<template>
	<v-container style="width: 70%" class="mt-5">
		<v-slide-x-transition mode="out-in">
			<router-view></router-view>
		</v-slide-x-transition>
	</v-container>
</template>

<script>
export default {
	name: "AppBoard",
};
</script>

<style></style>
