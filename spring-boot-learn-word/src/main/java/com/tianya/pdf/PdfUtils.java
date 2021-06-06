package com.tianya.pdf;

import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
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
	
	

	
	/**
	 * @description
	 *	给PDF文件添加页码及总页数
	 * @author TianwYam
	 * @date 2019年12月28日下午8:48:55
	 * @param orgPdfPath 源PDF文件路径
	 * @param outputPdfPath 加了页码的PDF文件路径
	 * @return 返回添加了页码的PDF文件路径
	 */
	public static String addPageNumAndTotal(String orgPdfPath, String outputPdfPath) {
		
		try (
			// 输出文件 流
			FileOutputStream fos = new FileOutputStream(outputPdfPath) ;){
			
			// 读取 源PDF文件，进行一页一页复制，才能触发 添加页码的  页面监听事件
			PdfReader reader = new PdfReader(orgPdfPath);
			// 获取 源文件总页数
			int num = reader.getNumberOfPages();
			System.out.println("总页数：" + num);
			
			// 新建文档，默认A4大小
			Document document = new Document(PageSize.A4);
			PdfWriter writer = PdfWriter.getInstance(document, fos);
			// 设置页面监听事件，必须在open方法前
			// 设置有总页数的 页面监听事件
			writer.setPageEvent(new PageNumAndTotalPdfPageEvent(num));
			document.open();
			
			// PDF内容体
			PdfContentByte pdfContent = writer.getDirectContent();
			
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
			
			// 字体 来源于 itext-asian JAR包
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
				// 具体位置 内容 旋转多少度 共360度
				content.showTextAligned(Element.ALIGN_CENTER, "机密文件", 300, 350, 300);
				content.showTextAligned(Element.ALIGN_TOP, "机密文件", 100, 100, 5);
				content.showTextAligned(Element.ALIGN_BOTTOM, "机密文件", 400, 400, 75);
				
				content.endText();
			}
			
			// 关闭
			stamper.close();
			reader.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	public static void main(String[] args) {
		
		
//		addWaterMark("E:\\javaWork\\test.pdf", "E:\\javaWork\\test2.pdf");
		addPageNumAndTotal("E:\\javaWork\\test.pdf", "E:\\javaWork\\test2page.pdf");
	}
	
	
	
	
	
	

}
