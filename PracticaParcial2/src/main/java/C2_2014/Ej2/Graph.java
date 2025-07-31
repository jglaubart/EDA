package C2_2014.Ej2;

import java.util.*;

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
        if (origin != null && dest != null && !origin.equals(dest)) {    //puede tener lazos
            // no necesariamente es simple
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
            if(weight < 0){
                throw new IllegalArgumentException("El peso no puede ser negativo");  //peso positivo
            }
            this.neighbor = neighbor;
            this.weight = weight;
        }
    }

    public int contarCaminosMinimos(V origen, V destino) {
        Node start = nodes.get(origen);
        Node end = nodes.get(destino);
        if (start == null || end == null) return 0;

        Map<Node, Double> dist = new HashMap<>();
        Map<Node, Integer> count = new HashMap<>();
        PriorityQueue<NodeDist> pq = new PriorityQueue<>(Comparator.comparingDouble((NodeDist a) -> a.dist));

        for (Node node : nodeList) {
            dist.put(node, Double.POSITIVE_INFINITY);
            count.put(node, 0);
        }

        dist.put(start, 0.0);          // La distancia desde el origen hasta él mismo es 0
        count.put(start, 1);           // Hay 1 forma de llegar al origen: estar ahí
        pq.add(new NodeDist(start, 0.0));

        while (!pq.isEmpty()) {
            NodeDist current = pq.poll();
            Node u = current.node;

            for (Arc arc : u.adj) {
                Node v = arc.neighbor;
                double newDist = dist.get(u) + arc.weight;

                if (newDist < dist.get(v)) {
                    dist.put(v, newDist);             // Nueva mejor distancia
                    count.put(v, count.get(u));
                    pq.add(new NodeDist(v, newDist));
                } else if (newDist == dist.get(v)) {
                    count.put(v, count.get(v) + count.get(u));  // Nuevo camino de distancia minima
                }
            }
        }

        return count.get(end);
    }

    private class NodeDist {
        Node node;
        double dist;
        NodeDist(Node node, double dist) {
            this.node = node;
            this.dist = dist;
        }
    }

    public static void main(String[] args) {
        Graph<Character> graph = new Graph<Character>();
        graph.addVertex('A');
        graph.addVertex('B');
        graph.addVertex('C');
        graph.addVertex('D');
        graph.addVertex('E');
        graph.addArc('A', 'B', 3.0);
        graph.addArc('A', 'D', 2.0);
        graph.addArc('A', 'C', 8.0);
        graph.addArc('D', 'C', 4.0);
        graph.addArc('D', 'E', 2.0);
        graph.addArc('B', 'D', 1.0);
        graph.addArc('B', 'E', 1.0);

        System.out.println(graph.contarCaminosMinimos('A', 'B'));
        System.out.println(graph.contarCaminosMinimos('A', 'C'));
        System.out.println(graph.contarCaminosMinimos('A', 'E'));

    }
}
