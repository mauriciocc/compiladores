rm Cafe.tokens
rm CafeLexer*
rm CafeParser*
rm *.class
rm *.j
java -jar antlr-3.2.jar Cafe.g
javac -cp antlr-3.2.jar *.java
java -cp .;antlr-3.2.jar CafeParser < Entrada.cafe > Entrada.j
java -jar jasmin-2.4.jar Entrada.j
java -cp . Entrada