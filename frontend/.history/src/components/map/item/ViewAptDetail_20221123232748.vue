<template>
  <v-flex xs12 sm3 md3 style="width: auto; max-width: 400px">
    <v-card class="elevation-25">
      <v-toolbar>
        <v-btn class="hover-underline-animation">
          <h5>매물 상세</h5>
        </v-btn>
        <v-spacer></v-spacer>

        <v-btn @click="likeThisApt" class="hover-underline-animation">
          <v-icon>mdi-heart-outline</v-icon>
        </v-btn>
      </v-toolbar>

      <!--  :loading="loading" -->
      <v-card class="mx-auto my-1" max-width="374">
        <template slot="progress">
          <v-progress-linear color="deep-purple" height="10" indeterminate></v-progress-linear>
        </template>

        <!-- 로드뷰 -->
        <div id="roadview" style="width: 100%; height: 200px"></div>

        <!-- <v-img height="250" :src="imgList[selected.aptCode%10]" ></v-img> -->

        <!-- 상세 정보 -->
        <div style="height: 295px">
          <v-card-title>
            <div>
              <div style="white-space: nowrap; font-size: 15px; width: auto">{{ selected.aptName }} 아파트<br /></div>
              <b>{{ selected.recentPrice }}만원</b>
            </div>
          </v-card-title>
          <v-card-text>
            <v-row align="center" class="mx-0 mb-4">
              <v-rating :value="3.5" color="amber" dense half-increments readonly size="14"></v-rating>

              <div class="grey--text ms-4">3.5 (413)</div>
            </v-row>
            <div class="mb-1">
              <v-icon>mdi-home-clock</v-icon>
              <v-divider class="mx-3" inset vertical></v-divider>
              Build.{{ selected.buildYear }}
            </div>

            <div class="mb-1">
              <v-icon>mdi-calendar-range</v-icon>
              <v-divider class="mx-3" inset vertical></v-divider>
              Deal.{{ selected.dealYear }}.{{ selected.dealMonth }}.{{ selected.dealDay }}
            </div>

            <div class="mb-1">
              <v-icon>mdi-stairs</v-icon>
              <v-divider class="mx-3" inset vertical></v-divider>
              {{ selected.floor }}층
            </div>

            <div class="mb-1">
              <v-icon class="mx-auto my-1">mdi-home-outline</v-icon>
              <v-divider class="mx-3" inset vertical></v-divider>
              Zip code.{{ selected.jibun }}
            </div>

            <div class="mb-1">
              <v-icon class="mx-auto my-1">mdi-texture-box</v-icon>
              <v-divider class="mx-3" inset vertical></v-divider>
              {{ selected.area }}m²
            </div>
          </v-card-text>

          <!-- @click="reserve" -->
          <!-- <v-divider class="mx-4"></v-divider>
					<v-card-actions>
					<v-btn color="deep-purple lighten-2" text >
						Detail
						<v-icon>mdi-menu-down</v-icon>
					</v-btn>
					</v-card-actions> -->
        </div>
      </v-card>
    </v-card>
  </v-flex>
</template>

<script>
import http from "@/util/http";
import { mapState } from "vuex";

const memberStore = "memberStore";
export default {
  name: "ViewAptDetail",
  userid: "",
  props: ["selected"],
  data() {
    return {
      isLike: false,
      // imgList: [
      //   "https://images.pexels.com/photos/65438/pexels-photo-65438.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
      //   "https://images.unsplash.com/photo-1544442711-fa772d436708?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8Y2l0eSUyMGJ1aWxkaW5nJTIwNGslMjB3YWxscGFwZXJ8ZW58MHx8MHx8&w=1000&q=80",
      //   "https://i.pinimg.com/originals/70/6c/8d/706c8dbc342f114430c112657f0256f9.jpg",
      //   "https://media.vanityfair.com/photos/54cab13e494254fc0996c199/master/pass/image.jpg",
      //   "https://upload.wikimedia.org/wikipedia/commons/9/94/Jackling_House%2C_ca._2007.jpg",
      //   "https://helios-i.mashable.com/imagery/articles/06QC1Y39QAJuZ427zc2aMWj/hero-image.fill.size_1248x702.v1611610058.jpg",
      //   "http://cdn3.vox-cdn.com/assets/4541467/7617568354_504c6aff6b_b.jpg",
      //   "http://www.inews365.com/data/photos/20160623/art_14652042411323.jpg",
      //   "https://mblogthumb-phinf.pstatic.net/20120718_89/moonjh7_1342578152208uc3yz_JPEG/%BF%B5%C8%AD_%B3%AA%C8%A6%B7%CE%C1%FD%BF%A1_002.jpg?type=w2",
      //   "https://us.123rf.com/450wm/frankix/frankix1202/frankix120200049/12385627-%EB%86%92%EC%9D%80-%ED%83%80-%ED%8A%B8%EB%9D%BC-%EC%82%B0%EB%A7%A5-%ED%8F%B4%EB%9E%80%EB%93%9C%EC%97%90%EC%84%9C-%EC%A0%84%ED%86%B5-%EA%B0%80%EC%98%A5.jpg?ver=6",
      // ]
    };
  },
  created() {
    if (this.userInfo != null) {
      this.userid = this.userInfo.userid;
    }
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.viewRoadmap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);

      script.src = "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=956eacd3c2cb4eccc009f58ff2bd3933";
      document.head.appendChild(script);
    }
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
  },
  watch: {
    selected() {
      this.viewRoadmap();
    },
  },
  methods: {
    viewRoadmap() {
      var roadviewContainer = document.getElementById("roadview"); //로드뷰를 표시할 div
      var roadview = new kakao.maps.Roadview(roadviewContainer); //로드뷰 객체
      var roadviewClient = new kakao.maps.RoadviewClient(); //좌표로부터 로드뷰 파노ID를 가져올 로드뷰 helper객체

      var position = new kakao.maps.LatLng(this.selected.lat, this.selected.lng);

      // 특정 위치의 좌표와 가까운 로드뷰의 panoId를 추출하여 로드뷰를 띄운다.
      roadviewClient.getNearestPanoId(position, 50, function (panoId) {
        roadview.setPanoId(panoId, position); //panoId와 중심좌표를 통해 로드뷰 실행
      });
    },
    likeThisApt() {
      if (this.userInfo == null) {
        alert("로그인 후 이용가능한 서비스 입니다.");
      } else {
        http
          .post(`/like`, {
            no: "2",
            userid: this.userInfo.userid,
          })
          .then(({ data }) => {
            let msg = "좋아요 등록 실패";
            if (data === "success") {
              msg = "좋아요 목록에 등록되었습니다";
            }
            alert(msg);
          });
      }
    },
  },
};
</script>

<style scoped>
@import "@/sass/map.css";
</style>
