
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Assert;
import org.junit.Test;
public class testSolution {

    @Test
    public void testSintaxError() { 
        //testes com digito
        String resultado=Solution.calcular("123.123.");
        Assert.assertEquals("Sintax Error", resultado);
        resultado=Solution.calcular("40(5)");
        Assert.assertEquals("Sintax Error", resultado);
        resultado=Solution.calcular("20x56+(67)");
        Assert.assertNotEquals("Sintax Error", resultado);
        resultado=Solution.calcular("45.");
        Assert.assertNotEquals("Sintax Error", resultado);
        resultado=Solution.calcular("(30x40)/50");
        Assert.assertNotEquals("Sintax Error", resultado);
        resultado=Solution.calcular("123");
        Assert.assertNotEquals("Sintax Error", resultado);

        //testes com ponto
        resultado=Solution.calcular("2..3");
        Assert.assertEquals("Sintax Error", resultado);
        resultado=Solution.calcular(".+");
        Assert.assertEquals("Sintax Error", resultado);
        resultado=Solution.calcular(".");
        Assert.assertEquals("Sintax Error", resultado);
        resultado=Solution.calcular(".(");
        Assert.assertEquals("Sintax Error", resultado);
        resultado=Solution.calcular("(.)");
        Assert.assertEquals("Sintax Error", resultado);
        resultado=Solution.calcular("3+.56");
        Assert.assertNotEquals("Sintax Error", resultado);
        resultado=Solution.calcular("56.+12");
        Assert.assertNotEquals("Sintax Error", resultado);
        resultado=Solution.calcular("45.");
        Assert.assertNotEquals("Sintax Error", resultado);
        resultado=Solution.calcular("(5.)");
        Assert.assertNotEquals("Sintax Error", resultado);

        //testes com + ou -
        resultado=Solution.calcular("(45+)");
        Assert.assertEquals("Sintax Error", resultado);
        resultado=Solution.calcular("45-x36");
        Assert.assertEquals("Sintax Error", resultado);
        resultado=Solution.calcular("45+");
        Assert.assertEquals("Sintax Error", resultado);
        resultado=Solution.calcular("(45-)");
        Assert.assertEquals("Sintax Error", resultado);
        resultado=Solution.calcular("-56.78");
        Assert.assertNotEquals("Sintax Error", resultado);
        resultado=Solution.calcular("34.+.78");
        Assert.assertNotEquals("Sintax Error", resultado);
        resultado=Solution.calcular("23+(34x45)");
        Assert.assertNotEquals("Sintax Error", resultado);
        resultado=Solution.calcular("24+--+45");
        Assert.assertNotEquals("Sintax Error", resultado);

        //testes com x ou /
        resultado=Solution.calcular("(45x)");
        Assert.assertEquals("Sintax Error", resultado);
        resultado=Solution.calcular("45/x36");
        Assert.assertEquals("Sintax Error", resultado);
        resultado=Solution.calcular("45x");
        Assert.assertEquals("Sintax Error", resultado);
        resultado=Solution.calcular("(45/)");
        Assert.assertEquals("Sintax Error", resultado);
        resultado=Solution.calcular("/56.78");
        Assert.assertEquals("Sintax Error", resultado);
        resultado=Solution.calcular("23/56.78");
        Assert.assertNotEquals("Sintax Error", resultado);
        resultado=Solution.calcular("34.x.78");
        Assert.assertNotEquals("Sintax Error", resultado);
        resultado=Solution.calcular("23/(34x45)");
        Assert.assertNotEquals("Sintax Error", resultado);
        resultado=Solution.calcular("--+24x++--45");
        Assert.assertNotEquals("Sintax Error", resultado);

        //testes com )
        resultado=Solution.calcular("(45x32)23");
        Assert.assertEquals("Sintax Error", resultado);
        resultado=Solution.calcular("(45x32).23");
        Assert.assertEquals("Sintax Error", resultado);
        resultado=Solution.calcular("(45x32)(23)");
        Assert.assertEquals("Sintax Error", resultado);
        resultado=Solution.calcular("((45x32)+(23))");
        Assert.assertNotEquals("Sintax Error", resultado);
        resultado=Solution.calcular("(45x32)+(23))");
        Assert.assertEquals("Sintax Error", resultado);
        resultado=Solution.calcular("(45x32)+2.5");
        Assert.assertNotEquals("Sintax Error", resultado);
        resultado=Solution.calcular("(45x32)");
        Assert.assertNotEquals("Sintax Error", resultado);

        //testes com (
        resultado=Solution.calcular("(45)");
        Assert.assertNotEquals("Sintax Error", resultado);
        resultado=Solution.calcular("(.56)");
        Assert.assertNotEquals("Sintax Error", resultado);
        resultado=Solution.calcular("((23+78)-4)");
        Assert.assertNotEquals("Sintax Error", resultado);
        resultado=Solution.calcular("()");
        Assert.assertEquals("Sintax Error", resultado);
        resultado=Solution.calcular("(+.45)");
        Assert.assertNotEquals("Sintax Error", resultado);
        resultado=Solution.calcular("(x34)");
        Assert.assertEquals("Sintax Error", resultado);
        resultado=Solution.calcular("(");
        Assert.assertEquals("Sintax Error", resultado);

        //teste contagem de parenteses
        resultado=Solution.calcular("(45))");
        Assert.assertEquals("Sintax Error", resultado);
        resultado=Solution.calcular("((56");
        Assert.assertEquals("Sintax Error", resultado);
        resultado=Solution.calcular("(((3)))");
        Assert.assertNotEquals("Sintax Error", resultado);
        resultado=Solution.calcular("(((2))+(23))");
        Assert.assertNotEquals("Sintax Error", resultado);
        resultado=Solution.calcular("(45+(23)))");
        Assert.assertEquals("Sintax Error", resultado);
        resultado=Solution.calcular("((23.45)+(23)");
        Assert.assertEquals("Sintax Error", resultado);

        //composto
        resultado=Solution.calcular("(45(23).560");
        Assert.assertEquals("Sintax Error", resultado);
        resultado=Solution.calcular("((23+.45)/2+(++-45.))");
        Assert.assertNotEquals("Sintax Error", resultado);
        resultado=Solution.calcular("(45/(23+56)x12))");
        Assert.assertEquals("Sintax Error", resultado);
        resultado=Solution.calcular("(45.45--34))");
        Assert.assertEquals("Sintax Error", resultado);
        resultado=Solution.calcular("((23))--.34/23");
        Assert.assertNotEquals("Sintax Error", resultado);
        resultado=Solution.calcular("(45)/((12.7+.56)/000.567)");
        Assert.assertNotEquals("Sintax Error", resultado);
        
        
    }
        
    
    // Método auxiliar para comparar de frente para trás (head para tail)
    private void assertLinkedListsEqualForward(NodeC expected, NodeC generated) {
        while (expected != null) {
            assertNotNull("A lista gerada terminou antes do esperado!", generated);
            assertEquals("Erro no nó com valor esperado: " + expected.c, expected.c, generated.c );
            expected = expected.next;
            generated = generated.next;
        }
        assertNull( "A lista gerada tem mais elementos do que o esperado!", generated);
    }

    // Método auxiliar para comparar de trás para frente (tail para head)
    private void assertLinkedListsEqualBackward(NodeC expected, NodeC generated) {
        while (expected != null) {
            assertNotNull("A lista gerada terminou antes do esperado!", generated);
            assertEquals("Erro no nó com valor esperado: " + expected.c, expected.c, generated.c );
            expected = expected.ant;
            generated = generated.ant;
        }
        assertNull( "A lista gerada tem mais elementos do que o esperado!", generated);
    }

    @Test
    public void testCreateLinkedList() {
        // Entrada da função
        String expressao = "12345";
        
        // Criar lista esperada 
        NodeC nodeA = new NodeC('1');
        NodeC nodeB = new NodeC('2');
        NodeC nodeC = new NodeC('3');
        NodeC nodeD = new NodeC('4');
        NodeC nodeE = new NodeC('5');

        // Conectar os nós (duplamente encadeados)
        nodeA.next = nodeB;
        nodeB.ant = nodeA;

        nodeB.next = nodeC;
        nodeC.ant = nodeB;

        nodeC.next = nodeD;
        nodeD.ant = nodeC;

        nodeD.next = nodeE;
        nodeE.ant = nodeD;

        // O tail será o último nó (nodeE), e o next de nodeE já é null
        NodeC expectedHead = nodeA;
        NodeC expectedTail = nodeE;

        // Criar lista com a função
        LinkedListWrapper listWrapper = new LinkedListWrapper();
        Solution.CreateLinkedList(expressao, listWrapper);


        // Comparar listas usando método auxiliar (de frente para trás)
        assertLinkedListsEqualForward(expectedHead, listWrapper.head);

        // Comparar listas usando método auxiliar (de trás para frente)
        assertLinkedListsEqualBackward(expectedTail, listWrapper.tail);

        // Entrada da função
        expressao = "12-.5";
        
        // Criar lista esperada 
        nodeA = new NodeC('1');
        nodeB = new NodeC('2');
        nodeC = new NodeC('-');
        nodeD = new NodeC('.');
        nodeE = new NodeC('5');

        // Conectar os nós (duplamente encadeados)
        nodeA.next = nodeB;
        nodeB.ant = nodeA;

        nodeB.next = nodeC;
        nodeC.ant = nodeB;

        nodeC.next = nodeD;
        nodeD.ant = nodeC;

        nodeD.next = nodeE;
        nodeE.ant = nodeD;

        // O tail será o último nó (nodeE), e o next de nodeE já é null
        expectedHead = nodeA;
        expectedTail = nodeE;

        // Criar lista com a função
        listWrapper = new LinkedListWrapper();
        Solution.CreateLinkedList(expressao, listWrapper);


        // Comparar listas usando método auxiliar (de frente para trás)
        assertLinkedListsEqualForward(expectedHead, listWrapper.head);

        // Comparar listas usando método auxiliar (de trás para frente)
        assertLinkedListsEqualBackward(expectedTail, listWrapper.tail);
    }
    @Test
    public void testCalcularValidas() {
        
        
        // Teste com números simples
        String resultado = Solution.calcular("5+3");
        Assert.assertEquals("8.", resultado);
    
        resultado = Solution.calcular("10-2");
        Assert.assertEquals("8.", resultado);
    
        resultado = Solution.calcular("4x2");
        Assert.assertEquals("8.", resultado);
    
        resultado = Solution.calcular("16/2");
        Assert.assertEquals("8.", resultado);
    
        // Teste com prioridade de operadores
        resultado = Solution.calcular("2+3x4"); // Deve seguir a ordem de precedência: 3x4 = 12, depois 2+12
        Assert.assertEquals("14.", resultado);
    
        resultado = Solution.calcular("(2+3)x4"); // Parênteses primeiro: 5x4
        Assert.assertEquals("20.", resultado);
    
        // Teste com números decimais
        resultado = Solution.calcular("2.5+3.5");
        Assert.assertEquals("6.", resultado);
    
        resultado = Solution.calcular("5.5x2");
        Assert.assertEquals("11.", resultado);
    
        // Teste com múltiplas operações2
        resultado = Solution.calcular("10+20x3-5/5"); // 20x3 = 60, depois 10+60 = 70, depois 5/5 = 1, então 70-1
        Assert.assertEquals("69.", resultado);
    
        resultado = Solution.calcular("(10+5)x(2+3)"); // (10+5) = 15, (2+3) = 5, então 15x5
        Assert.assertEquals("75.", resultado);

            // Expressões com parênteses aninhados222
    resultado = Solution.calcular("((2+3)x(4+5))"); // (5x9) = 45
    Assert.assertEquals("45.", resultado);

    resultado = Solution.calcular("(((3+2)x(5-2))+((4+6)/(2)))"); // (5x3) + (10/2) = 15 + 5 = 20
    Assert.assertEquals("20.", resultado);

    resultado = Solution.calcular("(5+(3x(2+1)))"); // 5 + (3x3) = 5 + 9 = 14
    Assert.assertEquals("14.", resultado);

    resultado = Solution.calcular("(10/(2+(3x(4-2))))"); // 10 / (2 + (3x2)) = 10 / (2+6) = 10 / 8 = 1.25
    Assert.assertEquals("1.25", resultado);

    resultado = Solution.calcular("(6x((2+3)/(1+1)))"); // 6 x (5/2) = 6 x 2.5 = 15
    Assert.assertEquals("15.", resultado);

    // Expressões com múltiplos sinais seguidos
    resultado = Solution.calcular("+---34x+--453"); // -34 x 453 = -15402
    Assert.assertEquals("-15402.", resultado);

    resultado = Solution.calcular("+-+--56/--2"); // 56 / 2 = 28
    Assert.assertEquals("-28.", resultado);

    resultado = Solution.calcular("--++45+--10"); // 45 + 10 = 55
    Assert.assertEquals("55.", resultado);

    resultado = Solution.calcular("34+-+23"); // 34 - 23 = 11
    Assert.assertEquals("11.", resultado);

    resultado = Solution.calcular("12/--4+---3"); // 12 / 4 - 3 = 3 - 3 = 0
    Assert.assertEquals("0.", resultado);

    // Expressões com números no formato `.45`
    resultado = Solution.calcular(".45x10"); // 0.45 x 10 = 4.5
    Assert.assertEquals("4.5", resultado);

    resultado = Solution.calcular("(3+.45)x2"); // (3.45) x 2 = 6.9
    Assert.assertEquals("6.9", resultado);

    resultado = Solution.calcular("(1.5x.5)+.25"); // (1.5 x 0.5) + 0.25 = 0.75 + 0.25 = 1
    Assert.assertEquals("1.", resultado);

    resultado = Solution.calcular("1/.5"); // 1 / 0.5 = 2
    Assert.assertEquals("2.", resultado);

    resultado = Solution.calcular("(.25x4)/.5"); // (0.25 x 4) / 0.5 = 1 / 0.5 = 2
    Assert.assertEquals("2.", resultado);

    // Expressões combinando os desafios acima
    resultado = Solution.calcular("((3.5+2.5)x(4-.5))"); // (6.0 x 3.5) = 21.0
    Assert.assertEquals("21.", resultado);

    resultado = Solution.calcular("(10/((.5+.5)x2))"); // 10 / (1 x 2) = 10 / 2 = 5
    Assert.assertEquals("5.", resultado);

    resultado = Solution.calcular("(.3+.2)x(5-3)"); // (0.5 x 2) = 1
    Assert.assertEquals("1.", resultado);

    resultado = Solution.calcular("((.5+.5)+(2x3))"); // (1 + 6) = 7
    Assert.assertEquals("7.", resultado);

    resultado = Solution.calcular("(-.5x2)+(3.5)"); // (-1) + 3.5 = 2.5
    Assert.assertEquals("2.5", resultado);
    }
    
}