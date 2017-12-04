<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" class="full-height">
<head>
<title>${group.name}</title>
<!-- Required meta tags -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
    content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet"
    href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css"
    integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb"
    crossorigin="anonymous">
</head>
<body class="full-height bg-light">
    <%@ include file="SharedViews/Layout_Navbar.jsp"%>
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm mr-3 mt-2 bg-white p-2 rounded">
                <div class="alert alert-primary mt-1 text-dark">
                    <h1>${group.name}</h1>
                    <h2 class="lead">Users in group: ${groupSize}</h2>
                </div>
                <ul class="list-group">
                    <c:forEach var="member" items="${groupUsers}">
                        <li class="list-group-item">${member.username }</li>
                    </c:forEach>
                </ul>
            </div>
            <div class="row col-sm-8 mt-2">
                <c:forEach var="item" items="${group.items}">
                    <c:if test="${item.active == true}">
                        <div class="card m-1" style="width: 20rem;">
                            <img class="card-img-top" src="..."
                                alt="Card image cap">
                            <div class="card-body">
                                <h4 class="card-title">${item.title }</h4>
                                <p class="card-text">${item.postTime}
                                    ${item.price }</p>
                                <a href="getPosts.do?id=${item.id}"
                                    class="btn btn-primary">See item
                                </a>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
            <div class="col-sm mt-2">
                <a class="btn btn-primary" href="newItem.do?id=${group.id}">Want to post a
                    new item? Go.</a>
            </div>
        </div>
    </div>
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
    <script
        src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js"
        integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh"
        crossorigin="anonymous"></script>
    <script
        src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"
        integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ"
        crossorigin="anonymous"></script>
</body>
</html>
