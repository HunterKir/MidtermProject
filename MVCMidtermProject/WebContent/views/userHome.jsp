<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
                    <h1 class="text-dark">${activeUser.firstName}
                        ${activeUser.lastName}</h1>
                    <h2 class="lead">
                        <a data-toggle="tooltip" data-placement="top"
                            title="See what your profile looks like to others"
                            href="viewProfile.do?userId=${activeUser.id}">@${activeUser.username}</a>
                    </h2>
                    <h4>Overall Rating: ${activeUser.overallRating}</h4>
                </div>
                <div class="row">
                    <%-- <form>
                            <input class="btn btn-primary ml-4 m-1" type="submit" value="edit profile"/>
                        </form> --%>
                    <button type="button"
                        class="btn btn-primary m-1 ml-4"
                        data-toggle="modal" data-target="#updateProfile">
                        edit profile</button>
                    <div class="modal fade" id="updateProfile"
                        tabindex="-1" role="dialog"
                        aria-labelledby="updateProfileModalLabel"
                        aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title"
                                        id="updateProfileModalLabel">Your
                                        Profile</h5>
                                    <button type="button" class="close"
                                        data-dismiss="modal"
                                        aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <form:form class=""
                                    action="updateUser.do" method="post"
                                    modelAttribute="user">
                                    <div class="modal-body">
                                        <form:input class="form-control"
                                            path="firstName"
                                            placeholder="first name"
                                            required="required"
                                            pattern="^[a-zA-Z]{1,}$"
                                            oninvalid="setCustomValidity('Name must not contain symbols and must be at least 1 character long.')"
                                            onchange="try{setCustomValidity('')}catch(e){}"></form:input>
                                        <form:input class="form-control"
                                            path="lastName"
                                            placeholder="last name"
                                            required="required"
                                            pattern="^[a-zA-Z]{1,}$"
                                            oninvalid="setCustomValidity('Name must not contain symbols and must be at least 1 character long.')"
                                            onchange="try{setCustomValidity('')}catch(e){}"></form:input>
                                        <form:input class="form-control"
                                            path="username"
                                            placeholder="username"
                                            required="required"
                                            pattern="^[a-zA-Z0-9]{5,}$"
                                            oninvalid="setCustomValidity('Username must not contain symbols and must be at least 5 characters long.')"
                                            onchange="try{setCustomValidity('')}catch(e){}"></form:input>
                                        <form:input class="form-control"
                                            id="userPassword"
                                            path="password"
                                            required="required"
                                            placeholder="new password"
                                            pattern="^[a-zA-Z0-9]{5,}$"
                                            oninvalid="setCustomValidity('Password must not contain symbols and must be at least 5 characters long.')"
                                            onchange="try{setCustomValidity('')}catch(e){}"></form:input>
                                        <p style="color: black;">${usernameError}</p>
                                        <input type="hidden" name="id"
                                            value="${activeUser.id}">
                                    </div>
                                    <div class="modal-footer">
                                        <input class="btn btn-primary"
                                            type="submit"
                                            value="Save Changes" />
                                </form:form>
                                <button type="button"
                                    class="btn btn-secondary"
                                    data-dismiss="modal">Cancel</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col bg-white rounded">
                <div class="row justify-content-between">
                    <p class="lead">Items for sale:</p>
                    <button type="button" class="btn btn-danger ml-5">
                        <span class="badge badge-light">${activeUser.totalItems}</span>
                    </button>
                </div>
            </div>
            <div class="col bg-white rounded mt-1">
                <div class="row justify-content-between">
                    <p class="lead">Items sold:</p>
                    <button type="button" class="btn btn-danger ml-5">
                        <span class="badge badge-light">${activeUser.itemsSold}</span>
                    </button>
                </div>
            </div>
            <!--                 <div class="col bg-white rounded mt-1">
                     <div class="row justify-content-between">
                          <p class="lead">
                               Profile views:
                          </p>
                          <button type="button" class="btn btn-danger ml-5">
                               <span class="badge badge-light">0</span>
                          </button>
                     </div>
                </div> -->
            <div class="col bg-white rounded mt-1">
                <div class="row justify-content-between">
                    <p class="lead">Communities created:</p>
                    <button type="button" class="btn btn-danger ml-3">
                        <span class="badge badge-light">
                            ${activeUser.ownedCommunitiesSize}</span>
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
                    <option value="group">View all groups</option>
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
                            Asking price: <fmt:formatNumber
                                type="currency">${item.price}</fmt:formatNumber></li>
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
                        <li class="row justify-content-between mt-1 p-1">
                            <label>${group.name}</label> <!-- Button trigger modal -->
                            <button type="button"
                                class="btn btn-primary"
                                data-toggle="modal"
                                data-target="#${group.id}">
                                View group details</button>
                        </li>
                        <!-- Modal -->
                        <div class="modal fade" id="${group.id}"
                            tabindex="-1" role="dialog"
                            aria-labelledby="exampleModalLabel"
                            aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title"
                                            id="exampleModalLabel">${group.name}</h5>
                                        <button type="button"
                                            class="close"
                                            data-dismiss="modal"
                                            aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <p class="lead">Group
                                            description</p>
                                        <p>${group.description }</p>
                                        <p class="lead">Group users:</p>
                                        <p>${group.size}</p>
                                    </div>
                                    <div class="modal-footer">
                                         <a class="btn btn-primary"
                                              href="userJoinGroup.do?groupId=${group.id}&userId=${activeUser.id}">Join
                                              group</a>
                                        <button type="button"
                                            class="btn btn-secondary"
                                            data-dismiss="modal">Close</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </ul>
            </c:if>
        </div>
        <div class="col-sm fixed">
            <a href="newGroup.do" class="btn btn-danger col"> Want
                to create a group? Go! </a>
            <div id="accordion" role="tablist">
                <c:forEach var="group" items="${activeUser.communities}">
                    <div class="card">
                        <div class="card-header" role="tab"
                            id="headingOne">
                            <h5 class="mb-0">
                                <a href="viewGroup.do?groupId=${group.id}">Home:
                                </a><a data-toggle="collapse"
                                    href="#${group.id }"
                                    aria-expanded="true"
                                    aria-controls="collapseOne">
                                    ${group.name} </a>
                            </h5>
                        </div>
                        <div id="${group.id}" class="collapse"
                            role="tabpanel" aria-labelledby="headingOne"
                            data-parent="#accordion">
                            <div class="card-body">
                                <c:if
                                    test="${not empty group.description}">
                                    <p class="lead">Group
                                        description</p>
                                    <p>${group.description }</p>
                                </c:if>
                                <p class="lead">Recent items
                                    activity</p>
                                <ul class="list-group">
                                    <c:forEach var="item"
                                        items="${group.items}">
                                        <c:if
                                            test="${item.active == true}">
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
<c:if test="${usernameError != null}">
    <script type="text/javascript">
					$(window).on('load', function() {
						$('#updateProfile').modal('show');
					});
				</script>
</c:if>
</html>
