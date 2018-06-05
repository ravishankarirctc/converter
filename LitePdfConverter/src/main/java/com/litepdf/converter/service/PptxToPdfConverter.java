package com.litepdf.converter.service;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 
 * @author RAVI
 *
 */
public interface PptxToPdfConverter {
	/**
	 * PPTX to PDF
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 * @throws Exception 
	 */
	
	public String pptxToPdf(String inPath, String outPath) throws FileNotFoundException, IOException, Exception;

}
