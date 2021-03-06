<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" class="full-height" id="login">
<head>
<title>Log In</title>
<!-- Required meta tags -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
    content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet"
    href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css"
    integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb"
    crossorigin="anonymous">
<link rel="stylesheet" href="views/css/master.css">
<link rel="stylesheet" href="views/css/login.css">
</head>
<body class="full-height">
    <div class="row third-height"></div>
    <div
        class="row align-items-center justify-content-center third-height">
        <div
            class="col-8 form-control center-text align-self-center transparency-white">
            <h1>Create Your Account</h1>

            <form:form class="form-group" action="newuser.do"
                method="POST" modelAttribute="user">

                <div class="row">
                    <div class="col"></div>
                    <div class="left-text form-group col-8">
                        First Name:
                        <form:input class="form-control"
                            path="firstName" />
                        <form:errors path="firstName" />
                    </div>
                    <div class="col"></div>
                </div>
                <div class="row">
                    <div class="col"></div>
                    <div class="left-text form-group col-8">
                        Last Name:
                        <form:input class="form-control" path="lastName" />
                        <form:errors path="lastName" />
                    </div>
                    <div class="col"></div>
                </div>
                <div class="row">
                    <div class="col"></div>
                    <div class="left-text form-group col-8">
                        Username:
                        <form:input class="form-control" path="username" />
                        <form:errors path="username" />
                    </div>
                    <div class="col"></div>
                </div>
                <div class="row">
                    <div class="col"></div>
                    <div class="left-text form-group col-8">
                        Password:
                        <form:password class="form-control"
                            path="password" />
                        <form:errors path="password" />
                    </div>
                    <div class="col"></div>
                </div>
                <%-- <div class="left-text form-group">
                         Confirm Password:
                         <form:input class="form-control" path="passwordConfirm"/>
                         <form:errors path="passwordConfirm"/>
                    </div> --%>
                <div class="form-group">
                    <input type="submit" name="" class="btn btn-primary"
                        value="Finish">
                </div>
                <p style="color: black;">${userExist}</p>
            </form:form>
        </div>
    </div>
    <div class="row third-height"></div>

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
