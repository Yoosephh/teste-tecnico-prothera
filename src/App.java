import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Cadastrando equipe..");
        Funcionario[] time = mockData();
        Thread.sleep(1500);
        System.out.println("Equipe cadastrada com sucesso!");
        System.out.println();
        Thread.sleep(750);

        System.out.println("Removendo João da Lista...");
        Funcionario[] novoTime = removeData(time, 1);
        Thread.sleep(1500);
        System.out.println("Tchau, João!");
        Thread.sleep(750);

        System.out.println();
        System.out.println("Formatando dados da equipe...");
        Thread.sleep(1500);
        formatData(novoTime);
        Thread.sleep(750);

        System.out.println();
        System.out.println("Atualizando salarios...");
        salaryIncrease(novoTime);
        Thread.sleep(1500);
        System.out.println("Salários aumentados! Todos estão 110% felizes!");
        System.out.println();
        Thread.sleep(750);

        System.out.println("E os aniversariantes de Outubro ou Dezembro são...");
        Thread.sleep(1500);
        printBirthdays(novoTime);
        Thread.sleep(750);
        System.out.println("Parabéns aos aniversariantes!");
        System.out.println();
        Thread.sleep(750);

        System.out.println("E o Elder Senior da equipe é...");
        Thread.sleep(1500);
        getOlderPerson(novoTime);
        System.out.println();

        System.out.println("Hora de organizar um pouco as coisas!");
        Thread.sleep(1500);
        Arrays.sort(novoTime, Comparator.comparing(Funcionario::getNome));
        for(Funcionario funcionario : novoTime) {
            System.out.println(funcionario);
        }
        Thread.sleep(750);
        System.out.println("Os funcionários estão em ordem alfabética! :D");
        System.out.println();
        Thread.sleep(750);

        System.out.println("Hora de calcular os gastos mensais de salário...");
        Thread.sleep(1500);
        totalSalary(novoTime);
        System.out.println();
        Thread.sleep(750);

        System.out.println("Vamos ver quantos salários mínimos cada pessoa ganha?");
        Thread.sleep(1500);
        for(Funcionario funcionario : novoTime) {
            System.out.println("{nome: " + funcionario.getNome() + ", salariosMinimos: " + 
            funcionario.getSalario().divide(BigDecimal.valueOf(1212), 2, RoundingMode.HALF_UP) + " salarios}");
        }
    }

    public static Funcionario[] mockData() {
        Funcionario[] time = new Funcionario[] { 
            new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"),
            new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador") ,
            new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador") ,
            new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor") ,
            new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista") ,
            new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador") ,
            new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador") ,
            new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente") ,
            new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista") ,
            new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente")  
        };
        return time;
    }

    public static Funcionario[] removeData(Funcionario[] data, int index) {
        if (index < 0 || index >= data.length) {
            throw new IllegalArgumentException("Índice fora dos limites da matriz.");
        }
        Funcionario[] newData = new Funcionario[data.length - 1];
        for (int i = 0, j = 0; i < data.length; i++) {
            if (i != index) {
                newData[j++] = data[i];
            }
        }
        return newData;
    }

    public static void formatData(Funcionario[] time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        DecimalFormatSymbols decFormat = new DecimalFormatSymbols();
        decFormat.setDecimalSeparator(',');
        decFormat.setGroupingSeparator('.');

        for(Funcionario funcionario : time) {
            System.out.println(
                "{nome='" + funcionario.getNome() + '\'' +
                ", dataNascimento=" + funcionario.getDataNascimento().format(formatter) +
                ", salario=" + new DecimalFormat("#,###.00", decFormat).format(funcionario.getSalario()) +
                ", funcao='" + funcionario.getFuncao() + '\'' +
                '}'
            );
        }
    }

    public static void salaryIncrease(Funcionario[] data) {
        for (Funcionario funcionario : data) {
            funcionario.setSalario(funcionario.getSalario().multiply(BigDecimal.valueOf(1.1)));
        }
    }

    public static void printBirthdays(Funcionario[] time) {
        for (Funcionario funcionario : time) {
            LocalDate date = funcionario.getDataNascimento();
            if (date.getMonth().toString().equals("OCTOBER") || date.getMonth().toString().equals("DECEMBER")) {
                System.out.println("{" +
                    "nome='" + funcionario.getNome() + '\'' +
                    ", dataNascimento=" + date +
                    ", salario=" + funcionario.getSalario() +
                    ", funcao='" + funcionario.getFuncao() + '\'' +
                    '}'
                );
            }
        }
    }

    public static void getOlderPerson(Funcionario[] time) {
        Funcionario oldestWorker = time[0];
        
        for(int i = 1; i < time.length ; i++) {
            if(time[i].getDataNascimento().isBefore(oldestWorker.getDataNascimento())) {
                oldestWorker = time[i];
            }
        }
        System.out.println(
            "{nome:" + oldestWorker.getNome() +
            ", idade: " + Period.between(oldestWorker.getDataNascimento(), LocalDate.now()).getYears() + " anos}"
        );
    }

    public static void totalSalary(Funcionario[] time) {
        BigDecimal total = BigDecimal.valueOf(0);
        for (Funcionario funcionario : time) {
            total = total.add(funcionario.getSalario());
        }
        System.out.println("Gasto total: " + NumberFormat.getCurrencyInstance().format(total));
    }
}
