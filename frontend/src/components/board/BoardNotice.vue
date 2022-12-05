<template>
	<v-container>
		<v-card>
			<v-toolbar style="z-index: 100">
				<v-toolbar-title style="font-weight: bold">공지사항</v-toolbar-title>
				<v-spacer></v-spacer>
				<v-btn icon to="/"><v-icon>mdi-close</v-icon></v-btn>
			</v-toolbar>
			<router-view></router-view>
		</v-card>
	</v-container>
</template>

<script>
export default {
	name: "BoardNotice",
};
</script>

<style></style>
