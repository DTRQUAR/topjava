<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <%--http://stackoverflow.com/questions/10327390/how-should-i-get-root-folder-path-in-jsp-page--%>
        <script type="text/javascript" src="webjars/jquery/2.1.4/jquery.min.js"></script>
        <script type="text/javascript" src="webjars/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="webjars/datetimepicker/2.3.4/jquery.datetimepicker.js"></script>
        <script type="text/javascript" src="webjars/datatables/1.10.9/js/jquery.dataTables.min.js"></script>
        <script type="text/javascript" src="webjars/noty/2.2.4/jquery.noty.packaged.min.js"></script>
        <script type="text/javascript" src="resources/js/datatablesUtil.js"></script>

<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <div class="view-box">
                <h3><a href="${pageContext.request.contextPath}">Home</a></h3>
                <h3><fmt:message key="meals.title"/></h3>
                <form method="post" action="meals/filter">
                    <dl>
                        <dt>From Date:</dt>
                        <dd><input type="date" name="startDate" value="${startDate}"></dd>
                    </dl>
                    <dl>
                        <dt>To Date:</dt>
                        <dd><input type="date" name="endDate" value="${endDate}"></dd>
                    </dl>
                    <dl>
                        <dt>From Time:</dt>
                        <dd><input type="time" name="startTime" value="${startTime}"></dd>
                    </dl>
                    <dl>
                        <dt>To Time:</dt>
                        <dd><input type="time" name="endTime" value="${endTime}"></dd>
                    </dl>
                    <button type="submit">Filter</button>
                </form>
                <%--<hr>--%>
                <%--<a href="meals/create">Add Meal</a>--%>
                <%--<hr>--%>

                <a class="btn btn-sm btn-info" id="add"><fmt:message key="meals.add"/></a>

                    <table class="table table-striped display" id="datatable">
                    <thead> <!--Если удалить внешне страница не изменится -->
                        <tr>
                        <th>Date</th>
                        <th>Description</th>
                        <th>Calories</th>
                        <th></th>
                        <th></th>
                        </tr>
                        </thead> <!--Если удалить внешне страница не изменится -->
                        <c:forEach items="${mealList}" var="meal">
                            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.UserMealWithExceed"/>
                            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                                <td>
                                        <%--<fmt:parseDate value="${meal.dateTime}" pattern="y-M-dd'T'H:m" var="parsedDate"/>--%>
                                        <%--<fmt:formatDate value="${parsedDate}" pattern="yyyy.MM.dd HH:mm" />--%>
                                    <%=TimeUtil.toString(meal.getDateTime())%>
                                </td>
                                <td>${meal.description}</td>
                                <td>${meal.calories}</td>
                                <td><a class="btn btn-xs btn-primary edit" href="meals/update?id=${meal.id}">Update</a></td>
                                <td><a class="btn btn-xs btn-primary delete" href="meals/delete?id=${meal.id}">Delete</a></td>
                            </tr>
                    </c:forEach>
                    </table>
            </div>
        </div>
    </div>
</div>
</section>
<jsp:include page="fragments/footer.jsp"/>

<div class="modal fade" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title"><fmt:message key="meals.edit"/></h2>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" method="post" id="detailsForm">
                    <input type="text" hidden="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="date" class="control-label col-xs-3">Date</label>

                        <div class="col-xs-9">
                            <input type="datetime-local" class="form-control" id="date" name="date" placeholder="Date">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="description" class="control-label col-xs-3">Description</label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="description" name="description" placeholder="Description">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="calories" class="control-label col-xs-3">Calories</label>

                        <div class="col-xs-9">
                            <input type="number" class="form-control" id="calories" name="calories" placeholder="Calories">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button type="submit" class="btn btn-primary">Save</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

</body>


<script type="text/javascript">

    var ajaxUrl = 'ajax/admin/meals/';
    var oTable_datatable;
    var oTable_datatable_params;

    //            $(document).ready(function () {
    $(function () {
        oTable_datatable = $('#datatable');
        oTable_datatable_params = {
            "bPaginate": false,
            "bInfo": false,
            "aoColumns": [
                {
                    "mData": "date"
                },
                {
                    "mData": "description"
                },
                {
                    "mData": "calories"
                },
                {
                    "sDefaultContent": "",
                    "bSortable": false
                },
                {
                    "sDefaultContent": "",
                    "bSortable": false
                }
            ],
            "aaSorting": [
                [
                    0,
                    "asc"
                ]
            ]
        };

        oTable_datatable.dataTable(oTable_datatable_params);
        makeEditable();
    });
</script>
</html>
