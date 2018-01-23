package interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {
	
	private static final Map<String, TokenType> keywords;
	
	static {
		keywords = new HashMap<>();
		keywords.put("int", TokenType.INT);
		keywords.put("string", TokenType.STRING_TYPE);
		keywords.put("nothing", TokenType.NOTHING);
		keywords.put("function", TokenType.FUNCTION);
		keywords.put("takes", TokenType.TAKES);
		keywords.put("return", TokenType.RETURN);
		keywords.put("returns", TokenType.RETURNS);
		keywords.put("if", TokenType.IF);
		keywords.put("else", TokenType.ELSE);
		keywords.put("while", TokenType.WHILE);
		keywords.put("for", TokenType.FOR);
	}

	private final String source;
	private final List<Token> tokens = new ArrayList<>();
	
	private int start = 0;
	private int current = 0;
	private int line = 1;

	public Scanner(String source) {
		this.source = source;
	}

	public List<Token> scanTokens() {
		while (!isAtEnd()) {
			start = current;
			scanToken();
		}
		
		tokens.add(new Token(TokenType.END_OF_FILE, "", null, line));
		return tokens;
	}
	
	private void scanToken() {
		char c = advance();
		switch (c) {
			case '{':
				addToken(TokenType.LEFT_CURLY_BRACKET);
				break;
			case '}':
				addToken(TokenType.RIGHT_CURLY_BRACKET);
				break;
			case ',':
				addToken(TokenType.COMMA);
				break;
			case ';':
				addToken(TokenType.SEMICOLON);
				break;
			case '-':
				addToken(TokenType.MINUS);
				break;
			case '*':
				addToken(TokenType.ASTERISK);
				break;
			case '/':
				addToken(TokenType.FORWARD_SLASH);
				break;
			case '=':
		    	addToken(TokenType.EQUAL);
		    	break;
			case '#':
				while (peek() != '\n' && !isAtEnd()) {
					advance();
				}
				break;
			case '+':
				addToken(match('+') ? TokenType.PLUS_PLUS : TokenType.PLUS);
				break;
			case '!':
				addToken(match('=') ? TokenType.BANG_EQUAL : TokenType.BANG);
				break; 
		    case '<':
		    	addToken(match('=') ? TokenType.LESS_EQUAL : TokenType.LESS);
		    	break;
		    case '>':
		    	addToken(match('=') ? TokenType.GREATER_EQUAL : TokenType.GREATER);
		    	break;
		    case ' ':
		    case '\r':
		    case '\t':
		    	break;
		    case '\n':
		    	line++;
		    	break;
		    case '"':
		    	processString();
		    	break;
			default:
				if (isDigit(c)) {
					processNumber();
				} else if (isLetter(c)) {
					processIdentifier();
				} else {
					Dunkin.error(line, "Unexpected character.");
				}
				break;
		}
		
	}
	
	private boolean isAtEnd() {
		return current >= source.length();
	}

	private void addToken(TokenType type) {
		addToken(type, null);
	}

	private void addToken(TokenType type, Object literal) {
		String text = source.substring(start, current);
		tokens.add(new Token(type, text, literal, line));
	}
	
	private char advance() {
		current++;
		return source.charAt(current - 1);
	}
	
	private boolean match(char expected) {
		if (isAtEnd() || source.charAt(current) != expected) {
			return false;
		} else {
			current++;
			return true;
		}
	}
	
	private char peek() {
	    if (isAtEnd()) {
	    	return '\0';
	    } else {
	    	return source.charAt(current);
	    }
	}
	
	private void processString() {
		while (peek() != '"' && !isAtEnd()) {
			if (peek() == '\n') {
				line++;
		    }
		    advance();
		}

		// Unterminated string.
		if (isAtEnd()) {
			Dunkin.error(line, "Unterminated string.");
		    return;
		}

		// The closing ".
		advance();

		// Trim the surrounding quotes.
		String value = source.substring(start + 1, current - 1);
		addToken(TokenType.STRING_LITERAL, value);
	}
	
	private boolean isDigit(char c) {
		if (c >= '0' && c <= '9') {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isLetter(char c) {
		if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == '_') {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isAlphanumeric(char c) {
		if (isDigit(c) || isLetter(c)) {
			return true;
		} else {
			return false;
		}
	}
	
	private void processNumber() {
		while (isDigit(peek())) advance();

	    // Look for a fractional part.
	    if (peek() == '.' && isDigit(peekNext())) {
	    	// Consume the "."
	    	advance();

	    	while (isDigit(peek())) {
	    		advance();
	    	}
	    }

	    addToken(TokenType.NUMBER, Double.parseDouble(source.substring(start, current)));
	}

	private char peekNext() {
		if (current + 1 >= source.length()) {
			return '\0';
		} else {
			return source.charAt(current + 1);
		}
	}
	
	private void processIdentifier() {
		while(isAlphanumeric(peek())) {
			advance();
		}
		String text = source.substring(start, current);
		TokenType type = keywords.get(text);
		if (type == null) {
			type = TokenType.IDENTIFIER;
		}
		addToken(type);
	}

}
