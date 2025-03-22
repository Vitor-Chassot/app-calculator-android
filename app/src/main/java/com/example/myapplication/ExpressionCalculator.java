import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.Expression;

public class ExpressionCalculator {

    // Método para calcular uma expressão matemática a partir de uma string
    public static double calcularExpressao(String expressao) {
        try {
            Expression expression = new ExpressionBuilder(expressao).build();
            return expression.evaluate();
        } catch (Exception e) {
            System.err.println("Erro ao avaliar a expressão: " + e.getMessage());
            return Double.NaN; // Retorna NaN se houver erro na expressão
        }
    }

    public static void main(String[] args) {
        // Exemplo de uso
        String expressao = "(((((3.5+2.5)*((4-0.5)/(2+0.3)))+((7-2)*(3/(1.5+0.5))))-((8/4)+((6.25-0.25)*2)))*(1+1))";
        double resultado = calcularExpressao(expressao); 
        // Marcando o tempo antes da execução
        int amostra=1000000;
        long startTime = System.nanoTime(); 
        
        for(int i=0;i<amostra;i++)
        resultado = calcularExpressao(expressao); // Calculando o resultado222
        long endTime = System.nanoTime(); // Marcando o tempo após a execução

        // Calculando a duração em nanossegundos
        long duration = endTime - startTime; // Tempo total em nanossegundos
        double durationMs = duration / 1_000_000.0; // Convertendo para milissegundos
        
        // Exibindo o tempo e o resultado
        System.out.println("Tempo total: " + durationMs + " ms");
        System.out.println("Tempo medio: " + (durationMs/amostra) + " ms");
        System.out.println("Resultado: " + resultado);
    }
}
