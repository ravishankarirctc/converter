package com.litepdf.converter.service.impl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.litepdf.converter.service.PptxToPdfConverter;

/**
 * PPTX to PDF converter
 * 
 * @author RAVI
 *
 */
@Service
public class PptxToPdfConverterImpl implements PptxToPdfConverter{
	
	@Autowired
	ServletContext servletContext;
	
	private XSLFSlide[] slides;

	@Override
	public String pptxToPdf(String inPath, String outPath) throws Exception {
		
		
		InputStream inStream = getInFileStream(inPath);
		OutputStream outStream = getOutFileStream(outPath);
		
		
		
		if(inPath.toLowerCase().endsWith("ppt")) {
			
			//for ppt to PDF
			//WordprocessingMLPackage wordMLPackage = getMLPackage(inStream);
			//Docx4J.toPDF(wordMLPackage, outStream);
			
		}else if(inPath.toLowerCase().endsWith("pptx")){
			
			//for pptx to PDF
			Dimension pgsize = processSlides(inStream);
			
			
		    double zoom = 2; // magnify it by 2 as typical slides are low res
		    AffineTransform at = new AffineTransform();
		    at.setToScale(zoom, zoom);

			
			Document document = new Document();

			PdfWriter writer = PdfWriter.getInstance(document, outStream);
			document.open();
			
			for (int i = 0; i < getNumSlides(); i++) {

				BufferedImage bufImg = new BufferedImage((int)Math.ceil(pgsize.width*zoom), (int)Math.ceil(pgsize.height*zoom), BufferedImage.TYPE_INT_RGB);
				Graphics2D graphics = bufImg.createGraphics();
				graphics.setTransform(at);
				//clear the drawing area
				graphics.setPaint(getSlideBGColor(i));
				graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
				try{
					drawOntoThisGraphic(i, graphics);
				} catch(Exception e){
					//Just ignore, draw what I have
				}
				
				Image image = Image.getInstance(bufImg, null);
				document.setPageSize(new Rectangle(image.getScaledWidth(), image.getScaledHeight()));
				document.newPage();
				image.setAbsolutePosition(0, 0);
				document.add(image);
			}
			//Seems like I must close document if not output stream is not complete
			document.close();
			
			//Not sure what repercussions are there for closing a writer but just do it.
			writer.close();
			

		}
		

		return "out_path";
	}
	
	protected Dimension processSlides(InputStream inStream) throws IOException {
		InputStream iStream = inStream;
		XMLSlideShow ppt = new XMLSlideShow(iStream);
		Dimension dimension = ppt.getPageSize();
		slides = ppt.getSlides();
		return dimension;
	}

	protected int getNumSlides() {
		return slides.length;
	}

	protected void drawOntoThisGraphic(int index, Graphics2D graphics) {
		slides[index].draw(graphics);
	}

	protected Color getSlideBGColor(int index) {
		return slides[index].getBackground().getFillColor();
	}

	//////////////////////////////////////////////////

	////////////////// 1
	protected InputStream getInFileStream(String inputFilePath) throws FileNotFoundException {
		File inFile = new File(servletContext.getRealPath(inputFilePath));
		FileInputStream iStream = new FileInputStream(inFile);
		return iStream;
	}

	///////////////////// 2
	protected OutputStream getOutFileStream(String outputFilePath) throws IOException {
		File outFile = new File(servletContext.getRealPath(outputFilePath));

		try {
			// Make all directories up to specified
			outFile.getParentFile().mkdirs();
		} catch (NullPointerException e) {
			// Ignore error since it means not parent directories
		}

		outFile.createNewFile();
		FileOutputStream oStream = new FileOutputStream(outFile);
		return oStream;
	}

}
