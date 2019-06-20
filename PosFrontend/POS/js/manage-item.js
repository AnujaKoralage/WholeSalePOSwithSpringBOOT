
function getCurrentCode(){
    var lastCode = $('tbody tr:last-child td:first-child').text();
    console.log(lastCode);
    var newCode = 'I00'+(parseInt(lastCode.substring(1))+1);
    return newCode;
}

$(document).ready(function () {
    $('#exampleInputQuantity').change(function () {
        $('#exampleInputQuantity').css('box-shadow','1px 2px 5px  #0062cc');
    });
    $('#exampleInputDescription').change(function () {
        $('#exampleInputDescription').css('box-shadow','1px 2px 5px  #0062cc');
    });
    $('#exampleInputPrice').change(function () {
        $('#exampleInputPrice').css('box-shadow','1px 2px 5px  #0062cc');
    });

    $.ajax({
        method:"GET",
        url:"http://localhost:8080/api/v1/items",
        async:true
    }).done(function (items) {
        for (var i=0; i<items.length; i++){
            $('tbody').append('<tr><td>'+("I"+items[i].code)+'</td><td>'+items[i].description+'</td><td>'+items[i].qty+'</td><td>'+items[i].price+'</td><td><img src="https://img.icons8.com/material/24/000000/delete.png"></td></tr>');
        }

        $('#exampleInputItemCode').val(getCurrentCode());
        $('#exampleInputDescription').focus();

        //update
        $('tbody tr').click(function () {
            var th = $(this);
            var valid =true;

            var code = $(this).find('td:nth-child(1)').text();
            var description = $(this).find('td:nth-child(2)').text();
            var qty = $(this).find('td:nth-child(3)').text();
            var price = $(this).find('td:nth-child(4)').text();

            $('#exampleInputItemCode').val(code);
            $('#exampleInputDescription').val(description);
            $('#exampleInputQuantity').val(qty);
            $('#exampleInputPrice').val(price);

            $('#addItem').replaceWith('<button id="updateItem" type="button" class="btn btn-primary">Update Item</button>');
            $('#updateItem').off('click');
            $('#updateItem').click(function () {

                if ($.trim($('#exampleInputDescription').val()).length === 0) {
                    $('#exampleInputDescription').css('box-shadow','1px 2px 5px  #b80009');
                    valid = false;
                }
                if ($.trim($('#exampleInputPrice').val()).length === 0) {
                    $('#exampleInputPrice').css('box-shadow','1px 2px 5px  #b80009');
                    valid = false;
                }
                if ($.trim($('#exampleInputQuantity').val()).length === 0) {
                    $('#exampleInputQuantity').css('box-shadow','1px 2px 5px  #b80009');
                    valid = false;
                }

                if (valid){
                    th.find('td:nth-child(2)').replaceWith('<td>'+$('#exampleInputDescription').val()+'</td>');
                    th.find('td:nth-child(3)').replaceWith('<td>'+$('#exampleInputQuantity').val()+'</td>');
                    th.find('td:nth-child(4)').replaceWith('<td>'+$('#exampleInputPrice').val()+'</td>');

                    $.ajax({
                        method:"PUT",
                        url:"http://localhost:8080/api/v1/items/"+code.substring(1),
                        async:true,
                        contentType: 'application/json',
                        data:JSON.stringify({
                            description:$('#exampleInputDescription').val(),
                            qty:$('#exampleInputQuantity').val(),
                            price:$('#exampleInputPrice').val()
                        })
                    }).done(function (data) {
                        alert("Item Updated Successfully");
                    }).fail(function (error) {
                        alert("Item can't update");
                    });

                    $('#exampleInputDescription').val('');
                    $('#exampleInputQuantity').val('');
                    $('#exampleInputPrice').val('');

                    $('#updateItem').replaceWith('<button id="addItem" type="button" class="btn btn-primary">Add Item</button>');
                    $('#exampleInputItemCode').val(getCurrentCode());
                    $('#exampleInputDescription').focus()
                }

            });
        });

        //delete
        $('tbody tr td:last-child img').click(function () {
            var d = $(this).parents('tr').find('td:nth-child(1)').text();
            var addbtn = $("#addItem").clone(true);
            console.log(d);
            if (confirm("Do you really want to delete?")){
                var t = $(this).parents('tr');

                $.ajax({
                    method:"DELETE",
                    url:"http://localhost:8080/api/v1/items/"+d.substring(1),
                    async:true
                }).done(function (data) {
                    alert("Customer Deleted Successfully");
                    t.remove();

                    $('#exampleInputDescription').focus();

                    $("#exampleInputItemCode").val(getCurrentCode());
                    $('#exampleInputDescription').val('');
                    $('#exampleInputQuantity').val('');
                    $('#exampleInputPrice').val('');

                    $("#updateItem").replaceWith(addbtn);

                }).fail(function (error) {
                    alert("Item can't delete");

                    $('#exampleInputDescription').focus();

                    $("#exampleInputItemCode").val(getCurrentCode());
                    $('#exampleInputDescription').val('');
                    $('#exampleInputQuantity').val('');
                    $('#exampleInputPrice').val('');

                    $("#updateItem").replaceWith(addbtn);
                });
            }
        });



    })

});

$('#addItem').click(function () {
    var valid = true;
    if ($.trim($('#exampleInputDescription').val()).length === 0) {
        $('#exampleInputDescription').css('box-shadow','1px 2px 5px  #b80009');
        valid = false;
    }
    if ($.trim($('#exampleInputPrice').val()).length === 0) {
        $('#exampleInputPrice').css('box-shadow','1px 2px 5px  #b80009');
        valid = false;
    }
    if ($.trim($('#exampleInputQuantity').val()).length === 0) {
        $('#exampleInputQuantity').css('box-shadow','1px 2px 5px  #b80009');
        valid = false;
    }
    if (valid){
        var code = $('#exampleInputItemCode').val();
        var description = $('#exampleInputDescription').val();
        var quantity = $('#exampleInputQuantity').val();
        var price = $('#exampleInputPrice').val();

        $('tbody').append('<tr><td>'+code+'</td><td>'+description+'</td><td>'+quantity+'</td><td>'+price+'</td><td><img src="https://img.icons8.com/material/24/000000/delete.png"></td></tr>');

        $.ajax({
            method:"POST",
            url:"http://localhost:8080/api/v1/items",
            async:true,
            contentType: 'application/json',
            data:JSON.stringify({
                code:code.substring(1),
                description:description,
                qty:quantity,
                price:price
            })})
                .done(function (response) {
                    alert("Item Successfully Saved");

                    $('#exampleInputItemCode').val(getCurrentCode());
                    $('#exampleInputDescription').val('');
                    $('#exampleInputQuantity').val('');
                    $('#exampleInputPrice').val('');
                    $('#exampleInputDescription').focus();

                    //delete
                    $('tbody tr td:last-child img').off("click");
                    $('tbody tr td:last-child img').click(function () {
                        if (confirm("Do you really want to delete?")){
                            var addbtn1 = $("#addItem").clone(true);
                            var d = $(this).parents('tr').find('td:nth-child(1)').text();
                            console.log(d);
                            var t = $(this).parents('tr');

                            $.ajax({
                                method:"DELETE",
                                url:"http://localhost:8080/api/v1/items/"+d.substring(1),
                                async:true
                            }).done(function (data) {
                                alert("Customer Deleted Successfully");
                                t.remove();

                                $('#exampleInputDescription').focus();

                                $("#exampleInputItemCode").val(getCurrentCode());
                                $('#exampleInputDescription').val('');
                                $('#exampleInputQuantity').val('');
                                $('#exampleInputPrice').val('');

                                $("#updateItem").replaceWith(addbtn1);
                            }).fail(function (JXHR) {
                                alert(JXHR.valueOf().toString());

                                $('#exampleInputDescription').focus();

                                $("#exampleInputItemCode").val(getCurrentCode());
                                $('#exampleInputDescription').val('');
                                $('#exampleInputQuantity').val('');
                                $('#exampleInputPrice').val('');

                                $("#updateItem").replaceWith(addbtn1);
                            });
                        }
                    });


                    //update
                    $('tbody tr').off("click");
                    $('tbody tr').click(function () {
                        var th = $(this);
                        var valid =true;

                        var code = $(this).find('td:nth-child(1)').text();
                        var description = $(this).find('td:nth-child(2)').text();
                        var qty = $(this).find('td:nth-child(3)').text();
                        var price = $(this).find('td:nth-child(4)').text();

                        $('#exampleInputItemCode').val(code);
                        $('#exampleInputDescription').val(description);
                        $('#exampleInputQuantity').val(qty);
                        $('#exampleInputPrice').val(price);

                        $('#addItem').replaceWith('<button id="updateItem" type="button" class="btn btn-primary">Update Item</button>');
                        $('#updateItem').off('click');
                        $('#updateItem').click(function () {

                            if ($.trim($('#exampleInputDescription').val()).length === 0) {
                                $('#exampleInputDescription').css('box-shadow','1px 2px 5px  #b80009');
                                valid = false;
                            }
                            if ($.trim($('#exampleInputPrice').val()).length === 0) {
                                $('#exampleInputPrice').css('box-shadow','1px 2px 5px  #b80009');
                                valid = false;
                            }
                            if ($.trim($('#exampleInputQuantity').val()).length === 0) {
                                $('#exampleInputQuantity').css('box-shadow','1px 2px 5px  #b80009');
                                valid = false;
                            }

                            if (valid){
                                th.find('td:nth-child(2)').replaceWith('<td>'+$('#exampleInputDescription').val()+'</td>');
                                th.find('td:nth-child(3)').replaceWith('<td>'+$('#exampleInputQuantity').val()+'</td>');
                                th.find('td:nth-child(4)').replaceWith('<td>'+$('#exampleInputPrice').val()+'</td>');

                                $.ajax({
                                    method:"PUT",
                                    url:"http://localhost:8080/item/"+code.substring(1),
                                    async:true,
                                    contentType: 'application/json',
                                    data:JSON.stringify({
                                        description:$('#exampleInputDescription').val(),
                                        qty:$('#exampleInputQuantity').val(),
                                        price:$('#exampleInputPrice').val()
                                    })
                                }).done(function (data) {
                                    alert("Item Updated Successfully");
                                }).fail(function (error) {
                                    console.log(error);
                                });

                                $('#exampleInputDescription').val('');
                                $('#exampleInputQuantity').val('');
                                $('#exampleInputPrice').val('');

                                $('#updateItem').replaceWith('<button id="addItem" type="button" class="btn btn-primary">Add Item</button>');
                                $('#exampleInputItemCode').val(getCurrentCode());
                                $('#exampleInputDescription').focus()
                            }

                        });
                    });

                })
                .fail(function (errorThrown) {
                    console.log(errorThrown);
                });


    }
});