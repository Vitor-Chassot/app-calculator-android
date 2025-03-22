
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
//import java.util.Scanner;


public class Solution {
    
    private static final MathContext PRECISAO_50_DIGITOS=new MathContext(50,RoundingMode.HALF_UP);
    

    public static void main(String[] args) {
        
        
        long startTime = System.nanoTime(); // Marca o tempo inicial
        
        
            Solution.calcular("(((((3.5+2.5)x((4-.5)/(2+.3)))+((7-2)x(3/(1.5+0.5))))-((8/4)+((6.25-.25)x2)))x(1+1))");
       

        long endTime = System.nanoTime(); // Marca o tempo final2
        
        long durationInicio = endTime - startTime; // Tempo total em nanossegundos
        double durationMs = durationInicio/ 1_000_000.0; // Convertendo para 
        int amostra=1000000;
        startTime = System.nanoTime();
        
        for (int i = 0; i < amostra; i++) {
            Solution.calcular("(((((3.5+2.5)x((4-.5)/(2+.3)))+((7-2)x(3/(1.5+0.5))))-((8/4)+((6.25-.25)x2)))x(1+1))");
        }

        endTime = System.nanoTime(); // Marca o tempo final222
        
        long duration = endTime - startTime; // Tempo total em nanossegundos
        durationMs = duration / 1_000_000.0; // Convertendo para milissegundos
        
        System.out.println("Tempo total: " + durationMs + " ms");
        System.out.println("Media: " + (durationMs/amostra) + " ms");
        
        System.out.println(Solution.calcular("(((((3.5+2.5)x((4-.5)/(2+.3)))+((7-2)x(3/(1.5+0.5))))-((8/4)+((6.25-.25)x2)))x(1+1))"));
    }
    public static void CreateLinkedList(String expressao, LinkedListWrapper listWrapper) {
        char[] Cexp = expressao.toCharArray();

        // Criar o primeiro nó (head)
        listWrapper.head = new NodeC(Cexp[0]);
        NodeC aux = listWrapper.head;
    
        // Criar os próximos nós
        for (int i = 1; i < Cexp.length; i++) {
            aux.next = new NodeC(Cexp[i]); // Criar próximo nó
            aux.next.ant = aux;            // Conectar o nó anterior
            aux = aux.next;                // Mover ponteiro para o novo nó
        }
        aux.next=null;
        // O último nó é o tail
        listWrapper.tail = aux;
    }
    
    public static String calcular (String expressao){
        
        if (expressao.contentEquals(""))
            return "0";
        LinkedListWrapper listWrapper = new LinkedListWrapper();
        CreateLinkedList(expressao, listWrapper);
        NodeC head=listWrapper.head;
        NodeC tail=listWrapper.tail;
        int erroSintax=testSintaxError(head, tail);
        if(erroSintax==1)
        return "Sintax Error";
        int mathError=evaluateParentheses(listWrapper);
        if(mathError==1)
        return "Math Error";
        trimTrailingZeros(listWrapper);
        String result=stringNumFromList(listWrapper.head,listWrapper.tail);
        if(result.length()<=15)
        return result;
        else
        return result.substring(0,15);     
    }
    private static void trimTrailingZeros(LinkedListWrapper listWrapper){
        NodeC current=listWrapper.tail;
        while(current.c=='0'){
          
            current=current.ant;
            current.next=null;
        }
        listWrapper.tail=current;
       
    }
    private static int evaluateParentheses(LinkedListWrapper listWrapper){
        NodeC head=listWrapper.head;
        NodeC tail=listWrapper.tail;
        NodeC current=head;
        int mathError=0;
        while(current!=tail.next){
            while(current!=tail.next&&current.c!='(')
            current=current.next;
            if(current==tail.next)
            break;
            NodeC beforeParen=current.ant;
            NodeC headAux=current.next; //inicio da sublista dentro do parenteses
            int countParen=1;
            
            while(current!=tail&&countParen!=0){
                current=current.next;
                if(current.c=='(')
                countParen++;
                else if(current.c==')')
                countParen--;
                
            }
            if(current==tail.next)
            break;
            NodeC afterParen=current.next;
            NodeC tailAux=current.ant; //fim da sublista dentro do parenteses
            LinkedListWrapper listWrapperAux = new LinkedListWrapper();
            listWrapperAux.head=headAux;
            listWrapperAux.tail=tailAux;
            mathError=evaluateParentheses(listWrapperAux);
            if(mathError==1)
            return 1;
            if(beforeParen==null){   //significa que o parenteses esta no head
                head=listWrapperAux.head;
                head.ant=null;
            }
            else{
                beforeParen.next=listWrapperAux.head;
                listWrapperAux.head.ant=beforeParen;
                if(beforeParen==head.ant)
                head=listWrapperAux.head;

            }
            if(afterParen==null){ //parenteses no tail
               tail=listWrapperAux.tail;
               tail.next=null;

            }
            else{
                listWrapperAux.tail.next=afterParen;
                afterParen.ant=listWrapperAux.tail;
                if(afterParen==tail.next)
                tail=listWrapperAux.tail;

            }
            
            current=listWrapperAux.tail.next;
            
            
            



        }
        listWrapper.head=head;
        
        listWrapper.tail=tail;
        
        mathError=evaluateAdditionAndSubtraction(listWrapper);
        if(mathError==1)
        return 1;
        return 0;






    }
    private static int evaluateAdditionAndSubtraction(LinkedListWrapper listWrapper){
        NodeC head=listWrapper.head;
        NodeC tail=listWrapper.tail;
        
        NodeC current=head;
        int mathError=0;

        while(current!=tail.next){
            
            while(current!=tail.next&&current.c!='x'&&current.c!='/')
            current=current.next;
            
            if(current==tail.next)
            break;

            NodeC fstNodeMult=current.ant; 
            while(fstNodeMult.ant!=head.ant&&((fstNodeMult.c>='0'&&fstNodeMult.c<='9')||fstNodeMult.c=='.'))
            fstNodeMult=fstNodeMult.ant;
            if(fstNodeMult!=head||fstNodeMult.c=='+'||fstNodeMult.c=='-')
            fstNodeMult=fstNodeMult.next;//fstNodeMult inicia a sublista de mult ou div, ex.: em "2+3x++-5-7", fstNodeMult estará em 3
            
            NodeC lstNodeMult=current.next;
            while(lstNodeMult.c=='+'||lstNodeMult.c=='-')
            lstNodeMult=lstNodeMult.next;
            ////System.out.println(lstNodeMult.c);
            while(lstNodeMult.next!=tail.next&&((lstNodeMult .c>='0'&&lstNodeMult .c<='9')||lstNodeMult .c=='.'))
            lstNodeMult=lstNodeMult.next;
            
            if(lstNodeMult!=tail)
            lstNodeMult=lstNodeMult.ant;//lstNodeMult termina a sublista de mult ou div, ex.: em "2+3x++-5-7", ;lstNodeMult estará em 5
            
            LinkedListWrapper listWrapperAux = new LinkedListWrapper();
            listWrapperAux.head=fstNodeMult;
            listWrapperAux.tail=lstNodeMult;
            mathError=evaluateMultiplicationAndDivision(listWrapperAux);//calcula a multiplicao ou divisao, recirando a sublista

            if(mathError==1)
            return 1;

            //encaixa a sublista na lista original:
            if(fstNodeMult==head){    
                head=listWrapperAux.head;
                head.ant=null;
            }
            else{
                fstNodeMult.ant.next=listWrapperAux.head;
                listWrapperAux.head.ant=fstNodeMult.ant;
            }
            if(lstNodeMult==tail){
                tail=listWrapperAux.tail;
                tail.next=null;
            }
            else{
                listWrapperAux.tail.next=lstNodeMult.next;
                lstNodeMult.next.ant=listWrapperAux.tail;
            }

            
            current=listWrapperAux.tail.next;
            
        }
        listWrapper.head=head;
        listWrapper.tail=tail;
        mathError=evaluateAdditionWithoutMultiplication(listWrapper);
        if(mathError==1)
        return 1;
        return 0;
    }
    private static int evaluateAdditionWithoutMultiplication(LinkedListWrapper listWrapper){
      
        NodeC head=listWrapper.head;
        NodeC tail=listWrapper.tail;
        NodeC current=head;
        boolean neg=false;
        int mathError=0;
        while(current!=tail.next){
            while(current.c=='+'||current.c=='-'){
                if(current.c=='-')
                neg=!neg;
                current=current.next;

            }
            double firstTerm=0;
            while(current!=tail.next&&current.c>='0'&&current.c<='9'){
                firstTerm=10*firstTerm+(current.c-'0');
                current=current.next;
            }
            
            if(current!=tail.next&&current.c=='.'){
                current=current.next;
                double firstTermAux=0;
                int i=0;
                while(current!=tail.next&&current.c>='0'&&current.c<='9'){
                    i++;
                    firstTermAux+=(double)(current.c-'0')/Math.pow(10, i);
                    current=current.next;
                }
                firstTerm+=firstTermAux;
            }
            
            
            if(neg)
            firstTerm=-firstTerm;
            
            
            
            if(current==tail.next&&!neg){

                
                break;
            }
            ////System.out.println("nao break");
               

            
            double secondTerm=0;    
            if(current==tail.next){
                secondTerm=0;
            }
            else{
                neg=false;
                while(current.c=='+'||current.c=='-'){
                    if(current.c=='-')
                    neg=!neg;
                    current=current.next;

                }
            
                while(current!=tail.next&&current.c>='0'&&current.c<='9'){
                    secondTerm=10*secondTerm+(current.c-'0');
                    current=current.next;
                }
                if(current!=tail.next&&current.c=='.'){
                    current=current.next;
                    double secondTermAux=0;
                    int i=0;
                    while(current!=tail.next&&current.c>='0'&&current.c<='9'){
                        i++;
                        secondTermAux+=(double)(current.c-'0')/Math.pow(10, i);
                        current=current.next;
                    }
                    secondTerm+=secondTermAux;
                }
                if(neg)
                secondTerm=-secondTerm;
            }
            
            neg=false;
            
            double result=firstTerm+secondTerm;
            //ver math error aqui;
            LinkedListWrapper listWrapperAux=new LinkedListWrapper();
            CreateLinkedList(String.valueOf(result), listWrapperAux);
            head=listWrapperAux.head;
            if(current==tail.next){
                tail=listWrapperAux.tail;
                break;
            }
            listWrapperAux.tail.next=current;
            current.ant=listWrapperAux.tail;
            current=head;

            

        }
        listWrapper.head=head;
        listWrapper.tail=tail;
        if(mathError==1)
        return 1;
        return 0;

        
    }
    private static int evaluateMultiplicationAndDivision(LinkedListWrapper listWrapper){
        NodeC head=listWrapper.head;
        NodeC current=head;
        double firstTerm=0;
        while(current.c>='0'&&current.c<='9'){
            firstTerm=10*firstTerm+(current.c-'0');
            current=current.next;
        }
        if(current.c=='.'){
            current=current.next;
            double firstTermAux=0;
            int i=0;
            while(current!=null&&current.c>='0'&&current.c<='9'){
                i++;
                firstTermAux+=(double)(current.c-'0')/Math.pow(10, i);
                current=current.next;
            }
            firstTerm+=firstTermAux;
        }
        boolean isMult=true;
        if(current.c=='/')
        isMult=false;
        current=current.next;

        boolean neg=false;
        while(current.c=='+'||current.c=='-'){
            if(current.c=='-')
            neg=!neg;
            current=current.next;

        }
        double secondTerm=0;
        while(current!=null&&current.c>='0'&&current.c<='9'){
            secondTerm=10*secondTerm+(current.c-'0');
            current=current.next;
        }
        if(current!=null&&current.c=='.'){
            current=current.next;
            double secondTermAux=0;
            int i=0;
            while(current!=null&&current.c>='0'&&current.c<='9'){
                i++;
                secondTermAux+=(double)(current.c-'0')/Math.pow(10, i);
                current=current.next;
            }
            secondTerm+=secondTermAux;
        }
        if(neg)
        secondTerm=-secondTerm;
        double result;
        if(isMult)
        result=firstTerm*secondTerm;
        else{
            if(secondTerm==0)
            return 1;
            result=firstTerm/secondTerm;

        }
        
        //testar math error aqui
        LinkedListWrapper listWrapperAux=new LinkedListWrapper();
        CreateLinkedList(String.valueOf(result), listWrapperAux);
        listWrapper.head=listWrapperAux.head;
        listWrapper.tail=listWrapperAux.tail;
        return 0;
        
    }
    

    public static int testSintaxError(NodeC head, NodeC tail){
        NodeC auxAnt=null;
        NodeC aux=head;
        boolean temPonto=false;
        boolean temDig=false;
        int nParen=0;
        if(aux.c==')'||aux.c=='x'||aux.c=='/')
            return 1;
        auxAnt=aux;
        aux=aux.next;
        while(auxAnt!=null){
            if(auxAnt.c=='('){
                nParen++;
                if(aux==null)
                    return 1;
                if(aux.c==')'||aux.c=='x'||aux.c=='/')
                    return 1;
            }
            else if(auxAnt.c==')'){
                nParen--;
                if(nParen<0)
                    return 1;
                if(aux!=null&&((aux.c>='0'&&aux.c<='9')||aux.c=='.'||aux.c=='('))
                    return 1;
                


            }
            else if(auxAnt.c=='+'||auxAnt.c=='-'){
                if(aux==null)
                    return 1;
                if(aux.c==')'||aux.c=='x'||aux.c=='/')
                    return 1;
            

            }
            else if(auxAnt.c=='x'||auxAnt.c=='/'){
                if(aux==null)
                    return 1;
                if(aux.c==')'||aux.c=='x'||aux.c=='/')
                    return 1;
            

            }
            else if(auxAnt.c>='0'&&auxAnt.c<='9'){
                if(!temDig)
                    temDig=true;
                if(aux!=null&&aux.c=='.'){
                    if(temPonto)
                        return 1;
                    else
                        temPonto=true;
                }
                if(aux!=null&&aux.c=='(')
                    return 1;
                if(aux!=null&&(aux.c<'0'||aux.c>'9')&&aux.c!='.'){
                    temPonto=false;
                    temDig=false;

                }
            }
            else if(auxAnt.c=='.'){
               if(aux==null){
                    if(!temDig)
                        return 1;

               }
               else{
                    if(aux.c=='('||(aux.c==')'&&!temDig)||aux.c=='.')
                        return 1;
                    

               }
               if(aux!=null&&(aux.c<'0'||aux.c>'9')&&aux.c!='.'){
                    temPonto=false;
                    temDig=false;

                }
            }


            auxAnt=aux;
            if(aux==null)
                break;
            aux=aux.next;



        }
        if(nParen!=0)
            return 1;
        return 0;

        


    }
    
    private static String stringNumFromList(NodeC head,NodeC tail){
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
class NodeC {
    char c;
    NodeC next;
    NodeC ant;

    public NodeC(char c) {
        this.c = c;
        this.next = null;
        this.ant=null;
    }

}
class LinkedListWrapper {
    NodeC head, tail;
}
