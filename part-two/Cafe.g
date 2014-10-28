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
  IF_COND = 'if';
  ELSE_COND = 'else';
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
  private static List<Character> symbol_type;
  private static List<Exception> compilerExceptions;
  private static Map<String, Integer> symbolAccess;
  private static int currentStack = 0;
  private static int maxStack = 0;
  private static int whileCount = 0;
  private static int ifCount = 0;
  private static int identationLevel = 1;
  
  public static void main(String[] args) throws Exception
  {
    ANTLRInputStream input = new ANTLRInputStream(System.in);
    CafeLexer lexer = new CafeLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    CafeParser parser = new CafeParser(tokens);
    
    symbol_table = new ArrayList<String>();  
	symbol_type = new ArrayList<Character>();  
	compilerExceptions = new ArrayList<Exception>();  
    symbolAccess = new HashMap<String, Integer>();
    symbol_table.add("args");   
	symbol_type.add('s');
    
    System.out.println(".source Teste.j");
    System.out.println(".class  public Teste");
    System.out.println(".super  java/lang/Object");
    System.out.println();
    System.out.println(".method public <init>()V");
    System.out.println("\taload_0");
    System.out.println("\tinvokenonvirtual java/lang/Object/<init>()V");
    System.out.println("\treturn");
    System.out.println(".end method");
	System.out.println();
    System.out.println(".method public static main([Ljava/lang/String;)V");               
	System.out.println();
    parser.program();
	System.out.println();
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
	
	for(Exception e : compilerExceptions) {
		System.err.println(e.getMessage());
	}
	
	if(!compilerExceptions.isEmpty()) {
		throw new IllegalArgumentException("Compiler found some errors on your code :(");
	}
	
	
  }


    private static void generateCode(String code, int val) {   
	    generateCode(code, val, true);
    }  
    
    private static void generateCode(String code, int val, boolean newLine) {         
		code = ident(code);
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
  
  private static String replicate(String s, int times) {
  StringBuilder sb = new StringBuilder("");
	for(int i = 0; i < times; i++) {
		sb.append(s);
	}
	return sb.toString(); 
  
  }
  
  private static String ident(String s) {
	return replicate("\t", identationLevel) + s;
  }
  
  private static void incrIdent(int i) {
	identationLevel += i;
  }
  
      
    
}

/*---------------- LEXER RULES ----------------*/

  NUM     : '0'..'9'+;
  SPACE   : (' '|'\t'|'\r'|'\n')+ { skip(); } ;
  VARIABLE: 'a'..'z'+;
  COMMENT : '//' ~('\r'|'\n')* { skip(); } ;
  STRING  : '"' ~('"')* '"' ;
  
  
  /*---------------- PARSER RULES ----------------*/
  
  program
  :  ( statement )*
    ;
  
  statement
  : print | attribuition | read | loop | if_cond
  ;  
  
  loop
  : 
       	{				
			whileCount++;
			int local  = whileCount;				
			System.out.println();			
			generateCode("BEGIN_WHILE_"+(local)+":", 0);
		}
			LOOP exp_comparison 
			{
				generateCode(" END_WHILE_"+(local)+" ;", 0);
				System.out.println();
				incrIdent(1);
			}
			OPEN_C (statement)* CLOSE_C	
  	       	{
				incrIdent(-1);
				System.out.println();
				generateCode("goto BEGIN_WHILE_"+(local)+" ;", 0);
				generateCode("END_WHILE_"+(local)+":\n", 0);				
				
			}
  ;
  
  if_cond
  : 
       	{				
			ifCount++;
			int local  = ifCount;										
			boolean elsePresent = false;			
			System.out.println();
		}
		
		IF_COND exp_comparison 
			{				
				generateCode(" NOT_IF_"+(local)+" ;", 0);
				System.out.println();
				incrIdent(1);
			}			
			OPEN_C (statement)* CLOSE_C		
				
			{
				incrIdent(-1);
			}
			
			(ELSE_COND OPEN_C 			
			{
				System.out.println();
				generateCode("goto END_ELSE_"+(local)+" ;", 0);
				System.out.println();
				generateCode("NOT_IF_"+(local)+":", 0);		
				System.out.println();
			}
			(statement)* 
			
			{				
					
					System.out.println();				
					System.out.println();
					generateCode("END_ELSE_"+(local)+":", 0); 
					System.out.println();						
			}
			
			CLOSE_C {elsePresent = true;})? 
			
			{
				
				if(!elsePresent) {					
					System.out.println();
					generateCode("NOT_IF_"+(local)+":", 0);		
					System.out.println();				
				}
			}
			
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
  
  PRINT 
  (
	  { generateCode("getstatic java/lang/System/out Ljava/io/PrintStream;", 1); }
	  (		  
		  type = expression 
		  { 
			if(type == 'i'){
			generateCode("invokevirtual  java/io/PrintStream/print(I)V", -2);
			} else {
			generateCode("invokevirtual  java/io/PrintStream/print(Ljava/lang/String;)V", -2);			
			}
			
		  }
	  )
	  {System.out.println();}
  )*
  
  {	
	generateCode("getstatic java/lang/System/out Ljava/io/PrintStream;", 1);
	generateCode("invokevirtual  java/io/PrintStream/println()V", 1);
  }
  ;
  
  attribuition
  : VARIABLE ATTRIB (
	  type = expression 
	  {
		String store = type == 'i' ? "istore " : "astore ";		
		if(symbol_table.contains($VARIABLE.text)) {
		Character symbolType = symbol_type.get(symbol_table.indexOf($VARIABLE.text));
			if(symbolType.equals(type)) {
				generateCode(store + (symbol_table.indexOf($VARIABLE.text)), -1);
			} else {
				compilerExceptions.add(new IllegalArgumentException("[ERROR] VARIABLE TYPE MISMATCH:  trying to set an '"+type+"' value on variable '"+$VARIABLE.text+"' of type '"+symbolType+"'. Position [" + $VARIABLE.line+ ","+$VARIABLE.getCharPositionInLine()+"]"));
			}
		} else {
		  symbol_table.add($VARIABLE.text); 
		  symbol_type.add(type); 
		  generateCode(store + (symbol_table.size()-1), -1);
		}
	  }	
	)
  ;
    
  expression returns [char type]
  :   t1 = term ( op = ( PLUS | MINUS ) t2 = term 
              { 			  
				if($t1.type == 'i' && $t2.type == 'i') {
					generateCode($op.type == PLUS ? "iadd" : "isub", -1);
				} else {
					compilerExceptions.add(new IllegalArgumentException("[ERROR] ARITHMETIC EXPRESSION WITH STRINGS:  "+$t1.type+" "+$op.text+" "+$t2.type + " on Position ["+$op.line+","+$op.getCharPositionInLine()+"]"));
				}
			  }
  )*
  {$type = $t1.type;}
    ;
    
  exp_comparison
  :   expression ( op = ( GT | GE | LT | LE | EQ | NE ) )  expression 
              { 
				String val = null;

				if($op.type == EQ) val = "if_icmpne";
				if($op.type == NE) val = "if_icmpeq";
				if($op.type == GT) val = "if_icmple"; 
				if($op.type == GE) val = "if_icmplt";
				if($op.type == LT) val = "if_icmpge";
				if($op.type == LE) val = "if_icmpgt";				
				
		        generateCode(val, -2, false); 
              }
    ;
  
  term returns [char type]   
  :   f1 = factor ( op = ( TIMES | OVER | REMAINDER ) f2 = factor 
                { 
				if($f1.type == 'i' && $f2.type == 'i') {
				generateCode($op.type == TIMES ? "imul" : $op.type == OVER ? "idiv" : "irem", -1);
				} else {
					compilerExceptions.add(new IllegalArgumentException("[ERROR] ARITHMETIC EXPRESSION WITH STRINGS:  "+$f1.type+" "+$op.text+" "+$f2.type + " on Position ["+$op.line+","+$op.getCharPositionInLine()+"]"));
				}
				} 
  )*
  {$type = $f1.type;}    
  ;
  
  factor returns [char type]
  :   
    NUM
    { 
		generateCode("ldc " + $NUM.text, 1);
		$type = 'i';
	}
	| STRING
    { 
		generateCode("ldc " + $STRING.text, 1);
		$type = 's';
	}
    | OPEN_P ex = expression CLOSE_P
	{	$type = $ex.type; }
    | VARIABLE 
    { 
      if(symbol_table.contains($VARIABLE.text)) {
		int idx = symbol_table.indexOf($VARIABLE.text);
		$type = symbol_type.get(idx);
		String load = ($type == 'i' ? "iload " : "aload ");
        generateCode(load + idx, 1);
        registerVarAccess($VARIABLE.text);
      } else {
        throw new IllegalStateException("Variable '"+$VARIABLE.text+"' undefined on position [" + $VARIABLE.line+ ","+$VARIABLE.getCharPositionInLine()+"]");
      }
    }
    ;
  
