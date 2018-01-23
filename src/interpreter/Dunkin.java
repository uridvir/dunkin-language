package interpreter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import interpreter.Scanner;
import interpreter.Token;

public class Dunkin {
	
	static boolean hadError = false;
	
	public static void main(String[] args) throws IOException {
		if (args.length > 1) {
			System.out.println("Usage: dunkin [file]");
		} else if (args.length == 1) {
			parseFile(args[0]);
		} else {
			interactivePrompt();
		}
	}

	private static void parseFile(String path) throws IOException {
		byte[] content = Files.readAllBytes(Paths.get(path));
		run(new String(content, Charset.defaultCharset()));
		
		if (hadError) System.exit(10);	
	}

	private static void interactivePrompt() throws IOException {
		InputStreamReader input = new InputStreamReader(System.in);
		BufferedReader reader = new BufferedReader(input);
		
		while (true) {
			System.out.print("> ");
			run(reader.readLine());
			hadError = false;
		}
		
	}
	
	private static void run(String source) {
		Scanner scanner = new Scanner(source);
		List<Token> tokens = scanner.scanTokens();
		
		//For now, don't interpret the tokens, just print them
		for (Token token : tokens) {
			System.out.println(token);
		}
		
	}
	
	static void error(int line, String message) {
		report(line, "", message);
	}

	private static void report(int line, String location, String message) {
		System.err.println("[line " + line + "] Error" + location + ": " + message);
		hadError = true;
	}
	
}
