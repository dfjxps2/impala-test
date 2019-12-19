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

public class ExpressionChkJudge56 extends UDF {
    private static ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
    private static ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");

    static {
        try {
            InputStream is=ExpressionChkJudge56.class.getResourceAsStream("/" + "ExpressionChkJudge56" + ".js");
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            String s="";
            String js="";
            try {
                while((s=br.readLine())!=null)
                    js+=s;
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
            result = Integer.parseInt(invocable.invokeFunction("ExpressionChkJudge56", field).toString());
        }catch (ScriptException e1){
            return -1;
        }catch (NoSuchMethodException e2){
            return -1;
        }
        return result;
    }

    public static void main(String[] args){
        ExpressionChkJudge56 js = new ExpressionChkJudge56();
        System.out.println(js.evaluate("aaaa"));
    }
}
