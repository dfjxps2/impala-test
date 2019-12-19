package com.dfjx;

import org.apache.hadoop.hive.ql.exec.UDF;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class ExpressionWashJudge118 extends UDF {
    private static ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
    private static ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");

    static {
        try {
            InputStream is=ExpressionWashJudge118.class.getResourceAsStream("/ExpressionWashJudge118.js");
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            String s="";
            String js="";
            try {
                while((s=br.readLine())!=null)
                    js+=s + "\n";
            }catch(IOException io){
                System.out.println("error:" + io.getMessage());
            }
            System.out.println("js code"+js);
            nashorn.eval(js);
        }catch(ScriptException e){
            System.out.println(e.toString());
        }
    }

    public Integer evaluate(String field) {

        int result;
        try {
            Invocable invocable = (Invocable)nashorn;
            result = Integer.parseInt(invocable.invokeFunction("ExpressionWashJudge118", field).toString());
        }catch (ScriptException e1){
            return -1;
        }catch (NoSuchMethodException e2){
            return -1;
        }
        return result;
    }

    public static void main(String[] args){
        ExpressionWashJudge118 js = new ExpressionWashJudge118();
        System.out.println(js.evaluate("aaaa"));
    }
}
