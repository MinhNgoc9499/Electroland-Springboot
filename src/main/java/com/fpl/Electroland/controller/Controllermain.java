package com.fpl.Electroland.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class Controllermain {

	@GetMapping("/main")
	public String Index(Model model) {
		return "index";
	}

	@GetMapping("/tt")
	public String tt(Model model) {

		return "tt";
	}

	@GetMapping("/test")
	public String Test() {
		return "test";
	}

	@PostMapping("/tinh")
	public String Tinh(HttpServletRequest req, Model model) {
		int max = 0;
		for (String x : req.getParameterValues("n")) {
			if (max < Integer.parseInt(x))
				max = Integer.parseInt(x);
		}
		;
		model.addAttribute("max", max);
		return "test";
	}

	@GetMapping("/address")
	public String Searchadd() {
		return "Add";
	}

	@PostMapping("/search")
	public String Search(@RequestParam String address, Model model) throws MalformedURLException, IOException {
		String encodedString = URLEncoder.encode(address, "UTF-8");
		String urlString = "https://rsapi.goong.io/geocode?address=" + encodedString
				+ "&api_key=Ses8uK1qoUDPIUhOQUFkgShQwOHbc3uE2iPfIZ9f";
		try (@SuppressWarnings("deprecation")
		java.util.Scanner s = new java.util.Scanner(new java.net.URL(urlString).openStream())) {
			String jsonString = s.useDelimiter("\\A").next();
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode node = objectMapper.readTree(jsonString);
			model.addAttribute("value", node.findValue("lat").asText() + "," + node.findValue("lng"));
		}
		return "Add";
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/location")
	public String Location(@RequestParam String location, Model model) throws MalformedURLException, IOException {
		String encodedString = URLEncoder.encode(location, "UTF-8");
		String urlString = "https://rsapi.goong.io/Geocode?latlng=" + encodedString
				+ "&api_key=Ses8uK1qoUDPIUhOQUFkgShQwOHbc3uE2iPfIZ9f";
		try (@SuppressWarnings("deprecation")
		java.util.Scanner s = new java.util.Scanner(new java.net.URL(urlString).openStream())) {
			String jsonString = s.useDelimiter("\\A").next();
			ObjectMapper objectMapper = new ObjectMapper();

			try {
				Map<String, Object> map = objectMapper.readValue(jsonString, Map.class);
				List<Map<String, Object>> results = (List<Map<String, Object>>) map.get("results");
				if (!results.isEmpty()) {
					String add = results.get(0).get("formatted_address").toString();
					model.addAttribute("value", add);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
		}
		return "Add";
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/khoangcach")
	public String Khoangcach(@RequestParam String location, Model model) throws MalformedURLException, IOException {
		String encodedString = URLEncoder.encode(location, "UTF-8");
		String urlString = "https://rsapi.goong.io/DistanceMatrix?origins=21.013672808000024,105.79825090900005&destinations="
				+ encodedString + "&vehicle=car&api_key=Ses8uK1qoUDPIUhOQUFkgShQwOHbc3uE2iPfIZ9f";
		try (@SuppressWarnings("deprecation")
		java.util.Scanner s = new java.util.Scanner(new java.net.URL(urlString).openStream())) {
			String jsonString = s.useDelimiter("\\A").next();
			ObjectMapper objectMapper = new ObjectMapper();

			try {
				Map<String, Object> map = objectMapper.readValue(jsonString, Map.class);
				List<Map<String, Object>> results = (List<Map<String, Object>>) map.get("rows");
				if (!results.isEmpty()) {
					Map<String, Object> row = (Map<String, Object>) results.get(0);
					List<Map<String, Object>> elements = (List<Map<String, Object>>) row.get("elements");
					Map<String, Object> distance = (Map<String, Object>) elements.get(0).get("distance");
					model.addAttribute("value", distance.get("text"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
		}
		return "Add";
	}

	@GetMapping("/checktt")
	public String checktt() throws IOException {
		return "tt";
	}

	@GetMapping("/form")
	public String getMethodName() {
		return "form";
	}

}
