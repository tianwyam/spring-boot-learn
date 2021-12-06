package com.tianya.springboot.es.controller;

import java.io.IOException;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.tianya.springboot.es.entity.Student;

@RestController
public class StudentController {
	
	public static final String STUDENT_INDEX = "student_index" ;
	
	@Autowired
	private RestHighLevelClient esClient;
	
	@GetMapping("/create/index")
	public String createIndex() {
		
		CreateIndexRequest createIndexRequest = new CreateIndexRequest(STUDENT_INDEX);
		
		try {
			CreateIndexResponse result = esClient.indices()
					.create(createIndexRequest, RequestOptions.DEFAULT);
			if (result.isAcknowledged()) {
				System.out.println("创建索引成功。。。。");
			}
			
			return JSON.toJSONString(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "createIndex";
	}
	
	
	@GetMapping("/insert")
	public String insertStudent() throws Exception {
		
		IndexRequest indexRequest = new IndexRequest(STUDENT_INDEX).id("100").type("long");
		
		Student student = Student.builder().id(100).age(24).name("To李小龙").addr("中国").build();
		
		indexRequest.source(JSON.toJSONString(student), XContentType.JSON);
		
		IndexResponse response = esClient.index(indexRequest, RequestOptions.DEFAULT);
		
		return JSON.toJSONString(response);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
