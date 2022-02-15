package com.baroyoyak.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Service;

@Service
public class PythonService {

	public String execPython(File text)
	{
		String line = "";
		String result = "요약결과 : ";
		String execPath = "python python/test.py \""+text.getAbsolutePath()+"\"";
		try {
			Process process = Runtime.getRuntime().exec(execPath);
			BufferedReader input =new BufferedReader(new InputStreamReader(process.getInputStream(), "euc-kr"));
			 
	        while ((line = input.readLine()) !=null) {
	            //System.out.println(line);
	            result+=line;
	        }
	        
	        input.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return result;
	}
	
	public String executePython(String text)
	{
		String result="";
		
		 // TODO Auto-generated method stub
	      try {
	      String command = "C:\\Users\\tz1009\\AppData\\Local\\Programs\\Python\\Python310\\python.exe";  // 명령어
	      String arg1 = "python/test.py"; // 인자
	      String arg2 = "C:\\\\JAVA\\\\baroyoyak\\\\python\\\\test.txt";
	      ProcessBuilder builder = new ProcessBuilder(command, arg1,arg2);
	      Process process = builder.start();
	      int exitVal = process.waitFor();  // 자식 프로세스가 종료될 때까지 기다림
	      BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "euc-kr")); // 서브 프로세스가 출력하는 내용을 받기 위해
	      String line;
	      while ((line = br.readLine()) != null) {
	           System.out.println(">>>  " + line); // 표준출력에 쓴다
	           result+=line;
	      }
	      if(exitVal != 0) {
	        // 비정상 종료
	        System.out.println("서브 프로세스가 비정상 종료되었다.");
	      }
	      }catch(Exception e) {
	         System.out.println(e.getMessage());
	      }
	      finally
	      {
	    	  //textfile.delete();
	      }
		
		return result;
	}
}
