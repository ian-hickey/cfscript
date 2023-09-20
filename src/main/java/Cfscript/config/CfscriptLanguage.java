package Cfscript.config;

import com.oracle.truffle.api.TruffleLanguage;


@TruffleLanguage.Registration(id = "cfscript", name = "CFScript", defaultMimeType = "text/x-cfscript", version = "1.0")
public class CfscriptLanguage extends TruffleLanguage<CfscriptContext> {
    static String ID = "cfscript";

    public CfscriptLanguage() {
        System.out.println("Ctor for CfscriptLanguage Called");
    }

    protected CfscriptContext createContext(TruffleLanguage.Env env) {
        System.out.println("CfscriptLanguage Context Called");
        return new CfscriptContext(env);
    }


    protected boolean isThreadAccessAllowed(Thread thread, boolean singleThreaded) {
        return true;  // For simplicity; you might need more sophisticated logic here.
    }

    protected Object getLanguageGlobal(CfscriptContext context) {
        return null;  // We don't have a global object for this simple example.
    }

    protected boolean isObjectOfLanguage(Object value) {
        return false;  // For this simple example; usually, you'll check if the value is a TruffleObject of your language.
    }

}
