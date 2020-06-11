<#import "parts/common.ftl" as c>

<@c.page>
<#if errorUsernameAlreadyExists??>
    <div class="alert alert-warning" role="alert">
        ${errorUsernameAlreadyExists}
    </div>
</#if>
<br>
<form action="/registration/add" method="post">
    <div class="form-group row">
        <label class="col-sm-2 col-form-label"> User Name: </label>
        <div class="col-sm-4">
            <input type="text" name="username" value="" class="form-control " placeholder="User name"/>
        </div>
    </div>
    <div class="form-group row">
        <label for="input-select-priority" class="col-2 col-form-label">User role</label>
        <div class="col-4">
            <select class="form-control" id="input-select-priority" name="role">
                <option selected>Choose role</option>
                <option value="DEVELOPER">Developer</option>
                <option value="ANALYST">Business Analyst</option>
                <option value="MANAGER">Project Manager</option>
            </select>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Password: </label>
        <div class="col-sm-4">
            <input type="password" name="password" class="form-control " placeholder="Password" />
        </div>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <button class="btn btn-primary" type="submit">Sign up</button>
</form>

</@c.page>