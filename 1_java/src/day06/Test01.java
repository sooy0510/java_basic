package day06;

import java.util.Arrays;

import util.Util;

public class Test01 {

	public static void main(String[] args) {
		int[] num1 = {99,44,77,68,80};
		System.out.println(Arrays.toString(num1));

		Util.sort(num1);
		System.out.println(Arrays.toString(num1));

		int[] num2 = {2,3,9,1,7};
		System.out.println(Arrays.toString(num2));
		
		Util.sort(num2);
		System.out.println(Arrays.toString(num2));


		System.out.println(Arrays.toString(num1));
		System.out.println(Arrays.toString(Util.sort(num1)));
}

}
