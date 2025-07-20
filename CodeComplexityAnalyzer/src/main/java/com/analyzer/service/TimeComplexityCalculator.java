package com.analyzer.service;

import com.analyzer.parser.Java8Parser;
import com.analyzer.parser.Java8Parser.MethodDeclarationContext;
import com.analyzer.parser.Java8ParserBaseListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import java.util.Stack;

public class TimeComplexityCalculator extends Java8ParserBaseListener {

    private int maxNestedLoops = 0;
    private int currentLoopDepth = 0;
    private boolean hasRecursion = false;
    private boolean hasLogarithmicLoop = false;
    private boolean hasSorting = false;
    private boolean hasQuadraticSort = false;
    private String methodName;
    private Stack<Boolean> loopStack = new Stack<>();
    private int recursionCount = 0;
	private boolean hasStatements;

    
    
    public static String calculate(MethodDeclarationContext ctx) {
        TimeComplexityCalculator analyzer = new TimeComplexityCalculator();
        ParseTreeWalker.DEFAULT.walk(analyzer, ctx);
        return analyzer.getTimeComplexity();
    }
    
    @Override
    public void enterStatement(Java8Parser.StatementContext ctx) {
        hasStatements = true;

        String statementText = ctx.getText();

        if (statementText.startsWith("for(") || statementText.startsWith("while(") || statementText.startsWith("do{")) {
            currentLoopDepth++;
            maxNestedLoops = Math.max(maxNestedLoops, currentLoopDepth);
            loopStack.push(true);
        }

        if (methodName != null && statementText.contains(methodName + "(")) {
            hasRecursion = true;
            recursionCount++;
        }

        if (statementText.matches(".*while\\s*\\(.*(i\\s*\\*\\=|i\\s*=\\s*i\\s*\\*).*\\).*")) {
            hasLogarithmicLoop = true;
        }

        if (statementText.contains("Arrays.sort") || statementText.contains("Collections.sort")) {
            hasSorting = true;
        }

        if (statementText.matches(".*for\\s*\\(.*\\).*for\\s*\\(.*\\).*swap.*")) {
            hasQuadraticSort = true;
        }
    }

    @Override
    public void exitStatement(Java8Parser.StatementContext ctx) {
        if (!loopStack.isEmpty()) {
            loopStack.pop();
            currentLoopDepth--;
        }
    }

    public String getTimeComplexity() {
    	 if (!hasStatements) return "O(1)";
        if (hasRecursion) {
            if (recursionCount > 1) return "O(2^n) (Exponential Recursion Detected)";
            return "O(n) (Recursion Detected)";
        }
        if (hasSorting) return "O(n log n)";
        if (hasQuadraticSort) return "O(n^2)";
        if (hasLogarithmicLoop) return "O(log n)";
        if (maxNestedLoops == 0) return "O(1)";
        if (maxNestedLoops == 1) return "O(n)";
        return "O(n^" + maxNestedLoops + ")";
    }

}
