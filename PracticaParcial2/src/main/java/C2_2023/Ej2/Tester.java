package C2_2023.Ej2;

public class Tester {
    public static void main(String[] args) {
        GraphBuilder<Integer, String> graphBuilder = new GraphBuilder<>();
        GraphService<Integer, String> g1 = graphBuilder.build();

        g1.addEdge(1, 2,"e1");
        g1.addEdge(2, 3,"e2");
        g1.addEdge(3, 1,"e3");
        g1.addEdge(4, 3,"e4");
        System.out.println(g1.connectedComponentsQty());

        GraphService<Integer, String> g2 = graphBuilder.build();

        g2.addEdge(1, 2,"e1");
        g2.addEdge(3,4,"e2");
        System.out.println(g2.connectedComponentsQty());

        GraphService<Integer, String> g3 = graphBuilder.build();

        g3.addEdge(1, 2,"e1");
        g3.addEdge(3,4,"e2");
        g3.addVertex(5);
        g3.addVertex(6);
        System.out.println(g3.connectedComponentsQty());

    }
}
