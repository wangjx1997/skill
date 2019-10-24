/**
 * Created by 汪锦玺 on 2019/2/11.
 */
$(function () {
    $("#checkAll").click(function () {
        if($(this).is(':checked')){
            $('input[name="permissions"]').each(function(){
                $(this).prop("checked",true);
            });
        }else{
            $('input[name="permissions"]').each(function(){
                $(this).prop("checked",false);
            });
        }
    });
});