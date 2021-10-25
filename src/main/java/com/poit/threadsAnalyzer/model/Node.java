package com.poit.threadsAnalyzer.model;

public class Node {
    private String operator;
    private boolean visited;

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
}
