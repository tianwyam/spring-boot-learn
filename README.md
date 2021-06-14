# spring-boot-learn



<br/>



是基于maven多模块工程来记录学习springboot的知识的一个过程



<br/>



目录：

- [spring-boot-learn](#spring-boot-learn)

  - [spring-boot-learn-jwt](#spring-boot-learn-jwt)

    - [JWT简单使用](#JWT简单使用)

  - [spring-boot-learn-freemarker](#spring-boot-learn-freemarker)

    - [freemarker实现转word](#freemarker实现转word)

  - [spring-boot-learn-jsp](#spring-boot-learn-jsp)

    - [集成JSP](#集成JSP)

  - [spring-boot-learn-word](#spring-boot-learn-word)

    - [给PDF文件添加文本水印](#给PDF文件添加文本水印)

  - [spring-boot-learn-feign](#spring-boot-learn-feign)

    - [第一种，在springboot环境下](#第一种，在springboot环境下)
    - [第二种，不在spring环境下](#第二种，不在spring环境下)

  - [spring-boot-learn-capture-screen](#spring-boot-learn-capture-screen)
      - [获取屏幕截屏](#获取屏幕截屏)
      - [服务端推送](#服务端推送)

  

  

  





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



### 第一种，在springboot环境下



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



### 第二种，不在spring环境下



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







