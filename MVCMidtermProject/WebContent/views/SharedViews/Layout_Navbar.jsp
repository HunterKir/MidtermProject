<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Image and text -->
<nav
    class="navbar navbar-expand-lg navbar-light bg-white border border-bottom-danger">
    <c:if test="${activeUser == null }">
        <a class="navbar-brand" href="home.do"> <img
            src="views/css/images/2000px-Dina-gor.svg.png" width="30"
            height="30" class="d-inline-block align-top"
            alt="Cloud Harbor"
            style="color: rgb(240, 85, 85); font-size: 18pt;">
        </a>
    </c:if>
    <c:if test="${activeUser != null }">
        <a class="navbar-brand" href="login.do"> <img
            src="views/css/images/2000px-Dina-gor.svg.png" width="30"
            height="30" class="d-inline-block align-top"
            alt="Cloud Harbor"
            style="color: rgb(240, 85, 85); font-size: 18pt;">
        </a>
    </c:if>
    <button class="navbar-toggler" type="button" data-toggle="collapse"
        data-target="#navbarNavAltMarkup"
        aria-controls="navbarNavAltMarkup" aria-expanded="false"
        aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
        <ul class="navbar-nav">
            <c:if test="${not empty activeUser}">
                <li><a class="nav-link" href="login.do">${activeUser.username}</a></li>
                <li><a class="nav-link" href="logout.do">Log-out</a></li>
                <li class="nav-item dropdown"><a
                    class="nav-link dropdown-toggle" href="#"
                    id="navbarDropdown" role="button"
                    data-toggle="dropdown" aria-haspopup="true"
                    aria-expanded="false">Groups</a>
                    <div class="dropdown-menu"
                        aria-labelledby="navbarDropdown">
                        <c:forEach var="community"
                            items="${activeUser.communities}">
                            <a
                                href="viewGroup.do?groupId=${community.id}"
                                class="dropdown-item">${community.name}</a>
                        </c:forEach>
                        <a href="newGroup.do" class="dropdown-item">New
                            Group</a>
                    </div></li>
            </c:if>
        </ul>
    </div>
    <c:if test="${empty activeUser}">
        <div class="text-white form-inline">
            <form class="form-inline" action="login.do" method="get">
                <input type="submit" class="btn btn-primary m-1" name=""
                    value="Log-in">
            </form>
            <!-- <a class="btn btn-primary" href="login.do">Log-in</a> -->
            <a class="btn btn-danger" href="newuser.do">Register</a>
        </div>
    </c:if>
</nav>
