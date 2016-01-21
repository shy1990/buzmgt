$(document).ready(function () {
//上移
$("span.arow-up").each(function () {
$(this).click(function () {
var $tr = $(this).parents("tr");
if ($tr.index() != 0) {
$tr.prev().before($tr);
}
});
});
//下移
var trLength = $("span.arow-down").length; 
$("span.arow-down").each(function () {
$(this).click(function () {
var $tr = $(this).parents("tr"); 
if ($tr.index() != trLength - 1) { 
$tr.next().after($tr);
}
});
});
});