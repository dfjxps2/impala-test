import org.apache.hadoop.hive.ql.exec.UDF;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JSUDFTest2 extends UDF {

    public String evaluate(String field) {

        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");

        String result;
        try {
            nashorn.eval("\n" +
                    "var func1 = function(name) {" +
                    "    return \"input is \" + name;" +
                    "};");
            Invocable invocable = (Invocable)nashorn;
            result = (String)invocable.invokeFunction("func1", field);
        }catch (ScriptException e1){
            return e1.toString();
        }catch (NoSuchMethodException e2){
            return e2.toString();
        }
        return result;
    }
}
