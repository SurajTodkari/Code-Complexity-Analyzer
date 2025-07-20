package com.analyzer.dto;

public class MethodComplexity {

	private String methodName ;
	private String timeComplexity ;
	private String spaceComplexity ;
	private int cyclomaticComplexity ;
	
	public MethodComplexity(String methodName, String timeComplexity, String spaceComplexity, int cyclomaticComplexity) {
		this.methodName=methodName;
		this.timeComplexity=timeComplexity;
		this.spaceComplexity=spaceComplexity;
		this.cyclomaticComplexity=cyclomaticComplexity;
	}
	public String getMethodName() {
		return methodName;
	}
	public String getTimeComplexity() {
		return timeComplexity;
	}
	public String getSpaceComplexity() {
		return spaceComplexity;
	}
	public int getCyclomaticComplexity() {
		return cyclomaticComplexity;
	}
}
