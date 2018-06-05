package com.litepdf.converter.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.servlet.ServletContext;

import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.docx4j.Docx4J;
import org.docx4j.convert.in.Doc;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.litepdf.converter.service.DocToPdfConverter;

/**
 * DOC/DOCX to PDF converter
 * 
 * @author RAVI
 *
 */
@Service
public class DocToPdfConverterImpl implements DocToPdfConverter{
	
	@Autowired
	ServletContext servletContext;

	@Override
	public String docToPdf(String inPath, String outPath) throws Exception {
		
		
		InputStream inStream = getInFileStream(inPath);
		OutputStream outStream = getOutFileStream(outPath);
		
		//Resource resource = new ClassPathResource ("WEB-INF\\doctopdf\\1.docx");
		//InputStream inStream = resource.getInputStream();
		
		
		if(inPath.toLowerCase().endsWith("doc")) {
			
			//for doc to PDF
			WordprocessingMLPackage wordMLPackage = getMLPackage(inStream);
			Docx4J.toPDF(wordMLPackage, outStream);
			
		}else if(inPath.toLowerCase().endsWith("docx")){
			
			//for docx to PDF
			XWPFDocument document = new XWPFDocument(inStream);
			PdfOptions options = PdfOptions.create();
			PdfConverter.getInstance().convert(document, outStream, options);
			

		}
		

		return "out_path";
	}
	
	
	

	//////////////////1
	protected InputStream getInFileStream(String inputFilePath) throws FileNotFoundException{
		File inFile = new File(servletContext.getRealPath(inputFilePath));
		FileInputStream iStream = new FileInputStream(inFile);
		return iStream;
	}
	
	/////////////////////2
	protected OutputStream getOutFileStream(String outputFilePath) throws IOException{
		File outFile = new File(servletContext.getRealPath(outputFilePath));
		
		try{
			//Make all directories up to specified
			outFile.getParentFile().mkdirs();
		} catch (NullPointerException e){
			//Ignore error since it means not parent directories
		}
		
		outFile.createNewFile();
		FileOutputStream oStream = new FileOutputStream(outFile);
		return oStream;
	}
	
	/////////////////////////////3
	protected WordprocessingMLPackage getMLPackage(InputStream iStream) throws Exception{
		PrintStream originalStdout = System.out;
		
		//Disable stdout temporarily as Doc convert produces alot of output
		System.setOut(new PrintStream(new OutputStream() {
			public void write(int b) {
				//DO NOTHING
			}
		}));
	
		WordprocessingMLPackage mlPackage = Doc.convert(iStream);
		
		System.setOut(originalStdout);
		return mlPackage;
	}
		
		
		
		
		
		/*
		 * DOCX to PDF converter
		 
		@Override
		public void docxToPdf() {
			try {
				long start = System.currentTimeMillis();
	
				// 1) Load DOCX into XWPFDocument
				InputStream is = new FileInputStream(new File("src/main/resources/2.docx"));
				XWPFDocument document = new XWPFDocument(is);
	
				// 2) Prepare Pdf options
				PdfOptions options = PdfOptions.create();
	
				// 3) Convert XWPFDocument to Pdf
				OutputStream out = new FileOutputStream(new File("may2.pdf"));
				PdfConverter.getInstance().convert(document, out, options);
	
				System.err.println("Generate pdf/HelloWorld.pdf with " + (System.currentTimeMillis() - start) + "ms");
	
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}*/
	}
