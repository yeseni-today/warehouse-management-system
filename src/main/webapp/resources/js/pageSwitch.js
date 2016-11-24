/**
 * Created by 22340 on 2016/11/23.
 */

function switch_page() {
    switch_page1( $('#currentPage'),$('#nextPage'))
}

function switch_page1(current,next) {
    var width = document.documentElement.clientWidth;
    var height = window.innerHeight + 5;
    console.log(height);
    // var currentPage = $('#currentPage');
    // var nextPage = $('#nextPage');
    var currentPage = current;
    var nextPage = next;
    var body = $('#body');

    //init
    body.css("height", height + "px");
    body.css("width", width + "px");
    body.css("overflow", "hidden");

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

    setTimeout(function () {
        currentPage.style.display = "none";
        currentPage.remove('currentPage');
    }, 1000);
}

