import java.util.ArrayList;
import java.util.List;

public class ProductVertex {
    int id;
    int quantity;
    String catagory;
    String name;
    List<Edge> neighbors;

    /**
     * Name is the name of the Product
     *  for example "String Cheese"
     *
     * ID, is the product ID used to locate the product
     *
     * neighbors is a list of edges containing the recommended products
     *  and their percentage of likelihood of purchasing
     */

    /**
     * constructor
     * take in ID number for parameter
     * initialize the rest of the variables
     */

    public ProductVertex(int id){
        this.id = id;
        name = "";
        neighbors = new ArrayList<>();
        quantity= 0;
        catagory = "";
    }

    public void addEdge(Edge e) {
        if(!neighbors.contains(e)){
            neighbors.add(e);
        }
    }


    public String toString(){
        //return String.valueOf(id);
        return String.valueOf(id);
    }
}

