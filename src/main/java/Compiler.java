
import java.io.StringWriter;
import java.util.*;

public class Compiler {

    private List<Token> tokens;
    private StringWriter sw = new StringWriter();

    public Compiler() {
    }

    public void analyse(List<Token> tokens) {
        try {
            this.tokens = tokens;
            tokens.add(new Token('$'));
            S();
        } finally {
            System.out.println("remaining tokens: " + tokens);
        }
    }

    private void S() {
        E();
    }

    private void E() // E -> T [(+|-) T]*
    {
        T();
        Token token = tokens.get(0);
        char type = token.getType();
        while (type == '+' || type == '-') {
            tokens.remove(0);
            T();
            if (type == '+') {
                add(Operation.ADIC);                
            } else if (type == '-') {
                add(Operation.SUBT);
            }

            //next
            token = tokens.get(0);
            type = token.getType();
        }
    }

    private void T() // T -> [(*|/) F]*
    {
        F();
        Token token = tokens.get(0);
        char type = token.getType();
        while (type == '*' || type == '/') {
            tokens.remove(0);
            F();
            if (type == '*') {
                add(Operation.MULT);
            } else if (type == '/') {
                add(Operation.DIVI);
            }

            //next
            token = tokens.get(0);
            type = token.getType();
        }
    }

    private void F() // F -> n | (E)
    {
        if (tokens.get(0).getType() == 'n') {
            add(tokens.remove(0).getValue());
        } else if (tokens.get(0).getType() == '(') {
            tokens.remove(0);
            E();
            if (tokens.get(0).getType() != ')') {
                error(tokens.get(0));
            }
            tokens.remove(0);
        } else {
            error(tokens.get(0));
        }
    }

    public String getGeneratedCode() {
        return sw.toString();
    }

    private void error(Token t) {
        System.out.println("error: unexpected symbol " + t.getType());
        throw new RuntimeException();
    }

    private void add(char character) {
        sw.append(character + "\n");
    }

    private void add(String s) {
        sw.append(s + "\n");
    }

    private void add(Double v) {
        sw.append(v + "\n");
    }

    private void add(Operation op) {
        sw.append(op.toString() + "\n");
    }

    private static enum Operation {

        ADIC, SUBT, MULT, DIVI
    }
}
