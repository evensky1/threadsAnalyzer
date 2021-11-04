package com.poit.threadsAnalyzer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.poit.threadsAnalyzer.model.Code;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PageController {
    @GetMapping
    public String pageOut(Model model) {
        model.addAttribute("inputCode", new Code());
        return "mainPage";
    }

    @PostMapping
    public String pageOut(@ModelAttribute("inputCode") Code code, Model model) {
        model.addAttribute("cl", code.cl());
        model.addAttribute("CL", code.CL());
        model.addAttribute("cli", code.CLI());
        return "mainPage";
    }
}
