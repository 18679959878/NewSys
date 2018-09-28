/**
 * 
 */
	function getQueryString(name) {
		var paraTotal = decodeURI(window.location.search);
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
		var r = paraTotal.substr(1).match(reg);
		if (r != null) return unescape(r[2]);
		return null;
	}