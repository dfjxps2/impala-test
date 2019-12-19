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

public class ExpressionWashTrans118 extends UDF {
    private static ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
    private static ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");

    static {
        try {
            InputStream is=ExpressionWashTrans118.class.getResourceAsStream("/ExpressionWashTrans118.js");
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

    public String evaluate(String field) {

        String result;
        try {
            Invocable invocable = (Invocable)nashorn;
            result = (String)invocable.invokeFunction("ExpressionWashTrans118", field).toString();
        }catch (ScriptException e1){
            return e1.toString();
        }catch (NoSuchMethodException e2){
            return e2.toString();
        }
        return result;
    }

    public static void main(String[] args){
        ExpressionWashTrans118 js = new ExpressionWashTrans118();
        System.out.println(js.evaluate("aaaa"));
    }
}
