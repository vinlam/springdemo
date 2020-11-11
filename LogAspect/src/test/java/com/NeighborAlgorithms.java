package com;

import java.util.*;
//基于用户的最近邻算法（User-Based Neighbor Algorithms），是一种非概率性的协同过滤算法，也是推荐系统中最最古老，最著名的算法，
//我们称那些兴趣相似的用户为邻居，如果用户n相似于用户u，我们就说n是u的一个邻居。起初算法，对于未知目标的预测是根据该用户的相似用户的评分作出预测的
public class NeighborAlgorithms {

	public static void main(String[] args) {

		// 输入用户总量
		UserSet userSet = new UserSet();
		userSet.put("小明").set("中国合伙人", 50).set("太平轮", 30).set("荒野猎人", 45).set("老炮儿", 50).set("我的少女时代", 30)
				.set("肖洛特烦恼", 45).set("火星救援", 50).create();

		userSet.put("小红").set("小时代4", 40).set("荒野猎人", 30).set("我的少女时代", 50).set("肖洛特烦恼", 50).set("火星救援", 30)
				.set("后会无期", 30).create();

		userSet.put("小阳").set("小时代4", 20).set("中国合伙人", 50).set("我的少女时代", 30).set("老炮儿", 50).set("肖洛特烦恼", 45)
				.set("速度与激情7", 50).create();

		userSet.put("小四").set("小时代4", 50).set("中国合伙人", 30).set("我的少女时代", 40).set("匆匆那年", 40).set("速度与激情7", 35)
				.set("火星救援", 35).set("后会无期", 45).create();

		userSet.put("六爷").set("小时代4", 20).set("中国合伙人", 40).set("荒野猎人", 45).set("老炮儿", 50).set("我的少女时代", 20).create();

		userSet.put("小李").set("荒野猎人", 50).set("盗梦空间", 50).set("我的少女时代", 30).set("速度与激情7", 50).set("蚁人", 45)
				.set("老炮儿", 40).set("后会无期", 35).create();

		userSet.put("隔壁老王").set("荒野猎人", 50).set("中国合伙人", 40).set("我的少女时代", 10).set("Phoenix", 50).set("甄嬛传", 40)
				.set("The Strokes", 50).create();

		userSet.put("邻村小芳").set("小时代4", 40).set("我的少女时代", 45).set("匆匆那年", 45).set("甄嬛传", 25).set("The Strokes", 30)
				.create();

		Recommend recommend = new Recommend();
		List<UserSet.Set> recommendations = recommend.recommend("小明", userSet);
		System.out.println("-----------------------");
		for (UserSet.Set set : recommendations) {
			System.out.println(set.username + " " + set.score);
		}
	}

}