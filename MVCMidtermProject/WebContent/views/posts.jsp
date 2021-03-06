<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>${item.title}</title>
<!-- Required meta tags -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
    content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <%@ include file="SharedViews/Layout_CssFiles.jsp"%>
</head>
<body class="bg-white">
    <%@ include file="SharedViews/Layout_Navbar.jsp"%>
    <div class="container">
        <div class="container">
            <div class="row">
                <div class="bg-light col-sm-3 mt-2 mr-5 rounded"
                    id="sellerProfile">
                    <div class="alert alert-primary">
                        <h1 class="text-dark">${item.user.firstName}
                            ${item.user.lastName}</h1>
                        <h2 class="lead">
                            <a
                                href="viewProfile.do?userId=${item.user.id}">@${item.user.username}</a>
                        </h2>
                        <!-- Flag not the right user -->
                        <h4>Overall rating: ${activeUser.overallRating }</h4>
                    </div>
                </div>
                <div class="col-sm-8 mt-2 p-2 rounded bg-light">
                    <div class="text-dark rounded pl-4">
                        <h1>${item.title}</h1>
                        <h5 class="lead">
                            Asking price:
                            <fmt:formatNumber type="currency">${item.price}</fmt:formatNumber>
                        </h5>
                        <p>${item.content}</p>
                        <c:if
                            test="${activeUser.id == item.user.id || activeUser.admin == true}">
                            <div class="row ml-2">
                                <c:if test="${item.active == true}">
                                    <c:if
                                        test="${activeUser.id == item.user.id}">
                                        <button type="button"
                                            class="btn btn-primary margins"
                                            data-toggle="modal"
                                            data-target="#edit">
                                            Edit</button>
                                        <div class="modal fade"
                                            id="edit" tabindex="-1"
                                            role="dialog"
                                            aria-labelledby="editModalLabel"
                                            aria-hidden="true">
                                            <div class="modal-dialog"
                                                role="document">
                                                <div
                                                    class="modal-content">
                                                    <div
                                                        class="modal-header">
                                                        <h5
                                                            class="modal-title"
                                                            id="editModalLabel">Edit
                                                            Your Item</h5>
                                                        <button
                                                            type="button"
                                                            class="close"
                                                            data-dismiss="modal"
                                                            aria-label="Close">
                                                            <span
                                                                aria-hidden="true">&times;</span>
                                                        </button>
                                                    </div>
                                                    <form:form class=""
                                                        action="updateItemInModal.do"
                                                        method="POST"
                                                        modelAttribute="item">
                                                        <div
                                                            class="modal-body">
                                                            <form:input
                                                                class="form-control"
                                                                path="title" pattern="^[a-zA-Z0-9 ]{5,}$" required="required" oninvalid="setCustomValidity('Title must not contain symbols and must be at least 5 characters long.')"
                                                                onchange="try{setCustomValidity('')}catch(e){}"/>
                                                            <form:input
                                                                class="form-control"
                                                                path="price" pattern="^[0-9]+\.?[0-9]*" required="required" oninvalid="setCustomValidity('Price must be a valid number.')"
                                                                onchange="try{setCustomValidity('')}catch(e){}"/>
                                                            <form:textarea
                                                                class="form-control"
                                                                rows="4"
                                                                path="content"/>
                                                            <input
                                                                type="hidden"
                                                                name="cid"
                                                                value="${item.community.id}">
                                                            <input
                                                                type="hidden"
                                                                name="uid"
                                                                value="${activeUser.id}">
                                                            <input
                                                                type="hidden"
                                                                name="id"
                                                                value="${item.id}">
                                                            <input
                                                                type="hidden"
                                                                name="postTime"
                                                                value="${item.postTime}">
                                                            <input
                                                                type="hidden"
                                                                name="category"
                                                                value="${item.category}">
                                                        </div>
                                                        <div
                                                            class="modal-footer">
                                                            <input
                                                                type="submit"
                                                                class="btn btn-primary"
                                                                name=""
                                                                value="Save Changes">
                                                    </form:form>
                                                    <button
                                                        type="button"
                                                        class="btn btn-secondary"
                                                        data-dismiss="modal">Cancel</button>
                                                </div>
                                            </div>
                                        </div>
                            </div>
                        </c:if>
                        <button type="button"
                            class="btn btn-primary margins"
                            data-toggle="modal" data-target="#sold">
                            Mark as Sold</button>
                        <div class="modal fade" id="sold" tabindex="-1"
                            role="dialog"
                            aria-labelledby="soldModalLabel"
                            aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title"
                                            id="soldModalLabel">Confirm
                                            Sold Status</h5>
                                        <button type="button"
                                            class="close"
                                            data-dismiss="modal"
                                            aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">Are
                                        you sure you want to mark this
                                        item as sold? This will remove
                                        this item from
                                        ${item.community.name}.</div>
                                    <div class="modal-footer">
                                        <form class="m-1"
                                            action="soldItem.do">
                                            <input
                                                class="btn btn-danger"
                                                type="submit"
                                                name="itemSold"
                                                value="Mark as Sold" />
                                            <input type="hidden"
                                                name="iid"
                                                value="${item.id}">
                                            <input type="hidden"
                                                name="cid"
                                                value="${item.community.id}">
                                        </form>
                                        <button type="button"
                                            class="btn btn-secondary"
                                            data-dismiss="modal">Cancel</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <button type="button"
                            class="btn btn-primary margins"
                            data-toggle="modal" data-target="#remove">
                            Remove</button>
                        <div class="modal fade" id="remove"
                            tabindex="-1" role="dialog"
                            aria-labelledby="removeModalLabel"
                            aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title"
                                            id="removeModalLabel">Confirm
                                            Removal</h5>
                                        <button type="button"
                                            class="close"
                                            data-dismiss="modal"
                                            aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">Are
                                        you sure you want to remove this
                                        item?</div>
                                    <div class="modal-footer">
                                        <form class="m-1"
                                            action="removeItem.do">
                                            <input
                                                class="btn btn-danger"
                                                type="submit"
                                                name="itemDelete"
                                                value="Remove" /> <input
                                                type="hidden" name="iid"
                                                value="${item.id}">
                                            <input type="hidden"
                                                name="cid"
                                                value="${item.community.id}">
                                        </form>
                                        <button type="button"
                                            class="btn btn-secondary"
                                            data-dismiss="modal">Cancel</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        </c:if>
                    </div>
                    </c:if>
                </div>
                <div class="bg-light pl-4 pr-4">
                    <c:if test="${item.active == true}">
                        <form action="newPost.do" method="POST">
                            <input type="hidden" name="ownerId"
                                value="${activeUser.id}" /> <input
                                type="hidden" name="itemId"
                                value="${item.id}" />
                            <textarea class="col rounded mt-2" rows="1"
                                cols="80%" name="content" required></textarea>
                            <input class="btn btn-primary m-1"
                                type="submit" value="message" />
                        </form>
                    </c:if>
                </div>
                <c:forEach var="post" items="${item.posts}">
                    <ul class="list-group">
                        <c:if test="${editPost == null}">
                            <li class="list-group-item">${post.user.username}:
                                ${post.content } <c:if
                                    test="${item.active == true}">
                                    <c:if
                                        test="${post.user.id == activeUser.id}">
                                        <div class="row">
                                            <form
                                                action="showUpdateArea.do"
                                                method="GET" class="m-1">
                                                <input type="hidden"
                                                    name="postId"
                                                    value="${post.id}" />
                                                <input type="hidden"
                                                    name="itemId"
                                                    value="${item.id}" />
                                                <input
                                                    class="btn btn-secondary"
                                                    type="submit"
                                                    value="edit" />
                                            </form>
                                            <c:if
                                                test="${post.user.id == activeUser.id || activeUser.admin == true}">
                                                <form
                                                    action="deletePost.do"
                                                    method="POST"
                                                    class="m-1">
                                                    <input type="hidden"
                                                        name="postId"
                                                        value="${post.id}" />
                                                    <input type="hidden"
                                                        name="itemId"
                                                        value="${item.id }" />
                                                    <input
                                                        class="btn btn-danger"
                                                        type="submit"
                                                        value="delete" />
                                                </form>
                                            </c:if>
                                        </div>
                                    </c:if>
                                </c:if>
                            </li>
                        </c:if>
                        <c:if test="${not empty editPost}">
                            <li class="list-group-item">
                                <form action="updatePost.do"
                                    method="POST">
                                    <input type="hidden" name="postId"
                                        value="${post.id}" /> <input
                                        type="hidden" name="itemId"
                                        value="${item.id}" />
                                    <textarea class="col" rows="1"
                                        name="postContent"
                                        placeholder="${post.content}"
                                        required wrap></textarea>
                                    <input class="btn btn-success mt-1"
                                        type="submit"
                                        value="update post" />
                                </form>
                            </li>
                        </c:if>
                    </ul>
                </c:forEach>
            </div>
        </div>
            <div class="col">
                <a class="btn btn-outline-danger"href="viewGroup.do?groupId=${item.community.id}">Go
                    back to ${item.community.name} home.</a>
            </div>
    </div>
    <hr>
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
