import java.util.*;

public class SyntacticAnalyser
{
    private List<Token> tokens;
    private int level = 0;
    
    public SyntacticAnalyser() {}

    private void call(String s) { 
        for (int i = 0; i < level; i++) { 
            System.out.print("  "); 
        }
        System.out.println(s);
    }

    public void analyse(List<Token> tokens)
    {
        this.tokens = tokens;
        tokens.add(new Token('$'));
        S();
        System.out.println("remaining tokens: " + tokens);
    }
    
    private void S()
    {
        call("S");
        E();
    }
    
    private void E() // E -> T | T + E
    {
        level++; call("E");
        T();
        if (tokens.get(0).getType() == '+') {
            tokens.remove(0); //System.out.print("+ ");
            E();
        }
        level--;
    }
    
    private void T() // T -> F | F * T
    {
        level++; call("T");
        F();
        if (tokens.get(0).getType() == '*') {
            tokens.remove(0); //System.out.print("* ");
            T();
        }
        level--;
    }
    
    private void F() // F -> n | (E)
    {
        level++; call("F");
        if (tokens.get(0).getType() == 'n') {
            tokens.remove(0); //System.out.print("n ");
        }
        else if (tokens.get(0).getType() == '(') {
            tokens.remove(0); //System.out.print("( ");
            E();
            if (tokens.get(0).getType() != ')') {
                System.out.println("error: unexpected symbol " + tokens.get(0).getType());
                System.exit(0);
            }
            tokens.remove(0); //System.out.print(") ");
        }
        else {
            System.out.println("error: unexpected symbol " + tokens.get(0).getType());
            System.exit(0);
        }
        level--;
    }
}