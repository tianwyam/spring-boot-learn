package com.tianya.pdf;

import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @description
 *	PDF操作工具类
 * @author TianwYam
 * @date 2019年12月28日下午8:44:38
 */
public class PdfUtils {
	
	
	/**
	 * @description
	 *	给PDF文件添加页码
	 * @author TianwYam
	 * @date 2019年12月28日下午8:48:55
	 * @param orgPdfPath 源PDF文件路径
	 * @param outputPdfPath 加了页码的PDF文件路径
	 * @return 返回添加了页码的PDF文件路径
	 */
	public static String addPageNum (String orgPdfPath, String outputPdfPath) {
		
		try (
			// 输出文件 流
			FileOutputStream fos = new FileOutputStream(outputPdfPath) ;){
			
			// 新建文档，默认A4大小
			Document document = new Document(PageSize.A4);
			PdfWriter writer = PdfWriter.getInstance(document, fos);
			// 设置页面监听事件，必须在open方法前
			writer.setPageEvent(new PageNumPdfPageEvent());
			document.open();
			
			// PDF内容体
			PdfContentByte pdfContent = writer.getDirectContent();
			// 读取 源PDF文件，进行一页一页复制，才能触发 添加页码的  页面监听事件
			PdfReader reader = new PdfReader(orgPdfPath);
			// 获取 源文件总页数
			int num = reader.getNumberOfPages();
			System.out.println("总页数：" + num);
			// 页面数是从1开始的 
			for (int i = 1; i <= num; i++) {
				document.newPage();
				// 设置空页码进行展示
				writer.setPageEmpty(false);
				PdfImportedPage page = writer.getImportedPage(reader, i);
				// 复制好的页面，添加到内容去，触发事件监听
				pdfContent.addTemplate(page, 0, 0);
			}
			
			document.close();
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return outputPdfPath ;
	}
	

}
