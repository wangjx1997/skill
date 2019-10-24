/**
 * Created by 汪锦玺 on 2019/2/15.
 */
$(function () {
    $("#submitRegister").click(function() {
        $.ajax({
            url: "/register",
            type: 'POST',
            data:$('#registerForm').serialize(),
            success: function(data){
                $('#registerForm')[0].reset();
                if (data.success) {
                    window.location.replace(data.body);
                } else {
                    toastr.error(data.message);
                }
            },
            error : function() {
                toastr.error("error!");
            }
        });
    });
});