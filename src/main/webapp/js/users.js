$(document).ready(function() {
    var file = $('[name="file"]');
    
    $('#btnUpload').on('click', function() {
    var filename = $.trim(file.val());
        
        if (!(isCSV(filename) )) {
            alert('Please upload CSV files only.');
            return;
        }
    
        $.ajax({
            url: '/users',
            type: "POST",
            data: new FormData(document.getElementById("fileForm")),
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false
          }).done(function(data) {
          	  file.val('');
              alert(data);
          }).fail(function(jqXHR, textStatus) {
              alert('File upload failed .');
          });
        
    });
    
    $('#btnClear').on('click', function() {
        file.val('');
    });
});

function getJsonFromDb() {
        $.ajax({
            url : 'testJson',
            success : function(data) {
                $('#result').html(data);
            }
        });
 }
 
 function isCSV(name) {
    return name.match(/csv$/i)
};
