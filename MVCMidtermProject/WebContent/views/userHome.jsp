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
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-3  rounded mt-2 p-2 fixed">
                <form action="newGroup.do" method="GET">
                    <Button type="submit" class="btn btn-primary">
                        <h4>Want to create a group?</h4>
                        <h3>Go!</h3>
                    </Button>
                </form>
            </div>
            <div class="col-sm">Search area</div>
            <div class="col-sm mt-2  fixed">
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
                            <div id="${group.id }" class="collapse show"
                                role="tabpanel"
                                aria-labelledby="headingOne"
                                data-parent="#accordion">
                                <div class="card-body">
                                    <c:forEach var="item"
                                        items="${group.items}">
                                    ${item}
                                </c:forEach>
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
