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
  ATTRIB = '=';
}

/*---------------- COMPILER INTERNALS ----------------*/

@header
{
  import java.util.List;
  import java.util.ArrayList;
    import java.util.Map;
  import java.util.HashMap;
}

@members
{
  private static List<String> symbol_table;
  private static Map<String, Integer> symbolAccess;
  private static int currentStack = 0;
  private static int maxStack = 0;
  
  public static void main(String[] args) throws Exception
  {
    ANTLRInputStream input = new ANTLRInputStream(System.in);
    CafeLexer lexer = new CafeLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    CafeParser parser = new CafeParser(tokens);
    
    symbol_table = new ArrayList<String>();  
    symbolAccess = new HashMap<String, Integer>();
    symbol_table.add("args");   
    
    System.out.println(".source Teste.j");
    System.out.println(".class  public Teste");
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
    System.out.println(".limit locals " + symbol_table.size());    
    System.out.println(".end method");
    
    symbol_table.remove(0); //Retira args
    for(String variable : symbol_table) {
      if(!symbolAccess.containsKey(variable)) {
        System.err.println("WARNING: variable '"+ variable + "' is declared but never used");
      }
    }
  }
  
    private static void generateCode(String code, int val) {         
      System.out.println(code);
      incrementStack(val);      
    }
  
  
  private static void incrementStack(int val) {         
      currentStack += val;
      if(currentStack > maxStack) {
        maxStack = currentStack;
      }
    }
  
  private static void registerVarAccess(String name) {
    symbolAccess.put(name, symbolAccess.containsKey(name) ? symbolAccess.get(name)+1 : 1);
  }
  
      
    
}

/*---------------- LEXER RULES ----------------*/

  NUM     : '0'..'9'+;
  SPACE   : (' '|'\t'|'\r'|'\n')+ { skip(); } ;
  VARIABLE: 'a'..'z'+;
  COMMENT : '//' ~('\r'|'\n')* { skip(); } ;
  
  
  /*---------------- PARSER RULES ----------------*/
  
  program
  :  ( statement )*
    ;
  
  statement
  : print | attribuition
  ;  
     
  print
  : 
  { generateCode("getstatic java/lang/System/out Ljava/io/PrintStream;", 1); }
  PRINT exp_arithmetic
  { generateCode("invokevirtual  java/io/PrintStream/println(I)V", -2); }
  ;
  
  attribuition
  : VARIABLE ATTRIB exp_arithmetic 
  {
    if(symbol_table.contains($VARIABLE.text)) {
      generateCode("istore " + (symbol_table.indexOf($VARIABLE.text)), 0);
    } else {
      symbol_table.add($VARIABLE.text); 
      generateCode("istore " + (symbol_table.size()-1), -1);
    }
  }
  ;
    
  exp_arithmetic
  :   term ( op = ( PLUS | MINUS ) term 
              { generateCode($op.type == PLUS ? "iadd" : "isub", -1);}
  )*
    ;
  
  term    
  :   factor ( op = ( TIMES | OVER | REMAINDER ) factor 
                { generateCode($op.type == TIMES ? "imul" : $op.type == OVER ? "idiv" : "irem", -1);} 
  )*
    
    ;    
  
  factor
  :   
    NUM
    { generateCode("ldc " + $NUM.text, 1);}
    | OPEN_P exp_arithmetic CLOSE_P
    | VARIABLE
    { 
      if(symbol_table.contains($VARIABLE.text)) {
        generateCode("iload " + (symbol_table.indexOf($VARIABLE.text)), 1);
        registerVarAccess($VARIABLE.text);
      } else {
        throw new IllegalStateException("Variable '"+$VARIABLE.text+"' undefined on position [" + $VARIABLE.line+ ","+$VARIABLE.getCharPositionInLine()+"]");
      }
    }
    ;
  
