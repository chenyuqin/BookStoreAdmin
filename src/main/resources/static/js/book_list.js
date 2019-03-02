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
    render(0, 0, 0);
    layui.use(['layer', 'jquery', 'form'], function () {
        var layer = layui.layer
            , form = layui.form
            , $ = layui.jquery;
        form.on('select(cate1)', function (data) {
            if (data.value == 0) {
                $("#cate2").html("<option value=\"0\">请选择分类</option>");
                $("#cate3").html("<option value=\"0\">请选择分类</option>");
                form.render('select');
                render(parseInt(data.value), 0, 0);
            } else {
                $.ajax({
                    url: "book/getAllByCategoryId",
                    data: {"category_id": parseInt(data.value)},
                    type: "get",
                    dataType: "JSON",
                    success: function (result) {
                        $("#cate2").html("<option value=\"0\">请选择分类</option>");
                        $.each(result, function (key, val) {
                            var option1 = $("<option>").val(val.id).text(val.cateName);
                            $("#cate2").append(option1);
                            form.render('select');
                        });
                    }
                });
                render(parseInt(data.value), 0, 0);
            }
        });
        form.on('select(cate2)', function (data) {
            if (data.value == 0) {
                $("#cate3").html("<option value=\"0\">请选择分类</option>");
                form.render('select');
                render($("#cate1 option:selected").val(), 0, 0);
            } else {
                $.ajax({
                    url: "book/getAllByBiggestCate",
                    data: {"biggestCate": data.value},
                    type: "get",
                    dataType: "JSON",
                    success: function (result) {
                        $("#cate3").html("<option value=\"0\">请选择分类</option>");
                        $.each(result, function (key, val) {
                            var option1 = $("<option>").val(val.id).text(val.cateName);
                            $("#cate3").append(option1);
                            form.render('select');
                        });
                    }
                });
                render(0,$("#cate2 option:selected").text(),0);
            }
        });
        form.on('select(cate3)', function (data) {
            if (data.value == 0) {
                render($("#cate2 option:selected").val(), 0, 0);
            } else {
                render(0, 0, $("#cate3 option:selected").text());
            }
        });
        form.on('submit(sreach)', function () {
            var queryString = $("#queryString").val();
            if (queryString == "" || queryString == null || queryString == undefined) {
                layer.msg('请输入有效的查询信息！');
                return false;
            } else {
                renderByQuery(queryString);
                return false;
            }
        });
    });
});

function render(category, biggestCate, biggerCate) {
    layui.use(['table', 'jquery', 'form', 'admin'], function () {
        var table = layui.table,
            $ = layui.jquery,
            admin = layui.admin;

        table.render({
            elem: '#articleList',
            cellMinWidth: 20,
            url: 'book/getAllBooks',
            // even: true,
            page: true,
            limits: [10, 20, 50],
            limit: 10,
            method: 'post',
            where: {category: category, biggestCate: biggestCate, biggerCate: biggerCate},
            cols: [
                [
                    {
                        field: 'operate', title: '操作', toolbar: '#operateTpl', align: 'center',
                        width: 80, unresize: true
                    },
                    {
                        field: 'id', title: 'ID', width: 69, align: 'center'
                    },
                    {
                        field: 'image1',
                        title: '图片',
                        align: 'center',
                        style: 'height:120px;',
                        templet: '#imgTpl',
                        width: 146
                    },
                    {
                        field: 'name', title: '书名', width: 211
                    },
                    {
                        field: 'author', title: '作者', unresize: true, align: 'center', width: 120
                    },

                    {
                        field: 'biggestCate', title: '分类', width: 105, align: 'center'
                    },
                    {
                        field: 'biggerCate', title: '小分类', width: 105, align: 'center'
                    },
                    {
                        field: 'publisher', title: '出版社', unresize: true, align: 'center', width: 128
                    },
                    {
                        field: 'publishTime', title: '出版时间', width: 105, align: 'center'
                    },
                    {
                        field: 'price', title: '价格', width: 80, align: 'center'
                    },
                    {
                        field: 'star', title: '好评率', unresize: true, width: 80, align: 'center'
                    }]
            ],
            done: function (res, curr, count) {
                $('th').css({'font-weight': '600', 'font-size': '15px', 'text-align': 'center'});
                $('tr').css({'font-family': '宋体', 'font-size': '13px'});
                $("#resultCount").text("搜索结果：" + count + "本");
            }
        });
        //监听工具条
        table.on('tool(demo)', function (obj) {
            var data = obj.data //获得当前行数据
                , layEvent = obj.event; //获得 lay-event 对应的值

            if (layEvent === 'detail') {
                window.open('http://139.199.207.248:8088/book_detail.html?bid=' + data.id);
            } else if (layEvent === 'del') {
                layer.confirm('确认是否删除该图书？', function (index) {
                    obj.del(); //删除对应行（tr）的DOM结构
                    layer.close(index);
                    //向服务端发送删除指令
                    $.ajax({
                        url: "book/delBookById",
                        data: {"id": data.id},
                        type: "post",
                        dataType: "JSON",
                        success: function (result) {

                        }
                    });
                });
            } else if (layEvent === 'edit') {
                WeAdminShow('图书编辑','editBook.html?bid=' + data.id);
            }
        });
    });
}

function renderByQuery(queryString) {
    layui.use(['table', 'jquery', 'form', 'admin'], function () {
        var table = layui.table,
            $ = layui.jquery,
            admin = layui.admin;

        table.render({
            elem: '#articleList',
            cellMinWidth: 20,
            url: 'book/getAllBooksBySearch',
            // even: true,
            page: true,
            limits: [10, 20, 50],
            limit: 10,
            method: 'post',
            where: {queryString: queryString},
            cols: [
                [
                    {
                        field: 'operate', title: '操作', toolbar: '#operateTpl', align: 'center',
                        width: 80, unresize: true
                    },
                    {
                        field: 'id', title: 'ID', width: 69, align: 'center'
                    },
                    {
                        field: 'image1',
                        title: '图片',
                        align: 'center',
                        style: 'height:120px;',
                        templet: '#imgTpl',
                        width: 146
                    },
                    {
                        field: 'name', title: '书名', width: 211
                    },
                    {
                        field: 'author', title: '作者', unresize: true, align: 'center', width: 120
                    },

                    {
                        field: 'biggestCate', title: '分类', width: 105, align: 'center'
                    },
                    {
                        field: 'biggerCate', title: '小分类', width: 105, align: 'center'
                    },
                    {
                        field: 'publisher', title: '出版社', unresize: true, align: 'center', width: 128
                    },
                    {
                        field: 'publishTime', title: '出版时间', width: 105, align: 'center'
                    },
                    {
                        field: 'price', title: '价格', width: 80, align: 'center'
                    },
                    {
                        field: 'star', title: '好评率', unresize: true, width: 80, align: 'center'
                    }]
            ],
            done: function (res, curr, count) {
                $('th').css({'font-weight': '600', 'font-size': '15px', 'text-align': 'center'});
                $('tr').css({'font-family': '宋体', 'font-size': '13px'});
                $("#resultCount").text("搜索结果：" + count + "本");
            }
        });
        //监听工具条
        table.on('tool(demo)', function (obj) {
            var data = obj.data //获得当前行数据
                , layEvent = obj.event; //获得 lay-event 对应的值

            if (layEvent === 'detail') {
                window.open('http://139.199.207.248:8088/book_detail.html?bid=' + data.id);
            } else if (layEvent === 'del') {
                layer.confirm('确认是否删除该图书？', function (index) {
                    obj.del(); //删除对应行（tr）的DOM结构
                    layer.close(index);
                    //向服务端发送删除指令

                });
            } else if (layEvent === 'edit') {
                WeAdminShow('图书编辑','editBook.html?bid=' + data.id);
            }
        });
    });
}




