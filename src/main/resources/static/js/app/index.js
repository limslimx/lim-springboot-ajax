var index={
    init: function () {
        var _this = this;
        $("#btn-submit").on("click", function () {
            _this.submit();
        })
    },
    submit: function () {
        var data={
            name: $("#name").val(),
            age: $("#age").val()
        };

        $.ajax({
            url: "/member/info",
            type: "post",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),
            success: function (json) {
                var msg="";
                for (var i = 0; i < json.length; i++) {
                    msg+=("<div>"+json[i].name);
                    msg+=("<div>"+json[i].age);
                }
                $("#ajax-test").html(msg);
            },
            error: function () {
                alert("error");
            }
        });
    }
};

index.init();