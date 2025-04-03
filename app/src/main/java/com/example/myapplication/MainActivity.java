package com.example.myapplication;



import android.os.Bundle;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    static String textoAtual="";
    static boolean calSemErro=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


    }
   public void onRegisterBtnClick(View view) {
       TextView textView=findViewById(R.id.textView);
       TextView result=findViewById(R.id.result);

       int id=view.getId();
       if(id==R.id.button_equal){
           String resultado;
           try{
               resultado=ArithmeticCalculator.calculate(textoAtual);
           }
           catch(SyntaxErrorException e){
               resultado="Sintax Error";
           }
           catch(ArithmeticException e){
               resultado="Math Error";
           }
           if(resultado.indexOf('r')==-1)
               calSemErro=true;
           result.setText(resultado);

       }

       else{
           if(id==R.id.button_del){
               if(textoAtual.length() > 0&&!calSemErro)
                    textoAtual = textoAtual.substring(0, textoAtual.length() - 1);
           }
           else if(id==R.id.buttonC){
               textoAtual="";
               result.setText("0");
               calSemErro=false;
           }
       else if(textoAtual.length()<50){
           if(id==R.id.button_add||id==R.id.button_sub||id==R.id.button_mul||id==R.id.button_div){
               if(calSemErro) {
                   textoAtual=result.getText().toString();
                   calSemErro=false;
               }
           if(id==R.id.button_add){
               textoAtual= textoAtual + "+";
           }
           else if(id==R.id.button_sub){
               textoAtual= textoAtual + "-";
           }
           else if(id==R.id.button_mul){
               textoAtual= textoAtual + "x";
           }
           else if(id==R.id.button_div){
               textoAtual= textoAtual + "/";
           }
           }
           else {
               if(calSemErro) {
                   textoAtual="";
                   calSemErro=false;
               }
               if (id == R.id.button0) {
                   textoAtual = textoAtual + "0";
               } else if (id == R.id.button1) {
                   textoAtual = textoAtual + "1";
               } else if (id == R.id.button2) {
                   textoAtual = textoAtual + "2";
               } else if (id == R.id.button3) {
                   textoAtual = textoAtual + "3";
               } else if (id == R.id.button4) {
                   textoAtual = textoAtual + "4";
               } else if (id == R.id.button5) {
                   textoAtual = textoAtual + "5";
               } else if (id == R.id.button6) {
                   textoAtual = textoAtual + "6";
               } else if (id == R.id.button7) {
                   textoAtual = textoAtual + "7";
               } else if (id == R.id.button8) {
                   textoAtual = textoAtual + "8";
               } else if (id == R.id.button9) {
                   textoAtual = textoAtual + "9";
               } else if (id == R.id.button_dot) {
                   textoAtual = textoAtual + ".";
               } else if (id == R.id.button_clo_par) {
                   textoAtual = textoAtual + ")";
               } else if (id == R.id.button_op_par) {
                   textoAtual = textoAtual + "(";
               }
           }


        }
           textView.setText(textoAtual);
       }


       }



    }










