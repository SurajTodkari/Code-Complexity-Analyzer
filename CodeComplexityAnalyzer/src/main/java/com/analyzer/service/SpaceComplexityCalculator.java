package com.analyzer.service;

import com.analyzer.parser.Java8Parser.MethodDeclarationContext;

public class SpaceComplexityCalculator {

    public static String calculate(MethodDeclarationContext ctx) {
        int variableCount = 0;  // Count simple variables (int a, b;)
        int arrayCount = 0;     // Count arrays (int[] arr = new int[n];)
        int objectCount = 0;    // Count object allocations (new ClassName();)
        int recursionDepth = 0; // Count recursive calls
        String methodName = ctx.methodHeader().methodDeclarator().Identifier().getText(); // Method Name

        String spaceComplexity = "O(1)"; // Default: O(1) if no dynamic memory allocation
        if (ctx.methodBody() != null && ctx.methodBody().block() != null && ctx.methodBody().block().blockStatements() != null) {
            for (var statement : ctx.methodBody().block().blockStatements().blockStatement()) {
                String statementText = statement.getText();

                // Check for variable declarations (int a, double x, String name)
                if (statementText.matches(".*(int|double|float|char|boolean|String)\\s+\\w+.*")) {
                    variableCount++;
                }

                // Check for array allocations (new int[n], new String[size])
                if (statementText.matches(".*new\\s+\\w+\\[.*")) {
                    arrayCount++;
                }

                // Check for object instantiation (new Object();)
                if (statementText.matches(".*new\\s+\\w+\\(.*")) {
                    objectCount++;
                }

                // Check for recursion (method calling itself)
                if (statementText.contains(methodName + "(")) {
                    recursionDepth++;
                }
            }
        }else {
        	return spaceComplexity ;
        }

        // Estimate Space Complexity
        if (arrayCount > 0 || objectCount > 0) {
            spaceComplexity = "O(n)";  // Arrays or dynamically created objects
        }
        if (recursionDepth > 0) {
            spaceComplexity = "O(n)";  // Linear recursion increases stack usage
        }
        if (recursionDepth > 1) {
            spaceComplexity = "O(n^" + recursionDepth + ")"; // Exponential recursion
        }

        return spaceComplexity;
    }
}
