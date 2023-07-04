import java.util.LinkedList;

public class Tree {
    public TreeNode root;

    Tree(boolean[] u, LinkedList<Integer> i) {
        root = new TreeNode(0, -1, -1, u);
        root.indices = i;
    }

    // esta função expande a árvore toda recursivamente 
    public void expand_all(Atributo[] atributos, int[][] db) {
        expand_all(root, atributos, db);
    }

    public void expand_all(TreeNode n, Atributo[] atributos, int[][] db) {
        n.expandbest(atributos, db);
        for (TreeNode filho : n.filhos) {
            expand_all(filho, atributos, db);
        }
    }

    // esta funcao imprime a estrutura da arvore
    public void print_tree(Atributo[] atributos, int[][] db, int a_nome) {
        print_tree(root, atributos, db, a_nome);
    }

    public void print_tree(TreeNode n, Atributo[] atributos, int[][] db, int a_nome) {
        if (n.depth != 0) {
            for (int i = 0; i < 2 * n.depth - 1; i++) {
                System.out.print("   ");
            }
            System.out.print(atributos[a_nome].valores.get(n.atributo_valor) + ": ");
        }
        if (n.filhos.isEmpty()) {
            int[] class_counter = new int[atributos[atributos.length - 1].valores.size()];
            for (Integer line : n.indices) {
                class_counter[db[line][db[0].length - 1]]++;
            }
            for (int i = 0; i < class_counter.length; i++) {
                if (class_counter[i] != 0) {
                    System.out.print(
                            atributos[atributos.length - 1].valores.get(i) + " (" + class_counter[i] + ")         ");
                }
            }
            System.out.println();
            return;
        }
        System.out.println();
        for (int i = 0; i < 2 * n.depth; i++) {
            System.out.print("   ");
        }
        System.out.println("<" + atributos[n.atributo_nome].nome + ">");
        for (TreeNode filho : n.filhos) {
            print_tree(filho, atributos, db, n.atributo_nome);
        }
    }

    // esta funcao utiliza a árvore para prever a classe de uma nova instancia
    public String predict(Atributo[] atributos, int[] new_line, int[][] db) {
        return predict(root, atributos, new_line, db);
    }

    public String predict(TreeNode n, Atributo[] atributos, int[] new_line, int[][] db) {
        if (n.filhos.isEmpty()) {
            return atributos[atributos.length - 1].valores.get(n.maxclass(atributos, db));
        }
        for (TreeNode filho : n.filhos) {
            if (filho.atributo_valor == new_line[n.atributo_nome]) {
                return predict(filho, atributos, new_line, db);
            }
        }
        return atributos[atributos.length - 1].valores.get(n.maxclass(atributos, db));
    }
}
