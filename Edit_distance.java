package com.user.dp;

import java.util.Scanner;

/*
 * 难度-->1(入门级DP题目)
 * 编辑距离：
 * 求解从字符串是s1到字符串s2需要的最小操作数
 * 支持操作包括：update delete insert
 * 拓展：字符串的一系列匹配问题
 */


public class Edit_Distance {
	public static void main(String[] args) {
		String s1, s2;
		Scanner scanner = new Scanner(System.in);
		System.out.println("请输入第一个字符串");
		s1 = scanner.nextLine();
		System.out.println("请输入第二个字符串");
		s2 = scanner.nextLine();
		int dp[][] = new int[s1.length()+1][s2.length()+1];
		//dp[i][j]：s1的i位置到s2的j位置所需要的最少操作数
		for(int i=0;i<s1.length()+1;i++) {
			dp[i][0] = i;
		}
		for(int j=0;j<s2.length()+1;j++) {
			dp[0][j] = j;
		}
		for(int i=1;i<s1.length()+1;i++) {
			for(int j=1;j<s2.length()+1;j++) {
				if(s1.charAt(i-1) == s2.charAt(j-1)) {
					dp[i][j] = dp[i-1][j-1];
				}else {
					dp[i][j] = Math.min(Math.min(dp[i-1][j],dp[i][j-1]), dp[i-1][j-1])+1;
				}
			}
		}
		System.out.println("两个字符串的编辑距离为：" + dp[s1.length()][s2.length()]);
	}
}
