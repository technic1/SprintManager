<#import "parts/common.ftl" as c>

<@c.page>
<#if errorAccessDenied??>
    <div class="alert alert-warning" role="alert">
        ${errorAccessDenied}
    </div>
</#if>
<br>
<#if sprint??>
    <a data-toggle="collapse" href="#multiCollapseExample1" role="button" aria-expanded="false" aria-controls="multiCollapseExample1">
        <h4>Active sprint: ${sprint.title}</h4>
    </a>
<div class="collapse multi-collapse" id="multiCollapseExample1">
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
            <th scope="col">End</th>
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
                <a class="btn btn-primary" data-toggle="collapse" href="#multiCollapseExample${task.id}" role="button" aria-expanded="false" aria-controls="multiCollapseExample${task.id}">
                    Edit
                </a>
            </td>
        </tr>
        <tr class="collapse multi-collapse" id="multiCollapseExample${task.id}">
            <td colspan="7">
                <div class="">
                    <form method="post">

                        <input hidden name="id" type="text" value="${task.id}">
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
                        <button action="/edit" class="btn btn-primary" type="submit">Edit</button>
                </div>
                </form>
                </div>
            </td>
            <td colspan="2">
                <form name="form2" method="post">
                    <input hidden name="taskId" type="text" value="${task.id}">
                    <button action="delete" class="btn btn-primary" type="submit">Delete from sprint</input>
                </form>
            </td>
        </tr>
        </#list>
        </tbody>
    </table>
</div>
</#if>
<div>
    <hr>
    <a class="btn " data-toggle="collapse" href="#multiCollapseExample" role="button" aria-expanded="false" aria-controls="multiCollapseExample">
        New task
    </a>
    <div class="collapse multi-collapse" id="multiCollapseExample">
        <form name="form1" method="post" action="/add">
            <div class="form-group row">
                <label for="input-title" class="col-2 col-form-label">Title</label>
                <div class="col-4">
                    <input class="form-control" type="text" name="title" id="input-title" placeholder="Enter task title">
                </div>
            </div>
            <div class="form-group row">
                <label for="input-select-priority" class="col-2 col-form-label">Priority</label>
                <div class="col-4">
                    <select class="form-control" size="2" id="input-select-priority" name="priority">
                        <option disabled>Choose priority</option>
                        <option selected value="LOW">Low</option>
                        <option value="HIGH">High</option>
                    </select>
                </div>
            </div>
            <div class="form-group row">
                <label for="input-estimate" class="col-2 col-form-label">Estimate</label>
                <div class="col-4">
                    <input class="form-control" type="text" name="estimate" id="input-estimate" placeholder="Enter estimate">
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <button class="btn btn-primary" type="submit">Add task</button>
        </form>
    </div>
    <h3>Backlog:</h3>
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
                <th scope="col">End</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
        <#list tasks as task>
                <tr>
                    <td>${task.number}</td>
                    <td>${task.title}</td>
                    <td>${task.authorName}</td>
                    <td>${task.taskPriority}</td>
                    <td>
                        ${task.taskState}
                        <#if task.taskState == "OPEN">
                            <form name="form2" method="post" action="/close">
                                <input hidden name="id" type="text" value="${task.id}">
                                <input type="hidden" name="_csrf" value="${_csrf.token}">
                                <button class="btn btn-primary" type="submit">Close</button>
                            </form>
                        </#if>
                    </td>
                    <td>${task.estimate}</td>
                    <td>${task.startDate}</td>
                    <td>
                        <#if task.endDate??>
                        ${task.endDate}
                        </#if>
                    </td>
                    <td>
                        <a class="btn btn-primary" data-toggle="collapse" href="#multiCollapseExample${task.id}" role="button" aria-expanded="false" aria-controls="multiCollapseExample${task.id}">
                            Edit
                        </a>
                    </td>
                </tr>
        <tr class="collapse multi-collapse" id="multiCollapseExample${task.id}">
            <td colspan="6">
                <div class="">
                    <form method="post" action="/edit">

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
                <form name="form2" method="post" action="/delete">
                    <input hidden name="id" type="text" value="${task.id}">
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <button class="btn btn-primary" type="submit">Delete</button>
                </form>
            </td>
        </tr>
        </#list>
        </tbody>
    </table>
</div>
</@c.page>