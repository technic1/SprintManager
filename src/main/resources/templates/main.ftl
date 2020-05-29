<#import "parts/common.ftl" as c>

<@c.page>
    <h3>Sprints:</h3>
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
            </tr>
        </thead>
        <tbody>
        <#list sprints as sprint>
                <tr onclick="window.location.href='/sprint/${sprint.id}'; return false">
                    <td>
                            ${sprint.title}
                    </td>
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
                </tr>
        </#list>
    </table>
    <hr>
    <a class="btn " data-toggle="collapse" href="#multiCollapseExample" role="button" aria-expanded="false" aria-controls="multiCollapseExample">
        New task
    </a>
    <div class="collapse multi-collapse" id="multiCollapseExample">
        <form name="form1" method="post" action="?add">
            <div class="form-group col-sm-4">
                <input class="form-control" type="text" name="title" placeholder="Enter task title">
            </div>
            <div class="form-group col-sm-4">
                <select class="form-control" size="2" name="priority">
                    <option disabled>Choose priority</option>
                    <option selected value="LOW">Low</option>
                    <option value="HIGH">High</option>
                </select>
            </div>
            <div class="form-group col-sm-4">
                <input class="form-control" type="text" name="estimate" placeholder="Enter estimate">
            </div>
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
                <th scope="col">Start</th>
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
            <td colspan="6">
                <div class="">
                    <form method="post" action="?edit">

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
                        <button class="btn btn-primary" type="submit">Edit</button>
                        </div>
                    </form>
                </div>
            </td>
            <td colspan="2">
                <form name="form2" method="post" action="?delete">
                    <input hidden name="id" type="text" value="${task.id}">
                    <button class="btn btn-primary" type="submit">Delete</button>
                </form>
            </td>
        </tr>
        </#list>
        </tbody>
    </table>


</@c.page>