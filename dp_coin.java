package com.user.dp;

import java.util.Scanner;

/*
 * 难度-->1(入门级DP题目)
 * 假设有几种硬币，如1、3、5，并且数量无限。
 * 请找出能够组成某个数目的找零所使用最少的硬币数。 
 * 3+3=5+1,即如果输入的数据mod5=1时，若讨论解决方案则有两种
 * 以上为“贪心算法”思路，但若面值为1，3，4时，4+1+1=3+3，但是3>2，此时贪心算法将不再合适
 * 
 */
public class DP_coin {
	
	private final static int MAX_NUM = 50000;
	
	public static void main(String[] args) {
		
		int count=0, res=0,option=0;
		System.out.println("请输入找零额");
		Scanner scanner = new Scanner(System.in);
		count = scanner.nextInt();
		System.out.println("请输入一种找零处理方法(1-5)");
		option = scanner.nextInt();
		switch(option) 
		{
			case 1:
				res = normal_coin(count); //方案一	
				if(!check_res(res))
				System.out.println("method_01_res:"+ res);
				break;
			case 2:
				res = dpdesign_coin(count); //方案二
				if(!check_res(res))
				System.out.println("method_02_res:"+ res);
				break;
			case 3:
				res = common_dp_coin(count); //方案三
				if(!check_res(res))
				System.out.println("method_03_res:"+ res);
				break;
			case 4:
				res = single_dp_coin(count); //方案四
				if(!check_res(res))
				System.out.println("method_04_res:"+ res);
				break;
			case 5:
				res = single_common_dp_coin(count); //方案五
				if(!check_res(res))
				System.out.println("method_05_res:"+ res);
				break;
			default :
				System.out.println("输入选项不正确，本次服务到此结束");
		}
	}
	
	//针对res的check_res函数
	public static boolean check_res(int res) {
		if(res != MAX_NUM) {
			return false;
		}else {
			System.out.println("无法给出合适的找零方案");
			return true;
		}			
	}
	
	//method_01 : normal_coin() 
	//针对题设的1，3，5的情况，贪心算法可解	
 	public static int normal_coin(int count) {
		int res = 0;
		res = count/5;
		count= count%5;
		res = res +count/3;
		count = count%3;
		res = res +count;
		// TODO Auto-generated method stub
		return res;
	}
	
	/*
	 * 动态规划思路： 用count描述子结构的状态，记作sum[k],直为所需最小硬币数的值
	 * 不同硬币面值为coin[0,1,...,n],则sum[k]=Min(sum[k-coin[0]],sum[k-coin[1]],...)+1
	 * 即递归设计，每次选择一个合适的coin
	 * 对于给定数目的找零total，需要求解sum[total]的值
	 */	
	
	//method_02 : dpdesign_coin
	//继续考虑每种coin有无限多个的情况	
	public static int dpdesign_coin(int count) {
		int arr[] = new int[] {1,3,5};
		int dp[][] = new int[arr.length][count+1];
		//dp[m][n]指代当count=n时需要m面值硬币的数量
		//initial count=0 时找零结果只有一种,即不需要硬币
		for(int i=0;i<arr.length;i++) {
			dp[i][0] = 0;
		}
		for(int j=1;j<count+1;j++) {
			if(j%arr[0]==0) {
				dp[0][j] = j/arr[0] ;
			}else {
				dp[0][j] = MAX_NUM;
			}
		}
		//update the Dim-2 array
		for(int i=1;i<arr.length;i++) {
			for(int j=1;j<count+1;j++) {
				if(arr[i]>j) {
					dp[i][j] = dp[i-1][j];
				}else {
					dp[i][j] = Math.min(dp[i-1][j], dp[i][j-arr[i]]+1);
				}
			}
		}
		int res = dp[arr.length-1][count];
		return res;
	}
	
	//method_03 : common_dp_coin
	//给出一般情况下的求解办法，即对于任意给出的硬币面值和找余金额给出相应解。
	public static int common_dp_coin(int count) {
		Scanner scanner = new Scanner(System.in);
		//输入可选择的硬币种类
		System.out.println("请输入可选择的硬币种类");
		int m = scanner.nextInt();
		int arr[] = new int[m];
		System.out.println("请依次输入硬币面值");
		//输入并初始化arr[] 
		//TODO --> error check : 输入的硬币面值应当各不相同
		for(int i=0;i<m;i++) {
			arr[i]=scanner.nextInt();
		}
		/*
		 * arr数组ASC排序 --> 测试发现并不需要
		 * Reason:从原理出发，迭代设计与值得大小并无设计上的必然联系
		 */
		//新建dp数组
		int dp[][] = new int[m][count+1];
		//初始化
		for(int i=0;i<m;i++) {
			dp[i][0] = 0;
		}
		for(int j=1;j<count+1;j++) {
			if(j%arr[0]==0) {
				dp[0][j] = j/arr[0];
			}else {
				dp[0][j] = MAX_NUM;
			}
		}
		//update the Dim-2 array
		for(int i=1;i<arr.length;i++) {
			for(int j=1;j<count+1;j++) {
				if(arr[i]>j) {
					dp[i][j] = dp[i-1][j];
				}else {
					dp[i][j] = Math.min(dp[i-1][j], dp[i][j-arr[i]]+1);
				}
			}
		}
		int res = dp[m-1][count];
		return res;
	}
	
	//method_04 : single_dp_coin
	public static int single_dp_coin(int count) {
		int arr[] = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
		int dp[][] = new int[arr.length][count+1];
		for(int i=0;i<arr.length;i++) {
			dp[i][0] = 0;
		}
		for(int j=0;j<count+1;j++) {
			if(j==arr[0]) {
				dp[0][j] = 1;
			}else {
				dp[0][j] = MAX_NUM;
			}
		}
		//update the Dim-2 array
		for(int i=1;i<arr.length;i++) {
			for(int j=1;j<count+1;j++) {
				if(arr[i]>j) {
					dp[i][j] = dp[i-1][j];
				}else {
					dp[i][j] = Math.min(dp[i-1][j], dp[i-1][j-arr[i]]+1);
					//使用dp[i-1][j-arr[i]]+1的原因：每种面额的硬币只有一张
				}
			}
		}
		int res = dp[arr.length-1][count];
		return res;
	}
	
	//method_05 : single_common_dp_coin(need to update)
	//再次明确dp[i][j]的含义：找零j的钱数，用i面额的钱的情况下一共需要几张
	public static int single_common_dp_coin(int count) {	
		Scanner scanner = new Scanner(System.in);
		//输入可选择的硬币种类
		System.out.println("请输入可选择的硬币种类");
		int m = scanner.nextInt();
		int arr[] = new int[m];
		System.out.println("请依次输入硬币面值");
		//输入并初始化arr[] 
		//TODO --> error check : 输入的硬币面值应当各不相同
		for(int i=0;i<m;i++) {
			arr[i]=scanner.nextInt();
		}
		//新建dp数组
		int dp[][] = new int[m][count+1];
		//初始化
		for(int i=0;i<m;i++) {
			dp[i][0] = 0;
		}
		for(int j=0;j<count+1;j++) {
			if(j==arr[0]) {
				dp[0][j] = 1;
			}else {
				dp[0][j] = MAX_NUM;
			}
		}
		//update the Dim-2 array
		for(int i=1;i<arr.length;i++) {
			for(int j=1;j<count+1;j++) {
				if(arr[i]>j) {
					dp[i][j] = dp[i-1][j];
				}else {
					dp[i][j] = Math.min(dp[i-1][j], dp[i-1][j-arr[i]]+1);
					//使用dp[i-1][j-arr[i]]+1的原因：每种面额的硬币只有一张
				}
			}
		}
		int res = dp[m-1][count];
		return res;
	}
}

