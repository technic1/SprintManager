<#import "parts/common.ftl" as c>

<@c.page>

    <div>Add task</div>
    <form name="form1" method="post" action="?add">
        <input type="text" name="title" placeholder="Enter task title">
        <!--<input type="date" name="start" placeholder="Enter task start date">-->
        <select size="2" name="priority">
            <option disabled>Choose priority</option>
            <option selected value="LOW">Low</option>
            <option value="HIGH">High</option>
        </select>
        <input type="text" name="estimate" placeholder="Enter estimate">
        <button type="submit">Add task</button>
    </form>
    <br>
    Backlog:
        <#list tasks as task>
            <div>
                Task number: ${task.number}
            </div>
            <div>
                title: ${task.title}
            </div>
            <div>
                author: ${task.authorName}
            </div>
            <div>
                 priority: ${task.taskPriority}
            </div>
            <div>
                state: ${task.taskState}
            </div>
            <div>
                estimate: ${task.estimate}
            </div>
            <#if task.startDate??>
                <div>
                    Start date: ${task.startDate}
                </div>
            </#if>
            <#if task.endDate??>
                <div>
                    End date: ${task.endDate}
                </div>
            </#if>
            <form name="form2" method="post" action="?delete">
                <input hidden name="id" type="text" value="${task.id}">
                <button type="submit">Delete task</button>
            </form>
            <br>
        </#list>
    </div>

</@c.page>