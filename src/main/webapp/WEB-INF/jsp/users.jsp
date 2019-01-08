<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<body>

<h1>Spring Boot Basic File Upload and Read</h1>


<form id="fileForm">
    <input type="file" name="file" />
    <span id="upload-error" class="error">${uploadError}</span><br/><br/>
    <button id="btnUpload" type="button">Upload file</button> 
    <button id="btnClear" type="button">Clear</button>
    <br/><br/>
</form>
<br/>

<hr/>

<button id="clickMe" type="button" value="Get JSON" onclick="getJsonFromDb();" >Display Data </button>
<br/>
<label id="result"></label
<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="http://malsup.github.com/jquery.form.js"></script> 
<script src="/js/users.js" type="text/javascript"></script>    


</body>
</html>