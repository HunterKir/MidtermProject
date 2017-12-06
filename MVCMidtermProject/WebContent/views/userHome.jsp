<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>${activeUser.username}</title>
<%@ include file="SharedViews/Layout_CssFiles.jsp"%>
</head>
<body class="bg-light">
    <%@ include file="SharedViews/Layout_Navbar.jsp"%>
    <div class="container-fluid mt-4">
        <div class="row">
            <div class="col-sm-3 bg-white rounded m-1">
                <div class="alert alert-primary mt-1">
                <h1 class="text-dark">${activeUser.firstName} ${activeUser.lastName}</h1>
                <h2 class="lead"><a  data-toggle="tooltip" data-placement="top" title="See what your profile looks like to others" href="viewProfile.do?userId=${activeUser.id}">@${activeUser.username}</a></h2>
                <h4>Overall Rating: ${activeUser.overallRating}</h4>
                </div>
                    <div class="row">
                        <form>
                            <input class="btn btn-primary ml-4 m-1" type="submit" value="edit profile"/>
                        </form>
                    </div>
                <div class="col bg-white rounded">
                        <div class="row justify-content-between">
                             <p class="lead">
                                  Items for sale:
                             </p>
                             <button type="button" class="btn btn-danger ml-5">
                                  <span class="badge badge-light">${activeUser.totalItems}</span>
                             </button>
                        </div>
                </div>
                <div class="col bg-white rounded mt-1">
                     <div class="row justify-content-between">
                          <p class="lead">
                               Most active post:
                          </p>
                          <button type="button" class="btn btn-danger ml-3">
                               <span class="badge badge-light">0</span>
                          </button>
                     </div>
                </div>
                <div class="col bg-white rounded mt-1">
                     <div class="row justify-content-between">
                          <p class="lead">
                               Profile views:
                          </p>
                          <button type="button" class="btn btn-danger ml-5">
                               <span class="badge badge-light">0</span>
                          </button>
                     </div>
                </div>
                <div class="col bg-white rounded mt-1">
                     <div class="row justify-content-between">
                          <p class="lead">
                               Items sold:
                          </p>
                          <button type="button" class="btn btn-danger ml-5">
                               <span class="badge badge-light">${activeUser.itemsSold}</span>
                          </button>
                     </div>
                </div>
            </div>
            <div class="col-sm bg-white  m-1">
                <form action="search.do" method="GET"
                    class="form-inline my-2 my-lg-0">
                    <select class="mt-2" name="searchSelect">
                        <option value="items">Search items</option>
                        <option value="people">Search people</option>
                        <option value="groups">Search groups</option>
                    </select> <input class="form-control col mt-3" type="search"
                        placeholder="Search" aria-label="Search"
                        name="search">
                    <button class="btn btn-outline-danger mt-3 ml-1"
                        type="submit">Search</button>
                </form>
                <c:if test="${not empty kwError}">
                    <p class="alert alert-danger">${kwError}</p>
                </c:if>
                <c:if test="${not empty itemsList}">
                    <ul class="list-group mt-2">
                        <c:forEach var="item" items="${itemsList}">
                            <li class="list-group-item"><a
                                href="getPosts.do?id=${item.id}">${item.title}</a>
                                Asking price: <fmt:formatNumber type="currency">${item.price}</fmt:formatNumber></li>
                        </c:forEach>
                    </ul>
                </c:if>
                <c:if test="${not empty userList}">
                    <ul class="list-group mt-2">
                        <c:forEach var="user" items="${userList}">
                            <li class="list-group-item"><a
                                href="getPosts.do?id=${user.id}">${user.username}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </c:if>
                <c:if test="${not empty groupList}">
                    <ul class="list-group mt-2">
                        <c:forEach var="group" items="${groupList}">
                            <li class="list-group-item"><a
                                href="getGroup.do?id=${group}">${groupList}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </c:if>
            </div>
            <div class="col-sm fixed">
                <a href="newGroup.do" class="btn btn-danger col">
                    Want to create a group? Go! </a>
                <div id="accordion" role="tablist">
                    <c:forEach var="group"
                        items="${activeUser.communities}">
                        <div class="card">
                            <div class="card-header" role="tab"
                                id="headingOne">
                                <h5 class="mb-0">
                                    <a
                                        href="viewGroup.do?id=${group.id}">Home:
                                    </a><a data-toggle="collapse"
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
                                <c:if test="${not empty group.description}">
                                    <p class="lead">Group description</p>
                                    <p>${group.description }</p>
                                </c:if>
                                    <p class="lead">Recent items activity</p>
                                    <ul class="list-group">
                                        <c:forEach var="item"
                                            items="${group.items}">
                                            <c:if test="${item.active == true}">
                                                 <li
                                                      style="list-style: none;"
                                                      class="alert alert-primary"><a
                                                      class="alert-link"
                                                      href="getPosts.do?id=${item.id}">${item.title}</a></li>
                                            </c:if>
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
