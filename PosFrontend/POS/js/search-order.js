$(document).ready(function () {

    $.ajax({
        method:"GET",
        url:"http://localhost:8080/api/v1/orders?maxid=lol",
        async:true
    }).done(function (orders,textStatus,jqXHR) {
        for (var i=0;i<orders.length;i++){
            $('tbody').append('<tr><td>'+orders[i].detailsDTO.orderid+'</td><td>'+orders[i].detailsDTO.cusid+'</td><td>'+orders[i].detailsDTO.orderdate+'</td><td>'+"25000"+'</td></tr>');
        }
    }).fail(function (error) {
        alert("can't load orders");
    });

});

$('#search').keyup(function () {

      $("tbody tr").hide();
    // console.log($(this).val())
    // $('td:contains('+$(this).val()+')').parents('tr').show();

    var key = $(this).val();
    $('tbody tr td').each(function () {
        var nw1 = $(this).text().substring(0,key.length);
        //console.log(nw,key);
        if (nw1 === key){
            $(this).parents('tr').show();
        }

    });
});