package com.tianya.springboot.excel.test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestMain {
	
	
	public static void getABC(List<Integer> nums) {
		// 先进行从小到大排序
		Collections.sort(nums);
		int size = nums.size();
		if (size == 3) {
			Integer sum = nums.stream().reduce(0, (a,b)->a+b);
			if (0 == sum) {
				// 输出ABC
				System.out.println(nums);
			}
		} else if ( size > 3){
			for (int i = 0; i < size-2; i++) {
				for (int j = i+1; j < size-1; j++) {
					for (int k = j+1; k < size; k++) {
						int sum = nums.get(i) + nums.get(j) + nums.get(k);
						if (0 == sum) {
							//输出 ABC
							System.out.println(nums.get(i) + " , " + nums.get(j) + " , " + nums.get(k));
						}
					}
				}
			}
		} else {
			// 输入少于3个
			System.out.println("输入少于3个");
		}
	}

	public static void main(String[] args) {
		
		List<Integer> nums = Arrays.asList(-1,0,1,2,-1,-4);
		System.out.println(nums);
		getABC(nums);
		
		
	}
	
}
