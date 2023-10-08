module cfscript {
    requires org.antlr.antlr4.runtime;
    requires com.fasterxml.jackson.databind;
    requires encoder;
    exports org.ionatomics;
    exports cfscript.parser;
    exports cfscript.typewriter;
}