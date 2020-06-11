<#import "parts/common.ftl" as c>

<@c.page>
<#if Session?? &&  Session.SPRING_SECURITY_LAST_EXCEPTION??>
    <div class="alert alert-danger" role="alert">
        ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
    </div>
</#if>
<br>
<form action="/login" method="post">
    <div class="form-group row">
        <label class="col-sm-2 col-form-label"> User Name: </label>
        <div class="col-sm-4">
            <input type="text" name="username" value="" class="form-control " placeholder="User name" />
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Password: </label>
        <div class="col-sm-4">
            <input type="password" name="password" class="form-control " placeholder="Password" />
        </div>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <a href="/registration" class="link">Sign up</a>
    <button class="btn btn-primary" type="submit">Sign In</button>
</form>

</@c.page>