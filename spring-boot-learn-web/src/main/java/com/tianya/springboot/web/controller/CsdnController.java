package com.tianya.springboot.web.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/csdn")
public class CsdnController {

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/flush")
	public String flush(HttpServletRequest request) {
		

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.USER_AGENT, request.getHeader("USER-AGENT"));

		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			headers.set(headerName, request.getHeader(headerName));
		}

		Cookie[] cookies = request.getCookies();
		List<String> cookieList = new ArrayList<>();
		for (Cookie cookie : cookies) {
			cookieList.add(cookie.getName() + "=" + cookie.getValue() + ";");
		}

		headers.put("Cookie", cookieList);

		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		String url = "https://blog.csdn.net/mybook201314/article/details/116303541";
		ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		return exchange.getBody();
	}

}
