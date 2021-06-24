package com.tianya.springboot.javafx.controller;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;

import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

@FXMLController
@SuppressWarnings("all")
public class MainStageController implements Initializable {
	
	
	/**
	 * maven 本地安装地址
	 */
	@FXML
	private TextField mavenHome ;
	
	
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
	
	
	
	
	
	
	
	
	
	public void uploadJar2Remote() {
		
		// 先删除临时文件
		deleteMavenVersion();
		
		
		
		
		
		
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
		
		mavenUploadProcess.appendText("##################");
		mavenUploadProcess.appendText("开始删除临时文件.....");
		Collection<File> listFiles = FileUtils.listFiles(new File(localRepAddr), extensions, true);
		for (File file : listFiles) {
			FileUtils.deleteQuietly(file);
			mavenUploadProcess.appendText("删除临时文件: " + file.getAbsolutePath());
		}
		
		mavenUploadProcess.appendText("删除临时文件完成.....");
		mavenUploadProcess.appendText("删除文件总数: " + listFiles.size());
		mavenUploadProcess.appendText("####################");
	}
	
	
	
	
	
	

    public void initialize(URL location, ResourceBundle resources) {

    }
}
