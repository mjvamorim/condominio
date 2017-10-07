<input type="hidden" class='id' name="id" value="x" />


$(function() {	
	$('.js-atualizar-permissoes').on('submit', function(event) {
		event.preventDefault();
		var modal = $(this);
		
		
		var botaoSalvar = $(event.currentTarget);
		var urlSalvar = botaoSalvar.attr('href');
		
		var response = $.ajax({
			url: '/security/users/permissoes',
			type: 'GET'
		});
		
		
		response.done(function(retorno) {
			var codigoTitulo = botaoSalvar.data('codigo');
			$('[data-role=' + codigoTitulo + ']').html('<span class="label label-success">' + retorno + '</span>');
			modal.dismiss();
		});
		
		response.fail(function(retorno) {
			console.log(retorno);
			alert('Erro salvando propriedades');
		});
		
	});
});

$('#usersRolesModal').on('show.bs.modal',function(event) {
	
	console.log(
			${roleRepository}.get(0).getName()
		);
	
	var button = $(event.relatedTarget);
	var model = button.data('model');
	var codigo = button.data('codigo');
	var descricao = button.data('descricao');
	var userRoles = button.data('userroles');
	var array = JSON.parse("[" + userRoles + "]");
	console.log(userRoles);
	console.log(array);

	var modal = $(this);

	modal.find('.modal-body span').html(
			'Tem certeza que deseja excluir o ' + userRoles
					+ ' <strong>' + descricao + '</strong>?');
	
	var checkbox = modal.find('.ckRole2');
	checkbox.prop("checked",true);
	console.log(checkbox.prop( "checked" ));
	
	
	/*<![CDATA[*/
	var allRoles = [[${allRoles}]];
	console.log(allRoles);
	for (var r in allRoles){
		if (allRoles.hasOwnProperty(r)) {
			console.log(r);
			console.log(allRoles[r]);
			if ( $.inArray(allRoles[r], array) > -1 ) {
				var checkbox = modal.find('.ck'+allRoles[r]);
				checkbox.prop("checked",true);	
			}
		}
	}
	/*]]>*/
	
// 	for (var option in optionArray) {
// 		var pair = optionArray[option];
// 		var checkbox = document.createElement("input");
// 		checkbox.type = "checkbox";
// 		checkbox.name = pair;
// 		checkbox.value = pair;
// 		checkbox.checked = "checked";
// 		sct.appendChild(checkbox);

// 		var label = document.createElement('label')
// 		label.htmlFor = pair;
// 		label.appendChild(document.createTextNode(pair));

// 		sct.appendChild(label);
// 		sct.appendChild(document.createElement("br"));
// 	}			
});



var inputhidden = document.createElement("input");
	inputhidden.type = "hidden";
	inputhidden.name = 'id';
	inputhidden.value = codigo;
	divhidden.appendChild(inputhidden);
	
	
	###Ajax
	
	var response = $.ajax({

	});
	
	
	response.done(function(retorno) {
		var codigoTitulo = botaoSalvar.data('codigo');
		$('[data-role=' + codigoTitulo + ']').html('<span class="label label-success">' + retorno + '</span>');
		modal.hide; ;
	});
	
	response.fail(function(retorno) {
		console.log(retorno);
		alert('Erro salvando propriedades');
	});
	