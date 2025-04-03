package com.example.myapplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


import org.junit.Assert;
import org.junit.Test;
public class testArithmeticCalculator {

    @Test
    public void testSintaxError() { 
        
    // Testes com digito
    try {
        ArithmeticCalculator.calculate("123.123.");
        Assert.fail("Expected SyntaxErrorException");
    } catch (SyntaxErrorException e) {
        assertEquals("Invalid expression", e.getMessage());
    }
    
    try {
        ArithmeticCalculator.calculate("40(5)");
        Assert.fail("Expected SyntaxErrorException");
    } catch (SyntaxErrorException e) {
        assertEquals("Invalid expression", e.getMessage());
    }

    try {
        ArithmeticCalculator.calculate("20x56+(67)");
       // Assert.assertNotEquals("Invalid expression", e.getMessage());
    } catch (SyntaxErrorException e) {
        Assert.fail("Did not expect SyntaxErrorException");
    }

    try {
        ArithmeticCalculator.calculate("45.");
       // Assert.assertNotEquals("Invalid expression", e.getMessage());
    } catch (SyntaxErrorException e) {
        Assert.fail("Did not expect SyntaxErrorException");
    }
    
    try {
        ArithmeticCalculator.calculate("(30x40)/50");
       // Assert.assertNotEquals("Invalid expression", e.getMessage());
    } catch (SyntaxErrorException e) {
        Assert.fail("Did not expect SyntaxErrorException");
    }
    
    try {
        ArithmeticCalculator.calculate("123");
       // Assert.assertNotEquals("Invalid expression", e.getMessage());
    } catch (SyntaxErrorException e) {
        Assert.fail("Did not expect SyntaxErrorException");
    }

    // Testes com ponto
        try {
            ArithmeticCalculator.calculate(".8.");
            Assert.fail("Expected SyntaxErrorException");
        } catch (SyntaxErrorException e) {
            assertEquals("Invalid expression", e.getMessage());
        }

    try {
        ArithmeticCalculator.calculate("2..3");
        Assert.fail("Expected SyntaxErrorException");
    } catch (SyntaxErrorException e) {
        assertEquals("Invalid expression", e.getMessage());
    }
    
    try {
        ArithmeticCalculator.calculate(".+");
        Assert.fail("Expected SyntaxErrorException");
    } catch (SyntaxErrorException e) {
        assertEquals("Invalid expression", e.getMessage());
    }
    
    try {
        ArithmeticCalculator.calculate(".");
        Assert.fail("Expected SyntaxErrorException");
    } catch (SyntaxErrorException e) {
        assertEquals("Invalid expression", e.getMessage());
    }
    
    try {
        ArithmeticCalculator.calculate(".(");
        Assert.fail("Expected SyntaxErrorException");
    } catch (SyntaxErrorException e) {
        assertEquals("Invalid expression", e.getMessage());
    }
    
    try {
        ArithmeticCalculator.calculate("(.)");
        Assert.fail("Expected SyntaxErrorException");
    } catch (SyntaxErrorException e) {
        assertEquals("Invalid expression", e.getMessage());
    }

    // Testes com + ou -
    try {
        ArithmeticCalculator.calculate("(45+)");
        Assert.fail("Expected SyntaxErrorException");
    } catch (SyntaxErrorException e) {
        assertEquals("Invalid expression", e.getMessage());
    }
    
    try {
        ArithmeticCalculator.calculate("45-x36");
        Assert.fail("Expected SyntaxErrorException");
    } catch (SyntaxErrorException e) {
        assertEquals("Invalid expression", e.getMessage());
    }
    
    try {
        ArithmeticCalculator.calculate("45+");
        Assert.fail("Expected SyntaxErrorException");
    } catch (SyntaxErrorException e) {
        assertEquals("Invalid expression", e.getMessage());
    }
    
    try {
        ArithmeticCalculator.calculate("(45-)");
        Assert.fail("Expected SyntaxErrorException");
    } catch (SyntaxErrorException e) {
        assertEquals("Invalid expression", e.getMessage());
    }

    // Testes com x ou /
    try {
        ArithmeticCalculator.calculate("(45x)");
        Assert.fail("Expected SyntaxErrorException");
    } catch (SyntaxErrorException e) {
        assertEquals("Invalid expression", e.getMessage());
    }
    
    try {
        ArithmeticCalculator.calculate("45/x36");
        Assert.fail("Expected SyntaxErrorException");
    } catch (SyntaxErrorException e) {
        assertEquals("Invalid expression", e.getMessage());
    }
    
    try {
        ArithmeticCalculator.calculate("45x");
        Assert.fail("Expected SyntaxErrorException");
    } catch (SyntaxErrorException e) {
        assertEquals("Invalid expression", e.getMessage());
    }
    
    try {
        ArithmeticCalculator.calculate("(45/)");
        Assert.fail("Expected SyntaxErrorException");
    } catch (SyntaxErrorException e) {
        assertEquals("Invalid expression", e.getMessage());
    }
    
    try {
        ArithmeticCalculator.calculate("/56.78");
        Assert.fail("Expected SyntaxErrorException");
    } catch (SyntaxErrorException e) {
        assertEquals("Invalid expression", e.getMessage());
    }
            // Testes com dígitos
    try {
        ArithmeticCalculator.calculate("23/56.78");
        
    } catch (SyntaxErrorException e) {
        Assert.fail("Did not expect SyntaxErrorException");
    }

    try {
        ArithmeticCalculator.calculate("34.x.78");
       
    } catch (SyntaxErrorException e) {
        Assert.fail("Did not expect SyntaxErrorException");
    }

    try {
        ArithmeticCalculator.calculate("23/(34x45)");
        
    } catch (SyntaxErrorException e) {
        Assert.fail("Did not expect SyntaxErrorException");
    }

    try {
        ArithmeticCalculator.calculate("--+24x++--45");
    
    } catch (SyntaxErrorException e) {
        Assert.fail("Did not expect SyntaxErrorException");
    }

    // Testes com ')'
    try {
        ArithmeticCalculator.calculate("(45x32)23");
        Assert.fail("Expected SyntaxErrorException");
    } catch (SyntaxErrorException e) {
    }

    try {
        ArithmeticCalculator.calculate("(45x32).23");
        Assert.fail("Expected SyntaxErrorException");
    } catch (SyntaxErrorException e) {
        
    }

    try {
        ArithmeticCalculator.calculate("(45x32)(23)");
        Assert.fail("Expected SyntaxErrorException");
    } catch (SyntaxErrorException e) {
    }

    try {
        ArithmeticCalculator.calculate("((45x32)+(23))");
    } catch (SyntaxErrorException e) {
        Assert.fail("Did not expect SyntaxErrorException");
    }

    try {
        ArithmeticCalculator.calculate("(45x32)+(23))");
        Assert.fail("Expected SyntaxErrorException");
    } catch (SyntaxErrorException e) {
       
    }

    try {
        ArithmeticCalculator.calculate("(45x32)+2.5");
        
    } catch (SyntaxErrorException e) {
        Assert.fail("Did not expect SyntaxErrorException");
    }

    try {
        ArithmeticCalculator.calculate("(45x32)");
        
    } catch (SyntaxErrorException e) {
        Assert.fail("Did not expect SyntaxErrorException");
    }

    // Testes com '('
    try {
        ArithmeticCalculator.calculate("(45)");
       
    } catch (SyntaxErrorException e) {
        Assert.fail("Did not expect SyntaxErrorException");
    }

    try {
        ArithmeticCalculator.calculate("(.56)");
        
    } catch (SyntaxErrorException e) {
        Assert.fail("Did not expect SyntaxErrorException");
    }

    try {
        ArithmeticCalculator.calculate("((23+78)-4)");
        
    } catch (SyntaxErrorException e) {
        Assert.fail("Did not expect SyntaxErrorException");
    }

    try {
        ArithmeticCalculator.calculate("()");
        Assert.fail("Expected SyntaxErrorException");
    } catch (SyntaxErrorException e) {
        
    }

    try {
        ArithmeticCalculator.calculate("(+.45)");
    } catch (SyntaxErrorException e) {
        Assert.fail("Did not expect SyntaxErrorException");
    }

    try {
        ArithmeticCalculator.calculate("(x34)");
        Assert.fail("Expected SyntaxErrorException");
    } catch (SyntaxErrorException e) {
        
    }

    try {
        ArithmeticCalculator.calculate("(");
        Assert.fail("Expected SyntaxErrorException");
    } catch (SyntaxErrorException e) {
    }

    //teste contagem de parenteses
    try {
        ArithmeticCalculator.calculate("(45))");
        Assert.fail("Expected SyntaxErrorException");
    } catch (SyntaxErrorException e) {
    }
    try {
        ArithmeticCalculator.calculate("(");
        Assert.fail("Expected SyntaxErrorException");
    } catch (SyntaxErrorException e) {
    }
    try {
        ArithmeticCalculator.calculate("((56");
        Assert.fail("Expected SyntaxErrorException");
    } catch (SyntaxErrorException e) {
    }
    try {
        ArithmeticCalculator.calculate("((56");
        Assert.fail("Expected SyntaxErrorException");
    } catch (SyntaxErrorException e) {
    }
    try {
        ArithmeticCalculator.calculate("((56");
        Assert.fail("Expected SyntaxErrorException");
    } catch (SyntaxErrorException e) {
    }
    try {
        ArithmeticCalculator.calculate("((56");
        Assert.fail("Expected SyntaxErrorException");
    } catch (SyntaxErrorException e) {
    }
    try {
        ArithmeticCalculator.calculate("(((3)))");
        
    } catch (SyntaxErrorException e) {
        Assert.fail("Did not expected SyntaxErrorException");
    }
    try {
        ArithmeticCalculator.calculate("(((2))+(23))");
        
    } catch (SyntaxErrorException e) {
        Assert.fail("Did not expected SyntaxErrorException");
    }
    try {
        ArithmeticCalculator.calculate("(((2))+(23))");
        
    } catch (SyntaxErrorException e) {
        Assert.fail("Did not expected SyntaxErrorException");
        
    }
    try {
        ArithmeticCalculator.calculate("(45+(23)))");
        Assert.fail("Expected SyntaxErrorException");
    } catch (SyntaxErrorException e) {
        
    }
    try {
        ArithmeticCalculator.calculate("((23.45)+(23)");
        Assert.fail("Expected SyntaxErrorException");
    } catch (SyntaxErrorException e) {
        
    }
    //composto
    try {
        ArithmeticCalculator.calculate("(45(23).560");
        Assert.fail("Expected SyntaxErrorException");
    } catch (SyntaxErrorException e) {
        
    }
    try {
        ArithmeticCalculator.calculate("((23+.45)/2+(++-45.))");
        
    } catch (SyntaxErrorException e) {
        Assert.fail("Did not expected SyntaxErrorException");
    }
    try {
        ArithmeticCalculator.calculate("(45/(23+56)x12))");
        Assert.fail("Expected SyntaxErrorException");
    } catch (SyntaxErrorException e) {
        
    }
    try {
        ArithmeticCalculator.calculate("(45.45--34))");
        Assert.fail("Expected SyntaxErrorException");
    } catch (SyntaxErrorException e) {
        
    }
    try {
        ArithmeticCalculator.calculate("((23))--.34/23");
        
    } catch (SyntaxErrorException e) {
        Assert.fail("Did not expected SyntaxErrorException");
    }
    try {
        ArithmeticCalculator.calculate("(45)/((12.7+.56)/000.567)");
        
    } catch (SyntaxErrorException e) {
        Assert.fail("Did not expected SyntaxErrorException");
    }

        
   
        
        
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
        ArithmeticCalculator.CreateLinkedList(expressao, listWrapper);


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
        ArithmeticCalculator.CreateLinkedList(expressao, listWrapper);


        // Comparar listas usando método auxiliar (de frente para trás)
        assertLinkedListsEqualForward(expectedHead, listWrapper.head);

        // Comparar listas usando método auxiliar (de trás para frente)
        assertLinkedListsEqualBackward(expectedTail, listWrapper.tail);
    }
    @Test
    public void testcalculateValidas() {
        
        
        // Teste com números simples
        String resultado = ArithmeticCalculator.calculate("5+3");
        assertEquals("8.", resultado);
    
        resultado = ArithmeticCalculator.calculate("10-2");
        assertEquals("8.", resultado);
    
        resultado = ArithmeticCalculator.calculate("4x2");
        assertEquals("8.", resultado);
    
        resultado = ArithmeticCalculator.calculate("16/2");
        assertEquals("8.", resultado);
    
        // Teste com prioridade de operadores
        resultado = ArithmeticCalculator.calculate("2+3x4"); // Deve seguir a ordem de precedência: 3x4 = 12, depois 2+12
        assertEquals("14.", resultado);
    
        resultado = ArithmeticCalculator.calculate("(2+3)x4"); // Parênteses primeiro: 5x4
        assertEquals("20.", resultado);
    
        // Teste com números decimais
        resultado = ArithmeticCalculator.calculate("2.5+3.5");
        assertEquals("6.", resultado);
    
        resultado = ArithmeticCalculator.calculate("5.5x2");
        assertEquals("11.", resultado);
    
        // Teste com múltiplas operações2
        resultado = ArithmeticCalculator.calculate("10+20x3-5/5"); // 20x3 = 60, depois 10+60 = 70, depois 5/5 = 1, então 70-1
        assertEquals("69.", resultado);
    
        resultado = ArithmeticCalculator.calculate("(10+5)x(2+3)"); // (10+5) = 15, (2+3) = 5, então 15x5
        assertEquals("75.", resultado);

            // Expressões com parênteses aninhados222
    resultado = ArithmeticCalculator.calculate("((2+3)x(4+5))"); // (5x9) = 45
    assertEquals("45.", resultado);

    resultado = ArithmeticCalculator.calculate("(((3+2)x(5-2))+((4+6)/(2)))"); // (5x3) + (10/2) = 15 + 5 = 20
    assertEquals("20.", resultado);

    resultado = ArithmeticCalculator.calculate("(5+(3x(2+1)))"); // 5 + (3x3) = 5 + 9 = 14
    assertEquals("14.", resultado);

    resultado = ArithmeticCalculator.calculate("(10/(2+(3x(4-2))))"); // 10 / (2 + (3x2)) = 10 / (2+6) = 10 / 8 = 1.25
    assertEquals("1.25", resultado);

    resultado = ArithmeticCalculator.calculate("(6x((2+3)/(1+1)))"); // 6 x (5/2) = 6 x 2.5 = 15
    assertEquals("15.", resultado);

    // Expressões com múltiplos sinais seguidos
    resultado = ArithmeticCalculator.calculate("+---34x+--453"); // -34 x 453 = -15402
    assertEquals("-15402.", resultado);

    resultado = ArithmeticCalculator.calculate("+-+--56/--2"); // 56 / 2 = 28
    assertEquals("-28.", resultado);

    resultado = ArithmeticCalculator.calculate("--++45+--10"); // 45 + 10 = 55
    assertEquals("55.", resultado);

    resultado = ArithmeticCalculator.calculate("34+-+23"); // 34 - 23 = 11
    assertEquals("11.", resultado);

    resultado = ArithmeticCalculator.calculate("12/--4+---3"); // 12 / 4 - 3 = 3 - 3 = 0
    assertEquals("0.", resultado);

    // Expressões com números no formato `.45`
    resultado = ArithmeticCalculator.calculate(".45x10"); // 0.45 x 10 = 4.5
    assertEquals("4.5", resultado);

    resultado = ArithmeticCalculator.calculate("(3+.45)x2"); // (3.45) x 2 = 6.9
    assertEquals("6.9", resultado);

    resultado = ArithmeticCalculator.calculate("(1.5x.5)+.25"); // (1.5 x 0.5) + 0.25 = 0.75 + 0.25 = 1
    assertEquals("1.", resultado);

    resultado = ArithmeticCalculator.calculate("1/.5"); // 1 / 0.5 = 2
    assertEquals("2.", resultado);

    resultado = ArithmeticCalculator.calculate("(.25x4)/.5"); // (0.25 x 4) / 0.5 = 1 / 0.5 = 2
    assertEquals("2.", resultado);

    // Expressões combinando os desafios acima
    resultado = ArithmeticCalculator.calculate("((3.5+2.5)x(4-.5))"); // (6.0 x 3.5) = 21.0
    assertEquals("21.", resultado);

    resultado = ArithmeticCalculator.calculate("(10/((.5+.5)x2))"); // 10 / (1 x 2) = 10 / 2 = 5
    assertEquals("5.", resultado);

    resultado = ArithmeticCalculator.calculate("(.3+.2)x(5-3)"); // (0.5 x 2) = 1
    assertEquals("1.", resultado);

    resultado = ArithmeticCalculator.calculate("((.5+.5)+(2x3))"); // (1 + 6) = 7
    assertEquals("7.", resultado);

    resultado = ArithmeticCalculator.calculate("(-.5x2)+(3.5)"); // (-1) + 3.5 = 2.5
    assertEquals("2.5", resultado);
    }
    @Test
    public void testValidResults() {
        String resultado;

        // Teste para números sem ponto decimal
        resultado = ArithmeticCalculator.calculate("23");
        assertEquals("23.", resultado);

        resultado = ArithmeticCalculator.calculate("1");
        assertEquals("1.", resultado);

        // Teste para números com ponto decimal, com zeros à direita
        resultado = ArithmeticCalculator.calculate("4.1000");
        assertEquals("4.1", resultado);

        resultado = ArithmeticCalculator.calculate("4.0000");
        assertEquals("4.", resultado);

        // Teste para números com ponto decimal, sem zeros à direita
        resultado = ArithmeticCalculator.calculate("4.5");
        assertEquals("4.5", resultado);

        resultado = ArithmeticCalculator.calculate("10.25");
        assertEquals("10.25", resultado);

        // Teste para resultado com zeros depois do ponto decimal
        resultado = ArithmeticCalculator.calculate("0");
        assertEquals("0.", resultado);

        resultado = ArithmeticCalculator.calculate("0.000000");
        assertEquals("0.", resultado);

        // Teste para números negativos com ponto decimal
        resultado = ArithmeticCalculator.calculate("-4.1000");
        assertEquals("-4.1", resultado);

        resultado = ArithmeticCalculator.calculate("-4.0000");
        assertEquals("-4.", resultado);

        resultado = ArithmeticCalculator.calculate("-4.5");
        assertEquals("-4.5", resultado);

        // Teste para números decimais com precisão
        resultado = ArithmeticCalculator.calculate("1.234567");
        assertEquals("1.234567", resultado);

        resultado = ArithmeticCalculator.calculate("123.456789");
        assertEquals("123.456789", resultado);

        // Teste para números grandes mas dentro do limite de 15 caracteres
        resultado = ArithmeticCalculator.calculate("1234567890123");
        assertEquals("1.23456789E12", resultado);

        resultado = ArithmeticCalculator.calculate("9876543210123");
        assertEquals("9.87654321E12", resultado);

        // Teste para números grandes com precisão truncada ou arredondada
        resultado = ArithmeticCalculator.calculate("123456789.12345678");
        assertEquals("123456789.", resultado);  // Correção: Arredondamento correto

        resultado = ArithmeticCalculator.calculate("987654321.987654321");
        assertEquals("987654322.", resultado);  // Correção: Arredondamento correto

        resultado = ArithmeticCalculator.calculate("12345.678901234");
        assertEquals("12345.6789", resultado);  // Correção: Arredondamento correto
        
    }

    // Testes que esperam "Math Error"
    @Test
    public void testMathError() {
        
        try {
            ArithmeticCalculator.calculate("12345678901234");
            
        } catch (ArithmeticException e) {
            
        }
        try {
            ArithmeticCalculator.calculate("123456789012345344444444343443");
            
        } catch (ArithmeticException e) {
            Assert.fail("Did not expected ArithmeticException");
        }
        try {
            ArithmeticCalculator.calculate("123456789012346");
            
        } catch (ArithmeticException e) {
            Assert.fail("Did not expected ArithmeticException");
        }
        try {
            ArithmeticCalculator.calculate("10000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
            Assert.fail("Expected ArithmeticException");
            
        } catch (ArithmeticException e) {
            
        }
       
        try {
            ArithmeticCalculator.calculate("12/0");
            Assert.fail("Expected ArithmeticException");
        } catch (ArithmeticException e) {
           
        }
        try {
            ArithmeticCalculator.calculate("12/(3x3-9)");
            Assert.fail("Expected ArithmeticException");
        } catch (ArithmeticException e) {
           
        }
        try {
            ArithmeticCalculator.calculate("50-(.12x23)/0");
            Assert.fail("Expected ArithmeticException");
        } catch (ArithmeticException e) {
           
        }
        
    }
    
}