package ast;

public class FunctionCallExpression extends Expression {
	
	public Function function;
	public Expression[] arguments;
	
	public FunctionCallExpression(Function function, Expression[] arguments){
		this.function = function;
		this.arguments = arguments;
	}
	
	public Object getValue() {
		return function.call(arguments);
	}
	
}
