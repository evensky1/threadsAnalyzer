package com.poit.threadsAnalyzer.model;

public class Node {
    private String operator;
    private boolean visited;
    private int depth;
    public Node(String operator){
        this.operator = operator;
        this.visited = false;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
