window.onload = function () {
    document.body.style.fontSize = screen.width * 0.01 + "px";
};

function setVisible(bool) {
    var code = document.getElementById("bar_code");
    if (bool) {
        code.style.display = "block";
    } else {
        code.style.display = "none";
    }
}

function alert_info(info) {
    alert(info);
}

function addItem() {
    closePopAdd();
    var da = $("applyform").serialize();
    $.ajax({
        url: "/apply/add/additem",
        type: "post",
        data: da,
        success: function (result) {
        }
    })
}

function openPopAdd(code, name) {
    var popAddBg = document.getElementsByClassName('pop_add-bg');
    var popAdd = document.getElementsByClassName('pop_add');
    var a = document.getElementsByName("itemCode");
    var b = document.getElementsByName("itemName");
    a[1].value = code;
    b[1].value = name;
    popAddBg[0].style.display = "block";
    setTimeout(function () {
        popAddBg[0].style.background = "rgba(181, 181, 181, 0.5)";
        popAdd[0].style.transform = "scale(1,1)"
    }, 1);

}

function closePopAdd() {
    var popAddBg = document.getElementsByClassName('pop_add-bg');
    var popAdd = document.getElementsByClassName('pop_add');
    popAddBg[0].style.background = "rgba(181, 181, 181, 0)";
    popAdd[0].style.transform = "scale(0,0)";
    setTimeout(function () {
        popAddBg[0].style.display = "none";
    }, 500);
}

function showLoading() {
    // $("#topdiv").style.display = "block";
    $("#topdiv").css("display", "block");
}
function hideLoading() {
    $("#topdiv").style.display = "none";

}

function oneTest() {
    var $current = $("#currentPage");
    var $next = $("#nextPage");
    var outClass = 'pt-page-moveToLeft';
    var inClass = 'pt-page-moveFromRight';
    $next.load("resources/js/storage_form.html");
    var animEndEventNames = {
        'WebkitAnimation': 'webkitAnimationEnd',
        'OAnimation': 'oAnimationEnd',
        'msAnimation': 'MSAnimationEnd',
        'animation': 'animationend'
    };

    // animation end event name
    var animEndEventName = animEndEventNames[Modernizr.prefixed('animation')];
    $current.addClass(outClass).on(animEndEventName, function () {
        alert("done");
        $current.off(animEndEventName);
    });
    $next.addClass(inClass).on(animEndEventName, function () {
        $next.off(animEndEventName);
    });
}







