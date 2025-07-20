package com.analyzer.service;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.springframework.stereotype.Service;

import com.analyzer.exception.ASTConversionException;
import com.analyzer.parser.Java8Lexer;
import com.analyzer.parser.Java8Parser;
import com.analyzer.parser.ThrowingErrorListener;


@Service
public class ASTConversion {

	public static Java8Parser.CompilationUnitContext convertToAST(String code) {
		CharStream input = CharStreams.fromString(code);
		
	  	try {
	  		 Java8Lexer lexer=new Java8Lexer(input);
	  		 lexer.removeErrorListeners();
	         lexer.addErrorListener(ThrowingErrorListener.INSTANCE); // Custom error listener
			
			CommonTokenStream token = new CommonTokenStream(lexer);
			
			Java8Parser parser= new Java8Parser(token);
			parser.removeErrorListeners();
            parser.addErrorListener(ThrowingErrorListener.INSTANCE);
			return  parser.compilationUnit();
	  	}
	  	catch (RecognitionException e) {
            throw new ASTConversionException("Syntax Error in Code: " + e.getMessage());
        } catch (Exception e) {
            throw new ASTConversionException("Unexpected error during AST conversion: " + e.getMessage(), e);
        }
		 
				
	}
}
