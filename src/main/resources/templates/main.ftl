<#import "parts/common.ftl" as c>

<@c.page>

    <div>Tasks</div>
    <form method="post">
        <input type="text" name="number" placeholder="Enter task number">
        <input type="text" name="start" placeholder="Enter task start data">
        <input type="text" name="end" placeholder="Enter task end data">
        <button type="submit">Add task</button>
    </form>

    Backlog:
        <#list tasks as task>
            <div>
                Task number: ${task.number}
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
        </#list>
    </div>

</@c.page>