$(function () {
    var order_id = getQueryString('id');
    $(".legend_book_id").text(order_id);

    layui.use(['form', 'layedit', 'laydate'], function () {
        var form = layui.form,
            layer = layui.layer,
            layedit = layui.layedit,
            laydate = layui.laydate;
        form.render('select');

        form.on('submit(demo1)', function (data) {
            $.ajax({
                url: "order/insertLogistic",
                data: {"order_id": order_id, "id": $("#l_id").val(), "name": $("#name option:selected").text()},
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