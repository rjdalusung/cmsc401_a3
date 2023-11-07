/*
RJ Dalusung
CMSC401
Assignment 3
*/

import java.util.Scanner;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.ArrayList;


public class CMSC401_A3{
    /**********  main function ****************/
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        
        //getting the number of cities and highways
        int numCities = input.nextInt(); //number of vertices
        List<List<Edge>> adj = new ArrayList<>(numCities);
        for(int i = 0; i < numCities; i++){
            adj.add(new ArrayList<>());
        }

        int[] w = new int[numCities]; 
        int numHighways = input.nextInt(); //number of edges
        for(int i = 0; i < numCities - 2; i++){
            int ind = input.nextInt() - 1;
            w[ind] = input.nextInt();
        }
        for(int i = 0; i < numHighways; i++){
            int city1 = input.nextInt() - 1;
            int city2 = input.nextInt() - 1;
            int weight = input.nextInt();
            addEdge(adj, city1, city2, weight);
        }
        int src = 0;
        dijkstra(adj, src,w);
    
    }
    /******************************************/
    static class Edge{
        int v, w;
        
        Edge(int v, int w){
            this.v = v;
            this.w = w;
        }
    }
    static class Node{
        int v, d;
        
        Node(int v, int d){
            this.v = v;
            this.d = d;
        }
    }
    static void addEdge(List<List<Edge>> adj, int u, int v, int w){
        adj.get(u).add(new Edge(v, w)); //add edge to list
        adj.get(v).add(new Edge(u, w)); //add edge to list
    }
    static void dijkstra(List<List<Edge>> adj, int src, int[] w){
        int V = adj.size();
        int[] dist = new int[V]; //shortest distance array
        int[] dist2 = new int[V]; //second shortest distance array
        //initialize distances to infinity
        for(int i = 0; i < V; i++){
            dist[i] = Integer.MAX_VALUE;
            dist2[i] = Integer.MAX_VALUE;
        }
        dist[src] = 0; //src distance is 0
        PriorityQueue<Node> pq = new PriorityQueue<>(V,new nodecomp());
        pq.add(new Node(src,0));
        while(!pq.isEmpty()){
            Node node = pq.poll(); //get the head of the queue and remove it
            int u = node.v; //get the vertex
            int ud = node.d; //get the distance
            int uw = w[u]; //get the vertex weight
            for(Edge edge : adj.get(u)){ //iterate through each edge
                int v = edge.v; //vertex to go
                int vw = edge.w; //edge weight
                int newDist = ud + vw + uw;
                if(newDist < dist[v]){
                    dist2[v] = dist[v]; //updates the second shortest 
                    dist[v] = newDist; //updates the shortest path
                    pq.add(new Node(v, newDist)); 
                }else if (newDist > dist[v] && newDist < dist2[v]){
                    dist2[v] = newDist;
                    pq.add(new Node(v, newDist));
                }
            }
        }
        for(int i = 0; i < V; i++){
            System.out.println("Shortest path from src to vertex " + i + " is: " + dist[i]);
        }
        if(dist2[1] == Integer.MAX_VALUE){
            System.out.println(dist[1]);
        } else {
            System.out.println(dist2[1]);
        }
    }
    static class nodecomp implements Comparator<Node>{
        public int compare(Node n1, Node n2){
            return n1.d - n2.d;
        }
    }

    
}