/**
 * 
 */
package core;



import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;


// same interface for graph, digraph, multigraph, weighted graph, etc

// V participa de hashing. Redefinir equals y hashcode para que detecte
// correctamente repetidos segun se desee


// E precisa la redefinicion de equals para que en remove lo encuentre 
// y lo pueda borrar. Actualmente no participa de un hashing. Esta encapsulado
// dentro de un objeto InternalEdge que lo contiene junto con el V destino. 
// Esa clase InternalEdge s� tiene hashcode y equals implementado.

public interface GraphService<V,E> {
	
	enum Multiplicity { SIMPLE, MULTIPLE};
	enum EdgeMode { UNDIRECTED, DIRECTED};
	enum SelfLoop { NO, YES};
	enum Weight{ NO, YES	};
	enum Storage { SPARSE, DENSE };

	// devuelve caracteristicas de la forma en que fue creado
	String getType();
		
	
	// if exists lo ignora
	// else generate a new vertex 
	void addVertex(V aVertex);

	
	int numberOfVertices();
	
	
	Collection<V> getVertices();

	
	// parameters cannot be null
	// if any of the vertices is not present, the node is created automatically

	// in the case of a weighted graph, E might implements the method double getWeight()
	// otherwise an exception will be thrown
		
	// if the graph is not "multi" and there exists other edge with same source and target, 
	// throws exception
	void addEdge(V aVertex, V otherVertex, E theEdge);

	
	int numberOfEdges();
	
	

	// if the vertex does not exists returns false
	// else remove the vertex and its incident edges and returns true
	boolean removeVertex(V aVertex);
	
	// removes the only edge that connects aVertex and otherVertex in a simple/default graph
	// if the edge does not exists returns false
	// else returns removes it and returns true
	// the method is not allowed in multi graph/digraph=> throws exception
	boolean removeEdge(V aVertex, V otherVertex);

	
	
	// removes all the edges that connects aVertex and otherVertex in multi graph/digraph with theEdge properties
	// removes the only edge that connects aVertex and otherVertex in a simple graph/digraph, 
	// without considering the edge properties
	// if the edge does not exists returns false
	// else returns returns true
	boolean removeEdge(V aVertex, V otherVertex, E theEdge);

	
	// dump all the informations and structure of vertexes and edges in any order
	void dump();
	
	
	// if directed or vertex does not exists: throw exception
	// if self loop contributes twice
	int degree(V vertex);
	
	// if undirected or vertex does not exists: throw exception
	// if self loop contributes twice
	int inDegree(V vertex);
	
	// if undirected or vertex does not exists: throw exception
	// if self loop contributes twice
	int outDegree(V vertex);
	
	// only for simple graph/digraph
	// multi: throw exception
	DijkstraPath<V,E> dijsktra(V source);

	void printBFS(V vertex);

	void printDFS(V vertex);

	Iterable<V> getBFS(V startNode);

	Iterable<V> getDFS(V startNode);

	void printAllPaths(V start, V end);
	void printAllPaths(V startNode, V endNode, Set<V> visited, ArrayList<V> path);

	boolean isBipartite();

	boolean hasCycle();
	
}
