package hdoa;

public class Sort {
	 int temp_i;
	 int temp_j;
	 
	//一般认为无序，但是冒泡排序和归并排序一个是正序排好用，一个逆序排好用，所以要改成别的，桶排序。
	 
	//按i排序
	 
	 /*
	 //冒泡排序，因为每次排完一趟就有一个最大值，所以不用管最后一个了，只管前i-1个即可，所以i--。
	 public int[][] bubbleSort(int MinSort[][]){
		  for(int i=MinSort.length-1;i>0;i--){
			  for(int j=0;j<i;j++){
				  if(MinSort[j][0]>MinSort[j+1][0]){
					  
					  temp_i=MinSort[j][0];
					  MinSort[j][0]=MinSort[j+1][0];
					  MinSort[j+1][0]=temp_i;
					  
					  temp_j=MinSort[j][1];
					  MinSort[j][1]=MinSort[j+1][1];
					  MinSort[j+1][1]=temp_j;
					  
				  }
			  }
		  }
		  //for (int k=0;k<MinSort.length;k++){
			// System.out.print("("+MinSort[k][0]+","+MinSort[k][1]+")"+",");
			 // }
		  return MinSort;
		  
	 }

*/
	 
	 public int[][] bucketSort_i(int[][] a, int max) {
	    	
        int[][] buckets;

        if (a==null || max<1)
            return null;
        

        // 创建一个容量为max的数组buckets，并且将buckets中的所有数据都初始化为0。
        buckets = new int[max][2];
        
        //System.out.println(buckets.length+"..................");
        //System.out.println( a.length);
        // 1. 计数
        int i=0;
        for(i = 0; i < a.length; i++){ 
            buckets[a[i][0]][0]++;
			buckets[a[i][0]][1]=a[i][1];
        }
        
        /*
        for(i = 0; i < a.length; i++){ 
            //buckets[a[i][0]][0]++;
        	
			buckets[a[i][0]][1]=a[i][1];
			
			 System.out.print(buckets[a[i][0]][1]);
        }
        System.out.print("\n");
       */
        
        // 2. 排序
        for (int m = 0, n = 0; m < max; m++) {
            while( (buckets[m][0]--) >0 ) {                //--，考虑到了重复项
                a[n][0] = m;
                a[n++][1] = buckets[m][1];  //第一次n不能++，做两次++肯定会越界
            }
        }

        buckets = null;
        return a;
    }
		        
	//按j排序
	    public int[][] bucketSort(int[][] a, int max) {
	    	
	    	 //for (int k=0; k<a.length; k++)
	             //System.out.printf( a[k][0]+","+a[k][1]+"   ");
	         //System.out.printf("\n");
	         
	        int[][] buckets;

	        if (a==null || max<1)
	            return null;
	        

	        // 创建一个容量为max的数组buckets，并且将buckets中的所有数据都初始化为0。
	        buckets = new int[max][2];
	        
	        //System.out.println(buckets.length+"..................");
	        //System.out.println( a.length);
	        // 1. 计数
	        int i=0;
	    	int k=0;
	    	int[] A=new int[2000]; //定义了上限(ct文件中等于0的值个数不能超过1000 )
	    	
	    	// 此for循环将j归位，然后将对应等于零的i存进一个数组，数组大小为k。
	        for(i = 0; i < a.length; i++){ 
	            buckets[a[i][1]][1]++;
	            
	            //if语句将i值j值重复与不重复的分开
	            if(a[i][1]==0){
	                A[k]=a[i][0];
	                //System.out.println( A[k]);		
	            	k++;
	            }else{
	            	buckets[a[i][1]][0]=a[i][0];
	            }
	            //System.out.println(a[i][1]);
				//buckets[a[i][1]][0]=a[i][0];
	        }
	        
	        
	        // 2. 排序
	        //此循环将数组A[k]循环赋值给a[][0]和a[][1]=0,但是只赋值k个。
	        for(int x=0;x<k;x++){
	        	
	        	a[x][0]=A[x];	
	        	a[x][1]=0;
	        	//System.out.println( a[x][0]);
	        }
	  
	       //该for循环将剩余的max-k个数（j非零）
	        for (int m = 1, n = k; m < max; m++) {
	            while( (buckets[m][1]--) >0 ) {                //--，考虑到了重复项
	                a[n][1] = m;
	                a[n++][0] = buckets[m][0];  //第一次n不能++，做两次++肯定会越界
	            }
	        }

	        buckets = null;
	        return a;
	    }
	    
	   //*/
}
  



