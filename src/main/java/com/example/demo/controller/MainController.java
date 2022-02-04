package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.DownloadService;
import com.example.demo.service.FileService;
import com.example.demo.service.PDFDocService;

@Controller
public class MainController {
	
	@Autowired
	FileService fileService;
	@Autowired
	PDFDocService pdfDocService;
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
		String text = pdfDocService.getText(result_file);
		
		HashMap<String,String> mymap = new HashMap<String,String>();
		mymap.put("title", filename.subSequence(0, filename.indexOf(".pdf"))+"의 요약본");
		mymap.put("body", text);
		JSONObject data = new JSONObject(mymap);
		
		return data;
	}
	
	
	@PostMapping("/download")
	public void download(String title, String body, HttpServletResponse response){
		//System.out.println("body : "+body);
		File file = fileService.String_to_File(title, body);
		downloadService.downloadResult(title, body, response, file);
	}

}
