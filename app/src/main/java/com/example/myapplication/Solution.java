
import java.math.BigDecimal;
import java.math.RoundingMode;


public class Solution {
    
    public static void main(String[] args) {
        String resultado = calculate("222222222323");
        System.out.println("Resultado: " + resultado);
    }
    
    public static void CreateLinkedList(String expression, LinkedListWrapper listWrapper) {
        char[] Cexp = expression.toCharArray();

        // Criar o primeiro nó (head)
        listWrapper.head = new NodeC(Cexp[0]);
        NodeC current = listWrapper.head;
    
        // Criar os próximos nós
        for (int i = 1; i < Cexp.length; i++) {
            current.next = new NodeC(Cexp[i]); // Criar próximo nó
            current.next.ant = current;            // Conectar o nó anterior
            current = current.next;                // Mover ponteiro para o novo nó
        }
        current.next=null;
        // O último nó é o tail
        listWrapper.tail = current;
    }
    
    public static String calculate (String expression){
        
        if (expression.contentEquals(""))
            return "0";
        LinkedListWrapper listWrapper = new LinkedListWrapper();
        CreateLinkedList(expression, listWrapper);
        NodeC head=listWrapper.head;                 
        NodeC tail=listWrapper.tail;                 
        if(!isSyntaxValid(head, tail))
        throw new SyntaxErrorException("Invalid expression");
        processCalculation(listWrapper);
        return formatResultToString(listWrapper);
         
    }
    private static void processCalculation(LinkedListWrapper listWrapper){
        int divisionByZeroMathError=evaluateParentheses(listWrapper);
        if(divisionByZeroMathError==1)
        throw new ArithmeticException("Division by zero");
        if(hasLargeNumberMathError(listWrapper.head, listWrapper.tail))
        throw new ArithmeticException("Number exceeds limit");
        
    
    }
    
    private static String formatResultToString(LinkedListWrapper listWrapper){
        int hasPoint=insertDecimalPoint(listWrapper);
        if(hasPoint==0){                  
            trimTrailingZeros(listWrapper);
        }
        String result=stringNumFromList(listWrapper.head,listWrapper.tail);
        if(result.length()<=15)
        return result;
        else
        return result.substring(0,15);   


    }
    private static boolean hasLargeNumberMathError(NodeC head,NodeC tail){ //A number must not have more than 14 digits before dot
        NodeC current=head;
        int count=0;
        while(current!=null&&current.c!='.'){
            count++;
            current=current.next;
        }
        if(count>14)
        return true;
        else
        return false;


    }
    private static void trimTrailingZeros(LinkedListWrapper listWrapper){
        NodeC current=listWrapper.tail;
        while(current.c=='0'){
          
            current=current.ant;
            current.next=null;
        }
        listWrapper.tail=current;
       
    }
    private static int insertDecimalPoint(LinkedListWrapper listWrapper){  // Insert a decimal dot if it does not have one. E.g., 456 becomes 456.
        NodeC current=listWrapper.head;
        while(current!=null&&current.c!='.'){
            current=current.next;
        }
        if(current==null){

            listWrapper.tail.next=new NodeC('.');
            listWrapper.tail.next.ant=listWrapper.tail;
            listWrapper.tail=listWrapper.tail.next;
            return 1; // retorna um se nao estava com ponto decimal. ex.: 2x3=6 fica "6."

        }
        return 0;
        
        
       
    }
  
    // Start of the recursive calculation of the expression, beginning with parentheses
    // e.g.:
    // 1) evaluateParentheses receives "(4+5)+(2x3)"
    // 2) Recursively calls evaluateParentheses for "4+5"
    // 3) Returns to the first call of evaluateParentheses, which now has "9+(2x3)" in its linked list
    // 4) Recursively calls evaluateParentheses for "2x3"
    // 5) Returns to the first call of evaluateParentheses, which now has "9+6" in its linked list
    // 6) Calls evaluateAdditionAndSubtraction to calculate 9+6=15
    // Other evaluate methods follow the same recursive logic, respecting the order of precedence: parentheses, multiplication/division, addition/subtraction
    // Only evaluateMultiplicationAndDivision does not use recursion, as it directly calculates a multiplication or division expression
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
            //LinkedListWrapper listInternParenthesis=new LinkedListWrapper();
            //LinkedListWrapper listExternParenthesis=new Linked
            NodeC beforeParen=current.ant;
            NodeC headAux=current.next; //inicio da sublista dentro do parenteses
            int countParen=1; //contagem de parenteses para obter os parenteses mais externos
            
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
            //encaixe da lista auxiliar na principal:
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
            
            while(lstNodeMult.next!=tail.next&&((lstNodeMult .c>='0'&&lstNodeMult .c<='9')||lstNodeMult .c=='.'))
            lstNodeMult=lstNodeMult.next;
            
            if(lstNodeMult!=tail)
            lstNodeMult=lstNodeMult.ant;//lstNodeMult termina a sublista de mult ou div, ex.: em "2+3x++-5-7", lstNodeMult estará em 5
            
            LinkedListWrapper listWrapperAux = new LinkedListWrapper();
            listWrapperAux.head=fstNodeMult;
            listWrapperAux.tail=lstNodeMult;
            mathError=evaluateMultiplicationAndDivision(listWrapperAux);//calcula a multiplicao ou divisao, recriando a sublista

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
    //calcula expressao já sem multiplicação/divisao, expressoes que chegam aqui devem ser como "2+3-45", por exemplo
    private static int evaluateAdditionWithoutMultiplication(LinkedListWrapper listWrapper){
        NodeC head=listWrapper.head;
        NodeC tail=listWrapper.tail;
        NodeC current=head;
        boolean neg=false;
        int mathError=0;

        while(current!=tail.next){
            while(current.c=='+'||current.c=='-'){ //obtem sinal do número
                if(current.c=='-')
                neg=!neg;
                current=current.next;
            }

            NodeC beginFstTerm=current;
            NodeC endFstTerm=null;
            while(current!=tail.next&&current.c>='0'&&current.c<='9'){
                
                current=current.next;
            }
            if(current!=tail.next&&current.c=='.')
            current=current.next;
            while(current!=tail.next&&current!=null&&current.c>='0'&&current.c<='9')
            current=current.next;
            if(current==tail.next)
            endFstTerm=tail;
            else
            endFstTerm=current.ant;

            if(current==tail.next){//otimizar os calculos caso nao haja segundo termo
                if(!neg)
                break;
                else{
                   // System.out.println("so o num "+stringNumFromList(beginFstTerm, endFstTerm));
                    LinkedListWrapper listWrapperAux=new LinkedListWrapper();
                    CreateLinkedList("-"+stringNumFromList(beginFstTerm, endFstTerm), listWrapperAux);
                    head=listWrapperAux.head;
                    tail=listWrapperAux.tail;
                    break;
                }
            }

            BigDecimal num1;
            if(neg)
            num1 = new BigDecimal("-"+stringNumFromList(beginFstTerm, endFstTerm));
            else
            num1 = new BigDecimal(stringNumFromList(beginFstTerm, endFstTerm));

            neg=false;
            while(current.c=='+'||current.c=='-'){
                if(current.c=='-')
                neg=!neg;
                current=current.next;

            }

            NodeC beginScdTerm=current;
            NodeC endScdTerm=null;
            while(current!=tail.next&&current.c>='0'&&current.c<='9')
            current=current.next;
            if(current!=tail.next&&current.c=='.')
            current=current.next;
            while(current!=tail.next&&current.c>='0'&&current.c<='9')
            current=current.next;
            if(current!=tail.next)
            endScdTerm=current.ant;
            else
            endScdTerm=tail;

            BigDecimal num2; 
            if(neg)
            num2 = new BigDecimal("-"+stringNumFromList(beginScdTerm, endScdTerm));
            else
            num2 = new BigDecimal(stringNumFromList(beginScdTerm, endScdTerm));

            neg=false;
            
            BigDecimal result = num1.add(num2);
            LinkedListWrapper listWrapperAux=new LinkedListWrapper();
            CreateLinkedList(result.toPlainString(), listWrapperAux);

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
        NodeC tail=listWrapper.tail;
        NodeC current=head;
        NodeC beginFstTerm=head;
        NodeC endFstTerm=null;

        while(current.c>='0'&&current.c<='9')
        current=current.next;
        if(current.c=='.')
        current=current.next;
        while(current!=null&&current.c>='0'&&current.c<='9')
        current=current.next;

        endFstTerm=current.ant;
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
        NodeC beginScdTerm=current;
        NodeC endScdTerm=null;
        while(current!=null&&current.c>='0'&&current.c<='9')
        current=current.next;
        
        if(current!=null&&current.c=='.')
        current=current.next;
        while(current!=null&&current.c>='0'&&current.c<='9')
        current=current.next;
        if(current!=null)
        endScdTerm=current.ant;
        else
        endScdTerm=tail;

        BigDecimal num1 = new BigDecimal(stringNumFromList(beginFstTerm, endFstTerm));
        BigDecimal num2=null;
       
        if(neg){
            num2 = new BigDecimal("-"+stringNumFromList(beginScdTerm, endScdTerm));

        }
        else{
            
            num2 = new BigDecimal(stringNumFromList(beginScdTerm, endScdTerm));

        }
        
        BigDecimal result=null;
        if(isMult){
            result = num1.multiply(num2);

        }
       
        else{
            
            if(num2.compareTo(BigDecimal.ZERO) == 0)
            return 1;
            result = num1.divide(num2, 50, RoundingMode.HALF_UP);

        }
        LinkedListWrapper listWrapperAux=new LinkedListWrapper();
        CreateLinkedList(result.toPlainString(), listWrapperAux);
        listWrapper.head=listWrapperAux.head;
        listWrapper.tail=listWrapperAux.tail;
        return 0;
        
    }
    
    
    public static boolean isSyntaxValid (NodeC head, NodeC tail){
        NodeC auxAnt=null;
        NodeC aux=head;
        boolean temPonto=false;
        boolean temDig=false;
        int nParen=0;
        if(aux.c==')'||aux.c=='x'||aux.c=='/')
            return false;
        auxAnt=aux;
        aux=aux.next;
        while(auxAnt!=null){
            
            if(auxAnt.c=='('){
                nParen++;
                if(aux==null)
                    return false;
                if(aux.c==')'||aux.c=='x'||aux.c=='/')
                    return false;
            }
            else if(auxAnt.c==')'){
                nParen--;
                if(nParen<0)
                    return false;
                if(aux!=null&&((aux.c>='0'&&aux.c<='9')||aux.c=='.'||aux.c=='('))
                    return false;
                


            }
            else if(auxAnt.c=='+'||auxAnt.c=='-'){
                if(aux==null)
                    return false;
                if(aux.c==')'||aux.c=='x'||aux.c=='/')
                    return false;
            

            }
            else if(auxAnt.c=='x'||auxAnt.c=='/'){
                if(aux==null)
                    return false;
                if(aux.c==')'||aux.c=='x'||aux.c=='/')
                    return false;
            

            }
            else if(auxAnt.c>='0'&&auxAnt.c<='9'){
                if(!temDig)
                    temDig=true;
                if(aux!=null&&aux.c=='.'){
                    if(temPonto)
                        return false;
                    else
                        temPonto=true;
                }
                if(aux!=null&&aux.c=='(')
                    return false;
                if(aux!=null&&(aux.c<'0'||aux.c>'9')&&aux.c!='.'){
                    temPonto=false;
                    temDig=false;

                }
            }
            else if(auxAnt.c=='.'){
               if(aux==null){
                    if(!temDig)
                        return false;

               }
               else{
                    if(aux.c=='('||(aux.c==')'&&!temDig)||aux.c=='.')
                        return false;
                    

               }
               if(aux!=null&&(aux.c<'0'||aux.c>'9')&&aux.c!='.'){
                    temPonto=false;
                    temDig=false;

                }
            }
            else{
                return false;
            }
            auxAnt=aux;
            if(aux==null)
                break;
            aux=aux.next;
        }
        if(nParen!=0)
            return false;
        return true;
    }
    
    private static String stringNumFromList(NodeC head,NodeC tail){
        String result="";
        while(head!=tail.next){
            result+=head.c;
            head=head.next;
        }
        return result;
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
class SyntaxErrorException extends RuntimeException {
    public SyntaxErrorException(String message) {
        super(message);
    }
}
