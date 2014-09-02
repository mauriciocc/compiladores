import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class LexicalAnalyser {

  private static final Pattern symbolPattern = Pattern.compile("[+\\-*/()]");
  private static final Pattern numericPattern = Pattern.compile("([0-9\\.]+)");
  private char[] entrada;
  private int indice;
  private List<Token> tokens;

  public LexicalAnalyser(String entrada) {
    this.entrada = entrada.toCharArray();
    this.indice = 0;
  }

  private Token getToken() {
    String value = "";
    for(; indice < entrada.length; indice++) {
      char character = entrada[indice];

      if(Character.isWhitespace(character)) {
        if(value.isEmpty()) {
          continue;
        } else {
          indice++;
          return new Token(Double.valueOf(value));
        }
      }

      if(symbolPattern.matcher(""+character).matches()) {
        if(value.isEmpty()) {
          indice++;
          return new Token(character);
        } else {
          return new Token(Double.valueOf(value));
        }
      }

      if(numericPattern.matcher(""+character).matches()) {
        value += character;
      }

    }
    if(value.isEmpty()) {
      return null;
    } else {
      return new Token(Double.valueOf(value));
    }
  }

  public List<Token> analyse() {
    if (tokens == null) {
      tokens = new ArrayList<Token>();
      while(indice < entrada.length) {
        Token token = getToken();
        if(token != null) {
          tokens.add(token);
        }
      }
    }
    return tokens;
  }
}