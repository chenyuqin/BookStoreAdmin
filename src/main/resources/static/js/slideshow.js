$(function () {

    layui.use(['form', 'layedit', 'laydate'], function () {
        var form = layui.form,
            layer = layui.layer,
            layedit = layui.layedit,
            laydate = layui.laydate;

        $.ajax({
            url: "slideshow/getSlideshows",
            data: {},
            type: "get",
            dataType: "JSON",
            success: function (result) {
                $.each(result, function (index, item) {
                    $("#img" + item.id).val(item.img);
                    $("#preview" + item.id).attr("src", item.img);
                    $("#url" + item.id).val(item.url.split("bid=")[1]);
                    $("#url" + item.id).parent().parent().find("a").attr("href", "http://139.199.207.248:8088" + item.url);

                    $("#img" + item.id).on("input", function (e) {
                        //获取input输入的值
                        $("#preview" + item.id).attr("src", e.delegateTarget.value);
                    });

                    $("#url" + item.id).on("input", function (e) {
                        //获取input输入的值
                        $("#url" + item.id).parent().parent().find("a").attr("href", "http://139.199.207.248:8088/book_detail.html?bid=" + e.delegateTarget.value);
                    });

                    $("#reset" + item.id).click(function () {
                        $.ajax({
                            url: "slideshow/getSlideshowById",
                            data: {"id": item.id},
                            type: "get",
                            dataType: "JSON",
                            success: function (result) {
                                $("#img" + item.id).val(result.img);
                                $("#preview" + item.id).attr("src", result.img);
                                $("#url" + item.id).val(result.url.split("bid=")[1]);
                                $("#url" + item.id).parent().parent().find("a").attr("href", "http://139.199.207.248:8088" + result.url);
                            }
                        });
                        return false;
                    });

                    $("#sumit" + item.id).click(function () {
                        $.ajax({
                            url: "slideshow/updateSlideshowById",
                            data: {
                                "id": item.id, "img": $("#img" + item.id).val(),
                                "url": "/book_detail.html?bid=" + $("#url" + item.id).val()
                            },
                            type: "post",
                            dataType: "JSON",
                            success: function (result) {

                            }
                        });
                        layer.alert("修改成功！", {
                            icon: 6
                        }, function () {

                        });
                        return false;
                    });
                });
            }
        });
    });
});