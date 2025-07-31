package C2_2014.Ej1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph<V> {
    private HashMap<V, Node> nodes = new HashMap<V, Node>();
    private List<Node> nodeList = new ArrayList<Node>();
    public void addVertex(V vertex) {
        if (!nodes.containsKey(vertex)) {
            Node node = new Node(vertex);
            nodes.put(vertex, node);
            nodeList.add(node);
        }
    }
    public void addArc(V v, V w, Double d) {
        Node origin = nodes.get(v);
        Node dest = nodes.get(w);
        if (origin != null && dest != null && !origin.equals(dest)) {    //sin lazos
            for (Arc arc : origin.adj) {
                if (arc.neighbor.info.equals(w)) {     //grafo simple, si ya esta no lo agrega
                    return;
                }
            }
            origin.adj.add(new Arc(dest, d));
            dest.adj.add(new Arc(origin, d));
        }
    }
    private class Node {
        V info;
        boolean visited = false;
        int tag = 0;
        List<Arc> adj = new ArrayList<Arc>();
        public Node(V info) {
            this.info = info;
        }
        public int hashCode() {
            return info.hashCode();
        }
        public boolean equals(Object obj) {
            if (obj == null || (obj.getClass() != getClass())) {
                return false;
            }
            return info.equals(((Node)obj).info);
        }
    }
    private class Arc {
        Node neighbor;
        Double weight;
        public Arc(Node neighbor, Double weight) {
            this.neighbor = neighbor;
            this.weight = weight;
        }
    }


    public List<V> findPathWithExactWeight(double targetWeight) {
        List<V> path = new ArrayList<>();
        for (Node startNode : nodeList) {  //dfs por todos los nodos hasta encintrar un camino
            for (Node node : nodeList) {   // reseteo todos los visitados del anterior
                node.visited = false;
            }

            path.clear();
            if (dfs(startNode, targetWeight, path)) {    //dfs devuelve true si encontro un camino con el peso indicado
                return path;
            }
        }
        return null;
    }

    private boolean dfs(Node current, double remainingWeight, List<V> path) {   //
        current.visited = true;
        path.add(current.info);

        if (remainingWeight == 0 && path.size() > 1) {
            return true; // se encontró un camino válido
        }

        for (Arc arc : current.adj) {
            Node neighbor = arc.neighbor;
            double weight = arc.weight;
            if (!neighbor.visited) {
                if (dfs(neighbor, remainingWeight - weight, path)) {
                    return true;
                }
            }
        }

        // lo saco si ya fue visitado
        path.removeLast();
        current.visited = false;
        return false;
    }

    public static void main(String[] args) {
        Graph<Character> graph = new Graph<>();
        graph.addVertex('A');
        graph.addVertex('B');
        graph.addVertex('C');
        graph.addVertex('D');
        graph.addVertex('E');
        graph.addArc('A', 'B', 4.0);
        graph.addArc('A', 'D', 2.0);
        graph.addArc('A', 'E', 6.0);
        graph.addArc('B', 'C', -2.0);
        graph.addArc('B', 'E', 7.0);
        graph.addArc('E', 'D', 1.0);

        System.out.println(graph.findPathWithExactWeight(7.0));
        System.out.println(graph.findPathWithExactWeight(5.0));
        System.out.println(graph.findPathWithExactWeight(12.0));
        System.out.println(graph.findPathWithExactWeight(15.0));
        System.out.println(graph.findPathWithExactWeight(18.0));
    }

}
