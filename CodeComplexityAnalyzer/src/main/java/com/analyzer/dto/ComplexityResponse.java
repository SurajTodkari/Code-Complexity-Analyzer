package com.analyzer.dto;

import java.util.List;

public class ComplexityResponse {

	private List<MethodComplexity> methodComplexities ;
	private String overallTimeComplexity ;
	private String overallSpaceComplexity ;
	private int overallCyclomaticComplexity ;
	
	public ComplexityResponse(List<MethodComplexity> methodComplexities, String overallTimeComplexity, String overallSpaceComplexity, int overallCycomaticComplexity) {
		
		this.methodComplexities=methodComplexities;
		this.overallTimeComplexity=overallTimeComplexity ;
		this.overallSpaceComplexity=overallSpaceComplexity ;
		this.overallCyclomaticComplexity=overallCycomaticComplexity ;
		
		
	}

	public List<MethodComplexity> getMethodComplexities() {
		return methodComplexities;
	}

	public String getOverallTimeComplexity() {
		return overallTimeComplexity;
	}

	public String getOverallSpaceComplexity() {
		return overallSpaceComplexity;
	}

	public int getOverallCyclomaticComplexity() {
		return overallCyclomaticComplexity;
	}
}
