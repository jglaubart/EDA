package jglaubart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class MultiDirectedGraph<V, E> {

    private Map<V, Collection<InternalEdge>> adjacencyList= new HashMap<>();

    public Collection<V> getVertices() {
        return adjacencyList.keySet() ;
    }

    protected MultiDirectedGraph(){
    }

    public void addVertex(V aVertex) {

        if (aVertex == null )
            throw new IllegalArgumentException("addVertexParamCannotBeNull");

        // no edges yet
        adjacencyList.putIfAbsent(aVertex,
                new ArrayList<InternalEdge>());
    }

    public void addEdge(V aVertex, V otherVertex, E theEdge) {

        if (aVertex == null || otherVertex == null || theEdge == null)
            throw new IllegalArgumentException("addEdgeParamCannotBeNull");

        addVertex(aVertex);
        addVertex(otherVertex);

        adjacencyList.get(aVertex).add( new InternalEdge(theEdge, otherVertex) );
    }

    void printAllVertexes(){
        for( V v : getVertices() )
            System.out.println( v );
    }

    void printAllEdges(){
        for( Map.Entry<V, Collection<InternalEdge>> entry : adjacencyList.entrySet() )
            for( InternalEdge i : entry.getValue() )
                System.out.println( entry.getKey().toString() + " -> "  + i.target.toString() + "[" + i.edge.toString() + "]" );
    }


    class InternalEdge {
        E edge;
        V target;

        InternalEdge(E propEdge, V target) {
            this.target = target;
            this.edge = propEdge;
        }
    }

    MultiDirectedGraph<V, E> transposeSummarize(BiFunction<E, E, E> summarizer) {
        MultiDirectedGraph<V, E> transposed = new MultiDirectedGraph<>();

        Map<V, Map<V, E>> tempAdjacency = new HashMap<>();

        for (Map.Entry<V, Collection<InternalEdge>> entry : adjacencyList.entrySet()) {
            V from = entry.getKey();
            for (InternalEdge internalEdge : entry.getValue()) {
                V to = internalEdge.target;
                E edge = internalEdge.edge;

                tempAdjacency.putIfAbsent(to, new HashMap<>());
                Map<V, E> innerMap = tempAdjacency.get(to);

                if (innerMap.containsKey(from)) {
                    E combinedEdge = summarizer.apply(innerMap.get(from), edge);
                    innerMap.put(from, combinedEdge);
                } else {
                    innerMap.put(from, edge);
                }
            }
        }

        for (Map.Entry<V, Map<V, E>> entry : tempAdjacency.entrySet()) {
            V from = entry.getKey();
            transposed.addVertex(from);
            for (Map.Entry<V, E> innerEntry : entry.getValue().entrySet()) {
                V to = innerEntry.getKey();
                E edge = innerEntry.getValue();
                transposed.addEdge(from, to, edge);
            }
        }

        return transposed;
    }


    // ejemplo 1
    public static void main(String[] args) {
        MultiDirectedGraph<String, Integer> myGraph = new MultiDirectedGraph<>();

        myGraph.addEdge("A", "B", 1);
        myGraph.addEdge("B", "C", 2 );
        myGraph.addEdge("B", "D", 3);
        myGraph.addEdge("D", "C", 4);
        myGraph.addEdge("D", "E", 5);
        myGraph.addEdge("A", "E", 6);
        myGraph.addEdge("E", "G", 7);
        myGraph.addEdge("G", "F", 8);
        myGraph.addEdge("F", "E", 9);
        myGraph.addEdge("A", "B", 10 );
        myGraph.addEdge("B", "C", 11 );
        myGraph.addEdge("B", "C", 12 );
        myGraph.addEdge("E", "G", 13 );

        System.out.println("ORIGINAL GRAPH");
        myGraph.printAllVertexes();
        myGraph.printAllEdges();

        MultiDirectedGraph<String, Integer> transposedGraph =  myGraph.transposeSummarize( (n,m) -> n+m );

        System.out.println("TRANSPOSED SUMMARIZED GRAPH");
        transposedGraph.printAllVertexes();
        transposedGraph.printAllEdges();

    }


   // ejemplo 2
   public static void main2(String[] args) {
        MultiDirectedGraph<String, Integer> myGraph = new MultiDirectedGraph<>();

        myGraph.addEdge("B", "C", 11 );
        myGraph.addEdge("B", "C", 12 );
        myGraph.addEdge("C", "B", 2);

        myGraph.addEdge("B", "D", 3);
        myGraph.addEdge("D", "C", 4);

        myGraph.addEdge("E", "G", 7);

        myGraph.addEdge("G", "F", 8);
        myGraph.addEdge("F", "G", -3 );

        myGraph.addEdge("F", "E", 9);

        myGraph.addEdge("E", "G", 13 );

        System.out.println("ORIGINAL GRAPH");
        myGraph.printAllVertexes();
        myGraph.printAllEdges();

        MultiDirectedGraph<String, Integer> transposedGraph =  myGraph.transposeSummarize( (n,m) -> n+m );

        System.out.println("TRANSPOSED SUMMARIZED GRAPH");
        transposedGraph.printAllVertexes();
        transposedGraph.printAllEdges();

    }
}

