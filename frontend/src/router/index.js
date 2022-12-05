import Vue from "vue";
import VueRouter from "vue-router";

import AppUser from "@/views/AppUser";
import UserLogin from "@/components/user/UserLogin";
import UserRegister from "@/components/user/UserRegister";
import UserModify from "@/components/user/UserModify";

import AppMain from "@/views/AppMain";

import AppBoard from "@/views/AppBoard";
import BoardNotice from "@/components/board/BoardNotice";
import BoardNoticeList from "@/components/board/notice/BoardNoticeList";
import BoardNoticeWrite from "@/components/board/notice/BoardNoticeWrite";
import BoardNoticeModify from "@/components/board/notice/BoardNoticeModify";
import BoardNoticeDelete from "@/components/board/notice/BoardNoticeDelete";
import BoardNoticeDetail from "@/components/board/notice/BoardNoticeDetail";
import BoardQna from "@/components/board/BoardQna";
import BoardQnaList from "@/components/board/qna/BoardQnaList";
import BoardQnaWrite from "@/components/board/qna/BoardQnaWrite";
import BoardQnaModify from "@/components/board/qna/BoardQnaModify";
import BoardQnaDelete from "@/components/board/qna/BoardQnaDelete";
import BoardQnaDetail from "@/components/board/qna/BoardQnaDetail";

import MainNews from "@/components/main/MainNews";
import MainPopular from "@/components/main/MainPopular";
import MainLike from "@/components/main/MainLike";

import AppMap from "@/views/AppMap";
import TableList from "@/components/map/TableList";
import MapList from "@/components/map/MapList";

import store from "@/store";

Vue.use(VueRouter);

// https://v3.router.vuejs.org/kr/guide/advanced/navigation-guards.html
const onlyAuthUser = async (to, from, next) => {
	const checkUserInfo = store.getters["memberStore/checkUserInfo"];
	const checkToken = store.getters["memberStore/checkToken"];
	let token = sessionStorage.getItem("access-token");
	console.log("로그인 처리 전", checkUserInfo, token);

	if (checkUserInfo != null && token) {
		console.log("토큰 유효성 체크하러 가자!!!!");
		await store.dispatch("memberStore/getUserInfo", token);
	}
	if (!checkToken || checkUserInfo === null) {
		alert("로그인이 필요한 페이지입니다..");
		// next({ name: "login" });
		router.push({ name: "login" });
	} else {
		next();
	}
};

const routes = [
	{
		path: "/",
		name: "main",
		component: AppMain,
		redirect: "/news",
		children: [
			{
				path: "news",
				name: "news",
				component: MainNews,
			},
			{
				path: "popular",
				name: "popular",
				component: MainPopular,
			},
			{
				path: "like",
				name: "like",
				component: MainLike,
			},
		],
	},
	{
		path: "/user",
		name: "user",
		component: AppUser,
		children: [
			{
				path: "login",
				name: "login",
				component: UserLogin,
			},
			{
				path: "register",
				name: "register",
				component: UserRegister,
			},
			{
				path: "modify",
				name: "userModify",
				component: UserModify,
			},
		],
	},
	{
		path: "/board",
		name: "boardMain",
		component: AppBoard,
		children: [
			{
				path: "notice",
				name: "notice",
				component: BoardNotice,
				redirect: "/board/notice/list",
				children: [
					{
						path: "list",
						name: "noticeList",
						component: BoardNoticeList,
					},
					{
						path: "write",
						name: "noticeWrite",
						component: BoardNoticeWrite,
					},
					{
						path: "edit",
						name: "noticeModify",
						component: BoardNoticeModify,
					},
					{
						path: "delete/:no",
						name: "noticeDelete",
						component: BoardNoticeDelete,
					},
					{
						path: ":no",
						name: "noticeDetail",
						component: BoardNoticeDetail,
					},
				],
			},
			{
				path: "qna",
				name: "qna",
				component: BoardQna,
				redirect: "/board/qna/list",
				children: [
					{
						path: "list",
						name: "qnaList",
						component: BoardQnaList,
					},
					{
						path: "write",
						name: "qnaWrite",
						beforeEnter: onlyAuthUser,
						component: BoardQnaWrite,
					},
					{
						path: "edit",
						name: "qnaModify",
						component: BoardQnaModify,
					},
					{
						path: "delete/:no",
						name: "qnaDelete",
						component: BoardQnaDelete,
					},
					{
						path: ":no",
						name: "qnaDetail",
						component: BoardQnaDetail,
					},
				],
			},
		],
	},
	{
		path: "/apt",
		name: "aptlist",
		redirect: "map",
		component: AppMap,
		children: [
			{
				path: "/map",
				name: "maplist",
				component: MapList,
			},
			{
				path: "/table",
				name: "tablelist",
				component: TableList,
			},
		],
	},
];

const router = new VueRouter({
	mode: "history",
	base: process.env.BASE_URL,
	routes,
});

export default router;
