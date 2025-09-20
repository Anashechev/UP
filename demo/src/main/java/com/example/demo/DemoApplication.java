package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@SpringBootApplication
@Controller
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("/")
	public String home() {
		return "home";
	}

	@GetMapping("/calculator")
	public String calculator() {
		return "calculator";
	}

	@PostMapping("/calculate")
	public String calculate(
			@RequestParam("a") double a,
			@RequestParam("b") double b,
			@RequestParam("op") String op,
			RedirectAttributes redirectAttributes
	) {
		double result;
		switch (op) {
			case "+":
				result = a + b;
				break;
			case "-":
				result = a - b;
				break;
			case "*":
				result = a * b;
				break;
			case "/":
				if (b == 0) {
					redirectAttributes.addFlashAttribute("error", "Деление на ноль невозможно");
					return "redirect:/calculator";
				}
				result = a / b;
				break;
			default:
				redirectAttributes.addFlashAttribute("error", "Неизвестная операция");
				return "redirect:/calculator";
		}
		redirectAttributes.addFlashAttribute("calcResult", result);
		return "redirect:/calculator/result";
	}

	@GetMapping("/calculator/result")
	public String calculatorResult() {
		return "calc-result";
	}

	@GetMapping("/converter")
	public String converter(Model model) {
		model.addAttribute("currencies", new String[]{"USD", "EUR", "RUB"});
		return "converter";
	}

	@PostMapping("/convert")
	public String convert(
			@RequestParam("from") String from,
			@RequestParam("to") String to,
			@RequestParam("amount") double amount,
			RedirectAttributes redirectAttributes
	) {
		double rate = getRate(from, to);
		double converted = amount * rate;
		redirectAttributes.addFlashAttribute("from", from);
		redirectAttributes.addFlashAttribute("to", to);
		redirectAttributes.addFlashAttribute("amount", amount);
		redirectAttributes.addFlashAttribute("converted", converted);
		return "redirect:/converter/result";
	}

	@GetMapping("/converter/result")
	public String convertResult() {
		return "convert-result";
	}

	private double getRate(String from, String to) {
		if (from.equals(to)) return 1.0;
		double usdToRub = 90.0;
		double usdToEur = 0.9;
		double usdToUsd = 1.0;

		double fromToUsd;
		switch (from) {
			case "USD": fromToUsd = 1.0; break;
			case "EUR": fromToUsd = 1.0 / usdToEur; break;
			case "RUB": fromToUsd = 1.0 / usdToRub; break;
			default: fromToUsd = 1.0;
		}

		double usdToTarget;
		switch (to) {
			case "USD": usdToTarget = usdToUsd; break;
			case "EUR": usdToTarget = usdToEur; break;
			case "RUB": usdToTarget = usdToRub; break;
			default: usdToTarget = 1.0;
		}

		return fromToUsd * usdToTarget;
	}
}
