/**
 * 
 */
/* Dizinler */
//Upload Image Dizini

function getUploadImagesPath() {
	return "/upload/";
}

function getMonthMap(month) {
	monthMap = {"01":"Ocak", "02":"Şubat", "03":"Mart", "04": "Nisan", "05": "Mayıs", "06": "haziran",
			"07": "Temmuz", "08": "Ağustos", "09": "Eylül", "10": "Ekim", "11": "Kasım", "12": "Aralık"};
	return monthMap[month];
}



/* SAVE ENTITY */

function ajaxCallEntityOperation(entityForm) {
	var resultAjax = false;
	var formData = new FormData(entityForm);
	$.ajax({
		type: $(entityForm).attr('method'),
		url: $(entityForm).attr('action'),
		async: false,
		cache: false,
        contentType: false,
        processData: false,
		data: formData,
		success: function (resp) {
			if(resp.errors != null) {
				alert(resp.errors);
			} 
			else if (resp.result) {
				resultAjax = true;
				alert(resp.resultMsg);
			}
			else window.location.replace("login.html");
		}
		
	});
	return resultAjax;
}

/* UPLOAD ENTITY */

function updateEntity(entityForm) {
	var resultAjax = false;
	var formData = new FormData(entityForm);
	$.ajax({
		type: $(entityForm).attr('method'),
		url: $(entityForm).attr('action'),
		async: false,
		cache: false,
        contentType: false,
        processData: false,
		data: formData,
		success: function (result) {
			var resp = $.parseJSON(result);
			if(resp.errors != null) {
				alert(resp.errors);
			} 
			else alert(resp.resultMsg);
			if (resp.result) {
				resultAjax = true;
			}
			
		}
		
	});
	return resultAjax;
}

function getAllEntity(url) {
	var entities = null;
	$.ajax({
		type: 'GET',
		async: false,
		url: url,
		datatype: 'json',
		success: function(data) {
			entities = data[0];
		}
	});
	
	return entities;
}

function getEntityById(url, id) {
	var entity = null;
	$.ajax({
		type: 'GET',
		async: false,
		url: url,
		data: {id: id},
		datatype: 'json',
		success: function(resp) {
		entity = $.parseJSON(resp.entity);
		}
	});
	return entity;
}

function getEntityCount(url) {
	var count = null;
	$.ajax({
		type: 'GET',
		async: false,
		url: url,
		datatype: 'json',
		success: function(resp) {
		count = $.parseJSON(resp.count);
		}
	});
	return count;
}

function getEntitiesByLimit(url, limit) {
	var entities = null;
	$.ajax({
		type: 'GET',
		async: false,
		url: url,
		data: {limit: limit},
		datatype: 'json',
		success: function(data) {
			entities = data[0];
		}
	});
	
	return entities;
}

function getEntitiesByLimitAndOffset(url, limit, offset) {
	var entities = null;
	$.ajax({
		type: 'GET',
		async: false,
		url: url,
		data: {limit: limit, offset: offset},
		datatype: 'json',
		success: function(data) {
			entities = data[0];
		}
	});
	
	return entities;
}


function isUrlExsist(url) {
	var result = false;
	$.ajax({
	    type: 'HEAD',
	    url: url,
	    asnc: false,
	success: function() {
	        reault = true
	},
	error: function() {
	        // page does not exist
	}
	});
	return result;
}

/* FOOTER JS */
var contact = getEntityById("/GetContact", 1);
var phone = document.getElementById("phone");
var mobilePhone = document.getElementById("mobilePhone");
var fax = document.getElementById("fax");
var email = document.getElementById("email");
var facebook = document.getElementById("facebook");
var twitter = document.getElementById("twitter");
var instagram = document.getElementById("instagram");
var address = document.getElementById("address");

phone.innerHTML = "Phone: " + contact.phone;
mobilePhone.innerHTML = "Mobile Phone: " + contact.mobilePhone;
fax.innerHTML = "Fax: " + contact.fax;
email.innerHTML = contact.email;
email.href = "mailto:" + contact.email;
facebook.href = contact.facebookLink;
twitter.href = contact.twitterLink;
instagram.href = contact.instagramLink;
address.innerHTML = contact.address;

/* EN FOOTER JS*/

/* AJAX LOADING */

var $loading = $('#loadingAjax');
$loading.hide();
$(document)
	.ajaxStart(function () {
		$loading.show();
	})
	.ajaxStop(function () {
		$loading.hide();
});