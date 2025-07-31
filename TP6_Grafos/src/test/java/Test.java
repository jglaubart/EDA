import core.EmptyEdgeProp;
import core.GraphBuilder;
import core.GraphService;

public class Test {
    public static void main(String[] args) {
        GraphService<Integer, EmptyEdgeProp> g = new GraphBuilder<Integer,EmptyEdgeProp>().
                withMultiplicity(GraphService.Multiplicity.SIMPLE).withDirected(GraphService.EdgeMode.DIRECTED).
                withAcceptSelfLoop(GraphService.SelfLoop.YES).
                withAcceptWeight(GraphService.Weight.NO).
                withStorage(GraphService.Storage.SPARSE).build();
        g.addVertex(8);
        g.addVertex(2);
        g.addEdge(2, 9, new EmptyEdgeProp());
        g.addEdge(2, 8, new EmptyEdgeProp());
        g.addEdge(8, 3, new EmptyEdgeProp());
        g.addEdge(9, 4, new EmptyEdgeProp());
        g.addEdge(4, 5, new EmptyEdgeProp());
        g.addEdge(3, 5, new EmptyEdgeProp());
        g.dump();
        System.out.println();

        g.printBFS(2);
        g.printDFS(2);
    }
}
