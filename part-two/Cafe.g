grammar Cafe;

/*---------------- TOKEN DEFINITIONS ----------------*/

tokens
{
  PLUS = '+';
  MINUS = '-';
  TIMES = '*';
  OVER = '/';
  REMAINDER = '%';
  OPEN_P = '(';
  CLOSE_P = ')';  
  PRINT = 'print';
}

/*---------------- COMPILER INTERNALS ----------------*/

@header
{
  //import java.util.ArrayList;
}

@members
{
  //private static ArrayList<String> symbol_table;
  private static int currentStack = 0;
  private static int maxStack = 0;
  
  public static void main(String[] args) throws Exception
  {
    ANTLRInputStream input = new ANTLRInputStream(System.in);
    CafeLexer lexer = new CafeLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    CafeParser parser = new CafeParser(tokens);
    
    //symbol_table = new ArrayList<String>();        
    
    
    System.out.println(".source Entrada.j");
    System.out.println(".class  public Entrada");
    System.out.println(".super  java/lang/Object");
    
    System.out.println(".method public <init>()V");
    System.out.println("aload_0");
    System.out.println("invokenonvirtual java/lang/Object/<init>()V");
    System.out.println("return");
    System.out.println(".end method");
    System.out.println(".method public static main([Ljava/lang/String;)V");               
    parser.program();
    System.out.println("return");
    System.out.println(".limit stack " + maxStack);    
    System.out.println(".end method");
  }
  
  private static void incrementStack(int val) {
    if(val < 0) {
      currentStack -= val;
    } else {
      currentStack += val;
      if(currentStack > maxStack) {
        maxStack = currentStack;
      }
    }
  }
      
    
}

/*---------------- LEXER RULES ----------------*/

  NUM     : '0'..'9'+;
  SPACE   : (' '|'\t'|'\r'|'\n')+ { skip(); } ;
  
  
  /*---------------- PARSER RULES ----------------*/
  
  program
  :  ( statement )*
    ;
  
  statement
  :  print
    ;  
  
  print
  : 
  { System.out.println("getstatic java/lang/System/out Ljava/io/PrintStream;"); }
  PRINT exp_arithmetic
  { System.out.println("invokevirtual  java/io/PrintStream/println(I)V"); incrementStack(-2); }
  ;
  
  exp_arithmetic
  :   term ( op = ( PLUS | MINUS ) term 
              { System.out.println($op.type == PLUS ? "iadd" : "isub"); incrementStack(1);}
  )*
    ;
  
  term    
  :   factor ( op = ( TIMES | OVER | REMAINDER ) factor 
                { System.out.println($op.type == TIMES ? "imul" : $op.type == OVER ? "idiv" : "irem");  incrementStack(1);} 
  )*
    
    ;    
  
  factor
  :   NUM
  { System.out.println("ldc "+$NUM.text);  incrementStack(1);}
  | OPEN_P exp_arithmetic CLOSE_P
    ;
  
