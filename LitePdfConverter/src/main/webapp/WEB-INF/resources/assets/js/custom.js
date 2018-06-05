angular.module('app', [], function() {})
FileUploadCtrl.$inject = ['$scope']
function FileUploadCtrl(scope) {
   var dropbox = document.getElementById("dropbox")
    scope.dropText = 'Drop files here...'
    	scope.uploadVisible = true

    // init event handlers
    function dragEnterLeave(evt) {
        evt.stopPropagation()
        evt.preventDefault()
        scope.$apply(function(){
            scope.dropText = 'Drop files here...'
            scope.dropClass = ''
        })
    }
    dropbox.addEventListener("dragenter", dragEnterLeave, false)
    dropbox.addEventListener("dragleave", dragEnterLeave, false)
    dropbox.addEventListener("dragover", function(evt) {
        evt.stopPropagation()
        evt.preventDefault()
        var clazz = 'not-available'
        var ok = evt.dataTransfer && evt.dataTransfer.types && evt.dataTransfer.types.indexOf('Files') >= 0
        scope.$apply(function(){
            scope.dropText = ok ? 'Drop files here...' : 'Only files are allowed!'
            scope.dropClass = ok ? 'over' : 'not-available'
        })
    }, false)
    dropbox.addEventListener("drop", function(evt) {
        console.log('drop evt:', JSON.parse(JSON.stringify(evt.dataTransfer)))
        evt.stopPropagation()
        evt.preventDefault()
        scope.$apply(function(){
            scope.dropText = 'Drop files here...'
            scope.dropClass = ''
        })
        var files = evt.dataTransfer.files
        if (files.length > 0) {
            scope.$apply(function(){
                scope.files = []
                for (var i = 0; i < files.length; i++) {
                    scope.files.push(files[i])
                }
            })
        }
    }, false)
    //============== DRAG & DROP =============

    scope.setFiles = function(element) {
    scope.$apply(function(scope) {
      console.log('files:', element.files);
      // Turn the FileList object into an Array
        scope.files = []
        for (var i = 0; i < element.files.length; i++) {
          scope.files.push(element.files[i])
        }
      scope.progressVisible = false
      });
    };

    scope.uploadFile = function() {
        var fd = new FormData()
        for (var i in scope.files) {
            fd.append("file", scope.files[i])
        }
        var href = location.href;
        fd.append("type", convertType)
        var xhr = new XMLHttpRequest()
        xhr.upload.addEventListener("progress", uploadProgress, false)
        xhr.addEventListener("load", uploadComplete, false)
        xhr.addEventListener("error", uploadFailed, false)
        xhr.addEventListener("abort", uploadCanceled, false)
        xhr.open("POST", "upload")
        scope.progressVisible = true
        xhr.send(fd)
    }
    
    
    scope.convertFile = function() {
    	var fd = new FormData()
        fd.append("type", convertType)
        fd.append("directory", scope.directory)
        fd.append("fileNames", scope.fileNames)
        var xhr = new XMLHttpRequest()
        xhr.upload.addEventListener("progress", uploadProgress, false)
        xhr.addEventListener("load", uploadCompleteconvert, false)
        xhr.addEventListener("error", uploadFailed, false)
        xhr.addEventListener("abort", uploadCanceled, false)
        xhr.open("POST", "converter")
        scope.progressVisible = true
        xhr.send(fd)
    }


    function uploadProgress(evt) {
        scope.$apply(function(){
            if (evt.lengthComputable) {
				if(Math.round(evt.loaded * 100 / evt.total)==100){
					var k=75;
					setTimeout(function(){ k++; scope.progress = k
					}, 100);
				}else{
					scope.progress = Math.round(evt.loaded * 100 / evt.total)
				}
            } else {
                scope.progress = 'unable to compute'
            }
        })
    }

    function uploadComplete(evt) {
        /* This event is raised when the server send back a response */
		scope.$apply(function(){
            scope.progressVisible = false
        })
        console.log(evt.target.responseText)
        var obj = JSON.parse(evt.target.responseText)
       // alert(obj.fileNames)
        scope.fileNames=obj.fileNames
        scope.directory=obj.directory
        //scope.convertFile(obj.fileNames,obj.directory)
        console.log(evt.target.responseText.errorList)
        console.log(evt.target.responseText.fileNames)
        console.log(evt.target.responseText.directory)
       scope.$apply(function(){
        scope.uploadVisible = false
        scope.convertVisible = true
       })
    }

    function uploadCompleteconvert(evt) {
        /* This event is raised when the server send back a response */
		scope.$apply(function(){
            scope.progressVisible = false
        })
       
        window.open(BASEURL+"download-file?url="+evt.target.responseText);
    }

    function uploadFailed(evt) {
        alert("There was an error attempting to upload the file.")
    }

    function uploadCanceled(evt) {
        scope.$apply(function(){
            scope.progressVisible = false
        })
        alert("The upload has been canceled by the user or the browser dropped the connection.")
    }
}


