<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>宠物商店</title>
    <style type="text/css">
        table {
            width: 80%;
            margin: auto;
        }

        td, th {
            border: 1px solid;
            text-align: center;
        }

        h1 {
            color: red;
            text-align: center;
        }

        div {
            width: 80%;
            margin: auto;
            margin-bottom: 20px;
        }

        #status {
            font-size: 12px;
        }
    </style>
</head>
<body>
<h1></h1>
<div>
    编号：<input type="text" id="petId"/>
    <button id="findById">查询</button>
    状态：
    <select id="status">
        <option value="available">available</option>
        <option value="pending">pending</option>
        <option value="sold">sold</option>
    </select>
</div>
<div>
    <form id="form">
        <input type="hidden" name="id" value="0"/>
        品种：<select name="category_id"></select><br/>
        名称：<input type="text" name="name"/><br/>
        种类：<select name="tag_id"></select><br/>
        状态：<select name="status">
        <option value="available">available</option>
        <option value="pending">pending</option>
        <option value="sold">sold</option>
    </select>
        <input type="button" value="添加" id="btnAdd"/>
        <input type="button" value="提交" id="btnPut"/>
    </form>
</div>

<div>
    <form method="post" enctype="multipart/form-data" id="upImg">
        编号：<input type="text" name="id" id="id"/>
        <label>图片上传:</label>
        <input type="file" name="file"><br/>
        <input type="submit" value="提交" id="btnUp"/>
    </form>
</div>
<table>
    <tr>
        <th>编号</th>
        <th>品种</th>
        <th>名字</th>
        <th>图片</th>
        <th>种类</th>
        <th>状态</th>
        <th>操作</th>
    </tr>
</table>
</body>
<script type="text/javascript" src="/js/jquery-1.11.3.min.js"></script>
<script>
    var petIndex = {
        errorData: function () {
            $.ajaxSetup({
                error: function (XMLHttpRequest) {
                    $("h1").empty();
                    $("h1").html("Error Message：" + XMLHttpRequest.responseJSON.message);
                    // console.log(XMLHttpRequest);
                    // console.log(XMLHttpRequest.status+XMLHttpRequest.responseJSON.message);
                }
            })
        },
        blindSelect: function () {
            var html = "";
            $.get("/tag", "", function (data) {
                $.each(data, function (i, o) {
                    html += "<option value=" + o.id + ">" + o.name + "</option>";
                });
                $("select[name='tag_id']").append(html);
            });

            $.get("/category", "", function (data) {
                html = "";
                $.each(data, function (i, o) {
                    html += "<option value=" + o.id + ">" + o.name + "</option>";
                });
                $("select[name='category_id']").append(html);
            });

        },
        blindTable: function (data) {
            $("#btnPut").hide();
            $("table tr:gt(0)").remove();
            $.each(data, function (index, obj) {
                var tr = $("<tr/>");
                $("<td/>").html(obj.id).appendTo(tr);
                $("<td/>").html(obj.category.name).appendTo(tr);
                $("<td/>").html(obj.name).appendTo(tr);
                $("<td/>").html(obj.photo_urls).appendTo(tr);
                //$("<td/>").html("<img src="+obj.photo_urls+" />").appendTo(tr);
                $("<td/>").html(obj.tag.name).appendTo(tr);
                $("<td/>").html(obj.status).appendTo(tr);
                var td = $("<td/>");
                $("<input type='button' value='删除'>").data("id", obj.id).appendTo(td);
                $("<input type='button' value='修改'>").data("pet", obj).appendTo(td);
                $("<input type='button' value='上传图片'>").data("id", obj.id).appendTo(td);
                td.appendTo(tr);
                $("table").append(tr);
            });
        },
        selectAll: function () {
            $.post("/pet/all", "", function (data) {
                petIndex.blindTable(data);
            });
        },
        findById: function () {
            $("#findById").click(function () {
                $.ajax({
                    type: "get",
                    url: "/pet/" + $("#petId").val()
                }).success(function (data) {
                    $("table tr:gt(0)").remove();
                    var tr = $("<tr/>");
                    $("<td/>").html(data.id).appendTo(tr);
                    $("<td/>").html(data.category.name).appendTo(tr);
                    $("<td/>").html(data.name).appendTo(tr);
                    $("<td/>").html(data.photo_urls).appendTo(tr);
                    //$("<td/>").html("<img src="+data.photo_urls+" />").appendTo(tr);
                    $("<td/>").html(data.tag.name).appendTo(tr);
                    $("<td/>").html(data.status).appendTo(tr);
                    var td = $("<td/>");
                    $("<input type='button' value='删除'>").data("id", obj.id).appendTo(td);
                    $("<input type='button' value='修改'>").data("pet", obj).appendTo(td);
                    $("<input type='button' value='上传图片'>").data("id", obj.id).appendTo(td);
                    td.appendTo(tr);
                    $("table").append(tr);
                });
            });
        },
        selectByStatus: function () {
            $("#status").click(function () {
                $.ajax({
                    type: "get",
                    url: "/pet/findByStatus",
                    data: "status=" + $("#status").val()
                }).success(function (data) {
                    petIndex.blindTable(data);
                });
            });
        },
        insert: function () {
            $("#btnAdd").click(function () {
                $.ajax({
                    type: "post",
                    url: "/pet",
                    data: $("#form").serialize()
                }).success(function () {
                    petIndex.selectAll();
                });
            });
        },
        delete: function () {
            $("table").on("click", "input[value='删除']", function () {
                $.ajax({
                    type: "delete",
                    url: "/pet/" + $(this).data("id")
                }).success(function () {
                    petIndex.selectAll();
                });
            });
        },
        pet: null,
        getPet: function () {
            $("table").on("click", "input[value='修改']", function () {
                petIndex.pet = $(this).data("pet");
                $("input[name='id']").val(petIndex.pet.id);
                $("select[name='category_id']").val(petIndex.pet.category.id);
                $("select[name='tag_id']").val(petIndex.pet.tag.id);
                $("input[name='name']").val(petIndex.pet.name);
                $("select[name='status']").val(petIndex.pet.status);
                $("#btnAdd").hide();
                $("#btnPut").show();
            });
        },
        putPet: function () {
            $("#btnPut").click(function () {
                $.ajax({
                    type: "put",
                    url: "/pet",
                    data: $("#form").serialize()
                });
                petIndex.pet = null;
                $("input[name='id']").val(0);
                petIndex.selectAll();
            });
        },
        postUpdPet: function () {
            $("#btnPut").click(function () {
                $.ajax({
                    type: "post",
                    url: "/pet/" + petIndex.pet.id,
                    data: $("#form").serialize()
                });
                petIndex.pet = null;
                $("input[name='id']").val(0);
                petIndex.selectAll();
            });
        },
        getId:function(){
            $("table").on("click", "input[value='上传图片']", function () {
                var id = $(this).data("id");
                $("#id").val(id);
                $("#upImg").attr("action","/pet/"+id+"/uploadImage");
                console.log($("#upImg").attr("action"));
            });
        },
        init: function () {
            petIndex.errorData();
            petIndex.selectAll();
            petIndex.findById();
            petIndex.selectByStatus();
            petIndex.blindSelect();
            petIndex.insert();
            petIndex.delete();
            petIndex.getPet();
            petIndex.putPet();
            //petIndex.postUpdPet();
            petIndex.getId();
        }
    }
    petIndex.init();
</script>
</html>
