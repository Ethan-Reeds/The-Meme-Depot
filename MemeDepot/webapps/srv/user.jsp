<!DOCTYPE html>
<html>
    <head>
        <title>MemeDepot ${account.getUsername()}</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <link rel="stylesheet" href="/css/bootstrap.min.css">
        <link rel="stylesheet" href="/jquery-ui-1.12.1/jquery-ui.css">
        <script src="/jquery.min.js"></script>
        <script src="/jquery-ui-1.12.1/jquery-ui.min.js"></script>
        <script src="/js/bootstrap.bundle.min.js"></script>
    </head>
    <style>
        .text-overflow-center {
            margin-left: -100%;
            margin-right: -100%;
            text-align: center;
        }
    </style>
<body>
    <header> 
            
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark" role = "navigation">
            <a class="navbar-brand" href="#">
                <img src="/Meme Depot Logo.png" width="30" height="30" class="d-inline-block align-top logoStyle" alt="">
                
            </a>
            <a class="navbar-brand" href="#" id = "logoText">Meme Depot</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
              <span class="navbar-toggler-icon"></span>
            </button>
          
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
              <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                  <a class="nav-link" href="/index.html">Home <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="/login.html">Log in</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/register.html">Register</a>
                </li>
              </ul>
            </div>
        </nav>
    </header>
    <div class="container-fluid" style="margin-top: 1em;">
        <div class="row justify-content-center">
            <div class="col-3">
                <!--Should I change this to reference a servlet that returns an image based on supplied user?-->
                <!--data:image/gif;base64,${image}}-->
                <img src="data:image/gif;base64,${image}" class="img-fluid rounded">
                <div class="text-overflow-center">
                    ${account.getUsername()}
                    <br>
                    ${account.getEmail()}
                </div>
            </div>
        </div>
    </div>
</body>
</html>

