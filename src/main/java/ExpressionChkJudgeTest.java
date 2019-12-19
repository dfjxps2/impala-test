import org.apache.hadoop.hive.ql.exec.UDF;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ExpressionChkJudgeTest extends UDF {
    private static ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
    private static ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");

    static {
        try {
            nashorn.eval("var ExpressionChkJudgeTest = function(name) { return 0;};");
//            nashorn.eval("\n" +
//                    "var func1 = function(name) {" +
//                    "    return \"input is \" + name;" +
//                    "};");
        }catch(ScriptException e){
            System.out.println(e.toString());
        }
    }

    public int evaluate(String field) {

        int result;
        try {
            Invocable invocable = (Invocable)nashorn;
            result = (Integer)invocable.invokeFunction("ExpressionChkJudgeTest", field);
        }catch (ScriptException e1){
            return 0;
        }catch (NoSuchMethodException e2){
            return 0;
        }
        return result;
    }

    public static void main(String[] args){
        System.out.println(new ExpressionChkJudgeTest().evaluate("abcd"));
    }
}
