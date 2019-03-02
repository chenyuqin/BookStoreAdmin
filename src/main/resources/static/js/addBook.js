$(function () {

    layui.use(['form', 'layedit', 'laydate'], function () {
        var form = layui.form,
            layer = layui.layer,
            layedit = layui.layedit,
            laydate = layui.laydate;

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
                url: "book/insertBook",
                data: {"json": JSON.stringify(data.field)},
                type: "post",
                dataType: "JSON",
                async: false,
                success: function (result) {

                }
            });
            layer.alert("添加成功！", {
                icon: 6
            }, function() {
                parent.location.reload();
            });
            return false;
        });
    });
});