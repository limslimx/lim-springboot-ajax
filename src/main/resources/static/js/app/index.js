var index={
    init: function () {
        var _this = this;
        $("#btn-submit").on("click", function () {
            _this.submit();
        });
        $("#btn-submit2").on("click", function () {
            _this.submit2();
        });
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
    },
    submit2: function () {
        var data = {
            name: $("#name").val(),
            age: $("#age").val()
        };

        $.ajax({
            url: "/member/info2",
            type: "post",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),
            success: function (json) {
                console.log(json);
                $("#ajax-test").html(json.test);
            },
            error: function () {
                alert("error");
            }
        });
    }
};

index.init();