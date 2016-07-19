package com.example.ahemenson.calculadora;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.Pattern;


public class MainActivity extends Activity {

    private TextView textView_screen;
    private String display = "";
    private String currentOperator = "";
    private String result = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView_screen = (TextView) findViewById(R.id.textView);
        textView_screen.setText(display);
    }

    private void updateScreen(){
        textView_screen.setText(display);
    }

    public void onClickNumber(View view){
        if(result != ""){
            clear();
            updateScreen();
        }
        Button b = (Button) view;
        display += b.getText();
        updateScreen();
    }

    private boolean isOperator(char op){
        switch (op){
            case '+':
            case '-':
            case 'x':
            case '/':
                return true;
            default:
                return false;
        }
    }

    public void onClickOperator(View view){
        if(display == ""){
            return;
        }
        Button bt = (Button) view;
        if(result != ""){
            String _display2 = result;
            clear();
            display = _display2;
        }
        if(currentOperator != ""){
            Log.d("CalcX", ""+display.charAt(display.length()-1));
            if (isOperator(display.charAt(display.length()-1))){
                display = display.replace(display.charAt(display.length()-1), bt.getText().charAt(0));
                updateScreen();
                return;
            }else{
                getResult();
                display = result;
                result = "";
            }
            currentOperator = bt.getText().toString();
        }
        display += bt.getText();
        currentOperator = bt.getText().toString();
        updateScreen();
    }

    private double operate(String a, String b, String op){
        switch (op){
            case "+":
                return Double.valueOf(a) + Double.valueOf(b);
            case "-":
                return Double.valueOf(a) - Double.valueOf(b);
            case "/":
                try {
                    return Double.valueOf(a) / Double.valueOf(b);
                }catch (Exception e){
                    Log.d("Calc", e.getMessage());
                }
            case "x":
                return Double.valueOf(a) * Double.valueOf(b);
            default:
                return -1;
        }
    }

    private void clear(){
        display = "";
        currentOperator = "";
        result = "";
    }

    public void onClickClear(View view){
        clear();
        updateScreen();
    }

    private boolean getResult(){
        if(currentOperator == ""){
            return false;
        }
        String[] operation = display.split(Pattern.quote(currentOperator));
        if(operation.length < 2){
            return false;
        }
        result = String.valueOf(operate(operation[0], operation[1], currentOperator));
        return true;
    }

    public void onClickEqual(View view){
        if(display == ""){
            return;
        }
        if(!getResult()){
            return;
        }
        textView_screen.setText(display + "\n" + String.valueOf(result));
    }

}
