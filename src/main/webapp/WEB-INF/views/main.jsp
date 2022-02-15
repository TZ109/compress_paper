<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Document</title>
    
</head>

<script>
	function btn_upload()
	{
		const upload_file = document.getElementById('upload_file');
		var progress_btn = document.getElementById('progress_btn');
		upload_file.click();

		//upload_file.addEventListener('change',(event)=>{
		//	const fileList = event.target.files;
		//	console.log(fileList[0]);
		//})
		
		
	}
	
	function btn_upload_change()
	{
		var upload_btn = document.getElementById("upload_btn");
		
		//console.log($('#upload_file')[0].files[0])
		if(document.getElementById("upload_file").value)
		{
			var form = document.getElementById('upload_file');
			var uploadfile = $('#upload_file')[0].files[0];
			
			var formData = new FormData();
			formData.append("file", uploadfile);
			//console.log(formData)
			
			if(!uploadfile.name.includes(".pdf"))
			{
				alert("pdf형식의 문서만 업로드 가능합니다.")
				return false;
			}
			
			var paper_compressed = document.getElementById('paper_compressed');
			var result_title = document.getElementById('result_title');
			var result_body = document.getElementById('result_body');
			
	        $.ajax({
				type : 'POST',
				url: "/upload",
				data: formData,
				 dataType: "json",
				enctype: 'multipart/form-data',
				processData:false,
				contentType:false,
				
				success: function(res){
					progress_btn.innerText = "완료";
					result_title.innerText = res.title;
					result_body.innerText = res.body;
					paper_compressed.style.display = 'block';
					$('#progress_btn').toggleClass('btn-warning');
					$('#progress_btn').toggleClass('btn-secondary');
					upload_btn.disabled = false;
				},
				error: function(err){
					alert("Error : "+err)
				}
				});
				
			progress_btn.innerText = "처리중";
			$('#progress_btn').toggleClass('btn-secondary');
			$('#progress_btn').toggleClass('btn-warning');
			upload_btn.disabled = true;
		}
		
		
	}
	
	function download_file()
	{
		var result_title = document.getElementById('result_title');
		var result_body = document.getElementById('result_body');
		var down_title = document.getElementById('down_title');
		var down_body = document.getElementById('down_body');
		
		down_title.value=result_title.innerText;
		down_body.value=result_body.innerText;
		
		$('#download_submit').submit();
	}
	
</script>

<body>

    <div class="container-fluid px-0">
        <div class="row flex-row justify-content-center">
            <div class="col-12 col-md-6 col-lg-5 mt-2">
                <div class="d-flex flex-row justify-content-between align-items-center px-2">
                    <img src="/img/bi-08.png" alt="로고">
                    <div class="d-flex flex-row">
                        <button class="btn btn-primary rounded me-2" onclick="btn_upload()" id="upload_btn">업로드</button>
                        <button class="btn btn-secondary rounded text-white" disabled id="progress_btn">상태</button>
                    </div>
                    
                    <input type="file" id="upload_file" style="display:none" required onchange="btn_upload_change()">
                    
                    
                </div>
                
                <p class="px-3">원하시는 특허 문서를 업로드 해주세요.</p>
            </div>
        </div>
        <div class="row flex-row justify-content-center">
            <div class="col-12 col-md-6 col-lg-5 mt-2" style="display:none" id="paper_compressed">

                
	                <p class="px-3">요약 결과!</p>
	                <div class="border border-dark mx-3 mb-2 px-1 rounded-2" id="result_title"
	                        style="min-height:30px; max-height:70px; font-size:14px;"></div>
	                <div class="border border-dark mx-3 px-1 rounded-2 mb-1" id="result_body"
	                        style="min-height:150px; max-height:400px; overflow-x :hidden; overflow-y :scroll; font-size:12px;">
	                </div>
            	<div class="d-flex flex-row mx-3">
	                <div class="btn btn-primary me-2" onclick="download_file()">다운로드</div>
	                <div class="btn btn-secondary " onclick="location.reload()">취소</div>
                </div>
            </div>
            
            <form method="post" action="/download" id="download_submit">
           		<input type="hidden" name ="title" id="down_title">
				<input type="hidden" name="body" id="down_body">
            </form>
            
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>