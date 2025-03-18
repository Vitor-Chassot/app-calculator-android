package com.example.myapplication;

import static com.example.myapplication.Solution.calcular;

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
           String resultado=calcular(textoAtual);
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
    /*private static String getLastXChars(String str,int x){
        int length=str.length();
        if(x>=length)
            return str;
        else
            return str.substring(length-x);
    }*/


class NodeC {
    char c;
    NodeC next;

    public NodeC(char c) {
        this.c = c;
        this.next = null;
    }

}
class Solution {
    private static final MathContext PRECISAO_50_DIGITOS=new MathContext(50,RoundingMode.HALF_UP);



    public static String calcular (String expressao){
        if (expressao.equals(""))
            return "0";
        char [] Cexp=expressao.toCharArray();
        NodeC head=new NodeC(Cexp[0]);
        NodeC aux=head;
        for(int i=1;i<Cexp.length;i++){
            aux.next=new NodeC(Cexp[i]);
            aux=aux.next;

        }
        int erro=calcular2(head,aux);
        if(erro==2)
            return "Math Error";
        else if(erro==1)
            return "Sintax Error";
        else{
            String result=stringNumFromList(head);
            if(result.length()<=15)
                return result;
            else
                return result.substring(0,15);

        }



    }
    private static int calcular2 (NodeC head,NodeC tail){



        NodeC aux=head;
        NodeC auxB=null;
        while(aux!=null){
            if(aux.c=='('){

                if(auxB!=null&&auxB.c!='+'&&auxB.c!='-'&&auxB.c!='x'&&auxB.c!='/')
                    return 1; //1 ==codigo de Sintax error;
                int acuPar = 1;
                NodeC staPar=aux.next;
                NodeC endPar;
                if(auxB==null){
                    endPar=new NodeC('n');
                    endPar.next=aux;       //apontar para aux caso auxB==null
                }
                else
                    endPar=auxB;
                NodeC auxPar=staPar;
                while(auxPar!=null&&acuPar>0){
                    if(auxPar.c=='(')
                        acuPar++;
                    else if(auxPar.c==')')
                        acuPar--;
                    endPar=endPar.next;
                    auxPar=auxPar.next;
                }
                if(acuPar==0){

                    if(auxPar!=null&&auxPar.c!='+'&&auxPar.c!='-'&&auxPar.c!='x'&&auxPar.c!='/')
                        return 1;
                    if(auxB==null){
                        head.c=staPar.c;
                        head.next=staPar.next;
                    }
                    else
                        auxB.next=staPar;
                    endPar.next=auxPar;
                    int erro;
                    if (auxB==null)
                        erro=calcular2(head,endPar);
                    else
                        erro=calcular2(staPar,endPar);
                    if(erro==1)
                        return 1;
                    else if(erro==2)
                        return 2;     //2 ==codigo de Sintax error;
                    if(auxPar==null||auxPar.next==null)
                        break;
                    else{
                        aux=auxPar.next;
                        auxB=auxPar;
                    }
                }
                else{

                    return 1;

                }
            }
            else{


                auxB=aux;
                aux=aux.next;}

        }
        return calcular3(head,tail);
    }
    private static int calcular3(NodeC head,NodeC tail){
        NodeC aux;
        boolean neg=false;
        if(head.c=='+')
            aux=head.next;
        else
            aux=head;
        boolean ponto=false;
        while(aux!=tail.next){
            while(aux!=tail.next&&(aux.c=='-'||aux.c=='+')){
                aux=aux.next;

            }
            if(aux==tail.next)
                return 1;
            if((aux.c<'0'||aux.c>'9')&&(aux.c!='.'))
                return 1;
            if(aux.c=='.'){
                aux=aux.next;
                if(aux==null||(aux.c<'0'||aux.c>'9'))
                    return 1;
                while(aux!=tail.next&&aux.c>='0'&&aux.c<='9')
                    aux=aux.next;
            }
            else{
                while(aux!=tail.next&&aux.c>='0'&&aux.c<='9')
                    aux=aux.next;
                if(aux!=tail.next&&aux.c=='.'){
                    aux=aux.next;
                    while(aux!=tail.next&&aux.c>='0'&&aux.c<='9')
                        aux=aux.next;
                }
            }
            if(aux!=tail.next&&(aux==tail||(aux.c!='+'&&aux.c!='-'&&aux.c!='x'&&aux.c!='/')))
                return 1;
            if(aux==tail.next)
                break;
            aux=aux.next;
        }

        return calcular4(head,tail);

    }
    private static int calcular4(NodeC head,NodeC tail){


        int i;
        int ind;
        int len;
        String a="";
        String b="";
        NodeC aux;
        NodeC aux2;
        NodeC auxIni;
        BigDecimal numA;
        BigDecimal numB;
        BigDecimal result;
        String strResult="";
        char [] arrayRes;
        boolean neg=false;
        boolean first=true;
        if(head.c=='+')
            aux=head.next;
        else
            aux=head;
        while(aux!=null&&aux!=tail.next){

            first=true;
            do{  a="";
                auxIni=aux;
                neg=false;
                while(aux!=tail&&(aux.c=='+'||aux.c=='-')){
                    if(aux.c=='-')
                        neg=!neg;

                    aux=aux.next;


                }
                if(neg)
                    a=a+'-';

                while(aux!=null&&aux!=tail.next&&((aux.c>='0'&&aux.c<='9')||aux.c=='.')){

                    if(!first||aux.c!='0'){
                        a=a+aux.c;
                        first=false;
                    }

                    aux=aux.next;
                }
                ind=a.indexOf('.');
                len=a.length();

                if(ind>=16||(ind==-1&&len>=16))
                    return 2;
                aux2=aux;
                if(aux!=tail&&aux!=null)
                    aux=aux.next;
            }while(aux2!=tail&&aux2!=null&&aux2.c!='x'&&aux2.c!='/');
            if(aux2!=tail&&aux2!=null&&(aux2.c=='x'||aux2.c=='/')){

                result=new BigDecimal(a,PRECISAO_50_DIGITOS);
                while(aux2!=tail&&aux2!=null&&(aux2.c=='x'||aux2.c=='/')){

                    char op=aux2.c;
                    b="";
                    neg=false;
                    while(aux!=tail&&(aux.c=='+'||aux.c=='-')){
                        if(aux.c=='-')
                            neg=!neg;

                        aux=aux.next;


                    }
                    if(neg)
                        b=b+'-';

                    while(aux!=null&&aux!=tail.next&&((aux.c>='0'&&aux.c<='9')||aux.c=='.')){

                        b=b+aux.c;
                        aux=aux.next;
                    }
                    ind=b.indexOf('.');
                    len=b.length();
                    if(ind>=16||(ind==-1&&len>=16))
                        return 2;

                    numB=new BigDecimal(b,PRECISAO_50_DIGITOS);
                    if(op=='x'){
                        result=result.multiply(numB,PRECISAO_50_DIGITOS);

                    }
                    else{ result=result.divide(numB, PRECISAO_50_DIGITOS);


                    }
                    result = result.setScale(result.scale() <= 0 ? 0 : result.stripTrailingZeros().scale(), RoundingMode.HALF_UP);
                    strResult = result.toString();
                    ind=strResult.indexOf('.');
                    len=strResult.length();
                    if(ind>=16||(ind==-1&&len>=16))
                        return 2;
                    aux2=aux;
                    if(aux!=tail&&aux!=null)
                        aux=aux.next;
                }

                arrayRes=strResult.toCharArray();
                auxIni.c=arrayRes[0];
                for (i=1;i<arrayRes.length;i++){
                    auxIni.next=new NodeC(arrayRes[i]);
                    auxIni=auxIni.next;
                }

                auxIni.next=aux2;




            }

        }
        result=new BigDecimal("0",PRECISAO_50_DIGITOS);
        aux=head;
        while(aux!=null&&aux!=tail.next){
            b="";
            neg=false;
            while(aux!=tail&&(aux.c=='+'||aux.c=='-')){
                if(aux.c=='-')
                    neg=!neg;

                aux=aux.next;


            }
            if(neg)
                b=b+'-';
            while(aux!=null&&aux!=tail.next&&((aux.c>='0'&&aux.c<='9')||aux.c=='.')){
                b=b+aux.c;
                aux=aux.next;
            }
            ind=b.indexOf('.');
            len=b.length();
            if(ind>=16||(ind==-1&&len>=16))
                return 2;

            numB=new BigDecimal(b,PRECISAO_50_DIGITOS);
            result=result.add(numB,PRECISAO_50_DIGITOS);
            result = result.setScale(result.scale() <= 0 ? 0 : result.stripTrailingZeros().scale(), RoundingMode.HALF_UP);
            strResult = result.toString();
            ind=strResult.indexOf('.');
            len=strResult.length();
            if(ind>=16||(ind==-1&&len>=16))
                return 2;
        }
        arrayRes=strResult.toCharArray();
        auxIni=head;
        auxIni.c=arrayRes[0];
        for (i=1;i<arrayRes.length;i++){
            auxIni.next=new NodeC(arrayRes[i]);
            auxIni=auxIni.next;
        }

        auxIni.next=tail.next;
        return 0;
    }
    private static String stringNumFromList(NodeC head){
        String result="";
        while(head!=null){
            result+=head.c;
            head=head.next;
        }
        return result;
    }
    private static String stringNumFromList2(NodeC head,NodeC tail){
        String result="";
        while(head!=tail.next){
            result+=head.c;
            head=head.next;
        }
        return result;
    }
    private static BigDecimal dividirComPrecisao(BigDecimal dividendo, BigDecimal divisor) {
        // Configura a precisão máxima para 50 dígitos
        MathContext mc = new MathContext(50, RoundingMode.HALF_UP);

        // Realiza a divisão
        BigDecimal resultado = dividendo.divide(divisor, mc);

        // Reduz a escala (casas decimais) ao mínimo necessário
        resultado = resultado.setScale(resultado.scale() <= 0 ? 0 : resultado.stripTrailingZeros().scale(), RoundingMode.HALF_UP);

        return resultado;
    }



}