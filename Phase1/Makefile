build: minijava.jj jtb.jar
		java -jar jtb.jar minijava.jj
		javacc jtb.out.jj

typechecking: Typecheck.java
		javac Typecheck.java
		java Typecheck < P.java

clean:
			rm -rf *.class
			rm -rf *.tgz
			rm -rf Phase1Tester/Output
			rm -rf typechecker/*.class
			#rm -v **/.DS_Store

test: Typecheck.java
	mkdir hw1
	cp Typecheck.java hw1/
	cp -R typechecker/ hw1/typechecker
	tar zcf hw1.tgz hw1/
	rm -rf hw1
	chmod +x Phase1Tester/run
	Phase1Tester/run Phase1Tester/SelfTestCases hw1.tgz
