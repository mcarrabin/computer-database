<%-- <%@ tag body-content="empty" %>
 --%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tld" uri="/WEB-INF/tld/pagination.tld"%>
<%@ attribute name="availablePages" required="true" %>
<%@ attribute name="page" required="true" type="com.excilys.computerdatabase.entities.Page" %>
<ul class="pagination">
	<li>
		<a href="home?numPage=1&elements=${ page.elements.size() }&search=${ page.searchFilter }" aria-label="Previous">
		    <span aria-hidden="true">&laquo;</span>
		</a>
	</li>
	
	<tld:pagination currentPage="${ page.numPage }" numPageMax="${ page.numPageMax }" />
	
	<c:forEach var="i" begin="${ min }" end="${ max }" step="1">
		<li class="${ i == page.numPage ? 'active' : '' }">
			<a href="<c:out value="home?numPage=${ i }&elements=${ page.elements.size() }&search=${ page.searchFilter }" />">
				<c:out value="${ i }" />
			</a>
		</li>
	</c:forEach>
	<li>
		<a href="home?numPage=${ page.numPageMax }&elements=${ page.elements.size() }&search=${ page.searchFilter }" aria-label="Next">
		    <span aria-hidden="true">&raquo;</span>
		</a>
	</li>
</ul>

<div class="btn-group btn-group-sm pull-right" role="group" >
    <a href="home?numPage=1&elements=10" type="button" class="btn btn-default${ 10 == page.elements.size() ? ' btn-primary' : '' }">10</a>
    <a href="home?numPage=1&elements=50" type="button" class="btn btn-default${ 50 == page.elements.size() ? ' btn-primary' : '' }">50</a>
    <a href="home?numPage=1&elements=100" type="button" class="btn btn-default${ 100 == page.elements.size() ? ' btn-primary' : '' }">100</a>
</div>
