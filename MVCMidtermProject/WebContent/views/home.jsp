<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
     <head>
     <title>Swap Meet</title>
<%@ include file="SharedViews/Layout_CssFiles.jsp"%>
<link rel="styleSheet" href="views/css/home.css"/>

</head>
<body>
<%@ include file="SharedViews/Layout_Navbar.jsp" %>
    <div class="container-fluid" id="home-headImage">
        <h1 class="row justify-content-center text-white">Swap Meet</h1>
    </div>
    <div class="container col-sm-8 justify-content-center">
        <h1 class="row justify-content-center">How it works</h1>
        
        <div class="row justify-content-center mb-2">
            <h4 class="col align-self-start">Step 1: Sign up</h4>
            <span class="col align-self-end"><img src="views/css/images/signup.jpg" />
            </span>
        </div>
        <div class="row justify-content-center p-2">
            <span class="col align-self-start"><img src="views/css/images/groupImage.jpg" /></span>
            <h4 class="col align-self-end">Step 2: Join or create a group</h4>
        </div>
        <div class="row mb-2">
            <h4 class="col align-self-start">Step 3: Off to post or meet!</h4>
          <span class="col align-self-end"><img src="views/css/images/Fly.jpg" /></span> 
        </div>
    </div>

    <%@ include file="SharedViews/Layout_Footer.jsp" %>
    <%@ include file="SharedViews/Layout_Scripts.jsp"%>
</body>
</html>
