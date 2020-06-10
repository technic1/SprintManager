<#import "parts/common.ftl" as c>

<@c.page>
<#if errorOneActiveSprint??>
    <div class="alert alert-warning" role="alert">
        ${errorOneActiveSprint}
    </div>
</#if>
<#if errorNoTasksInSprint??>
<div class="alert alert-warning" role="alert">
    ${errorNoTasksInSprint}
</div>
</#if>
<#if errorSetFinished??>
    <div class="alert alert-warning" role="alert">
        ${errorSetFinished}
    </div>
</#if>
<#if errorDate??>
<div class="alert alert-warning" role="alert">
    ${errorDate}
</div>
</#if>
<table class="table table-hover">
    <thead>
        <tr>
            <th scope="col">Title</th>
            <th scope="col">Author</th>
            <th scope="col">State</th>
            <th scope="col">Start date</th>
            <th scope="col">Expected end date</th>
            <th scope="col">Actual end date</th>
            <th scope="col">Tasks</th>
            <th scope="col">Estimate</th>
            <th></th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>${sprint.title}</td>
            <td>${sprint.authorName}</td>
            <td>${sprint.sprintState}</td>
            <td>${sprint.startDate}</td>
            <td>${sprint.endDateExpect}</td>
            <td>
                <#if sprint.endDateFact??>
                    ${sprint.endDateFact}
                </#if>
            </td>
            <td>
                ${sprint.countTasks}
            </td>
            <td>${sprint.estimate}</td>
            <td>
                <a class="btn btn-primary" data-toggle="collapse" href="#sprint${sprint.id}" role="button" aria-expanded="false" aria-controls="sprint${sprint.id}">
                    Edit
                </a>
            </td>
        </tr>
        <tr class="collapse multi-collapse" id="sprint${sprint.id}">
            <td colspan="9">
                <form name="form2" method="post" action="/sprint/${sprint.id}/edit-sprint">
                    <div class="form-group row">
                        <label for="formGroupExampleInput" class="col-2 col-form-label">Title</label>
                        <div class="col-4">
                            <input type="text" class="form-control" id="formGroupExampleInput" name="title" placeholder="Enter title">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="example-datetime-local-input" class="col-2 col-form-label">Start date</label>
                        <div class="col-4">
                            <input class="form-control" type="date" name="startDate" id="example-datetime-local-input">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="example-date-input" class="col-2 col-form-label">Expected end date</label>
                        <div class="col-4">
                            <input class="form-control" type="date" name="endDateExpect" id="example-date-input">
                        </div>
                    </div>
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <button type="submit" class="btn btn-primary">Edit sprint</button>
                </form>
            </td>
        </tr>
    </tbody>
</table>
<#if sprint.sprintState != "STARTED" && sprint.sprintState != "FINISHED">
    <form name="form2" method="post" action="/sprint/${sprint.id}/start">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button class="btn btn-primary" type="submit">Start</button>
    </form>
</#if>
<br>
<#if sprint.sprintState != "FINISHED" && sprint.sprintState != "DRAFT">
    <form name="form2" method="post" action="/sprint/${sprint.id}/finish">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button class="btn btn-primary" type="submit">Close</button>
    </form>
</#if>
<a class="btn " data-toggle="collapse" href="#multiCollapseExample" role="button" aria-expanded="false" aria-controls="multiCollapseExample">
    Add task from backlog
</a>
<div class="collapse multi-collapse" id="multiCollapseExample">
    <table class="table table-striped">
        <thead>
            <tr>
                <th scope="col">Number</th>
                <th scope="col">Title</th>
                <th scope="col">Author</th>
                <th scope="col"></th>
            </tr>
        </thead>
        <tbody>
            <#list backlogTasks as task>
                <tr>
                    <td>${task.number}</td>
                    <td>${task.title}</td>
                    <td>${task.authorName}</td>
                    <td>
                        <form name="form1" method="post" action="/sprint/${sprint.id}/add">
                            <input hidden value="${task.id}" name="taskId">
                            <input type="hidden" name="_csrf" value="${_csrf.token}">
                            <button class="btn btn-primary" type="submit">Add</button>
                        </form>
                    </td>
                </tr>
            </#list>
        </tbody>
    </table>
</div>
<h3>Sprint Backlog:</h3>
<table class="table table-hover">
    <thead>
    <tr>
        <th scope="col">Number</th>
        <th scope="col">Title</th>
        <th scope="col">Author</th>
        <th scope="col">Priority</th>
        <th scope="col">State</th>
        <th scope="col">Estimate</th>
        <th scope="col">Created</th>
        <th scope="col">Closed</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <#list sprintTasks as task>
        <tr>
            <td>${task.number}</td>
            <td>${task.title}</td>
            <td>${task.authorName}</td>
            <td>${task.taskPriority}</td>
            <td>${task.taskState}</td>
            <td>${task.estimate}</td>
            <td>${task.startDate}</td>
            <td>
                <#if task.endDate??>
                ${task.endDate}
            </#if>
            </td>
            <td>
                <a class="btn btn-primary" data-toggle="collapse" href="#task${task.id}" role="button" aria-expanded="false" aria-controls="task${task.id}">
                    Edit
                </a>
            </td>
        </tr>
        <tr class="collapse multi-collapse" id="task${task.id}">
            <td colspan="7">
                <div class="">
                    <form method="post" action="/sprint/${sprint.id}/edit">
                        <input hidden name="id" type="text" value="${task.id}">
                        <input hidden name="authorName" type="text" value="${task.authorName}">
                        <div class="form-group col-sm-6">
                            <input class="form-control" id="exampleFormControlInput1" type="text" name="title" placeholder="Enter task title">
                        </div>
                        <div class="form-group col-sm-6">
                            <select class="form-control" id="exampleFormControlSelect1" size="2" name="priority">
                                <option disabled>Choose priority</option>
                                <option value="LOW">Low</option>
                                <option value="HIGH">High</option>
                            </select>
                        </div>
                        <div class="form-group col-sm-6">
                            <select class="form-control" id="exampleFormControlSelect1" size="3" name="state">
                                <option disabled>Edit state</option>
                                <option value="OPEN">Open</option>
                                <option value="CLOSED">Close</option>
                            </select>
                        </div>
                        <div class="form-group col-sm-6">
                            <input class="form-control" type="text" name="estimate" placeholder="Enter estimate">
                        </div>
                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                        <button class="btn btn-primary" type="submit">Edit</button>
                </div>
                </form>
                </div>
            </td>
            <td colspan="2">
                <form name="form2" method="post" action="/sprint/${sprint.id}/delete">
                    <input hidden name="taskId" type="text" value="${task.id}">
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <button class="btn btn-primary" type="submit">Delete from sprint</button>
                </form>
            </td>
        </tr>
    </#list>
    </tbody>
</table>


</@c.page>