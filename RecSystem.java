import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class RecSystem {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("/Users/ameliathreatt/IdeaProjects/OptionalHW/src/products.csv"));
        Graph g = new Graph();
        g.buildGraph(scan);

        Scanner myObj = new Scanner(System.in);

        while(myObj.hasNext()){
        System.out.println("What would you like to buy? ");
        String product = myObj.nextLine();

        if(g.getVertex(product) == null){
            System.out.println("Item not found, sorry :(");

        }
        List<String> rec = g.recommendation(g.getVertex(product));
        if(rec.size() == 3){
            for(int i =0; i < rec.size(); i++){
                System.out.println(rec.get(i));
            }
        }
        else{
            if(g.getCategory(g.getVertex(product).catagory).size() != 0) {
                System.out.println("Some other popular products from " + g.getVertex(product).catagory + ": ");
                rec = g.getCategory(g.getVertex(product).catagory);
                for (int i = 0; i < rec.size(); i++) {
                    System.out.println(rec.get(i));
                }
            }

            else{
                System.out.println("Thanks for shopping!");

            }
        }
    }
        }
}
