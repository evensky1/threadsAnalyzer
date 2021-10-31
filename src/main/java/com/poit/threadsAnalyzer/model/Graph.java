package com.poit.threadsAnalyzer.model;
import java.util.ArrayList;
import java.util.Stack;

public class Graph {
    private ArrayList<ArrayList<Integer>>  adjMat;
    private ArrayList<Node> nodes;
    private Stack<Integer> stack;

    public Graph(){
        adjMat = new ArrayList<ArrayList<Integer>>(0);
        nodes = new ArrayList<>(0);
        stack = new Stack<>();
    }
    public Node getNode(int n){
        return nodes.get(n);
    }
    public void addNode(String operator){
        nodes.add(new Node(operator));
        adjMat.add(new ArrayList<Integer>(nodes.size() - 1));
        for(ArrayList<Integer> column:adjMat){
            while(column.size() < nodes.size()) {
                column.add(0);
            }
        }
    }

    public void addEdge(int start, int end, int length, int val){
        if(start < this.adjMat.size() && end < this.adjMat.size()) {
            adjMat.get(start).remove(end);
            adjMat.get(start).add(end, length);
            adjMat.get(end).remove(start);
            adjMat.get(end).add(start, val);
        } else {
            System.out.println("Нумерация то с нуля");
        }
    }

    private int check(int n){
        for(int i = 0; i < adjMat.size(); i++){
            if(!adjMat.get(n).get(i).equals(0) && !nodes.get(i).isVisited()){
                return i;
            }
        }
        return -1;
    }

    public void dfs(int index){
        nodes.get(index).setVisited(true);
        stack.push(index);
        while(!stack.isEmpty()){
            int neighbour = check(stack.peek());

            if(neighbour == -1){
                stack.pop();
            } else {
                System.out.println(nodes.get(neighbour).getOperator());
                nodes.get(neighbour).setVisited(true);
                stack.push(neighbour);
            }
        }

        for(Node node: nodes){
            node.setVisited(false);
        }
    }

    public int findMax(){
        int maxDepth = 0;
        for(Node node: this.nodes){
            if(node.getDepth() > maxDepth){
                maxDepth = node.getDepth();
            }
        }
        return maxDepth;
    }
}
