package C2_2013;

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
        if (origin != null && dest != null && !origin.equals(dest)) {
            for (Arc arc : origin.adj) {
                if (arc.neighbor.info.equals(w)) {
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

    public List<V> cicloToniano() {
        int n = nodeList.size();
        if (n == 0) return null;
        int minLength = (n + 1) / 2;
        for (Node start : nodeList) {
            List<V> path = new ArrayList<>();
            Set<Node> visited = new HashSet<>();
            if (dfsCicloToniano(start, start, path, visited, minLength, 0)) {
                return path;
            }
        }
        return null;
    }

    private boolean dfsCicloToniano(Node current, Node start, List<V> path, Set<Node> visited, int minLength, int depth) {
        visited.add(current);
        path.add(current.info);

        for (Arc arc : current.adj) {
            Node neighbor = arc.neighbor;
            if (neighbor.equals(start) && depth + 1 >= minLength) {
                path.add(start.info); // Cierra el ciclo
                return true;
            }
            if (!visited.contains(neighbor)) {
                if (dfsCicloToniano(neighbor, start, path, visited, minLength, depth + 1)) {
                    return true;
                }
            }
        }
        path.removeLast();
        visited.remove(current);
        return false;
    }


    public double radio() {
        double radio = Double.POSITIVE_INFINITY;
        if (nodeList.isEmpty()) return radio;
        for (Node node : nodeList) {
            double excentricidad = excentricidad(node);
            if (excentricidad < radio) {
                radio = excentricidad;
            }
        }
        return radio;
    }

    private double excentricidad(Node source) {
        Map<Node, Double> dist = new HashMap<>();
        for (Node node : nodeList) {
            dist.put(node, Double.POSITIVE_INFINITY);
        }
        dist.put(source, 0.0);
        PriorityQueue<NodeDist> pq = new PriorityQueue<>(Comparator.comparingDouble(nd -> nd.dist));
        pq.add(new NodeDist(source, 0.0));

        while (!pq.isEmpty()) {
            NodeDist current = pq.poll();
            Node u = current.node;
            for (Arc arc : u.adj) {
                Node v = arc.neighbor;
                double newDist = dist.get(u) + arc.weight;
                if (newDist < dist.get(v)) {
                    dist.put(v, newDist);
                    pq.add(new NodeDist(v, newDist));
                }
            }
        }
        double maxDist = 0.0;
        for (double d : dist.values()) {
            if (d != Double.POSITIVE_INFINITY && d > maxDist) {
                maxDist = d;
            }
        }
        return maxDist;
    }

    private class NodeDist {
        Node node;
        double dist;
        NodeDist(Node node, double dist) {
            this.node = node;
            this.dist = dist;
        }
    }
}
