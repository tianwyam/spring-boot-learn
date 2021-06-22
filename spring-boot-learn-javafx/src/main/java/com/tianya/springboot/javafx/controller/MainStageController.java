package com.tianya.springboot.javafx.controller;

import java.net.URL;
import java.util.ResourceBundle;

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
		
		
		
		
		
		
		
		
	}
	
	
	

    public void initialize(URL location, ResourceBundle resources) {

    }
}
