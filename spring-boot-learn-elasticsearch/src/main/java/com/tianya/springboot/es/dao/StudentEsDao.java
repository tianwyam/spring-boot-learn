package com.tianya.springboot.es.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.tianya.springboot.es.entity.Student;

@Repository
public interface StudentEsDao extends ElasticsearchRepository<Student, Long> {

}
