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
  READ = 'read';
  LOOP = 'while';
  ATTRIB = '=';
  OPEN_C = '{'; 
  CLOSE_C = '}';
  GT = '>';
  GE = '>=';
  LT = '<';
  LE = '<=';
  EQ = '==';
  NE = '!=';
  
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
  private static int whileCount = 0;
  
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
	    generateCode(code, val, true);
    }  
    
    private static void generateCode(String code, int val, boolean newLine) {         
    if(newLine) {
      System.out.println(code);
      } else {
      System.out.print(code);
      }
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
  : print | attribuition | read | loop
  ;  
  
  loop
  : 
       	{generateCode("BEGIN_WHILE_"+(++whileCount)+":", 0);}
  	LOOP exp_comparison 
       	{generateCode(" END_WHILE_"+(whileCount)+" ;", 0);}
  	OPEN_C (statement)* CLOSE_C	
  	       	{generateCode("END_WHILE_"+(whileCount)+":", 0);}
  ;
  
     
  read
  	:	READ VARIABLE
     	{
     		generateCode("invokestatic Runtime/readInt()I ;", 1);  
		if(symbol_table.contains($VARIABLE.text)) {
			generateCode("istore " + (symbol_table.indexOf($VARIABLE.text)), -1);
		} else {
			throw new IllegalStateException("Error: Trying to put read value on an undefined variable '"+$VARIABLE.text+"' on position [" + $VARIABLE.line+ ","+$VARIABLE.getCharPositionInLine()+"]");
		}
	} 
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
      generateCode("istore " + (symbol_table.indexOf($VARIABLE.text)), -1);
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
    
  exp_comparison
  :   exp_arithmetic ( op = ( GT | GE | LT | LE | EQ | NE ) )  exp_arithmetic 
              { 
              String val = null;
	              	if($op.type == GT) {
		         val = "if_icmpgt";                  	
			} else if($op.type == GE) {
					         val = "if_icmpge";
          	
			} else if($op.type == LT) {
								         val = "if_icmplt";
      	
			} else if($op.type == LE) {
								         val = "if_icmple";

			} else if($op.type == EQ) {
								         val = "if_icmpeq";

			} else if($op.type == NE) {
								         val = "if_icmpne";

		        }
		        generateCode(val, -2, false); 
              }
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
  
