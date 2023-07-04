import java.util.LinkedList;

public class TreeNode {
    int depth;
    int atributo_nome;
    int atributo_valor;
    boolean[] atributos_usados;
    LinkedList<Integer> indices;
    LinkedList<TreeNode> filhos;

    TreeNode(int d, int a_nome, int a_valor, boolean[] a_usados) {
        depth = d;
        atributo_nome = a_nome;
        atributo_valor = a_valor;
        atributos_usados = a_usados;
        indices = new LinkedList<>();
        filhos = new LinkedList<>();
    }

    // esta funcao determina se todos os valores presentes no nó são da mesma classe
    public boolean is_pure(int[][] bd) {
        for (Integer line : indices) {
            if (bd[line][bd[0].length - 1] != bd[indices.getFirst()][bd[0].length - 1])
                return false;
        }
        return true;
    }

    // esta funcao determina o valor de entropia para sub-set de dados
    public Double entropy(LinkedList<Integer> indices, int[][] db, int numclasses) { // numclasses =
                                                                                     // atributos[length-1].valores.size()
        Double entropy = 0.0;
        int[] pk = new int[numclasses];

        for (Integer line : indices) {
            pk[db[line][db[0].length - 1]]++;
        }
        for (int i = 0; i < numclasses; i++) {
            Double p = (pk[i] / 1.0) / indices.size();
            if (p > 0) {
                entropy += p * (Math.log(p) / Math.log(2));
            }
        }

        return -entropy*indices.size();
    }

    // esta funcao calcula a entropia de cada possível de split e retorna o melhor
    public int choosebest(Atributo[] atributos, int[][] db) {
        int bestatributo = -1;
        Double bestentropy = 2.0;
        for (int i = 0; i < atributos.length - 1; i++) {
            if (!atributos_usados[i]) {
                Double entropy = 0.0;
                for (int j = 0; j < atributos[i].valores.size(); j++) {
                    LinkedList<Integer> indices_temp = new LinkedList<>();
                    for (Integer line : indices) {
                        if (db[line][i] == j)
                            indices_temp.add(line);
                    }
                    if (indices_temp.size() > 0) {
                        entropy += entropy(indices_temp, db, atributos[atributos.length - 1].valores.size());
                    }
                }
                if ((entropy / indices.size()) < bestentropy) {
                    bestatributo = i;
                    bestentropy = entropy / indices.size();
                }
            }
        }
        return bestatributo;
    }

    // esta funcao após escolher o split, executa-o
    public void expandbest(Atributo[] atributos, int[][] db) {
        int atributo = choosebest(atributos, db);
        atributo_nome = atributo;

        if (atributo != -1 && !is_pure(db) /* && indices.size() > valor_minimo_de_instancias_por_no */) {
            for (int i = 0; i < atributos[atributo].valores.size(); i++) {
                LinkedList<Integer> indices_temp = new LinkedList<>();
                for (Integer line : indices) {
                    if (db[line][atributo] == i)
                        indices_temp.add(line);
                }
                if (indices_temp.size() > 0) {
                    boolean[] usados = new boolean[atributos.length];
                    for (int j = 0; j < usados.length; j++) {
                        usados[j] = atributos_usados[j];
                    }
                    usados[atributo] = true;
                    TreeNode filho = new TreeNode(depth + 1, atributo, i, usados);
                    filho.indices = indices_temp;
                    filhos.add(filho);
                }
            }
        }
    }

    // esta funcao determina a classe mais predominante neste no
    public int maxclass(Atributo[] atributos, int[][] db) {
        int[] class_counter = new int[atributos[atributos.length - 1].valores.size()];
        for (Integer line : indices) {
            class_counter[db[line][db[0].length - 1]]++;
        }
        int max = 0;
        int maxclass = -1;
        for (int i = 0; i < class_counter.length; i++) {
            if (class_counter[i] > max) {
                max = class_counter[i];
                maxclass = i;
            }
        }
        return maxclass;
    }
}
