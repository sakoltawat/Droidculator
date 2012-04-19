package com.android.Droidculator;

import java.util.Stack;
import java.util.StringTokenizer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class DroidculatorActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    public void ButtonOnClick(View v){
    	EditText text1 = (EditText) findViewById(R.id.EditText);
    	
    	switch(v.getId()){
    		case R.id.num1_button:
    			text1.append("1");
    			break;
    		case R.id.num2_button:
    			text1.append("2");
    			break;
    		case R.id.num3_button:
    			text1.append("3");
    			break;
    		case R.id.num4_button:
    			text1.append("4");
    			break;
    		case R.id.num5_button:
    			text1.append("5");
    			break;
    		case R.id.num6_button:
    			text1.append("6");
    			break;
    		case R.id.num7_button:
    			text1.append("7");
    			break;
    		case R.id.num8_button:
    			text1.append("8");
    			break;
    		case R.id.num9_button:
    			text1.append("9");
    			break;
    		case R.id.num0_button:
    			text1.append("0");
    			break;
    		case R.id.point_button:
    			text1.append(".");
    			break;
    		case R.id.plus_button:
    			text1.append("+");
    			break;
    		case R.id.minus_button:
    			text1.append("-");
    			break;
    		case R.id.multiple_button:
    			text1.append("*");
    			break;
    		case R.id.divide_button:
    			text1.append("/");
    			break;
    		case R.id.Bracketopen_button:
    			text1.append("(");
    			break;
    		case R.id.Bracketclose_button:
    			text1.append(")");
    			break;
    		case R.id.del_button:
    			if(text1.getText().toString().length() > 0){
	    			 String strText1Tmp = text1.getText().toString().substring(0, text1.getText().toString().length()-1);
		   			 
	    			 text1.setText("");
	    			 text1.append(strText1Tmp);
    		}
    			break;
    		case R.id.clear_button:
    			text1.setText("");
    			
    			break;
    		case R.id.equal_button:
   			
   			 String strStack = toPostfix(text1.getText().toString());
   		     float intValue = Calculator(strStack);
	   		
	   			 text1.setText(Float.toString(intValue));
	   			 break;
    	}
    }
    public static int getPriority(char chaOperator){
    	if(chaOperator == '+' || chaOperator == '-'){
    		return 1;
    	}else if(chaOperator == '*' || chaOperator == '/'){
    		return 2;
    	}
    	return 0;
    }
    public static boolean isFloat(String strInput){
    	try{
    		Float.parseFloat(strInput);
    		return true;
    	}catch(Exception e){
    		return false;
    	}
    }
    
   
    
    public static String toPostfix(String strInfix){
		String strExpression;
		String strPostfix = " ";

		strInfix  = strInfix.replaceAll("\\+|\\(|\\)|-|\\*|/", " $0 ");
		StringTokenizer strToken = new StringTokenizer(strInfix);
		
		Stack<Character> operatorStack = new Stack<Character>();

		while(strToken.hasMoreTokens()){
			strExpression = strToken.nextToken();
			
			if(Character.isDigit(strExpression.charAt(0))){
				strPostfix = strPostfix + " " + Float.parseFloat(strExpression);
			}else if(strExpression.equals("(")){
				Character operator = new Character('(');
				operatorStack.push(operator);
			}else if (strExpression.equals(")")){
				while(((Character) operatorStack.peek()).charValue() != '('){
					strPostfix = strPostfix + " " + operatorStack.pop();
				}
				
				operatorStack.pop();
			}else{
				
				while(!operatorStack.isEmpty() && !(operatorStack.peek()).equals("(") && getPriority(strExpression.charAt(0)) <= getPriority(((Character) operatorStack.peek()).charValue())){
					strPostfix = strPostfix + " " + operatorStack.pop();
				}

				Character operator = new Character(strExpression.charAt(0));
				operatorStack.push(operator);
			}
		}
		
		while(!operatorStack.isEmpty()){
			strPostfix = strPostfix + " " + operatorStack.pop();
		}

		return strPostfix;
	}
    public static float Calculator(String strPostfix) {
    	float a;
    	float b;
    	float result = 0;
		
    	String[] arrPostfix = strPostfix.split(" ");
	    Stack<Float> CalStack = new Stack<Float>();

		for(int i = 0; i < arrPostfix.length; i++){
			String ch = arrPostfix[i];

			if(isFloat(ch)){
				CalStack.push(Float.parseFloat(ch));
			}else{
				
				if(ch.equals("+")){
					a = CalStack.pop();
					b = CalStack.pop();
					result = a + b;
					CalStack.push(result);
				}else if(ch.equals("-")){
					a = CalStack.pop();
					b = CalStack.pop();
					result = b - a;
					CalStack.push(result);
				}else if(ch.equals("*")){
					a = CalStack.pop();
					b = CalStack.pop();
					result = a * b;
					CalStack.push(result);
				}else if(ch.equals("/")){
					a = CalStack.pop();
					b = CalStack.pop();
					result = b / a;
					CalStack.push(result);
				}
			}
		}
		return result;
	}
	
}