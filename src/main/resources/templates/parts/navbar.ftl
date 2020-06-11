<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">SprintManager</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="/">Backlog <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="/newsprint">New sprint<span class="sr-only">(current)</span></a>
            </li>

                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Sprints
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <#if sprints??>
                            <#list sprints as sprint>
                        <a class="dropdown-item <#if sprint.sprintState == "STARTED">text-success
                        <#elseif sprint.sprintState == "FINISHED">text-danger
                        <#else>text-muted
                    </#if>" href="/sprint/${sprint.id}">${sprint.title}</a>
                            </#list>
                        </#if>
                    </div>
                </li>

        </ul>
        <#if user??>
            <div class="navbar-text mr-3">${user.username}</div>
            <form action="/logout" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <button type="submit" class="btn btn-primary">Sign Out</button>
            </form>
        </#if>

    </div>

</nav>