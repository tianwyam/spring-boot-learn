package com.tianya.word.utils;

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
	
	
	public static void main(String[] args) {
		
		String wordFilePath = "M:\\test.doc" ;
		String pdfFilePath = "M:\\w2p.pdf" ;
		word2pdf(wordFilePath, pdfFilePath);
		
	}
	
}
