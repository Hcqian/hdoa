package hdoa;

import java.util.Scanner;



import java.lang.Math;

//该算法如果两个.ct文件相同碱基对超过1000，那么需要修改sort里的int[] A=new int[1000]值

public class Hdoa {
	
	private static Scanner on;
	private static Scanner in;


	public int[] Hausdorff(int[][] sort1_i,int[][] sort1_j,int[][] sort2_i,int[][] sort2_j){
		
		int k=sort1_i.length+sort2_i.length;

		int[] d=new int[k];    //用来存储d的值，即相当于H中的最小值，最大值
		int r=0;              //用来存储当前框的“半径”
		int i=0,j=0;
		//int t=0;
		Search search_i=new Search();
		for(int m=0;m<=k;m++){
			
			if(i<sort1_i.length && j<sort2_i.length){
				
				if(sort1_i[i][0]<=sort2_i[j][0]){                      //定第一个，从第二个里分别按i和j找两个元素，与第一个比
					
					//System.out.println("if--else if-1执行");
					
			    		//在Search基础上，实现d=min{d(i.j, i1’.j1’),d(i.j, i2’.j2’)};

							int s1=search_i.binarySearch_i(sort2_i,sort1_i[i][0]);   //s1值等于middle值
							int s2=search_i.sequnceSearch_j(sort2_j,sort1_i[i][1]);   //s2值等于middle值

							int abs1=Math.abs(sort1_i[i][0]-sort2_i[s1][0]);     //abs1为i与i1之间的距离   i1
							int abs2=Math.abs(sort1_i[i][1]-sort2_i[s1][1]);     //abs2为j与j1之间的距离   j1
							
							//按i找出的点（碱基对）与i.j的距离
							int a1=abs1>abs2?abs1:abs2;
									
							//System.out.println("a1="+a1);
							
							int abs3=Math.abs(sort1_i[i][0]-sort2_j[s2][0]);     //abs3为i与i2之间的距离   i2
							int abs4=Math.abs(sort1_i[i][1]-sort2_j[s2][1]);     //abs4为j与j2之间的距离   j2
							
							//按j找出的点（碱基对）与i.j的距离		
							int a2=abs3>abs4?abs3:abs4;
							
							//System.out.println("a2="+a2);
							
							int prior_i;
							int prior_j;
							int next_i;
							int next_j;
						
							//计算H距离
							//System.out.println("111111111111111111111");
							if (a1 <= a2){
								
								//System.out.println("按i开始找");
								
								//System.out.println("$$$$$$$$$$$$$$$$$$$$$$");
								if(abs1 > abs2){
									//System.out.println("按i开始找,且mini>minj,直接得到结果");
									//System.out.println("+++++++++++++++++++++++");
									d[m]=a1;
									i++;
									//System.out.println("i = "+i);  
									
									}else{
										//System.out.println("进入-按i开始找的else");
									    r=Math.min(a1,a2); //得到目前矩形框半径	
										int c1_i=sort1_i[i][0]-r;
										int c1_j=sort1_i[i][1]-r;
										int c3_i=sort1_i[i][0]+r; 
										int c3_j=sort1_i[i][1]+r;
									
										int c=0;
										
										if(sort2_i[s1][1] > sort1_i[i][1]){                        //即minj>j
											
											//System.out.println("按j且minj>j");
											
											next_i = abs1;
											next_j = abs2;
										//向后找，递归找到最小r，以及其各个短点
										//System.out.println(c1_i<next_i && next_i<c3_i && c1_j<next_j && next_j<c3_j);
											while (c1_i<=sort2_i[s1+c][0] && sort2_i[s1+c][0]<=c3_i && c1_j<=sort2_i[s1+c][1] && sort2_i[s1+c][1]<=c3_j){
												int nr=Math.max(next_i,next_j);
												r=Math.min(r,nr);
												c++;
											
												c1_i=sort1_i[i][0]-r;                            //这里多计算了很多式子，将来优化
												c1_j=sort1_i[i][1]-r;
												c3_i=sort1_i[i][0]+r; 
												c3_j=sort1_i[i][1]+r;
												
												if(s1+c<sort2_i.length){
													
													next_i = Math.abs(sort1_i[i][0]-sort2_i[s1+c][0]);
													next_j = Math.abs(sort1_i[i][1]-sort2_i[s1+c][1]);
													}else{
														break;               //跳出while循环
													}
												}
												//必定是大于0（1以上）的部分在比对，所以通过下面代码可除去其它冗余项
												int s;
												if(c1_i<=1){
													s=1;
													}else{
														s=c1_i;
														}
												int num=c3_i-s<sort2_i.length-s?c3_i-s:sort2_i.length-s;   //得到的需要比对的横轴个数(需要加1,或者=num)，这里c3_i-s可能会超出sort2_i的长度，股选取一个最小值。
												//System.out.println("num***1"+"="+num);
												//System.out.println("sort2_i[s-1][0]"+"="+sort2_i[s-1][0]);
												//System.out.println("sort2_i[s-1][1]"+"="+sort2_i[s-1][1]);
												//a到num得值==s到c3_i-s，下面循环可以保证正好从s到c3_i为止（和从0到num值一样）
												
												int A1[]=new int[num+1];
												int A2[]=new int[num+1];
												int max1[]=new int[num+1];
												int h=s-1;
												//System.out.println("h"+"="+h);   ..........
								    	
												int l=0;       //计数器，记录S2中有几个数进行了比对
												for(int a=0;a<=num;a++){      //等于num是因为5-1=4，从一到五其实有五个数，4-0=4，从0到4其实有5个数，需要比对5次
													
													//System.out.println("sort2_i"+"["+h+"]"+"[0]"+"="+sort2_i[h][0]);    ........
													//System.out.println("sort2_i"+"["+h+"]"+"[1]"+"="+sort2_i[h][1]);        ...........
											
													//System.out.println(sort2_i[h][0]>=c1_i && sort2_i[h][0]<=c3_i && sort2_i[h][1]>=c1_j && sort2_i[h][1]<=c3_j );  ........
													if(sort2_i[h][0]>=c1_i && sort2_i[h][0]<=c3_i && sort2_i[h][1]>=c1_j && sort2_i[h][1]<=c3_j){   //s-1：因为数组从零开始，RNA中[0][0]即（1,n）
														
														//计算H距离sort1_i[i][j]和sort2_i
														A1[l]=Math.abs(sort1_i[i][0]-sort2_i[h][0]);                            
														//System.out.println("A1"+"["+l+"]"+"="+A1[l]);  .........
												
														A2[l]=Math.abs(sort1_i[i][1]-sort2_i[h][1]);
														//System.out.println("A2"+"["+l+"]"+"="+A2[l]); .............
												
														max1[l]=A1[l]>A2[l]?A1[l]:A2[l];
												
														l++;         //计数器，记录S2中有几个数进行了比对
														h++;
														}else{
															h++;
														}
													}
												int temp=max1[0];
										
												for(int b=0;b<l;b++){
													if(max1[b]<temp){
														temp=max1[b];
														}
													}
												d[m]=temp; //得到最小值                  //将temp值存入min[]数组中
										
												i++;
												
												}
										else if(sort2_i[s1][1] <= sort1_i[i][1]){                   //即minj<j
											
											//System.out.println("按j且minj<=j");
											
											prior_i = abs1; //i与第s1-1个i1的差的绝对值
											prior_j = abs2; //j与第s1-1个j1的差的绝对值
											//向前找，递归找到最小r，以及其各个短点
											while (c1_i<=sort2_i[s1-c][0] && sort2_i[s1-c][0]<=c3_i && c1_j<=sort2_i[s1-c][1] && sort2_i[s1-c][1]<=c3_j){
													
													int nr=Math.max(prior_i,prior_j);
													r=Math.min(r,nr);
													c++;
													//System.out.println("c="+c); 
													c1_i=sort1_i[i][0]-r;
													c1_j=sort1_i[i][1]-r;
													c3_i=sort1_i[i][0]+r; 
													c3_j=sort1_i[i][1]+r;
													
													if(s1-c>0){
														prior_i = Math.abs(sort1_i[i][0]-sort2_i[s1-c][0]);
														prior_j = Math.abs(sort1_i[i][1]-sort2_i[s1-c][1]);
														}else{
															break;                 //跳出while循环
														}
													}
											//必定是大于0（1以上）的部分在比对，所以通过下面代码可除去其它冗余项
											int s;
											if(c1_i<=1){
												s=1;
												}else{
													s=c1_i;
													}
											int num=c3_i-s<sort2_i.length-s?c3_i-s:sort2_i.length-s;   //得到的需要比对的横轴个数(需要加1,或者=num)，这里c3_i-s可能会超出sort2_i的长度，股选取一个最小值。
											int A1[]=new int[num+1];
										    int A2[]=new int[num+1];
										    int max1[]=new int[num+1];
										    	
										    int h=s-1;
										    //System.out.println("h"+"="+h);   ..........
										    	
										    int l=0;       //计数器，记录S2中有几个数进行了比对
										    for(int a=0;a<=num;a++){      //等于num是因为5-1=4，从一到五其实有五个数，4-0=4，从0到4其实有5个数，需要比对5次
										    	
												if(sort2_i[h][0]>=c1_i && sort2_i[h][0]<=c3_i && sort2_i[h][1]>=c1_j && sort2_i[h][1]<=c3_j){   //s-1：因为数组从零开始，RNA中[0][0]即（1,n）
													//计算H距离sort1_i[i][j]和sort2_i
													A1[l]=Math.abs(sort1_i[i][0]-sort2_i[h][0]);                            
													//System.out.println("A1"+"["+l+"]"+"="+A1[l]);  .........
														
													A2[l]=Math.abs(sort1_i[i][1]-sort2_i[h][1]);
													//System.out.println("A2"+"["+l+"]"+"="+A2[l]); .............
														
													max1[l]=A1[l]>A2[l]?A1[l]:A2[l];
													l++;         //计数器，记录S2中有几个数进行了比对
													h++;
													}else{
														h++;
													}
												}
										    int temp=max1[0];
										    for(int b=0;b<l;b++){
										    	if(max1[b]<temp){
										    		temp=max1[b];
										    		}
										    	}
										    d[m]=temp; //得到最小值                  //将temp值存入min[]数组中
										    i++;
										    
										    }
										}
								}
							
							
							else if (a1 > a2){
								if(abs3 < abs4){
									d[m]=a2;                                    
									i++;
									
									}else{
										//System.out.println("r="+r);  
										r=Math.min(a1,a2); //得到目前矩形框半径      已知a1>a2$$$$$$$$$
										
										int c1_i=sort1_i[i][0]-r;
										int c1_j=sort1_i[i][1]-r;
										int c3_i=sort1_i[i][0]+r; 
										int c3_j=sort1_i[i][1]+r;

										int c=0;
										//System.out.println(sort2_j[s2][0] <= sort1_i[i][0]);
										if(sort2_j[s2][0] <= sort1_i[i][0]){                 //如果mini<i
											
											//System.out.println("按j且mini<=i");
											prior_i = abs3;
											prior_j = abs4;
											
											//向前找，递归找到最小r，以及其各个短点
											//System.out.println(c1_i<=prior_i && prior_i<=c3_i && c1_j<=prior_j && prior_j<=c3_j);
											while (c1_i<=sort2_j[s2-c][0] && sort2_j[s2-c][0]<=c3_i && c1_j<=sort2_j[s2-c][1] && sort2_j[s2-c][1]<=c3_j){
												
												int nr=Math.max(prior_i,prior_j);
												r=Math.min(r,nr);
												c++;
												
												//System.out.println("c="+c); 
												c1_i=sort1_i[i][0]-r;
												c1_j=sort1_i[i][1]-r;
												c3_i=sort1_i[i][0]+r; 
												c3_j=sort1_i[i][1]+r;
												
												if(s2-c>0){
													
													prior_i = Math.abs(sort1_i[i][0]-sort2_j[s2-c][0]);
													prior_j = Math.abs(sort1_i[i][1]-sort2_j[s2-c][1]);
													
													}else{
														break;
														}
												}
											
											//必定是大于0（1以上）的部分在比对，所以通过下面代码可除去其它冗余项
											int s;
											if(c1_i<=1){
												s=1;
											}else{
												s=c1_i;
												}
											int num=c3_i-s<sort2_i.length-s?c3_i-s:sort2_i.length-s;   //得到的需要比对的横轴个数(需要加1,或者=num)，这里c3_i-s可能会超出sort2_i的长度，股选取一个最小值。
											//a到num得值==s到c3_i-s，下面循环可以保证正好从s到c3_i为止（和从0到num值一样）
											
											
											int A1[]=new int[num+1];
									    	int A2[]=new int[num+1];
									    	int max1[]=new int[num+1];
									    	
									    	int h=s-1;
									    	//System.out.println("h"+"="+h);   ..........
									    	
									    	int l=0;       //计数器，记录S2中有几个数进行了比对
											for(int a=0;a<=num;a++){      //等于num是因为5-1=4，从一到五其实有五个数，4-0=4，从0到4其实有5个数，需要比对5次
												
												
												
												if(sort2_i[h][0]>=c1_i && sort2_i[h][0]<=c3_i && sort2_i[h][1]>=c1_j && sort2_i[h][1]<=c3_j){   //s-1：因为数组从零开始，RNA中[0][0]即（1,n）
													
													//计算H距离sort1_i[i][j]和sort2_i
													A1[l]=Math.abs(sort1_i[i][0]-sort2_i[h][0]);                            
													//System.out.println("A1"+"["+l+"]"+"="+A1[l]);  .........
													
													A2[l]=Math.abs(sort1_i[i][1]-sort2_i[h][1]);
													//System.out.println("A2"+"["+l+"]"+"="+A2[l]); .............
													
													max1[l]=A1[l]>A2[l]?A1[l]:A2[l];
													l++;         //计数器，记录S2中有几个数进行了比对
													
													h++;
												}else{
													h++;
												}
											}
											int temp=max1[0];
											
											for(int b=0;b<l;b++){
												if(max1[b]<temp){
													temp=max1[b];
												}
											}
											
											d[m]=temp; //得到最小值                  //将temp值存入min[]数组中
											
											i++;
										//}
									}
										
										
										else if(sort2_j[s2][0] > sort1_i[i][0]){                        //即minj>j
											
											
											next_i = abs3;
											next_j = abs4;
											while (c1_i<=sort2_j[s2+c][0] && sort2_j[s2+c][0]<=c3_i && c1_j<=sort2_j[s2+c][1] && sort2_j[s2+c][1]<=c3_j){
												int nr=Math.max(next_i,next_j);
												r=Math.min(r,nr);
												
												
												c++;
												
												
												c1_i=sort1_i[i][0]-r;                            //这里多计算了很多式子，将来优化
												c1_j=sort1_i[i][1]-r;
												c3_i=sort1_i[i][0]+r; 
												c3_j=sort1_i[i][1]+r;
												
												if(s2+c<sort2_j.length){
													
													next_i = Math.abs(sort1_i[i][0]-sort2_j[s2+c][0]);
													next_j = Math.abs(sort1_i[i][1]-sort2_j[s2+c][1]);
													
													}else{
														break;               //跳出while循环
													}
												}
											
											
												int s;
												if(c1_i<=1){
													s=1;
													}else{
														s=c1_i;
														}
												int num=c3_i-s<sort2_i.length-s?c3_i-s:sort2_i.length-s;   //得到的需要比对的横轴个数(需要加1,或者=num)，这里c3_i-s可能会超出sort2_i的长度，股选取一个最小值。
												//a到num得值==s到c3_i-s，下面循环可以保证正好从s到c3_i为止（和从0到num值一样）
												
												int A1[]=new int[num+1];
												int A2[]=new int[num+1];
												int max1[]=new int[num+1];
												int h=s-1;
								    	
												int l=0;       //计数器，记录S2中有几个数进行了比对
												for(int a=0;a<=num;a++){      //等于num是因为5-1=4，从一到五其实有五个数，4-0=4，从0到4其实有5个数，需要比对5次
													if(sort2_i[h][0]>=c1_i && sort2_i[h][0]<=c3_i && sort2_i[h][1]>=c1_j && sort2_i[h][1]<=c3_j){   //s-1：因为数组从零开始，RNA中[0][0]即（1,n）
														
														//计算H距离sort1_i[i][j]和sort2_i
														A1[l]=Math.abs(sort1_i[i][0]-sort2_i[h][0]);                            
												
														A2[l]=Math.abs(sort1_i[i][1]-sort2_i[h][1]);
												
														max1[l]=A1[l]>A2[l]?A1[l]:A2[l];
														//long startTime = System.currentTimeMillis();
												
														l++;         //计数器，记录S2中有几个数进行了比对
												
														h++;
														}else{
															h++;
														}
													}
												int temp=max1[0];
										
												for(int b=0;b<l;b++){
													if(max1[b]<temp){
														temp=max1[b];
														}
													}
												d[m]=temp; //得到最小值                  //将temp值存入min[]数组中
										
												i++;
												
												}
										}
							}

				}
			
				
				else if(sort1_i[i][0]>sort2_i[j][0]){
					
					Search search_i1=new Search();    //int[][] m=new Search().binarySearch(sort1_i[][]);
					int s3=search_i1.binarySearch_i(sort1_i,sort2_i[j][0]);   //s1值等于middle值或者-1
							
					Search search_j1=new Search();    //int[][] m=new Search().binarySearch(sort1_i[][]);
					int s4=search_j1.sequnceSearch_j(sort1_j,sort2_i[j][1]);   //s2值等于middle值或者-1
							
							
					int abs1=Math.abs(sort2_i[j][0]-sort1_i[s3][0]);
					int abs2=Math.abs(sort2_i[j][1]-sort1_i[s3][1]);
							
					int a3=abs1>abs2?abs1:abs2;
			    				
					int abs3=Math.abs(sort2_i[j][0]-sort1_j[s4][0]);
					int abs4=Math.abs(sort2_i[j][1]-sort1_j[s4][1]);
			    				
			    	int a4=abs3>abs4?abs3:abs4;
					
					int prior_i;
					int prior_j;
					int next_i;
					int next_j;
				
					if (a3 <= a4){                                                       //若选择按i排的
						
						if(abs1 > abs2){
							d[m]=a3;
							j++;
							
							}else{
								//System.out.println("进入-按i开始找的else");
							    r=Math.min(a3,a4); //得到目前矩形框半径	
								int c1_i=sort2_i[j][0]-r;
								int c1_j=sort2_i[j][1]-r;
								int c3_i=sort2_i[j][0]+r; 
								int c3_j=sort2_i[j][1]+r;
							
								int c=0;
								
								if(sort1_i[s3][1] > sort2_i[j][1]){                        //且minj>j
									
									next_i = abs1;
									next_j = abs2;
									while (c1_i<=sort1_i[s3+c][0] && sort1_i[s3+c][0]<=c3_i && c1_j<=sort1_i[s3+c][1] && sort1_i[s3+c][1]<=c3_j){
										int nr=Math.max(next_i,next_j);
										r=Math.min(r,nr);
										c++;
									
										c1_i=sort2_i[j][0]-r;                            //这里多计算了很多式子，将来优化
										c1_j=sort2_i[j][1]-r;
										c3_i=sort2_i[j][0]+r; 
										c3_j=sort2_i[j][1]+r;
										
										if(s3+c<sort1_i.length){
											
											next_i = Math.abs(sort2_i[j][0]-sort1_i[s3+c][0]);
											next_j = Math.abs(sort2_i[j][1]-sort1_i[s3+c][1]);
											}else{
												break;               //跳出while循环
											}
										}
									
									
									int s;
									if(c1_i<=1){
										s=1;
										}else{
											s=c1_i;
											}
									int num=c3_i-s<sort1_i.length-s?c3_i-s:sort1_i.length-s;   //得到的需要比对的横轴个数(需要加1,或者=num)，这里c3_i-s可能会超出sort1_i的长度，股选取一个最小值。
										
										
									int A1[]=new int[num+1];
								    int A2[]=new int[num+1];
								    int max1[]=new int[num+1];
								    int h=s-1;
								    	
								    int l=0;
									for(int a=0;a<=num;a++){      //等于num是因为5-1=4，从一到五其实有五个数，4-0=4，从0到4其实有5个数，需要比对5次
									if(sort1_i[h][0]>=c1_i && sort1_i[h][0]<=c3_i && sort1_i[h][1]>=c1_j && sort1_i[h][1]<=c3_j ){   //s-1：因为数组从零开始，RNA中[0][0]即（1,n）
										
										A1[l]=Math.abs(sort2_i[j][0]-sort1_i[h][0]);
										A2[l]=Math.abs(sort2_i[j][1]-sort1_i[h][1]);
										max1[l]=A1[l]>A2[l]?A1[l]:A2[l];
												
										l++;         //计数器，记录S2中有几个数进行了比对
										h++;
										}else{
											h++;
											}
									}
									int temp=max1[0];
									//System.out.println(temp);
									for(int b=0;b<l;b++){
										if(max1[b]<temp){
											temp=max1[b];	
											}
										//System.out.println("temp"+"="+temp);  ..........
										}
									d[m]=temp; //得到最小值                  //将temp值存入min[]数组中
									
					    			j++;
					    			}
								
								
								else if(sort1_i[s3][1] <= sort2_i[j][1]){                   //即minj<j
									
									
									prior_i = abs1; //i与第s3个i1的差的绝对值
									prior_j = abs2; //j与第s4个j1的差的绝对值
									//向前找，递归找到最小r，以及其各个短点
									while (c1_i<=sort1_i[s3-c][0] && sort1_i[s3-c][0]<=c3_i && c1_j<=sort1_i[s3-c][1] && sort1_i[s3-c][1]<=c3_j){
											
											int nr=Math.max(prior_i,prior_j);
											r=Math.min(r,nr);
											c++;
											//System.out.println("c="+c); 
											c1_i=sort2_i[j][0]-r;
											c1_j=sort2_i[j][1]-r;
											c3_i=sort2_i[j][0]+r; 
											c3_j=sort2_i[j][1]+r;
											
											if(s3-c>0){
												prior_i = Math.abs(sort2_i[j][0]-sort1_i[s3-c][0]);
												prior_j = Math.abs(sort2_i[j][1]-sort1_i[s3-c][1]);
												}else{
													break;                 //跳出while循环
												}
											}
									//必定是大于0（1以上）的部分在比对，所以通过下面代码可除去其它冗余项
									int s;
									if(c1_i<=1){
										s=1;
										}else{
											s=c1_i;
											}
									int num=c3_i-s<sort1_i.length-s?c3_i-s:sort1_i.length-s;   //得到的需要比对的横轴个数(需要加1,或者=num)，这里c3_i-s可能会超出sort1_i的长度，股选取一个最小值。
										
										
									int A1[]=new int[num+1];
								    int A2[]=new int[num+1];
								    int max1[]=new int[num+1];
								    int h=s-1;
								    //System.out.println("h"+"="+h);  ..........
								    	
								    int l=0;
									for(int a=0;a<=num;a++){      //等于num是因为5-1=4，从一到五其实有五个数，4-0=4，从0到4其实有5个数，需要比对5次
									if(sort1_i[h][0]>=c1_i && sort1_i[h][0]<=c3_i && sort1_i[h][1]>=c1_j && sort1_i[h][1]<=c3_j ){   //s-1：因为数组从零开始，RNA中[0][0]即（1,n）
										
										A1[l]=Math.abs(sort2_i[j][0]-sort1_i[h][0]);
										A2[l]=Math.abs(sort2_i[j][1]-sort1_i[h][1]);
										max1[l]=A1[l]>A2[l]?A1[l]:A2[l];
										//System.out.println("max1"+"["+l+"]"+"="+max1[l]);  ........
									    //min[j]=max1[j]<max1[j+1]?max1[j]:max1[j+1];       
										//System.out.println("max"+" "+max1[j]);
												
										l++;         //计数器，记录S2中有几个数进行了比对
										//System.out.println("l++++++++++++"+"="+l);  .......
										//System.out.print("\n");  ........
										h++;
										}else{
											h++;
											}
									}
									int temp=max1[0];
									//System.out.println(temp);
									for(int b=0;b<l;b++){
										if(max1[b]<temp){
											temp=max1[b];	
											}
										//System.out.println("temp"+"="+temp);  ..........
										}
									d[m]=temp; //得到最小值                  //将temp值存入min[]数组中
									
									//System.out.println("d"+"["+m+"]="+d[m]); 
					    				
					    		    //for(int s=0;s<d.length;s++){
					    			//System.out.println("d"+"["+m+"]"+"="+j);
					    			//}
					    			j++;
								    }
								}
						}
					
					
					
					else if (a3 > a4){
						//System.out.println("开始按j找");
						//System.out.println(abs3 < abs4);
						if(abs3 < abs4){
							d[m]=a4;                                    
							j++;
							
							}else{
								//System.out.println("r="+r);  
								r=Math.min(a3,a4); //得到目前矩形框半径      已知a1>a2$$$$$$$$$
								
								int c1_i=sort2_i[j][0]-r;
								int c1_j=sort2_i[j][1]-r;
								int c3_i=sort2_i[j][0]+r; 
								int c3_j=sort2_i[j][1]+r;

								int c=0;
								//System.out.println(sort2_j[s2][0] <= sort1_i[i][0]);
								if(sort1_j[s4][0] <= sort2_i[j][0]){                 //如果mini<i
									
									//System.out.println("按j且mini<=i");
									prior_i = abs3;
									prior_j = abs4;
									
									//向前找，递归找到最小r，以及其各个短点
									//System.out.println(c1_i<=prior_i && prior_i<=c3_i && c1_j<=prior_j && prior_j<=c3_j);
									while (c1_i<=sort1_j[s4-c][0] && sort1_j[s4-c][0]<=c3_i && c1_j<=sort1_j[s4-c][1] && sort1_j[s4-c][1]<=c3_j){
										
										int nr=Math.max(prior_i,prior_j);
										r=Math.min(r,nr);
										c++;
										
										//System.out.println("c="+c); 
										c1_i=sort2_i[j][0]-r;
										c1_j=sort2_i[j][1]-r;
										c3_i=sort2_i[j][0]+r; 
										c3_j=sort2_i[j][1]+r;
										
										if(s4-c>0){
											
											prior_i = Math.abs(sort2_i[j][0]-sort1_j[s4-c][0]);
											prior_j = Math.abs(sort2_i[j][1]-sort1_j[s4-c][1]);
											
											}else{
												break;
												}
										}
									
									
									//必定是大于0（1以上）的部分在比对，所以通过下面代码可除去其它冗余项
									int s;
									if(c1_i<=1){
										s=1;
										}else{
											s=c1_i;
											}
									int num=c3_i-s<sort1_i.length-s?c3_i-s:sort1_i.length-s;   //得到的需要比对的横轴个数(需要加1,或者=num)，这里c3_i-s可能会超出sort1_i的长度，股选取一个最小值。
									//System.out.println("num---3"+"="+num);
									//a到num得值==s到c3_i-s，下面循环可以保证正好从s到c3_i为止（和从0到num值一样）
										
										
									int A1[]=new int[num+1];
								    int A2[]=new int[num+1];
								    int max1[]=new int[num+1];
								    int h=s-1;
								    //System.out.println("h"+"="+h);  ..........
								    	
								    int l=0;
									for(int a=0;a<=num;a++){      //等于num是因为5-1=4，从一到五其实有五个数，4-0=4，从0到4其实有5个数，需要比对5次
									//System.out.println("sort1_i"+"["+h+"]"+"[0]"+"="+sort1_i[h][0]);  .......
									//System.out.println("sort1_i"+"["+h+"]"+"[1]"+"="+sort1_i[h][1]);   .........
									if(sort1_i[h][0]>=c1_i && sort1_i[h][0]<=c3_i && sort1_i[h][1]>=c1_j && sort1_i[h][1]<=c3_j ){   //s-1：因为数组从零开始，RNA中[0][0]即（1,n）
										
										//计算H距离sort1_i[i][j]和sort2_i
										A1[l]=Math.abs(sort2_i[j][0]-sort1_i[h][0]);
										A2[l]=Math.abs(sort2_i[j][1]-sort1_i[h][1]);
										max1[l]=A1[l]>A2[l]?A1[l]:A2[l];
										//System.out.println("max1"+"["+l+"]"+"="+max1[l]);  ........
									    //min[j]=max1[j]<max1[j+1]?max1[j]:max1[j+1];       
										//System.out.println("max"+" "+max1[j]);
												
										l++;         //计数器，记录S2中有几个数进行了比对
										//System.out.println("l++++++++++++"+"="+l);  .......
										//System.out.print("\n");  ........
										h++;
										}else{
											h++;
											}
									}
									int temp=max1[0];
									//System.out.println(temp);
									for(int b=0;b<l;b++){
										if(max1[b]<temp){
											temp=max1[b];	
											}
										//System.out.println("temp"+"="+temp);  ..........
										}
									d[m]=temp; //得到最小值                  //将temp值存入min[]数组中
									
									//System.out.println("d"+"["+m+"]="+d[m]); 
					    				
					    		    //for(int s=0;s<d.length;s++){
					    			//System.out.println("d"+"["+m+"]"+"="+j);
					    			//}
					    			j++;
									//System.out.println("j = "+j);   
								//}
							}
								
								
								else if(sort1_j[s4][0] > sort2_i[j][0]){                        //即minj>j
									
									//System.out.println("按j且mini>i");
									
									next_i = abs3;
									next_j = abs4;
									while (c1_i<=sort1_j[s4+c][0] && sort1_j[s4+c][0]<=c3_i && c1_j<=sort1_j[s4+c][1] && sort1_j[s4+c][1]<=c3_j){
										
										
										int nr=Math.max(next_i,next_j);
										r=Math.min(r,nr);
										
										//System.out.println("r="+r);
										
										c++;
										
										//System.out.println("c="+c);
										
										c1_i=sort2_i[j][0]-r;                            //这里多计算了很多式子，将来优化
										c1_j=sort2_i[j][1]-r;
										c3_i=sort2_i[j][0]+r; 
										c3_j=sort2_i[j][1]+r;
										
										//System.out.println("c1_i="+c1_i+",c1_j="+c1_j+",c3_i="+c3_i+",c3_j="+c3_j);
										//System.out.println(s2+c<sort2_j.length);
										if(s4+c<sort1_j.length){
											
											next_i = Math.abs(sort2_i[j][0]-sort1_j[s4+c][0]);
											next_j = Math.abs(sort2_i[j][1]-sort1_j[s4+c][1]);
											
											//System.out.println("next_i="+next_i+",next_j="+next_j);
											//System.out.println();
											
											}else{
												break;               //跳出while循环
											}
										}
									
									
									//必定是大于0（1以上）的部分在比对，所以通过下面代码可除去其它冗余项
									int s;
									if(c1_i<=1){
										s=1;
										}else{
											s=c1_i;
											}
									int num=c3_i-s<sort1_i.length-s?c3_i-s:sort1_i.length-s;   //得到的需要比对的横轴个数(需要加1,或者=num)，这里c3_i-s可能会超出sort1_i的长度，股选取一个最小值。
									//System.out.println("num---4"+"="+num);
									//a到num得值==s到c3_i-s，下面循环可以保证正好从s到c3_i为止（和从0到num值一样）
										
										
									int A1[]=new int[num+1];
								    int A2[]=new int[num+1];
								    int max1[]=new int[num+1];
								    int h=s-1;
								    //System.out.println("h"+"="+h);  ..........
								    	
								    int l=0;
									for(int a=0;a<=num;a++){      //等于num是因为5-1=4，从一到五其实有五个数，4-0=4，从0到4其实有5个数，需要比对5次
									//System.out.println("sort1_i"+"["+h+"]"+"[0]"+"="+sort1_i[h][0]);  .......
									//System.out.println("sort1_i"+"["+h+"]"+"[1]"+"="+sort1_i[h][1]);   .........
									if(sort1_i[h][0]>=c1_i && sort1_i[h][0]<=c3_i && sort1_i[h][1]>=c1_j && sort1_i[h][1]<=c3_j ){   //s-1：因为数组从零开始，RNA中[0][0]即（1,n）
										
										//计算H距离sort1_i[i][j]和sort2_i
										A1[l]=Math.abs(sort2_i[j][0]-sort1_i[h][0]);
										A2[l]=Math.abs(sort2_i[j][1]-sort1_i[h][1]);
										max1[l]=A1[l]>A2[l]?A1[l]:A2[l];
										//System.out.println("max1"+"["+l+"]"+"="+max1[l]);  ........
									    //min[j]=max1[j]<max1[j+1]?max1[j]:max1[j+1];       
										//System.out.println("max"+" "+max1[j]);
												
										l++;         //计数器，记录S2中有几个数进行了比对
										//System.out.println("l++++++++++++"+"="+l);  .......
										//System.out.print("\n");  ........
										h++;
										}else{
											h++;
											}
									}
									int temp=max1[0];
									//System.out.println(temp);
									for(int b=0;b<l;b++){
										if(max1[b]<temp){
											temp=max1[b];	
											}
										//System.out.println("temp"+"="+temp);  ..........
										}
									d[m]=temp; //得到最小值                  //将temp值存入min[]数组中
									
									//System.out.println("d"+"["+m+"]="+d[m]); 
					    				
					    		    //for(int s=0;s<d.length;s++){
					    			//System.out.println("d"+"["+m+"]"+"="+j);
					    			//}
					    			j++;
									//System.out.println("j = "+j); 
										
										}
								}
					}

			    	
			    	
			    	
			    	
					}
			}
			
			
			else if(i==sort1_i.length && j<sort2_i.length){
				
				//System.out.println("else if-1执行");
				
				//Search search_i1=new Search();    //int[][] m=new Search().binarySearch(sort1_i[][]);
	    		//int s5=search_i1.binarySearch_i(sort1_i,sort2_i[j][0]);   //s1值等于middle值或者-1
	    		int s5=sort1_i.length-1;                                    //因为i已经到sort1_i的头
	    		//System.out.println("s5 = "+s5);        
	    		
	    		Search search_j1=new Search();    //int[][] m=new Search().binarySearch(sort1_i[][]);
	    		int s6=search_j1.sequnceSearch_j(sort1_j,sort2_i[j][1]);   //s2值等于middle值或者-1
	    		//System.out.println("s6 = "+s6);    
	    		
	    		//System.out.println("search_i"+"["+m+"]"+ "="+"("+sort1_i[s5][0]+","+sort1_i[s5][1]+")"); 
	    		//System.out.println("search_j"+"["+m+"]"+ "="+"("+sort1_j[s6][0]+","+sort1_j[s6][1]+")"); 
	    		int abs1=Math.abs(sort2_i[j][0]-sort1_i[s5][0]);
	    		int abs2=Math.abs(sort2_i[j][1]-sort1_i[s5][1]);
	    		
	    		int a3=abs1>abs2?abs1:abs2;
	    		
	    		int abs3=Math.abs(sort2_i[j][0]-sort1_j[s6][0]);
	    		int abs4=Math.abs(sort2_i[j][1]-sort1_j[s6][1]);
	    		
	    		int a4=abs3>abs4?abs3:abs4;
	    	
	    		
	    		
	    		
	    		
		    	
				
				int prior_i;
				int prior_j;
				int next_i;
				int next_j;
			
				//计算H距离
				if (a3 <= a4){                                                       //若选择按i排的
					
					//System.out.println("按i开始找");
					
					if(abs1 > abs2){
						//System.out.println("按i开始找,且mini>minj,直接得到结果");
						d[m]=a3;
						j++;
						//System.out.println("j = "+j);  
						
						}else{
							//System.out.println("进入-按i开始找的else");
						    r=Math.min(a3,a4); //得到目前矩形框半径	
							int c1_i=sort2_i[j][0]-r;
							int c1_j=sort2_i[j][1]-r;
							int c3_i=sort2_i[j][0]+r; 
							int c3_j=sort2_i[j][1]+r;
						
							int c=0;
							
							if(sort1_i[s5][1] > sort2_i[j][1]){                        //且minj>j
								
								//System.out.println("按j且minj>j");
								
								next_i = abs1;
								next_j = abs2;
							//向后找，递归找到最小r，以及其各个短点
							//System.out.println(c1_i<next_i && next_i<c3_i && c1_j<next_j && next_j<c3_j);
								while (c1_i<=sort1_i[s5+c][0] && sort1_i[s5+c][0]<=c3_i && c1_j<=sort1_i[s5+c][1] && sort1_i[s5+c][1]<=c3_j){
									int nr=Math.max(next_i,next_j);
									r=Math.min(r,nr);
									c++;
								
									c1_i=sort2_i[j][0]-r;                            //这里多计算了很多式子，将来优化
									c1_j=sort2_i[j][1]-r;
									c3_i=sort2_i[j][0]+r; 
									c3_j=sort2_i[j][1]+r;
									
									if(s5+c<sort1_i.length){
										
										next_i = Math.abs(sort2_i[j][0]-sort1_i[s5+c][0]);
										next_j = Math.abs(sort2_i[j][1]-sort1_i[s5+c][1]);
										}else{
											break;               //跳出while循环
										}
									}
								
								
								//必定是大于0（1以上）的部分在比对，所以通过下面代码可除去其它冗余项
								int s;
								if(c1_i<=1){
									s=1;
									}else{
										s=c1_i;
										}
								int num=c3_i-s<sort1_i.length-s?c3_i-s:sort1_i.length-s;   //得到的需要比对的横轴个数(需要加1,或者=num)，这里c3_i-s可能会超出sort1_i的长度，股选取一个最小值。
								//System.out.println("num"+"="+num);   ...........
								//a到num得值==s到c3_i-s，下面循环可以保证正好从s到c3_i为止（和从0到num值一样）
									
									
								int A1[]=new int[num+1];
							    int A2[]=new int[num+1];
							    int max1[]=new int[num+1];
							    int h=s-1;
							    //System.out.println("h"+"="+h);  ..........
							    	
							    int l=0;
								for(int a=0;a<=num;a++){      //等于num是因为5-1=4，从一到五其实有五个数，4-0=4，从0到4其实有5个数，需要比对5次
								//System.out.println("sort1_i"+"["+h+"]"+"[0]"+"="+sort1_i[h][0]);  .......
								//System.out.println("sort1_i"+"["+h+"]"+"[1]"+"="+sort1_i[h][1]);   .........
								if(sort1_i[h][0]>=c1_i && sort1_i[h][0]<=c3_i && sort1_i[h][1]>=c1_j && sort1_i[h][1]<=c3_j ){   //s-1：因为数组从零开始，RNA中[0][0]即（1,n）
									
									//计算H距离sort1_i[i][j]和sort2_i
									A1[l]=Math.abs(sort2_i[j][0]-sort1_i[h][0]);
									A2[l]=Math.abs(sort2_i[j][1]-sort1_i[h][1]);
									max1[l]=A1[l]>A2[l]?A1[l]:A2[l];
									//System.out.println("max1"+"["+l+"]"+"="+max1[l]);  ........
								    //min[j]=max1[j]<max1[j+1]?max1[j]:max1[j+1];       
									//System.out.println("max"+" "+max1[j]);
											
									l++;         //计数器，记录S2中有几个数进行了比对
									//System.out.println("l++++++++++++"+"="+l);  .......
									//System.out.print("\n");  ........
									h++;
									}else{
										h++;
										}
								}
								int temp=max1[0];
								//System.out.println(temp);
								for(int b=0;b<l;b++){
									if(max1[b]<temp){
										temp=max1[b];	
										}
									//System.out.println("temp"+"="+temp);  ..........
									}
								d[m]=temp; //得到最小值                  //将temp值存入min[]数组中
								
								//System.out.println("d"+"["+m+"]="+d[m]); 
				    				
				    		    //for(int s=0;s<d.length;s++){
				    			//System.out.println("d"+"["+m+"]"+"="+j);
				    			//}
				    			j++;
				    			}
							
							
							else if(sort1_i[s5][1] <= sort2_i[j][1]){                   //即minj<j
								
								//System.out.println("按j且minj<=j");
								
								prior_i = abs1; //i与第s3个i1的差的绝对值
								prior_j = abs2; //j与第s4个j1的差的绝对值
								//向前找，递归找到最小r，以及其各个短点
								while (c1_i<=sort1_i[s5-c][0] && sort1_i[s5-c][0]<=c3_i && c1_j<=sort1_i[s5-c][1] && sort1_i[s5-c][1]<=c3_j){
										
										int nr=Math.max(prior_i,prior_j);
										r=Math.min(r,nr);
										
										//System.out.println("r"+"="+r);
										c++;
										//System.out.println("c="+c); 
										c1_i=sort2_i[j][0]-r;
										c1_j=sort2_i[j][1]-r;
										c3_i=sort2_i[j][0]+r; 
										c3_j=sort2_i[j][1]+r;
										
										if(s5-c>0){
											prior_i = Math.abs(sort2_i[j][0]-sort1_i[s5-c][0]);
											prior_j = Math.abs(sort2_i[j][1]-sort1_i[s5-c][1]);
											}else{
												break;                 //跳出while循环
											}
										}
								//必定是大于0（1以上）的部分在比对，所以通过下面代码可除去其它冗余项
								//System.out.println("c3_i"+"="+c3_i);
								int s;
								if(c1_i<=1){
									s=1;
									}else{
										s=c1_i;
										//System.out.println("c1_i"+"="+c1_i);
										}
								int num=c3_i-s<sort1_i.length-s?c3_i-s:sort1_i.length-s;   //得到的需要比对的横轴个数(需要加1,或者=num)，这里c3_i-s可能会超出sort1_i的长度，股选取一个最小值。
								//System.out.println("num"+"="+num);
								//a到num得值==s到c3_i-s，下面循环可以保证正好从s到c3_i为止（和从0到num值一样）
									
								
								int A1[]=new int[num+1];
								//System.out.println("A1"+"="+A1.length);
							    int A2[]=new int[num+1];
							    int max1[]=new int[num+1];
							    int h=s-1;
							    //System.out.println("h"+"="+h);  ..........
							    	
							    int l=0;
								for(int a=0;a<=num;a++){      //等于num是因为5-1=4，从一到五其实有五个数，4-0=4，从0到4其实有5个数，需要比对5次
								//System.out.println("sort1_i"+"["+h+"]"+"[0]"+"="+sort1_i[h][0]);  .......
								//System.out.println("sort1_i"+"["+h+"]"+"[1]"+"="+sort1_i[h][1]);   .........
								if(sort1_i[h][0]>=c1_i && sort1_i[h][0]<=c3_i && sort1_i[h][1]>=c1_j && sort1_i[h][1]<=c3_j ){   //s-1：因为数组从零开始，RNA中[0][0]即（1,n）
									
									//计算H距离sort1_i[i][j]和sort2_i
									A1[l]=Math.abs(sort2_i[j][0]-sort1_i[h][0]);
									A2[l]=Math.abs(sort2_i[j][1]-sort1_i[h][1]);
									max1[l]=A1[l]>A2[l]?A1[l]:A2[l];
									//System.out.println("max1"+"["+l+"]"+"="+max1[l]);  ........
								    //min[j]=max1[j]<max1[j+1]?max1[j]:max1[j+1];       
									//System.out.println("max"+" "+max1[j]);
											
									l++;         //计数器，记录S2中有几个数进行了比对
									//System.out.println("l++++++++++++"+"="+l);  .......
									//System.out.print("\n");  ........
									h++;
									}else{
										h++;
										}
								}
								int temp=max1[0];
								//System.out.println(temp);
								for(int b=0;b<l;b++){
									if(max1[b]<temp){
										temp=max1[b];	
										}
									//System.out.println("temp"+"="+temp);  ..........
									}
								d[m]=temp; //得到最小值                  //将temp值存入min[]数组中
								
								//System.out.println("d"+"["+m+"]="+d[m]); 
				    				
				    		    //for(int s=0;s<d.length;s++){
				    			//System.out.println("d"+"["+m+"]"+"="+j);
				    			//}
				    			j++;
							    }
							}
					}
				
				
				
				else if (a3 > a4){
					//System.out.println("开始按j找");
					//System.out.println(abs3 < abs4);
					if(abs3 < abs4){
						d[m]=a4;                                    
						j++;
						
						}else{
							//System.out.println("r="+r);  
							r=Math.min(a3,a4); //得到目前矩形框半径      已知a1>a2$$$$$$$$$
							
							int c1_i=sort2_i[j][0]-r;
							int c1_j=sort2_i[j][1]-r;
							int c3_i=sort2_i[j][0]+r; 
							int c3_j=sort2_i[j][1]+r;

							int c=0;
							//System.out.println(sort2_j[s2][0] <= sort1_i[i][0]);
							if(sort1_j[s6][0] <= sort2_i[j][0]){                 //如果mini<i
								
								//System.out.println("按j且mini<=i");
								prior_i = abs3;
								prior_j = abs4;
								
								//向前找，递归找到最小r，以及其各个短点
								//System.out.println(c1_i<=prior_i && prior_i<=c3_i && c1_j<=prior_j && prior_j<=c3_j);
								while (c1_i<=sort1_j[s6-c][0] && sort1_j[s6-c][0]<=c3_i && c1_j<=sort1_j[s6-c][1] && sort1_j[s6-c][1]<=c3_j){
									
									int nr=Math.max(prior_i,prior_j);
									r=Math.min(r,nr);
									c++;
									
									//System.out.println("c="+c); 
									c1_i=sort2_i[j][0]-r;
									c1_j=sort2_i[j][1]-r;
									c3_i=sort2_i[j][0]+r; 
									c3_j=sort2_i[j][1]+r;
									
									if(s6-c>0){
										
										prior_i = Math.abs(sort2_i[j][0]-sort1_j[s6-c][0]);
										prior_j = Math.abs(sort2_i[j][1]-sort1_j[s6-c][1]);
										
										}else{
											break;
											}
									}
								
								
								//必定是大于0（1以上）的部分在比对，所以通过下面代码可除去其它冗余项
								int s;
								if(c1_i<=1){
									s=1;
									}else{
										s=c1_i;
										}
								int num=c3_i-s<sort1_i.length-s?c3_i-s:sort1_i.length-s;   //得到的需要比对的横轴个数(需要加1,或者=num)，这里c3_i-s可能会超出sort1_i的长度，股选取一个最小值。
								//System.out.println("num"+"="+num);   ...........
								//a到num得值==s到c3_i-s，下面循环可以保证正好从s到c3_i为止（和从0到num值一样）
									
									
								int A1[]=new int[num+1];
							    int A2[]=new int[num+1];
							    int max1[]=new int[num+1];
							    int h=s-1;
							    //System.out.println("h"+"="+h);  ..........
							    	
							    int l=0;
								for(int a=0;a<=num;a++){      //等于num是因为5-1=4，从一到五其实有五个数，4-0=4，从0到4其实有5个数，需要比对5次
								if(sort1_i[h][0]>=c1_i && sort1_i[h][0]<=c3_i && sort1_i[h][1]>=c1_j && sort1_i[h][1]<=c3_j ){   //s-1：因为数组从零开始，RNA中[0][0]即（1,n）
									
									//计算H距离sort1_i[i][j]和sort2_i
									A1[l]=Math.abs(sort2_i[j][0]-sort1_i[h][0]);
									A2[l]=Math.abs(sort2_i[j][1]-sort1_i[h][1]);
									max1[l]=A1[l]>A2[l]?A1[l]:A2[l];
											
									l++;         //计数器，记录S2中有几个数进行了比对
									h++;
									}else{
										h++;
										}
								}
								int temp=max1[0];
								//System.out.println(temp);
								for(int b=0;b<l;b++){
									if(max1[b]<temp){
										temp=max1[b];	
										}
									}
								d[m]=temp; //得到最小值                  //将temp值存入min[]数组中
								
				    			j++;
						}
							
							
							else if(sort1_j[s6][0] > sort2_i[j][0]){                        //即minj>j
								
								//System.out.println("按j且mini>i");
								
								next_i = abs3;
								next_j = abs4;
							//向后找，递归找到最小r，以及其各个短点
								while (c1_i<=sort1_j[s6+c][0] && sort1_j[s6+c][0]<=c3_i && c1_j<=sort1_j[s6+c][1] && sort1_j[s6+c][1]<=c3_j){
									
									
									int nr=Math.max(next_i,next_j);
									r=Math.min(r,nr);
									c++;
									
									c1_i=sort2_i[j][0]-r;                            //这里多计算了很多式子，将来优化
									c1_j=sort2_i[j][1]-r;
									c3_i=sort2_i[j][0]+r; 
									c3_j=sort2_i[j][1]+r;
									
									if(s6+c<sort1_j.length){
										
										next_i = Math.abs(sort2_i[j][0]-sort1_j[s6+c][0]);
										next_j = Math.abs(sort2_i[j][1]-sort1_j[s6+c][1]);
										
										}else{
											break;               //跳出while循环
										}
									}
								
								
								//必定是大于0（1以上）的部分在比对，所以通过下面代码可除去其它冗余项
								int s;
								if(c1_i<=1){
									s=1;
									}else{
										s=c1_i;
										}
								int num=c3_i-s<sort1_i.length-s?c3_i-s:sort1_i.length-s;   //得到的需要比对的横轴个数(需要加1,或者=num)，这里c3_i-s可能会超出sort1_i的长度，股选取一个最小值。
								//System.out.println("num"+"="+num);   ...........
								//a到num得值==s到c3_i-s，下面循环可以保证正好从s到c3_i为止（和从0到num值一样）
									
									
								int A1[]=new int[num+1];
							    int A2[]=new int[num+1];
							    int max1[]=new int[num+1];
							    int h=s-1;
							    //System.out.println("h"+"="+h);  ..........
							    	
							    int l=0;
								for(int a=0;a<=num;a++){      //等于num是因为5-1=4，从一到五其实有五个数，4-0=4，从0到4其实有5个数，需要比对5次
								if(sort1_i[h][0]>=c1_i && sort1_i[h][0]<=c3_i && sort1_i[h][1]>=c1_j && sort1_i[h][1]<=c3_j ){   //s-1：因为数组从零开始，RNA中[0][0]即（1,n）
									
									//计算H距离sort1_i[i][j]和sort2_i
									A1[l]=Math.abs(sort2_i[j][0]-sort1_i[h][0]);
									A2[l]=Math.abs(sort2_i[j][1]-sort1_i[h][1]);
									max1[l]=A1[l]>A2[l]?A1[l]:A2[l];
									//System.out.println("max1"+"["+l+"]"+"="+max1[l]);  ........
								    //min[j]=max1[j]<max1[j+1]?max1[j]:max1[j+1];       
									//System.out.println("max"+" "+max1[j]);
											
									l++;         //计数器，记录S2中有几个数进行了比对
									h++;
									}else{
										h++;
										}
								}
								int temp=max1[0];
								for(int b=0;b<l;b++){
									if(max1[b]<temp){
										temp=max1[b];	
										}
									}
								d[m]=temp; //得到最小值                  //将temp值存入min[]数组中
								
				    			j++;
									
									}
							}
				}
	    		
			}
			
			
			
			else if(i<sort1_i.length && j==sort2_i.length){
				
	    		//int s7=search_i.binarySearch_i(sort2_i,sort1_i[i][0]);   //s1值等于middle值或者-1
	    		int s7=sort2_i.length-1;   //因为sort2_i已到头
	    		
	    		Search search_j=new Search();
	    		int s8=search_j.sequnceSearch_j(sort2_j,sort1_i[i][1]);   //s2值等于middle值或者-1
	    		
	    		int abs1=Math.abs(sort1_i[i][0]-sort2_i[s7][0]);
	    		int abs2=Math.abs(sort1_i[i][1]-sort2_i[s7][1]);
	    		
	    		int a1=abs1>abs2?abs1:abs2;
	    		
	    		int abs3=Math.abs(sort1_i[i][0]-sort2_j[s8][0]);
	    		int abs4=Math.abs(sort1_i[i][1]-sort2_j[s8][1]);
	    		
	    		int a2=abs3>abs4?abs3:abs4;
	    		
	    		
	    		
	    		
				
				int prior_i;
				int prior_j;
				int next_i;
				int next_j;
			
				//计算H距离
				if (a1 <= a2){
					
					//System.out.println("按i开始找");
					
					if(abs1 > abs2){
						//System.out.println("按i开始找,且mini>minj,直接得到结果");
						//System.out.println("+++++++++++++++++++++++");
						d[m]=a1;
						i++;
						//System.out.println("i = "+i);  
						
						}else{
							//System.out.println("进入-按i开始找的else");
						    r=Math.min(a1,a2); //得到目前矩形框半径	
							int c1_i=sort1_i[i][0]-r;
							int c1_j=sort1_i[i][1]-r;
							int c3_i=sort1_i[i][0]+r; 
							int c3_j=sort1_i[i][1]+r;
						
							int c=0;
							
							if(sort2_i[s7][1] > sort1_i[i][1]){                        //即minj>j
								
								//System.out.println("按j且minj>j");
								
								next_i = abs1;
								next_j = abs2;
							//向后找，递归找到最小r，以及其各个短点
							//System.out.println(c1_i<next_i && next_i<c3_i && c1_j<next_j && next_j<c3_j);
								while (c1_i<=sort2_i[s7+c][0] && sort2_i[s7+c][0]<=c3_i && c1_j<=sort2_i[s7+c][1] && sort2_i[s7+c][1]<=c3_j){
									int nr=Math.max(next_i,next_j);
									r=Math.min(r,nr);
									c++;
								
									c1_i=sort1_i[i][0]-r;                            //这里多计算了很多式子，将来优化
									c1_j=sort1_i[i][1]-r;
									c3_i=sort1_i[i][0]+r; 
									c3_j=sort1_i[i][1]+r;
									
									if(s7+c<sort2_i.length){
										
										next_i = Math.abs(sort1_i[i][0]-sort2_i[s7+c][0]);
										next_j = Math.abs(sort1_i[i][1]-sort2_i[s7+c][1]);
										}else{
											break;               //跳出while循环
										}
									}
									//必定是大于0（1以上）的部分在比对，所以通过下面代码可除去其它冗余项
									int s;
									if(c1_i<=1){
										s=1;
										}else{
											s=c1_i;
											}
									int num=c3_i-s<sort2_i.length-s?c3_i-s:sort2_i.length-s;   //得到的需要比对的横轴个数(需要加1,或者=num)，这里c3_i-s可能会超出sort2_i的长度，股选取一个最小值。
									//System.out.println("num"+"="+num);  ..................................
									//System.out.println("sort2_i[s-1][0]"+"="+sort2_i[s-1][0]);
									//System.out.println("sort2_i[s-1][1]"+"="+sort2_i[s-1][1]);
									//a到num得值==s到c3_i-s，下面循环可以保证正好从s到c3_i为止（和从0到num值一样）
									
									int A1[]=new int[num+1];
									int A2[]=new int[num+1];
									int max1[]=new int[num+1];
									int h=s-1;
									//System.out.println("h"+"="+h);   ..........
					    	
									int l=0;       //计数器，记录S2中有几个数进行了比对
									for(int a=0;a<=num;a++){      //等于num是因为5-1=4，从一到五其实有五个数，4-0=4，从0到4其实有5个数，需要比对5次
										
										//System.out.println("sort2_i"+"["+h+"]"+"[0]"+"="+sort2_i[h][0]);    ........
										//System.out.println("sort2_i"+"["+h+"]"+"[1]"+"="+sort2_i[h][1]);        ...........
								
										//System.out.println(sort2_i[h][0]>=c1_i && sort2_i[h][0]<=c3_i && sort2_i[h][1]>=c1_j && sort2_i[h][1]<=c3_j );  ........
										if(sort2_i[h][0]>=c1_i && sort2_i[h][0]<=c3_i && sort2_i[h][1]>=c1_j && sort2_i[h][1]<=c3_j){   //s-1：因为数组从零开始，RNA中[0][0]即（1,n）
											
											//计算H距离sort1_i[i][j]和sort2_i
											A1[l]=Math.abs(sort1_i[i][0]-sort2_i[h][0]);                            
											//System.out.println("A1"+"["+l+"]"+"="+A1[l]);  .........
									
											A2[l]=Math.abs(sort1_i[i][1]-sort2_i[h][1]);
											//System.out.println("A2"+"["+l+"]"+"="+A2[l]); .............
									
											max1[l]=A1[l]>A2[l]?A1[l]:A2[l];
											//System.out.println("max1"+"["+l+"]"+"="+max1[l]);  .........
											//min[j]=max1[j]<max1[j+1]?max1[j]:max1[j+1];       
											//System.out.println("max"+" "+max1[j]);
											//long startTime = System.currentTimeMillis();
									
											l++;         //计数器，记录S2中有几个数进行了比对
									
											//long endTime = System.currentTimeMillis();
											//System.out.println("程序运行时间："+(endTime-startTime)+"ms");
											//System.out.println("l++++++++++++"+"="+l);   .............
											//System.out.print("\n"); ...........
											h++;
											}else{
												h++;
											}
										}
									int temp=max1[0];
							
									for(int b=0;b<l;b++){
										if(max1[b]<temp){
											temp=max1[b];
											}
										//System.out.println("temp"+"="+temp); 
										}
									d[m]=temp; //得到最小值                  //将temp值存入min[]数组中
									//System.out.println("d"+"["+m+"]="+d[m]); 
							
									i++;
									//System.out.println("i = "+i);   
									
									}
							else if(sort2_i[s7][1] <= sort1_i[i][1]){                   //即minj<j
								
								//System.out.println("按j且minj<=j");
								
								prior_i = abs1; //i与第s1-1个i1的差的绝对值
								prior_j = abs2; //j与第s1-1个j1的差的绝对值
								//向前找，递归找到最小r，以及其各个短点
								while (c1_i<=sort2_i[s7-c][0] && sort2_i[s7-c][0]<=c3_i && c1_j<=sort2_i[s7-c][1] && sort2_i[s7-c][1]<=c3_j){
										
										int nr=Math.max(prior_i,prior_j);
										r=Math.min(r,nr);
										c++;
										//System.out.println("c="+c); 
										c1_i=sort1_i[i][0]-r;
										c1_j=sort1_i[i][1]-r;
										c3_i=sort1_i[i][0]+r; 
										c3_j=sort1_i[i][1]+r;
										
										if(s7-c>0){
											prior_i = Math.abs(sort1_i[i][0]-sort2_i[s7-c][0]);
											prior_j = Math.abs(sort1_i[i][1]-sort2_i[s7-c][1]);
											}else{
												break;                 //跳出while循环
											}
										}
								//必定是大于0（1以上）的部分在比对，所以通过下面代码可除去其它冗余项
								int s;
								if(c1_i<=1){
									s=1;
									}else{
										s=c1_i;
										}
								int num=c3_i-s<sort2_i.length-s?c3_i-s:sort2_i.length-s;   //得到的需要比对的横轴个数(需要加1,或者=num)，这里c3_i-s可能会超出sort2_i的长度，股选取一个最小值。
								//System.out.println("num"+"="+num);  ..................................
								//System.out.println("sort2_i[s-1][0]"+"="+sort2_i[s-1][0]);
								//System.out.println("sort2_i[s-1][1]"+"="+sort2_i[s-1][1]);
								//a到num得值==s到c3_i-s，下面循环可以保证正好从s到c3_i为止（和从0到num值一样）
								int A1[]=new int[num+1];
							    int A2[]=new int[num+1];
							    int max1[]=new int[num+1];
							    	
							    int h=s-1;
							    //System.out.println("h"+"="+h);   ..........
							    	
							    int l=0;       //计数器，记录S2中有几个数进行了比对
							    for(int a=0;a<=num;a++){      //等于num是因为5-1=4，从一到五其实有五个数，4-0=4，从0到4其实有5个数，需要比对5次
							    	
							    	//System.out.println("sort2_i"+"["+h+"]"+"[0]"+"="+sort2_i[h][0]);    ........
									//System.out.println("sort2_i"+"["+h+"]"+"[1]"+"="+sort2_i[h][1]);        ...........
										
									//System.out.println(sort2_i[h][0]>=c1_i && sort2_i[h][0]<=c3_i && sort2_i[h][1]>=c1_j && sort2_i[h][1]<=c3_j );  ........
									if(sort2_i[h][0]>=c1_i && sort2_i[h][0]<=c3_i && sort2_i[h][1]>=c1_j && sort2_i[h][1]<=c3_j){   //s-1：因为数组从零开始，RNA中[0][0]即（1,n）
										//计算H距离sort1_i[i][j]和sort2_i
										A1[l]=Math.abs(sort1_i[i][0]-sort2_i[h][0]);                            
										//System.out.println("A1"+"["+l+"]"+"="+A1[l]);  .........
											
										A2[l]=Math.abs(sort1_i[i][1]-sort2_i[h][1]);
										//System.out.println("A2"+"["+l+"]"+"="+A2[l]); .............
											
										max1[l]=A1[l]>A2[l]?A1[l]:A2[l];
										//System.out.println("max1"+"["+l+"]"+"="+max1[l]);  .........
								        //min[j]=max1[j]<max1[j+1]?max1[j]:max1[j+1];       
										//System.out.println("max"+" "+max1[j]);
										//long startTime = System.currentTimeMillis();
											
										l++;         //计数器，记录S2中有几个数进行了比对
											
										//long endTime = System.currentTimeMillis();
										//System.out.println("程序运行时间："+(endTime-startTime)+"ms");
									    //System.out.println("l++++++++++++"+"="+l);   .............
										//System.out.print("\n"); ...........
										h++;
										}else{
											h++;
										}
									}
							    int temp=max1[0];
							    for(int b=0;b<l;b++){
							    	if(max1[b]<temp){
							    		temp=max1[b];
							    		}
							    	//System.out.println("temp"+"="+temp); 
							    	}
							    d[m]=temp; //得到最小值                  //将temp值存入min[]数组中
							    //System.out.println("d"+"["+m+"]="+d[m]); 
							    i++;
							    //System.out.println("i = "+i);   
							    
							    }
							}
					}
				
				
				else if (a1 > a2){
					//System.out.println("开始按j找");
					//System.out.println(abs3 < abs4);
					if(abs3 < abs4){
						d[m]=a2;                                    
						i++;
						
						}else{
							//System.out.println("r="+r);  
							r=Math.min(a1,a2); //得到目前矩形框半径      已知a1>a2$$$$$$$$$
							
							int c1_i=sort1_i[i][0]-r;
							int c1_j=sort1_i[i][1]-r;
							int c3_i=sort1_i[i][0]+r; 
							int c3_j=sort1_i[i][1]+r;

							int c=0;
							//System.out.println(sort2_j[s2][0] <= sort1_i[i][0]);
							if(sort2_j[s8][0] <= sort1_i[i][0]){                 //如果mini<i
								
								//System.out.println("按j且mini<=i");
								prior_i = abs3;
								prior_j = abs4;
								
								//向前找，递归找到最小r，以及其各个短点
								//System.out.println(c1_i<=prior_i && prior_i<=c3_i && c1_j<=prior_j && prior_j<=c3_j);
								while (c1_i<=sort2_j[s8-c][0] && sort2_j[s8-c][0]<=c3_i && c1_j<=sort2_j[s8-c][1] && sort2_j[s8-c][1]<=c3_j){
									
									int nr=Math.max(prior_i,prior_j);
									r=Math.min(r,nr);
									c++;
									
									//System.out.println("c="+c); 
									c1_i=sort1_i[i][0]-r;
									c1_j=sort1_i[i][1]-r;
									c3_i=sort1_i[i][0]+r; 
									c3_j=sort1_i[i][1]+r;
									
									if(s8-c>0){
										
										prior_i = Math.abs(sort1_i[i][0]-sort2_j[s8-c][0]);
										prior_j = Math.abs(sort1_i[i][1]-sort2_j[s8-c][1]);
										
										}else{
											break;
											}
									}
								
								//必定是大于0（1以上）的部分在比对，所以通过下面代码可除去其它冗余项
								int s;
								if(c1_i<=1){
									s=1;
								}else{
									s=c1_i;
									}
								int num=c3_i-s<sort2_i.length-s?c3_i-s:sort2_i.length-s;   //得到的需要比对的横轴个数(需要加1,或者=num)，这里c3_i-s可能会超出sort2_i的长度，股选取一个最小值。
								//System.out.println("num"+"="+num);  ..................................
								//System.out.println("sort2_i[s-1][0]"+"="+sort2_i[s-1][0]);
								//System.out.println("sort2_i[s-1][1]"+"="+sort2_i[s-1][1]);
								//a到num得值==s到c3_i-s，下面循环可以保证正好从s到c3_i为止（和从0到num值一样）
								
								
								int A1[]=new int[num+1];
						    	int A2[]=new int[num+1];
						    	int max1[]=new int[num+1];
						    	
						    	int h=s-1;
						    	//System.out.println("h"+"="+h);   ..........
						    	
						    	int l=0;       //计数器，记录S2中有几个数进行了比对
								for(int a=0;a<=num;a++){      //等于num是因为5-1=4，从一到五其实有五个数，4-0=4，从0到4其实有5个数，需要比对5次
									
									
									
									//System.out.println("sort2_i"+"["+h+"]"+"[0]"+"="+sort2_i[h][0]);    ........
									//System.out.println("sort2_i"+"["+h+"]"+"[1]"+"="+sort2_i[h][1]);        ...........
									
									//System.out.println(sort2_i[h][0]>=c1_i && sort2_i[h][0]<=c3_i && sort2_i[h][1]>=c1_j && sort2_i[h][1]<=c3_j );  ........
									if(sort2_i[h][0]>=c1_i && sort2_i[h][0]<=c3_i && sort2_i[h][1]>=c1_j && sort2_i[h][1]<=c3_j){   //s-1：因为数组从零开始，RNA中[0][0]即（1,n）
										
										//计算H距离sort1_i[i][j]和sort2_i
										A1[l]=Math.abs(sort1_i[i][0]-sort2_i[h][0]);                            
										//System.out.println("A1"+"["+l+"]"+"="+A1[l]);  .........
										
										A2[l]=Math.abs(sort1_i[i][1]-sort2_i[h][1]);
										//System.out.println("A2"+"["+l+"]"+"="+A2[l]); .............
										
										max1[l]=A1[l]>A2[l]?A1[l]:A2[l];
										//System.out.println("max1"+"["+l+"]"+"="+max1[l]);  .........
							            //min[j]=max1[j]<max1[j+1]?max1[j]:max1[j+1];       
										//System.out.println("max"+" "+max1[j]);
										//long startTime = System.currentTimeMillis();
										
										l++;         //计数器，记录S2中有几个数进行了比对
										
									    //long endTime = System.currentTimeMillis();
									    //System.out.println("程序运行时间："+(endTime-startTime)+"ms");
										//System.out.println("l++++++++++++"+"="+l);   .............
										//System.out.print("\n"); ...........
										h++;
									}else{
										h++;
									}
								}
								int temp=max1[0];
								
								for(int b=0;b<l;b++){
									if(max1[b]<temp){
										temp=max1[b];
									}
									//System.out.println("temp"+"="+temp); 
								}
								
								d[m]=temp; //得到最小值                  //将temp值存入min[]数组中
								//System.out.println("d"+"["+m+"]="+d[m]); 
								
								i++;
								//System.out.println("i = "+i);   
							//}
						}
							
							
							else if(sort2_j[s8][0] > sort1_i[i][0]){                        //即minj>j
								
								//System.out.println("按j且mini>i");
								
								next_i = abs3;
								next_j = abs4;
							//向后找，递归找到最小r，以及其各个短点
							//System.out.println(c1_i<next_i && next_i<c3_i && c1_j<next_j && next_j<c3_j);
								while (c1_i<=sort2_j[s8+c][0] && sort2_j[s8+c][0]<=c3_i && c1_j<=sort2_j[s8+c][1] && sort2_j[s8+c][1]<=c3_j){
									
									
									int nr=Math.max(next_i,next_j);
									r=Math.min(r,nr);
									c++;
									c1_i=sort1_i[i][0]-r;                            //这里多计算了很多式子，将来优化
									c1_j=sort1_i[i][1]-r;
									c3_i=sort1_i[i][0]+r; 
									c3_j=sort1_i[i][1]+r;
									
									if(s8+c<sort2_j.length){
										
										next_i = Math.abs(sort1_i[i][0]-sort2_j[s8+c][0]);
										next_j = Math.abs(sort1_i[i][1]-sort2_j[s8+c][1]);
										
										}else{
											break;               //跳出while循环
										}
									}
								
								
									//必定是大于0（1以上）的部分在比对，所以通过下面代码可除去其它冗余项
									int s;
									if(c1_i<=1){
										s=1;
										}else{
											s=c1_i;
											}
									int num=c3_i-s<sort2_i.length-s?c3_i-s:sort2_i.length-s;   //得到的需要比对的横轴个数(需要加1,或者=num)，这里c3_i-s可能会超出sort2_i的长度，股选取一个最小值。
									//a到num得值==s到c3_i-s，下面循环可以保证正好从s到c3_i为止（和从0到num值一样）
									
									int A1[]=new int[num+1];
									int A2[]=new int[num+1];
									int max1[]=new int[num+1];
									int h=s-1;
									//System.out.println("h"+"="+h);   ..........
					    	
									int l=0;       //计数器，记录S2中有几个数进行了比对
									for(int a=0;a<=num;a++){      //等于num是因为5-1=4，从一到五其实有五个数，4-0=4，从0到4其实有5个数，需要比对5次
										
										if(sort2_i[h][0]>=c1_i && sort2_i[h][0]<=c3_i && sort2_i[h][1]>=c1_j && sort2_i[h][1]<=c3_j){   //s-1：因为数组从零开始，RNA中[0][0]即（1,n）
											
											//计算H距离sort1_i[i][j]和sort2_i
											A1[l]=Math.abs(sort1_i[i][0]-sort2_i[h][0]);                            
									
											A2[l]=Math.abs(sort1_i[i][1]-sort2_i[h][1]);
									
											max1[l]=A1[l]>A2[l]?A1[l]:A2[l];
											//System.out.println("max1"+"["+l+"]"+"="+max1[l]);  .........
											//min[j]=max1[j]<max1[j+1]?max1[j]:max1[j+1];       
											//System.out.println("max"+" "+max1[j]);
											//long startTime = System.currentTimeMillis();
									
											l++;         //计数器，记录S2中有几个数进行了比对
									
											//long endTime = System.currentTimeMillis();
											h++;
											}else{
												h++;
											}
										}
									int temp=max1[0];
							
									for(int b=0;b<l;b++){
										if(max1[b]<temp){
											temp=max1[b];
											}
										//System.out.println("temp"+"="+temp); 
										}
									d[m]=temp; //得到最小值                  //将temp值存入min[]数组中
									//System.out.println("d"+"["+m+"]="+d[m]); 
							
									i++;
									//System.out.println("*********i = "+i);   
									
									}
							}
				}
				 
			}
			else if(i==sort1_i.length && j==sort2_i.length){
				
				//System.out.println("else if-2执行+++++++++++++结束");
				//System.out.print("\n");                              
				 
				break;
			}
		}
		
		//(时间统计***************************************************************************)
		//long endTime = System.currentTimeMillis();
		//System.out.println("程序运行时间："+(endTime-startTime_0)+"ms");
		
		
		return d;
		

	}


	public static void main(String[] args){
		
		
		in = new Scanner(System.in);
		System.out.println("please input a Hdoa path:");
		String a=in.next();
		
		on = new Scanner(System.in);
		System.out.println("please input next Hdoa path:");
		String b=on.next();
		
		//long startTime = System.currentTimeMillis();
		
    	
		int[][] c1=new ArryFile().send(a);
		int[][] c2=new ArryFile().send(b);
 
		long startTime = System.currentTimeMillis();
		//得到c1的另一个相同数组
		int[][] temp1=new int[c1.length][2];
		for(int q=0;q<2;q++){
			for(int p=0;p<c1.length;p++){
				temp1[p][q]=c1[p][q];
			}
		}
		//得到c2的另一个相同数组
		int[][] temp2=new int[c2.length][2];
		for(int n=0;n<2;n++){
			for(int m=0;m<c2.length;m++){
				temp2[m][n]=c2[m][n];
			}
		}
		

		Sort sort=new Sort();
	    
		int[][] sort_i=sort.bucketSort_i(c1,c1.length+1);   //桶排序
		int[][] sort_j=sort.bucketSort(temp1,temp1.length+1);   //桶排序
	    int[][] sort_i1=sort.bucketSort_i(c2,c2.length+1);   //桶排序
	    int[][] sort_j1=sort.bucketSort(temp2,temp2.length+1);   //桶排序
	    
	    int[] d=new Hdoa().Hausdorff(sort_i,sort_j,sort_i1,sort_j1);
	    

	    
	    int n=0;
	    for(int k=0;k<d.length;k++){
	    	if(d[k]>n){
	    		n=d[k];
	    	}
	    }
	    
	    long endTime = System.currentTimeMillis();
	    System.out.println("程序运行时间："+(endTime-startTime)+"ms");
	    System.out.println("The Hausdorff is "+n);

	}

}
