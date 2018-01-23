package ast;

public class PrintFunction extends Function {

	@Override
	public Expression call(Expression[] args) {
		for (Expression exp : args) {
			System.out.print(exp.getValue().toString());
		}
		return new VoidExpression();
	}

}
