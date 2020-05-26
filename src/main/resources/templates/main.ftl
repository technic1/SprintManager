<#import "parts/common.ftl" as c>

<@c.page>

    <div>Add task</div>
    <form method="post">
        <input type="text" name="title" placeholder="Enter task title">
        <input type="date" name="start" placeholder="Enter task start date">
        <select size="2" name="priority">
            <option disabled>Choose priority</option>
            <option selected value="LOW">Low</option>
            <option value="HIGH">High</option>
        </select>
        <input type="text" name="rate" placeholder="Enter rate">
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
            <br>
        </#list>
    </div>

</@c.page>