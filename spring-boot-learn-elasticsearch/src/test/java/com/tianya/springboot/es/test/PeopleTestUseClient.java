package com.tianya.springboot.es.test;

import java.util.Arrays;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;


/**
 * @Description: 
 * 使用 client方式
 * @author: TianwYam
 * @date 2022年3月23日 下午9:47:23
 */
@SpringBootTest
public class PeopleTestUseClient {
	
	
	@Autowired
	private RestHighLevelClient esClient ;
	
	@Test
	public void getAll() throws Exception {
		
		SearchRequest searchRequest = new SearchRequest("people_index");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		
		System.out.println("参数：");
		System.out.println(sourceBuilder);
		
		searchRequest.source(sourceBuilder);
		SearchResponse response = esClient.search(searchRequest, RequestOptions.DEFAULT);
		System.out.println("结果：");
		System.out.println(JSON.toJSONString(response, true));
	}
	
	@Test
	public void where() throws Exception {
		
		// 类似：select * from people where age = 45
		
		SearchRequest searchRequest = new SearchRequest("people_index");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		
		sourceBuilder.query(QueryBuilders.termQuery("age", 45));
		
		System.out.println("参数：");
		System.out.println(JSON.toJSONString(JSON.parse(sourceBuilder.toString()), true));
		
		searchRequest.source(sourceBuilder);
		SearchResponse response = esClient.search(searchRequest, RequestOptions.DEFAULT);
		System.out.println("结果：");
		System.out.println(JSON.toJSONString(response, true));
	}
	
	
	
	@Test
	public void in() throws Exception {
		
		// 类似：select * from people where professional in ('演员', '歌手')
		
		SearchRequest searchRequest = new SearchRequest("people_index");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		
		sourceBuilder.query(QueryBuilders.termsQuery("professional.keyword", Arrays.asList("演员","歌手")));
		
		System.out.println("参数：");
		System.out.println(JSON.toJSONString(JSON.parse(sourceBuilder.toString()), true));
		
		searchRequest.source(sourceBuilder);
		SearchResponse response = esClient.search(searchRequest, RequestOptions.DEFAULT);
		System.out.println("结果：");
		System.out.println(JSON.toJSONString(response, true));
	}
	
	
	@Test
	public void betweenAnd() throws Exception {
		
		// 类似：select * from people where age between 50 and 55
		
		SearchRequest searchRequest = new SearchRequest("people_index");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		
//		sourceBuilder.query(QueryBuilders.rangeQuery("age").from(50).to(55));
		
		//  50 < age <= 55
		sourceBuilder.query(QueryBuilders.rangeQuery("age").gt(50).lte(55));
		
		System.out.println("参数：");
		System.out.println(JSON.toJSONString(JSON.parse(sourceBuilder.toString()), true));
		
		searchRequest.source(sourceBuilder);
		SearchResponse response = esClient.search(searchRequest, RequestOptions.DEFAULT);
		System.out.println("结果：");
		System.out.println(JSON.toJSONString(response, true));
	}
	
	
	@Test
	public void likeLeft() throws Exception {
		
		// 类似：select * from people where professional like '武打%'
		
		SearchRequest searchRequest = new SearchRequest("people_index");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		
		sourceBuilder.query(QueryBuilders.prefixQuery("professional.keyword", "武打"));
		
		System.out.println("参数：");
		System.out.println(JSON.toJSONString(JSON.parse(sourceBuilder.toString()), true));
		
		searchRequest.source(sourceBuilder);
		SearchResponse response = esClient.search(searchRequest, RequestOptions.DEFAULT);
		System.out.println("结果：");
		System.out.println(JSON.toJSONString(response, true));
	}
	
	
	
	@Test
	public void likeAny() throws Exception {
		
		// 类似：select * from people where professional like '%打%员'
		
		SearchRequest searchRequest = new SearchRequest("people_index");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		
		// 通配符模糊查询
		sourceBuilder.query(QueryBuilders.wildcardQuery("professional.keyword", "*打*员"));
		
		System.out.println("参数：");
		System.out.println(JSON.toJSONString(JSON.parse(sourceBuilder.toString()), true));
		
		searchRequest.source(sourceBuilder);
		SearchResponse response = esClient.search(searchRequest, RequestOptions.DEFAULT);
		System.out.println("结果：");
		System.out.println(JSON.toJSONString(response, true));
	}
	
	
	
	@Test
	public void searchMore() throws Exception {
		
		// 类似：select * from people where age = 45 and professional = '歌手'
		
		SearchRequest searchRequest = new SearchRequest("people_index");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		
		// 组合查询
		sourceBuilder.query(QueryBuilders.boolQuery()
				.must(QueryBuilders.termQuery("age", 45))
				.must(QueryBuilders.termQuery("professional.keyword", "歌手")));
		
		System.out.println("参数：");
		System.out.println(JSON.toJSONString(JSON.parse(sourceBuilder.toString()), true));
		
		searchRequest.source(sourceBuilder);
		SearchResponse response = esClient.search(searchRequest, RequestOptions.DEFAULT);
		System.out.println("结果：");
		System.out.println(JSON.toJSONString(response, true));
	}
	
	
	@Test
	public void max() throws Exception {
		
		// 类似：select max(age) as ageMax from people 
		
		SearchRequest searchRequest = new SearchRequest("people_index");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		
		sourceBuilder.size(0);
		sourceBuilder.aggregation(AggregationBuilders.max("ageMax").field("age"));
		
		System.out.println("参数：");
		System.out.println(JSON.toJSONString(JSON.parse(sourceBuilder.toString()), true));
		
		searchRequest.source(sourceBuilder);
		SearchResponse response = esClient.search(searchRequest, RequestOptions.DEFAULT);
		System.out.println("结果：");
		System.out.println(JSON.toJSONString(response, true));
	}
	
	
	@Test
	public void count() throws Exception {
		
		// 类似：select count(uid) as uidNum from people 
		
		SearchRequest searchRequest = new SearchRequest("people_index");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		
		sourceBuilder.size(0);
		sourceBuilder.aggregation(AggregationBuilders.count("uidNum").field("uid"));
		
		System.out.println("参数：");
		System.out.println(JSON.toJSONString(JSON.parse(sourceBuilder.toString()), true));
		
		searchRequest.source(sourceBuilder);
		SearchResponse response = esClient.search(searchRequest, RequestOptions.DEFAULT);
		System.out.println("结果：");
		System.out.println(JSON.toJSONString(response, true));
	}
	
	
	
	
	@Test
	public void distinct() throws Exception {
		
		// 类似：select count(distinct uid) as uidCount from people 
		
		SearchRequest searchRequest = new SearchRequest("people_index");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		
		sourceBuilder.size(0);
		sourceBuilder.aggregation(AggregationBuilders.cardinality("uidCount").field("uid"));
		
		System.out.println("参数：");
		System.out.println(JSON.toJSONString(JSON.parse(sourceBuilder.toString()), true));
		
		searchRequest.source(sourceBuilder);
		SearchResponse response = esClient.search(searchRequest, RequestOptions.DEFAULT);
		System.out.println("结果：");
		System.out.println(JSON.toJSONString(response, true));
	}
	
	
	
	
	@Test
	public void groupBy() throws Exception {
		
		// 类似：select age, count(*) from people group by age
		
		SearchRequest searchRequest = new SearchRequest("people_index");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		
		sourceBuilder.size(0);
		sourceBuilder.aggregation(AggregationBuilders.terms("ageGroup").field("age"));
		
		System.out.println("参数：");
		System.out.println(JSON.toJSONString(JSON.parse(sourceBuilder.toString()), true));
		
		searchRequest.source(sourceBuilder);
		SearchResponse response = esClient.search(searchRequest, RequestOptions.DEFAULT);
		System.out.println("结果：");
		System.out.println(JSON.toJSONString(response, true));
	}
	
	
	
	@Test
	public void groupByOrder() throws Exception {
		
		// 类似：select age as key , count(*) as count from people group by age order by count asc, key desc
		
		SearchRequest searchRequest = new SearchRequest("people_index");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		
		sourceBuilder.size(0);
		sourceBuilder.aggregation(AggregationBuilders.terms("ageGroup")
				.field("age")
				.order(BucketOrder.compound(BucketOrder.count(true), BucketOrder.key(false))));
//		sourceBuilder.aggregation(AggregationBuilders.terms("ageGroup").field("age").order(BucketOrder.key(false)));
		
		System.out.println("参数：");
		System.out.println(JSON.toJSONString(JSON.parse(sourceBuilder.toString()), true));
		
		searchRequest.source(sourceBuilder);
		SearchResponse response = esClient.search(searchRequest, RequestOptions.DEFAULT);
		System.out.println("结果：");
		System.out.println(JSON.toJSONString(response, true));
	}
	
	
	
	@Test
	public void groupByMore() throws Exception {
		
		// 类似：select age , professional, count(*) as count from people group by age, professional
		
		SearchRequest searchRequest = new SearchRequest("people_index");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		
		sourceBuilder.size(0);
		sourceBuilder.aggregation(AggregationBuilders.terms("ageGroup")
				.field("age")
				.subAggregation(AggregationBuilders.terms("profGroup")
						.field("professional.keyword")));
//		sourceBuilder.aggregation(AggregationBuilders.terms("ageGroup").field("age").order(BucketOrder.key(false)));
		
		System.out.println("参数：");
		System.out.println(JSON.toJSONString(JSON.parse(sourceBuilder.toString()), true));
		
		searchRequest.source(sourceBuilder);
		SearchResponse response = esClient.search(searchRequest, RequestOptions.DEFAULT);
		System.out.println("结果：");
		System.out.println(response);
//		System.out.println(JSON.toJSONString(response, true));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
