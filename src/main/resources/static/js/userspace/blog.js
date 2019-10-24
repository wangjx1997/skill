/*!
 * article.html 页面脚本.
 */
"use strict";
//# sourceURL=blog.js

// DOM 加载完再执行
$(function () {



    //生成目录
    $.catalog("#catalog", ".post-content");

    //目录滚动
    $(this).scroll(function () {
        var bodyTop = 0;
        if (typeof window.pageYOffset != 'undefined') {
            bodyTop = window.pageYOffset;
        }
        else if (typeof document.compatMode != 'undefined' && document.compatMode != 'BackCompat') {
            bodyTop = document.documentElement.scrollTop;
        }
        else if (typeof document.body != 'undefined') {
            bodyTop = document.body.scrollTop;
        }
        $("#mesBox").css("top", bodyTop) // 设置层的CSS样式中的top属性, 注意要是小写，要符合“标准”
    });

    // 处理删除技术文章事件

    $(".blog-content-container").on("click", ".blog-delete-blog", function () {
        $.ajax({
            url: $(this).attr("blogUrl"),
            type: 'DELETE',
            success: function (data) {
                if (data.success) {
                    // 成功后，重定向
                    window.location = data.body;
                } else {
                    toastr.error(data.message);
                }
            },
            error: function () {
                toastr.error("error!");
            }
        });
    });

    // 获取评论列表
    function getCommnet(blogId) {
        $.ajax({
            url: '/comments',
            type: 'GET',
            data: {"blogId": blogId},
            success: function (data) {
                    $("#mainContainer").html(data);
            },
            error: function () {
                toastr.error("error!");
            }
        });
    }

    // 提交评论
    $(".blog-content-container").on("click", "#submitComment", function () {
        $.ajax({
            url: '/comments',
            type: 'POST',
            data: {"blogId": blogId, "commentContent": $('#commentContent').val()},
            success: function (data) {
                if (data.success) {
                    // 清空评论框
                    $('#commentContent').val('');
                    toastr.info(data.message);
                    // 获取评论列表
                    getCommnet(blogId);
                } else {
                    toastr.error(data.message);
                }
            },
            error: function () {
                toastr.error("error!");
            }
        });
    });

    //删除评论
    $(".blog-content-container").on("click", ".blog-delete-comment", function () {

        $.ajax({
            url: '/comments/' + $(this).attr("commentId") + '?blogId=' + blogId,
            type: 'DELETE',
            success: function (data) {
                if (data.success) {
                    toastr.info(data.message);
                    // 获取评论列表
                    getCommnet(blogId);
                } else {
                    toastr.error(data.message);
                }
            },
            error: function () {
                toastr.error("error!");
            }
        });
    });

    // 提交点赞
    $(".blog-content-container").on("click", "#submitVote", function () {

        $.ajax({
            url: '/votes',
            type: 'POST',
            data: {"blogId": blogId},
            success: function (data) {
                if (data.success) {
                    toastr.info(data.message);
                    // 成功后，重定向
                    window.location = blogUrl;
                } else {
                    toastr.error(data.message);
                }
            },
            error: function () {
                toastr.error("error!");
            }
        });
    });

    // 取消点赞
    $(".blog-content-container").on("click", "#cancelVote", function () {

        $.ajax({
            url: '/votes/' + $(this).attr('voteId') + '?blogId=' + blogId,
            type: 'DELETE',
            success: function (data) {
                if (data.success) {
                    toastr.info(data.message);
                    // 成功后，重定向
                    window.location = blogUrl;
                } else {
                    toastr.error(data.message);
                }
            },
            error: function () {
                toastr.error("error!");
            }
        });
    });

    // 初始化 技术文章评论
    getCommnet(blogId);
});