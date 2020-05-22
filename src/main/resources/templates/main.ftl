<#import "parts/common.ftl" as c>

<@c.page>

    <div>Tasks</div>
    <form method="post">
        <input type="text" name="number" placeholder="Enter task number">
        <input type="text" name="start" placeholder="Enter task start data">
        <input type="text" name="end" placeholder="Enter task end data">
        <button type="submit">Add task</button>
    </form>

    List of tasks:
    <div>
        <#list tasks as task>
        <div>
            Task number: ${task.number}
        </div>
        <div>
            Start date:${task.start}
        </div>
        <div>
            End date${task.end}
        </div>
        </#list>
    </div>

</@c.page>