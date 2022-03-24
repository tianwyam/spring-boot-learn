# spring-boot-learn



<br/>



是基于maven多模块工程来记录学习springboot的知识的一个过程



<br/>



目录：

- [spring-boot-learn](#spring-boot-learn)

  - [spring-boot-learn-jwt](#spring-boot-learn-jwt)

    - [JWT简单使用](#JWT简单使用)
    - [使用Assembly打包](#使用Assembly打包)
  - [spring-boot-learn-freemarker](#spring-boot-learn-freemarker)

    - [freemarker实现转word](#freemarker实现转word)
  - [spring-boot-learn-jsp](#spring-boot-learn-jsp)

    - [集成JSP](#集成JSP)
  - [spring-boot-learn-word](#spring-boot-learn-word)

    - [给PDF文件添加文本水印](#给PDF文件添加文本水印)
  - [spring-boot-learn-feign](#spring-boot-learn-feign)

    - [在springboot环境下](#在springboot环境下)
    - [不在spring环境下](#不在spring环境下)
  - [spring-boot-learn-capture-screen](#spring-boot-learn-capture-screen)
      - [获取屏幕截屏](#获取屏幕截屏)
      - [服务端推送](#服务端推送)
  - [spring-boot-learn-websocket 实现聊天-即时通讯](#spring-boot-learn-websocket)
  - [spring-boot-learn-mybatis 简单使用](#spring-boot-learn-mybatis)
  - [spring-boot-learn-javafx 使用JavaFX制作一个GUI客户端](#spring-boot-learn-javafx)
      - [上传本地JAR到私服仓库](#上传本地JAR到私服仓库)
  - [spring-boot-learn-mockito 单元测试](#spring-boot-learn-mockito)

  

  





<br/><br/>





## 	spring-boot-learn-jwt



spring boot集成JWT，实现token认证



### JWT简单使用



第一步：导入依赖

~~~xml

<!-- JWT -->
<dependency>
	<groupId>com.auth0</groupId>
	<artifactId>java-jwt</artifactId>
	<version>3.8.1</version>
</dependency>

<dependency>
	<groupId>io.jsonwebtoken</groupId>
	<artifactId>jjwt</artifactId>
	<version>0.9.1</version>
</dependency>

~~~



第二步：使用JWT工具类生成token(用于生成token)

~~~java

/**
 * @description
 *	生成TOKEN
 * @author TianwYam
 * @date 2021年5月19日下午5:43:36
 * @param tokenReqBean
 * @return
 */
public static String token(TokenReqBean tokenReqBean) {
	
	long nowTime = System.currentTimeMillis();
	// 私钥 不允许给其他人
	Algorithm algorithm = Algorithm.HMAC256(secret);
	return JWT.create()
			// 内容
			.withSubject(encode(JSON.toJSONString(tokenReqBean)))
			// token生成时刻
			.withIssuedAt(new Date(nowTime))
			// 过期时刻
			.withExpiresAt(new Date(nowTime + EXPIRES_TIME))
			.sign(algorithm);
}

~~~



第三步：验证token

~~~java

/**
 * @description
 *	验证TOKEN是否过期等等
 * @author TianwYam
 * @date 2021年5月19日下午5:51:22
 * @param token
 * @return 过期 则抛出异常
 */
public static TokenReqBean checkToken(String token) {
	Algorithm algorithm = Algorithm.HMAC256(secret);
	JWTVerifier verifier = JWT.require(algorithm).build();
	String subject = verifier.verify(token).getSubject();
	return JSON.parseObject(decode(subject), TokenReqBean.class);
}

~~~





<br/>



### 使用Assembly打包





pom.xml

~~~xml

<!-- 打包 -->
<build>

	<!-- 定义资源 -->
	<resources>

		<resource>
			<directory>src/main/java</directory>
			<includes>
				<include>**/*.*</include>
			</includes>
		</resource>

		<resource>
			<directory>src/main/resources</directory>
			<includes>
				<include>*.*</include>
			</includes>
			<!-- 把配置文件中的引用值转为实际值 ${}转成实际值 -->
			<filtering>true</filtering>
		</resource>

	</resources>

	<!-- 插件 -->
	<plugins>

		<!-- 1、打jar包 插件 -->
		<plugin>
			<groupId>org.apache.maven.plugins</groupId>
			<artifactId>maven-jar-plugin</artifactId>
			<configuration>
				<!-- 排除配置文件，不打到jar包内 -->
				<excludes>
					<exclude>*.properties</exclude>
					<exclude>*.yml</exclude>
					<exclude>release-note</exclude>
				</excludes>

				<archive>
					<!-- 配置jar包中MANIFEST的配置 -->
					<manifest>
						<!-- 指定JAR中的classpath -->
						<!-- 也可以不指定，由启动脚本指定 -->
						<addClasspath>true</addClasspath>
						<!-- MANIFEST.MF中 class-path 加入前缀 -->
						<classpathPrefix>lib/</classpathPrefix>
						<!-- jar不包含唯一版本标识 -->
						<useUniqueVersions>false</useUniqueVersions>
						<!-- 指定入口主类 -->
						<mainClass>com.tianya.springboot.jwt.LearnJwtApplication</mainClass>
					</manifest>

					<!-- MANIFEST.MF中 class-path 加入当前资源文件目录 -->
					<manifestEntries>
						<Class-Path>./</Class-Path>
					</manifestEntries>
				</archive>

				<!-- 打的jar包最后输出的目录 -->
				<outputDirectory>${project.build.directory}</outputDirectory>

			</configuration>
		</plugin>

		<!-- 2、拷贝maven依赖的jar -->
		<plugin>
			<groupId>org.apache.maven.plugins</groupId>
			<artifactId>maven-dependency-plugin</artifactId>
			<executions>
				<execution>
					<id>copy-dependencies</id>
					<phase>package</phase>
					<goals>
						<goal>copy-dependencies</goal>
					</goals>
					<configuration>
						<!-- 拷贝maven依赖的jar 的位置 -->
						<outputDirectory>${project.build.directory}/lib</outputDirectory>
						<excludeTransitive>false</excludeTransitive>
						<stripVersion>false</stripVersion>
						<includeScope>runtime</includeScope>
					</configuration>
				</execution>
			</executions>
		</plugin>

		<!-- 3、assembly插件 用来整体打包(包含配置文件、主jar、maven依赖的jar、脚本文件等) -->
		<!-- assembly可以整体打成一个 zip tar.gz等安装包 -->
		<plugin>
			<artifactId>maven-assembly-plugin</artifactId>
			<configuration>
				<descriptors>
					<!-- 打包的具体描述文件 -->
					<descriptor>assembly.xml</descriptor>
				</descriptors>
			</configuration>
			<executions>
				<execution>
					<id>make-assembly</id>
					<phase>package</phase>
					<goals>
						<goal>single</goal>
					</goals>
					<configuration>
						<appendAssemblyId>false</appendAssemblyId>
						<!-- 最终打的整体包的名称 -->
						<finalName>${project.artifactId}-install-${project.version}-${maven.build.timestamp}</finalName>
					</configuration>
				</execution>
			</executions>
		</plugin>

	</plugins>

</build>
~~~





assembly.xml

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<assembly>

	<!-- 可自定义，这里指项目环境 -->
	<id>allsystem</id>
	
	<!-- 打包类型，可以设置多个，最后输出的也是多个包 -->
	<formats>
		<format>tar.gz</format>
		<format>zip</format>
	</formats>
	
	<!-- 打包包含根目录 -->
	<includeBaseDirectory>true</includeBaseDirectory>
	
	<fileSets>
		
		<!-- 复制配置文件到 打包目录中 -->
		<fileSet>
			<!-- 源资源目录 -->
			<directory>${basedir}/target/classes</directory>
			<!-- 输出到打包的目录 -->
			<outputDirectory>./</outputDirectory>
			<!-- 设置文件权限 -->
			<fileMode>0777</fileMode>
			<!-- 包括 -->
			<includes>
				<include>*.properties</include>
				<include>*.yml</include>
				<include>*.sh</include>
			</includes>
		</fileSet>
		
		
		<!-- 复制第三方依赖包 到 打包目录lib中 -->
		<fileSet>
			<directory>${basedir}/target/lib</directory>
			<outputDirectory>lib</outputDirectory>
			<fileMode>0777</fileMode>
		</fileSet>
		
		
		<!-- 复制项目JAR包 到 打包目录中 -->
		<fileSet>
			<directory>${basedir}/target</directory>
			<outputDirectory>./</outputDirectory>
			<fileMode>0777</fileMode>
			<includes>
				<include>${project.build.finalName}.jar</include>
			</includes>
		</fileSet>
		
	</fileSets>

</assembly>
~~~







<br/>





## spring-boot-learn-freemarker



spring boot集成freemarker



<br/>



### freemarker实现转word



第一步：制作word模板

第二步：word模板转xml文件

第三步：修改xml文件内容(添加freemarker语法)

第四步：修改xml文件后缀为.ftl

第五步：使用freemarker的API语法实现输出word文件





<br/>

<br/>



## spring-boot-learn-jsp



spring boot集成JSP



<br/>



### 集成JSP



第一步：添加依赖

~~~xml

<!-- spring boot web -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- servlet api -->
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
</dependency>

<!-- JSTL（JSP Standard Tag Library，JSP标准标签库) -->
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>jstl</artifactId>
</dependency>

<!-- tomcat 中JSP的解析器 -->
<dependency>
    <groupId>org.apache.tomcat.embed</groupId>
    <artifactId>tomcat-embed-jasper</artifactId>
</dependency>

~~~



第二步：主配置application.yml文件

spring boot 版本不一样配置也不一样

~~~yaml

server:
  port: 8080
  servlet:
    context-path: /
    
    
spring:
  mvc:
    view:
      # 前缀
      prefix: /WEB-INF/
      # 后缀
      suffix: .jsp  
~~~



第三步：编写控制器

~~~java
@Controller
public class IndexController {
	
	@GetMapping("/index")
	public String index() {
		return "index" ;
	}

}
~~~

启动类：

~~~java
@SpringBootApplication
public class JspLearnApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(JspLearnApplication.class, args);
	}
}
~~~





第四步：新建JSP文件

目前只有两种位置放JSP，才能不报404资源找不到错误

1、/resources/META-INF/resourcesWEB-INF/

2、/webapp/WEB-INF/

 若是两个位置都存在相同资源

​	则优先级：/webapp/WEB-INF/  大于 /resources/META-INF/resourcesWEB-INF/

其他位置，都报找不到资源404



位置：webapp/WEB-INF/

~~~jsp
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>首页</title>
</head>
<body>
	INDEX JSP | /webapp/WEB-INF/index.jsp
</body>
</html>
~~~



JSP位置放不对，很容易导致资源访问不到问题





<br/>





## spring-boot-learn-word

  针对word的操作：

  - spire.doc.free 简单示例
  - itextpdf实现对生成后的PDF添加页码示例



<br/>



### 给PDF文件添加文本水印



第一步：导入依赖

~~~xml

<dependency>
    <groupId>com.itextpdf</groupId>
    <artifactId>itextpdf</artifactId>
    <version>5.5.13.1</version>
</dependency>

<!-- 字体依赖 -->
<dependency>
    <groupId>com.itextpdf</groupId>
    <artifactId>itext-asian</artifactId>
    <version>5.2.0</version>
</dependency>

~~~



第二步：编写代码

~~~java

/**
 * @description
 *	给PDF文档添加水印
 * @author TianwYam
 * @date 2021年4月28日上午10:00:05
 */
public static void addWaterMark(String pdfFilePath, String outputFilePath) {
	
	
	try {
		// 原PDF文件
		PdfReader reader = new PdfReader(pdfFilePath);
		// 输出的PDF文件内容
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputFilePath));
		
		// 字体
		BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", true);

		PdfGState gs = new PdfGState(); 
		// 设置透明度
		gs.setFillOpacity(0.3f); 
		gs.setStrokeOpacity(0.4f);
		
		int totalPage = reader.getNumberOfPages() + 1;
		for (int i = 1; i < totalPage; i++) {
			// 内容上层
//				PdfContentByte content = stamper.getOverContent(i);
			// 内容下层
			PdfContentByte content = stamper.getUnderContent(i);
			
			content.beginText();
			// 字体添加透明度
			content.setGState(gs);
			// 添加字体大小等
			content.setFontAndSize(baseFont, 50);
			// 添加范围
			content.setTextMatrix(70, 200);
			// 具体位置 内容 旋转多少度
			content.showTextAligned(Element.ALIGN_CENTER, "机密文件", 300, 350, 300);
			content.showTextAligned(Element.ALIGN_TOP, "机密文件", 100, 100, 5);
			content.showTextAligned(Element.ALIGN_BOTTOM, "机密文件", 400, 400, 75);
			
			content.endText();
		}
		
		// 关闭流
		stamper.close();
		reader.close();
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	
}

~~~





<br/>



  

## spring-boot-learn-excel

  针对excel的操作：

  - hutool工具实现对excel导入导出的示例



<br/>



## spring-boot-learn-crawler-webmagic

  简单学习WebMagic，是一个简单灵活的Java爬虫框架

  - 使用webmagic爬取了笔趣阁小说类别
  - 爬取了笔趣阁小说《剑来》目录



<br/>



## spring-boot-learn-mybatis

  spring boot整合mybatis持久层框架


第一步：添加spring boot和mybatis的依赖，可以根据实际情况进行修改版本

~~~xml

<!-- Spring Boot 整合的 mybatis 的依赖jar包，
其中包含mybatis相关的依赖 -->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.0.0</version>
</dependency>

<!-- MySql驱动 -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>

~~~

第二步：application.yml文件添加配置
分为数据源配置 + mybatis的配置

~~~yaml

## 数据源的配置
spring:
  datasource:
    username: root
    password: mysql
    ## 新版本MySQL驱动链接URL必须添加时区配置 serverTimezone=UTC
    url: jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC
    driver-class-name: com.mysql.jdbc.Driver
    
   
## mybatis的配置
mybatis:
  ## 配置mybatis的总配置文件路径
  config-location: classpath:mybatis/mybatis.xml
  ## 配置文件各个mapper xml文件的路径
  mapper-locations: classpath:mybatis/mapper/*.xml
  ## 配置取别名的实体类包全路径
  type-aliases-package: com.tianya.springboot.mybatis.entity
  
  
~~~

第三步：在启动类上添加mapper接口扫描路径

~~~java

@SpringBootApplication
// 自动扫描mapper接口全路径
@MapperScan("com.tianya.springboot.mybatis.mapper")
public class SpringMybatisApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SpringMybatisApplication.class, args);
	}

}
~~~

第四步：在接口上添加mapper注解

~~~java
// mapper是mybatis的注解，定义一个mapper接口
// 也会被添加到spring bean容器动
// 若是只用@Repository注解，不会有mybatis的自动扫描配置
// 若是配置了SqlSessionFactory，也是可以的
@Mapper
public interface IUserMapper {
	public List<UserBean> getUserList() ;
}

~~~

第五步：在mapper xml文件具体实现的SQL的方法

~~~xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!-- namespace必须是mapper接口的全路径 -->
<mapper namespace="com.tianya.springboot.mybatis.mapper.IUserMapper">
    <!-- id必须是mapper接口中定义的方法名，不可重载 -->
    <select id="getUserList" resultType="UserBean">
        select * from users
    </select>
</mapper>

~~~



<br/>



## spring-boot-learn-capture-screen



<br/>



  使用服务端推送技术SSE+屏幕截屏，实现一个简单的屏幕共享功能
  - SseEmitter 实现服务端推送功能
  - java.awt.Toolkit 获取屏幕截屏



<br/>



### 获取屏幕截屏



~~~java

/**
* 捕获屏幕，生成图片
*/
public static String capture() {

    // 获取屏幕大小
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    String fileName = null ;
    try {

        Robot robot = new Robot();
        Rectangle screenRect = new Rectangle(screenSize);
        // 捕获屏幕
        BufferedImage screenCapture = robot.createScreenCapture(screenRect);
        //存放位置
        String path = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
        fileName = System.currentTimeMillis() + ".png" ;
        LOGGER.info("屏幕截屏路径：{}", path);
        // 把捕获到的屏幕 输出为 图片
        ImageIO.write(screenCapture, "png", new File(path +File.separator + fileName));

    } catch (Exception e) {
        LOGGER.error("获取屏幕截屏发生异常", e);
    }

    return fileName ;
}


~~~



<br/>



### 服务端推送



<br/>



方式一：自己拼接返回参数值

~~~java
// 服务端推送 返回结果必须以 data: 开始
public static final String SSE_RETURN_START = "data:" ;

// // 服务端推送 返回结果必须以 \n\n 结束
public static final String SSE_RETURN_END = "\n\n" ;

// 服务端推送SSE技术 内容格式必须是  text/event-stream
@GetMapping(value = {"","/"}, produces = "text/event-stream;charset=UTF-8")
public String index() {
    String capture = CaptureScreenUtils.capture();
    // 返回的内容 必须以 data: 开头，以 \n\n 结尾，不然前端JS处始终不会进入到 onmessage 方法内
    return SSE_RETURN_START + capture + SSE_RETURN_END ;
}
~~~

方式二：使用Spring MVC已经封装的对象SseEmitter 来实现

~~~java

@GetMapping("/index")
public SseEmitter sse() {

    String capture = CaptureScreenUtils.capture();
    SseEmitter emitter = new SseEmitter(100L) ;
    try {
        emitter.send(capture);
    } catch (IOException e) {
        e.printStackTrace();
    }

    return emitter;
}
	
~~~

实际是封装的内部拼接的头尾

~~~java
// org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilderImpl

@Override
public SseEventBuilder data(Object object, @Nullable MediaType mediaType) {
    append("data:");
    saveAppendedText();
    this.dataToSend.add(new DataWithMediaType(object, mediaType));
    append("\n");
    return this;
}
~~~

**前端页面JS**

~~~javascript
var source = new EventSource("/sse/index");
source.onmessage = function(event){
    console.log('onmessage');
    console.log(event.data);
    document.getElementById('captureImgId').src = event.data;
};
~~~





<br/>



## spring-boot-learn-websocket



采用全注解方式实现 web socket聊天

第一步：首先引入websocket的依赖

~~~xml

<!-- spring boot WebSocket相关包 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-websocket</artifactId>
</dependency>
~~~



第二步：采用注解方式实现websocket服务端

~~~java
package com.tianya.springboot.websocket.server;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianya.springboot.websocket.handle.AppIdMsgHandle;

import lombok.extern.slf4j.Slf4j;

/**
 * @description
 *	WebSocket 服务端
 * 每种应用下，对应的会话连接
 * <pre>key: appId 不同应用，
 * 如：
 * 	msg对应消息推送，
 * 	log对应日志推送等，
 * 	chat对应聊天信息推送</pre>
 * @author TianwYam
 * @date 2020年9月17日下午1:47:22
 */
@Slf4j
@Service
@ServerEndpoint(value = "/ws/{appId}")
public class WebSocketServer {
	
	// 自动注入消息处理的实现
	// @Autowired 在这儿自动注入会报空指针异常，取不到
	private static Set<AppIdMsgHandle> appIdMsgHandles ;

	/*** 每种应用下，对应的会话连接 */
	private static final ConcurrentHashMap<String, LinkedHashSet<Session>> APPID_CONNECT_SESSION_MAP = new ConcurrentHashMap<>();
	
	
	/**
	 * @description
	 *	session 连接 开始
	 * @author TianwYam
	 * @date 2020年9月17日下午2:27:12
	 * @param session 会话连接
	 * @param appId 应用ID
	 */
	@OnOpen
	public void open(Session session, @PathParam(value = "appId") String appId) {
		
		// 获取此应用下所有连接
		LinkedHashSet<Session> sessions = getSessions(appId);
		if (sessions == null) {
			sessions = new LinkedHashSet<>();
			APPID_CONNECT_SESSION_MAP.put(appId, sessions);
		}
		
		// 新会话session 连接成功
		sessions.add(session);
		log.info("新连接进入，目前[{}]总人数：{}", appId, sessions.size());
	}
	
	
	/**
	 * @description
	 *	session 连接关闭
	 * @author TianwYam
	 * @date 2020年9月17日下午3:32:45
	 * @param session
	 * @param appId
	 */
	@OnClose
	public void close(Session session, @PathParam(value = "appId") String appId) {
		
		// 获取此应用下所有连接
		LinkedHashSet<Session> sessions = getSessions(appId);
		if (sessions != null) {
			// 会话session 连接断开
			sessions.remove(session);
			log.info("连接断开，目前[{}]总人数：{}", appId, sessions.size());
		}
		
	}
	
	
	/**
	 * @description
	 *	接受消息 
	 * @author TianwYam
	 * @date 2020年9月17日下午3:39:22
	 * @param message 消息内容
	 * @param session 会话
	 * @param appId 应用
	 */
	@OnMessage
	public void onMessage(String message, Session session, @PathParam(value = "appId") String appId) {
		
		// 消息处理
		for (AppIdMsgHandle appIdMsgHandle : appIdMsgHandles) {
			if (Objects.equals(appIdMsgHandle.getAppId(), appId)) {
				appIdMsgHandle.handleMsg(message, session);
			}
		}
	}
	
	

	/**
	 * @description
	 *	连接 报错
	 * @author TianwYam
	 * @date 2020年9月17日下午3:50:12
	 * @param session
	 * @param error
	 */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("websocket发生错误", error);
    }

	
	
	
	/**
	 * @description
	 *	根据应用获取所有在线人员
	 * @author TianwYam
	 * @date 2020年9月17日下午3:17:04
	 * @param appId 应用ID
	 * @return
	 */
	public static LinkedHashSet<Session> getSessions(String appId){
		return APPID_CONNECT_SESSION_MAP.get(appId);
	}
	
	
	
	/**
	 * @description
	 *	发送文本消息
	 * @author TianwYam
	 * @date 2020年9月17日下午3:52:52
	 * @param session
	 * @param message
	 */
	public static void sendTxtMsg(Session session , String message) {
		try {
			session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			log.error("websocket发生消息失败", e);
		}
	}



	/**
	 * @description
	 * <pre>
	 *	WebSocket是线程安全的，有用户连接时就会创建一个新的端点实例，一个端点只能保证一个线程调用。总结就是，WebSocket是多例的。
	 *	Autowired 是程序启动时就注入进去
	 *	用静态变量来保存消息处理实现类，避免出现空指针异常
	 *	另一种方式就是 使用 ApplicationContext.getBean()的方式也可以获取
	 *	</pre>
	 * @author TianwYam
	 * @date 2020年9月17日下午7:43:13
	 * @param appIdMsgHandles
	 */
	@Autowired
	public void setAppIdMsgHandles(Set<AppIdMsgHandle> appIdMsgHandles) {
		WebSocketServer.appIdMsgHandles = appIdMsgHandles;
	}	
}

~~~



第三步：界面websocket客户端

~~~javascript
$(function() {

    var websocket;

    // 首先判断是否 支持 WebSocket
    if('WebSocket' in window) {
        websocket = new WebSocket("ws://localhost:8080/ws/chat");
    } else if('MozWebSocket' in window) {
        websocket = new MozWebSocket("ws://localhost:8080/ws/chat");
    } else {
        websocket = new SockJS("http://localhost:8080/ws/chat");
    }

    // 打开时
    websocket.onopen = function(event) {
        console.log("  websocket.onopen  ");
        console.log(event);
        $("#msg").append("<p>(<font color='blue'>欢迎加入聊天群</font>)</p>");
    };

    // 处理消息时
    websocket.onmessage = function(event) {
    	console.log(event);
        $("#msg").append("<p>(<font color='red'>" + event.data + "</font>)</p>");
        console.log("  websocket.onmessage   ");
    };

    websocket.onerror = function(event) {
        console.log("  websocket.onerror  ");
    };

    websocket.onclose = function(event) {
        console.log("  websocket.onclose  ");
    };

    // 点击了发送消息按钮的响应事件
    $("#TXBTN").click(function(){
        // 获取消息内容
        var text = $("#tx").val();
        // 判断
        if(text == null || text == ""){
            alert(" content  can not empty!!");
            return false;
        }
        // 发送消息
        websocket.send(text);
        $("#tx").val('');
    });
});

~~~

html界面

~~~html
<!DOCTYPE HTML>
<html>
    <head>
        <title>WebSocket聊天</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="renderer" content="webkit">
        <!-- 引入 JQuery  -->
        <script type="text/javascript" src="js/jquery.min.js"></script>
        <!-- 引入 sockJS  -->
        <script type="text/javascript" src="js/sockjs.min.js" ></script>
        <!-- 自定义JS文件 -->
        <script type="text/javascript" src="js/index.js"></script>
    </head>
    <body>
        <!-- 最外边框 -->
        <div style="margin: 20px auto; border: 1px solid blue; width: 300px; height: 500px;">
            <!-- 消息展示框 -->
            <div id="msg" style="width: 100%; height: 70%; border: 1px solid yellow;overflow: auto;"></div>
            <!-- 消息编辑框 -->
            <textarea id="tx" style="width: 98%; height: 20%;"></textarea>
            <!-- 消息发送按钮 -->
            <button id="TXBTN" style="width: 100%; height: 8%;">发送数据</button>
        </div>
    </body>
</html>

~~~



<br/>



## spring-boot-learn-validation



针对项目做参数校验

采用：

- spring validation
- hibernate validator





第一步：做全局异常处理：

~~~java
/**
 * @description
 *	全局异常校验处理
 * @author TianwYam
 * @date 2020年12月25日下午7:22:47
 */
@RestControllerAdvice
public class GlobalValidExceptionHandler {
	
	/**
	 * @description
	 *	类绑定异常
	 * @author TianwYam
	 * @date 2020年12月28日下午5:14:27
	 * @param e
	 * @return
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public ResultUtis validBeanException(ConstraintViolationException e) {
		String errorMsg = "" ;
		Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
		for (ConstraintViolation<?> constraintViolation : constraintViolations) {
			errorMsg = constraintViolation.getMessage();
			break;
		}
		return ResultUtis.error(6101, errorMsg);
	}
	
	
	/**
	 * @description
	 *	方法参数校验异常
	 * @author TianwYam
	 * @date 2020年12月28日下午5:16:02
	 * @param e
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResultUtis validMethodException(MethodArgumentNotValidException e) {
		String errorMsg = "" ;
		List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
		for (ObjectError objectError : allErrors) {
			errorMsg = objectError.getDefaultMessage();
			break;
		}
		return ResultUtis.error(6102, errorMsg);
	}
	
	
	/**
	 * @description
	 *	参数绑定异常
	 * @author TianwYam
	 * @date 2020年12月28日下午5:17:11
	 * @param e
	 * @return
	 */
	@ExceptionHandler(BindException.class)
	public ResultUtis validBindException(BindException e) {
		String errorMsg = "" ;
		List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
		for (ObjectError objectError : allErrors) {
			errorMsg = objectError.getDefaultMessage();
			break;
		}
		return ResultUtis.error(6103, errorMsg);
	}
	
	@ExceptionHandler(Exception.class)
	public ResultUtis validException(Exception e) {
		return ResultUtis.error(ResultCode.SERVER_ERROR);
	}

}

~~~



第二步：在对应参数类、参数值变量等上面加校验注解

~~~java

@Data
@Builder
public class Student {
	
	@NotBlank(message = "学号不能为空", groups = {ValidType.Update.class, ValidType.Delete.class})
	private int id ;
	
	@NotBlank(message = "学生姓名不可以为空", groups = {ValidType.Insert.class})
	private String name ;
	
	private String addr ;
	
	@Range(max = 300, message = "年龄不能超过300")
	private int age ;
	
	private int height ;
	
	private String phone ;
	
	private String idcard ;
	
	@Email(message = "邮箱格式错误")
	private String email ;

}
~~~



第三步：在对应调用处添加验证注解 @Validated 或者 @Valid

~~~java
	@PostMapping("/")
	public ResultUtis add(
			@RequestBody 
			@Validated(ValidType.Insert.class) 
			Student student) {
		
		return ResultUtis.success(student);
	}
~~~



注意：

分组校验时采用 @Validated

嵌套校验时，子校验采用 @Valid



自定义分组：

~~~java
public interface ValidType {
	
	// 若是不继承 Default，则分组校验只会校验 相应加了 update组检验的属性才能校验，没有添加的，是不会校验的
	interface Update extends Default{}
	
	interface Selete extends Default{}
	
	interface Delete extends Default{}
	
	interface Insert extends Default{}
	
}

~~~

注意：若是不继承 Default，则分组校验只会校验 相应加了 update组检验的属性才能校验，没有添加的，是不会校验的





<br/>







## spring-boot-learn-feign



spring boot中使用feign，实现请求第三方接口，作为一个 http请求工具



分为两种使用方式：第一种，在springboot环境下；第二种，不在spring环境下



<br/>



### 在springboot环境下



第一步，添加maven配置，引入JAR包，pom.xml文件

~~~xml

<!-- 用于springboot项目环境下，或者springcloud项目环境下 集成 feign -->
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-openfeign</artifactId>
	<version>2.0.2.RELEASE</version>
</dependency>

~~~



第二步，编写feign客户端，相当于实际请求接口

~~~java

@FeignClient(name = "studentFeignClient", url = "${student.feign.url}")
public interface StudentFeignClient {

	@GetMapping("/list")
	public ResultUtis getList() ;
}

~~~



第三步，注册到启动类上

~~~java
// 开启 feign
@EnableFeignClients
@SpringBootApplication
public class FeignLearnApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeignLearnApplication.class, args);
	}
}

~~~



第四步，实际调用

~~~java

@RestController
@RequestMapping("/student")
public class StudentController {
	
    // 实际调用，自动注入
	@Autowired
	private StudentFeignClient studentFeignClient ;

	@GetMapping("/list")
	public ResultUtis getList() {
		return studentFeignClient.getList();
	}
}

~~~





<br/>



### 不在spring环境下



第一步，配置maven，添加JAR包，pom.xml

~~~xml

<!-- 以下几个用于 spring环境、springmvc环境下的，或者 不是 spring容器内的项目 集成 feign -->
<!-- feign-core 是核心，其他JAR用不到，可以不用引入 -->
 <dependency>
	 <groupId>io.github.openfeign</groupId>
	 <artifactId>feign-core</artifactId>
	<version>${feign.version}</version>
</dependency>

 <dependency>
	 <groupId>io.github.openfeign</groupId>
	 <artifactId>feign-gson</artifactId>
	<version>${feign.version}</version>
</dependency>

 <dependency>
	 <groupId>io.github.openfeign</groupId>
	 <artifactId>feign-jackson</artifactId>
	<version>${feign.version}</version>
</dependency>



 <dependency>
	 <groupId>io.github.openfeign</groupId>
	 <artifactId>feign-httpclient</artifactId>
	<version>${feign.version}</version>
</dependency>


 <dependency>
	 <groupId>io.github.openfeign</groupId>
	 <artifactId>feign-okhttp</artifactId>
	<version>${feign.version}</version>
</dependency>

 <dependency>
	 <groupId>io.github.openfeign</groupId>
	 <artifactId>feign-ribbon</artifactId>
	<version>${feign.version}</version>
</dependency>

		
~~~



第二步，编写feign客户端，实际访问请求接口

~~~java

/**
 * @description
 *	不在spring环境下的  feign 接口定义
 * @author TianwYam
 * @date 2020年12月25日下午6:25:42
 */
public interface StudentFeignClientNoSpring {
	

	// 不使用spring环境下的，只能使用 feign自带的 RequestLine 来声明 方法 请求 类型
	@RequestLine("GET /list")
	public ResultUtis getList() ;

}

~~~



第三步，调用方式

~~~java

/**
 * @description
 *	在不是 spring环境下 、 springboot环境下的 使用feign
 * @author TianwYam
 * @date 2020年12月25日下午6:17:56
 */
public class FeignNoSpringMain {

	public static void main(String[] args) {
		
		
		StudentFeignClientNoSpring studentFeignClient = Feign.builder()
			.decoder(new GsonDecoder())
			.target(StudentFeignClientNoSpring.class, "http://localhost:8081/api/v1/student");
		
		ResultUtis list = studentFeignClient.getList();
		
		System.out.println(JSON.toJSONString(list, SerializerFeature.PrettyFormat));
	}
	
}

~~~

此处写成main方式，实际项目中，可以写成一个工具类

~~~java

/**
 * @description
 *	Feign工具类
 * @author TianwYam
 * @date 2021年1月30日上午11:24:21
 */
public class FeignClientUtils {

	/**
	 * @description
	 *	获取 feign客户端
	 * @author TianwYam
	 * @date 2021年1月30日上午11:23:34
	 * @param <T> 客户端类
	 * @param url 调用客户端请求的URL
	 * @param clazz 客户端 class
	 * @return
	 */
	public static <T> T getClient(String url, Class<T> clazz) {

		return Feign.builder()
				.decoder(new GsonDecoder())
				.encoder(new GsonEncoder())
				.target(clazz, url);
	}
}
~~~







## spring-boot-learn-javafx



使用JavaFX集成spring boot简单编写了一个GUI客户端，实现上传本地JAR到私服仓库



maven pom.xml

~~~xml
<!-- spring boot 集成 JavaFX -->
<dependency>
    <groupId>de.roskenet</groupId>
    <artifactId>springboot-javafx-support</artifactId>
    <version>2.1.6</version>
</dependency>


<!-- spring boot 集成 JavaFX 的 插件 -->
<plugin>
    <groupId>com.zenjava</groupId>
    <artifactId>javafx-maven-plugin</artifactId>
    <configuration>
        <mainClass>com.tianya.springboot.javafx.LearnJavaFXApplication</mainClass>
        <vendor>tianwyam</vendor>
    </configuration>
</plugin>
~~~





### 上传本地JAR到私服仓库



主要命令：

~~~powershell
 cmd /c mvn -s maven本地的/config/setting.xml deploy:deploy-file -Durl=私服仓库地址  -DrepositoryId=私服仓库ID -DgeneratePom=false  -Dpackaging=jar -Dfile=jar包文件地址  -Dsources=源jar包地址  -Djavadoc=doc包地址
~~~



使用 Java调用本地命令

~~~java
Runtime CMD = Runtime.getRuntime();
Process proc = CMD.exec(命令);
~~~







## spring-boot-learn-mockito



单元测试



### springboot 集成的 test 进行单元测试



第一步：maven配置引入依赖

~~~xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
</dependency>

~~~



第二步：编写测试用例

~~~java
package com.tianya.springboot.learntest.test.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;
import com.tianya.springboot.learntest.entity.UserBean;
import com.tianya.springboot.learntest.service.UserService;

/**
 * @description
 *	第一种方式：spring boot 测试方式
 *	它可以模拟springboot启动整个容器
 * @author TianwYam
 * @date 2021年8月22日下午6:19:44
 */
@SpringBootTest
public class UserServiceTest {
	
	@Autowired
	private UserService userService;
	
	
	@Test
	public void testGetAll() {
		
		List<UserBean> users = userService.getUsers();
		
		System.out.println(JSON.toJSONString(users, true));
		
	}

}
~~~



这种方式需要启动整个项目spring容器运行



<hr/>



## spring-boot-learn-elasticsearch



对 elasticsearch操作



### 一、使用spring-data方式进行操作elasticsearch





1.添加依赖

~~~xml
<!-- 添加 elasticsearch 客户端 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
</dependency>

~~~



2.添加ES配置

~~~yml

#使用模板方式
spring:
  elasticsearch:
    rest:
      uris:
      - http://localhost:9200
    
~~~



3.添加实体类

~~~java
package com.tianya.springboot.es.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "people_index", type = "people")
public class PeopleBean {
	
	/**
	 * 主键
	 */
	@Id
	private Long uid ;
	
	/**
	 * 姓名
	 */
	@Field
	private String name ;
	
	/**
	 * 年龄
	 */
	@Field
	private int age ;
	
	/**
	 * 地址
	 */
	@Field
	private String addr ;
	
	/**
	 * 生日
	 */
	@Field
	private String birthDay ;
	
	/**
	 * 职业
	 */
	@Field
	private String professional ;
	
	/**
	 * 兴趣
	 */
	@Field
	private String interest ;
	
}
~~~



4.添加repository

~~~java
package com.tianya.springboot.es.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.tianya.springboot.es.entity.PeopleBean;

@Repository
public interface PeopleEsDao extends ElasticsearchRepository<PeopleBean, Long>{

}
~~~



准备操作都做完了，开始进行对elasticsearch操作了



#### 1.1 新增



~~~java
@SpringBootTest
public class PeopleTest {
	
	@Autowired
	private PeopleEsDao peopleEsDao ;
	
	@Test
	public void insert() {
		
		List<PeopleBean> peopleBeanList = new ArrayList<>();
		
		peopleBeanList.add(PeopleBean.builder().uid(1L).name("吴彦祖").age(45).birthDay("1977-01-01").addr("香港").professional("演员").interest("赛车").build());
		peopleBeanList.add(PeopleBean.builder().uid(2L).name("吴奇隆").age(55).birthDay("1967-01-01").addr("大陆").professional("歌手").interest("演戏").build());
		peopleBeanList.add(PeopleBean.builder().uid(3L).name("吴京").age(45).birthDay("1977-01-01").addr("大陆").professional("武打演员").interest("武术").build());
		peopleBeanList.add(PeopleBean.builder().uid(4L).name("古天乐").age(55).birthDay("1967-01-01").addr("香港").professional("演员").interest("唱歌").build());
		peopleBeanList.add(PeopleBean.builder().uid(5L).name("苏炳添").age(35).birthDay("1987-01-01").addr("大陆").professional("运动员").interest("跑步").build());
		peopleBeanList.add(PeopleBean.builder().uid(6L).name("刘亦菲").age(30).birthDay("1992-01-01").addr("大陆").professional("歌手").interest("演戏").build());
		peopleBeanList.add(PeopleBean.builder().uid(7L).name("刘翔").age(30).birthDay("1992-01-01").addr("大陆").professional("运动员").interest("健身").build());
		peopleBeanList.add(PeopleBean.builder().uid(8L).name("张家辉").age(45).birthDay("1977-01-01").addr("香港").professional("演员").interest("唱歌").build());
		peopleBeanList.add(PeopleBean.builder().uid(9L).name("毛不易").age(45).birthDay("1977-01-01").addr("大陆").professional("歌手").interest("唱歌").build());
		
		
        // 新增
		Iterable<PeopleBean> saveResult = peopleEsDao.saveAll(peopleBeanList);
		System.out.println(JSON.toJSONString(saveResult, true));
	}
}
~~~

结果：

~~~json
[
	{
		"addr":"香港",
		"age":45,
		"birthDay":"1977-01-01",
		"interest":"赛车",
		"name":"吴彦祖",
		"professional":"演员",
		"uid":1
	},
	{
		"addr":"大陆",
		"age":55,
		"birthDay":"1967-01-01",
		"interest":"演戏",
		"name":"吴奇隆",
		"professional":"歌手",
		"uid":2
	},
	{
		"addr":"大陆",
		"age":45,
		"birthDay":"1977-01-01",
		"interest":"武术",
		"name":"吴京",
		"professional":"武打演员",
		"uid":3
	},
	{
		"addr":"香港",
		"age":55,
		"birthDay":"1967-01-01",
		"interest":"唱歌",
		"name":"古天乐",
		"professional":"演员",
		"uid":4
	},
	{
		"addr":"大陆",
		"age":35,
		"birthDay":"1987-01-01",
		"interest":"跑步",
		"name":"苏炳添",
		"professional":"运动员",
		"uid":5
	},
	{
		"addr":"大陆",
		"age":30,
		"birthDay":"1992-01-01",
		"interest":"演戏",
		"name":"刘亦菲",
		"professional":"歌手",
		"uid":6
	},
	{
		"addr":"大陆",
		"age":30,
		"birthDay":"1992-01-01",
		"interest":"健身",
		"name":"刘翔",
		"professional":"运动员",
		"uid":7
	},
	{
		"addr":"香港",
		"age":45,
		"birthDay":"1977-01-01",
		"interest":"唱歌",
		"name":"张家辉",
		"professional":"演员",
		"uid":8
	},
	{
		"addr":"大陆",
		"age":45,
		"birthDay":"1977-01-01",
		"interest":"唱歌",
		"name":"毛不易",
		"professional":"歌手",
		"uid":9
	}
]
~~~



#### 1.2 查询-所有



~~~java
@Test
public void get() {
    // 查询所有
    Iterable<PeopleBean> peopleList = peopleEsDao.findAll();
    System.out.println(JSON.toJSONString(peopleList, true));
}

~~~

类似SQL:

~~~sql
SELECT * FROM people
~~~



#### 1.3 查询-排序

~~~java
@Test
public void order4Get() {

    // 按照uid进行排序
    Sort orderSort = Sort.by(Order.asc("uid"));
    Iterable<PeopleBean> sortPeopleList = peopleEsDao.findAll(orderSort);
    System.out.println(JSON.toJSONString(sortPeopleList, true));
}
~~~

可以按照多个字段进行排序

public static Sort by(Order... orders) ;



类似SQL:

~~~sql
SELECT * FROM people order by uid
~~~



#### 1.4 分页查询

~~~java
@Test
public void page4Get() {

    PageRequest page = PageRequest.of(0, 5, Sort.by(Order.asc("uid")));
    // 分页查询
    Page<PeopleBean> pagePeopleList = peopleEsDao.findAll(page);
    System.out.println(JSON.toJSONString(pagePeopleList, true));
}

~~~

类似SQL：

~~~sql
SELECT * FROM people order by uid limit 0,5
~~~

结果：

~~~json
{
	"content":[
		{
			"addr":"香港",
			"age":45,
			"birthDay":"1977-01-01",
			"interest":"赛车",
			"name":"吴彦祖",
			"professional":"演员",
			"uid":1
		},
		{
			"addr":"大陆",
			"age":55,
			"birthDay":"1967-01-01",
			"interest":"唱歌",
			"name":"吴奇隆",
			"professional":"演员",
			"uid":2
		},
		{
			"addr":"大陆",
			"age":45,
			"birthDay":"1977-01-01",
			"interest":"武术",
			"name":"吴京",
			"professional":"演员",
			"uid":3
		},
		{
			"addr":"香港",
			"age":55,
			"birthDay":"1967-01-01",
			"interest":"唱歌",
			"name":"古天乐",
			"professional":"演员",
			"uid":4
		},
		{
			"addr":"大陆",
			"age":35,
			"birthDay":"1987-01-01",
			"interest":"跑步",
			"name":"苏炳添",
			"professional":"运动员",
			"uid":5
		}
	],
	"empty":false,
	"facets":[],
	"first":true,
	"last":false,
	"maxScore":null,
	"number":0,
	"numberOfElements":5,
	"pageable":{
		"offset":0,
		"pageNumber":0,
		"pageSize":5,
		"paged":true,
		"sort":{
			"empty":false,
			"sorted":true,
			"unsorted":false
		},
		"unpaged":false
	},
	"size":5,
	"sort":{"$ref":"$.pageable.sort"},
	"totalElements":8,
	"totalPages":2
}
~~~



#### 1.5 搜索-等值查询-Term

~~~java
@Test
public void search() {

    TermQueryBuilder ageQuery = QueryBuilders.termQuery("age", 45);
	// age = 45
    Iterable<PeopleBean> search = peopleEsDao.search(ageQuery);
    System.out.println("结果：");
    System.out.println(JSON.toJSONString(search, true));
}

~~~

类似SQL：

~~~sql
SELECT * FROM people WHERE age = 45
~~~

结果：

~~~json
[
	{
		"addr":"香港",
		"age":45,
		"birthDay":"1977-01-01",
		"interest":"唱歌",
		"name":"张家辉",
		"professional":"演员",
		"uid":8
	},
	{
		"addr":"香港",
		"age":45,
		"birthDay":"1977-01-01",
		"interest":"赛车",
		"name":"吴彦祖",
		"professional":"演员",
		"uid":1
	},
	{
		"addr":"大陆",
		"age":45,
		"birthDay":"1977-01-01",
		"interest":"武术",
		"name":"吴京",
		"professional":"演员",
		"uid":3
	}
]
~~~



若是查询中文，需要添加 keyword ，term 是指分词后的 = 查询

~~~java
@Test
public void searchChinese() {

    // 添加 keyword 的目的是 以后面查询的值整体查询，不要分词，不然查询不到
    TermQueryBuilder termQuery = QueryBuilders.termQuery("addr.keyword", "大陆");

    System.out.println("参数：");
    System.out.println(termQuery.toString(true));

    Iterable<PeopleBean> search = peopleEsDao.search(termQuery);
    System.out.println("结果：");
    System.out.println(JSON.toJSONString(search, true));
}
~~~



#### 1.6 搜索-多条件

~~~java
@Test
public void searchMore() {


    BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
        .filter(QueryBuilders.termQuery("addr.keyword", "大陆"))
        .filter(QueryBuilders.termQuery("age", 45));

    System.out.println("参数：");
    System.out.println(boolQuery.toString(true));

    Iterable<PeopleBean> search = peopleEsDao.search(boolQuery);
    System.out.println("结果：");
    System.out.println(JSON.toJSONString(search, true));
}

~~~

类似SQL：

~~~sql
SELECT * FROM people WHERE addr = '大陆' AND age = 45
~~~

结果：

~~~json
{
    "addr":"大陆",
    "age":45,
    "birthDay":"1977-01-01",
    "interest":"武术",
    "name":"吴京",
    "professional":"演员",
    "uid":3
}
~~~



#### 1.7 搜索-范围查询



~~~java
@Test
public void searchBetweenAnd() {

    // >= 
    // RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("age").gte(55);
    //  between and
    RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("age").from(55).to(66);

    System.out.println("参数：");
    System.out.println(rangeQuery.toString(true));

    Iterable<PeopleBean> search = peopleEsDao.search(rangeQuery);
    System.out.println("结果：");
    System.out.println(JSON.toJSONString(search, true));

}
~~~

类似SQL：

~~~sql
SELECT * FROM people WHERE age BETWEEN 55 AND 66
~~~

gte >=

gt >

lt < 

lte <=

from ... to ... 类似 between and





#### 1.8 搜索-分页



~~~java
@Test
public void searchSort() {

    BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
        .filter(QueryBuilders.termQuery("professional.keyword", "演员"))
        .filter(QueryBuilders.termQuery("age", 55));
    System.out.println("参数：");
    System.out.println(queryBuilder.toString(true));
    // 分页排序
    PageRequest page = PageRequest.of(0, 5, Sort.by(Order.desc("age"), Order.asc("uid")));
    // 搜索分页查询
    Iterable<PeopleBean> search = peopleEsDao.search(queryBuilder, page);
    System.out.println("结果：");
    System.out.println(JSON.toJSONString(search, true));

}

~~~

类似SQL：

~~~sql
SELECT 
* 
FROM people 
WHERE professional = '演员'
AND age = 55
ORDER BY age desc, uid asc
limit 0,5
~~~



结果：

~~~json
[
	{
		"addr":"大陆",
		"age":55,
		"birthDay":"1967-01-01",
		"interest":"唱歌",
		"name":"吴奇隆",
		"professional":"演员",
		"uid":2
	},
	{
		"addr":"香港",
		"age":55,
		"birthDay":"1967-01-01",
		"interest":"唱歌",
		"name":"古天乐",
		"professional":"演员",
		"uid":4
	}
]
~~~





#### 1.9 搜索-组合查询-特定输出字段



~~~java
@Test
public void searchQuery() {

    NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder()
        .withFields("uid","name","age","professional")
        .withFilter(QueryBuilders.termQuery("professional.keyword", "演员"))
        .withQuery(QueryBuilders.rangeQuery("age").gte(45))
        .withSort(SortBuilders.fieldSort("uid"))
        .withPageable(PageRequest.of(0, 3));

    Iterable<PeopleBean> search = peopleEsDao.search(queryBuilder.build());

    System.out.println("结果：");
    System.out.println(JSON.toJSONString(search, true));

}

~~~

类似SQL：

~~~sql
select 
	uid,name,age,professional
from people
where professional = '演员'
and age >= 45
order by uid
limit 0,3
~~~

结果：

~~~json
[
	{
		"age":45,
		"name":"吴彦祖",
		"professional":"演员",
		"uid":1
	},
	{
		"age":55,
		"name":"吴奇隆",
		"professional":"演员",
		"uid":2
	},
	{
		"age":45,
		"name":"吴京",
		"professional":"演员",
		"uid":3
	}
]
~~~



#### 1.10 搜索-分组





~~~java

@Test
public void searchAggr() {

    NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder()
        .addAggregation(AggregationBuilders.terms("ageCount")
                        .field("age")
                        .size(100)
                        .subAggregation(AggregationBuilders.terms("professionalCount").field("professional.keyword")
                                        .subAggregation(AggregationBuilders.topHits("fieldNms")
                                                        .fetchSource(new String[] {"uid","age","professional","name"}, null))));

    // 分组聚合分页结果
    AggregatedPage<PeopleBean> peopleAggrPage = (AggregatedPage<PeopleBean>)peopleEsDao.search(queryBuilder.build());

    System.out.println("结果：");
    ParsedLongTerms ageAggr = (ParsedLongTerms)peopleAggrPage.getAggregation("ageCount");
    List<? extends Bucket> buckets = ageAggr.getBuckets();
    for (Bucket bucket : buckets) {

        Number age = bucket.getKeyAsNumber();
        long docCount = bucket.getDocCount();

        System.out.println("年龄："+age + " 总数：" + docCount);

        Aggregations aggregations = bucket.getAggregations();
        for (Aggregation aggregation : aggregations) {

            ParsedStringTerms profAggr = (ParsedStringTerms) aggregation;
            List<? extends Bucket> profBuckets = profAggr.getBuckets();
            for (Bucket bucket2 : profBuckets) {
                long profCount = bucket2.getDocCount();
                String prof = bucket2.getKeyAsString();
                System.out.println("\t职业："+prof+" 总数："+profCount);

                Aggregations filedNmAggrs = bucket2.getAggregations();
                for (Aggregation filedNmAggr : filedNmAggrs) {

                    ParsedTopHits topHits = (ParsedTopHits) filedNmAggr ;
                    SearchHits hits = topHits.getHits();
                    for (SearchHit hit : hits) {
                        String source = hit.getSourceAsString();
                        System.out.println("\t\t" + JSON.parseObject(source, PeopleBean.class));
                    }

                }
            }

        }

    }


}

~~~



类似SQL：

~~~sql
select * from people
group by age, professional
~~~





### 二、使用client方式操作ES



依赖还是跟上面一样，配置也是

查询所有：

~~~java

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
	
}
~~~





#### 2.1 等值查询 =



~~~java

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

~~~



类似SQL：

~~~sql
select * from people where age = 45
~~~



URL查询：

~~~json
{
	"query":{
		"term":{
			"age":{
				"boost":1.0,
				"value":45
			}
		}
	}
}
~~~





#### 2.2 多值查询 in



~~~java

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

~~~

类似SQL：

~~~sql
select * from people where professional in ('演员', '歌手')
~~~



SDL：

~~~json
{
	"query":{
		"terms":{
			"professional.keyword":[
				"演员",
				"歌手"
			],
			"boost":1.0
		}
	}
}
~~~



#### 2.3 范围查询



~~~java

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

~~~

类似SQL：

~~~sql
select * from people where age between 50 and 55
~~~



SDL：

~~~json
{
	"query":{
		"range":{
			"age":{
				"include_lower":false,
				"include_upper":true,
				"from":50,
				"boost":1.0,
				"to":55
			}
		}
	}
}
~~~



#### 2.4 模糊查询



~~~java

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

~~~

类似SQL：

~~~sql
select * from people where professional like '武打%'
~~~

SDL:

~~~json
{
	"query":{
		"prefix":{
			"professional.keyword":{
				"boost":1.0,
				"value":"武打"
			}
		}
	}
}
~~~



#### 2.5 通配符模糊查询



~~~java

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

~~~

类似SQL：

~~~sql
select * from people where professional like '%打%员'
~~~

SDL：

~~~json
{
	"query":{
		"wildcard":{
			"professional.keyword":{
				"boost":1.0,
				"wildcard":"*打*员"
			}
		}
	}
}
~~~



#### 2.6 统计查询（最大、最小、平均、求和）



~~~java

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

~~~

类似SQL：

~~~sql
select max(age) as ageMax from people
~~~

SDL:

~~~json
{
	"size":0,
	"aggregations":{
		"ageMax":{
			"max":{
				"field":"age"
			}
		}
	}
}
~~~



#### 2.7 去重



~~~java

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

~~~

类似SQL:

~~~sql
select count(distinct uid) as uidCount from people 
~~~

SDL：

~~~json
{
	"size":0,
	"aggregations":{
		"uidCount":{
			"cardinality":{
				"field":"uid"
			}
		}
	}
}
~~~





#### 2.8 分组聚合



~~~java

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

~~~

l类似SQL：

~~~sql
select age, count(*) from people group by age
~~~

SDL:

~~~json
{
	"size":0,
	"aggregations":{
		"ageGroup":{
			"terms":{
				"shard_min_doc_count":0,
				"field":"age",
				"size":10,
				"show_term_doc_count_error":false,
				"min_doc_count":1,
				"order":[
					{
						"_count":"desc"
					},
					{
						"_key":"asc"
					}
				]
			}
		}
	}
}
~~~



#### 2.9 分组排序



~~~java

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

~~~

类似SQL：

~~~sql
select age as key , count(*) as count 
from people group by age 
order by count asc, key desc
~~~

SDL:

~~~json
{
	"size":0,
	"aggregations":{
		"ageGroup":{
			"terms":{
				"shard_min_doc_count":0,
				"field":"age",
				"size":10,
				"show_term_doc_count_error":false,
				"min_doc_count":1,
				"order":[
					{
						"_count":"asc"
					},
					{
						"_key":"desc"
					}
				]
			}
		}
	}
}
~~~







#### 2.10 多值聚合



~~~java

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

~~~



l类似SQL：

~~~sql
select age , professional, count(*) as count 
from people 
group by age, professional
~~~

SDL:

~~~json
{
	"size":0,
	"aggregations":{
		"ageGroup":{
			"terms":{
				"shard_min_doc_count":0,
				"field":"age",
				"size":10,
				"show_term_doc_count_error":false,
				"min_doc_count":1,
				"order":[
					{
						"_count":"desc"
					},
					{
						"_key":"asc"
					}
				]
			},
			"aggregations":{
				"profGroup":{
					"terms":{
						"shard_min_doc_count":0,
						"field":"professional.keyword",
						"size":10,
						"show_term_doc_count_error":false,
						"min_doc_count":1,
						"order":[
							{
								"_count":"desc"
							},
							{
								"_key":"asc"
							}
						]
					}
				}
			}
		}
	}
}
~~~

