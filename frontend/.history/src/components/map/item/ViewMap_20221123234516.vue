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
  props: ["dongcode", "search", "polyGugun", "receivedSelected"],
  data() {
    return {
      markerPositions1: [
        [33.452278, 126.567803],
        [33.452671, 126.574792],
        [33.451744, 126.572441],
      ],
      markerPositions2: [
        [37.499590490909185, 127.0263723554437],
        [37.499427948430814, 127.02794423197847],
        [37.498553760499505, 127.02882598822454],
        [37.497625593121384, 127.02935713582038],
        [37.49629291770947, 127.02587362608637],
        [37.49754540521486, 127.02546694890695],
        [37.49646391248451, 127.02675574250912],
      ],
      markers: [],
      starbucks: [],
      markerList: [],
      infowindow: null,
      isShowInfoMoney: 0,
      polygons: [],
      areas: [],
      map: null,
      gugun: "",
      dong: "",
    };
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
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
      (this.areas = [
        {
          name: "용산구",
          path: [
            new kakao.maps.LatLng(37.5548768201904, 126.96966524449994),
            new kakao.maps.LatLng(37.55308718044556, 126.97642899633566),
            new kakao.maps.LatLng(37.55522076659584, 126.97654602427454),
            new kakao.maps.LatLng(37.55320655210504, 126.97874667968763),
            new kakao.maps.LatLng(37.55368689494708, 126.98541456064552),
            new kakao.maps.LatLng(37.54722934282707, 126.995229135048),
            new kakao.maps.LatLng(37.549694559809545, 126.99832516302801),
            new kakao.maps.LatLng(37.550159406110104, 127.00436818301327),
            new kakao.maps.LatLng(37.54820235864802, 127.0061334023129),
            new kakao.maps.LatLng(37.546169758665414, 127.00499711608721),
            new kakao.maps.LatLng(37.54385947805103, 127.00727818360471),
            new kakao.maps.LatLng(37.54413326436179, 127.00898460651953),
            new kakao.maps.LatLng(37.539639030116945, 127.00959054834321),
            new kakao.maps.LatLng(37.537681185520256, 127.01726163044557),
            new kakao.maps.LatLng(37.53378887274516, 127.01719284893274),
            new kakao.maps.LatLng(37.52290225898471, 127.00614038053493),
            new kakao.maps.LatLng(37.51309192794448, 126.99070240960813),
            new kakao.maps.LatLng(37.50654651085339, 126.98553683648308),
            new kakao.maps.LatLng(37.50702053393398, 126.97524914998174),
            new kakao.maps.LatLng(37.51751820477105, 126.94988506562748),
            new kakao.maps.LatLng(37.52702918583156, 126.94987870367682),
            new kakao.maps.LatLng(37.534519656862926, 126.94481851935942),
            new kakao.maps.LatLng(37.537500243531994, 126.95335659960566),
            new kakao.maps.LatLng(37.54231338779177, 126.95817394011969),
            new kakao.maps.LatLng(37.54546318600178, 126.95790512689311),
            new kakao.maps.LatLng(37.548791603525764, 126.96371984820232),
            new kakao.maps.LatLng(37.55155543391863, 126.96233786542686),
            new kakao.maps.LatLng(37.5541513366375, 126.9657135934734),
            new kakao.maps.LatLng(37.55566236579088, 126.9691850696746),
            new kakao.maps.LatLng(37.5548768201904, 126.96966524449994),
          ],
        },
        {
          name: "중구",
          path: [
            new kakao.maps.LatLng(37.544062989758594, 127.00854659142894),
            new kakao.maps.LatLng(37.54385947805103, 127.00727818360471),
            new kakao.maps.LatLng(37.546169758665414, 127.00499711608721),
            new kakao.maps.LatLng(37.54820235864802, 127.0061334023129),
            new kakao.maps.LatLng(37.550159406110104, 127.00436818301327),
            new kakao.maps.LatLng(37.549694559809545, 126.99832516302801),
            new kakao.maps.LatLng(37.54722934282707, 126.995229135048),
            new kakao.maps.LatLng(37.55368689494708, 126.98541456064552),
            new kakao.maps.LatLng(37.55320655210504, 126.97874667968763),
            new kakao.maps.LatLng(37.55522076659584, 126.97654602427454),
            new kakao.maps.LatLng(37.55308718044556, 126.97642899633566),
            new kakao.maps.LatLng(37.55487749311664, 126.97240854546743),
            new kakao.maps.LatLng(37.5548766923893, 126.9691718124876),
            new kakao.maps.LatLng(37.55566236579088, 126.9691850696746),
            new kakao.maps.LatLng(37.55155543391863, 126.96233786542686),
            new kakao.maps.LatLng(37.55498984534305, 126.96173858545431),
            new kakao.maps.LatLng(37.55695455613004, 126.96343068837372),
            new kakao.maps.LatLng(37.5590262922649, 126.9616731414449),
            new kakao.maps.LatLng(37.56197662569172, 126.96946316364357),
            new kakao.maps.LatLng(37.56582132960869, 126.96669525397355),
            new kakao.maps.LatLng(37.56824746386509, 126.96909838710842),
            new kakao.maps.LatLng(37.569485309984174, 126.97637402412326),
            new kakao.maps.LatLng(37.56810323716611, 126.98905202099309),
            new kakao.maps.LatLng(37.56961739576364, 127.00225936812329),
            new kakao.maps.LatLng(37.56966688588187, 127.0152677241078),
            new kakao.maps.LatLng(37.572022763755164, 127.0223363152772),
            new kakao.maps.LatLng(37.57190723475508, 127.02337770475695),
            new kakao.maps.LatLng(37.56973041414113, 127.0234585247501),
            new kakao.maps.LatLng(37.565200182350495, 127.02358387477513),
            new kakao.maps.LatLng(37.56505173515675, 127.02678930885806),
            new kakao.maps.LatLng(37.563390358462826, 127.02652159646888),
            new kakao.maps.LatLng(37.5607276739534, 127.02339232029838),
            new kakao.maps.LatLng(37.55779412537163, 127.0228934248264),
            new kakao.maps.LatLng(37.556850715898484, 127.01807638779917),
            new kakao.maps.LatLng(37.55264513061776, 127.01620129137214),
            new kakao.maps.LatLng(37.547466935106435, 127.00931996404753),
            new kakao.maps.LatLng(37.54502351209897, 127.00815187343248),
            new kakao.maps.LatLng(37.544062989758594, 127.00854659142894),
          ],
        },
        {
          name: "종로구",
          path: [
            new kakao.maps.LatLng(37.631840777111364, 126.9749313865046),
            new kakao.maps.LatLng(37.632194205253654, 126.97609588529602),
            new kakao.maps.LatLng(37.629026103322374, 126.97496405167149),
            new kakao.maps.LatLng(37.6285585388996, 126.97992605309885),
            new kakao.maps.LatLng(37.626378096236195, 126.97960492198952),
            new kakao.maps.LatLng(37.6211493968146, 126.98365245774505),
            new kakao.maps.LatLng(37.6177725051378, 126.9837302191854),
            new kakao.maps.LatLng(37.613985109550605, 126.98658977758268),
            new kakao.maps.LatLng(37.611364924201304, 126.98565700183143),
            new kakao.maps.LatLng(37.60401657024552, 126.98665951539246),
            new kakao.maps.LatLng(37.60099164566844, 126.97852019816328),
            new kakao.maps.LatLng(37.59790270809407, 126.97672287261275),
            new kakao.maps.LatLng(37.59447673441787, 126.98544283754865),
            new kakao.maps.LatLng(37.59126960661375, 126.98919808879788),
            new kakao.maps.LatLng(37.592300831997434, 127.0009511248032),
            new kakao.maps.LatLng(37.58922302426079, 127.00228260552726),
            new kakao.maps.LatLng(37.586091007146834, 127.00667090686603),
            new kakao.maps.LatLng(37.58235007703611, 127.00677925856456),
            new kakao.maps.LatLng(37.58047228501006, 127.00863575242668),
            new kakao.maps.LatLng(37.58025588757531, 127.01058748333907),
            new kakao.maps.LatLng(37.582338528091164, 127.01483104096094),
            new kakao.maps.LatLng(37.581693162424465, 127.01673289259993),
            new kakao.maps.LatLng(37.57758802896556, 127.01812215416163),
            new kakao.maps.LatLng(37.5788668917658, 127.02140099081309),
            new kakao.maps.LatLng(37.578034045208426, 127.02313962015988),
            new kakao.maps.LatLng(37.57190723475508, 127.02337770475695),
            new kakao.maps.LatLng(37.56966688588187, 127.0152677241078),
            new kakao.maps.LatLng(37.56961739576364, 127.00225936812329),
            new kakao.maps.LatLng(37.5681357695346, 126.99014772014593),
            new kakao.maps.LatLng(37.569315246023024, 126.9732046364419),
            new kakao.maps.LatLng(37.56824746386509, 126.96909838710842),
            new kakao.maps.LatLng(37.56582132960869, 126.96669525397355),
            new kakao.maps.LatLng(37.57874076105342, 126.95354824618335),
            new kakao.maps.LatLng(37.581020184166476, 126.95812059678624),
            new kakao.maps.LatLng(37.59354736740056, 126.95750665936443),
            new kakao.maps.LatLng(37.595061575856455, 126.9590412421402),
            new kakao.maps.LatLng(37.59833350100327, 126.9576941779143),
            new kakao.maps.LatLng(37.59875701675023, 126.95306020161668),
            new kakao.maps.LatLng(37.602476031211225, 126.95237386792348),
            new kakao.maps.LatLng(37.60507154496655, 126.95404376087069),
            new kakao.maps.LatLng(37.60912809443569, 126.95032198271032),
            new kakao.maps.LatLng(37.615539700280216, 126.95072546923387),
            new kakao.maps.LatLng(37.62433621196653, 126.94900237336302),
            new kakao.maps.LatLng(37.62642708817027, 126.95037844036497),
            new kakao.maps.LatLng(37.629590994617104, 126.95881385457929),
            new kakao.maps.LatLng(37.629794440379136, 126.96376660599225),
            new kakao.maps.LatLng(37.632373740990175, 126.97302793692637),
            new kakao.maps.LatLng(37.631840777111364, 126.9749313865046),
          ],
        },
        {
          name: "서대문구",
          path: [
            new kakao.maps.LatLng(37.59851932019209, 126.95347706883003),
            new kakao.maps.LatLng(37.5992407011344, 126.95499403097206),
            new kakao.maps.LatLng(37.59833350100327, 126.9576941779143),
            new kakao.maps.LatLng(37.595061575856455, 126.9590412421402),
            new kakao.maps.LatLng(37.59354736740056, 126.95750665936443),
            new kakao.maps.LatLng(37.581020184166476, 126.95812059678624),
            new kakao.maps.LatLng(37.57874076105342, 126.95354824618335),
            new kakao.maps.LatLng(37.56197662569172, 126.96946316364357),
            new kakao.maps.LatLng(37.5575156365052, 126.95991288916548),
            new kakao.maps.LatLng(37.55654562007193, 126.9413708153468),
            new kakao.maps.LatLng(37.555098093384, 126.93685861757348),
            new kakao.maps.LatLng(37.55884751347576, 126.92659242918415),
            new kakao.maps.LatLng(37.5633319104926, 126.92828128083327),
            new kakao.maps.LatLng(37.56510367293256, 126.92601582346325),
            new kakao.maps.LatLng(37.57082994377989, 126.9098094620638),
            new kakao.maps.LatLng(37.57599550420081, 126.902091747923),
            new kakao.maps.LatLng(37.587223103650295, 126.91284666446226),
            new kakao.maps.LatLng(37.58541570520177, 126.91531241017965),
            new kakao.maps.LatLng(37.585870567159255, 126.91638068573187),
            new kakao.maps.LatLng(37.583095195853055, 126.9159399866114),
            new kakao.maps.LatLng(37.583459593417196, 126.92175886498167),
            new kakao.maps.LatLng(37.587104600730505, 126.92388813813815),
            new kakao.maps.LatLng(37.588386594820484, 126.92800815682232),
            new kakao.maps.LatLng(37.59157595859555, 126.92776504133688),
            new kakao.maps.LatLng(37.59455434247408, 126.93027139545339),
            new kakao.maps.LatLng(37.59869748704149, 126.94088480070904),
            new kakao.maps.LatLng(37.60065830191363, 126.9414041615336),
            new kakao.maps.LatLng(37.60305781086164, 126.93995273804141),
            new kakao.maps.LatLng(37.610598531321436, 126.95037536795142),
            new kakao.maps.LatLng(37.6083605525023, 126.95056259057313),
            new kakao.maps.LatLng(37.60507154496655, 126.95404376087069),
            new kakao.maps.LatLng(37.602476031211225, 126.95237386792348),
            new kakao.maps.LatLng(37.59851932019209, 126.95347706883003),
          ],
        },
        {
          name: "동대문구",
          path: [
            new kakao.maps.LatLng(37.607062869017085, 127.07111288773496),
            new kakao.maps.LatLng(37.60107201319839, 127.07287376670605),
            new kakao.maps.LatLng(37.59724304056685, 127.06949105186925),
            new kakao.maps.LatLng(37.58953367466315, 127.07030363208528),
            new kakao.maps.LatLng(37.58651213184981, 127.07264218709383),
            new kakao.maps.LatLng(37.5849555116177, 127.07216063016078),
            new kakao.maps.LatLng(37.58026781100598, 127.07619547037923),
            new kakao.maps.LatLng(37.571869232268774, 127.0782018408153),
            new kakao.maps.LatLng(37.559961773835425, 127.07239004251258),
            new kakao.maps.LatLng(37.56231553903832, 127.05876047165025),
            new kakao.maps.LatLng(37.57038253579033, 127.04794980454399),
            new kakao.maps.LatLng(37.572878529071055, 127.04263554582458),
            new kakao.maps.LatLng(37.57302061077518, 127.0381755492195),
            new kakao.maps.LatLng(37.56978273516453, 127.03099733100001),
            new kakao.maps.LatLng(37.57190723475508, 127.02337770475695),
            new kakao.maps.LatLng(37.57838361223621, 127.0232348231103),
            new kakao.maps.LatLng(37.58268174514337, 127.02953994610249),
            new kakao.maps.LatLng(37.58894739851823, 127.03553876830637),
            new kakao.maps.LatLng(37.5911852565689, 127.03621919708065),
            new kakao.maps.LatLng(37.59126734230753, 127.03875553445558),
            new kakao.maps.LatLng(37.5956815721534, 127.04062845365279),
            new kakao.maps.LatLng(37.5969637344377, 127.04302522879048),
            new kakao.maps.LatLng(37.59617641777492, 127.04734129391157),
            new kakao.maps.LatLng(37.60117358544485, 127.05101351973708),
            new kakao.maps.LatLng(37.600149587503246, 127.05209540476308),
            new kakao.maps.LatLng(37.60132672748398, 127.05508130598699),
            new kakao.maps.LatLng(37.6010580545608, 127.05917142337097),
            new kakao.maps.LatLng(37.605121767227374, 127.06219611364686),
            new kakao.maps.LatLng(37.607062869017085, 127.07111288773496),
          ],
        },
        {
          name: "성북구",
          path: [
            new kakao.maps.LatLng(37.63654916557213, 126.98446028560235),
            new kakao.maps.LatLng(37.631446839436855, 126.99372381657889),
            new kakao.maps.LatLng(37.626192451322005, 126.99927047335905),
            new kakao.maps.LatLng(37.62382095469671, 127.00488450145781),
            new kakao.maps.LatLng(37.624026217174986, 127.00788862747375),
            new kakao.maps.LatLng(37.6205124078061, 127.00724034082933),
            new kakao.maps.LatLng(37.61679651952433, 127.01014412786792),
            new kakao.maps.LatLng(37.61472018601129, 127.01451127202589),
            new kakao.maps.LatLng(37.614629670135216, 127.01757841621624),
            new kakao.maps.LatLng(37.61137091590441, 127.02219857751122),
            new kakao.maps.LatLng(37.612692696824915, 127.02642583551054),
            new kakao.maps.LatLng(37.612367438936786, 127.03018593770908),
            new kakao.maps.LatLng(37.60896889076571, 127.0302525167858),
            new kakao.maps.LatLng(37.61279787695882, 127.03730791358603),
            new kakao.maps.LatLng(37.62426467261789, 127.04973339977498),
            new kakao.maps.LatLng(37.61449950127667, 127.06174181124696),
            new kakao.maps.LatLng(37.61561580859776, 127.06985247014711),
            new kakao.maps.LatLng(37.61351359068103, 127.07170798866412),
            new kakao.maps.LatLng(37.60762512162974, 127.07105453180604),
            new kakao.maps.LatLng(37.605121767227374, 127.06219611364686),
            new kakao.maps.LatLng(37.6010580545608, 127.05917142337097),
            new kakao.maps.LatLng(37.60132672748398, 127.05508130598699),
            new kakao.maps.LatLng(37.600149587503246, 127.05209540476308),
            new kakao.maps.LatLng(37.60117358544485, 127.05101351973708),
            new kakao.maps.LatLng(37.59617641777492, 127.04734129391157),
            new kakao.maps.LatLng(37.59644879095525, 127.04184728392097),
            new kakao.maps.LatLng(37.59126734230753, 127.03875553445558),
            new kakao.maps.LatLng(37.5911852565689, 127.03621919708065),
            new kakao.maps.LatLng(37.58894739851823, 127.03553876830637),
            new kakao.maps.LatLng(37.58268174514337, 127.02953994610249),
            new kakao.maps.LatLng(37.57782865303167, 127.02296295333255),
            new kakao.maps.LatLng(37.57889204835333, 127.02179043639809),
            new kakao.maps.LatLng(37.57758802896556, 127.01812215416163),
            new kakao.maps.LatLng(37.581693162424465, 127.01673289259993),
            new kakao.maps.LatLng(37.582338528091164, 127.01483104096094),
            new kakao.maps.LatLng(37.58025588757531, 127.01058748333907),
            new kakao.maps.LatLng(37.58047228501006, 127.00863575242668),
            new kakao.maps.LatLng(37.58235007703611, 127.00677925856456),
            new kakao.maps.LatLng(37.586091007146834, 127.00667090686603),
            new kakao.maps.LatLng(37.58922302426079, 127.00228260552726),
            new kakao.maps.LatLng(37.592300831997434, 127.0009511248032),
            new kakao.maps.LatLng(37.59126960661375, 126.98919808879788),
            new kakao.maps.LatLng(37.59447673441787, 126.98544283754865),
            new kakao.maps.LatLng(37.59790270809407, 126.97672287261275),
            new kakao.maps.LatLng(37.60099164566844, 126.97852019816328),
            new kakao.maps.LatLng(37.60451393107786, 126.98678626394351),
            new kakao.maps.LatLng(37.611364924201304, 126.98565700183143),
            new kakao.maps.LatLng(37.613985109550605, 126.98658977758268),
            new kakao.maps.LatLng(37.6177725051378, 126.9837302191854),
            new kakao.maps.LatLng(37.6211493968146, 126.98365245774505),
            new kakao.maps.LatLng(37.626378096236195, 126.97960492198952),
            new kakao.maps.LatLng(37.6285585388996, 126.97992605309885),
            new kakao.maps.LatLng(37.62980449548538, 126.97468284124939),
            new kakao.maps.LatLng(37.633657663246694, 126.97740053878216),
            new kakao.maps.LatLng(37.63476479485093, 126.98154674721893),
            new kakao.maps.LatLng(37.63780700422825, 126.9849494717052),
            new kakao.maps.LatLng(37.63654916557213, 126.98446028560235),
          ],
        },
        {
          name: "성동구",
          path: [
            new kakao.maps.LatLng(37.57275246810175, 127.04241813085706),
            new kakao.maps.LatLng(37.57038253579033, 127.04794980454399),
            new kakao.maps.LatLng(37.56231553903832, 127.05876047165025),
            new kakao.maps.LatLng(37.5594131360664, 127.07373408220053),
            new kakao.maps.LatLng(37.52832388381049, 127.05621773388143),
            new kakao.maps.LatLng(37.53423885672233, 127.04604398310076),
            new kakao.maps.LatLng(37.53582328355087, 127.03979942567628),
            new kakao.maps.LatLng(37.53581367627865, 127.0211714455564),
            new kakao.maps.LatLng(37.53378887274516, 127.01719284893274),
            new kakao.maps.LatLng(37.537681185520256, 127.01726163044557),
            new kakao.maps.LatLng(37.53938672166098, 127.00993448922989),
            new kakao.maps.LatLng(37.54157804358092, 127.00879872996808),
            new kakao.maps.LatLng(37.54502351209897, 127.00815187343248),
            new kakao.maps.LatLng(37.547466935106435, 127.00931996404753),
            new kakao.maps.LatLng(37.55264513061776, 127.01620129137214),
            new kakao.maps.LatLng(37.556850715898484, 127.01807638779917),
            new kakao.maps.LatLng(37.55779412537163, 127.0228934248264),
            new kakao.maps.LatLng(37.5607276739534, 127.02339232029838),
            new kakao.maps.LatLng(37.563390358462826, 127.02652159646888),
            new kakao.maps.LatLng(37.56505173515675, 127.02678930885806),
            new kakao.maps.LatLng(37.565200182350495, 127.02358387477513),
            new kakao.maps.LatLng(37.57190723475508, 127.02337770475695),
            new kakao.maps.LatLng(37.56978273516453, 127.03099733100001),
            new kakao.maps.LatLng(37.57302061077518, 127.0381755492195),
            new kakao.maps.LatLng(37.57275246810175, 127.04241813085706),
          ],
        },
        {
          name: "마포구",
          path: [
            new kakao.maps.LatLng(37.584651324803644, 126.88883849288884),
            new kakao.maps.LatLng(37.57082994377989, 126.9098094620638),
            new kakao.maps.LatLng(37.56510367293256, 126.92601582346325),
            new kakao.maps.LatLng(37.5633319104926, 126.92828128083327),
            new kakao.maps.LatLng(37.55884751347576, 126.92659242918415),
            new kakao.maps.LatLng(37.55675317809392, 126.93190919632814),
            new kakao.maps.LatLng(37.555098093384, 126.93685861757348),
            new kakao.maps.LatLng(37.55654562007193, 126.9413708153468),
            new kakao.maps.LatLng(37.557241466445234, 126.95913438471307),
            new kakao.maps.LatLng(37.55908394430372, 126.96163689468189),
            new kakao.maps.LatLng(37.55820141918588, 126.96305432966605),
            new kakao.maps.LatLng(37.554784413504734, 126.9617251098019),
            new kakao.maps.LatLng(37.548791603525764, 126.96371984820232),
            new kakao.maps.LatLng(37.54546318600178, 126.95790512689311),
            new kakao.maps.LatLng(37.54231338779177, 126.95817394011969),
            new kakao.maps.LatLng(37.539468942052544, 126.955731506394),
            new kakao.maps.LatLng(37.536292068277106, 126.95128907260018),
            new kakao.maps.LatLng(37.53569162926515, 126.94627494388307),
            new kakao.maps.LatLng(37.53377712226906, 126.94458373402794),
            new kakao.maps.LatLng(37.54135238063506, 126.93031191951576),
            new kakao.maps.LatLng(37.539036674424615, 126.9271006565075),
            new kakao.maps.LatLng(37.54143034750605, 126.9224138272872),
            new kakao.maps.LatLng(37.54141748538761, 126.90483000187002),
            new kakao.maps.LatLng(37.548015078285694, 126.89890097452322),
            new kakao.maps.LatLng(37.56300401736817, 126.86623824787709),
            new kakao.maps.LatLng(37.57178997971358, 126.85363039091744),
            new kakao.maps.LatLng(37.57379738998644, 126.85362646212587),
            new kakao.maps.LatLng(37.57747251471329, 126.864939928088),
            new kakao.maps.LatLng(37.5781913017327, 126.87625939970273),
            new kakao.maps.LatLng(37.57977132158497, 126.87767870371688),
            new kakao.maps.LatLng(37.584440882833654, 126.87653889183002),
            new kakao.maps.LatLng(37.59079311146897, 126.88205386700724),
            new kakao.maps.LatLng(37.584651324803644, 126.88883849288884),
          ],
        },
      ]),
        this.makePolyGugun(-1);
    },
    makeMark(dongcode) {
      this.deletePolygon();
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
    makePolyGugun(idx) {
      if (idx == -1) {
        var mapContainer = document.getElementById("map"), // 지도를 표시할 div
          mapOption = {
            center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
            level: 8, // 지도의 확대 레벨
          };

        this.map = new kakao.maps.Map(mapContainer, mapOption);

        var customOverlay = new kakao.maps.CustomOverlay({});

        // // 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
        // var mapTypeControl = new kakao.maps.MapTypeControl();
        // // 지도에 컨트롤을 추가해야 지도위에 표시됩니다
        // // kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
        // map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);
        // // 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
        // var zoomControl = new kakao.maps.ZoomControl();
        // map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

        for (var i = 0, len = this.areas.length; i < len; i++) {
          this.displayArea(this.map, customOverlay, this.areas[i], i);
        }
        // 지도에 영역데이터를 폴리곤으로 표시합니다
      } else this.deletePolygon();
    },
    displayArea(map, customOverlay, area) {
      // 다각형을 생성합니다
      var polygon = new kakao.maps.Polygon({
        map: map, // 다각형을 표시할 지도 객체
        path: area.path,
        strokeWeight: 2,
        strokeColor: "#000000",
        strokeOpacity: 0.8,
        fillColor: "#fff",
        fillOpacity: 0.7,
      });

      this.polygons.push(polygon);

      // 다각형에 mouseover 이벤트를 등록하고 이벤트가 발생하면 폴리곤의 채움색을 변경합니다
      // 지역명을 표시하는 커스텀오버레이를 지도위에 표시합니다
      kakao.maps.event.addListener(polygon, "mouseover", function () {
        polygon.setOptions({
          fillColor: "#E65100",
        });

        // customOverlay.setContent(
        //   '<div class="area"><b>' + area.name + "</b></div>"
        // );

        // customOverlay.setPosition(mouseEvent.latLng);
        // customOverlay.setMap(map);
      });

      // 다각형에 mousemove 이벤트를 등록하고 이벤트가 발생하면 커스텀 오버레이의 위치를 변경합니다
      // kakao.maps.event.addListener(polygon, "mousemove", function (mouseEvent) {
      //   customOverlay.setPosition(mouseEvent.latLng);
      // });

      // 다각형에 mouseout 이벤트를 등록하고 이벤트가 발생하면 폴리곤의 채움색을 원래색으로 변경합니다
      // 커스텀 오버레이를 지도에서 제거합니다
      kakao.maps.event.addListener(polygon, "mouseout", function () {
        polygon.setOptions({
          fillColor: "#fff",
        });
        // customOverlay.setMap(null);
      });
      // let _this = this;
      // 다각형에 click 이벤트를 등록하고 이벤트가 발생하면 다각형의 이름과 면적을 인포윈도우에 표시합니다
      // kakao.maps.event.addListener(polygon, 'click', function() {
      //     let level = map.getLevel() - 2;

      //     map.setLevel(level, {anchor: _this.centroid(idx), animate: {
      //       duration: 350
      //     }})
      //     _this.deletePolygon();
      // });
    },
    // centroid(idx) {
    //   let i, j, len, p1, p2, f, areaValue, x, y;

    //   areaValue = x = y = 0;

    //   let points = this.areas[idx];

    //   console.log(points.path[0]);

    //   for(i=0, len = points.length, j = len - 1; i < len; j = i++) {
    //     p1 = points[i];
    //     p2 = points[j];

    //     f = p1.Ma * p2.La - p2.Ma * p1.La;
    //     x += (p1.La + p2.La) * f;
    //     y += (p1.Ln + p2.Ln) * f;
    //     areaValue += f *3;
    //   }

    //   return new kakao.maps.LatLng(x / areaValue, y / areaValue);
    // },
    deletePolygon() {
      for (let i = 0; i < this.polygons.length; i++) {
        this.polygons[i].setMap(null);
      }
      this.polygons = [];
    },
    searchPlaces() {
      // var keyword = document.getElementById("keyword").value;
      var keyword = this.gugun + " " + this.dong + " " + this.search;
      console.log("keyword", keyword);
      this.deletePolygon();
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
    getListItem(index, places) {
      var el = document.createElement("li"),
        itemStr =
          '<span class="markerbg marker_' +
          (index + 1) +
          '"></span>' +
          '<div class="info">' +
          "   <h5>" +
          places.place_name +
          "</h5>";

      if (places.road_address_name) {
        itemStr +=
          "    <span>" +
          places.road_address_name +
          "</span>" +
          '   <span class="jibun gray">' +
          places.address_name +
          "</span>";
      } else {
        itemStr += "    <span>" + places.address_name + "</span>";
      }

      itemStr += '  <span class="tel">' + places.phone + "</span>" + "</div>";

      el.innerHTML = itemStr;
      el.className = "item";

      return el;
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
    // displayPagination(pagination) {
    //   var paginationEl = document.getElementById("pagination"),
    //     fragment = document.createDocumentFragment(),
    //     i;

    //   // 기존에 추가된 페이지번호를 삭제합니다
    //   while (paginationEl.hasChildNodes()) {
    //     paginationEl.removeChild(paginationEl.lastChild);
    //   }

    //   for (i = 1; i <= pagination.last; i++) {
    //     var el = document.createElement("a");
    //     el.href = "#";
    //     el.innerHTML = i;

    //     if (i === pagination.current) {
    //       el.className = "on";
    //     } else {
    //       el.onclick = (function (i) {
    //         return function () {
    //           pagination.gotoPage(i);
    //         };
    //       })(i);
    //     }

    //     fragment.appendChild(el);
    //   }
    //   paginationEl.appendChild(fragment);
    // },
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
