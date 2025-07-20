package com.analyzer.service;

import com.analyzer.parser.Java8Parser.MethodDeclarationContext;

public class CyclomaticComplexityCalculator {

    public static int calculate(MethodDeclarationContext ctx) {
        int complexity = 1; // Start with base complexity

        if (ctx.methodBody() != null && ctx.methodBody().block() != null && ctx.methodBody().block().blockStatements() != null) {
            for (var statement : ctx.methodBody().block().blockStatements().blockStatement()) {
                String statementText = statement.getText();

                // Improved pattern matching using regex with word boundaries
                if (statementText.matches(".*\\b(if|for|while|catch|do)\\b.*")) {
                    complexity++;
                }

                // Handle switch cases (each case adds to complexity)
                if (statementText.contains("case ")) {
                    complexity++;
                }

                // Count logical operators (&& and ||) as extra paths
                complexity += countOccurrences(statementText, "&&");
                complexity += countOccurrences(statementText, "\\|\\|"); // Escaped for regex
            }
        }else {
        	return complexity;
        }
        return complexity;
    }

    // Utility method to count occurrences of a substring in a string
    private static int countOccurrences(String text, String pattern) {
        return text.split(pattern, -1).length - 1;
    }
}
