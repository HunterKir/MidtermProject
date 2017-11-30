<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" class="full-height" id="login">
     <head>
     <title>Log In</title>
         <!-- Required meta tags -->
     <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
     <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

         <!-- Bootstrap CSS -->
     <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
     <link rel="stylesheet" href="css/master.css">
     <link rel="stylesheet" href="css/login.css">
</head>
<body class="full-height">
     <div class="row third-height">

     </div>
     <div class="row align-items-center justify-content-center third-height">
          <div class="col-10 form-control center-text align-self-center transparency-white">
               <h1>Create Your Account</h1>
               <form:form class="form-group" action="createUser.do" method="get" modelAttribute="user">
                    <div class="left-text form-group">
                         First Name:
                         <form:input class="form-control" path="firstName"/>
                         <form:errors path="firstName"/>
                    </div>
                    <div class="left-text form-group">
                         Last Name:
                         <form:input class="form-control" path="lastName"/>
                         <form:errors path="lastName"/>
                    </div>
                    <div class="left-text form-group">
                         Username:
                         <form:input class="form-control" path="username"/>
                         <form:errors path="username"/>
                    </div>
                    <div class="left-text form-group">
                         Password:
                         <form:password class="form-control" path="password"/>
                         <form:errors path="password"/>
                    </div>
                    <div class="left-text form-group">
                         Confirm Password:
                         <form:input class="form-control" path="passwordConfirm"/>
                         <form:errors path="passwordConfirm"/>
                    </div>
                    <div class="form-group">
                         <input type="submit" name="" class="btn btn-primary" value="Finish">
                    </div>
               </form:form>
               <%-- <h1>Create Your Account</h1>
               <form class="form-group" action="createUser.do" method="post">
                    <div class="left-text form-group">
                         First Name
                         <input type="text" name="firstName" class="form-control" value="" placeholder="First Name">
                    </div>
                    <div class="left-text form-group">
                         Last Name
                         <input type="text" name="lastName" class="form-control" value="" placeholder="Last Name">
                    </div>
                    <div class="left-text form-group">
                         Username
                         <input type="text" name="username" class="form-control" value="" placeholder="Username">
                    </div>
                    <div class="left-text form-group">
                         Password
                         <input type="password" name="password" class="form-control" value="" placeholder="Password">
                    </div>
                    <div class="left-text form-group">
                         Confirm Password
                         <input type="password" name="confirmPassword" class="form-control" value="" placeholder="Confirm Password">
                    </div>
                    <div class="form-group">
                         <input type="submit" name="" class="btn btn-primary" value="Finish">
                    </div>
               </form> --%>
          </div>
     </div>
     <div class="row third-height">

     </div>

         <!-- Optional JavaScript -->
         <!-- jQuery first, then Popper.js, then Bootstrap JS -->
     <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
     <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
     <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
</body>
</html>
