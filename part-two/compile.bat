java -jar antlr-3.2.jar Cafe.g
javac -cp antlr-3.2.jar *.java
java -cp .;antlr-3.2.jar CafeParser < entrada.txt > assembly.j
java -jar jasmin-2.4.jar assembly.j