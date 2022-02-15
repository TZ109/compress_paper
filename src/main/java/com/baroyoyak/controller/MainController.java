package com.baroyoyak.controller;

import java.io.File;
import java.io.IOException;
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
import com.baroyoyak.service.PythonService;
import com.baroyoyak.thread.FileThread;



@Controller
public class MainController {
	
	String lock = "lock";
	
	@Autowired
	FileService fileService;
	@Autowired
	PDFService pdfService;
	@Autowired
	DownloadService downloadService;
	@Autowired
	PythonService pythonService;

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
		
		String title = fileService.get_format_time()+filename.substring(0, filename.indexOf(".pdf"));
		
		FileThread thread1 = new FileThread(title, text);
		
		thread1.start();
		try {
			thread1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("쓰레드 종료 : "+title);
		
		File textfile = new File("python" + File.separator+title+".txt");
		
		text = pythonService.execPython(textfile);
		
		textfile.delete();
		
		HashMap<String,String> mymap = new HashMap<String,String>();
		mymap.put("title", filename.substring(0, filename.indexOf(".pdf"))+"의 요약본");
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
