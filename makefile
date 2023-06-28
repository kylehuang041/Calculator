run:
	javac -cp lib/commons-math3-3.6.1.jar src/Calculator.java src/Calculation.java
	java -cp src:lib/commons-math3-3.6.1.jar Calculator