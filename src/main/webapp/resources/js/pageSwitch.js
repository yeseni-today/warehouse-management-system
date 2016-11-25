/**
 * Created by 22340 on 2016/11/23.
 */

function jump2NextPageAndChangeId() {
    switch_page1($('#currentPage'), $('#nextPage'));
    changeId($('#currentPage'), $('#nextPage'));
}
function changeId(current, next) {
    // $('#nextPage').load("/storage/add/additemajax");
    // jump2NextPageAndChangeId();
    var pre = current;
    var current = next;
    current.attr("id", "currentPage");
    pre.attr("id", "nextPage");
}

function switch_page1(current, next) {
    var width = document.documentElement.clientWidth;
    var height = window.innerHeight + 5;
    // console.log(height);
    var currentPage = current;
    var nextPage = next;
    var body = $('#body');

    //init
    body.css("height", height + "px");
    body.css("width", width + "px");
    body.css("overflow", "hidden");
    currentPage.css("display","block");
    nextPage.css("display","block");

    //start
    currentPage.css("height", height + "px");
    currentPage.css("width", width + "px");
    nextPage.css("height", height + "px");
    nextPage.css("width", width + "px");

    //设置css过场动画
    currentPage.addClass('currentPage');
    nextPage.addClass('nextPage');
    //end,
    setTimeout(function () {
        currentPage.addClass('currentPageAfter');
        nextPage.addClass('nextPageAfter');
    }, 10);

    //还原
    setTimeout(function () {

        body.css("height", "auto");
        body.css("width","auto");
        body.css("overflow", "auto");

        currentPage.css("height", "auto");
        currentPage.css("width", "auto");
        nextPage.css("height", "auto");
        nextPage.css("width", "auto");

        //移除样式
        currentPage.removeClass('currentPage');
        nextPage.removeClass('nextPage');
        currentPage.removeClass('currentPageAfter');
        nextPage.removeClass('nextPageAfter');

        //置空
        currentPage.css("display","none");

    }, 5000);
}

