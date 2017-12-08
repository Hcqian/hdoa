package hdoa;

public class Search {
	  public int binarySearch_i(int array[][],int des){ 
		  
		    int left = 0, right = 0;  
		    for (right = array.length - 1; left != right;) {  
		        int midIndex = (right + left) / 2;  
		        int mid = (right - left);  
		        if (des == array[midIndex][0]) {  
		            return midIndex;  
		        }  
		  
		        if (des >array[midIndex][0]) {  
		            left = midIndex;  
		        } else {  
		            right = midIndex;  
		        }  
		  
		        if (mid <= 1) {  
		            break;  
		        }  
		    }  
		    return (Math.abs(des-array[left][0])<Math.abs(des-array[right][0]) ? left : right);
		}  

	  
	  //在RNA二级结构中，是配对方式存在的，所以i值有什么，j值一定也有什么。但是在普通二维中不一定。
	  public int sequnceSearch_j(int array[][],int des){        //也是一个折半查找
	  
	    int left = 0, right = 0;  
	    for (right = array.length - 1; left != right;) {  
	        int midIndex = (right + left) / 2;  
	        int mid = (right - left);  
	        if (des == array[midIndex][1]) {  
	            return midIndex;  
	        }  
	  
	        if (des >array[midIndex][1]) {  
	            left = midIndex;  
	        } else {  
	            right = midIndex;  
	        }  
	  
	        if (mid <= 1) {  
	            break;  
	        }  
	    }  
	 
	    return (Math.abs(des-array[left][1])<Math.abs(des-array[right][1]) ? left : right);  
	}  
	}  


