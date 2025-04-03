package com.example.myapplication;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;


public class ArithmeticCalculator {
    
    public static void main(String []args){
     //System.out.println(calculate("11222/1200000000000000000000"));

    }
    
    public static void CreateLinkedList(String expression, LinkedListWrapper listWrapper) {
        char[] Cexp = expression.toCharArray();
    
        // Create the first node (head)
        listWrapper.head = new NodeC(Cexp[0]);
        NodeC current = listWrapper.head;
    
        // Create the next nodes
        for (int i = 1; i < Cexp.length; i++) {
            current.next = new NodeC(Cexp[i]); // Create next node
            current.next.ant = current;        // Connect the previous node
            current = current.next;            // Move pointer to the new node
        }
        current.next = null;
        
        // The last node is the tail
        listWrapper.tail = current;
    }
    
    
    public static String calculate (String expression){
        
        if (expression.contentEquals(""))
            return "0";
        LinkedListWrapper listWrapper = new LinkedListWrapper();
        CreateLinkedList(expression, listWrapper);                
        if(!isSyntaxValid(listWrapper.head, listWrapper.tail))
        throw new SyntaxErrorException("Invalid expression");
        processCalculation(listWrapper);
        return formatScientific(stringNumFromList(listWrapper.head, listWrapper.tail),9);
         
    }
    private static void processCalculation(LinkedListWrapper listWrapper){
      //  System.out.println("inicio "+stringNumFromList(listWrapper.head, listWrapper.tail));
        evaluateParentheses(listWrapper);
      //  System.out.println("fim "+stringNumFromList(listWrapper.head, listWrapper.tail));
        
    
    }
    public static String formatScientific(String number, int significantDigits) {
        BigDecimal bigDecimal = new BigDecimal(number).stripTrailingZeros();

        // Remover a parte inteira 0, se for necessário, e verificar número de dígitos
        String result = bigDecimal.toPlainString();
        int totalDigits = result.replace(".", "").length();

        // Caso o número já tenha menos de 9 dígitos significativos, retorna como está
        if (totalDigits <= significantDigits) {
            return result + (result.contains(".") ? "" : ".");
        }

        // Arredondar o número para o número correto de dígitos significativos
        BigDecimal rounded = bigDecimal.round(new MathContext(significantDigits, RoundingMode.HALF_UP));
        // Converter para notação científica com a base correta
        String scientific = rounded.toEngineeringString();
        // Verificar o expoente e retornar erro se ele for maior ou igual a 1000 ou menor que -1000 para evitar problema no ajuste do ponto decimal e expoente
        //haverá um novo teste mais preciso, mas por enquanto preciso apenas garantir que o expoente tem um valor possivel de ser armazenado com inteiro
        if (scientific.contains("E")) {
            int exponent = Integer.parseInt(scientific.split("E")[1]);
            if (Math.abs(exponent) >= 100) {
                throw new ArithmeticException("Number exceeds limit");
            }
        }
        
     
        // Ajuste da posição do ponto decimal e do expoente
        if (scientific.contains("E")) {
            boolean mais=true;
            if(scientific.contains("E-"))
            mais=false;
           if(scientific.contains(".")){
          
                int indPoint=scientific.indexOf('.');
                int indExp=scientific.indexOf('E');
                String expoente=scientific.substring(indExp+2);
                int exp = Integer.valueOf(expoente);
                exp+=indPoint-1;
                scientific=scientific.substring(0,indExp+1);
                scientific=scientific.substring(0,indPoint)+scientific.substring(indPoint+1);
                indExp=scientific.indexOf('E');
                if(mais)
                scientific=scientific.substring(0, 1)+"."+scientific.substring(1,indExp)+'E'+exp;
                else{   
                    scientific=scientific.substring(0, 1)+"."+scientific.substring(1,indExp)+'E'+'-'+exp;

                }
                
               


           }
           else{
            
            int indExp=scientific.indexOf('E');
            String expoente=scientific.substring(indExp+2);
            int exp = Integer.valueOf(expoente);
            exp+=indExp-1;
            scientific=scientific.substring(0,indExp+1);
            indExp=scientific.indexOf('E');
            if(mais)
            scientific=scientific.substring(0,1)+"."+scientific.substring(1,indExp)+"E"+exp;
            else{
               
                scientific=scientific.substring(0, 1)+"."+scientific.substring(1,indExp)+'E'+'-'+exp;

            }
  



           }
        }

        // Verificar o expoente e retornar erro se ele for maior ou igual a 100 ou menor que -100
        if (scientific.contains("E")) {
            int exponent = Integer.parseInt(scientific.split("E")[1]);
            if (Math.abs(exponent) >= 100) {
                throw new ArithmeticException("Number exceeds limit");
            }
        }
        if(!scientific.contains(".")){
            if(scientific.contains("E")){
                int index=scientific.indexOf('E');
                scientific=scientific.substring(0,index)+'.'+scientific.substring(index);
            }
            
            else
            scientific+='.';

        }
        
        return scientific;
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
    private static void evaluateParentheses(LinkedListWrapper listWrapper){ 
       
        NodeC current=listWrapper.head;
        
        while(current!=listWrapper.tail.next){
            while(current!=listWrapper.tail.next&&current.c!='(')
            current=current.next;
            if(current==listWrapper.tail.next)
            break;
            LinkedListWrapper listInternParenthesis=new LinkedListWrapper();
            LinkedListWrapper listExternParenthesis=new LinkedListWrapper();
            current=getParenthesisSublists(listWrapper.tail,current,listExternParenthesis,listInternParenthesis);
            evaluateParentheses(listInternParenthesis);
            // Fitting the auxiliary list into the main list:
            insertParenthesisSublist(listExternParenthesis,listInternParenthesis,listWrapper);
            current=listInternParenthesis.tail.next;
        }
        
        evaluateAdditionAndSubtraction(listWrapper);
        
        return;

    }
    private static void insertParenthesisSublist(LinkedListWrapper listExternParenthesis,LinkedListWrapper listInternParenthesis,LinkedListWrapper listWrapper){
        if(listExternParenthesis.head==null){   // Means that the parenthesis is at the head
            listWrapper.head=listInternParenthesis.head;
            listWrapper.head.ant=null;
        }
        else{
            listExternParenthesis.head.next=listInternParenthesis.head;
            listInternParenthesis.head.ant=listExternParenthesis.head;
            if(listExternParenthesis.head==listWrapper.head.ant)
            listWrapper.head=listInternParenthesis.head;

        }
        if(listExternParenthesis.tail==null){ // Means that the parenthesis is at the tail
            listWrapper.tail=listInternParenthesis.tail;
           listWrapper.tail.next=null;

        }
        else{
            listInternParenthesis.tail.next=listExternParenthesis.tail;
            listExternParenthesis.tail.ant=listInternParenthesis.tail;
            if(listExternParenthesis.tail==listWrapper.tail.next)
            listWrapper.tail=listInternParenthesis.tail;
        }
    }
    private static NodeC getParenthesisSublists(NodeC tail,NodeC current, LinkedListWrapper listExternParenthesis,LinkedListWrapper listInternParenthesis){
        listExternParenthesis.head=current.ant;
        listInternParenthesis.head=current.next; // Start of the sublist inside the parentheses
        int countParen=1; // Count parentheses to get the outermost parentheses
            
        while(current!=tail&&countParen!=0){
            current=current.next;
            if(current.c=='(')
            countParen++;
            else if(current.c==')')
            countParen--;
                
        }
        listExternParenthesis.tail=current.next;
        listInternParenthesis.tail=current.ant; // End of the sublist inside the parentheses
        return current;
    }
    private static void evaluateAdditionAndSubtraction(LinkedListWrapper listWrapper){
       
        NodeC current=listWrapper.head;

        while(current!=listWrapper.tail.next){
            
            while(current!=listWrapper.tail.next&&current.c!='x'&&current.c!='/')
            current=current.next;
            
            if(current==listWrapper.tail.next)
            break;
            LinkedListWrapper multiplicationSublist = new LinkedListWrapper();
            
            getMultiplicationSublist(multiplicationSublist,current,listWrapper.tail,listWrapper.head);
            LinkedListWrapper initialMultiplicationSublist = new LinkedListWrapper();

            initialMultiplicationSublist.head=multiplicationSublist.head;
            initialMultiplicationSublist.tail=multiplicationSublist.tail;
            evaluateMultiplicationAndDivision(multiplicationSublist);// Calculates multiplication or division, recreating the sublist

            insertMultiplicationSublist(multiplicationSublist,initialMultiplicationSublist,listWrapper);
            current=multiplicationSublist.tail.next;
        }
        evaluateAdditionWithoutMultiplication(listWrapper);
        
        return;
        
    }
    private static void getMultiplicationSublist(LinkedListWrapper sublist, NodeC current,NodeC tail,NodeC head){
        sublist.head=current.ant; 
            while(sublist.head.ant!=head.ant&&((sublist.head.c>='0'&&sublist.head.c<='9')||sublist.head.c=='.'))
            sublist.head=sublist.head.ant;
            if(sublist.head!=head||sublist.head.c=='+'||sublist.head.c=='-')
            sublist.head=sublist.head.next;// sublist.head starts the multiplication or division sublist, e.g., in "2+3x++-5-7", sublist.head will be at 3
            
            sublist.tail=current.next;
            while(sublist.tail.c=='+'||sublist.tail.c=='-')
            sublist.tail=sublist.tail.next;
            
            while(sublist.tail.next!=tail.next&&((sublist.tail .c>='0'&&sublist.tail .c<='9')||sublist.tail .c=='.'))
            sublist.tail=sublist.tail.next;
            
            if(sublist.tail!=tail)
            sublist.tail=sublist.tail.ant;// sublist.tail ends the multiplication or division sublist, e.g., in "2+3x++-5-7", sublist.tail will be at 5
    }
    private static void insertMultiplicationSublist(LinkedListWrapper sublist,LinkedListWrapper initialSublist,LinkedListWrapper listWrapper){
            // Fitting the auxiliary list into the main list:
            if(initialSublist.head==listWrapper.head){    
                listWrapper.head=sublist.head;
                listWrapper.head.ant=null;
            }
            else{
                initialSublist.head.ant.next=sublist.head;
                sublist.head.ant=initialSublist.head.ant;
            }
            if(initialSublist.tail==listWrapper.tail){
                listWrapper.tail=sublist.tail;
                listWrapper.tail.next=null;
            }
            else{
                sublist.tail.next=initialSublist.tail.next;
                initialSublist.tail.next.ant=sublist.tail;
            }

    }
    // Calculates the expression without multiplication/division, expressions that reach here should be like "2+3-45", for example
    private static void evaluateAdditionWithoutMultiplication(LinkedListWrapper listWrapper){
        
        NodeC current=listWrapper.head;
        boolean neg=false;

        while(current!=listWrapper.tail.next){
            while(current.c=='+'||current.c=='-'){ // Gets the sign of the number
                if(current.c=='-')
                neg=!neg;
                current=current.next;
            }
            LinkedListWrapper fstTerm=new LinkedListWrapper();
            current=getFstAddTerm(current,fstTerm,listWrapper.tail);

            if(current==listWrapper.tail.next){// Optimize calculations if there is no second term
                
                if(!neg){
                    
                    
                    listWrapper.head=fstTerm.head;
                    listWrapper.tail=fstTerm.tail;
                    break;
                }
                else{
                  //  System.out.println(("aqui"));
                    LinkedListWrapper listInternParenthesis=new LinkedListWrapper();
                    CreateLinkedList("-"+stringNumFromList(fstTerm.head, fstTerm.tail), listInternParenthesis);
                    listWrapper.head=listInternParenthesis.head;
                    listWrapper.tail=listInternParenthesis.tail;
                    break;
                }
            }

            BigDecimal num1;
            if(neg)
            num1 = new BigDecimal("-"+stringNumFromList(fstTerm.head, fstTerm.tail));
            else
            num1 = new BigDecimal(stringNumFromList(fstTerm.head, fstTerm.tail));

            neg=false;
            while(current.c=='+'||current.c=='-'){
                if(current.c=='-')
                neg=!neg;
                current=current.next;
            }

            LinkedListWrapper scdTerm=new LinkedListWrapper();
            current=getScdAddTerm(current,scdTerm,listWrapper.tail);

            BigDecimal num2; 
            if(neg)
            num2 = new BigDecimal("-"+stringNumFromList(scdTerm.head, scdTerm.tail));
            else
            num2 = new BigDecimal(stringNumFromList(scdTerm.head, scdTerm.tail));

            neg=false;
            
            BigDecimal result = num1.add(num2);
            LinkedListWrapper listSum=new LinkedListWrapper();
            CreateLinkedList(result.toPlainString(), listSum);
            current=insertResultList(current,listSum,listWrapper);
            if(current==listWrapper.tail.next)
            break;
            current=listWrapper.head;          
        }
        
        return;
    }
    private static NodeC insertResultList(NodeC current,LinkedListWrapper listSum,LinkedListWrapper listWrapper){
        listWrapper.head=listSum.head;
        if(current==listWrapper.tail.next){
            listWrapper.tail=listSum.tail;
            return current;
        }
        listSum.tail.next=current;
        current.ant=listSum.tail;
        return current;


    }
    private static NodeC getScdAddTerm(NodeC current, LinkedListWrapper term,NodeC tail){
        term.head=current;
        while(current!=tail.next&&current.c>='0'&&current.c<='9')
        current=current.next;
        if(current!=tail.next&&current.c=='.')
        current=current.next;
        while(current!=tail.next&&current.c>='0'&&current.c<='9')
        current=current.next;
        if(current!=tail.next)
        term.tail=current.ant;
        else
        term.tail=tail;
        return current;
    }
    private static NodeC getFstAddTerm(NodeC current, LinkedListWrapper term,NodeC tail){
        term.head=current;
            
        while(current!=tail.next&&current.c>='0'&&current.c<='9')
        current=current.next;
        if(current!=tail.next&&current.c=='.')
        current=current.next;
        while(current!=tail.next&&current!=null&&current.c>='0'&&current.c<='9')
        current=current.next;
        if(current==tail.next)
        term.tail=tail;
        else
        term.tail=current.ant;
        return current;

    }
    private static void evaluateMultiplicationAndDivision(LinkedListWrapper listWrapper){
        
        NodeC current=listWrapper.head;
        LinkedListWrapper fstTerm=new LinkedListWrapper();
        current=getFstMultTerm(current,fstTerm,listWrapper.head);

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

        LinkedListWrapper scdTerm=new LinkedListWrapper();
        current=getScdMultTerm(current,scdTerm,listWrapper.tail);
        
        BigDecimal num1 = new BigDecimal(stringNumFromList(fstTerm.head, fstTerm.tail));
        BigDecimal num2=null;
       
        if(neg)
        num2 = new BigDecimal("-"+stringNumFromList(scdTerm.head, scdTerm.tail));
        else            
        num2 = new BigDecimal(stringNumFromList(scdTerm.head, scdTerm.tail));
        
        BigDecimal result=null;
        if(isMult)
        result = num1.multiply(num2);
       
        else{
            if(num2.compareTo(BigDecimal.ZERO) == 0){
                throw new ArithmeticException("Division by zero");
            }
            
            result = num1.divide(num2, 50, RoundingMode.HALF_UP);
        }

        LinkedListWrapper listInternParenthesis=new LinkedListWrapper();
        CreateLinkedList(result.toPlainString(), listInternParenthesis);
        listWrapper.head=listInternParenthesis.head;
        listWrapper.tail=listInternParenthesis.tail;
        
        
    }
    
    private static NodeC getFstMultTerm(NodeC current, LinkedListWrapper term,NodeC head){
        term.head=head;
        while(current.c>='0'&&current.c<='9')
        current=current.next;
        if(current.c=='.')
        current=current.next;
        while(current!=null&&current.c>='0'&&current.c<='9')
        current=current.next;
        term.tail=current.ant;
        return current;

    }
    private static NodeC getScdMultTerm(NodeC current, LinkedListWrapper term,NodeC tail){
        term.head=current;
        while(current!=null&&current.c>='0'&&current.c<='9')
        current=current.next;
        if(current!=null&&current.c=='.')
        current=current.next;
        while(current!=null&&current.c>='0'&&current.c<='9')
        current=current.next;
        if(current!=null)
        term.tail=current.ant;
        else
        term.tail=tail;
        return current;

    }
    public static boolean isSyntaxValid (NodeC head, NodeC tail){
        NodeC auxAnt=null;
        NodeC aux=head;
        boolean temPonto=false;
        boolean temDig=false;
        int nParen=0;
        if(aux.c==')'||aux.c=='x'||aux.c=='/')
            return false;
        if(aux.c=='.')
            temPonto=true;
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
    
    public static String stringNumFromList(NodeC head,NodeC tail){
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