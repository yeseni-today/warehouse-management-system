//字体大小初始化
window.onload = function () {
    document.body.style.fontSize = screen.width * 0.01 + "px";
    //防止刷新时 调整大小出现闪动
    document.body.style.display = "block";
};

//返回顶部
$(document).ready(function () {
    var $backTop = $("#back_top");
    $backTop.hide();

    $backTop.click(function () {
        $("#currentPage").animate({scrollTop: 0}, 300);
    });

    $("#currentPage,#nextPage").scroll(function () {
        setTimeout(function () {
        }, 100);
        if ($("#currentPage").scrollTop() > 100 || $("#nextPage").scrollTop > 100) {
            $("#back_top").fadeIn(500);
        } else {
            $("#back_top").fadeOut(500);
        }
    });
});

//绑定事件 展开查询功能条
$(document).ready(function () {
    $("#slide_panel_bt").click(function () {
        var $slide = $(".slide-panel");
        if ($slide.css("display") == "none") {
            $slide.slideDown("slow");
        } else {
            $slide.slideUp("slow");
        }
    });
});

//加载消息
$(document).ready(msg_findTypeOf('inform'));

//入库 选择分类 input隐藏
function setVisible(bool) {
    var code = document.getElementById("bar_code");
    if (bool) {
        code.style.display = "block";
    } else {
        code.style.display = "none";
    }
}

//入库 添加分类
function addCategory() {
    var a = $('#form_addCategory').serialize();
    $.ajax({
        url: "/newcategory",
        type: "post",
        data: a,
        success: function (result) {
            if (result.message == "success") {
                closePop();
                alert("添加分类成功");
            } else {
                alert("添加分类失败");
            }
        },
        error: function () {
            alert("ajax请求发送失败");
        }
    })
}
//入库 添加公司
function addCompany() {
    var a = $('#form_addCompany').serialize();
    $.ajax({
        url: "/newcompany",
        type: "post",
        data: a,
        success: function (result) {
            if (result.message == "success") {
                closePop();
                alert("添加厂家信息成功");
            } else {
                alert("添加厂家信息失败");
            }
        },
        error: function () {
            alert("ajax请求发送失败");
        }
    })
}

//搜索物品   operation="icon-plus"  加号  "icon-search" 放大镜
//          用到的地方   查询   申请单添加加
function searchItem(operation) {
    var values = $("#query_input_info").serialize();
    var $table = $("#query_item_result").find("tbody");
    $.ajax({
        url: "/query/searchItem",
        type: "get",
        data: values,
        success: function (items) {
            $table.find("tr").remove();
            //加载特效
            var _display = function (item) {
                var itemhtml = "<tr style='display: none' id='tr" + item.itemCode + "'>" +
                    "<td>" + item.itemCode + "</td>" +
                    "<td>" + item.itemName + "</td>" +
                    "<td>" + item.categoryEntity.categoryName + "</td>" +
                    "<td>" + item.itemCount + "</td>" +
                    "<td class='myTable-operation " + operation + "' " +
                    "onclick=\"openPop_select(\'" + item.itemCode + "\',\'" + item.itemName + "\',\'" + operation + "\')\"></td>" +
                    "</tr>";
                $table.append(itemhtml);
            };
            var _afterdisplay = function (item) {
                $("#tr" + item.itemCode).fadeIn(500);
            };
            beautifyDisplay(_display, _afterdisplay, items, "query");
        },
        error: function () {
            alert("ajax请求发送失败");
        }
    })
}
//选择弹出框
function openPop_select(itemCode, itemName, operation) {
    if (operation == "icon-plus") {
        openPop_add(itemCode, itemName);//申请 添加物品弹出框
    } else {
        openPop_itemInfo(itemCode);//查询 详情弹出框
    }
}
//申请 添加物品弹出框
function openPop_add(code, name) {
    document.getElementById("info_of_apply_item").reset();
    document.getElementsByName("itemCode")[1].value = code;
    document.getElementsByName("itemName")[1].value = name;
    openPop();
}
//查询 详情弹出框
function openPop_itemInfo(itemCode) {
    $(".pop li").css({"min-height": "3em", "line-height": "3em"});  //todo 弹出窗口样式
    $.ajax({
        url: "/query/itemInfo",
        type: "post",
        data: {"itemCode": itemCode},
        success: function (result) {
            if (result.message == "success") {
                openPop();
                var item = result.content.itemEntity;
                document.getElementsByName("itemCode")[1].value = item.itemCode;
                document.getElementsByName("itemName")[1].value = item.itemName;
                //命名冲突
                // $("[name='itemCode']").val(item.itemCode);
                // $("[name='itemName']").val(item.itemName);
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
                $("[name='itemSlot']").val(result.content.slot);
            } else {
                alert("查询详情出错");
            }
        },
        error: function () {
            alert("ajax请求发送失败");
        }
    });
}


//todo 加载特效
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

//消息 查找   todo 消息url
function msg_findTypeOf(type) {
    var url = "";
    var typeStr = "";
    switch (type) {
        case "all":
            url = "";
            typeStr = "所有消息";
            break;
        case "inform":
            url = "/message/findmessagebyid";
            typeStr = "通知";
            break;
        case "other":
            url = "";
            typeStr = "其他";
            break;
        case "remind":
            url="";
            // url = "/message/warnmsg";
            typeStr = "提醒";
            break;
    }
    // document.getElementsByClassName("message-type")[0].innerHTML = typeStr;
    $(".message-type").text(typeStr);
    msg_findBy(url, "get");
}
//消息 获得
function msg_findBy(url, type) {
    $.ajax({
        url: url,
        type: type,
        success: function (result) {
            // if (result.message == "success") {
            var umessageList = result.content;
            msg_display(umessageList);
            // } else {
            //     alert("查询信息出错");
            // }
        },
        error: function () {
            alert("ajax请求发送失败");
        }
    });
}
//消息 显示
function msg_display(msgs) {
    var contentRight = $("#message");
    contentRight.find(".message").remove();

    if (  typeof(msgs) == 'undefined'||msgs.length==0) {
        var html = "<div class='message no-message'>没有消息</div>";
        contentRight.append(html);
    }
    //todo foreach
    $.each(msgs, function (i, message) {
        if (message.messageState != 2) {
            var messageBox = "<div class='message effect4' id='" + message.messageId + "'>" +
                "<span class='message-title' ><strong>" + message.messageTitle + "</strong></span>" +
                "<span class='message-date'>" + getDateAndTime(message.messageDate) + "</span>" +
                "<div class='message-content' >" + message.messageContent + "</div>" +
                "<div class='message-operation'>" +
                "<a href='#' onclick=\"msg_hide(\'" + message.messageId + "\')\">不再显示</a>" +
                "</div>" +
                "</div>";
            contentRight.append(messageBox);
        }
    })
}


//todo delete
/*
 function msg_delete() {
 var messageID = $("[name='messageID']").val();
 $.ajax({
 url: "/message/delete?messageID=" + messageID,
 type: "get",
 success: function (result) {
 if (result.message = "success") {
 }
 }
 });
 closePop();
 }
 function findLogs() {//todo
 $.ajax({
 url: "/log/findLogs",
 type: "get",
 success: function (result) {
 var messages = result.content;
 $(".content-right .message").remove();
 var contentRight = $("#message");
 $.each(messages, function (i, message) {
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
 }
 */

//申请 清空
function apply_clear() {
    $.ajax({
        url: "/apply/add/clearformajax",
        type: "post",
        success: function () {//todo result
            $("#apply_for_submit").find("tbody").find("tr").remove();
            alert("清空成功");
        },
        error: function () {
            alert("ajax请求发送失败");
        }
    })
}


//弹出框  todo
function openPop() {
    var $pop = $(".pop-bg");
    $pop.css('display', 'block');
    setTimeout(function () {
        $pop.css('background', 'rgba(181, 181, 181, 0.5)');
        $pop.find(".pop").css('transform', 'scale(1,1)');
    }, 1);
}
function closePop() {
    var $pop = $(".pop-bg");
    $pop.css('background', 'rgba(181, 181, 181, 0)');
    $pop.find(".pop").css('transform', 'scale(0,0)');
    setTimeout(function () {
        $pop.css('display', 'none');
    }, 500);
}

//弹出框 id
function openPopById(id) {
    var $pop = $("#" + id);
    $pop.css('display', 'block');
    setTimeout(function () {
        $pop.css('background', 'rgba(181, 181, 181, 0.5)');
        $pop.find(".pop").css('transform', 'scale(1,1)');
    }, 1);
}
function closePopById(id) {
    var $pop = $("#" + id);
    $pop.css('background', 'rgba(181, 181, 181, 0)');
    $pop.find(".pop").css('transform', 'scale(0,0)');
    setTimeout(function () {
        $pop.css('display', 'none');
    }, 500);
}


// todo delete
/*function openAddCategory() {
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
 function closeapplyFormTablePop() {
 closePop();
 $("[id ^='tr']").css("display", "none");// todo ??
 }*/

//入库 物品详情
function openPop_storageItemDetails(itemForm) {
    var item = JSON.parse(itemForm);

    $("[name='itemName']").val(item.itemName);
    $("[name='itemCategory']").val(item.itemCategoryID);
    $("[name='itemCount']").val(item.itemCount);
    $("[name='itemPrice']").val(item.itemPrice);
    $("[name='itemSpec']").val(item.itemSpec);
    $("[name='itemCompany']").val(item.itemCompanyID);
    $("[name='billCode']").val(item.billCode);
    $("[name='itemSlot']").val(item.itemSlot);

    openPop();
}

// todo zhenli
function openPop_storageInfo(storageFormId) {
    var $table = $("#storage_table").find("tbody");
//init
    $('#storage_id').text("  ");
    $('#operation_id').text("  ");
    $('#storage_time').text("  ");
    $table.find("tr").remove();
    openPop();

    $.ajax({
        url: "/storage/StorageFormInfo.json",
        type: "post",
        data: {"storage_id": storageFormId},
        success: function (result) {
            if (result.message == "success") {
                var storageForm = result.content;

                $('#storage_id').text(storageForm.storageId);
                $('#operation_id').text(storageForm.operationId);
                $('#storage_time').text(storageForm.storageTime);

                var items = storageForm.items;

                var html = "";
                // for (var item in items) {
                for (var i = 0; i < items.length; i++) {
                    html += "<tr>" +
                        "<td>" + items[i].itemCode + "</td>" +
                        "<td>" + items[i].counts + "</td>" +
                        "<td>" + items[i].price + "</td>" +
                        "<td>" + items[i].billCode + "</td>" +
                        "<td>" + items[i].itemSlot + "</td>" +
                        "<td>" + items[i].itemBatch + "</td>" +
                        "<td>" + items[i].itemIndate + "</td>" +
                        "<td>" + items[i].allowCount + "</td>" +
                        "</tr>";
                }
                $table.append(html);
            } else {
                alert("失败");
            }
        },
        error: function () {
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


//加载图标
function showLoading() {
    $("#load").css("display", "block");
}

function hideLoading() {
    $("#load").css("display", "none");
}

function deleteStroageItem(itemCode) {
    $.ajax({
        url: "/storage/add/deleteItem",
        type: "get",
        data: {"itemCode": itemCode},
        success: function (result) {
            alert("删除成功");
            $("tr#" + itemCode).fadeOut(500);
        },
        error: function () {
            alert("ajax请求发送失败");
        }
    });
}
//入库 清空
function deleteAll() {
    showLoading();
    $.ajax({
        url: "/storage/add/deleteAll",
        success: function (result) {
            hideLoading();
            alert("清空成功");
            $("#new_storage_table").find("tbody").find("td").remove();
        },
        error: function () {
            alert("ajax请求发送失败");
        }
    })
}


//apply

function addItem() {
    var da = $('#info_of_apply_item').serialize();
    $.ajax({
        url: "/apply/add/additem",
        type: "post",
        data: da,
        success: function (result) {
            if (result.message == "success") {
                closePop();
                alert("添加成功");
            } else {
                alert("添加失败");
            }
        },
        error: function () {
            alert("添加失败，请重试");
        }
    });
}

//申请 提交
function applyToSubmit() {
    $.ajax({
        url: "/apply/add/submit",
        type: "post",
        success: function (result) {
            if (result.message == "success") {
                alert("提交成功");
                location = "/apply";
            } else {
                alert("提交失败");
            }
        },
        error: function () {
            alert("ajax请求发送失败");
        }
    })
}

function applyDelet(itemCode) {//todo
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
            alert("ajax请求发送失败");
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
            alert(result);
            if (result.message == "success") {
                alert("发送成功");
            } else {
                alert("发送失败");
            }
        },
        error: function () {
            alert("ajax请求发送失败");
        }
    })
}

/**
 * todo 不再显示一条信息
 * 这里直接调用删除信息ajax
 * */

//todo test
function msg_hide(messageID) {
    var $messageBox = $("#" + messageID);
    $.ajax({
        url: "/message/delete",
        data: {"messageID": messageID},
        success: function (result) {
            if (result.message == "success") {
                //设为不再显示
                $messageBox.css("min-height","auto");
                $messageBox.slideUp(500);
                $("#" + messageID).slideUp(500);
            } else {
                alert("不再显示执行失败，错误信息为：" + result.message)
            }
        },
        error: function () {
            alert("你的网络有问题，请稍后重试");
        }
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
            console.log(result.content[0]);
            var _display = function (item) {
                var itemhtml = "<tr style='display: none' id='tr" + item.storageId + "'>" +
                    "<td>" + item.storageId + "</td>" +
                    "<td>" + getDate(item.storageTime) + "</td>" +
                    "<td>" + item.operationId + "</td>" +
                    "<td onclick=\"openPop_storageInfo(\'" + item.storageId + "\')\">" + "操作" + "</td>" +
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


//
function getObjextInfo(object) {
    var s = "";
    for (var i in object) {
        s += "[" + i + ":" + object[i] + "];"
    }
    // JSON.stringify();
    return s;
}

//申请 详情
function openPop_applyInfo(applyID) {
    var $table = $("#apply_table").find("tbody");
    var $applyID = $('#apply_id');
    var $operationID = $('#operation_id');
    var $applyTime = $('#apply_time');
    //init
    $applyID.text("  ");
    $operationID.text("  ");
    $applyTime.text("  ");
    $table.find("tr").remove();

    $.ajax({
        url: "/apply/applyinfo.json?application_id=" + applyID,
        type: "get",

        success: function (result) {
            var applyJSON = result.content;
            if (result.message == "success") {
                openPop();
                $('#apply_id').text(applyJSON.applicationId);
                $('#operation_id').text(applyJSON.usersId);
                $('#apply_time').text(applyJSON.applicationTime);

                var items = applyJSON.items;
                var html = "";
                for (var i = 0; i < items.length; i++) {
                    html += "<tr>" +
                        "<td>" + items[i].itemCode + "</td>" +
                        // "<td>" + items[i].itemName + "</td>" +
                        "<td>" + items[i].counts + "</td>" +
                        "<td>" + items[i].applicationType + "</td>" +
                        "<td>" + items[i].applicationText + "</td>" +
                        "</tr>";
                }
                $table.append(html);
            } else {
                alert("失败");
            }
        },
        error: function () {
            alert("ajax请求发送失败");
        }
    })
}

//审核
function getNeedExamineByID(application_id) {
    var html = "";
    var table = $("#applyFormTable").find("tbody");
    var buttons = $("button");
    //init
    $('#applyFromID').text("  ");
    $('#usersId').text("  ");
    $('#applyTime').text("  ");
    table.find("tr").remove();

    $.ajax({
        url: "/apply/applyinfo.json?application_id=" + application_id,
        type: "get",
        success: function (result) {
            var applyFromJSON = result.content;
            if (result.message == "success") {
                openPop();
                $('#applyFromID').text(applyFromJSON.applicationId);
                $('#usersId').text(applyFromJSON.usersId);
                $('#applyTime').text(applyFromJSON.applicationTime);

                buttons[0].onclick = function () {
                    examineApply(true, application_id);  //通过
                };

                buttons[1].onclick = function () {
                    examineApply(false, application_id);  //不通过
                };

                var html = "";
                var items = applyFromJSON.items;
                // alert(JSON.stringify(items[0]));
                for (var i = 0; i < items.length; i++) {
                    html += "<tr>" +
                        "<td>" + items[i].itemCode + "</td>" +
                        // "<td>" + items[i].itemName + "</td>" +
                        "<td>" + items[i].counts + "</td>" +
                        "<td>" + items[i].applicationType + "</td>" +
                        "<td>" + items[i].applicationText + "</td>" +
                        "</tr>";
                }
                table.append(html);

                /*var _display = function (item) {
                 var html = "<tr style='display:none;' id='tr1" + item.itemCode + "'>" +
                 "<td>" + item.itemCode + "</td>" +
                 "<td>" + item.counts + "</td>" +
                 "<td>" + item.applicationType + "</td>" +
                 "<td>" + item.applicationText + "</td>" +
                 "</td>";
                 table.append(html);
                 };

                 var _afterdisplay = function (item) {
                 console.log('#tr1' + item.itemCode);
                 $('#tr1' + item.itemCode).fadeIn(1000);
                 };

                 beautifyDisplay(_display, _afterdisplay, applyFromJSON.items, "申请单列表");*/
            } else {
                alert("获取失败，失败信息：" + result.message)
            }
        }

    })
}

function examineApply(states, apply_id) {
    $.ajax({
        url: "/manage/passexamine",
        type: "post",
        data: {"states": states, "apply_id": apply_id},
        success: function (result) {
            if (result.message == "success") {
                alert("成功");
                closePop();
                $("#" + apply_id).fadeOut(500);
            } else {
                alert("系统错误");
            }
        },
        error: function () {
            alert("ajax请求发送失败");
        }
    })
}

//出库 查询
function getOutOperationByID(outID) {
    var html = "";
    var $table = $("#out_table").find("tbody");
    var button = document.getElementsByTagName("button")[0];
    // 清空弹出框
    var $out_id=$('#out_id');
    var $users_id=$('#users_id');
    var $out_address=$('#out_address');
    $out_id.text("  ");
    $users_id.text("  ");
    $out_address.text("  ");
    $table.find("tr").remove();

    $.ajax({
        url: "/manage/outopreationinfo.json?out_id=" + outID,
        type: "get",
        success: function (result) {
            if (result.message == "success") {
                var outStorageOperationJSON = JSON.parse(result.content).operation;
                var items = JSON.parse(result.content).outStorages;
                console.log(outStorageOperationJSON);
                $out_id.text(outStorageOperationJSON.outId);
                $users_id.text(outStorageOperationJSON.usersId);
                $out_address.text(outStorageOperationJSON.outAddress);

                openPop();
                button.onclick= function () {
                    outStorage(outStorageOperationJSON.outId);  //确定出库
                };

                for (var i = 0; i < items.length; i++) {
                    html += "<tr>" +
                        "<td>" + items[i].itemCode + "</td>" +
                        // "<td>" + items[i].itemName + "</td>" +
                        "<td>" + items[i].counts + "</td>" +
                        "</tr>";
                }
                $table.append(html);
            } else {
                alert("系统错误");
            }
        },
        error: function () {
            alert("ajax请求发送失败");
        }
    })
}

function outStorage(outID) {
    alert(outID);
    $.ajax({
        url: "/manage/confirmOut",
        type: "post",
        data: {"out_id": outID},
        success: function (result) {
            if (result.message == "success") {
                alert("成功");
                closePop();
                $("#" + outID).fadeOut(500);
            } else {
                alert("系统错误");
            }
        },
        error: function () {
            alert("ajax请求发送失败");
        }
    })
}


//todo 日志
function log_find(type) {
    var typeStr = "";
    var url = "";
    switch (type) {
        case "system":
            typeStr = "系统日志";
            url = "/log/system";
            break;
        case "outStorage":
            typeStr = "出库日志";
            url = "/log/outstorage";
            break;
        case "inStorage":
            typeStr = "入库日志";
            url = "/log/instorage";
            break;
        case "apply":
            typeStr = "申请日志";
            url = "/log/apply";
            break;
        case "itemMaintain":
            typeStr = "物品维护日志";
            url = "/log/maintain";
            break;
    }
    document.getElementsByClassName("message-type")[0].innerHTML = typeStr;

    var logs = log_findBy(url);
}

function log_findBy(url) {
    var logs;
    $.ajax({
        url: url,
        type: "post",
        success: function (result) {
            if (result.message == "success") {
                logs = result.content;
                showLogs(logs);
            } else {
                alert("查询出错");
            }
        },
        error: function () {
            alert("ajax请求发送失败");
        }
    });
}

//todo
function showLogs(logs) {
    var html = "";
    var $log = $("#log");
    $log.find(".message").remove();
    if (logs.length == 0) {
        html = "<div class='message no-message'>没有日志</div>"
    }
    for (var i = 0; i < logs.length; i++) {
        html += "<div class='message' id=''>" +
            "<span class='message-title'><strong>" + logs[i].logId + "</strong></span>" +
            "<span class='message-date'>" + getDateAndTime(logs[i].logDate) + "</span>" +
            "<span class='message-date'>" + logs[i].logLevel + "</span>" +
            "<div class='message-content'>" + logs[i].logInfo + "</div>" +
            // "<div class='message-operation' onclick='openPop()'>详情</div>" +
            "</div>";
    }
    $log.append(html);
}


//todo url findlo
function queryItemInDate() {
    $.ajax({
        url: "/manage/queryItemInDateById.json",
        type: "post",
        data: $("#query_input_info").serialize(),
        success: function (result) {
            if (result.message == "success") {
                var items = result.content;
                showItemInDate(items);
            } else {
                alert("查询失败");
            }
        },
        error: function () {
            alert("ajax发送失败");
        }
    })
}

function showItemInDate(items) {
    var html = "";
    var $table = $("#query_item_result").find("tbody");
    $table.find("tr").remove();
    for (var i = 0; i < items.length; i++) {
        html += "<tr>" +
            "<td >" + items[i].itemCode + "</td>" +
            "<td>" + items[i].itemName + "</td>" +
            "<td>" + items[i].itemBatch + "</td>" +
            "<td>" + items[i].itemIndate + "</td>" +
            "<td>" + items[i].itemSlot + "</td>" +
            "<td>" + items[i].allowCount + "</td>" +
            "</tr>";
    }
    $table.append(html);
}


//table 排序
function sortTable(table, idx) {
    var otable = document.getElementById(table),
        otody = otable.tBodies[0],
        otr = otody.rows,
        tarr = [];
    for (var i = 0; i < otr.length; i++) {
        tarr[i] = otr[i];
    }
    // console.log(tarr);
    if (otody.sortCol == idx) {
        tarr.reverse();
    } else {
        tarr.sort(function (tr1, tr2) {
            var value1 = tr1.cells[idx].innerHTML;
            var value2 = tr2.cells[idx].innerHTML;
            if (!isNaN(value1) && !isNaN(value2)) {
                return value1 - value2;
            } else {
                return value1.localeCompare(value2);
            }
        })
    }
    var fragment = document.createDocumentFragment();
    for (var i = 0; i < tarr.length; i++) {
        fragment.appendChild(tarr[i]);
    }
    otody.appendChild(fragment);
    otody.sortCol = idx;
}

//入库 提交
function storage_submit() {
    $.ajax({
        url: "/storage/add/submit",
        type: "get",
        success: function (result) {
            alert("提交成功");
            location="/storage";
        },
        error: function () {
            alert("ajax请求发送失败");
        }
    })
}


//毫秒转换
function getDateAndTime(ms) {
    var d = new Date(ms);
    return d.toLocaleString();
}
function getDate(ms) {
    var d = new Date(ms);
    var year = d.getFullYear();
    var month = d.getMonth();
    var date = d.getDate();
    return year + "-" + month + "-" + date;
}
