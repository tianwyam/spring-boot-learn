package com.tianya.springboot.javafx.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.tianya.springboot.javafx.entity.MavenJarBean;

import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

@FXMLController
@SuppressWarnings("all")
public class MainStageController implements Initializable {
	
	
	private static final ExecutorService EXECUTOR_SERVER = Executors.newFixedThreadPool(30);
	private static final Runtime CMD = Runtime.getRuntime();
	
	/**
	 * maven 本地安装地址
	 */
	@FXML
	private TextField mavenHome ;
	
	
	/**
	 * maven 远程仓库ID
	 */
	@FXML
	private TextField mavenRepoId ;
	
	/**
	 * maven 远程仓库地址
	 */
	@FXML
	private TextField mavenRemoteRepoAddr ;
	
	/**
	 * maven 远程仓库 user
	 */
	@FXML
	private TextField mavenRemoteRepoUser ;
	
	/**
	 * maven 远程仓库 password
	 */
	@FXML
	private TextField mavenRemoteRepoPassword ;
	
	
	/**
	 * maven 本地仓库地址
	 */
	@FXML
	private TextField mavenLocalRepoAddr ;
	
	/**
	 * maven 上传进程
	 */
	@FXML
	private TextArea mavenUploadProcess ;
	
	
	public void usage() {
		
		mavenUploadProcess.appendText("\n>>> 注意事项");
		mavenUploadProcess.appendText("\n##################");
		mavenUploadProcess.appendText("\n使用此工具时必须满足的前提条件：");
		mavenUploadProcess.appendText("\n(1)本地必须有Java环境、JAVA_HOME等");
		mavenUploadProcess.appendText("\n(2)本地必须有maven环境 需要配置环境变量");
		mavenUploadProcess.appendText("\n##################\n");
	}
	
	
	
	public void uploadJar2Remote() {
		
		// 清空内容
		mavenUploadProcess.setText(null);
		
		// 提示
		usage();
		
		// 采用子线程更新UI界面
		new Thread(()-> {
			
			// 检查参数
			if (!paramCheck()) {
				return ;
			}
			
			// 先删除临时文件
			deleteMavenVersion();
			
			// 上传
			deploy();
			
		}).start();
		
	}
	
	
	
	
	public void deploy() {
		
		// 本地仓库地址
		String localRepAddr = mavenLocalRepoAddr.getText();
		// 上传私服
		upload(new File(localRepAddr));
	}
	

	public void upload(File localRepoDir) {
		
		//  获取文件夹列表
		File[] listFiles = localRepoDir.listFiles();
		if (listFiles.length > 0) {
			// 目录
			if (listFiles[0].isDirectory()) {
				for (File file : listFiles) {
					if (file.isDirectory()) {
						upload(file);
					}
				}
			} else if (listFiles[0].isFile()) {
				
				MavenJarBean jar = new MavenJarBean();
				for (File file : listFiles) {
					
					String name = file.getName();
					if (name.endsWith(".pom")) {
						jar.setPomFile(file);
					}else if (name.endsWith("-javadoc.jar")) {
						jar.setJavadocFile(file);
					}else if (name.endsWith("-sources.jar")) {
						jar.setSourceFile(file);
					}else if (name.endsWith(".jar")) {
						jar.setJarFile(file);
					}
					
				}
				
				// 上传JAR包
				uploadJar(jar);
			}
			
			
		}
		
		
	}
	
	
	public void uploadJar(MavenJarBean jar) {
		
		String baseCmd = " cmd /c mvn -s %s deploy:deploy-file -Durl=%s -DrepositoryId=%s -DgeneratePom=false " ;
		
		String winUploadJarCmd = String.format(baseCmd, 
				mavenHome.getText() + "/conf/setting.xml",
				mavenRemoteRepoAddr.getText(),
				mavenRepoId.getText());
		
		EXECUTOR_SERVER.execute(()->{
			
			
			String cmd = jar.cmd();
			
			try {
				Process proc = CMD.exec(winUploadJarCmd + cmd);
				List<String> readLines = IOUtils.readLines(proc.getErrorStream(), Charset.defaultCharset());
				if (readLines != null && readLines.size() > 0) {
					String process = readLines.stream()
							.map(str -> Thread.currentThread().getName() + " >> : " + str)
							.collect(Collectors.joining("\n"));
					mavenUploadProcess.appendText(process);
				}
				proc.waitFor();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		});
		
	}
	
	
	
	public boolean paramCheck() {
		

		// 本地maven地址
		String mavenHomeAddr = mavenHome.getText();
		if (StringUtils.isBlank(mavenHomeAddr)) {
			mavenUploadProcess.appendText("\n本地maven安装地址为空，请重新设置\n");
			return false;
		}
		
		// 远程仓库地址
		String remoteRepo = mavenRemoteRepoAddr.getText();
		if (StringUtils.isBlank(remoteRepo)) {
			mavenUploadProcess.appendText("\n远程仓库地址为空，请重新设置\n");
			return false;
		}
		
		// 仓库repoId
		String repoId = mavenRepoId.getText();
		if (StringUtils.isBlank(repoId)) {
			mavenUploadProcess.appendText("\n仓库ID为空，请重新设置\n");
			return false;
		}
		
		// 本地仓库地址
		String localRepAddr = mavenLocalRepoAddr.getText();
		if (StringUtils.isBlank(localRepAddr)) {
			mavenUploadProcess.appendText("\n本地仓库地址为空，请重新设置\n");
			return false;
		}
		
		return true ;
	}
	
	
	
	
	
	/**
	 * @description
	 *	删除临时文件
	 * @author TianwYam
	 * @date 2021年6月24日下午4:49:15
	 */
	public void deleteMavenVersion() {
		
		// 本地仓库地址
		String localRepAddr = mavenLocalRepoAddr.getText();
		
		// 后缀扩展
		String[] extensions = {
				"properties","lastUpdated","repositories"
		};
		
		mavenUploadProcess.appendText("\n##################\n");
		mavenUploadProcess.appendText("\n开始删除临时文件.....\n");
		Collection<File> listFiles = FileUtils.listFiles(new File(localRepAddr), extensions, true);
		for (File file : listFiles) {
			FileUtils.deleteQuietly(file);
			mavenUploadProcess.appendText("\n删除临时文件: " + file.getAbsolutePath());
		}
		
		mavenUploadProcess.appendText("\n删除临时文件完成.....\n");
		mavenUploadProcess.appendText("\n删除文件总数: " + listFiles.size());
		mavenUploadProcess.appendText("\n####################\n");
	}
	
	
	

    public void initialize(URL location, ResourceBundle resources) {

    }
}
