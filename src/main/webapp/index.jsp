<%@page language="java" contentType="text/html; charset=utf-8" %>
<html>
<body>
<h2>tomcat1!</h2>
<h2>tomcat1!</h2>
<h2>tomcat1!</h2>
<h2>Hello World!</h2>
<%request.setCharacterEncoding("utf-8");%>
<h2>Spring MVC 文件上传</h2>
<form name="form1" action="/manage/product/upload.do" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file" />
    <input type="submit" value="Spring MVC上传文件" />
</form>

<h2>富文本文件上传</h2>
<form name="form2" action="/manage/product/richtext_img_upload.do" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file" />
    <input type="submit" value="富文本上传文件" />
</form>
</body>
</html>
