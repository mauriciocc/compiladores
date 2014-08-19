import java.util.ArrayList;
import java.util.List;

public class TestSyntacticAnalyser
{
    public static void main(String[] args)
    {
        String s = " 1 + 2.0* ( 03 *4.56 + 78.9)";
        System.out.println("Entrada: '" + s + "'");
        
        LexicalAnalyser la = new LexicalAnalyser(s);
        List<Token> tokens = la.analyse();
        
        SyntacticAnalyser sa = new SyntacticAnalyser();
        sa.analyse(tokens);
    }
}