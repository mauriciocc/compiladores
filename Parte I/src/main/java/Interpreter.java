
import java.util.*;

public class Interpreter {

    private List<Token> tokens;
    private int level = 0;

    public Interpreter() {
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

    private double E() // E -> T [(+|-) T]*
    {
        double value = 0;
        value = T();
        Token token = tokens.get(0);
        char type = token.getType();
        while (type == '+' || type == '-') {
            tokens.remove(0);
            if (type == '+') {
                value += T();
            } else if (type == '-') {
                value -= T();
            }

            //next
            token = tokens.get(0);
            type = token.getType();
        }
        return value;
    }

    private double T() // T -> [(*|/) F]*
    {
        double value = 0;

        value = F();
        Token token = tokens.get(0);
        char type = token.getType();
        while (type == '*' || type == '/') {
            tokens.remove(0);
            if (type == '*') {
                value *= F();
            } else if (type == '/') {
                value /= F();
            }

            //next
            token = tokens.get(0);
            type = token.getType();
        }
        return value;
    }

    private double F() // F -> n | (E)
    {
        double value = 0;
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
        return value;
    }

    private void error(Token t) {
        System.out.println("error: unexpected symbol " + t.getType());
        throw new RuntimeException();
    }
}
