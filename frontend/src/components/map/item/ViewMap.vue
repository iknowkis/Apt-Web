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
  props: [
    "dongcode",
    "search",
    "sido",
    "receivedSelected",
    "closeStr",
    "showDataInfo",
    "showRoadInfo",
    "moneyRange",
  ],
  data() {
    return {
      markers: [],
      starbucks: [],
      cafes: [],
      stores: [],
      schools: [],
      etcs: {},
      infowindow: null,
      isShowInfoMoney: true,
      isShowRoadInfo: false,
      map: null,
      gugun: "",
      dong: "",
      infoList: [],
      limitMoneyRange: [],
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
    search() {
      this.searchPlaces(this.search);
    },
    closeStr() {
      this.removeChipMarker(this.closeStr);
    },
    receivedSelected() {
      this.map.setCenter(
        new kakao.maps.LatLng(
          this.receivedSelected.lat,
          this.receivedSelected.lng
        )
      );
    },
    moneyRange() {
      this.limitMoneyRange = this.moneyRange;
    },
    showDataInfo() {
      this.isShowInfoMoney = this.showDataInfo;
      this.receivedShowDataInfo(this.showDataInfo);
    },
    showRoadInfo() {
      this.isShowRoadInfo = this.showRoadInfo;
      this.receivedShowRoadInfo(this.showRoadInfo);
    },
  },
  methods: {
    initMap() {
      this.makeMap(-1);
    },
    makeMark(dongcode) {
      console.log("makeMark " + dongcode);
      this.map.setLevel(4);
      for (let el of this.infoList) {
        el.close();
      }
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
          let min = this.limitMoneyRange[0];
          let max = this.limitMoneyRange[1];
          let dealMoney = parseInt(
            parseInt(data[i].recentPrice.replace(",", "")) / 1000
          );
          if (dealMoney < min || dealMoney > max) continue;
          // if (i % 3 == 0) {
          //   imageSize = new kakao.maps.Size(50, 45);
          //   markerImage = new kakao.maps.MarkerImage(imageSrcMarked, imageSize);
          // } else {
          //   imageSize = new kakao.maps.Size(43, 50);
          //   markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
          // }

          let title = `${data[i].aptName}\n${this.prettyMoney(
            data[i].recentPrice
          )}`;
          var marker = new kakao.maps.Marker({
            map: this.map,
            position: new kakao.maps.LatLng(data[i].lat, data[i].lng),
            title: title,
            image: markerImage,
          });
          this.markers.push(marker);
          this.map.setCenter(new kakao.maps.LatLng(data[i].lat, data[i].lng));

          let content = `<div class="info-title">${
            data[i].aptName
          }<br/>${this.prettyMoney(data[i].recentPrice)}</div>`;

          var info = new kakao.maps.InfoWindow({
            map: this.map,
            position: new kakao.maps.LatLng(data[i].lat, data[i].lng),
            content: content,
          });
          var infoTitle = document.querySelectorAll(".info-title");
          // .innerHTML = ""; // ## 이거 추가해서 버튼 활성화하기 아니면 v-slot 적용
          let e = infoTitle[infoTitle.length - 1];
          var w = e.offsetWidth + 10;
          var ml = w / 2;
          e.parentElement.style.top = "60px";
          e.parentElement.style.left = "50%";
          e.parentElement.style.marginLeft = -ml + "px";
          e.parentElement.style.width = "fit-content";
          e.parentElement.style["font-size"] = "12px";
          e.parentElement.parentElement.style["z-index"] = "100";
          e.parentElement.previousSibling.style.display = "none";
          e.parentElement.parentElement.style.border = "0px";
          e.parentElement.parentElement.style.background = "unset";
          e.parentElement.setAttribute("class", "showInfoMoney");

          e.parentElement.style.display = "block";
          e.parentElement.style.background = "#50627F";
          e.parentElement.style.color = "#fff";
          e.parentElement.style["text-align"] = "center";
          e.parentElement.style.height = "fit-content";
          e.parentElement.style["line-height"] = "22px";
          e.parentElement.style["border-radius"] = "4px";
          e.parentElement.style.padding = "0px 2px";

          this.infoList.push(info);

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
            level: 7, // 지도의 확대 레벨
          };

        this.map = new kakao.maps.Map(mapContainer, mapOption);

        // 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
        var mapTypeControl = new kakao.maps.MapTypeControl();
        // 지도에 컨트롤을 추가해야 지도위에 표시됩니다
        // kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
        this.map.addControl(
          mapTypeControl,
          kakao.maps.ControlPosition.TOPRIGHT
        );
        // 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
        var zoomControl = new kakao.maps.ZoomControl();
        this.map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
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
      let places = new kakao.maps.services.Places();
      if (places) {
        places.keywordSearch(keyword, this.placesSearchCB);
      }
      // 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
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
      let bounds = new kakao.maps.LatLngBounds();
      var infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });
      // 검색 결과 목록에 추가된 항목들을 제거합니다
      // this.removeAllChildNods(listEl);
      // 지도에 표시되고 있는 마커를 제거합니다
      this.etcs[this.search] = [];

      for (var i = 0; i < places.length; i++) {
        // 마커를 생성하고 지도에 표시합니다

        var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x);
        var marker = this.addMarker(placePosition, i);
        bounds.extend(placePosition);

        (function (marker, title, _this) {
          kakao.maps.event.addListener(marker, "mouseover", function () {
            _this.displayInfowindow(marker, title, infowindow);
          });

          kakao.maps.event.addListener(marker, "mouseout", function () {
            infowindow.close();
          });
        })(marker, places[i].place_name, this);
      }
      this.map.setBounds(bounds);
    },
    addMarker(position) {
      var imageSrc = require("@/assets/markerOrange.png");
      var imageSize = new kakao.maps.Size(35, 35);
      var marker = null;

      if (
        this.search.replace("스타벅스", "").length ==
        this.search.length - 4
      ) {
        imageSrc = require("@/assets/markerStarbucks.png");
        let markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
        marker = new kakao.maps.Marker({
          position: position, // 마커의 위치
          image: markerImage,
        });
        marker.setMap(this.map); // 지도 위에 마커를 표출합니다
        this.starbucks.push(marker);
      } else if (
        this.search.replace("스벅", "").length ==
        this.search.length - 2
      ) {
        imageSrc = require("@/assets/markerStarbucks.png");
        let markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
        marker = new kakao.maps.Marker({
          position: position, // 마커의 위치
          image: markerImage,
        });
        marker.setMap(this.map); // 지도 위에 마커를 표출합니다
        this.starbucks.push(marker);
      } else if (
        this.search.replace("카페", "").length ==
        this.search.length - 2
      ) {
        imageSrc = require("@/assets/markerGreen.png");
        let markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
        marker = new kakao.maps.Marker({
          position: position, // 마커의 위치
          image: markerImage,
        });
        marker.setMap(this.map); // 지도 위에 마커를 표출합니다
        this.cafes.push(marker);
      } else if (
        this.search.replace("편의점", "").length ==
        this.search.length - 3
      ) {
        imageSrc = require("@/assets/markerBlue.png");
        let markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
        marker = new kakao.maps.Marker({
          position: position, // 마커의 위치
          image: markerImage,
        });
        marker.setMap(this.map); // 지도 위에 마커를 표출합니다
        this.stores.push(marker);
      } else if (
        this.search.replace("학교", "").length ==
        this.search.length - 2
      ) {
        imageSrc = require("@/assets/markerRed.png");
        let markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
        marker = new kakao.maps.Marker({
          position: position, // 마커의 위치
          image: markerImage,
        });
        marker.setMap(this.map); // 지도 위에 마커를 표출합니다
        this.schools.push(marker);
      } else {
        let markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
        marker = new kakao.maps.Marker({
          position: position, // 마커의 위치
          image: markerImage,
        });
        marker.setMap(this.map); // 지도 위에 마커를 표출합니다
        this.etcs[this.search].push(marker);
      }

      // var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize),
      //   marker = new kakao.maps.Marker({
      //     position: position, // 마커의 위치
      //     image: markerImage,
      //   });

      // marker.setMap(this.map); // 지도 위에 마커를 표출합니다
      // this.starbucks.push(marker); // 배열에 생성된 마커를 추가합니다

      return marker;
    },
    removeChipMarker(str) {
      if (str == "스타벅스") {
        for (let i = 0; i < this.starbucks.length; i++) {
          this.starbucks[i].setMap(null);
        }
      } else if (str == "카페") {
        for (let i = 0; i < this.cafes.length; i++) {
          this.cafes[i].setMap(null);
        }
      } else if (str == "편의점") {
        for (let i = 0; i < this.stores.length; i++) {
          this.stores[i].setMap(null);
        }
      } else if (str == "학교") {
        for (let i = 0; i < this.schools.length; i++) {
          this.schools[i].setMap(null);
        }
      } else {
        for (let j = 0; j < this.etcs[str].length; j++) {
          this.etcs[str][j].setMap(null);
        }
        this.etcs[str] = [];
      }
    },
    removeMarker() {
      for (var i = 0; i < this.markers.length; i++) {
        this.markers[i].setMap(null);
      }
      this.markers = [];
    },
    displayInfowindow(marker, title, infowindow) {
      var content = '<div style="padding:5px;z-index:1;">' + title + "</div>";

      infowindow.setContent(content);
      infowindow.open(this.map, marker);
    },
    receivedShowDataInfo() {
      var showInfoMoneyList = document.querySelectorAll(".showInfoMoney");
      console.log(showInfoMoneyList.length);
      for (let el of showInfoMoneyList) {
        el.parentElement.style.display = this.isShowInfoMoney
          ? "block"
          : "none";
      }
    },
    receivedShowRoadInfo() {
      if (this.isShowRoadInfo) {
        // 교통 정보 표시
        this.map.addOverlayMapTypeId(kakao.maps.MapTypeId.TRAFFIC);
      } else {
        // 제거
        this.map.removeOverlayMapTypeId(kakao.maps.MapTypeId.TRAFFIC);
      }
    },
    prettyMoney(value) {
      return parseInt(value.replace(",", "")) >= 10000
        ? parseInt(parseInt(value.replace(",", "")) / 10000) +
            "억 " +
            (parseInt(value.replace(",", "")) % 10000 == 0
              ? ""
              : (parseInt(value.replace(",", "")) % 10000) + "만원")
        : parseInt(value.replace(",", "")) + "만원";
    },
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
