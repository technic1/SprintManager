<#import "parts/common.ftl" as c>

<@c.page>
<br>
<form method="post" action="/new">
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
    <button type="submit" class="btn btn-primary">Add</button>
</form>
</@c.page>