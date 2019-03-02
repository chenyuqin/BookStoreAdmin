$(function () {
    var id = getQueryString("bid");
    $(".legend_book_id").text(id);

    layui.use(['form', 'layedit', 'laydate'], function () {
        var form = layui.form,
            layer = layui.layer,
            layedit = layui.layedit,
            laydate = layui.laydate;

        $.ajax({
            url: "book/getBookById",
            data: {"id": parseInt(id)},
            type: "post",
            dataType: "JSON",
            async: false,
            success: function (result) {
                $("#name").val(result.book.name);
                $("#author").val(result.book.author);
                $("#publisher").val(result.book.publisher);
                $("#publishTime").val(result.book.publishTime);
                var category = result.book.category;
                $("#category option[value=" + category + "]").attr("selected", "");
                $("#biggestCate").html("<option value=\"0\">请选择二级分类</option>");
                $.each(result.biggestcates, function (key, val) {
                    if (val.cateName == result.book.biggestCate) {
                        var option1 = $("<option>").val(val.id).text(val.cateName).attr("selected", "");
                        $("#biggestCate").append(option1);
                    } else {
                        var option1 = $("<option>").val(val.id).text(val.cateName);
                        $("#biggestCate").append(option1);
                    }
                    form.render('select');
                });

                $("#biggerCate").html("<option value=\"0\">请选择三级分类</option>");
                $.each(result.biggercates, function (key, val) {
                    if (val.cateName == result.book.biggerCate) {
                        var option1 = $("<option>").val(val.id).text(val.cateName).attr("selected", "");
                        $("#biggerCate").append(option1);
                    } else {
                        var option1 = $("<option>").val(val.id).text(val.cateName);
                        $("#biggerCate").append(option1);
                    }
                    form.render('select');
                });

                $("#describe").val(result.book.describe);
                $("#detail").val(result.book.detail);
                $("#price").val(result.book.price);
                $("#prePrice").val(result.book.prePrice);
                $("#stock").val(result.book.stock);
                $("#image1").val(result.book.image1);
                $("#image2").val(result.book.image2);
                $("#image3").val(result.book.image3);
                $("#image4").val(result.book.image4);
                $("#image5").val(result.book.image5);
                $("#packing").val(result.book.packing);
                $("#paper").val(result.book.paper);
                $("#suit").val(result.book.suit);
                $("#ISBN").val(result.book.isbn);
                $("#contentSummary").val(result.book.contentSummary);
                $("#authorSummary").val(result.book.authorSummary);
                $("#catalog").val(result.book.catalog);
            }
        });

        form.on('select(category)', function (data) {
            if (data.value == 0) {
                $("#biggestCate").html("<option value=\"0\">请选择二级分类</option>");
                $("#biggerCate").html("<option value=\"0\">请选择三级分类</option>");
                form.render('select');
            } else {
                $("#biggerCate").html("<option value=\"0\">请选择三级分类</option>");
                $.ajax({
                    url: "book/getAllByCategoryId",
                    data: {"category_id": parseInt(data.value)},
                    type: "get",
                    dataType: "JSON",
                    success: function (result) {
                        $("#biggestCate").html("<option value=\"0\">请选择二级分类</option>");
                        $.each(result, function (key, val) {
                            var option1 = $("<option>").val(val.id).text(val.cateName);
                            $("#biggestCate").append(option1);
                            form.render('select');
                        });
                    }
                });
            }
        });
        form.on('select(biggestCate)', function (data) {
            if (data.value == 0) {
                $("#biggerCate").html("<option value=\"0\">请选择三级分类</option>");
                form.render('select');
            } else {
                $.ajax({
                    url: "book/getAllByBiggestCate",
                    data: {"biggestCate": data.value},
                    type: "get",
                    dataType: "JSON",
                    success: function (result) {
                        $("#biggerCate").html("<option value=\"0\">请选择三级分类</option>");
                        $.each(result, function (key, val) {
                            var option1 = $("<option>").val(val.id).text(val.cateName);
                            $("#biggerCate").append(option1);
                            form.render('select');
                        });
                    }
                });
            }
        });

        //监听提交
        form.on('submit(demo1)', function (data) {
            $.ajax({
                url: "book/editBookById",
                data: {"id": parseInt(id), "json": JSON.stringify(data.field)},
                type: "post",
                dataType: "JSON",
                async: false,
                success: function (result) {

                }
            });
            layer.alert("修改成功！", {
                icon: 6
            }, function() {
                parent.location.reload();
                // 获得frame索引
                var index = parent.layer.getFrameIndex(window.name);
                //关闭当前frame
                parent.layer.close(index);
            });
            return false;
        });
    });
});


function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}