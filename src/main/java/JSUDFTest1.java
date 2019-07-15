import org.apache.hadoop.hive.ql.exec.UDF;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JSUDFTest1 extends UDF {
    private static ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
    private static ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");

    static {
        try {
            nashorn.eval("\n" +
                    "var func1 = function(name) {" +
                    "    return \"input is \" + name;" +
                    "};");
        }catch(ScriptException e){
            System.out.println(e.toString());
        }
    }

    public String evaluate(String field) {

        String result;
        try {
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
