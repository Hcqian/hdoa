package hdoa;

import java.io.BufferedReader; 
import java.io.FileInputStream; 
import java.io.IOException; 
import java.io.InputStreamReader; 
import java.util.ArrayList;

public class ArryFile {
		   ArrayList<String>list=new ArrayList<String>(); 
		   String s;
		   public int[][] send(String ss){  ///
		   try { 
			   InputStreamReader isr=new InputStreamReader(new FileInputStream(ss)); 
			   BufferedReader br=new BufferedReader(isr); 
			   while((s=br.readLine())!=null){ 
				   list.add(s);
	                } 
		   } catch (IOException e) {
			  System.out.println("路径有误");
		   } 
		   char[][] c=new char[list.size()][];   // 依据文件建一个字符数组
		   String[][] str=new String[list.size()-4][2];  //建一个list.size()-4行2列的数组
		   for(int i=0;i<c.length;i++){ 
			   c[i]=list.get(i).toCharArray();   //将字符串转化为字符数组c[i]表示读取每一行
			   
		   } 
		   for(int i=4;i<c.length;i++){ 
			 String d=String.valueOf(c[i]);  //将字符数组c[i]转化为为字符串string
			 String[] str2 = d.split("\\s+"); //将一个字符串分割为子字符串，然后将结果作为字符串数组返回
			 str[i-4][0]=str2[1];
			 str[i-4][1]=str2[5];
		   } 
		    int a,b;
		    a = str.length;
		    b = str[0].length;
		    int result[][] = new int[a][b];
		    for(int i = 0 ; i < a ; ++ i)
		        for(int j = 0 ; j < b ; ++j)
		            result[i][j] = Integer.parseInt(str[i][j]);
		    return result;
		   
		}
}
