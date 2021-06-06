package com.tianya.springboot.web.config;

import java.util.Arrays;
import java.util.List;

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
		headers.set("referer", "https://blog.csdn.net/mybook201314");
		headers.set("authority", "blog.csdn.net");
		headers.set("method", "GET");
		headers.set("scheme", "https");
		headers.set("accept-encoding", "gzip, deflate, br");
		headers.set("cache-control", "max-age=0");
		
		
		
		String csdnUrl = "https://blog.csdn.net" ;
		List<String> urlList = Arrays.asList(
				"/mybook201314/article/details/117621862",
				"/mybook201314/article/details/117589533",
				"/mybook201314/article/details/116542986",
				"/mybook201314/article/details/116952009",
				"/mybook201314/article/details/117002272",
				"/mybook201314/article/details/117338648",
				"/mybook201314/article/details/117406657",
				"/mybook201314/article/details/117451022",
				"/mybook201314/article/details/102641119",
				"/mybook201314/article/details/115336106",
				"/mybook201314/article/details/116398838",
				"/mybook201314/article/details/115363090",
				"/mybook201314/article/details/116277989",
				"/mybook201314/article/details/115711242",
				"/mybook201314/article/details/116422683" );
		for (String url : urlList) {
			headers.set("path", url);
			HttpEntity<String> entity = new HttpEntity<>(null, headers);
			restTemplate.exchange( csdnUrl + url, HttpMethod.GET, entity, String.class);
		}
		
		
		System.out.println("刷新CSDN阅读量 成功 ");
	}

}
