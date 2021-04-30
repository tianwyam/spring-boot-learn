package com.tianya.word.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.xmlbeans.XmlObject;

import com.microsoft.schemas.vml.CTShape;
import com.spire.doc.Document;
import com.spire.doc.FileFormat;

/**
 * @description
 *	使用spire.office.free操作word文档
 *	<p>免费版本只能转前10页</p>
 * @author TianwYam
 * @date 2020年3月25日下午10:34:43
 */
public class Word2PdfUtils {

	/**
	 * @description
	 *	word转PDF操作
	 * @author TianwYam
	 * @date 2020年3月25日下午10:40:57
	 * @param wordFilePath word文档的文件路径及名称
	 * @param pdfFilePath 输出的PDF文件目录及名称
	 */
	public static void word2pdf(String wordFilePath, String pdfFilePath) {
		Document document = new Document();
		document.loadFromFile(wordFilePath, FileFormat.Doc);
		document.saveToFile(pdfFilePath, FileFormat.PDF);
	}
	
	
	
	/**
	 * @description
	 *	给word文档添加水印
	 * @author TianwYam
	 * @date 2021年4月29日下午6:01:13
	 * @param wordFilePath
	 * @param outputFilePath
	 */
	public static void addWaterMark(String wordFilePath, String outputFilePath) {

		// XWPF 的 对word的操作 .docx
		try (XWPFDocument document = new XWPFDocument(new FileInputStream(wordFilePath));) {
			
			XWPFHeaderFooterPolicy headerFooterPolicy = document.getHeaderFooterPolicy();
			if (headerFooterPolicy == null) {
				headerFooterPolicy = document.createHeaderFooterPolicy();
			}

			headerFooterPolicy.createWatermark("私人文件");
			
			XWPFHeader header = headerFooterPolicy.getHeader(XWPFHeaderFooterPolicy.DEFAULT);
			List<XWPFParagraph> paragraphs = header.getParagraphs();
			for (XWPFParagraph xwpfParagraph : paragraphs) {
				
				XmlObject[] xmlobjects = xwpfParagraph.getCTP().getRArray(0).getPictArray(0).selectChildren(
						new QName("urn:schemas-microsoft-com:vml", "shape"));
				if (xmlobjects.length > 0) {
					CTShape ctshape = (CTShape) xmlobjects[0];
					ctshape.setFillcolor("#d8d8d8");
					ctshape.setStyle(ctshape.getStyle() + ";rotation:315");
				}
			}

			document.write(new FileOutputStream(outputFilePath));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	
	public static void main(String[] args) {
		
		String wordFilePath = "M:\\wordTest.docx" ;
//		String pdfFilePath = "M:\\w2p.pdf" ;
//		word2pdf(wordFilePath, pdfFilePath);
		
		String outputFilePath = "M:\\wordTest22.docx" ;
		addWaterMark(wordFilePath, outputFilePath);
		
	}
	
}
