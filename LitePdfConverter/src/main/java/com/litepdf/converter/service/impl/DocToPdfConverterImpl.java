package com.litepdf.converter.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.litepdf.converter.service.DocToPdfConverter;

/**
 * DOC/DOCX to PDF converter using Apache POIs
 * 
 * @author RAVI
 *
 */
public class DocToPdfConverterImpl implements DocToPdfConverter{
	
	/*
	 * DOCX to PDF converter
	 */
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
	}
}
