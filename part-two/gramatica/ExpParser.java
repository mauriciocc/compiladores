// $ANTLR 3.2 Sep 23, 2009 12:02:23 Exp.g 2014-08-26 22:00:50

    //import java.util.ArrayList;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class ExpParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "PLUS", "MINUS", "TIMES", "OVER", "OPEN_P", "CLOSE_P", "NUM", "SPACE"
    };
    public static final int OVER=7;
    public static final int OPEN_P=8;
    public static final int PLUS=4;
    public static final int CLOSE_P=9;
    public static final int MINUS=5;
    public static final int TIMES=6;
    public static final int SPACE=11;
    public static final int EOF=-1;
    public static final int NUM=10;

    // delegates
    // delegators


        public ExpParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public ExpParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return ExpParser.tokenNames; }
    public String getGrammarFileName() { return "Exp.g"; }


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



    // $ANTLR start "program"
    // Exp.g:63:1: program : expression ;
    public final void program() throws RecognitionException {
        try {
            // Exp.g:64:5: ( expression )
            // Exp.g:64:8: expression
            {
            pushFollow(FOLLOW_expression_in_program159);
            expression();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "program"


    // $ANTLR start "expression"
    // Exp.g:67:1: expression : term (op= ( PLUS | MINUS ) term )* ;
    public final void expression() throws RecognitionException {
        Token op=null;

        try {
            // Exp.g:68:5: ( term (op= ( PLUS | MINUS ) term )* )
            // Exp.g:68:9: term (op= ( PLUS | MINUS ) term )*
            {
            pushFollow(FOLLOW_term_in_expression180);
            term();

            state._fsp--;

            // Exp.g:68:14: (op= ( PLUS | MINUS ) term )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>=PLUS && LA1_0<=MINUS)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // Exp.g:68:16: op= ( PLUS | MINUS ) term
            	    {
            	    op=(Token)input.LT(1);
            	    if ( (input.LA(1)>=PLUS && input.LA(1)<=MINUS) ) {
            	        input.consume();
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_term_in_expression198);
            	    term();

            	    state._fsp--;

            	     System.out.println((op!=null?op.getType():0) == PLUS ? "iadd" : "isub"); 

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "expression"


    // $ANTLR start "term"
    // Exp.g:73:1: term : factor (op= ( TIMES | OVER ) factor )* ;
    public final void term() throws RecognitionException {
        Token op=null;

        try {
            // Exp.g:74:5: ( factor (op= ( TIMES | OVER ) factor )* )
            // Exp.g:74:9: factor (op= ( TIMES | OVER ) factor )*
            {
            pushFollow(FOLLOW_factor_in_term231);
            factor();

            state._fsp--;

            // Exp.g:74:16: (op= ( TIMES | OVER ) factor )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>=TIMES && LA2_0<=OVER)) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // Exp.g:74:18: op= ( TIMES | OVER ) factor
            	    {
            	    op=(Token)input.LT(1);
            	    if ( (input.LA(1)>=TIMES && input.LA(1)<=OVER) ) {
            	        input.consume();
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_factor_in_term249);
            	    factor();

            	    state._fsp--;

            	     System.out.println((op!=null?op.getType():0) == TIMES ? "imul" : "idiv"); 

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "term"


    // $ANTLR start "factor"
    // Exp.g:80:1: factor : ( NUM | OPEN_P expression CLOSE_P );
    public final void factor() throws RecognitionException {
        Token NUM1=null;

        try {
            // Exp.g:81:5: ( NUM | OPEN_P expression CLOSE_P )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==NUM) ) {
                alt3=1;
            }
            else if ( (LA3_0==OPEN_P) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // Exp.g:81:9: NUM
                    {
                    NUM1=(Token)match(input,NUM,FOLLOW_NUM_in_factor291); 
                     System.out.println("ldc "+(NUM1!=null?NUM1.getText():null)); 

                    }
                    break;
                case 2 :
                    // Exp.g:83:4: OPEN_P expression CLOSE_P
                    {
                    match(input,OPEN_P,FOLLOW_OPEN_P_in_factor306); 
                    pushFollow(FOLLOW_expression_in_factor308);
                    expression();

                    state._fsp--;

                    match(input,CLOSE_P,FOLLOW_CLOSE_P_in_factor310); 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "factor"

    // Delegated rules


 

    public static final BitSet FOLLOW_expression_in_program159 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_term_in_expression180 = new BitSet(new long[]{0x0000000000000032L});
    public static final BitSet FOLLOW_set_in_expression188 = new BitSet(new long[]{0x0000000000000500L});
    public static final BitSet FOLLOW_term_in_expression198 = new BitSet(new long[]{0x0000000000000032L});
    public static final BitSet FOLLOW_factor_in_term231 = new BitSet(new long[]{0x00000000000000C2L});
    public static final BitSet FOLLOW_set_in_term239 = new BitSet(new long[]{0x0000000000000500L});
    public static final BitSet FOLLOW_factor_in_term249 = new BitSet(new long[]{0x00000000000000C2L});
    public static final BitSet FOLLOW_NUM_in_factor291 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_P_in_factor306 = new BitSet(new long[]{0x0000000000000500L});
    public static final BitSet FOLLOW_expression_in_factor308 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_CLOSE_P_in_factor310 = new BitSet(new long[]{0x0000000000000002L});

}