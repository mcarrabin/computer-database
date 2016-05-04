<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet" media="screen">
<link
	href="${pageContext.request.contextPath}/resources/css/font-awesome.css"
	rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/resources/css/main.css"
	rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="${ pageContext.request.contextPath }/home"> Application -
				Computer Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>Add Computer</h1>
					<form action="add" method="POST" id="add-form">
						<fieldset>

							<div class="form-group" id="computerNameDiv">
								<label for="computerName">Computer name</label> <input
									type="text" class="form-control" id="computerName"
									placeholder="Computer name" name="computerName" required>
							</div>
							<div class="form-group" id="introducedDiv">
								<label for="introduced">Introduced date (dd/MM/yyyy)</label> <input
									type="date" class="form-control" id="introduced"
									placeholder="Introduced date" name="introduced">
							</div>
							<div class="form-group" id="discontinuedDiv">
								<label for="discontinued">Discontinued date (dd/MM/yyyy)</label> <input
									type="date" class="form-control" id="discontinued"
									placeholder="Discontinued date" name="discontinued">
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> <select
									class="form-control" id="companyId" name="companyId">
									<c:forEach items="${companies}" var="company">
										<option value="${company.id}">${company.name}</option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Add" class="btn btn-primary" id="add-button">
							or <a href="${pageContext.request.contextPath}/home" class="btn btn-default">Cancel</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
	<script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/dashboard.min.js"></script>
	<script type="text/javascript">
	$(function() {
		$("#add-form").on("submit", function (e) {
			if($('#computerName').parent().hasClass("has-error") 
					|| $('#introduced').parent().hasClass("has-error") 
					|| $('#discontinued').parent().hasClass("has-error")) 
			{
				e.preventDefault();
			}
		});
		
		$('#computerName').focusout(function() {
			if( !$(this).val()) {
				$(this).parent().addClass('has-error');
			} else {
				$(this).parent().removeClass('has-error');
			}
		});
		
		var regExp = '(0[1-9]|[12][0-9]|3[01])[\/](0[1-9]|1[012])[\/](19[7-9][0-9]|20[0-3][0-9])';
		$('#introduced').focusout(function() {
			if((!$(this).val().match(regExp)) && ($(this).val().length > 0)) {
				$(this).parent().addClass('has-error');
			} else {
				$(this).parent().removeClass('has-error');
			}
		});

		$('#discontinued').focusout(function() {
			if((!$(this).val().match(regExp)) && ($(this).val().length > 0)) {
				$(this).parent().addClass('has-error');
			} else {
				$(this).parent().removeClass('has-error');
			}
		});
		
		
	});
	</script>
</body>
</html>