<template>
  <v-card>
    <v-toolbar elevation="0" color="white">
      <v-text-field
        class="mr-5"
        v-model="search"
        label="공지사항을 검색해보세요"
        single-line
        hide-details
        clearable
      ></v-text-field>
      <v-divider v-if="userInfo && userInfo.userid === 'admin'" inset vertical></v-divider>
      <v-btn v-if="userInfo && userInfo.userid === 'admin'" class="ml-2" text @click="writeItem"
        >새 공지사항 등록</v-btn
      >
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
    <v-divider></v-divider>
    <v-data-table :headers="headers" :items="articles" :search="search" @click:row="viewDetail">
      <template v-slot:items="props">
        <td>{{ props.item.hit }}</td>
        <td class="text-xs-right">{{ props.item.userid }}</td>
        <td class="justify-center layout px-0">
          <v-icon small class="mr-2" @click="editItem(props.item)"> edit </v-icon>
          <v-icon small @click="deleteItem(props.item)"> delete </v-icon>
        </td>
      </template>
      <template v-slot:no-data>
        <h4>게시판이 비어있습니다.</h4>
        <!-- <v-btn color="primary" @click="initialize">Reset</v-btn> -->
      </template>
    </v-data-table>
  </v-card>
</template>

<script>
import http from "@/util/http";
import { mapState } from "vuex";

const memberStore = "memberStore";
export default {
  name: "BoardNoticeList",
  data: () => ({
    dialog: false,
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
        text: "조회수",
        value: "hit",
      },
    ],
    articles: [],
    editedIndex: -1,
    editedItem: {
      subject: "",
      articleno: 0,
      hit: 0,
      userid: "",
    },
    defaultItem: {
      subject: "",
      articleno: 0,
      hit: 0,
      userid: "",
    },
  }),

  computed: {
    formTitle() {
      return this.editedIndex === -1 ? "New Item" : "Edit Item";
    },
    ...mapState(memberStore, ["isLogin", "userInfo"]),
  },

  watch: {
    dialog(val) {
      val || this.close();
    },
  },

  created() {
    this.initialize();
  },

  methods: {
    initialize() {
      http.get(`/notice`).then(({ data }) => {
        this.articles = data;
        console.log(data);
      });
    },
    writeItem() {
      this.$router.push({ name: "noticeWrite" });
    },
    editItem(item) {
      this.editedIndex = this.articles.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },

    deleteItem(item) {
      const index = this.articles.indexOf(item);
      confirm("Are you sure you want to delete this item?") && this.articles.splice(index, 1);
    },

    close() {
      this.dialog = false;
      setTimeout(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      }, 300);
    },

    save() {
      if (this.editedIndex > -1) {
        Object.assign(this.articles[this.editedIndex], this.editedItem);
      } else {
        this.articles.push(this.editedItem);
      }
      this.close();
    },

    viewDetail(event) {
      this.$router.push({ path: "" + event.articleno });
    },
  },
};
</script>

<style scoped>
.v-text-field label {
  font-size: 12px;
}
.v-data-table-header > tr {
  background-color: #29307d;
}
</style>
