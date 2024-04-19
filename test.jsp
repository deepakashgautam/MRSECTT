<html>
<script type="text/javascript">
function callMe(obj) {
//alert(obj.name);
//alert(obj.value);
	var id = obj.getAttribute('name');
	var value = obj.getAttribute('value');
	if(value.length != 0) {
		if(value.length == 8) {
			var dd = value.substring(0,2);
			var mm = value.substring(2,4);
			var yy = value.substring(4);
			var isLpY = isleap(yy);
	//	alert(isLpY);
			if(mm > 0 && mm < 13) {
				if(yy > 1900) {
					if(mm == '01' && (dd >= 01 && dd <= 31)) {
						value = dd+'/'+mm+'/'+yy;
						window.document.getElementById(id).value = value;
					}else if(mm == '02' && (dd >= 01 && dd <= 28)) {
						value = dd+'/'+mm+'/'+yy;
						window.document.getElementById(id).value = value;
					}else if(mm == '03' && (dd >= 01 && dd <= 31)) {
						value = dd+'/'+mm+'/'+yy;
						window.document.getElementById(id).value = value;
					}else if(mm == '04' && (dd >= 01 && dd <= 30)) {
						value = dd+'/'+mm+'/'+yy;
						window.document.getElementById(id).value = value;
					}else if(mm == '05' && (dd >= 01 && dd <= 31)) {
						value = dd+'/'+mm+'/'+yy;
						window.document.getElementById(id).value = value;
					}else if(mm == '06' && (dd >= 01 && dd <= 30)) {
						value = dd+'/'+mm+'/'+yy;
						window.document.getElementById(id).value = value;
					}else if(mm == '07' && (dd >= 01 && dd <= 31)) {
						value = dd+'/'+mm+'/'+yy;
						window.document.getElementById(id).value = value;
					}else if(mm == '08' && (dd >= 01 && dd <= 31)) {
						value = dd+'/'+mm+'/'+yy;
						window.document.getElementById(id).value = value;
					}else if(mm == '09' && (dd >= 01 && dd <= 31)) {
						value = dd+'/'+mm+'/'+yy;
						window.document.getElementById(id).value = value;
					}else if(mm == '10' && (dd >= 01 && dd <= 31)) {
						value = dd+'/'+mm+'/'+yy;
						window.document.getElementById(id).value = value;
					}else if(mm == '11' && (dd >= 01 && dd <= 31)) {
						value = dd+'/'+mm+'/'+yy;
						window.document.getElementById(id).value = value;
					}else if(mm == '12' && (dd >= 01 && dd <= 31)) {
						value = dd+'/'+mm+'/'+yy;
						window.document.getElementById(id).value = value;
					}else {
						alert('Not a valid day.');
						window.document.getElementById(id).focus();
					}
				}else {
					alert('Not a valid year.');
					window.document.getElementById(id).focus();
				}
			}else {
				alert('Not a valid month.');
				window.document.getElementById(id).focus();
			}
		}
		else {
			alert('Invalid date format. Use only ddMMyyyy format.');
			window.document.getElementById(id).focus();
			window.document.getElementById(id).value='';		
		}
	}
}

function isleap(year)
{
 //var year = yy.value;
 //alert('year : '+ year);
 if((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) {
 	alert('return 1');
 	return '1';
 }else {
 	alert('return 0');
 	return '0';
 }
}

</script>
<BODY BGCOLOR=#FFFFFF LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0>
<input type="text" name="date" id="date" onblur="callMe(this);">
<input type="text" name="year" id="year" onclick="isleap(this);">
</BODY>
</html>