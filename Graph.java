import java.io.File;
import java.io.FileNotFoundException;
import  java.util.*;

public class Graph {
    List<ProductVertex> vertices;

    public Graph(){
        vertices = new ArrayList<>();

    }

    /**
     * hasVertex takes a Vertex v as parameter
     * returns true if the graph contains any existing vertices that has the same value as the given vertex
     *
     * @param v
     * @return
     */
    public boolean hasVertex(ProductVertex v) {
        return vertices.contains(v);
    }

    /**
     * getVertices returns the list of Vertices of the graph.
     * Since List is a Collection, you need a concrete class when creating a list object.
     * You may use any List type data structures (e.g., ArrayList, LinkedList, etc.)
     *
     * @return
     */
    public List<ProductVertex> getVertices() {
        return vertices;
    }

    /**
     * addVertex takes a Vertex v and add it to the graph.
     * If the graph already contains a vertex with the same value, throw an IllegalArgumentException.
     *
     * @param v
     */
    public void addVertex(ProductVertex v) {
        vertices.add(v);
    }

    /**
     * getVertex takes a value and returns the vertex that contains the value.
     * If the graph doesn't contain such vertex, return null.
     *
     * @param ID
     * @return
     */
    public ProductVertex getVertex(int ID) {
        for(int i = 0; i < vertices.size(); i++){
            if(vertices.get(i).id == ID){
                return vertices.get(i);
            }
        }
        return null;
    }
    public ProductVertex getVertex(String name) {
        for(int i = 0; i < vertices.size(); i++){
            if(vertices.get(i).name.equals(name)){
                return vertices.get(i);
            }
        }
        return null;
    }

    public List<String> getCategory(String category){
        Hashtable<Integer, String> hashtable = new Hashtable<>();
        List<Integer> quant = new ArrayList<>();
        List<String> output = new ArrayList<>();
        for(int i =0; i < vertices.size(); i++){
            if(vertices.get(i).catagory.equals(category)){
                hashtable.put(vertices.get(i).quantity, vertices.get(i).name);
                quant.add(vertices.get(i).quantity);
            }
        }
        Collections.sort(quant);
        Collections.reverse(quant);

        for(int j =0; j < 3; j++){
            output.add(hashtable.get(quant.get(j)));
        }

        return output;
    }

    public List<String> topThree(){
        Hashtable<Integer, String> hashtable = new Hashtable<>();
        List<Integer> quant = new ArrayList<>();
        List<String> output = new ArrayList<>();
        for(int i =0; i < vertices.size(); i++){
                hashtable.put(vertices.get(i).quantity, vertices.get(i).name);
                quant.add(vertices.get(i).quantity);
        }
        Collections.sort(quant);
        Collections.reverse(quant);

        for(int j =0; j < 3; j++){
            output.add(hashtable.get(quant.get(j)));
        }

        return output;
    }

    public void buildGraph(Scanner scan){
        scan.useDelimiter(",");

        //converting data into a list of lists for easier access to the information
        List<List<String>> list = new ArrayList<>();
        while(scan.hasNextLine()){
            list.add(Arrays.asList((scan.nextLine().split(","))));
        }
        scan.close();

        //creating graph

        ProductVertex productVertex = new ProductVertex(0);

        for(int i =0; i < list.size(); i++){
            List<String> p1 = list.get(i);
            productVertex = getVertex(i+1);

            //converting list to product
            if(productVertex == null) {
                productVertex = new ProductVertex(i+1);
            }
            productVertex.name = p1.get(1);
            productVertex.catagory = p1.get(2);
            productVertex.quantity = Integer.parseInt(p1.get(3));
            //adding vertex to graph
            addVertex(productVertex);
        }

        //all vertices have been created now to update the neighbor list
        for(int k = 0; k< vertices.size(); k++){
            List<String> productInformation = list.get(k);
            for(int j = 4; j < productInformation.size(); j+=2){
                getVertex(k+1).neighbors.add(new Edge(Double.parseDouble(productInformation.get(j+1)), getVertex(k+1), (getVertex(Integer.parseInt(productInformation.get(j))))));

            }
        }
    }

    //converting list of neigbors to hashtable
    public Hashtable<Double, ProductVertex> toTable(List<Edge> edges){
        Hashtable<Double, ProductVertex> hashtable = new Hashtable<>();
        for(int i =0; i< edges.size(); i++){
            hashtable.put(edges.get(i).weight, edges.get(i).destination);
        }

        return hashtable;
    }

    public List<String> recommendation(ProductVertex productVertex){
        List<String> recNames = new ArrayList<>();
        List<Double> weights = new ArrayList<>();
        Hashtable<Double, ProductVertex> neighborTable = toTable(productVertex.neighbors);

        //adding all current neighbors
            for (int l = 0; l < productVertex.neighbors.size(); l++) {
                recNames.add(productVertex.neighbors.get(l).destination.name);
                weights.add(productVertex.neighbors.get(l).weight);
            }

            //list of all the weights ordered from greatest to least
            Collections.sort(weights);
            Collections.reverse(weights);

            if(recNames.size() == 3){
                return recNames;
            }

            //adding the Names of the items with the highest weight for products with more than three neighbors
            if(recNames.size()>3){
               recNames.clear();
              for(int i = 0; i < 3; i++){
                 recNames.add(neighborTable.get(weights.get(i)).name);

                }
            }

            if(recNames.size() < 3 && recNames.size() > 0) {
                //find highest recommend item, and visit its neighbors
                while (recNames.size() < 3) {
                    ProductVertex highestRecommended;
                    for(int index =0; index < neighborTable.size(); index++) {
                        highestRecommended = neighborTable.get(weights.get(index));
                        Hashtable<Double, ProductVertex> pt = toTable(highestRecommended.neighbors);
                        List<String> ls = recommendation(highestRecommended);

                        for(int a =0; a < ls.size(); a ++){
                           if(recNames.size() < 3) {
                               recNames.add(ls.get(a));
                           }
                        }
                    }
                }
            }

            //if there is none
            if(recNames.size() == 0){
                recNames.add(" ");
            }

        return recNames;
    }

    /**
     * addEdge takes a starting value, a destination value, and a weight,
     * and adds such edge between vertices that contain the starting value and destination value.
     * This method constructs a directed edge, which means start -> dest.
     *
     * @param idStart
     * @param idDest
     * @param cost
     */
    public void addEdge(int idStart, int idDest, Double cost) {
        ProductVertex s =  getVertex(idStart);
        ProductVertex d = getVertex(idDest);
        if(s == null){
            throw new IllegalArgumentException(idStart + " does not exist. Cannot create edge.");
        }
        if(s == null){
            throw new IllegalArgumentException(idDest + " does not exist. Cannot create edge.");
        }
        Edge ed = new Edge(cost, s, d);

        s.neighbors.add(ed);


    }


    public void printGraph() {
        for(int i =0; i < vertices.size(); i++){
            System.out.println(vertices.get(i).toString());
            for(int j =0; j < vertices.get(i).neighbors.size(); j++){
                System.out.println(vertices.get(i).neighbors.get(j).toString());
            }
            System.out.println("");
        }
    }
}
