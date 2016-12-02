/**
 * Created by 22340 on 2016/11/27.
 */
$(document).ready(function () {
    $("#loginButton").click(function () {
        sign();
    });
});

function sign() {
    var username = $("[name='username']").val();
    var password = $("[name='password']").val();
    if (checkForm(username, password) == true) {
        $.ajax({
            type: "post",
            url: "/signin",             //向springboot请求数据的url
            data: {"username": username, "password": password},
            success: function (json) {
                $("#loginButton").val("正在登陆");
                showLoading();
                setTimeout(function () {
                    if (json.message == ("success")) {
                        console.log(json.content)
                        location = json.content;        //跳转
                    } else {
                        $("#loginButton").val("登陆失败请重试");
                    }
                    hideLoading();
                }, 1000);
            }
        });
    }
}

function checkForm(username, password) {
    var ifInput = true;
    if (username == "") {
        ifInput = false;
        $("#loginButton").val("请输入账户名");
    } else if (password == "") {
        ifInput = false;
        $("#loginButton").val("请输入密码");
    }
    return ifInput;
}