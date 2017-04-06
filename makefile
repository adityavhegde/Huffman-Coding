
# Initialize
JCC = javac
JFLAGS = -g
RM = rm

# Build
default: decoder.class encoder.class

decoder.class: decoder.java
	$(JCC) $(JFLAGS) decoder.java

encoder.class: encoder.java
	$(JCC) $(JFLAGS) encoder.java

# Clean
clean:
	$(RM) *.class
