package C2_2023.Ej2;

import java.util.*;

public class AdjacencyListGraph<V, E> implements GraphService<V, E> {

    private boolean isSimple;
    protected boolean isDirected;
    private boolean acceptSelfLoop;
    private boolean isWeighted;
    private final Map<V, Collection<InternalEdge>> adjacencyList= new HashMap<>();

    protected   Map<V, Collection<AdjacencyListGraph<V,E>.InternalEdge>> getAdjacencyList() {
        return adjacencyList;
    }
    public Collection<V> getVertices() {
        return getAdjacencyList().keySet() ;
    }

    protected AdjacencyListGraph(boolean isSimple, boolean isDirected, boolean acceptSelfLoop, boolean isWeighted) {
        this.isSimple = isSimple;
        this.isDirected = isDirected;
        this.acceptSelfLoop= acceptSelfLoop;
        this.isWeighted = isWeighted;
    }
    @Override
    public int connectedComponentsQty() {
        if (isDirected) {
            throw new UnsupportedOperationException("No se puede calcular componentes conexas en grafos dirigidos.");
        }

        Collection<V> vertices = getVertices();
        Set<V> visited = new HashSet<>();
        int count = 0;

        for (V v : vertices) {
            if (!visited.contains(v)) {
                dfs(v, visited);
                count++;
            }
        }
        return count;
    }

    private void dfs(V v, java.util.Set<V> visited) {
        visited.add(v);
        for (InternalEdge edge : getAdjacencyList().get(v)) {
            V neighbor = edge.target;
            if (!visited.contains(neighbor)) {
                dfs(neighbor, visited);
            }
        }
    }


    public void addVertex(V aVertex) {

        if (aVertex == null )
            throw new IllegalArgumentException("addVertexParamCannotBeNull");

        // no edges yet
        getAdjacencyList().putIfAbsent(aVertex, new ArrayList<InternalEdge>());
    }

    public void addEdge(V aVertex, V otherVertex, E theEdge) {

        // validation!!!!
        if (aVertex == null || otherVertex == null || theEdge == null)
            throw new IllegalArgumentException("addEdgeParamCannotBeNull");

        // es con peso? debe tener implementado el metodo double getWeight()
        if (isWeighted) {
            // reflection
            Class<?> c = theEdge.getClass();
            try {
                c.getDeclaredMethod("getWeight");
            } catch (NoSuchMethodException | SecurityException e) {
                throw new RuntimeException( "Graph is weighted but the method double getWeighed() is not declared in theEdge");
            }
        }

        if (! acceptSelfLoop && aVertex.equals(otherVertex)) {
            throw new RuntimeException(String.format("Graph does not accept self loops between %s and %s" ,
                    aVertex, otherVertex) );
        }

        // if any of the vertex is not presented, the node is created automatically
        addVertex(aVertex);
        addVertex(otherVertex);
    }

    class InternalEdge{
        E edge;
        V target;

        InternalEdge(E propEdge, V target) {
            this.target = target;
            this.edge = propEdge;
        }
    }
}
