/*!
 * user main JS.
 */
"use strict";
//# sourceURL=main.js

// DOM 加载完再执行
$(function () {

    var _pageSize; // 存储用于搜索

    // 根据用户名、页面索引、页面大小获取用户列表
    function getResourcesByName(pageIndex, pageSize) {
        $.ajax({
            url: "/resources",
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
        getResourcesByName(pageIndex, pageSize);
        _pageSize = pageSize;
    });

    // 搜索
    $("#searchNameBtn").click(function () {
        getResourcesByName(0, _pageSize);
    });

    // 获取添加用户的界面
    $("#addResources").click(function () {
        $.ajax({
            url: "/resources/add",
            success: function (data) {
                $("#resourcesFormContainer").html(data);
            },
            error: function (data) {
                toastr.error("error!");
            }
        });
    });

    // 获取编辑用户的界面
    $("#rightContainer").on("click", ".blog-edit-resources", function () {
        $.ajax({
            url: "/resources/edit/" + $(this).attr("resourcesId"),
            success: function (data) {
                $("#resourcesFormContainer").html(data);
            },
            error: function () {
                toastr.error("error!");
            }
        });
    });

    // 提交变更后，清空表单
    $("#submitEdit").click(function () {
        $.ajax({
            url: "/resources",
            type: 'POST',
            data: $('#resourcesForm').serialize(),
            success: function (data) {
                $('#resourcesForm')[0].reset();

                if (data.success) {
                    $("#searchName").val('');
                    // 从新刷新主界面
                    getResourcesByName(0, _pageSize);
                } else {
                    toastr.error(data.message);
                }
            },
            error: function () {
                toastr.error("error!");
            }
        });
    });

    // 删除用户
    $("#rightContainer").on("click", ".blog-delete-resources", function () {
        $.ajax({
            url: "/resources/" + $(this).attr("resourcesId"),
            type: 'DELETE',
            success: function (data) {
                if (data.success) {
                    // 从新刷新主界面
                    getResourcesByName(0, _pageSize);
                } else {
                    toastr.error(data.message);
                }
            },
            error: function () {
                toastr.error("error!");
            }
        });
    });
});