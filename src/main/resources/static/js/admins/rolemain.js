/*!
 * user main JS.
 */
"use strict";
//# sourceURL=main.js

// DOM 加载完再执行
$(function () {

    var _pageSize; // 存储用于搜索

    // 根据角色名、页面索引、页面大小获取用户列表
    function getRoleByName(pageIndex, pageSize) {
        $.ajax({
            url: "/roles",
            contentType: 'application/json',
            data: {
                "async": true,
                "pageIndex": pageIndex,
                "pageSize": pageSize,
                "name": $("#searchName").val()
            },
            success: function (data) {
                $("#mainContainer").html(data);
            },
            error: function () {
                toastr.error("error!");
            }
        });
    }

    // 分页
    $.tbpage("#mainContainer", function (pageIndex, pageSize) {
        getRoleByName(pageIndex, pageSize);
        _pageSize = pageSize;
    });

    // 搜索
    $("#searchNameBtn").click(function () {
        getRoleByName(0, _pageSize);
    });

    // 获取添加角色的界面
    $("#addRole").click(function () {
        $.ajax({
            url: "/roles/add",
            type: "GET",
            success: function (data) {
                $("#roleFormContainer").html(data);
            },
            error: function (data) {
                toastr.error("error!");
            }
        });
    });

    // 获取编辑角色的界面
    $("#rightContainer").on("click", ".blog-edit-role", function () {
        $.ajax({
            url: "/roles/edit/" + $(this).attr("roleId"),
            success: function (data) {
                $("#roleFormContainer").html(data);
            },
            error: function () {
                toastr.error("error!");
            }
        });
    });

    // 提交变更后，清空表单
    $("#submitEdit").click(function () {
        $.ajax({
            url: "/roles",
            type: 'POST',
            data: $('#roleForm').serialize(),
            success: function (data) {
                $('#roleForm')[0].reset();
                if (data.success) {
                    // 从新刷新主界面
                    getRoleByName(0, _pageSize);
                } else {
                    toastr.error(data.message);
                }
            },
            error: function () {
                toastr.error("error!");
            }
        });
    });

    // 删除角色
    $("#rightContainer").on("click", ".blog-delete-role", function () {
        $.ajax({
            url: "/roles/" + $(this).attr("roleId"),
            type: 'DELETE',
            success: function (data) {
                if (data.success) {
                    // 从新刷新主界面
                    getRoleByName(0, _pageSize);
                } else {
                    toastr.error(data.message);
                }
            },
            error: function () {
                toastr.error("error!");
            }
        });
    });


    /*//点击全选的判断
     $("#checkAll").click(function () {
     //看全选 是否被选中
     alert(2)
     var ischeckAll = $(this).attr("checked");
     var checkBoxAll = $("input[name='permissions']");
     if (ischeckAll == "checked") {
     //全部选中
     $.each(checkBoxAll, function (i, checkbox) {
     $(checkbox).attr("checked", true);
     });
     } else {
     //全不选
     $.each(checkBoxAll, function (i, checkbox) {
     $(checkbox).attr("checked", false);
     });
     }
     });*/
});