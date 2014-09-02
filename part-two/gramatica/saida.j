.source saida.j
.class  public saida
.super  java/lang/Object
.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/<init>()V
return
.end method
.method public static main([Ljava/lang/String;)V
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc 1
ldc 2
ldc 3
imul
ldc 2
idiv
iadd
invokevirtual java/io/PrintStream/println(I)V
return
.limit stack 50
.end method
