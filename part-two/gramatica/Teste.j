.source Teste.j
.class  public Teste
.super  java/lang/Object

;
; Inicializacao padrao (chama inicializacao de java.lang.Object)
;
.method public <init>()V
    aload_0
    invokenonvirtual java/lang/Object/<init>()V
    return
.end method

;
; main() - imprime 3
;
.method public static main([Ljava/lang/String;)V

    ; empilha System.out
    getstatic java/lang/System/out Ljava/io/PrintStream;
    ; calcula a expressao 1 + 2
    ldc 1
    ldc 2
    iadd
    ; chama o metodo PrintStream.println(), usando os dois valores da pilha
    invokevirtual java/io/PrintStream/println(I)V

    ; finaliza o metodo
    return
    
; indica que no maximo tres itens podem ser colocados na pilha
.limit stack 3    
.end method
