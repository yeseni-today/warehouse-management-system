/**
 * Created by 22340 on 2016/11/5.
 */
window.onload = function () {
    document.body.style.fontSize = screen.width * 0.01 + "px";

    var ul = document.getElementById("myUl");
    if (ul != null) {
        ul.style.width = screen.width * 0.65 + "px";

        var lis = ul.getElementsByTagName("li");
        for (var i = 0; i < lis.length; i++) {
            lis[i].style.fontSize = screen.width * 0.01 + "px";
        }
    }
};