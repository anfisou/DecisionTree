import java.io.*;
import java.util.Scanner;
import java.util.LinkedList;

// Classe que guarda os atributos e todos os seus possíveis valores
class Atributo {
    public String nome;
    public LinkedList<String> valores;

    // Construtor
    Atributo(String n) {
        nome = n;
        valores = new LinkedList<String>();
        // valores.add("AlgoRidiculoQueNuncaVaiSer"); (parabéns, encontrou o easter
        // egg!)
    }
}

public class DecisionTree {
    // esta funcao é usada para transformar valores númericos em valores categóricos
    public static void treat_data(String[] dados, String filename, int indice) {
        // Normalizar valores do dataset weather
        if (filename.equals("weather.csv")) {
            if (Integer.parseInt(dados[1 + indice]) > 80) {
                dados[1 + indice] = ">80";
            } else if (Integer.parseInt(dados[1 + indice]) > 75) {
                dados[1 + indice] = "76-80";
            } else if (Integer.parseInt(dados[1 + indice]) > 70) {
                dados[1 + indice] = "71-75";
            } else {
                dados[1 + indice] = "64-70";
            }

            if (Integer.parseInt(dados[2 + indice]) > 90) {
                dados[2 + indice] = ">90";
            } else if (Integer.parseInt(dados[2 + indice]) > 80) {
                dados[2 + indice] = "81-90";
            } else if (Integer.parseInt(dados[2 + indice]) > 70) {
                dados[2 + indice] = "71-80";
            } else {
                dados[2 + indice] = "65-70";
            }
        }

        // Normalizar valores do dataset iris
        if (filename.equals("iris.csv")) {
            if (Float.valueOf(dados[indice]) > 7.0) {
                dados[indice] = ">7";
            } else if (Float.valueOf(dados[indice]) > 6.0) {
                dados[indice] = "6-7";
            } else if (Float.valueOf(dados[indice]) > 5.0) {
                dados[indice] = "5-6";
            } else {
                dados[indice] = "4-5";
            }

            if (Float.valueOf(dados[1 + indice]) > 4.0) {
                dados[1 + indice] = ">4";
            } else if (Float.valueOf(dados[1 + indice]) > 3.5) {
                dados[1 + indice] = "3.5-4";
            } else if (Float.valueOf(dados[1 + indice]) > 3.0) {
                dados[1 + indice] = "3-3.5";
            } else if (Float.valueOf(dados[1 + indice]) > 2.5) {
                dados[1 + indice] = "2.5-3";
            } else {
                dados[1 + indice] = "2-2.5";
            }

            if (Float.valueOf(dados[2 + indice]) > 6.0) {
                dados[2 + indice] = ">6";
            } else if (Float.valueOf(dados[2 + indice]) > 5.0) {
                dados[2 + indice] = "5-6";
            } else if (Float.valueOf(dados[2 + indice]) > 4.0) {
                dados[2 + indice] = "4-5";
            } else if (Float.valueOf(dados[2 + indice]) > 3.0) {
                dados[2 + indice] = "3-4";
            } else if (Float.valueOf(dados[2 + indice]) > 2.0) {
                dados[2 + indice] = "2-3";
            } else {
                dados[2 + indice] = "1-2";
            }

            if (Float.valueOf(dados[3 + indice]) > 2.0) {
                dados[3 + indice] = ">2";
            } else if (Float.valueOf(dados[3 + indice]) > 1.5) {
                dados[3 + indice] = "1.5-2";
            } else if (Float.valueOf(dados[3 + indice]) > 1.0) {
                dados[3 + indice] = "1-1.5";
            } else if (Float.valueOf(dados[3 + indice]) > 0.5) {
                dados[3 + indice] = "0.5-1";
            } else {
                dados[3 + indice] = "0-0.5";
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String filename = args[0] + ".csv";

        // Termina caso o dataset não exista
        if (!filename.equals("restaurant.csv") && !filename.equals("weather.csv") && !filename.equals("iris.csv")
                && !filename.equals("connect4.csv")) {
            System.out.println("Dataset not valid!");
            System.exit(0);
        }
        Scanner in = new Scanner(new File(filename));

        // Conta as linhas do dataset
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        int lines = 0;
        while (reader.readLine() != null)
            lines++;
        reader.close();
        lines--;

        // Lê a primeira linha com o nome dos atributos
        String a = in.next();
        String[] at = a.split(",");
        int inicio = 1;

        // Exceção neste dataset porque não tem indice
        if (filename.equals("connect4.csv"))
            inicio = 0;

        // Inicializar o array de atributos
        Atributo[] atributos = new Atributo[at.length - inicio];
        for (int i = inicio; i < at.length; i++) {
            atributos[i - inicio] = new Atributo(at[i]);
        }

        // Ler e normalizar os dados e armazená-los em bd[][]
        int[][] bd = new int[lines][at.length - inicio];
        int cline = 0;
        while (in.hasNext()) {
            a = in.next();
            String[] dados = a.split(",");

            // Normalização
            treat_data(dados, filename, 1);

            // Armazenar os dados
            for (int i = inicio; i < at.length; i++) {
                if (!atributos[i - inicio].valores.contains(dados[i]))
                    atributos[i - inicio].valores.add(dados[i]);
                bd[cline][i - inicio] = atributos[i - inicio].valores.indexOf(dados[i]);
            }
            cline++;
        }

        // Inicializar a árvore
        boolean[] usados = new boolean[atributos.length];
        LinkedList<Integer> indices = new LinkedList<>();
        for (int i = lines - 1; i >= 0; i--) {
            indices.addFirst(i);
        }
        Tree tree = new Tree(usados, indices);

        // Desenvolver a árvore
        tree.expand_all(atributos, bd);
        in.close();
        
        if (!filename.equals("connect4.csv")) {
            // Imprimir a estrutura da árvore
            tree.print_tree(atributos, bd, -1);

            // Prever a classe de novas instancias
            Scanner input = new Scanner(new File("test.csv"));
            BufferedReader test_reader = new BufferedReader(new FileReader("test.csv"));
            int test_lines = 0;

            while (test_reader.readLine() != null)
                test_lines++;
            test_reader.close();

            for (int k = 0; k < test_lines; k++) {
                a = input.next();
                String[] test = a.split(",");
                treat_data(test, filename, 0);
                int[] new_line = new int[atributos.length - 1];
                for (int i = 0; i < atributos.length - 1; i++) {
                    if (!atributos[i].valores.contains(test[i]))
                        atributos[i].valores.add(test[i]);
                    new_line[i] = atributos[i].valores.indexOf(test[i]);
                }
                System.out.println(tree.predict(atributos, new_line, bd));
            }
            input.close();
        } else {
            // Jogar Connect 4 com o Monte Carlo Tree Search usando 
            // a classificação árvore na fase de rollout
            MonteCarlo.run(tree, atributos, bd);
        }
    }
}