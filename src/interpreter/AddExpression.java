package interpreter;

import ast.Expression;
import ast.IntExpression;

public class AddExpression extends Expression {

	public IntExpression left;
	public IntExpression right;
	
	public AddExpression(IntExpression left, IntExpression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public Object getValue() {
		return (int) left.getValue() + (int) right.getValue();
	}

}
