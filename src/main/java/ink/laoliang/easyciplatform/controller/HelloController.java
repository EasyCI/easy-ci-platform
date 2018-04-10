package ink.laoliang.easyciplatform.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping(value = "/")
    public String hello() {
        return "<center>" +
                "<h1>Hello!</h1>" +
                "<h3>This is a back end service for <a href=\"https://github.com/EasyCI\">EasyCI</a> project!</h3>" +
                "</center>";
    }
}
