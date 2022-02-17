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
		
		//사용자가 업로드한 파일 서버에 저장
		File result_file = fileService.MultipartFile_to_File(file);
		//pdf파일의 텍스트 추출
		String text = pdfService.getText_spire(result_file);
		//정규표현식으로 필요없는 띄어쓰기, 줄바꿈 제거
		text = text.replace(System.lineSeparator(), "");
		text = text.replaceAll("-\\s[0-9]+\\s-", "");
		text = text.replaceAll("\\[[0-9]+\\]", "");
		text = text.replace("                 ", "");
		text = text.replace(", ", ",");
		text = text.replace(". ", ".");
		text = text.replace(",", ", ");
		text = text.replace(".", ". ");
		
		String title = fileService.get_format_time()+filename.substring(0, filename.indexOf(".pdf"));
		
		//쓰레드 생성해서 파이썬에 전달해줄 텍스트 파일 만들기
		FileThread thread1 = new FileThread(title, text);
		
		thread1.start();
		try {
			//파일 만드는 동안 메인 쓰레드 대기
			thread1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("쓰레드 종료 : "+title);
		
		File textfile = new File("python" + File.separator+title+".txt");
		
		
		//파이썬 실행, 요약 텍스트 받아옴
		text = pythonService.execApachePy(textfile);
		
		textfile.delete();
		//json형태로 리턴
		HashMap<String,String> mymap = new HashMap<String,String>();
		mymap.put("title", filename.substring(0, filename.indexOf(".pdf"))+"의 요약본");
		mymap.put("body", text);
		JSONObject data = new JSONObject(mymap);
		

		return data;
	}
	
	//파일 다운로드
	@PostMapping("/download")
	public void download(String title, String body, HttpServletResponse response){

		
		File file = fileService.makePDFFile(title, body);
		downloadService.downloadResult_pdf(title, body, response, file);
	}

}
