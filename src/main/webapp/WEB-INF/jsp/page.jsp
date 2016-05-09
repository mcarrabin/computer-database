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
                <c:out value="${page.itemsTotalCount}" /> Computers found
                <c:if test="${ not empty page.search}" >
                	<c:out value=" with filter on name applied: ${ page.search }" />	
                </c:if>
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="<customTag:linkBuilder 
                    										root="home" 
                    										numPage="1"
                    										nbElements="${  page.itemsPerPage  }" 
                    								/>" 
                    	method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                        <a type="button" href="<customTag:linkBuilder 
                        						root="home" 
                        						numPage="1"
                        						nbElements="${ page.itemsPerPage }" />" 
                        	class="btn btn-primary<c:out value="${ empty page.search ? ' disabled' : '' }" /> "
                        >clear Filter</a>
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="computer/add">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="computer/edit" onclick="$.fn.toggleEditMode();">Edit</a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="computer/delete" method="POST">
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
                        	<i class="
	                        	<c:choose>
	                        		<c:when test="${ page.sorting == 'asc' && page.orderBy == 'computerName' }" >
	                        			<c:out value="glyphicon glyphicon-arrow-up" />
	                        		</c:when>
	                        		<c:when test="${ page.sorting == 'desc' && page.orderBy == 'computerName' }">
	                        			<c:out value="glyphicon glyphicon-arrow-down" />
	                        		</c:when>
	                        	</c:choose>
                        	"></i>Computer name
                            <a href="<customTag:linkBuilder 
                            			root="home" 
                            			currentOrderBy="${ page.orderBy }" 
                            			currentSorting="${ page.sorting }" 
                            			orderBy="computerName" 
                            			numPage="${ page.currentPage }" 
                            			search="${ page.search }" 
                            			nbElements="${ page.itemsPerPage }"/>
                            	" type="button" class="glyphicon glyphicon-sort" >
                            </a>
                        </th>
                        <th>
                        	<i class="
	                        	<c:choose>
	                        		<c:when test="${ page.sorting == 'asc' && page.orderBy == 'introduced' }" >
	                        			<c:out value="glyphicon glyphicon-arrow-up" />
	                        		</c:when>
	                        		<c:when test="${ page.sorting == 'desc' && page.orderBy == 'introduced' }" >
	                        			<c:out value="glyphicon glyphicon-arrow-down" />
	                        		</c:when>
	                        	</c:choose>
                        	"></i>Introduced date
                        	<a href="<customTag:linkBuilder 
                            			root="home"
                            			currentOrderBy="${ page.orderBy }" 
                            			currentSorting="${ page.sorting }" 
                            			orderBy="introduced" 
                            			numPage="${ page.currentPage }" 
                            			search="${ page.search }" 
                            			nbElements="${ page.itemsPerPage }"/>
                            	" type="button" class="glyphicon glyphicon-sort" >
                        	</a>
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                        	<i class="
	                        	<c:choose>
	                        		<c:when test="${ page.sorting == 'asc' && page.orderBy == 'discontinued' }" >
	                        			<c:out value="glyphicon glyphicon-arrow-up" />
	                        		</c:when>
	                        		<c:when test="${ page.sorting == 'desc' && page.orderBy == 'discontinued' }">
	                        			<c:out value="glyphicon glyphicon-arrow-down" />
	                        		</c:when>
	                        	</c:choose>
                        	"></i>Discontinued date
                            <a href="<customTag:linkBuilder 
                            			root="home" 
                            			currentOrderBy="${ page.orderBy }" 
                            			currentSorting="${ page.sorting }" 
                            			orderBy="discontinued" 
                            			numPage="${ page.currentPage }" 
                            			search="${ page.search }" 
                            			nbElements="${ page.itemsPerPage }"/>
                            	" type="button" class="glyphicon glyphicon-sort" ></a>
                        </th>
                        <!-- Table header for Company -->
                        <th>
                        	<i class="
	                        	<c:choose>
	                        		<c:when test="${ page.sorting == 'asc' && page.orderBy == 'company' }" >
	                        			<c:out value="glyphicon glyphicon-arrow-up" />
	                        		</c:when>
	                        		<c:when test="${ page.sorting == 'desc' && page.orderBy == 'company' }">
	                        			<c:out value="glyphicon glyphicon-arrow-down" />
	                        		</c:when>
	                        	</c:choose>
                        	"></i>Company
                            <a href="<customTag:linkBuilder 
                            			root="home" 
                            			currentOrderBy="${ page.orderBy }" 
                            			currentSorting="${ page.sorting }"  
                            			orderBy="company" 
                            			numPage="${ page.currentPage }" 
                            			search="${ page.search }" 
                            			nbElements="${ page.itemsPerPage }"/>
                            	" type="button" class="glyphicon glyphicon-sort" ></a>
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
	                <c:forEach items="${page.elements}" var="computer" >
	                    <tr>
	                        <td class="editMode">
	                            <input type="checkbox" name="cb" class="cb" value="${ computer.id }" id="${computer.name}_id">
	                        </td>
	                        <td>
	                            <a id="${computer.name}_name" href="computer/edit?computerId=${ computer.id }" ><c:out value="${computer.name}" /></a>
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