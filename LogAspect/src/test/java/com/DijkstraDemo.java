package com;

import java.util.Scanner;

import com.sun.tools.javac.util.GraphUtils.Node;
import com.util.JsonUtil;

import java.util.*;

public class DijkstraDemo {

	private static int IMAX = 10000; // 不连通状态
	public static int[][] adjMat = { 
			{ 0, 1, 3, 6 }, 
			{ 1, 0, IMAX, 6 }, 
			{ 3, IMAX, 0, 2 }, 
			{ 6, 6, 2, 0 } 
			};
	static final int MAX = 10000;
	public static void main(String[] args) {

		Dijkstra(adjMat, 0, 3);
		
		int[][] weight = {
                {0,3,2000,7,MAX},
                {3,0,4,2,MAX},
                {MAX,4,0,5,4},
                {7,2,5,0,6},
                {MAX,MAX,4,6,0}
                };
        int start = 0;
        int[] dijsktra = Dijsktra(weight,start);
        System.out.println(JsonUtil.beanToJson(dijsktra));
	}

	public static void Dijkstra(int[][] martix,int start,int terminal){
        boolean []isVisted = new boolean[martix.length];
        int []d = new int[martix.length];
        for (int i = 0;i<martix.length;i++){
            isVisted[i] = false; //该点是否被计入，可以理解为判断该点是否已经加入集合B
            d[i] = IMAX;//在当前的集合B所能连接的路径中，从起始点到该点的最短路径
        }
        isVisted[start] = true;
        d[start] = 0;
        int unVisitNode = martix.length ;
        int index = start;
        while (unVisitNode > 0 && !isVisted[terminal] ){
            int min = IMAX;
            //选出集合A中的点到集合B的路径最短的点
            for (int i = 0;i<d.length;i++){
                if (min > d[i] && !isVisted[i]){
                    index = i;
                    min = d[i];
                }
            }
            
            
        for (int i = 0;i<martix.length;i++){
                if (d[index] + martix[index][i] < d[i]) //更新当前的最短路径
                    d[i] = d[index] + martix[index][i];
        }

        unVisitNode -- ;
            isVisted[index] = true;
        }

        System.out.println(d[terminal]);
    }
	
	//接受一个有向图的权重矩阵，和一个起点编号start（从0编号，顶点存在数组中)
    //返回一个int[] 数组，表示从start到它的最短路径长度  
    public static int[] Dijsktra(int[][]weight,int start){
        int length = weight.length;
        int[] shortPath = new int[length];//存放从start到各个点的最短距离
        shortPath[0] = 0;//start到他本身的距离最短为0
        String path[] = new String[length];//存放从start点到各点的最短路径的字符串表示
        for(int i=0;i<length;i++){
            path[i] = start+"->"+i;
        }
        int visited[] = new int[length];//标记当前该顶点的最短路径是否已经求出，1表示已经求出
        visited[0] = 1;//start点的最短距离已经求出
        for(int count = 1;count<length;count++){
            int k=-1;
            int dmin = Integer.MAX_VALUE;
            for(int i=0;i<length;i++){
                if(visited[i]==0 && weight[start][i]<dmin){
                    dmin = weight[start][i];
                    k=i;
                }
            }
            //选出一个距离start最近的未标记的顶点     将新选出的顶点标记为以求出最短路径，且到start的最短路径为dmin。
            shortPath[k] = dmin;
            visited[k] = 1;
            //以k为中间点，修正从start到未访问各点的距离
            for(int i=0;i<length;i++){
                if(visited[i]==0 && weight[start][k]+weight[k][i]<weight[start][i]){
                    weight[start][i] = weight[start][k]+weight[k][i];
                    path[i] = path[k]+"->"+i;
                }
            }
        }
        for(int i=0;i<length;i++){
            System.out.println("从"+start+"出发到"+i+"的最短路径为："+path[i]+"="+shortPath[i]);
        }
        return shortPath;
         
    }
}