/*!
 * edit.html 页面脚本.
 */
"use strict";
//# sourceURL=blogedit.js

// DOM 加载完再执行
$(function() {
	
	// 初始化 md 编辑器
    $("#md").markdown({
        language: 'zh',
        fullscreen: {
            enable: true
        },
        resize:'vertical',
        localStorage:'md',
        imgurl: 'http://localhost:8081',
        base64url: 'http://localhost:8081'
    });

    // 初始化下拉
    $('.form-control-chosen').chosen();

	// 初始化标签控件
    $('.form-control-tag').tagsInput({
        'defaultText':'输入标签'
    });

    function countCatalogId() {
        var catalogs = $('#catalogSelect').val();
        if(catalogs == null) {
            alert("请到个人主页添加分类!");
        }
    }
    countCatalogId();

    //图片插入
 	$("#uploadImage").click(function() {
		$.ajax({
		    url: 'http://localhost:8081/upload',
		    type: 'POST',
		    cache: false,
		    data: new FormData($('#uploadForMid')[0]),
		    processData: false,
		    contentType: false,
		    success: function(data){
		    	var mdcontent=$("#md").val();
		    	 $("#md").val(mdcontent + "\n![]("+data +") \n");
	         }
		}).done(function(res) {
			$('#file').val('');
		}).fail(function(res) {});
 	})
 
 	// 发布文章
 	$("#submitBlog").click(function() {
        var editUrl = '/u/'+ $(this).attr("userName") + '/skills/edit';
		$.ajax({
		    url: editUrl,
		    type: 'POST',
			contentType: "application/json; charset=utf-8",
		    data:JSON.stringify({
				"id":$('#blogId').val(),
		    	"title": $('#title').val(),
		    	"summary": $('#summary').val(),
		    	"content": $('#md').val(),
                "catalog_id":$('#catalogSelect').val(),
                "tags":$('.form-control-tag').val()
            }),
			 success: function(data){
				 if (data.success) {
					// 成功后，重定向
					 window.location = data.body;
				 } else {
					 toastr.error(data.message);
				 }
		     },
		     error : function() {
		    	 toastr.error("error!");
		     }
		})
 	})
	

});