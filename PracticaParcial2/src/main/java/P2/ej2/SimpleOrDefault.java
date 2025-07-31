package P2.ej2;
 
import java.util.*;

import P2.ej2.*;


public class SimpleOrDefault<V,E> extends AdjacencyListGraph<V,E> {

	public SimpleOrDefault(boolean isDirected, boolean acceptSelfLoops, boolean isWeighted) {
		super(true, isDirected, acceptSelfLoops, isWeighted);
	
	}

	
	@Override
	public double getLocalClusteringCoeff(V aVertex) {
		// TODO Auto-generated method stub
		if (!existsVertex(aVertex))
			throw new RuntimeException("el vertice no existe en el grafo");

		if (!isSimple || isDirected || acceptSelfLoop)
			throw new RuntimeException("tiene que ser simple, sin lazos y no dirigido");

		List<V> neighbors = new ArrayList<>();
		for (InternalEdge e : getAdjacencyList().get(aVertex)) {
			neighbors.add(e.target);
		}
		int n = neighbors.size();
		if (n <= 1) return -1;

		int links = 0;
		Map<V, Set<V>> neighborSet = new HashMap<>();
		for(V u : neighbors) {
			Set<V> set = new HashSet<>();
			for (InternalEdge e : getAdjacencyList().get(u)) {
				set.add(e.target);
			}
			neighborSet.put(u, set);
		}


		for (int i = 0; i < n; i++) {
			V u = neighbors.get(i);
			for (int j = i + 1; j < n; j++) {
				V v = neighbors.get(j);   // sigue sacando los vecinos del vertice original, pero los de mas adelante para no repetir
				if (neighborSet.get(u).contains(v)) links++;
			}
		}
		double den = n * (n - 1) / 2.0;
		return links / den;
	}
	
	
	
	@Override
	public void addEdge(V aVertex, V otherVertex, E theEdge) {

		// validacion y creacion de vertices si fuera necesario
		super.addEdge(aVertex, otherVertex, theEdge);

		
		Collection<InternalEdge> adjListSrc = getAdjacencyList().get(aVertex);

		// if exists edge with same target...
		for (InternalEdge internalEdge : adjListSrc) {
			if (internalEdge.target.equals(otherVertex))
				throw new IllegalArgumentException( "Simple Graph: cannot have repeated edges" );
		}
		

		// creacion de ejes
		adjListSrc.add(new InternalEdge(theEdge, otherVertex));

		if (!isDirected ) {
			Collection<AdjacencyListGraph<V, E>.InternalEdge> adjListDst = getAdjacencyList().get(otherVertex);
			adjListDst.add(new InternalEdge(theEdge, aVertex));
		}
	
	}

	public static void main(String[] args) {

		SimpleOrDefault<String, Integer> g = new SimpleOrDefault<String, Integer>(false, false, false);
		g.addVertex("V2");
		g.addVertex("V3");
		g.addVertex("V4");
		g.addVertex("V5");
		g.addVertex("V6");
		g.addVertex("V9");

		g.addEdge("V2", "V3", 1);
		g.addEdge("V2", "V6", 1);
		g.addEdge("V2", "V4", 1);
		g.addEdge("V2", "V9", 1);
		g.addEdge("V3", "V4", 1);
		g.addEdge("V3", "V9", 1);
		g.addEdge("V4", "V9", 1);
		g.addEdge("V4", "V5", 1);
		g.addEdge("V4", "V6", 1);
		g.addEdge("V5", "V6", 1);
		g.addEdge("V6", "V9", 1);
		g.addEdge("V9", "V5", 1);

		System.out.println(g.getLocalClusteringCoeff("V9"));
	}

}