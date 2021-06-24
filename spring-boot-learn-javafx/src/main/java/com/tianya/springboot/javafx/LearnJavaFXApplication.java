package com.tianya.springboot.javafx;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tianya.springboot.javafx.screen.CustomScreen;
import com.tianya.springboot.javafx.view.MainStageView;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import javafx.stage.Stage;

@SpringBootApplication
public class LearnJavaFXApplication extends AbstractJavaFxApplicationSupport {

	public static void main(String[] args) {
		launch(LearnJavaFXApplication.class, 
				MainStageView.class, 
				new CustomScreen(), 
				args);
	}
	
	
    @Override
    public void start(Stage stage) throws Exception {
        super.start(stage);
        
        // 设置窗口无法改动
        stage.setResizable(false);
    }
}
