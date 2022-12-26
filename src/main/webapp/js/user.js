$(document).ready(function () {
    $("#data1").click(function () {
        $("#userData1").toggle();
        $("#userData2").hide();
        $("#userData3").hide();
    });
});
$(document).ready(function () {
    $("#data2").click(function () {
        $("#userData1").hide();
        $("#userData2").toggle();
        $("#userData3").hide();
    });
});
$(document).ready(function () {
    $("#data3").click(function () {
        $("#userData1").hide();
        $("#userData2").hide();
        $("#userData3").toggle();
    });
});