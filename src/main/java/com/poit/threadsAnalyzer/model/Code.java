package com.poit.threadsAnalyzer.model;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Code {
    private String code;
    private Graph graph;

    public Code() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private void createGraph() {
        graph = new Graph();
        String tempCode = deleteCommentsAndStringsFromCode(this.code);
        Pattern pattern = Pattern.compile("begin|((?<!end )while)|((?<!end )until)|if|for|end|when|break");
        Matcher matcher = pattern.matcher(tempCode);
        graph.addNode("Code");
        int currentElem = 0;
        int previousElem = 0;
        int tempDepth = 0;
        Stack<Integer> trace = new Stack<>();
        while (matcher.find()) {
            if (!(matcher.group().equals("end") || matcher.group().equals("break"))) {
                graph.addNode(matcher.group());
                currentElem++;
                tempDepth++;
                graph.getNode(currentElem).setDepth(tempDepth);
                graph.addEdge(previousElem, currentElem, 1, 0);
                trace.push(previousElem);
                previousElem = currentElem;
            } else {
                if(!trace.isEmpty()) {
                    previousElem = trace.pop();
                    tempDepth = graph.getNode(previousElem).getDepth();
                }
            }
        }
    }

    public String CLI() {
        createGraph();
        return "Максимальный уровень вложенности равен " + (this.graph.findMax() - 1);
    }

    public String deleteCommentsAndStringsFromCode(String code) {
        String codeTemp = code.replaceAll("(\".*?[^\\\\](\\\\\\\\)*\")|('.*?[^\\\\](\\\\\\\\)*')", " ");
        codeTemp = codeTemp.replaceAll("(=begin\\s(.*\\r?\\n)*?=end\\s)|(#.*)", " ");
        return codeTemp;
    }
}
