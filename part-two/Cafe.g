grammar Cafe;

/*---------------- TOKEN DEFINITIONS ----------------*/

tokens
{
    PLUS = '+';
    MINUS = '-';
    TIMES = '*';
    OVER = '/';
    OPEN_P = '(';
    CLOSE_P = ')';
}

/*---------------- COMPILER INTERNALS ----------------*/

@header
{
    //import java.util.ArrayList;
}

@members
{
    //private static ArrayList<String> symbol_table;

    public static void main(String[] args) throws Exception
    {
        ANTLRInputStream input = new ANTLRInputStream(System.in);
        ExpLexer lexer = new ExpLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ExpParser parser = new ExpParser(tokens);

        //symbol_table = new ArrayList<String>();        
    
 
        System.out.println(".source saida.j");
        System.out.println(".class  public saida");
        System.out.println(".super  java/lang/Object");
  
        System.out.println(".method public <init>()V");
        System.out.println("aload_0");
        System.out.println("invokenonvirtual java/lang/Object/<init>()V");
        System.out.println("return");
        System.out.println(".end method");
        System.out.println(".method public static main([Ljava/lang/String;)V");
        System.out.println("getstatic java/lang/System/out Ljava/io/PrintStream;");
        
        parser.program();
        
        System.out.println("invokevirtual java/io/PrintStream/println(I)V");
        System.out.println("return");
        System.out.println(".limit stack 50");    
        System.out.println(".end method");
    }
}

/*---------------- LEXER RULES ----------------*/

NUM     : '0'..'9'+('.' '0'..'9'+)? ;
SPACE   : (' '|'\t'|'\r'|'\n')+ { skip(); } ;


/*---------------- PARSER RULES ----------------*/

program
    :  expression  
    ;

expression
    :   term ( op = ( PLUS | MINUS ) term 
    { System.out.println($op.type == PLUS ? "iadd" : "isub"); }
  )*
 ;
 
term    
    :   factor ( op = ( TIMES | OVER ) factor 
    { System.out.println($op.type == TIMES ? "imul" : "idiv"); } 
  )*
    
    ;    
 
factor
    :   NUM
        { System.out.println("ldc "+$NUM.text); }
 | OPEN_P expression CLOSE_P
    ;

