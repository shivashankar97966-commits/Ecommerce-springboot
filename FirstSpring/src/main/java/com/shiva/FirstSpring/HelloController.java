package com.shiva.FirstSpring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hellow")
    public String HellowWorld() {
        return "Hellow world";
    }

    @PostMapping("/helloPost")
    public String HellowPost(@RequestBody String name) {
        return "Hello" + name + ", user created with given name";
    }
}
