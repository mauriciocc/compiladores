public class Token {

  private char tipo;
  private double valor;

  public Token(char tipo) {
    this.tipo = tipo;
  }

  public Token(double valor) {
    this.tipo = 'n';
    this.valor = valor;
  }

  public char getType() {
    return this.tipo;
  }

  public double getValue() {
    return this.valor;
  }

  public String toString() {
    return "["+ (tipo == 'n' ? tipo + ", " + valor : "" + tipo)+"]";
  }

}