package com.nashtech.question1;

public class Calculator {
	private int a;
	private int b;
	
	public Calculator() {}
	
	public Calculator(int a, int b) {
		this.a = a;
		this.b = b;
	}
	
	public int sum() {
		return a + b;
	}
	
	public int sum(int a,int b) {
		return a + b;
	}
	
	public static void main(String[] args) {
		System.out.println("Sum of 3 and 5 should 8: " + new Calculator(3, 5).sum());
		System.out.println("Sum of 10 and 12 should 22: " + new Calculator().sum(10,12));
	}
	
	
}
