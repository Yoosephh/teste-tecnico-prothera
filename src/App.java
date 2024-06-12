import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;

public class App {
    public static void main(String[] args) throws Exception {
        Funcionario[][] team = mockData();
        // System.out.println("O retorno a seguir se refere à equipe completa:");
        // System.out.println(Arrays.deepToString(team).replace("], ", "]\n"));
        // System.out.println("Equipe completa retornada!");
        
        // System.out.println("Removendo João da Lista!");
        // Object[][] newTeam = removeData(team, 1);
        // System.out.println(Arrays.deepToString(newTeam).replace("], ", "]\n"));
        // System.out.println("Tchau, João!");

        for (Funcionario[] funcionarioArr : team) {
            for(Funcionario funcionario : funcionarioArr) {
                System.out.println("{" +
                    "nome='" + funcionario.getNome() + '\'' +
                    ", dataNascimento=" + formatData(funcionario.getDataNascimento()) +
                    ", salario=" + formatSalary(funcionario.getSalario())  +
                    ", funcao='" + funcionario.getFuncao() + '\'' +
                    '}'
                );
            }
        }
        System.out.println("Atualizando salarios:");
        salaryIncrease(team);
        System.out.println(Arrays.deepToString(team).replace("], ", "]\n"));
        System.out.println("Agrupando os funcionários...");
        

    }

    public static Funcionario[][] mockData() {
        return new Funcionario[][] { {
            new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador") },
            { new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador") },
            { new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador") },
            { new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor") },
            { new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista") },
            { new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador") },
            { new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador") },
            { new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente") },
            { new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista") },
            { new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente") } 
        };
    }

    public static Funcionario[][] removeData(Funcionario[][] data, int index) {
        if (index < 0 || index >= data.length) {
            throw new IllegalArgumentException("Índice fora dos limites da matriz.");
        }

        Funcionario[][] newData = new Funcionario[data.length - 1][];
        for (int i = 0, j = 0; i < data.length; i++) {
            if (i != index) {
                newData[j++] = data[i];
            }
        }
        return newData;
    }

    public static String formatData(LocalDate dataNascimento) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return dataNascimento.format(formatter);
    }

    public static String formatSalary(BigDecimal salario) {
        DecimalFormatSymbols dFormat = new DecimalFormatSymbols();
        dFormat.setDecimalSeparator(',');
        dFormat.setGroupingSeparator('.');
        return new DecimalFormat("#,###.00", dFormat).format(salario);
    }

    public static void salaryIncrease(Funcionario[][] data) {
        for (Funcionario[] funcionarioArr : data) {
            for(Funcionario funcionario : funcionarioArr) {
                funcionario.setSalario(funcionario.getSalario().multiply(BigDecimal.valueOf(1.1)));
            }
        }
    }

}
