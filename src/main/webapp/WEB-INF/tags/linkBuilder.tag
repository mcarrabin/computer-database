<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="search" required="false" %>
<%@ attribute name="numPage" required="false" %>
<%@ attribute name="nbElements" required="false" %>
<%@ attribute name="orderBy" required="false" %>
<%@ attribute name="currentOrderBy" required="false" %>
<%@ attribute name="currentSorting" required="false" %>
<%@ attribute name="root" required="true" %>
<c:set var="pageVar" value="?page=${ empty numPage ? 1 : numPage }" />
<c:set var="searchVar">
	<c:if test = "${ not empty nbElements }" >
		<c:out value="&elements=${ nbElements }" />
	</c:if>
</c:set>
<c:set var="nbElementsVar" >
	<c:if test="${not empty search }" >
		<c:out value="&search=${ search }" />
	</c:if>
</c:set>
<c:set var="orderByVar" >
	<c:if test="${not empty orderBy }" >
		<c:out value="&orderby=${ orderBy }" />
	</c:if>
</c:set>
<c:set var="sortingVar" >
	<c:choose>
		<c:when test="${ orderBy == currentOrderBy && not empty orderBy }" >
			<c:choose>
				<c:when test="${ currentSorting == 'asc'}" >
					<c:out value="&sort=desc" />
				</c:when>
				<c:otherwise>
					<c:out value="&sort=asc" />
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:when test="${ orderBy != currentOrderBy && not empty orderBy }" >
			<c:out value="&sort=asc" />
		</c:when>
		<c:otherwise>
			<c:out value="" />
		</c:otherwise>
	</c:choose>
</c:set>
<c:url value="${ root }${ pageVar}${ searchVar }${ nbElementsVar }${ orderByVar }${ sortingVar }" ></c:url>
