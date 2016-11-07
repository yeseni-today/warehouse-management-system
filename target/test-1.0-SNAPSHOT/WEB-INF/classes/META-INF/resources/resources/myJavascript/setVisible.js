/**
 * Created by 22340 on 2016/11/5.
 */
function setVisible(bool) {
    var code = document.getElementById("codeLi");
    if (bool) {
        code.style.display = "block";
    } else {
        code.style.display = "none";
    }
}