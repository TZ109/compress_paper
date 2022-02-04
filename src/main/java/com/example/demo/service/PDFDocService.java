package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PDFDocService {

	public String getText(File result_file )
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
