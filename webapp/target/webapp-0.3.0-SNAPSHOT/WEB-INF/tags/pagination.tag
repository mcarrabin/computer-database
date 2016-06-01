<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tld" uri="/WEB-INF/tld/pagination.tld"%>
<%@ attribute name="availablePages" required="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="customTag" %>
<%@ attribute name="page" required="true" type="com.excilys.computerdatabase.entities.Page" %>
<ul class="pagination">
	<li>
		<a href="<customTag:linkBuilder root="home" currentOrderBy="${ page.orderBy }" currentSorting="${ page.sorting }" numPage="1" nbElements="${ page.itemsPerPage }" search="${ page.search }" />" aria-label="Previous">
		    <span aria-hidden="true">&laquo;</span>
		</a>
	</li>
	
	<tld:pagination currentPage="${ page.currentPage }" numPageMax="${ page.maxPage }" />
	
	<c:forEach var="i" begin="${ min }" end="${ max }" step="1">
		<li class="${ i == page.currentPage ? 'active' : '' }">
			<a href="<customTag:linkBuilder root="home" currentOrderBy="${ page.orderBy }" currentSorting="${ page.sorting }" numPage="${ i }" nbElements="${ page.itemsPerPage }" search="${ page.search }" />">
				<c:out value="${ i }" />
			</a>
		</li>
	</c:forEach>
	<li>
		<a href="<customTag:linkBuilder root="home" currentOrderBy="${ page.orderBy }" currentSorting="${ page.sorting }" search="${ page.search }" numPage="${ page.maxPage }" nbElements="${ page.itemsPerPage }" />" aria-label="Next">
		    <span aria-hidden="true">&raquo;</span>
		</a>
	</li>
</ul>

<div class="btn-group btn-group-sm pull-right" role="group" >
    <a href="<customTag:linkBuilder root="home" currentOrderBy="${ page.orderBy }" currentSorting="${ page.sorting }" search="${ page.search }" numPage="1" nbElements="10" />" type="button" class="btn btn-default${ 10 == page.itemsPerPage ? ' btn-primary' : '' }">10</a>
    <a href="<customTag:linkBuilder root="home" currentOrderBy="${ page.orderBy }" currentSorting="${ page.sorting }" search="${ page.search }" numPage="1" nbElements="50" />" type="button" class="btn btn-default${ 50 == page.itemsPerPage ? ' btn-primary' : '' }">50</a>
    <a href="<customTag:linkBuilder root="home" currentOrderBy="${ page.orderBy }" currentSorting="${ page.sorting }" search="${ page.search }" numPage="1" nbElements="100" />" type="button" class="btn btn-default${ 100 == page.itemsPerPage ? ' btn-primary' : '' }">100</a>
</div>
