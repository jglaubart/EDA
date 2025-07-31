package core;


import java.util.*;

abstract public class AdjacencyListGraph<V, E> implements GraphService<V, E> {

	protected boolean isSimple;
	protected boolean isDirected;
	protected boolean acceptSelfLoop;
	protected boolean isWeighted;
	protected String type;

	enum Color{
		WHITE, RED, GREEN
	}
	
	// HashMap no respeta el orden de insercion. En el testing considerar eso
	private Map<V,Collection<InternalEdge>> adjacencyList= new HashMap<>();
	
	// respeta el orden de llegada y facilita el testing
	//	private Map<V,Collection<InternalEdge>> adjacencyList= new LinkedHashMap<>();
	
	protected Map<V,Collection<AdjacencyListGraph<V, E>.InternalEdge>> getAdjacencyList() {
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
	public String getType() {
		return type;
	}
	
	@Override
	public void addVertex(V aVertex) {
	
		if (aVertex == null )
		throw new IllegalArgumentException(Messages.getString("addVertexParamCannotBeNull"));
	
		// no edges yet
		getAdjacencyList().putIfAbsent(aVertex, 
				new ArrayList<InternalEdge>());
	}

	
	@Override
	public int numberOfVertices() {
		return getVertices().size();
	}

	@Override
	public Collection<V> getVertices() {
		return getAdjacencyList().keySet() ;
	}
	
	@Override
	public int numberOfEdges() {
		// COMPLETAR
		int numberOfEdges = 0;

		int mult = isDirected ? 2 : 1;

		for(Map.Entry<V, Collection<InternalEdge>> entry : adjacencyList.entrySet()) {
			numberOfEdges += mult * entry.getValue().size();
		}

		return numberOfEdges / 2;
	}

	

	@Override
	public void addEdge(V aVertex, V otherVertex, E theEdge) {

		// validation!!!!
		if (aVertex == null || otherVertex == null || theEdge == null)
			throw new IllegalArgumentException(Messages.getString("addEdgeParamCannotBeNull"));

		// es con peso? debe tener implementado el metodo double getWeight()
		if (isWeighted) {
			// reflection
			Class<?> c = theEdge.getClass();
			try {
				c.getDeclaredMethod("getWeight");
			} catch (NoSuchMethodException | SecurityException e) {
				throw new RuntimeException(
						type + " is weighted but the method double getWeighed() is not declared in theEdge");
			}
		}
		
		if (!acceptSelfLoop && aVertex.equals(otherVertex)) {
			throw new RuntimeException(String.format("%s does not accept self loops between the same vertex", type));
		}

		// if any of the vertex is not presented, the node is created automatically
		addVertex(aVertex);
		addVertex(otherVertex);
	}


	
	@Override
	public boolean removeVertex(V aVertex) {
		// COMPLETAR
		if(aVertex == null)
			throw new RuntimeException("Vertex is null");

		if(adjacencyList.get(aVertex) == null)
			return false;

		if(isDirected){
			adjacencyList.remove(aVertex);
			for(Map.Entry<V, Collection<InternalEdge>> entry : adjacencyList.entrySet()) {
				entry.getValue().removeIf(edge -> edge.target.equals(aVertex));
			}
		}
		else {
			for(InternalEdge edge : adjacencyList.get(aVertex)) {
				if(!edge.target.equals(aVertex)) {
					adjacencyList.get(edge.target).removeIf(otherEdge -> otherEdge.target.equals(aVertex));
				}
			}
			adjacencyList.remove(aVertex);
		}
		return true;
	}

	@Override
	public boolean removeEdge(V aVertex, V otherVertex) {
		// COMPLETAR
		throw new RuntimeException("not implemented yet");
	}

	
	@Override
	public boolean removeEdge(V aVertex, V otherVertex, E theEdge) {
		// COMPLETAR
		throw new RuntimeException("not implemented yet");
	}
	
	
	@Override
	public void dump() {
		// COMPLETAR
		System.out.println(type);
		System.out.println("Vertices:");
		System.out.println(getVertices());
		for(V v : getVertices()){
			System.out.print(v + ": ");
			for(InternalEdge e : getAdjacencyList().get(v)){
				E edge = e.edge;
				V target = e.target;
				System.out.print("-[" + edge + "]-");
				if(isDirected) System.out.print("> ");
				System.out.print(target + " ");
				//String.format("-%s-(%s)", edge, target);
			}
			System.out.println();
		}
	}


	@Override
	public int degree(V aVertex) {
		// COMPLETAR
		//Si es dirigido, no podemos hablar de degree
		if(isDirected)
			throw new RuntimeException("directed graph cannot call this method");

		if(aVertex == null)
			throw new RuntimeException("vertex cannot be null");

		if(adjacencyList.get(aVertex) == null)
			throw new RuntimeException("vertex not found");

		return adjacencyList.get(aVertex).size();
	}



	@Override
	public int inDegree(V aVertex) {
		// COMPLETAR
		//Solo para dirigidos
		if(!isDirected)
			throw new RuntimeException("graph that isn't directed, cannot call this method");

		if(aVertex == null)
			throw new RuntimeException("vertex cannot be null");

		int degree = 0;

		for(Map.Entry<V, Collection<InternalEdge>> entry : adjacencyList.entrySet()) {
			for(InternalEdge edge : entry.getValue()) {
				if(edge.target.equals(aVertex))
					degree++;
			}
		}

		return degree;
	}

	@Override
	public int outDegree(V aVertex) {
		// COMPLETAR
		//Solamente para dirigidos
		if(!isDirected)
			throw new RuntimeException("graph that isn't directed cannot call this method");

		if(aVertex == null)
			throw new RuntimeException("vertex cannot be null");

		if(adjacencyList.get(aVertex) == null)
			throw new RuntimeException("vertex not found");

		return adjacencyList.get(aVertex).size();
	}

	
	class InternalEdge {
		E edge;
		V target;

		InternalEdge(E propEdge, V target) {
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
	public void printBFS(V vertex) {
		if (!getAdjacencyList().containsKey(vertex)) {
			return;
		}
		Queue<V> queue = new LinkedList<>();
		Set<V> visited = new HashSet<>();
		queue.add(vertex);
		visited.add(vertex);
		while(!queue.isEmpty()){
			V v = queue.remove();
			System.out.print(v + " ");
			for(InternalEdge e : getAdjacencyList().get(v)){
				if(!visited.contains(e.target)){
					queue.add(e.target);
					visited.add(e.target);
				}
			}
		}
		System.out.println();
	}

	@Override
	public void printDFS(V vertex) {
		if (!getAdjacencyList().containsKey(vertex)) {
			return;
		}
		Stack<V> stack = new Stack<>();
		Set<V> visited = new HashSet<>();
		stack.push(vertex);
		visited.add(vertex);
		while(!stack.isEmpty()){
			V v = stack.pop();
			System.out.print(v + " ");
			for(InternalEdge e : getAdjacencyList().get(v)){
				if(!visited.contains(e.target)){
					stack.push(e.target);
					visited.add(e.target);
				}
			}
		}
		System.out.println();
	}

	private Iterable<V> getTree(boolean BFS, V startNode){
		if (startNode == null || !getAdjacencyList().containsKey(startNode))
			throw new IllegalArgumentException(Messages.getString("vertexParamError"));
		return new Iterable<V>() {
			@Override
			public Iterator<V> iterator() {
				return BFS ? new BFSIterator(startNode) : new DFSIterator(startNode);
			}
		};
	}
	@Override
	public Iterable<V> getBFS(V startNode){
		return getTree(true, startNode);
	}

	@Override
	public Iterable<V> getDFS(V startNode){
		return getTree(false, startNode);
	}

	private class BFSIterator implements Iterator<V>{

		private Queue<V> queue = new LinkedList<>();
		private Set<V> visited = new HashSet<>();

		public BFSIterator(V start){
			queue.add(start);
		}

		@Override
		public boolean hasNext() {
			while(!queue.isEmpty()){
				V current = queue.peek();
				if(!visited.contains(current))
					return true;
				queue.poll();
			}
			return false;
		}

		@Override
		public V next() {
			V current = queue.poll();
			visited.add(current);
			for(InternalEdge edge: getAdjacencyList().get(current)){
				if(!visited.contains(edge.target)){
					queue.add(edge.target);
				}
			}
			return current;
		}
	}

	private class DFSIterator implements Iterator<V>{

		private Stack<V> queue = new Stack<>();
		private Set<V> visited = new HashSet<>();

		public DFSIterator(V start){
			queue.add(start);
		}

		@Override
		public boolean hasNext() {
			while(!queue.isEmpty()){
				V current = queue.peek();
				if(!visited.contains(current))
					return true;
				queue.pop();
			}
			return false;
		}

		@Override
		public V next() {
			V current = queue.pop();
			visited.add(current);
			for(InternalEdge edge: getAdjacencyList().get(current)){
				if(!visited.contains(edge.target)){
					queue.push(edge.target);
				}
			}
			return current;
		}
	}

	@Override
	public boolean isBipartite(){

		//check if graph is empty
		if(adjacencyList.isEmpty())
			return true;

		//initialize colors for all vertices
		Map<V, Color> colors = new HashMap<>();

		//color all the vertices with white color

		for (V v : adjacencyList.keySet()) {
			colors.put(v, Color.WHITE);
		}

		//start coloring vertices , this code will handle the disconnected graph as well
		//color the first vertex with RED
		for(V vertex : adjacencyList.keySet()) {
			if(colors.get(vertex)==Color.WHITE) {
				colors.put(vertex, Color.RED);

				boolean result = isBipartiteUtil(vertex, colors);
				if(!result)
					return false;
			}
		}
		return true;
	}

	private boolean isBipartiteUtil(V vertex, Map<V, Color> colors){

		//travel all adjacent vertices
		for(InternalEdge edge : adjacencyList.get(vertex)) {
			if(colors.get(edge.target)==Color.WHITE) {
				if(colors.get(vertex)==Color.RED) {
					colors.put(edge.target,Color.GREEN);
				}
				else if(colors.get(vertex)==Color.GREEN) {
					colors.put(edge.target, Color.RED);
				}
				isBipartiteUtil(edge.target, colors);
			}
			else if(colors.get(vertex)==colors.get(edge.target)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean hasCycle() {
		Set<V> visitedNodes = new HashSet<>();
		Set<InternalEdge> visitedEdges = new HashSet<>();

		for(V vertex : getAdjacencyList().keySet()) {
			if(hasCycleRec(visitedNodes,visitedEdges, null, vertex))
				return true;
		}
		return false;
	}

	private boolean hasCycleRec(Set<V> visitedNodes, Set<InternalEdge> visitedEdges, V lastNode, V vertex) {
		visitedNodes.add(vertex);
		boolean addedSameEdge = false;
		for(InternalEdge edge : getAdjacencyList().get(vertex)) {
			if(!addedSameEdge && !isDirected && edge.target.equals(lastNode)) { //No cuento la misma arista
				visitedEdges.add(edge);
				addedSameEdge = true;
			}
			else if(visitedNodes.contains(edge.target) && !visitedEdges.contains(edge)) {
				return true;
			}
			else {
				visitedEdges.add(edge);
				if(hasCycleRec(visitedNodes, visitedEdges, vertex, edge.target)){
					return true;
				}
			}
		}
		return false;
	}
}
