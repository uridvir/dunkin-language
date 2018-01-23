package interpreter;

public enum TokenType {
	//One-character tokens
	LEFT_CURLY_BRACKET, RIGHT_CURLY_BRACKET, SEMICOLON, COMMA,
	PLUS, MINUS, ASTERISK, FORWARD_SLASH, PERCENT, CARET,
	
	//One or two character tokens
	BANG, BANG_EQUAL, EQUAL, GREATER, GREATER_EQUAL, LESS, LESS_EQUAL,
	
	//Two-character token
	PLUS_PLUS,
	
	//Literals
	IDENTIFIER, STRING_LITERAL, NUMBER,
	
	/* Keywords
	 */
	//Types
	INT, STRING_TYPE, NOTHING,
	//Functions
	FUNCTION, TAKES, RETURN, RETURNS,
	//Control statements
	IF, ELSE, WHILE, FOR,
	
	END_OF_FILE
}
