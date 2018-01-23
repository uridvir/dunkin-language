package ast;

public class StringExpression extends Expression {

	String value;
	
	public StringExpression(String value){
		this.value = value;
	}

	@Override
	public Object getValue() {
		return value;
	}
	
}
