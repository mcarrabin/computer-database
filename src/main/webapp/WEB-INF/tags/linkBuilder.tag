<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="search" required="false" %>
<%@ attribute name="numPage" required="false" %>
<%@ attribute name="nbElements" required="false" %>
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
<c:url value="${ root }${ pageVar}${ searchVar }${ nbElementsVar }" ></c:url>
