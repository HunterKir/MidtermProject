<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>Swap Meet</title>
<%@ include file="SharedViews/Layout_CssFiles.jsp"%>
<link rel="styleSheet" href="views/css/home.css" />
</head>
<body>
<%@ include file="SharedViews/Layout_Navbar.jsp" %>
    <div class="Jumbotron-fluid">
        <div class="row">
            <div class="card" style="width: 20rem; height:400px">
                <img class="card-img-top" src="..." alt="Card image cap">
                <div class="card-body">
                    <h4 class="card-title">Card title</h4>
                    <p class="card-text">Some quick example text to
                        build on the card title and make up the bulk of
                        the card's content.</p>
                    <a href="#" class="btn btn-primary">Go somewhere</a>
                </div>
            </div>
            <div class="card" style="width: 20rem; height:400px">
                <img class="card-img-top" src="..." alt="Card image cap">
                <div class="card-body">
                    <h4 class="card-title">Card title</h4>
                    <p class="card-text">Some quick example text to
                        build on the card title and make up the bulk of
                        the card's content.</p>
                    <a href="#" class="btn btn-primary">Go somewhere</a>
                </div>
            </div>
            <div class="card" style="width: 20rem;height:400px">
                <img class="card-img-top" src="..." alt="Card image cap">
                <div class="card-body">
                    <h4 class="card-title">Card title</h4>
                    <p class="card-text">Some quick example text to
                        build on the card title and make up the bulk of
                        the card's content.</p>
                    <a href="#" class="btn btn-primary">Go somewhere</a>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="SharedViews/Layout_Scripts.jsp"%>
</body>
</html>
