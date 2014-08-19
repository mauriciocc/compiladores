import java.util.List;

public class TestSyntacticAnalyser
{
    public static void main(String[] args)
    {
        //String s = " 1 + 2.0* ( 03 *4.56 + 78.9)";
        //String s = " (1 + 2) * 3";
        String s = "12 + 50 - 15 * 4/2";
        System.out.println("Entrada: '" + s + "'");
        
        LexicalAnalyser la = new LexicalAnalyser(s);
        List<Token> tokens = la.analyse();
        
        SyntacticAnalyser sa = new SyntacticAnalyser();
        System.out.println(sa.analyse(tokens));
    }
}