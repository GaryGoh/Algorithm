# Pseudo-function to turn create a classpath from a list.
# Abstracted from http://stackoverflow.com/a/9458230
classpathify=$(subst $(eval) ,:,$1)

SRCDIR=src

LIBDIR=lib
LIBS=daj_port.jar awt_port.jar

BUILDDIR=build
CLASSDIR=$(BUILDDIR)/classes

JAVA=java
JAVAC=javac
CLASSPATH=$(call classpathify,$(CLASSDIR) $(addprefix $(LIBDIR)/,$(LIBS)))

TARGET=Main

.PHONY: all
all: clean run

.PHONY: run
run: $(CLASSDIR)/$(TARGET).class
	$(JAVA) -classpath $(CLASSPATH) $(TARGET)

$(CLASSDIR)/%.class: $(SRCDIR)/%.java $(CLASSDIR)
	$(JAVAC) -sourcepath $(SRCDIR) -d $(CLASSDIR) -classpath $(CLASSPATH) $<

$(CLASSDIR): $(BUILDDIR)
	mkdir $(CLASSDIR)

$(BUILDDIR):
	mkdir $(BUILDDIR)

.PHONY: clean
clean:
	rm -rf $(BUILDDIR)
