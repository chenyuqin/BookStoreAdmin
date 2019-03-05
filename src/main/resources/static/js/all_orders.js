$(function () {
    // $.ajax({
    //     url: "book/getAllBooks",
    //     data: {"status": 0},
    //     type: "get",
    //     dataType: "JSON",
    //     success: function (result) {
    //
    //     }
    // });

});

function renderTable(status) {
    layui.use(['table', 'jquery', 'admin'], function () {
        var table = layui.table,
            $ = layui.jquery,
            admin = layui.admin;

        table.render({
            elem: '#articleList',
            cellMinWidth: 20,
            url: 'order/getAllOrders',
            // even: true,
            page: true,
            limits: [10, 20, 50],
            limit: 10,
            method: 'post',
            where: {status: parseInt(status)},
            cols: [
                [
                    {
                        field: 'operate', title: '操作', toolbar: '#operateTpl', align: 'center',
                        width: 90, unresize: true
                    },
                    {
                        field: 'order_id', title: '订单号', width: 160, align: 'center'
                    },
                    {
                        field: 'name', title: '收件人', width: 75, templet:function (d) {
                            return d.address.name;
                        }
                    },
                    {
                        field: 'phone', title: '联系方式', unresize: true, align: 'center', width: 120, templet:function (d) {
                            return d.address.phone;
                        }
                    },

                    {
                        field: 'addr', title: '收件地址', templet: '#reviewTpl', align: 'center',style: 'font-size:13px;',
                    },
                    {
                        field: 'postcode', title: '邮政编码', width: 90, align: 'center', templet:function (d) {
                            return d.address.postcode;
                        }
                    },
                    {
                        field: 'totalPrice', title: '总价钱', width: 90, align: 'center', templet:function (d) {
                            return "￥" + d.totalPrice;
                        }
                    },
                    {
                        field: 'pay_way_tpl', templet: '#pay_way_tpl', title: '支付方式', unresize: true, align: 'center', width: 100
                    },
                    {
                        field: 'createTime', title: '创建时间', unresize: true, align: 'center', width: 160
                    },
                    {
                        field: 'status', templet: '#status_tpl', title: '订单状态', unresize: true, align: 'center', width: 100
                    }
                ]
            ],
            done: function (res, curr, count) {
                $("#resultCount").text("搜索结果：" + count + "条");
            }
        });
        //监听工具条
        table.on('tool(demo)', function (obj) {
            var data = obj.data //获得当前行数据
                , layEvent = obj.event; //获得 lay-event 对应的值

            if (layEvent === 'detail') {
                WeAdminShow('查看订单详情', 'showOrder.html?id=' + data.order_id, 600, 600);
            } else if (layEvent === 'toSend') {
                WeAdminShow('填写物流信息', 'editLogistic.html?id=' + data.order_id, 400, 270);
            }
        });
    });
}



