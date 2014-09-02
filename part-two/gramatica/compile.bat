java -jar antlr-3.2.jar Exp.g
javac -cp antlr-3.2.jar *.java
java -cp .;antlr-3.2.jar ExpParser < entrada.txt > saida.j
java -jar jasmin-2.4.jar saida.j