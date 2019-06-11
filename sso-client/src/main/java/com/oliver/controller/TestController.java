package com.oliver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * com.oliver.controller TestController
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/6/5 18:27
 */
@Controller
public class TestController {
    @GetMapping("test")
    public String test() {
        return "test";
    }
}
