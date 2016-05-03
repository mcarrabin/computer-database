<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="customTag" %>
<%@ taglib uri="/WEB-INF/tld/pagination.tld" prefix="tld"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/resources/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/resources/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="home"> Application - Computer Database </a>
        </div>
    </header>


    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
                <c:out value="${page.numElementTotal}" /> Computers found
                <c:if test="${ not empty page.searchFilter}" >
                	<c:out value=" with filter on name applied: ${ page.searchFilter }" />	
                </c:if>
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="<customTag:linkBuilder root="home" numPage="1" nbElements="10" />" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                        <a type="button" href="<customTag:linkBuilder root="home" numPage="1" nbElements="${ page.itemsPerPage }" />"<c:if test="${ page.searchFilter.length() == 0 }" ><c:out value="disabled" /> </c:if> 
                        class="btn btn-primary">clear Filter</a>
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="computer/add">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="computer/edit" onclick="$.fn.toggleEditMode();">Edit</a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="#" method="POST">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->
                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>

                        <th>
                            <a href="<customTag:linkBuilder root="home" currentOrderBy="${ page.orderByFilter }" currentSorting="${ page.sorting }" orderBy="c.name" numPage="${ page.numPage }" search="${ page.searchFilter }" nbElements="${ page.itemsPerPage }"/>" >Computer name</a>
                        </th>
                        <th>
                            <a href="<customTag:linkBuilder root="home" currentOrderBy="${ page.orderByFilter }" currentSorting="${ page.sorting }" orderBy="c.introduced" numPage="${ page.numPage }" search="${ page.searchFilter }" nbElements="${ page.itemsPerPage }"/>" >Introduced date </a>
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            <a href="<customTag:linkBuilder root="home" currentOrderBy="${ page.orderByFilter }" currentSorting="${ page.sorting }" orderBy="c.discontinued" numPage="${ page.numPage }" search="${ page.searchFilter }" nbElements="${ page.itemsPerPage }"/>" >Discontinued date</a>
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            <a href="<customTag:linkBuilder root="home" currentOrderBy="${ page.orderByFilter }" currentSorting="${ page.sorting }"  orderBy="comp.name" numPage="${ page.numPage }" search="${ page.searchFilter }" nbElements="${ page.itemsPerPage }"/>" >Company</a>
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
	                <c:forEach items="${page.elements}" var="computer" >
	                    <tr>
	                        <td class="editMode">
	                            <input type="checkbox" name="cb" class="cb" value="${ computer.id }">
	                        </td>
	                        <td>
	                            <a href="computer/edit?id=${ computer.id }" ><c:out value="${computer.name}" /></a>
	                        </td>
	                        <td><c:out value="${computer.introduced}" /></td>
	                        <td><c:out value="${computer.discontinued}" /></td>
	                        <td><c:out value="${computer.companyName }" /></td>
	
	                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
  			<customTag:pagination availablePages="${ availablePages }"
 				page="${ page }" />
		</div>
	</footer>
	<script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/dashboard.js"></script>

</body>
</html>