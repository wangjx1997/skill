"use strict";
//# sourceURL=main.js

// DOM 加载完再执行
$(function () {

    // 返回顶部的效果事件
    NProgress.start();

    $(window).scroll(function () {  //只要窗口滚动,就触发下面代码
        var scrollt = document.documentElement.scrollTop + document.body.scrollTop; //获取滚动后的高度
        if (scrollt > 200) {  //判断滚动后高度超过200px,就显示
            $("#goToTop").fadeIn(400); //淡出
        } else {
            $("#goToTop").stop().fadeOut(400); //如果返回或者没有超过,就淡入.必须加上stop()停止之前动画,否则会出现闪动
        }
    });
    $("#goToTop").click(function () { //当点击标签的时候,使用animate在200毫秒的时间内,滚到顶部
        $("html,body").animate({scrollTop: "0px"}, 200);
    });
    NProgress.done();

    /*给登录后头部信息赋值*/
    function initHeader() {
        var uSelf = $("#uSelf").html();
        if (uSelf != null || uSelf != undefined) {
            $.ajax({
                url: '/users/' + uSelf,
                type: 'GET',
                success: function (data) {
                    if (data.success) {
                        var uAvatar = data.body;
                        $("#uImg").attr("src", uAvatar == "null" ? "/images/avatar-defualt.jpg" : uAvatar);
                    }
                }
            });
        }else {
            $("#uImg").attr("src", "/images/avatar-defualt.jpg");
        }
        $("#u").attr("href", "/u/" + uSelf);
        $("#uProfile").attr("href", "/u/" + uSelf + "/profile");
        $("#faWenZhang").attr("href", "/u/" + uSelf + "/skills/edit");
    }

    initHeader();

    /**
     * 鼠标划过就展开子菜单，免得需要点击才能展开
     */
    function dropdownOpen() {

        $("#dropdownMenu1").hover(
            function () {
                $("#dropDownCur").show();
            });//为了鼠标可以进入下拉框
        $("#dropDownCur").hover(function () {
            $(this).show();//鼠标进入下拉框
        }, function () {
            $(this).hide();//鼠标离开下拉框后，就会消失
        });

    }

    dropdownOpen();

});