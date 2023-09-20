package Cfscript.config;

import com.oracle.truffle.api.TruffleLanguage;

import java.util.HashMap;
import java.util.Map;

public class CfscriptContext {
    private final TruffleLanguage.Env env;  // The environment in which the language is executed.

    public CfscriptContext(TruffleLanguage.Env env) {
        this.env = env;
    }
    // Here we have global application scope
    public Map<String, Object> application = new HashMap<>();
}
