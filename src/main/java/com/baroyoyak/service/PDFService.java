package com.baroyoyak.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import com.baroyoyak.service.pdf.GetLinesFromPDF;
import com.spire.pdf.*;
import com.spire.pdf.exporting.text.SimpleTextExtractionStrategy;
import com.spire.pdf.widget.PdfPageCollection;


@Service
public class PDFService {

	public String getText(File result_file)
	{
		
		String text="";
		
		//if(file!=null && file.getOriginalFilename().contains(".pdf"))
		{
			try {
				
				
				BufferedInputStream fi = new BufferedInputStream(new FileInputStream(result_file));
				
				
				
				PDDocument pdfDoc = PDDocument.load(fi);
				
				
				PDFTextStripper stripper = new GetLinesFromPDF();
				stripper.setSortByPosition(true);
				stripper.setStartPage(0);
				stripper.setEndPage(pdfDoc.getNumberOfPages());
				text = stripper.getText(pdfDoc);
				
				Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
				
				stripper.writeText(pdfDoc, dummy);
				pdfDoc.close();
				fi.close();
				//System.out.println(GetLinesFromPDF.lines);
				
				for(String line:GetLinesFromPDF.lines){
	                text+=line;
	            }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//result_file.delete();
		
		return text;
	}
	
	public String getText_spire(File result_file )
	{
		
		String text="";
		
		//if(file!=null && file.getOriginalFilename().contains(".pdf"))
		{
			try {
				System.out.println("파일이름 : "+result_file.getName());
				PdfDocument doc = new PdfDocument();
				doc.loadFromFile("temp"+File.separator+result_file.getName());
				PdfPageCollection pages = doc.getPages();
				
				StringBuilder sb=new StringBuilder();

				
				for(int i=0; i<pages.getCount();i++)
				{
					PdfPageBase page = pages.get(i);
					//SimpleTextExtractionStrategy strategy = new SimpleTextExtractionStrategy();
					text += page.extractText(true);
					sb.append(text);
				}
				
			FileWriter writer = new FileWriter("temp/result.txt");
				
			writer.write(sb.toString());
				
			writer.flush();

			doc.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		result_file.delete();
		
		return text;
	}
	
	public String getText_ver2(File result_file )
	{
		
		String text="";
		
		//if(file!=null && file.getOriginalFilename().contains(".pdf"))
		{
			try {
				
				PDFParser parser = new PDFParser(new RandomAccessFile(result_file,"r"));
				parser.parse();
				COSDocument cosDoc = parser.getDocument();
				PDFTextStripper pdfStripper = new PDFTextStripper();
				
				PDDocument pdDoc = new PDDocument(cosDoc);
				text=pdfStripper.getText(pdDoc);
				
				pdDoc.close();
				//System.out.println(text);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		result_file.delete();
		
		return text;
	}

	public String getText_origin(File result_file )
	{
		
		String text="";
		
		//if(file!=null && file.getOriginalFilename().contains(".pdf"))
		{
			try {
				PDDocument pdfDoc = PDDocument.load(result_file);
				text = new PDFTextStripper().getText(pdfDoc);
				pdfDoc.close();
				//System.out.println(text);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		result_file.delete();
		
		return text;
	}
	
}
