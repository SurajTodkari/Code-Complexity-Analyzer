package com.analyzer.service;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.springframework.stereotype.Service;

import com.analyzer.dto.ComplexityResponse;
import com.analyzer.dto.MethodComplexity;
import com.analyzer.parser.Java8Parser;
import com.analyzer.parser.Java8ParserBaseListener;

@Service
public class CodeAnalysisService {

	public ComplexityResponse analyze(String code) {
		Java8Parser.CompilationUnitContext ast = ASTConversion.convertToAST(code);
		List<MethodComplexity> methodComplexities=new ArrayList<>() ;
		
		ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(new Java8ParserBaseListener() {
            @Override
            public void enterMethodDeclaration(Java8Parser.MethodDeclarationContext ctx) {
                String methodName = ctx.methodHeader().methodDeclarator().Identifier().getText();
                int cyclomaticComplexity = CyclomaticComplexityCalculator.calculate(ctx);
                String timeComplexity = TimeComplexityCalculator.calculate(ctx);
                String spaceComplexity = SpaceComplexityCalculator.calculate(ctx);

                methodComplexities.add(new MethodComplexity(methodName, timeComplexity, spaceComplexity, cyclomaticComplexity));
            }
        }, ast);

        return calculateOverallComplexity(methodComplexities);
    }
	public ComplexityResponse calculateOverallComplexity(List<MethodComplexity> methodComplexities) {
	    int totalCyclomatic = 0;
	    String overallTimeComplexity = "O(1)";
	    String overallSpaceComplexity = "O(1)";

	    // Define a ranked list of complexities for comparison
	    List<String> complexityOrder = List.of("O(1)", "O(log n)", "O(n)", "O(n log n)", "O(n^2)", "O(n^3)", "O(2^n)", "O(n!)");

	    for (MethodComplexity mc : methodComplexities) {
	        totalCyclomatic += mc.getCyclomaticComplexity();

	        // Compare time complexities
	        if (complexityOrder.indexOf(mc.getTimeComplexity()) > complexityOrder.indexOf(overallTimeComplexity)) {
	            overallTimeComplexity = mc.getTimeComplexity();
	        }

	        // Compare space complexities
	        if (complexityOrder.indexOf(mc.getSpaceComplexity()) > complexityOrder.indexOf(overallSpaceComplexity)) {
	            overallSpaceComplexity = mc.getSpaceComplexity();
	        }
	    }

	    return new ComplexityResponse(methodComplexities, overallTimeComplexity, overallSpaceComplexity, totalCyclomatic);
	}

	 
}
