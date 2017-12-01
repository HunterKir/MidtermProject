<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>
<%@ include file="SharedViews/Layout_CssFiles.jsp"%>
</head>
<body class="bg-light">
    <%@ include file="SharedViews/Layout_Navbar.jsp"%>
    <div class="container-fluid mt-4">
        <div class="row">
            <div class="col-sm-3  rounded fixed">
                <a href="newGroup.do" class="btn btn-danger col">
                    Want to create a group? Go! </a>
            </div>
            <div class="col-sm bg-white">
                <form class="form-inline my-2 my-lg-0">
                    <input class="form-control col" type="search"
                        placeholder="Search" aria-label="Search">
                    <button class="btn btn-outline-danger my-2 my-sm-0"
                        type="submit">Search</button>
                </form>
            </div>
            <div class="col-sm fixed">
                <div id="accordion" role="tablist">
                    <c:forEach var="group"
                        items="${activeUser.communities}">
                        <div class="card">
                            <div class="card-header" role="tab"
                                id="headingOne">
                                <h5 class="mb-0">
                                    <a data-toggle="collapse"
                                        href="#${group.id }"
                                        aria-expanded="true"
                                        aria-controls="collapseOne">
                                        ${group.name} </a>
                                </h5>
                            </div>
                            <div id="${group.id }" class="collapse"
                                role="tabpanel"
                                aria-labelledby="headingOne"
                                data-parent="#accordion">
                                <div class="card-body">
                                <ul class="list-group">
                                    <c:forEach var="item"
                                        items="${group.items}">
                                    <li class="list-group-item"><a href="getPosts.do?id=${item.id}">${item.title}</a></li>
                                </c:forEach>
                                </ul>     
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</body>
<%@ include file="SharedViews/Layout_Scripts.jsp"%>
</html>
