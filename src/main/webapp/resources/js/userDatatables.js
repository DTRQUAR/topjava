var ajaxUrl = 'ajax/admin/users/';
var datatableApi;

/*
* Функция обновления таблицы
* посылаем запрос get по урлу ajaxUrl
*
* */
function updateTable() {
    $.get(ajaxUrl, function (data) {
        updateTableByData(data);
    });
}

/*
* Функция срабатывающая после загрузки страницы
* - применяем к элементу с id = datatable, функцию DataTable(), для отрисовки таблицы
* - "url": ajaxUrl - указываем урл, по которму будем брать данные (срабатываем метод контроллера AdminAjaxController,
* который возвращает список пользователей в формате JSON)
* */
$(function () {
    datatableApi = $('#datatable').DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "email",
                "render": function (data, type, row) {
                    if (type == 'display') {
                        return '<a href="mailto:' + data + '">' + data + '</a>';
                    }
                    return data;
                }
            },
            {
                "data": "roles"
            },
            {
                "data": "enabled",
                "render": function (data, type, row) {
                    if (type == 'display') {
                        return '<input type="checkbox" ' + (data ? 'checked' : '') + ' onclick="enable($(this),' + row.id + ');"/>';
                    }
                    return data;
                }
            },
            {
                "data": "registered",
                "render": function (date, type, row) {
                    if (type == 'display') {
                        var dateObject = new Date(date);
                        return '<span>' + dateObject.toISOString().substring(0, 10) + '</span>';
                    }
                    return date;
                }
            },
            {
                "orderable": false,
                "sDefaultContent": "",
                "render": renderEditBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        /*
        * 0 - указывает по какой колонке будет производится упорядочивание
        * asc - по возрастанию
        * */
        "order": [
            [
                0,
                "asc"
            ]
        ],
        /*
        * Данная функция вызывается когда отрисовывается строка
        * Эта функию проверяет - если enabled = false, то строка с даными зачеркивается
        * */
        "createdRow": function (row, data, dataIndex) {
            if (!data.enabled) {
                $(row).css("text-decoration", "line-through");
            }
        },

        /*
        * Выполняем фун-ию makeEditable, после полной загрузки данных таблицы
        * (когда таблицы уже отрисовалась)
        * */
        "initComplete": makeEditable
    });
});