package com.cs.order.utils;

public class RoundOffTest {

	public static void main(String[] args) {
		int i=19;
		double d= (9*10)/(double)i;
		int y = (int) Math.round(2.6);
		System.out.println(y);
		System.out.println(d);
		System.out.println((9*10)/19d);
		System.out.println((int)Math.round((d)));
	}
}
