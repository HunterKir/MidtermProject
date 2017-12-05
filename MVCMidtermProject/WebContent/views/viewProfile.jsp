<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>${viewedUser.username}</title>
<%@ include file="SharedViews/Layout_CssFiles.jsp"%>
</head>
<body class="bg-light">
    <%@ include file="SharedViews/Layout_Navbar.jsp"%>
    <div class="container-fluid mt-4 ">
        <div class="row">
            <div class="col-sm-3 m-1">
                <div class="alert alert-primary mt-1">
                    <h1 class="text-dark">${viewedUser.firstName}
                        ${viewedUser.lastName}</h1>
                    <h2 class="lead">
                        <a href="viewProfile.do?userId=${viewedUser.id}">@${viewedUser.username}</a>
                    </h2>
                </div>
                <div class="col bg-white rounded">
                    <p class="lead">
                        Items for sale:
                        <button type="button"
                            class="btn btn-danger ml-5">
                            <span class="badge badge-light">${viewedUser.totalItems}</span>
                        </button>
                    </p>
                </div>
                <div class="col bg-white rounded mt-1">
                    <p class="lead">
                        Items sold:
                        <button type="button"
                            class="btn btn-danger ml-5">
                            <span class="badge badge-light">${viewedUser.itemsSold}</span>
                        </button>
                    </p>
                </div>
            </div>
            <div class="col-sm-8 m-1">
            <div class="row">
                <c:forEach var="item" items="${viewedUser.itemsPosted}">
                <c:if test="${item.active == true }">
                    <div class="card m-1" style="width: 20rem;">
   <!--                      <img class="card-img-top" src="..."
                            alt="Card image cap"> -->
                        <div class="card-body">
                            <h4 class="card-title">${item.title }</h4>
                            <p class="card-text">
                                Date Posted:
                                <javatime:format
                                    value="${item.postTime}" style="MS" />
                                <br> Price:
                                <fmt:formatNumber type="currency">${item.price}</fmt:formatNumber>
                            </p>
                            <a href="getPosts.do?id=${item.id}"
                                class="btn btn-primary">See item </a>
                        </div>
                    </div>
                </c:if>
                </c:forEach>
            </div>
            </div>
        </div>
    </div>

</body>
<%@ include file="SharedViews/Layout_Scripts.jsp"%>
</html>
