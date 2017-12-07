<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>${group.name}</title>
<%@ include file="SharedViews/Layout_CssFiles.jsp"%>

</head>
<body class="bg-light">
    <%@ include file="SharedViews/Layout_Navbar.jsp"%>
    <div class="container-fluid col-10">
        <div class="row col bg-white m-2">
            <div class="col-sm mr-3 mt-2 bg-white p-2 rounded">
                <div class="alert alert-primary mt-1 text-dark">
                    <h1>${group.name}</h1>
                    <h2 class="lead">Users in group: ${group.size}</h2>
                    <h2>Overall rating: ${group.overallGroupRating}</h2>
                </div>
            </div>
             <div class="row col-sm-8 mt-2">
             <h4 class="lead">Group description</h4>
                <p>${group.description}</p>
                <a class="btn btn-primary text-white p-2 m-2">Join group</a>
             </div>
       </div>
   </div>
</body>
<%@ include file="SharedViews/Layout_Scripts.jsp"%>

</html>
