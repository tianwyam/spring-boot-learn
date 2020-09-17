# spring-boot-learn



是基于maven多模块工程来记录学习springboot的知识的一个过程



目录：

- ## spring-boot-learn-word

  针对word的操作：

  - spire.doc.free 简单示例
  - itextpdf实现对生成后的PDF添加页码示例

  

- ## spring-boot-learn-excel

  针对excel的操作：

  - hutool工具实现对excel导入导出的示例



- ## spring-boot-learn-crawler-webmagic

  简单学习WebMagic，是一个简单灵活的Java爬虫框架

  - 使用webmagic爬取了笔趣阁小说类别
  - 爬取了笔趣阁小说《剑来》目录



- ## spring-boot-learn-mybatis

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



- ## spring-boot-learn-capture-screen
  使用服务端推送技术SSE+屏幕截屏，实现一个简单的屏幕共享功能
  - SseEmitter 实现服务端推送功能
  - java.awt.Toolkit 获取屏幕截屏

**获取屏幕截屏**

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



**服务端推送**

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

