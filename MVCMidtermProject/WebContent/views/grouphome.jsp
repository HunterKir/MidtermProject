<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
                <c:if test="${activeUser.id == group.owner.id}">
                <div class="row">
                     <button type="button" class="btn btn-primary margins" data-toggle="modal" data-target="#updateGroup">
                          Update Group
                     </button>
                     <div class="modal fade" id="updateGroup" tabindex="-1" role="dialog" aria-labelledby="updateGroupModalLabel" aria-hidden="true">
                          <div class="modal-dialog" role="document">
                               <div class="modal-content">
                                    <div class="modal-header">
                                         <h5 class="modal-title" id="updateGroupModalLabel">Update This Group</h5>
                                         <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                              <span aria-hidden="true">&times;</span>
                                         </button>
                                    </div>
                                    <form:form class="" action="updateGroup.do" method="post" modelAttribute="group">
                                    <div class="modal-body">
                                        <form:input class="form-control" path="name"/>
                                        <form:textarea class="form-control" rows="5" path="description"/>
                                        <input type="hidden" name="cid" value="${group.id}">
                                    </div>
                                    <div class="modal-footer">
                                             <input class="btn btn-primary" type="submit" value="Save Changes"/>
                                        </form:form>
                                         <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                    </div>
                               </div>
                          </div>
                     </div>
                     <button type="button" class="btn btn-primary margins" data-toggle="modal" data-target="#deleteGroup">
                          Delete Group
                     </button>
                     <div class="modal fade" id="deleteGroup" tabindex="-1" role="dialog" aria-labelledby="deleteGroupModalLabel" aria-hidden="true">
                          <div class="modal-dialog" role="document">
                               <div class="modal-content">
                                    <div class="modal-header">
                                         <h5 class="modal-title" id="deleteGroupModalLabel">Confirm Deletion</h5>
                                         <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                              <span aria-hidden="true">&times;</span>
                                         </button>
                                    </div>
                                    <div class="modal-body">
                                         Are you sure you want to delete this group?
                                    </div>
                                    <div class="modal-footer">
                                         <form class="m-1" action="deleteGroup.do">
                                             <input class="btn btn-primary ml-2 m-1" type="submit" value="Delete"/>
                                             <input type="hidden" name="cid" value="${group.id}">
                                         </form>
                                         <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                    </div>
                               </div>
                          </div>
                     </div>
                    </div>
                </c:if>
                <ul class="list-group">
                    <c:forEach var="member" items="${groupUsers}">
                        <li class="list-group-item"><a href="viewProfile.do?userId=${member.id}">${member.username }</a></li>
                    </c:forEach>
                </ul>
            </div>
            <div class="row col-sm-8 mt-2">
                <form class="container" action="groupSearch.do"
                    method="GET" class="form-inline my-2 my-lg-0">
                    <input type="hidden" name="groupId"
                        value="${group.id}" /> <select class="mt-2"
                        name="searchSelect">
                        <option value="items">Search items</option>
                        <option value="people">Search people</option>
                    </select><input class="form-control col mt-3" type="search"
                        placeholder="Search" aria-label="Search"
                        name="search">
                    <div class="row">
                        <button class="btn btn-outline-danger mt-3 ml-3"
                            type="submit">Search</button>
                        <div class="dropdown mt-3 ml-1">
                            <button
                                class="btn btn-secondary dropdown-toggle"
                                type="button" id="dropdownMenuButton"
                                data-toggle="dropdown"
                                aria-haspopup="true"
                                aria-expanded="false">Filter by
                                category</button>
                            <div class="dropdown-menu"
                                aria-labelledby="dropdownMenuButton">
                                <c:forEach var="cat"
                                    items="${categories}">
                                    <a class="dropdown-item text-white" name="category"
                                        value="${cat.type}" href="searchByCategory.do?category=${cat.type}&groupId=${group.id}">${cat.type}</a>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </form>
                <div class="row justify-content-center">
                    <c:if test="${ not empty searchItemsList}">
                        <c:forEach var="item" items="${searchItemsList}">
                            <div class="card m-1" style="width: 20rem;">
                                <img class="card-img-top" src="..."
                                    alt="Card image cap">
                                <div class="card-body">
                                    <h4 class="card-title">${item.title }</h4>
                                    <p class="card-text">
                                        Date Posted:
                                        <javatime:format
                                            value="${item.postTime}"
                                            style="MS" />
                                        <br> Price:
                                        <fmt:formatNumber
                                            type="currency">${item.price}</fmt:formatNumber>
                                    </p>
                                    <a href="getPosts.do?id=${item.id}"
                                        class="btn btn-primary">See
                                        item </a>
                                </div>
                            </div>
                        </c:forEach>
                    </c:if>
                    <c:if
                        test="${empty searchItemsList && empty searchUsersList}">
                        <c:forEach var="item" items="${group.items}">
                            <c:if test="${item.active == true}">
                                <div class="card m-1"
                                    style="width: 20rem;">
                                    <!--                                 <img class="card-img-top" src="..."
                                    alt="Card image cap"> -->
                                    <div class="card-body">
                                        <h4 class="card-title">${item.title }</h4>
                                        <p class="card-text">
                                            Date Posted:
                                            <javatime:format
                                                value="${item.postTime}"
                                                style="MS" />
                                            <br> Price:
                                            <fmt:formatNumber
                                                type="currency">${item.price}</fmt:formatNumber>
                                        </p>
                                        <a
                                            href="getPosts.do?id=${item.id}"
                                            class="btn btn-primary">See
                                            item </a>
                                    </div>
                                </div>
                            </c:if>
                        </c:forEach>
                    </c:if>
                    <c:if test="${not empty searchUsersList}">
                        <ul class="list-group">
                            <c:forEach var="user"
                                items="${searchUsersList}">
                                <li class="list-group-item">${user.username }</li>
                            </c:forEach>
                        </ul>
                    </c:if>
                </div>
            </div>
            <div class="col-sm mt-2">
                <a class="btn btn-primary"
                    href="newItem.do?id=${group.id}">Want to post a
                    new item? Go.</a>
                <div class="row col-sm mt-2">
                    <form action="searchByMin.do" method="POST">
                        <p class="lead">Filter items by price</p>
                        <label> Search by minimum price</label> <input
                            class="rounded" type="number" min="0" />
                    </form>
                    <form action="searchByMax.do" method="POST">
                        <label> Search by maximum price</label> <input
                            class="rounded" type="number" min="0" />
                    </form>
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
