package com.tianya.springboot.freemarker.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tianya.springboot.freemarker.entity.Courses;
import com.tianya.springboot.freemarker.entity.Student;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

/**
 * @Description: 
 * 工具类
 * @author: TianwYam
 * @date 2021年5月4日 上午9:23:34
 */
public class FreeMarkerUtils {
	
	
	
	private static final Logger LOG = LoggerFactory.getLogger(FreeMarkerUtils.class);

	
	/**
	 * @Description: 
	 * 获取模板
	 * @author: TianwYam
	 * @date 2021年5月4日 上午9:36:13
	 * @param templateName 模板名称
	 * @return
	 */
	public static Template getTemplate(String templateName) {
		
		try {
			Configuration configuration = new Configuration(Configuration.VERSION_2_3_21);
			String dir = FreeMarkerUtils.class.getClassLoader().getResource("templates/ftl").getFile();
			LOG.info("模板目录：{}", dir);
			configuration.setDirectoryForTemplateLoading(new File(dir));
			configuration.setDefaultEncoding("UTF-8");
			configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
			
			return configuration.getTemplate(templateName);
		} catch (Exception e) {
			LOG.error("获取模板发生异常", e);
		}
		
		return null ;
	}

	
	/**
	 * @Description: 
	 *  填充模板，输出文件
	 * @author: TianwYam
	 * @date 2021年5月4日 上午9:41:37
	 * @param outputFilePath 填充后的文件输出的全路径
	 * @param templateName 模板的名称
	 * @param data 需要填充的数据
	 */
	public static void fillTemplate(String outputFilePath, String templateName, Object data) {
		
		Template template = getTemplate(templateName);
		if (template != null) {
			try {
				template.process(data, new FileWriter(outputFilePath));
				LOG.info("生成word文件成功");
			} catch (TemplateException | IOException e) {
				LOG.error("生成word文件失败", e);
			}
		}
		
	}
	
	
	
	
	public static void main(String[] args) {
		
		// 课程
		List<Courses> courses = new ArrayList<>();
		courses.add(Courses.builder().courseId(100).courseName("语文").courseScore(87).build());
		courses.add(Courses.builder().courseId(110).courseName("数学").courseScore(94).build());
		courses.add(Courses.builder().courseId(120).courseName("英语").courseScore(67).build());
		courses.add(Courses.builder().courseId(130).courseName("政治").courseScore(54).build());
		
		
		// 学生
		Student student = Student.builder()
				.id(2021050611)
				.name("花无缺")
				.age(25)
				.sex(1)
				.address("四川省成都市大熊猫基地养殖员")
				.height(175)
				.courses(courses)
				.build();
		
		// 生成word文件
		fillTemplate("E:\\javaWork\\freemarker\\student.docx", "student.ftl", student);
	}
	
	
	
	
}
