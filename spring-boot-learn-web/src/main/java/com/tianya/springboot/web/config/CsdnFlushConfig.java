package com.tianya.springboot.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableScheduling
public class CsdnFlushConfig {
	
	@Autowired
	private RestTemplate restTemplate ;
	
	
	@Scheduled(cron = "1 * * * * ?")
	public void flushCsdn() {
		

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36");
		headers.set("sec-fetch-mode", "navigate");
		headers.set("sec-fetch-site", "same-origin");
		headers.set("sec-fetch-user", "?1");
		headers.set("upgrade-insecure-requests", "1");
		headers.set("referer", "https://blog.csdn.net/mybook201314?spm=1010.2135.3001.5343");
		headers.set("authority", "blog.csdn.net");
		headers.set("method", "GET");
		headers.set("path", "/mybook201314/article/details/116398838");
		headers.set("scheme", "https");
		headers.set("accept-encoding", "gzip, deflate, br");
		headers.set("cache-control", "max-age=0");
		
		
		HttpEntity<String> entity = new HttpEntity<>(null, headers);


		String url = "https://blog.csdn.net/mybook201314/article/details/116398838";
		restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		
		System.out.println("刷新CSDN阅读量 成功 ");
	}

}
