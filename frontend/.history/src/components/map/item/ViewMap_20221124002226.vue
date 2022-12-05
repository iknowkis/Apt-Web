<template>
	<div>
		<div id="map"></div>
		<!-- <div class="button-group">
    <v-btn small dark class="showInfoMoney my-2">매물 금액 표시</v-btn>
  </div> -->
	</div>
</template>

<script>
import http from "@/util/http";

export default {
  name: "ViewMap",
  props: ["dongcode", "search", "sido", "receivedSelected"],
  data() {
    return {
      markers: [],
      starbucks: [],
      infowindow: null,
      isShowInfoMoney: 0,
      map: null,
      gugun: "",
      dong: "",
    };
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap;
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);

      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=956eacd3c2cb4eccc009f58ff2bd3933&libraries=services";
      document.head.appendChild(script);
    }
  },
  watch: {
    dongcode() {
      this.makeMark(this.dongcode);
    },
    sido() {
      this.makeMap(this.sido);
    },
    polyGugun() {
      this.makePolyGugun(this.polyGugun);
    },
    search() {
      this.searchPlaces(this.search);
    },
    receivedSelected() {
      this.map.setCenter(
        new kakao.maps.LatLng(
          this.receivedSelected.lat,
          this.receivedSelected.lng
        )
      );
    },
  },
  methods: {
    initMap() {
        this.makeMap(-1);
    },
    makeMark(dongcode) {
      this.isShowInfoMoney = 0;
      console.log("makeMark " + dongcode);
      this.map.setLevel(4);

      // 교통 정보 표시
      // map.addOverlayMapTypeId(kakao.maps.MapTypeId.TRAFFIC);

      // 북마크한 곳 하트 표시
      // var imageSrcMarked =
      //   "https://cdn-icons-png.flaticon.com/512/2536/2536651.png";
      // var imageSrcMarked = require("@/assets/markerHouse.png");
      // var imageSrcMarked = require("@/assets/markerSearch.png");
      // var imageSrc =
      //   "https://www.designhouseagency.com/wp-content/uploads/2017/10/home-map-marker.png";

      var imageSrc = require("@/assets/markerHouse.png");
      var imageSize = null;
      var markerImage = null;

      // let avgLat = 0;
      // let avgLng = 0;

      // document.getElementById('list').innerHTML = null;
      // let lenData = 0;
      this.removeMarker();
      let _this = this;
      http("/address/apt?dong=" + dongcode).then(({ data }) => {
        // lenData = data.length;
        console.log(data);
        this.gugun = data[0].gugunName;
        this.dong = data[0].dongName;

        imageSize = new kakao.maps.Size(43, 50);
        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

        for (let i = 0; i < data.length; i++) {
          // if (i % 3 == 0) {
          //   imageSize = new kakao.maps.Size(50, 45);
          //   markerImage = new kakao.maps.MarkerImage(imageSrcMarked, imageSize);
          // } else {
          //   imageSize = new kakao.maps.Size(43, 50);
          //   markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
          // }

          let title = `${data[i].aptName}\n${data[i].recentPrice.trim()}만원`;
          var marker = new kakao.maps.Marker({
            map: this.map,
            position: new kakao.maps.LatLng(data[i].lat, data[i].lng),
            title: title,
            image: markerImage,
          });
          this.markers.push(marker);
          this.map.setCenter(new kakao.maps.LatLng(data[i].lat, data[i].lng));

          // let content = `<div class="info-title">${data[i].aptName}<br/>${data[i].recentPrice}만원</div>`;

          // let info = new kakao.maps.InfoWindow({
          //   map: map,
          //   position: new kakao.maps.LatLng(data[i].lat, data[i].lng),
          //   content: content,
          // });
          // var infoTitle = document.querySelectorAll('.info-title');
          // // .innerHTML = ""; // ## 이거 추가해서 버튼 활성화하기 아니면 v-slot 적용
          // let e = infoTitle[infoTitle.length-1];
          // var w = e.offsetWidth + 10;
          // var ml = w/2;
          // e.parentElement.style.top = "60px";
          // e.parentElement.style.left = "50%";
          // e.parentElement.style.marginLeft = -ml+"px";
          // e.parentElement.style.width = "fit-content";
          // e.parentElement.style["font-size"] = "12px";
          // e.parentElement.parentElement.style["z-index"] = "100";
          // e.parentElement.previousSibling.style.display = "none";
          // e.parentElement.parentElement.style.border = "0px";
          // e.parentElement.parentElement.style.background = "unset";

          // e.parentElement.style.display= "block";
          // e.parentElement.style.background= "#50627F";
          // e.parentElement.style.color = "#fff";
          // e.parentElement.style["text-align"] = "center";
          // e.parentElement.style.height= "fit-content";
          // e.parentElement.style["line-height"] ="22px";
          // e.parentElement.style["border-radius"] ="4px";
          // e.parentElement.style.padding="0px 2px";

          kakao.maps.event.addListener(marker, "click", function () {
            console.log("123", 123123);
            _this.map.relayout();
            _this.map.setCenter(
              new kakao.maps.LatLng(data[i].lat, data[i].lng)
            );
            console.log(i + "마커 aptCode: " + data[i].aptCode);

            // hit viewcount
            http("/address/hit?no=" + data[i].no).then(() => {
              data[i].hit++;
              _this.$emit("PassSelectedData", data[i]);
            });
          });

          // kakao.maps.event.addListener(info, 'mouseover', function() {
          //   console.log(i + "마커 aptCode: " + data[i].aptCode);
          //   _this.$emit("PassSelectedData", data[i]);
          // });
        }

        // var showInfoMoney = document.querySelector('.showInfoMoney');

        // showInfoMoney.addEventListener('click', function() {
        //   if((_this.isShowInfoMoney+1) % lenData == 0 ) {
        //     for(let el of infoTitle) {
        //       el.parentElement.style.display = el.parentElement.style.display == "none" ? "block" : "none";
        //     }
        //     _this.isShowInfoMoney += 1;
        //   } else _this.isShowInfoMoney += 1;
        //     console.log("!!!: " + _this.isShowInfoMoney);
        // })
      });
    },
    makeMap(idx) {
      if (idx == -1) {
        var mapContainer = document.getElementById("map"), // 지도를 표시할 div
          mapOption = {
            center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
            level: 8, // 지도의 확대 레벨
        };

        this.map = new kakao.maps.Map(mapContainer, mapOption);

        // // 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
        // var mapTypeControl = new kakao.maps.MapTypeControl();
        // // 지도에 컨트롤을 추가해야 지도위에 표시됩니다
        // // kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
        // map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);
        // // 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
        // var zoomControl = new kakao.maps.ZoomControl();
        // map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
      }
    },
    searchPlaces() {
      // var keyword = document.getElementById("keyword").value;
      var keyword = this.gugun + " " + this.dong + " " + this.search;
      console.log("keyword", keyword);
      // this.map.setLevel(5); // 스타벅스 검색 시 지도 축소
      if (!keyword.replace(/^\s+|\s+$/g, "")) {
        alert("키워드를 입력해주세요!");
        return false;
      }
      // 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
      new kakao.maps.services.Places().keywordSearch(
        keyword,
        this.placesSearchCB
      );
    },
    placesSearchCB(data, status) {
      console.log("data", data);
      if (status === kakao.maps.services.Status.OK) {
        // 정상적으로 검색이 완료됐으면
        // 검색 목록과 마커를 표출합니다
        this.displayPlaces(data);

        // 페이지 번호를 표출합니다
        // this.displayPagination(pagination);
      } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        alert("검색 결과가 존재하지 않습니다.");
        return;
      } else if (status === kakao.maps.services.Status.ERROR) {
        alert("검색 결과 중 오류가 발생했습니다.");
        return;
      }
    },
    displayPlaces(places) {
      let menuEl = document.getElementById("menu_wrap");
      this.removeStarbucksMarker();

      // 검색 결과 목록에 추가된 항목들을 제거합니다
      // this.removeAllChildNods(listEl);
      // 지도에 표시되고 있는 마커를 제거합니다
      for (var i = 0; i < places.length; i++) {
        // 마커를 생성하고 지도에 표시합니다

        var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x);
        this.addMarker(placePosition, i);
      }
      // 검색결과 항목들을 검색결과 목록 Element에 추가합니다
      menuEl.scrollTop = 0;
    },
    addMarker(position) {
      var imageSrc = require("@/assets/markerOrange.png");
      var imageSize = new kakao.maps.Size(45, 45);

      if (
        this.search.replace("스타벅스", "").length ==
        this.search.length - 4
      ) {
        imageSrc = require("@/assets/markerStarbucks.png");
      } else if (
        this.search.replace("스벅", "").length ==
        this.search.length - 2
      ) {
        imageSrc = require("@/assets/markerStarbucks.png");
      } else if (
        this.search.replace("카페", "").length ==
        this.search.length - 2
      ) {
        imageSrc = require("@/assets/markerBlue.png");
      } else if (
        this.search.replace("병원", "").length ==
        this.search.length - 2
      ) {
        imageSrc = require("@/assets/markerGreen.png");
      } else if (
        this.search.replace("학교", "").length ==
        this.search.length - 2
      ) {
        imageSrc = require("@/assets/markerRed.png");
      }

      var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize),
        marker = new kakao.maps.Marker({
          position: position, // 마커의 위치
          image: markerImage,
        });

      marker.setMap(this.map); // 지도 위에 마커를 표출합니다
      this.starbucks.push(marker); // 배열에 생성된 마커를 추가합니다

      return marker;
    },
    removeStarbucksMarker() {
      for (var i = 0; i < this.starbucks.length; i++) {
        this.starbucks[i].setMap(null);
      }
      this.starbucks = [];
    },
    removeMarker() {
      for (var i = 0; i < this.markers.length; i++) {
        this.markers[i].setMap(null);
      }
      this.markers = [];
    },
    displayInfowindow(marker, title) {
      var content = '<div style="padding:5px;z-index:1;">' + title + "</div>";

      kakao.maps.InfoWindow.setContent(content);
      kakao.maps.InfoWindow.open(this.map, marker);
    },
    // removeAllChildNods(el) {
    //   while (el.hasChildNodes()) {
    //     el.removeChild(el.lastChild);
    //   }
    // },
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
#map {
  height: 500px;
}
.button-group {
  margin: 10px 0px;
}
button {
  margin: 0 3px;
}
</style>
