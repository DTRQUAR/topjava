function makeEditable() {
    form = $('#detailsForm');

    /*
    * Функция срабатывает при нажатии на кнопку "Добавить пользователя"
    * - form.find(":input").val("") - ищем все поля с типом input и присваивем им значение
    * пустой строки
    * - $('#id').val(0) - элементу с id = id присваиваем значение 0
    * - $('#editRow').modal(); - у элемента с id = editRow, вызываем открытие
    * модального окна
    * -
    * */
    $('#add').click(function () {
        form.find(":input").val("");
        $('#id').val(0);
        $('#editRow').modal();
    });

    /*
    * Функция срабатывает при нажатии на кнопку Save в модальном
    * окне ввода данных пользователя
    * */
    form.submit(function () {
        save();
        return false;
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(event, jqXHR, options, jsExc);
    });

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    /*
    * ajaxSend будет выполняться для каждого AJAX'овского запроса
    * */
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
}

/*
* Событие срабатывающие при нажатии на кнпоку Edit
* - присваиваем всем input полям уже существующие
* значения у объекта
* - Открывается модальное окно, для редактирования
* при этом input ол
* */
function updateRow(id) {
    $.get(ajaxUrl + id, function (data) {
        $.each(data, function (key, value) {
            form.find("input[name='" + key + "']").val(value);
        });
        $('#editRow').modal();
    });
}

function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: 'DELETE',
        success: function () {
            updateTable();
            successNoty('Deleted');
        }
    });
}

/*
* Функция срабатывает при нажатии на checkbox у пользователя
* - chkbox.closest('tr') - находит все элементы лежащие внутри этого тэга,
* в нашем случае это строка (row)
* */
function enable(chkbox, id) {
    var enabled = chkbox.is(":checked");
    chkbox.closest('tr').css("text-decoration", enabled ? "none" : "line-through");
    $.ajax({
        url: ajaxUrl + id,
        type: 'POST',
        data: 'enabled=' + enabled,
        success: function () {
            successNoty(enabled ? 'Enabled' : 'Disabled');
        }
    });
}

/*
* Функция обновления таблицы по данным
* datatableApi - здесь хранится результат выполнения фун-ии DataTable()
* сначала очищаем таблицу, затем добавляем строки, пользуясь data
* */
function updateTableByData(data) {
    datatableApi.clear().rows.add(data).draw();
}

/*
* Функция реализующая сохранение пользователя или еды
* эта функция вызывается при нажатии на кнопку save
* модального окна
* */
function save() {
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: function () {
            $('#editRow').modal('hide');
            updateTable();
            successNoty('Saved');
        }
    });
}

var failedNote;

/*
* Функция реализующая закрытие окна уведомления
* */
function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

/*
* Выводится уведомление вниу справа, в зеленой блоке
* */
function successNoty(text) {
    closeNoty();
    noty({
        text: text,
        type: 'success',
        layout: 'bottomRight',
        timeout: true
    });
}

function failNoty(event, jqXHR, options, jsExc) {
    closeNoty();
    var errorInfo = $.parseJSON(jqXHR.responseText);
    failedNote = noty({
        text: 'Failed: ' + jqXHR.statusText + "<br>" + errorInfo.cause + "<br>" + errorInfo.detail,
        type: 'error',
        layout: 'bottomRight'
    });
}

/*
* Добавляется кнопка редактирования для записи в строке
* вешается событие: при клике срабатывает фнукция updateRow(id)
* */
function renderEditBtn(data, type, row) {
    if (type == 'display') {
        return '<a class="btn btn-xs btn-primary" onclick="updateRow(' + row.id + ');">Edit</a>';
    }
    return data;
}

/*
 * Добавляется кнопка удаления записи в строке
 * вешается событие: при клике срабатывает фнукция deleteRow(id)
 * */
function renderDeleteBtn(data, type, row) {
    if (type == 'display') {
        return '<a class="btn btn-xs btn-danger" onclick="deleteRow(' + row.id + ');">Delete</a>';
    }
    return data;
}
