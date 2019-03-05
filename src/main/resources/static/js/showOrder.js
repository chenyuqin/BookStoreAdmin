$(function () {
    $.ajax({
        url: "order/getOrderBookByOrderId",
        data: {"order_id": getQueryString("id")},
        type: "post",
        dataType: "JSON",
        success: function (result) {
            $.each(result, function (index, item) {
                $("#content").append(
                    '<div style="height: 120px;">\n' +
                    '\t\t\t\t<img class="image1" src="' + item.image1 + '" />\n' +
                    '\t\t\t\t<div style="float: left;width: 400px;">\n' +
                    '\t\t\t\t    <a href="' + "http://139.199.207.248:8088/book_detail.html?bid=" + result.id + '" class="name" target="_blank">' + item.name + '</a>\n' +
                    '\t\t\t\t    <p class="author">' + item.author + '</p>\n' +
                    '\t\t\t\t    <p class="price">￥' + item.price + '</p>\n' +
                    '\t\t\t\t    <p class="count">数量：' + item.count + '</p>\n' +
                    '                </div>\n' +
                    '\t\t\t</div>\n' +
                    '\t\t\t<hr>'
                )
            });
        }
    });
});

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}