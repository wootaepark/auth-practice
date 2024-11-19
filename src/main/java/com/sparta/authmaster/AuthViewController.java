package com.sparta.authmaster;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class AuthViewController {


	@GetMapping("/")
	public String home(){
		return "home";
	}



	@GetMapping("/loginForm")
	public String index(Model model
	){
		return "loginForm";
	}
}
