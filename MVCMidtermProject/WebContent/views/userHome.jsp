<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
            <div class="col-sm-3  bg-white rounded m-2 p-2">
                <form action="newGroup.do" method="GET">
                    <Button type="submit"class="btn btn-primary">
                        <h4>Want to create a group?</h4>
                        <h3>Go!</h3>
                    </Button>
                </form>
            </div>
            <div class="col-sm">Search area</div>
            <div class="col-sm">User groups</div>
        </div>
    </div>
</body>
</html>
