/**
 * Created by 22340 on 2016/11/23.
 */

function change1() {
    var url = "/test";
    var animation = 'move-to-left';
    jumpToWithAnim(url, animation);
}

function jump2StorageAddItem() {
    var url = "/storage/add/additemajax";
    var animation = 'move-to-left';
    jumpToWithAnim(url, animation);
}

function jumpTo(url) {
    jumpToWithAnim(url, 'move-to-left');
}


//
function jumpToWithAnim(url, animation) {
    var currentPageId = "currentPage";
    var nextPageId = "nextPage";

    $('#' + nextPageId).load(url);
    jumpPageFromTo(currentPageId, nextPageId, animation);
}

function jumpPageFromTo(currentPageId, nextPageId, animation) {

    var animationTime = 500;//ms
    var classes = getClassName(animation);

    var contentStart = classes.contentStart;
    var nextStart = classes.nextStart;
    var currentEnd = classes.currentEnd;
    var nextEnd = classes.nextEnd;
    //init
    $('#' + currentPageId).addClass(contentStart).css({display: 'block', transition: animationTime / 1000 + "s"});
    $('#' + nextPageId).addClass(nextStart).css({display: 'block', transition: animationTime / 1000 + "s"});
    //开始
    setTimeout(function () {
        $('#' + currentPageId).addClass(currentEnd);
        $('#' + nextPageId).addClass(nextEnd);
    }, 1);
    //还原
    setTimeout(function () {
        $('#' + currentPageId).attr({id: 'temp'}).removeClass(contentStart + ' ' + currentEnd).css("display", "none");
        $('#' + nextPageId).attr({id: 'currentPage'}).removeClass(nextStart + ' ' + nextEnd);
        $("#temp").attr({id: 'nextPage'});
    }, animationTime);
}

function getClassName(animation) {
    var contentStart = animation + '-contentStart';
    var nextStart = animation + '-nextStart';
    var currentEnd = animation + '-currentEnd';
    var nextEnd = animation + '-nextEnd';
    return {
        contentStart: contentStart,
        nextStart: nextStart,
        currentEnd: currentEnd,
        nextEnd: nextEnd
    };
}


