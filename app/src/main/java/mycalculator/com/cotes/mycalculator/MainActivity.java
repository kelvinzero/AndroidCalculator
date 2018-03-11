package mycalculator.com.cotes.mycalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView inputBox;
    TextView resultBox;
    private String pendingOperation = "";
    private double numberOne = Double.NaN;
    private double numberTwo = Double.NaN;
    private boolean decimalUsed = false;
    private boolean clearNextNumber = false;
    private boolean equalsClicked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputBox = findViewById(R.id.inputText);
        resultBox = findViewById(R.id.resultText);
    }

    public void onClickBackSpace(View v){
        String currentInput = inputBox.getText().toString();
        if(currentInput.endsWith("."))
            decimalUsed = false;
        if(currentInput.startsWith("-")){
            if(currentInput.length() == 2)
                currentInput = "0";
            else
                currentInput = currentInput.substring(0, currentInput.length()-1);
        }
        else{
            if(currentInput.length() == 1)
                currentInput = "0";
            else
                currentInput = currentInput.substring(0, currentInput.length()-1);
        }
        if(currentInput.endsWith(".")) {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
            decimalUsed = false;
        }
        inputBox.setText(currentInput);
    }


    public void onClickClear(View v){
        numberOne = Double.NaN;
        numberTwo = Double.NaN;
        inputBox.setText("0");
        resultBox.setText("");
        pendingOperation = "";
        decimalUsed = false;
        clearNextNumber = false;
        equalsClicked = false;
    }

    public void onClickChangeSign(View v){
        String currentInput = inputBox.getText().toString();

        if(currentInput.equals("0"))
            return;
        if(currentInput.startsWith("-"))
            currentInput = currentInput.substring(1);
        else
            currentInput = "-" + currentInput;
        inputBox.setText(currentInput);
    }

    public void onClickNumber(View v){

        if (clearNextNumber) {
            inputBox.setText("0");
            clearNextNumber = false;
            decimalUsed = false;
        }

        String clicked = ((Button)v).getText().toString();
        String currentInput = inputBox.getText().toString();

        if(clicked.equals(".")){
            if(decimalUsed)
                return;
            else {
                currentInput += ".";
                decimalUsed = true;
            }
        }
        else if(clicked.toLowerCase().equals("rand"))
            currentInput = Math.random() + "";
        else {
            if(currentInput.equals("0"))
                currentInput = clicked;
            else if(currentInput.equals("-0"))
                currentInput = "-" + clicked;
            else
                currentInput += clicked;
        }
        inputBox.setText(currentInput);
    }

    public void onClickEquals(View v){
        if(Double.isNaN(numberOne) || equalsClicked)
            return;
        doOperation();
        equalsClicked = true;
        numberTwo = Double.NaN;
    }

    public void onClickOperator(View v){

        String clicked = ((Button)v).getText().toString();
        if(clicked.toLowerCase().equals("sin") || clicked.toLowerCase().equals("cos") || clicked.equals("!") ||
                clicked.toLowerCase().equals("log\u2082") || clicked.equals("ln") || clicked.toLowerCase().equals("tan") ||
                clicked.toLowerCase().equals("atan") || clicked.toLowerCase().equals("\u221a")){
            pendingOperation = clicked;
            doOperation();
        }
        else {
            if (!equalsClicked)
                doOperation();
            equalsClicked = false;
            pendingOperation = clicked;
        }
        Toast.makeText(this, clicked, Toast.LENGTH_SHORT).show();
    }

    private void doOperation(){

        if(!Double.isNaN(numberOne)){
            numberTwo = Double.parseDouble(inputBox.getText().toString());
            if(pendingOperation.equals("+"))
                numberOne += numberTwo;
            else if(pendingOperation.equals("-"))
                numberOne -= numberTwo;
            else if(pendingOperation.equals("\u00d7"))
                numberOne *= numberTwo;
            else if(pendingOperation.equals("\u00F7"))
                numberOne /= numberTwo;
            else if(pendingOperation.equals("\u221a"))
                numberOne = Math.sqrt(numberTwo);
            else if(pendingOperation.toLowerCase().equals("cos"))
                numberOne = Math.cos(numberTwo);
            else if(pendingOperation.toLowerCase().equals("sin"))
                numberOne = Math.sin(numberTwo);
            else if(pendingOperation.equals("!"))
                numberOne = factorial(numberTwo);
            else if(pendingOperation.toLowerCase().equals("tan"))
                numberOne = Math.tan(numberTwo);
            else if(pendingOperation.toLowerCase().equals("atan"))
                numberOne = Math.atan(numberTwo);
            else if(pendingOperation.toLowerCase().equals("ln"))
                numberOne = Math.log(numberTwo);
            else if(pendingOperation.toLowerCase().equals("log\u2082"))
                numberOne = Math.log(numberTwo)/Math.log(2);
        }
        else{
            numberOne = Double.parseDouble(inputBox.getText().toString());
            if(pendingOperation.equals("\u221a"))
                numberOne = Math.sqrt(numberOne);
            else if(pendingOperation.toLowerCase().equals("cos"))
                numberOne = Math.cos(numberOne);
            else if(pendingOperation.toLowerCase().equals("sin"))
                numberOne = Math.sin(numberOne);
            else if(pendingOperation.equals("!"))
                numberOne = factorial(numberOne);
            else if(pendingOperation.toLowerCase().equals("tan"))
                numberOne = Math.tan(numberOne);
            else if(pendingOperation.toLowerCase().equals("atan"))
                numberOne = Math.atan(numberOne);
            else if(pendingOperation.toLowerCase().equals("ln"))
                numberOne = Math.log(numberOne);
            else if(pendingOperation.toLowerCase().equals("log\u2082"))
                numberOne = Math.log(numberOne)/Math.log(2);
        }
        resultBox.setText(numberOne + "");
        clearNextNumber = true;
    }


    private long factorial(double d){
        int dToI = (int)d;
        long total = dToI;
        for(int i = dToI-1; i > 1; i --)
            total *= i;
        return total;
    }
}
