package kr.co.farmstory.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MainController {

    private final BuildProperties buildProperties;


    @GetMapping(value = {"/", "/index"})
    public String index(Model model){



        // 상단 BuildProperties 주입
        String appVersion = buildProperties.getVersion();
        log.info(appVersion);

        model.addAttribute("appVersion",appVersion);
        return "/index";
    }


    @GetMapping("/introduction/hello")
    public String hello(){

        return "/introduction/hello";

    }

    @GetMapping("/introduction/direction")
    public String direction(){

        return "/introduction/direction";
    }

    @GetMapping("/notfound")
    public String notfound(){
        return "/notfound";
    }
}
