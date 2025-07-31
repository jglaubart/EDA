
package C2_2023.Ej2;

import java.util.Collection;

public class SimpleOrDefault<V,E> extends AdjacencyListGraph<V,E> {

	protected SimpleOrDefault(boolean isDirected, boolean acceptSelfLoops, boolean isWeighted) {
		super(true, isDirected, acceptSelfLoops, isWeighted);
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

		if (!isDirected) {
			Collection<AdjacencyListGraph<V, E>.InternalEdge> adjListDst = getAdjacencyList().get(otherVertex);
			adjListDst.add(new InternalEdge(theEdge, aVertex));
		}

	}

}