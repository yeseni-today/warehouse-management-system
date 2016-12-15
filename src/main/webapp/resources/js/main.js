window.onload = function () {
    document.body.style.fontSize = screen.width * 0.01 + "px";
    document.body.style.display = "block";
};

$(document).ready(function () {
    $("#query").click(function () {
        query();
    });
});

$(document).ready(function () {
    $("#slide_panel_bt").click(function () {
        if ($(".slide-panel").css("display") == "none") {
            $(".slide-panel").slideDown("slow");
        } else {
            $(".slide-panel").slideUp("slow");
        }
    });
});

$(document).ready(msg_findmsg());

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


function addCategory() {
    var a = $('#form_addCategory').serialize();
    $.ajax({
        url: "/newcategory",
        type: "post",
        data: a,
        success: function (result) {
            closePop();
            alert("添加分类成功");

        },
        error: function () {
            alert("添加分类失败，请重试");
        }
    })
}

function addCompany() {
    var a = $('#form_addCompany').serialize();
    $.ajax({
        url: "/newcompany",
        type: "post",
        data: a,
        success: function (result) {
            closePop();
            alert("添加厂家信息成功");
        },
        error: function () {
            alert("添加厂家信息失败，请重试");
        }
    })
}

function query() {
    var values = $("#queryinput").serialize();
    $.ajax({
        type: "get",
        url: "/query/queryItem",    //向springboot请求数据的url
        data: values,
        success: function (items) {
            var table = $("#result_table").find("tbody");
            table.find("tr").remove();
            var _display = function (item) {
                var itemhtml = "<tr style='display: none' id='tr" + item.itemCode + "'>" +
                    "<td>" + item.itemCode + "</td>" +
                    "<td>" + item.itemName + "</td>" +
                    "<td>" + item.categoryEntity.categoryName + "</td>" +
                    "<td>" + item.itemCount + "</td>" +
                    "<td onclick=\"openItemInfoPop(\'" + item.itemCode + "\')\">" + "详情" + "</td>" +
                    "</tr>";
                table.append(itemhtml);
            };
            var _afterdisplay = function (item) {
                $("#tr" + item.itemCode).fadeIn(500);
            };
            beautifyDisplay(_display, _afterdisplay, items, "query");
        }
    })
}

function applyQueryAdd() {
    var values = $("#queryAddinput").serialize();
    $.ajax({
        type: "get",
        url: "/query/queryItem",    //向springboot请求数据的url
        data: values,
        success: function (items) {
            var table = $("#add_result_table").find("tbody");
            table.find("tr").remove();
            var _display = function (item) {
                var itemhtml = "<tr style='display: none' id='tr" + item.itemCode + "'>" +
                    "<td>" + item.itemCode + "</td>" +
                    "<td>" + item.itemName + "</td>" +
                    "<td>" + item.categoryEntity.categoryName + "</td>" +
                    "<td>" + item.itemCount + "</td>" +
                    "<td class='myTable-operation' onclick=\"openPopAdd(\'" + item.itemCode + "\',\'"+item.itemName+"\')\">" +
                    "<div class='icon-plus'></div>" + "</td>" +
                    "</tr>";
                table.append(itemhtml);
            }
            var _afterdisplay = function (item) {
                $("#tr" + item.itemCode).fadeIn(500);
            }
            beautifyDisplay(_display, _afterdisplay, items, "query");
        }
    })
}

function openItemInfoPop(itemCode) {
    $(".pop li").css({"min-height": "3em", "line-height": "3em"});
    $.ajax({
        url: "/query/itemInfo",
        type: "post",
        data: {"itemCode": itemCode},
        success: function (result) {
            var item= result.content;
            $("[name='itemCode']").val(item.itemCode);
            $("[name='itemName']").val(item.itemName);
            $("[name='itemSpec']").val(item.itemSpec);
            $("[name='itemCount']").val(item.itemCount);
            $("[name='itemPrice']").val(item.itemPrice);
            $("[name='itemIntroduce']").val(item.itemIntroduce);
            $("[name='itemBorrowTimelimit']").val(item.itemBorrowTimelimit);
            $("[name='itemState']").val(item.itemState);
            $("[name='itemExamine']").val(item.itemExamine);
            $("[name='itemRemind']").val(item.itemRemind);
            $("[name='itemCategory']").val(item.categoryEntity.categoryName);
            $("[name='companyName']").val(item.companyEntity.companyName);
            $("[name='storageLocation']").val(item.storageLocation);
        },
        error:function () {
            alert("失败");
        }
    });
    openPop();
}

var displayarraysId = [];
var displayarraysIsLoading = [];
var displayarraysInterValId = [];
var displayarraysCurrentIntervalID;
/**
 * 定时执行显示的数据
 * @param display 如何显示数据函数
 * @param afterdisplay 显示数据之后的函数
 * @param items 显示的数据数组
 * @param id 指定一个id
 * */
function beautifyDisplay(display, afterdisplay, items, id) {
    var flag = false;
    //先查看是否是之前是否还有这个ID,有则获取index，没有则新增
    var Idindex = 0;
    for (i = 0; i < displayarraysId.length; i++) {
        if (displayarraysId[i] == id) {
            flag = true;
            Idindex = i;
            break;
        }
    }

    //如果没有，放入到数组中
    if (!flag) {
        displayarraysId[displayarraysId.length] = id;
        Idindex = displayarraysId.length - 1;
    }

    var intervid = displayarraysInterValId[Idindex];
    //查看是否正在执行
    if (displayarraysIsLoading[Idindex] == true) {
        //如果正在执行，先关闭定时器
        notifyClearInterval(intervid)
    }
    //启动定时器显示数据
    var itemindex = items.length > 0 ? 0 : -1;
    console.log("数据长度：" + items.length);
    //设置正在执行
    displayarraysIsLoading[Idindex] = true;
    //将interval的ID放置到数组中
    var _close = function () {
        if (typeof(displayarraysCurrentIntervalID) != "undefined") {
            notifyClearInterval(displayarraysCurrentIntervalID);
            displayarraysIsLoading[Idindex] = false;
        }
    };
    displayarraysCurrentIntervalID = displayarraysInterValId[Idindex] = setInterval(function () {
        if (itemindex == -1) {
            _close();
            return;
        }
        var item = items[itemindex];
        //显示数据
        display(item);
        //回调数据
        afterdisplay(item);
        if (itemindex < items.length - 1) {
            itemindex++;
        } else {
            _close();
        }
    }, 100);
}


/**
 * 通知关闭定时器
 * @param a 定时器的ID
 * */
function notifyClearInterval(a) {
    clearInterval(a);
}

function msg_send() {
    showLoading();
    $.ajax({
        url: "/message/sendajax",
        type: "post",
        data: $('#newmessage').serialize(),
        success: function (result) {
            if (result.status == 200) {
                //发送成功
                alert("发送成功")
            } else {
                alert("发送失败")
            }
        },
        error: alert("发送失败")
    });
    hideLoading();
}

var msgs = new Array();

function msg_findmsg() {
    var url = "/message/findmessagebyid";
    var type = "get";
    msgFindBy(url, type);
}

function msg_findWarnMsg() {
    var url = "/message/warnmsg";
    var type = "get";
    msgFindBy(url, type);
}

function msgFindBy(url, type) {
    showLoading();
    $.ajax({
        url: url,
        type: type,
        success: function (result) {
            //result.content 是一个umessage的list列表
            displayMsg(result.content)
        }
    });
    hideLoading();
}

function displayMsg(msgs) {
    $(".content-right .message").remove();
    var contentRight = $("#message");
    $.each(msgs, function (i, message) {
        if (message.messageState != 2) {
            msgs.push(message);
            messageBox = "<div class='message' id='" + message.messageId + "'>" +
                "<span class='message-title' ><strong>" + message.messageTitle + "</strong></span>" +
                "<span class='message-date'>" + getDate(message.messageDate) + "</span>" +
                "<div class='message-content' >" + message.messageContent + "</div>" +
                "<div class='message-operation'>" +
                "<a href='#' onclick=\"msg_read(\'" + message.messageId + "\')\">设为已读</a>&nbsp;&nbsp;" +
                "<a href='#' onclick=\"msg_hide(\'" + message.messageId + "\')\">不再显示</a>" +
                "</div>" +
                "</div>";
            contentRight.append(messageBox);
        }
    })
}
function getDate(ms) {
    var d = new Date(ms);
    return d.toLocaleString();
}

function msg_delete() {
    var messageID = $("[name='messageID']").val();
    $.ajax({
        url: "/message/delete?messageID=" + messageID,
        type: "get",
        success: function (result) {
            if (result.message = "success") {
                //todo
                msg_findmsg();
                alert("success");
            }
        }
    });
    closePop();
}
function findLogs() {//todo
    showLoading();
    $.ajax({
        url: "/log/findLogs",
        type: "get",
        success: function (result) {
            //result.content 是一个umessage的list列表
            var messages = result.content;
            $(".content-right .message").remove();
            var contentRight = $("#message");
            $.each(messages, function (i, message) {
                msgs.push(message);
                messageBox = "<div class='message'>" +
                    "<span class='message-title' >" + message.messageId + "</span>" +
                    "<span class='message-date'>" + message.messageDate + "</span>" +
                    "<div class='message-content' >" + message.messageContent + "</div>" +
                    "<div class='message-operation' onclick=\"openPopMessage('" + i + "')\">详情</div>" +
                    "</div>";
                contentRight.append(messageBox);
            })
        }
    });
    hideLoading();
}

function clearApplyForm() {
    $.ajax({
        url: "/apply/add/clearformajax",
        type: "post",
        data: {},
        success: function () {
            alert("清空成功");
        },
        error: function () {
            alert("清空失败，请重试");
        }
    })
}
//弹出框
function openPop() {
    $(".pop-bg").css('display', 'block');
    setTimeout(function () {
        $(".pop-bg").css('background', 'rgba(181, 181, 181, 0.5)');
        $(".pop").css('transform', 'scale(1,1)');
    }, 1);
}

function closePop() {
    $(".pop-bg").css('background', 'rgba(181, 181, 181, 0)');
    $(".pop").css('transform', 'scale(0,0)');
    setTimeout(function () {
        $(".pop-bg").css('display', 'none');
    }, 500);
}

function closeapplyFormTablePop() {
    closePop();
    $("[id ^='tr']").css("display","none")
}
function openAddCategory() {
    $(".pop-addCategory").css('display', 'block');
    setTimeout(function () {
        $(".pop-addCategory").css('background', 'rgba(181, 181, 181, 0.5)');
        $(".pop-addCategory .pop").css('transform', 'scale(1,1)');
    }, 1);
}

function openAddCompany() {
    $(".pop-addCompany").css('display', 'block');
    setTimeout(function () {
        $(".pop-addCompany").css('background', 'rgba(181, 181, 181, 0.5)');
        $(".pop-addCompany .pop").css('transform', 'scale(1,1)');
    }, 1);
}
function openPopAdd(code, name) {
    document.getElementsByName("itemCode")[1].value = code;
    document.getElementsByName("itemName")[1].value = name;
    openPop();
}
function openPopDetails(itemForm) {
    var item = $.parseJSON(itemForm);

    $("[name='itemName']").val(item.itemName);
    $("[name='itemCategory']").val(item.itemCategoryID);
    $("[name='itemCount']").val(item.itemCount);
    $("[name='itemPrice']").val(item.itemPrice);
    $("[name='itemSpec']").val(item.itemSpec);
    $("[name='itemCompany']").val(item.itemCompanyID);
    $("[name='billCode']").val(item.billCode);
    $("[name='storageLocation']").val(item.storageLocation);
    console.log(item.storageLocation);

    openPop();
}

function openStorageFormInfoPop(storageFormId) {
    openPop();
    var table=$("#applyFormTable").find("tbody");

    $.ajax({
        url:"/storage/StorageFormInfo.json",
        type:"post",
        data:{"storage_id":storageFormId},
        success:function (result) {
            var storageForm=result.content;
            table.find("tr").remove();
            var html="";
            for(var item in storageForm ){
                html += "<tr><td>"+item.itemCode+"</td>" +
                    "<td>"+item.itemCount+"</td>" +
                    "<td>"+item+"</td>" +
                    "<td>"+item+"</td>" +
                    "</tr>";
            }
        },
        error:function () {
            alert("ajax请求发送失败");
        }
    })
}
/*function openPopMessage(message) {
 // alert(msgs[0]);
 // var d = "";
 // for (var name in msgs[0]) {
 //     d += name + ":" + msgs[0][name] + ";"
 // }
 // console.log(d);
 var msg = msgs[message];

 $("[name='messageID']").val(msg.messageId);
 $("[name='messageType']").val(msg.messageType);
 $("[name='messageContent']").val(msg.messageContent);
 $("[name='messageData']").val(msg.messageDate);
 $("[name='messageSendID']").val(msg.messageSendId);
 $("[name='messageReceiveID']").val(msg.messageReceiveId);
 $("[name='messageState']").val(msg.messageState);
 $("[name='messageTitle']").val(msg.messageTitle);

 openPop();
 }*/


//加载界面
function showLoading() {
    $("#load").css("display", "block");
}

function hideLoading() {
    $("#load").css("display", "none");
}

function deleteStroageItem(itemCode) {
    showLoading();
    $.ajax({
        url: "/storage/add/deleteItem",
        data: {"itemCode": itemCode},
        type: "get",
        success: function () {
            setTimeout(function () {
                hideLoading();
                $("tr#" + itemCode).remove();
            }, 1000);
        }
    });
}

function deleteAll() {
    showLoading();
    $.ajax({
        url: "/storage/add/deleteAll",
        success: function (result) {
            setTimeout(function () {
                hideLoading();
            }, 500);
            $('#storagetable tr:gt(0)').remove();
        }
    })
}


//apply

function addItem() {
    var da = $('#applyForm').serialize();
    $.ajax({
        url: "/apply/add/additem",
        type: "post",
        data: da,
        success: function (result) {
            if(result.message=="success"){
                closePop();
                alert("添加成功");
            }else {
                alert("添加失败");
            }
        },
        error: function () {
            alert("添加失败，请重试");
        }
    });
}
function applySubmitForm() {
    $.ajax({
        url: "/apply/add/submit",
        type: "post",
        success: function (result) {
            if (result.message == "success") {
                alert("提交成功");
            } else {
                alert("提交失败");
            }
        },
        error: function () {
            alert("提交失败");
        }
    })
}

function applyDelet(itemCode) {//todo
    alert(itemCode);
    $.ajax({
        url: "apply/add/deleteajax",
        type: "post",
        data: {'itemCode': itemCode},
        success: function (result) {
            if (result.message == "success") {
                $("." + itemCode).remove();
                alert("删除成功");

            } else {
                alert("删除失败");
            }
        },
        error: function () {
            alert("删除失败!");
        }
    })
}

//message
function msg_send() {
    var newmessage = $('#newmessage').serialize();
    $.ajax({
        url: "/message/sendajax",
        type: "post",
        data: newmessage,
        success: function (result) {
            if (result.message == "success") {
                alert("发送成功");
            }
        },
        error: function () {
            alert("发送失败，请检查网络");
        }
    })
}
/**
 * todo 不再显示一条信息
 * 这里直接调用删除信息ajax
 * */
function msg_hide(messageID) {
    $.ajax({
        url: "/message/delete",
        data: {"messageID": messageID},
        success: function (result) {
            if (result.message == "success") {
                //设为已读调用
                alert("不再显示执行成功")
            } else {
                alert("不再显示执行失败，错误信息为：" + result.message)
            }
        },
        error: function () {
            alert("你的网络有问题，请稍后重试")
        },
    })
}
/**
 * 已读一条消息
 * */
function msg_read(messageID) {
// alert(messageID)
    $.ajax({
        url: "/message/read",
        data: {"messageID": messageID},
        success: function (result) {
            if (result.message == "success") {
                //设为已读调用
                alert("设为已读成功")
            } else {
                alert("设为已读失败，错误信息为：" + result.message)
            }
        },
        error: function () {
            alert("你的网络有问题，请稍后重试")
        },
    })
}
//查询对应条件的入库单
function queryStorageList() {
    da = $('#storageQueryForm').serialize();
    $.ajax({
        url: "/storage/listajax",
        data: da,
        type: "get",
        success: function (result) {
            $("#result_storage_table").find("tr").remove("tr:gt(0)");
            var _display = function (item) {
                var itemhtml = "<tr style='display: none' id='tr" + item.storageId + "'>" +
                    "<td>" + item.storageId + "</td>" +
                    "<td>" + getDate(item.storageTime) + "</td>" +
                    "<td>" + item.opreationId + "</td>" +
                    "<td onclick=\"openStorageFormInfoPop(\'"+item.storageId+"\')\">" + "操作" + "</td>" +
                    "</tr>";
                $("#result_storage_table").append(itemhtml)
            };
            var _afterdisplay = function (item) {
                $("#tr" + item.storageId).fadeIn(500);
            };
            beautifyDisplay(_display, _afterdisplay, result.content, "storage");
        },
    })
}

//查询对应条件的申请单
function queryApplyList() {
    da = $('#applyQueryForm').serialize();
    $.ajax({
        url: "/apply/listajax",
        data: da,
        type: "get",
        success: function (result) {
            $("#result_apply_table").find("tr").remove("tr:gt(0)");
            var _display = function (item) {
                item1 = "<tr style='display: none' id='tr" + item.applicationId + "'>" +
                    "<td>" + item.applicationId + "</td>" +
                    "<td>" + item.applicationTime + "</td>" +
                    "<td>" + item.examineId + "</td>" +
                    "<td>" + item.states + "</td>" +
                    "<td>" + item.statesTime + "</td>" +
                    "<td>" + "操作" + "</td>" +
                    "</tr>";
                $("#result_apply_table").append(item1)
            }
            var _afterdisplay = function (item) {
                $("#tr" + item.applicationId).fadeIn(500);
            }
            beautifyDisplay(_display, _afterdisplay, result.content, "apply")
        },
    })
}

function getObjextInfo(object) {
    var s = "";
    for(var i in object){
        s+="["+i+":"+object[i]+"];"
    }
    return s;
}

//审核
function getApplyFormByID(application_id) {
    var html = "";
    var table = $("#applyFormTable").find("tbody");
    var buttons = $("#applyFormTable button");
    //init
    $('#applyFromID').text("  ");
    $('#usersId').text("  ");
    $('#applyTime').text("  ");
    table.find("tr").remove();

    $.ajax({
        url: "/apply/applyinfo.json?application_id="+application_id,
        type: "get",
        // date: {"application_id": application_id},
        success: function (result) {
            if (result.message == "success") {
                openPop();
                $('#applyFromID').text(result.content.applicationId);
                $('#usersId').text(result.content.usersId);
                $('#applyTime').text(result.content.applicationTime);

                buttons[0].onclick=function () {
                    examineApply(true,application_id);  //通过
                };
                // buttons[0].click=function () {
                //     examineApply(true,application_id);  //通过
                // };
                buttons[1].onclick=function (){
                    examineApply(false,application_id);  //不通过
                };

                var _display = function (item) {
                    var html = "<tr style='display:none;' id='tr1"+item.itemCode+"'>" +
                        "<td>" + item.itemCode + "</td>" +
                        "<td>" + item.counts + "</td>" +
                        "<td>" + item.applicationType + "</td>" +
                        "<td>" + item.applicationText + "</td>" +
                        "</td>";
                    table.append(html);
                };

                var _afterdisplay = function (item) {
                    console.log('#tr1'+item.itemCode);
                    $('#tr1'+item.itemCode).fadeIn(1000);
                };

                beautifyDisplay(_display,_afterdisplay,result.content.items,"申请单列表");
            } else {
                alert("获取失败，失败信息："+result.message)
            }
        }

    })

}

function examineApply(states,apply_id) {
    $.ajax({
        url:"/manage/passexamine",
        type: "post",
        data: {"states":states, "apply_id":apply_id},
        success:function (result) {
            if(result.message=="success"){
                alert("成功");
                closePop();
            }else {
            //    todo
                alert("系统错误");
            }
        },
        error:function () {
            alert("发送失败");
        }
    })
}
