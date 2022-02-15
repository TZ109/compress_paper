package com.baroyoyak.service.pdf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

public class GetLinesFromPDF extends PDFTextStripper {

	
	public static List<String> lines = new ArrayList<String>();
	
	public GetLinesFromPDF() throws IOException {
		// TODO Auto-generated constructor stub
	}

	@Override
    protected void writeString(String str, List<TextPosition> textPositions) throws IOException {
        lines.add(str);
        // you may process the line here itself, as and when it is obtained
    }
	
}
