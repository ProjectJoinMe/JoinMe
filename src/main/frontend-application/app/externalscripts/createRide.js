/**
 * Created by Nicole on 14.03.2017.
 */
$("#").click(function ()
{
    if ($("#toAndReturnCheckBox").attr("checked"))
    {
        $(".returnFormRow").show();
    }
    else
    {
        $(".returnFormRow").hide();
    }
});