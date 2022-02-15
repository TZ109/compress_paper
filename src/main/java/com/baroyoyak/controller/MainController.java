package com.baroyoyak.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.baroyoyak.service.DownloadService;
import com.baroyoyak.service.FileService;
import com.baroyoyak.service.PDFService;



@Controller
public class MainController {
	
	@Autowired
	FileService fileService;
	@Autowired
	PDFService pdfService;
	@Autowired
	DownloadService downloadService;

	@RequestMapping(path = {"","/"}, method = RequestMethod.GET)
	public String main()
	{
		return "main";
	}
	
	@ResponseBody
	@PostMapping(value = { "/upload" }, consumes = { "multipart/form-data" })
	public JSONObject upload(@RequestParam(value="file", required=true) MultipartFile file) throws IOException
	{
		String filename = file.getOriginalFilename();
		
		File result_file = fileService.MultipartFile_to_File(file);
		String text = pdfService.getText_spire(result_file);
		
		text = text.replace(System.lineSeparator(), "");
		text = text.replaceAll("-\\s[0-9]+\\s-", "");
		text = text.replaceAll("\\[[0-9]+\\]", "");
		text = text.replace("                 ", "");
		text = text.replace(", ", ",");
		text = text.replace(". ", ".");
		text = text.replace(",", ", ");
		text = text.replace(".", ". ");
		
		HashMap<String,String> mymap = new HashMap<String,String>();
		mymap.put("title", filename.subSequence(0, filename.indexOf(".pdf"))+"의 요약본");
		mymap.put("body", text);
		JSONObject data = new JSONObject(mymap);
		
		
		
		return data;
	}
	
	
	@PostMapping("/download")
	public void download(String title, String body, HttpServletResponse response){
		//System.out.println("body : \n"+body);
		//File file = fileService.makeHWPFile(title, body);
		
		File file = fileService.makePDFFile(title, body);
		downloadService.downloadResult_pdf(title, body, response, file);
	}

}
