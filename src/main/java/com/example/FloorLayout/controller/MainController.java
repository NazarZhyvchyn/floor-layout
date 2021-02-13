package com.example.FloorLayout.controller;

import com.example.FloorLayout.domain.Point;
import com.example.FloorLayout.domain.Layout;
import com.example.FloorLayout.service.LayoutService;
import com.example.FloorLayout.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Controller
public class MainController {

    @Autowired
    private LayoutService layoutService;

    @Autowired
    private ValidatorService validatorService;

    @GetMapping("/")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "guest") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting" ;
    }

    @GetMapping("/all")
    public String main(Model model) {
        List<Layout> layouts = layoutService.findAll();
        model.addAttribute("layouts", layouts);

        return "main" ;
    }

    @PostMapping("/add")
    public String addLayout(@RequestParam String name,
                            @RequestParam String points,
                            Model model) {

        if (validatorService.validate(points, model)) return "errors/error" ;

        layoutService.saveLayout(name, points.replaceAll(",", ""));

        return "redirect:/all" ;
    }

    @GetMapping("/add")
    public String addLayout() {
        return "add" ;
    }

    @GetMapping("/show")
    public String showLayout(@RequestParam Integer id, Model model) {
        Layout layout = layoutService.findLayoutById(id);
        List<Point> points = layoutService.getXY(layout.getPoints());

        model.addAttribute("layout", layout);
        model.addAttribute("points", points);
        model.addAttribute("x", points.get(0).getX());
        model.addAttribute("y", points.get(0).getY());

        return "layout" ;
    }

    @Transactional
    @GetMapping("/delete")
    public String deleteLayout(@RequestParam Integer id) {
        layoutService.deleteLayout(id);
        return "redirect:/all" ;
    }

    @GetMapping("/update")
    public String updateLayout(@RequestParam Integer id, Model model) {
        Layout layout = layoutService.findLayoutById(id);
        model.addAttribute("layout", layout);

        return "update" ;
    }

    @Transactional
    @PostMapping("/update")
    public String updateLayout(@RequestParam Integer id,
                               @RequestParam String name,
                               @RequestParam String points,
                               Model model) {

        if (validatorService.validate(points, model)) return "errors/error" ;

        layoutService.deleteLayout(id);
        layoutService.saveLayout(name, points.replaceAll(",", " "));

        return "redirect:/all" ;
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter,
                         Map<String, Object> model) {
        Iterable<Layout> layouts = layoutService.filter(filter);
        model.put("layouts", layouts);

        return "main" ;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/validateRoom")
    @ResponseBody
    public String validateRoom(@RequestBody String body, Model model) {

        String patternString = "\\d+" ;
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(body);

        String result = "" ;
        while (matcher.find()) {
            result += matcher.group();
        }
        result = result.replaceAll("^(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)$", "$1 $2, $3 $4, $5 $6, $7 $8");

        System.out.println(result);

        if (!validatorService.validate(result, model)) {
            return body;
        } else {
            return "Error: " + model.getAttribute("error");
        }
    }


}
