package P2.ej2;


import java.util.ArrayList;   
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;



abstract public class AdjacencyListGraph<V, E> implements GraphService<V, E> {

	protected boolean isSimple;
	protected boolean isDirected;
	protected boolean acceptSelfLoop;
	protected boolean isWeighted;
	protected String type;
	

	
	// HashMap no respeta el orden de insercion. En el testing considerar eso
	private Map<V,Collection<InternalEdge>> adjacencyList= new HashMap<>();
	
	protected   Map<V,  Collection<InternalEdge>> getAdjacencyList() {
		return adjacencyList;
	}
	
	
	protected AdjacencyListGraph(boolean isSimple, boolean isDirected, boolean acceptSelfLoop, boolean isWeighted) {
		this.isSimple = isSimple;
		this.isDirected = isDirected;
		this.acceptSelfLoop= acceptSelfLoop;
		this.isWeighted = isWeighted;
		this.type = String.format("%s %sWeighted %sGraph with %sSelfLoop", 
				isSimple ? "Simple" : "Multi", isWeighted ? "" : "Non-",
				isDirected ? "Di" : "", acceptSelfLoop? "":"No ");

	}
	
	
	@Override
	public Collection<V> getVertices() {
		return getAdjacencyList().keySet() ;
	}
	
	@Override
	public void addVertex(V aVertex) {
	
		if (aVertex == null )
            throw new IllegalArgumentException("addVertexParamCannotBeNull");
	
		// no edges yet
		getAdjacencyList().putIfAbsent(aVertex, 
				new ArrayList<InternalEdge>());
	}


	

	@Override
	public void addEdge(V aVertex, V otherVertex, E theEdge) {

		// validation!!!!
		if (aVertex == null || otherVertex == null || theEdge == null)
            throw new IllegalArgumentException("addEdgeParamCannotBeNull");

		// es con peso? debe tener implementado el metodo double getWeight()
		if (isWeighted) {
			// reflection
			Class<? extends Object> c = theEdge.getClass();
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

	

	

	
	

	
	protected boolean existsVertex(V aVertex) {
		return getAdjacencyList().containsKey(aVertex);
	}
	
	@Override
	public int degree(V aVertex) {
		if (aVertex == null || !existsVertex(aVertex) )
			throw new IllegalArgumentException("vertex parameter does not exist"); 
		
				
		if ( isDirected )
			throw new IllegalArgumentException("degree expects an undirected graph");
		

		Collection<InternalEdge> adjList = getAdjacencyList().get(aVertex);
		return adjList.size();
	}

	

	@Override
	public int inDegree(V aVertex) {
		throw new RuntimeException("not implemented yet");
	}



	@Override
	public int outDegree(V aVertex) {
		throw new RuntimeException("not implemented yet");
	}

	

	
	// ATENCION! se cambio la clase, su constructor y el atributo target a puplic.
	public class InternalEdge {
		E edge;
		public V target;

		public InternalEdge(E propEdge, V target) {
			this.target = target;
			this.edge = propEdge;
		}

		@Override
		public boolean equals(Object obj) {
			@SuppressWarnings("unchecked")
			InternalEdge aux = (InternalEdge) obj;

			return ((edge == null && aux.edge == null) || (edge != null && edge.equals(aux.edge)))
					&& target.equals(aux.target);
		}

		@Override
		public int hashCode() {
			return target.hashCode();
		}

		@Override
		public String toString() {
			return String.format("-[%s]-(%s)", edge, target);
		}
	}
	
	@Override
	public void dump() {
		// por ser un MAP hay que recorrer TODO para saber donde estan los buckets
		// habilitados (nodos)
		// o sea, es peor que O(N)
		// con ArrayList hubiera sigo O(N), pero habria que navegar para las operaciones
		System.out.println(type);

		System.out.println("Vertices:");
		Collection<V> vertices = getVertices();
		for (V aV : vertices) {
			System.out.print(String.format("(%s) ", aV));
		}
			
		System.out.println();
		
		System.out.println("Edges:");
		
		for (V aV : vertices) {
			Collection<InternalEdge> lista = getAdjacencyList().get(aV);
			for (InternalEdge e : lista) {
				if (isDirected)
					System.out.println(String.format("(%s) -%s-> (%s)", aV,
							e.edge == null ? "" : e.edge, e.target));
				else {
					// pero lo va a imprimir 2 veces... Aca no hay simetria como en matrices
					System.out.println(String.format("(%s) -%s- (%s)", aV,
							e.edge == null ? "" : e.edge, e.target));
				}
					
			}
		}
	

	}
	
	
}