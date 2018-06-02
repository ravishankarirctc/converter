<!DOCTYPE html>
<html lang="en" ng-app="app">

  <head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
	<script>var BASEURL="http://localhost:8888/LitePdfConverter/";</script>
    <title>Litepdf - Convert any file</title>

    <!-- Bootstrap core CSS -->
    <link href="resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom fonts for this template -->
    <link href="resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="resources/vendor/simple-line-icons/css/simple-line-icons.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic,700italic" rel="stylesheet" type="text/css">

    <!-- Custom styles for this template -->
    <link href="resources/css/landing-page.min.css" rel="stylesheet">
    <link href="resources/css/custom.css" rel="stylesheet">
  </head>

  <body>
    <!-- <div id="preloader"></div> -->
    <!-- Navigation -->
    <nav class="navbar navbar-light bg-light static-top">
      <div class="container">
        <a class="navbar-brand" href="#"><img src="resources/img/litepdf.JPG" style="width: 15%;"></a>
        <a class="btn btn-primary" href="#">Contact Us</a>
      </div>
    </nav>

    <!-- Masthead -->
    <header class="masthead text-white text-center features-icons bg-light text-center" ng-controller="FileUploadCtrl">
      <!-- <div class="overlay"></div> -->
     <div class="container">
        <div class="row">
		 <div class="col-xl-12 mx-auto">
            <h1 class="mb-5" style="font-size: 2rem;    margin-bottom: 15px !important;display: inline-block;font-size: 1.5rem;margin-right: 3%;">Select a File to Upload</h1>
	    <input type="file" ng-model-instant id="fileToUpload" multiple onchange="angular.element(this).scope().setFiles(this)" style="margin-bottom: 20px;" />
          </div>
    </div>
	    <div class="row">
		 <div class="col-xl-12 mx-auto">
		  <h1 class="mb-5" style="font-size: 2rem;    margin-bottom: 15px !important;">OR</h1>
		 <div id="dropbox" class="dropbox" ng-class="dropClass" style="width:100%;height:4em;"><span>{{dropText}}</span></div>
    </div>
    </div>
	<div ng-show="files.length" style="margin-top: 2%;">
        <div ng-repeat="file in files.slice(0)" style="margin-bottom: 2%;">
            <span>{{file.webkitRelativePath || file.name}}</span>
            (<span ng-switch="file.size > 1024*1024">
                <span ng-switch-when="true">{{file.size / 1024 / 1024 | number:2}} MB</span>
                <span ng-switch-default>{{file.size / 1024 | number:2}} kB</span>
            </span>)
        </div>
        <input class="btn" type="button" ng-click="uploadFile()" value="Upload" />
		<span ng-show="progressVisible" style="display: block;">Please wait, File is uploading.....</span>
        <div ng-show="progressVisible" style="margin-top: 2%;">
            <div class="percent">{{progress}}%</div>
            <div class="progress-bar">
                <div class="uploaded" ng-style="{'width': progress+'%'}"></div>
            </div>
        </div>
    </div>

		</div>
    </header>

  

    <!-- Footer -->
    <footer class="footer bg-light">
      <div class="container">
        <div class="row">
          <div class="col-lg-6 h-100 text-center text-lg-left my-auto">
            <ul class="list-inline mb-2">
              <li class="list-inline-item">
                <a href="#">About</a>
              </li>
              <li class="list-inline-item">&sdot;</li>
              <li class="list-inline-item">
                <a href="#">Contact</a>
              </li>
              <li class="list-inline-item">&sdot;</li>
              <li class="list-inline-item">
                <a href="#">Terms of Use</a>
              </li>
              <li class="list-inline-item">&sdot;</li>
              <li class="list-inline-item">
                <a href="#">Privacy Policy</a>
              </li>
            </ul>
            <p class="text-muted small mb-4 mb-lg-0">&copy; Your Website 2018. All Rights Reserved.</p>
          </div>
          <div class="col-lg-6 h-100 text-center text-lg-right my-auto">
            <ul class="list-inline mb-0">
              <li class="list-inline-item mr-3">
                <a href="#">
                  <i class="fa fa-facebook fa-2x fa-fw"></i>
                </a>
              </li>
              <li class="list-inline-item mr-3">
                <a href="#">
                  <i class="fa fa-twitter fa-2x fa-fw"></i>
                </a>
              </li>
              <li class="list-inline-item">
                <a href="#">
                  <i class="fa fa-instagram fa-2x fa-fw"></i>
                </a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </footer>

    <!-- Bootstrap core JavaScript -->
    <script src="resources/vendor/jquery/jquery.min.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/angular.js/1.2.5/angular.js"></script>
    <script src="resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	 <script src="resources/js/custom.js"></script>


  </body>

</html>
