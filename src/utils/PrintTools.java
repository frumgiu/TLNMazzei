package utils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;

public class PrintTools {
    public static void printResultMatrix(int n, String startSymbol, ArrayList<String> line, HashMap<Integer, HashMap<Integer, ArrayList<Casella>>> table) {
        System.out.print("RESULT: ");
        if (table.get(0) != null && table.get(0).get(n) != null && table.get(0).get(n).size() != 0 && table.get(0).get(n).stream().anyMatch(s -> s.getElement().equals(startSymbol)))
            System.out.println("The line IS an element of the Context Free Grammar defined");
        else
            System.out.println("The line IS NOT an element of the Context Free Grammar defined");

        System.out.println("Table created from the CYK algorithm");
        int numSpace = findLongString(table);
        int numSpaceLine = findLongestWord(line);
        for (int j = 0; j < n; j++) {
            System.out.printf("%" + numSpaceLine + "s %-" + numSpaceLine + "s", line.get(j), " | ");
            for (int i = 1; i <= n; i++) {
                if (table.containsKey(j) && table.get(j).containsKey(i)) {
                    System.out.printf("%" + numSpace + "s", table.get(j).get(i));
                } else {
                    System.out.printf("%" + numSpace + "s", "-");
                }
            }
            System.out.print("\n");
        }
        System.out.print("\n\n");
    }
    private static int findLongestWord(ArrayList<String> line) {
        if (line.stream().max(Comparator.comparing(String::length)).isPresent()) {
            String s = line.stream().max(Comparator.comparing(String::length)).get();
            return s.length();
        }
        return 10;
    }

    private static int findLongString(HashMap<Integer, HashMap<Integer, ArrayList<Casella>>> table) {
        int total = 5;
        String s = table.values().stream()                      // stream dei valori della prima mappa
                .flatMap(m -> m.values().stream())              // stream dei valori della seconda mappa
                .flatMap(Collection::stream)                    // stream degli elementi della lista
                .map(Casella::getElement).max(Comparator.comparing(String::length)).orElse(null);
        if (s != null)
            total = s.length();
        return 45;
    }

    public static void printTree(int n, String startSymbol, HashMap<Integer, HashMap<Integer, ArrayList<Casella>>> result) {
        ArrayList<Casella> leaves = result.get(0).get(n);   // prendi le foglie dell'albero
        System.out.println("Albero/i parsificazione:\n");
        for (Casella leaf : leaves) {
            if (leaf.getElement().equals(startSymbol)) {
                printNode(leaf, 0);                   // stampa la foglia
                System.out.println("\n----------------------------\n");
            }
        }
        System.out.print("\n\n");
    }

    private static void printNode(Casella node, int depth) {
        if (node != null) {
            for (int i = 0; i < depth; i++) {
                System.out.print("\t");                     // stampa una tabulazione per ogni livello di profondità
            }
            System.out.println(node.getElement());          // stampa l'elemento del nodo corrente
            ArrayList<Casella> parents = new ArrayList<>(); // prendi i genitori del nodo corrente
            parents.add(node.getParentOne());
            parents.add(node.getParentTwo());
            for (Casella parent : parents) {
                printNode(parent, depth + 1);         // stampa i genitori del nodo corrente
            }
        }
    }
}