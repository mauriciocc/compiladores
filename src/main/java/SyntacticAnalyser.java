
import java.util.*;

public class SyntacticAnalyser {

    private List<Token> tokens;
    private int level = 0;

    public SyntacticAnalyser() {
    }

    private void call(String s) {
        for (int i = 0; i < level; i++) {
            System.out.print("  ");
        }
        System.out.println(s);
    }

    public double analyse(List<Token> tokens) {
        try {
            this.tokens = tokens;
            tokens.add(new Token('$'));
            return S();
        } finally {
            System.out.println("remaining tokens: " + tokens);
        }
    }

    private double S() {
        call("S");
        return E();
    }

    private double E() // E -> T | T + E
    {
        double value = 0;
        level++;
        call("E");
        value = T();
        if (tokens.get(0).getType() == '+') {
            tokens.remove(0);
            value += E();
        } else if (tokens.get(0).getType() == '-') {
            tokens.remove(0);
            value -= E();
        }
        level--;
        return value;
    }

    private double T() // T -> F | F * T
    {
        double value = 0;
        level++;
        call("T");
        value = F();
        if (tokens.get(0).getType() == '*') {
            tokens.remove(0);
            value *= T();
        } else if (tokens.get(0).getType() == '/') {
            tokens.remove(0);
            double t = T();
            if(t == 0) {
                throw new ArithmeticException("cannot divide by 0");
            }
            value /= t;
        }
        level--;
        return value;
    }

    private double F() // F -> n | (E)
    {
        double value = 0;
        level++;
        call("F");
        if (tokens.get(0).getType() == 'n') {
            value = tokens.remove(0).getValue();
        } else if (tokens.get(0).getType() == '(') {
            tokens.remove(0);
            value = E();
            if (tokens.get(0).getType() != ')') {
                error(tokens.get(0));
            }
            tokens.remove(0);
        } else {
            error(tokens.get(0));
        }
        level--;
        return value;
    }

    private void error(Token t) {
        System.out.println("error: unexpected symbol " + t.getType());
        throw new RuntimeException();
    }
}
