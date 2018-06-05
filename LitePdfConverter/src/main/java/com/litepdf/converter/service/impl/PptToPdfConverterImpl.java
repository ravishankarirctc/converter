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

import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.litepdf.converter.service.PptToPdfConverter;

/**
 * PPT/PPTX to PDF converter
 * 
 * @author RAVI
 *
 */
@Service
public class PptToPdfConverterImpl implements PptToPdfConverter{
	
	@Autowired
	ServletContext servletContext;
	
	private Slide[] slides;

	@Override
	public String pptToPdf(String inPath, String outPath) throws Exception {
		
		
		InputStream inStream = getInFileStream(inPath);
		OutputStream outStream = getOutFileStream(outPath);
		
		
		
		
			
			//for ppt to PDF
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
			

		

		return "out_path";
	}
	

	protected Dimension processSlides(InputStream inStream) throws IOException{

		SlideShow ppt = new SlideShow(inStream);
		Dimension dimension = ppt.getPageSize();
		slides = ppt.getSlides();
		return dimension;
	}
	
	protected int getNumSlides(){
		return slides.length;
	}
	
	protected void drawOntoThisGraphic(int index, Graphics2D graphics){
		slides[index].draw(graphics);
	}
	
	protected Color getSlideBGColor(int index){
		return slides[index].getBackground().getFill().getForegroundColor();
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
