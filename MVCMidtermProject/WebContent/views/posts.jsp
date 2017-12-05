<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>${item.title}</title>
<!-- Required meta tags -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
    content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet"
    href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css"
    integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb"
    crossorigin="anonymous">
<link rel="stylesheet" href="views/css/master.css">
</head>
<body class="bg-light">
    <%@ include file="SharedViews/Layout_Navbar.jsp"%>
    <div class="container-fluid">
        <div class="container">
            <div class="row">
                <div class="bg-white col-sm-3 mt-2 mr-5 rounded"
                    id="sellerProfile">
                    <div class="alert alert-primary">
                        <h1 class="text-dark">${item.user.firstName}
                            ${item.user.lastName}</h1>
                        <h2 class="lead">@${item.user.username}</h2>
                    </div>
                </div>
                <div class="col-sm-8 mt-2 p-2 rounded bg-light">
                    <div class="bg-white rounded pl-4">
                        <h1>${item.title}</h1>
                        <h3>Asking price: $${item.price}</h3>
                        <h4>${item.content}</h4>
                        <c:if test="${activeUser.id == item.user.id}">
                            <div class="row ml-2">
                                <form class="m-1">
                                    <input class="btn btn-secondary"
                                        type="submit" name="itemUpdate"
                                        value="update" />
                                </form>
                                <form class="m-1">
                                    <input class="btn btn-success"
                                        type="submit" name="itemSold"
                                        value="sold" />
                                </form>
                                <form class="m-1">
                                    <input class="btn btn-danger"
                                        type="submit" name="itemDelete"
                                        value="delete" />
                                </form>
                            </div>
                        </c:if>
                    </div>
                    <div class="bg-white pl-4 pr-4">
                        <form action="newPost.do" method="POST">
                            <input type="hidden" name="ownerId"
                                value="${activeUser.id}" /> <input
                                type="hidden" name="itemId"
                                value="${item.id}" />
                            <textarea class="col rounded mt-2" rows="1"
                                cols="80%" name="content"></textarea>
                            <input class="btn btn-primary m-1"
                                type="submit" value="Inquire" />
                        </form>
                    </div>
                    <c:forEach var="post" items="${item.posts}">
                        <ul class="list-group">
                        <c:if test="${editPost == null}">
                            <li class="list-group-item">${post.user.username}:
                                ${post.content }
                            <c:if test="${post.user.id == activeUser.id}">
                                    <div class="row">
                                        <form action="showUpdateArea.do" method="GET" class="m-1">
                                           <input type="hidden" name="postId" value="${post.id}"/>
                                           <input type="hidden" name="itemId" value="${item.id}"/>
                                            <input
                                                class="btn btn-secondary"
                                                type="submit"
                                                value="edit" />
                                        </form>
                                        <form action="deletePost.do" method="POST" class="m-1">
                                            <input type="hidden" name="postId" value="${post.id}" />
                                            <input type="hidden" name="itemId" value="${item.id }"/>
                                            <input
                                                class="btn btn-danger"
                                                type="submit"
                                                value="delete" />
                                        </form>
                                    </div>
                                </c:if>
                            </li>
                        </c:if>
                        <c:if test="${not empty editPost}">
                            <li class="list-group-item">
                                <form action="updatePost.do" method="POST">
                                     <input type="hidden" name="postId" value ="${post.id}"/>
                                     <input type="hidden" name="itemId" value ="${item.id}"/>
                                     <textarea class="col" rows="1" name="postContent" placeholder="${post.content}" required wrap></textarea>
                                     <input class="btn btn-success mt-1" type="submit" value="update post"/> 
                                </form>
                            </li>
                        </c:if>
                        </ul>
                    </c:forEach>
                </div>
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
