<template>
	<v-container fluid>
		<v-layout align-center justify-center>
			<v-flex xs12 sm12 md12 style="max-width:1690px">
				<v-card class="elevation-25">
					<v-toolbar>
						<v-text-field
							prepend-icon="mdi-home-search-outline"
							v-model="search"
							label="검색어를 입력하세요"
							single-line
							hide-details
							clearable
						></v-text-field>
						<v-spacer></v-spacer>
						<v-dialog v-model="dialog" max-width="500px">
							<v-card>
								<v-card-title>
									<span class="headline">{{ formTitle }}</span>
								</v-card-title>

								<v-card-actions>
									<v-spacer></v-spacer>
									<v-btn color="blue darken-1" flat @click="close">Cancel</v-btn>
									<v-btn color="blue darken-1" flat @click="save">Save</v-btn>
								</v-card-actions>
							</v-card>
						</v-dialog>
					</v-toolbar>
					<v-data-table
						:headers="headers"
						:items="articles"
						:search="search"
						class="elevation-1"
						@click:row="viewDetail"
					>
						<template v-slot:items="props">
							<td>{{ props.item.hit }}</td>
							<td class="text-xs-right">{{ props.item.userid }}</td>
							<td class="justify-center layout px-0">
								<v-icon small class="mr-2" @click="editItem(props.item)"> edit </v-icon>
								<v-icon small @click="deleteItem(props.item)"> delete </v-icon>
							</td>
						</template>
						<template v-slot:no-data>
							<h4>-</h4>
							<!-- <v-btn color="primary" @click="initialize">Reset</v-btn> -->
						</template>
					</v-data-table>
				</v-card>
			</v-flex>
		</v-layout>
	</v-container>
</template>

<script>
export default {
  name: "TableList",
  data() {
    return {
      search: "",
      headers: [
        {
          text: "No",
          align: "center",
          value: "articleno",
        },
        {
          text: "제목",
          align: "left",
          value: "subject",
        },
        {
          text: "작성자",
          value: "userid",
        },
        {
          text: "조회수",
          value: "hit",
        },
      ],
      articles: [],
    };
  },
  mounted() {},
  methods: {},
};
</script>

<style></style>
