
package com.gestaobeleza.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TesteController {

    @GetMapping("/teste")
    public String testarApi() {
        return "Nossa API está funcionando!";
    }
}
