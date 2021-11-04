package com.poit.threadsAnalyzer.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Code {
    private String code;
    private Graph graph;
    private int cl, CL;

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
        Pattern pattern = Pattern.compile("\\bbegin\\b|((?<!end\\s)\\bwhile\\b)|((?<!end\\s)\\buntil\\b)|\\bloop\\b|\\bif\\b|\\bfor\\b|\\bcase\\b|\\bend\\b");
        Matcher matcher = pattern.matcher(tempCode);
        graph.addNode("Code");
        int currentElem = 0;
        int previousElem = 0;
        int tempDepth = 0;
        Stack<Integer> trace = new Stack<>();
        while (matcher.find()) {
            if (!matcher.group().equals("end")) {
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

    private ArrayList<String> createArray(boolean onlyCondition) {
        ArrayList<String> regArr = new ArrayList<>();
        File regSrc = new File(onlyCondition ? "regExOperatorsOnlyCondition.txt" : "regExOperators.txt");
        try {
            Scanner read = new Scanner(regSrc);
            while (read.hasNext()) {
                regArr.add(read.nextLine().trim());
            }
            return regArr;
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            regArr.add("");
            return regArr;
        }
    }

    public int codeOperatorsCount(String codeTemp, boolean onlyCondition) {
        int operatorsCount = 0;

        codeTemp = deleteCommentsAndStringsFromCode(codeTemp);
        ArrayList<String> regulars = createArray(onlyCondition);

        Pattern pattern;
        Matcher matcher;
        for (String regExp : regulars) {
            if (!regExp.isEmpty()) {
                pattern = Pattern.compile(regExp);
                matcher = pattern.matcher(codeTemp);
                while (matcher.find()) {
                    // удаляем найденное, чтобы, например, "+=" не реагировало на "+" и "="
                    // для этой же цели в файле с регулярками всё в порядке от длинного к короткому
                    if (regExp.contains("(")) {
                        codeTemp = codeTemp.replaceFirst("\\(", " ");
                    } else {
                        codeTemp = codeTemp.replaceFirst(regExp, " ");
                    }
                    operatorsCount++;
                }
            }
        }
        return operatorsCount;
    }

    public String CLI() {
        createGraph();
        return "Максимальный уровень вложенности равен " + (this.graph.findMax() - 1);
    }

    public String cl() {
        cl = codeOperatorsCount(code, false);
        return "";
    }

    public String CL() {
        cl = codeOperatorsCount(code, false);
        int conditions = codeOperatorsCount(code, true);

        return Float.toString((float) conditions/cl);
    }

    public String deleteCommentsAndStringsFromCode(String code) {
        code = code.replaceAll("(\".*?[^\\\\](\\\\\\\\)*\")|('.*?[^\\\\](\\\\\\\\)*')", " ");
        code = code.replaceAll("(=begin\\s(.*\\r?\\n)*?=end\\s)|(#.*)", " ");
        return code;
    }
}
