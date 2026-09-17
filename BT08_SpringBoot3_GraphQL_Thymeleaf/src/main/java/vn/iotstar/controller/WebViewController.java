package vn.iotstar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.iotstar.repository.CategoryRepository;

@Controller
public class WebViewController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "home";
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin";
    }
}
