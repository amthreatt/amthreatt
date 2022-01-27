import java.util.List;
public class Edge {
    double weight;
    ProductVertex source;
    ProductVertex destination;

    public Edge(double weight, ProductVertex source, ProductVertex destination){
        this.weight = weight;
        this.source = source;
        this.destination = destination;
    }
    public ProductVertex getVert(double w){
        return destination;
    }

    public String toString(){
        return " weight: " + weight + "|("+ source.toString() + " - " + destination.toString() + ")";
    }
}
