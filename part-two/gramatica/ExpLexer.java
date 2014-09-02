// $ANTLR 3.2 Sep 23, 2009 12:02:23 Exp.g 2014-08-26 22:00:50

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class ExpLexer extends Lexer {
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

    public ExpLexer() {;} 
    public ExpLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public ExpLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "Exp.g"; }

    // $ANTLR start "PLUS"
    public final void mPLUS() throws RecognitionException {
        try {
            int _type = PLUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Exp.g:3:6: ( '+' )
            // Exp.g:3:8: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PLUS"

    // $ANTLR start "MINUS"
    public final void mMINUS() throws RecognitionException {
        try {
            int _type = MINUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Exp.g:4:7: ( '-' )
            // Exp.g:4:9: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MINUS"

    // $ANTLR start "TIMES"
    public final void mTIMES() throws RecognitionException {
        try {
            int _type = TIMES;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Exp.g:5:7: ( '*' )
            // Exp.g:5:9: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TIMES"

    // $ANTLR start "OVER"
    public final void mOVER() throws RecognitionException {
        try {
            int _type = OVER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Exp.g:6:6: ( '/' )
            // Exp.g:6:8: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OVER"

    // $ANTLR start "OPEN_P"
    public final void mOPEN_P() throws RecognitionException {
        try {
            int _type = OPEN_P;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Exp.g:7:8: ( '(' )
            // Exp.g:7:10: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OPEN_P"

    // $ANTLR start "CLOSE_P"
    public final void mCLOSE_P() throws RecognitionException {
        try {
            int _type = CLOSE_P;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Exp.g:8:9: ( ')' )
            // Exp.g:8:11: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CLOSE_P"

    // $ANTLR start "NUM"
    public final void mNUM() throws RecognitionException {
        try {
            int _type = NUM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Exp.g:57:9: ( ( '0' .. '9' )+ ( '.' ( '0' .. '9' )+ )? )
            // Exp.g:57:11: ( '0' .. '9' )+ ( '.' ( '0' .. '9' )+ )?
            {
            // Exp.g:57:11: ( '0' .. '9' )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='0' && LA1_0<='9')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // Exp.g:57:11: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);

            // Exp.g:57:20: ( '.' ( '0' .. '9' )+ )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='.') ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // Exp.g:57:21: '.' ( '0' .. '9' )+
                    {
                    match('.'); 
                    // Exp.g:57:25: ( '0' .. '9' )+
                    int cnt2=0;
                    loop2:
                    do {
                        int alt2=2;
                        int LA2_0 = input.LA(1);

                        if ( ((LA2_0>='0' && LA2_0<='9')) ) {
                            alt2=1;
                        }


                        switch (alt2) {
                    	case 1 :
                    	    // Exp.g:57:25: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt2 >= 1 ) break loop2;
                                EarlyExitException eee =
                                    new EarlyExitException(2, input);
                                throw eee;
                        }
                        cnt2++;
                    } while (true);


                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NUM"

    // $ANTLR start "SPACE"
    public final void mSPACE() throws RecognitionException {
        try {
            int _type = SPACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Exp.g:58:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // Exp.g:58:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // Exp.g:58:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            int cnt4=0;
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0>='\t' && LA4_0<='\n')||LA4_0=='\r'||LA4_0==' ') ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // Exp.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt4 >= 1 ) break loop4;
                        EarlyExitException eee =
                            new EarlyExitException(4, input);
                        throw eee;
                }
                cnt4++;
            } while (true);

             skip(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SPACE"

    public void mTokens() throws RecognitionException {
        // Exp.g:1:8: ( PLUS | MINUS | TIMES | OVER | OPEN_P | CLOSE_P | NUM | SPACE )
        int alt5=8;
        switch ( input.LA(1) ) {
        case '+':
            {
            alt5=1;
            }
            break;
        case '-':
            {
            alt5=2;
            }
            break;
        case '*':
            {
            alt5=3;
            }
            break;
        case '/':
            {
            alt5=4;
            }
            break;
        case '(':
            {
            alt5=5;
            }
            break;
        case ')':
            {
            alt5=6;
            }
            break;
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
            {
            alt5=7;
            }
            break;
        case '\t':
        case '\n':
        case '\r':
        case ' ':
            {
            alt5=8;
            }
            break;
        default:
            NoViableAltException nvae =
                new NoViableAltException("", 5, 0, input);

            throw nvae;
        }

        switch (alt5) {
            case 1 :
                // Exp.g:1:10: PLUS
                {
                mPLUS(); 

                }
                break;
            case 2 :
                // Exp.g:1:15: MINUS
                {
                mMINUS(); 

                }
                break;
            case 3 :
                // Exp.g:1:21: TIMES
                {
                mTIMES(); 

                }
                break;
            case 4 :
                // Exp.g:1:27: OVER
                {
                mOVER(); 

                }
                break;
            case 5 :
                // Exp.g:1:32: OPEN_P
                {
                mOPEN_P(); 

                }
                break;
            case 6 :
                // Exp.g:1:39: CLOSE_P
                {
                mCLOSE_P(); 

                }
                break;
            case 7 :
                // Exp.g:1:47: NUM
                {
                mNUM(); 

                }
                break;
            case 8 :
                // Exp.g:1:51: SPACE
                {
                mSPACE(); 

                }
                break;

        }

    }


 

}