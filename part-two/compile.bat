rm Cafe.tokens
rm CafeLexer*
rm CafeParser*
rm *.class
rm *.j
java -jar antlr-3.2.jar Cafe.g
javac -cp antlr-3.2.jar *.java
java -cp .;antlr-3.2.jar CafeParser < %1 > Teste.j
java -jar jasmin-2.4.jar Teste.j
java -cp . Teste