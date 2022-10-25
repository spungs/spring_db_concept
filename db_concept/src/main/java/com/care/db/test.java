package com.care.db;

import java.util.Random;

public class test {
	public static void main(String[] args) {
		double n = Math.random();
		System.out.println(n);
		String num = Double.toString(n).substring(2,8);
		System.out.println(num);
	}
}
