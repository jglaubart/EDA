package C2_2023.Ej2; /**
 * 
 */


import java.util.Collection;

public interface GraphService<V,E> {
	
	enum Multiplicity { SIMPLE, MULTIPLE};
	enum EdgeMode { UNDIRECTED, DIRECTED};
	enum SelfLoop { NO, YES};
	enum Weight{ NO, YES	};
	enum Storage { SPARSE, DENSE };


	// if exists lo ignora
	// else generate a new vertex 
	public void addVertex(V aVertex);

	public Collection<V> getVertices();

	// parameters cannot be null
	// if any of the vertices is not present, the node is created automatically

	// in the case of a weighted graph, E might implements the method double getWeight()
	// otherwise an exception will be thrown
		
	// if the graph is not "multi" and there exists other edge with same source and target, 
	// throws exception
	public void addEdge(V aVertex, V otherVertex, E theEdge);

	public int connectedComponentsQty();

}
