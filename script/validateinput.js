/****************************************************************
 * Common Javascript File for TMS Application					*
 ****************************************************************
 * Create Date:		01/10/2009									*
 * Last Updated:	10/02/2010		Updated By:		Devendra	*
 * Version: 1.0.0												*
 ****************************************************************/




var errorColor='yellow';	// Color for highlighing the error field
var normalColor='white';	// Color for non error field

/*****************************************************************
* Custom Error Handler                                           *
******************************************************************/
onerror=myerrorHandler;

function myerrorHandler(msg,file_loc,line_num)	{
	
	alert(	"Javascript Error Occured!!!\n\n" +
		"Description : " + msg + "\n\n" +
		"File:         " + file_loc + "\n\n" +
		"Line Number:  " + line_num);
	return false;
} 

/*****************************************************************
* Finding pixel position of an object on page                    *
******************************************************************/
function findPos(obj) {
	var curleft = curtop = 0;
	if (obj.offsetParent) {
		do {
			curleft += parseInt(obj.offsetLeft);
			curtop += obj.offsetTop;
		} while (obj = obj.offsetParent);
		return [curleft,curtop];
	}
}	
	
/*****************************************************************
* Calender for entering Date with input parameter of text field  *
******************************************************************/
/*function newCalendar(txtid)
{	
	var width  = 200;
	var height = 200;
	var left   = (screen.width  - width)/2;
	var top    = (screen.height - height)/2;
	var params = 'width='+width+', height='+height;
	params += ', top='+top+', left='+left;
	params += ', directories=no';
	params += ', location=no';
	params += ', menubar=no';
	params += ', resizable=no';
	params += ', scrollbars=yes';
	params += ', status=no';
	params += ', toolbar=no';
	 
	var x = window.open("CalendarPopUp.jsp?"+txtid,"CAL",params);	
	var pos =findPos(document.getElementById(txtid.split('-')[0]));
	x.focus();
}*/

function newCalendar(txtid,cnt)
{	
	//alert(cnt);	
var width  = 200;
	var height = 200;
	var left   = (screen.width  - width)/2;
	var top    = (screen.height - height)/2;
	var params = 'width='+width+', height='+height;
	params += ', top='+top+', left='+left;
	params += ', directories=no';
	params += ', location=no';
	params += ', menubar=no';
	params += ', resizable=no';
	params += ', scrollbars=yes';
	params += ', status=no';
	params += ', toolbar=no';
	var x="";
	if(cnt==undefined)
	x = window.open("CalendarPopUp.jsp?"+txtid,"CAL",params);
	else
	{ 
		
		x = window.open("CalendarPopUp.jsp?"+txtid+"~"+cnt,"CAL",params);
	
	}
	var pos =findPos(document.getElementById(txtid.split('-')[0]));
	x.focus();
}

/***********************************************************************
* OnKeyPress event of text field to allow only numeric charaters.      *
* To allow any character apart from numeric asccii value can be paased.*
************************************************************************/
//function chknumeric(except){
//	if((window.event.keyCode<48 || window.event.keyCode>57) && (window.event.keyCode!=eval(except)))
//		window.event.keyCode=-1;	
//}

/*****************************************************************************************************
* Function modified by Munendra Singh on 14/06/2010 to allow at max 3 special characters at a time  *
******************************************************************************************************/
function chknumeric(except1, except2, except3)
{
	if((window.event.keyCode<48 || window.event.keyCode>57) && (window.event.keyCode!=eval(except1)) && (window.event.keyCode!=eval(except2)) && (window.event.keyCode!=eval(except3)))
	{
		window.event.keyCode=-1;
	}
}

/***********************************************************************
* OnKeyPress event of text field to allow only alpha numeric charaters.*
* To allow any special characters, list of characters to be passed     *
* in single quotes.                                                    *
************************************************************************/
function allowAlphaNum(allowed)
{	
	var str = String.fromCharCode(window.event.keyCode);
	if(!allowed)
		allowed=""; // if allowed is not passed convert undifined to ""
	var regex=new RegExp("^[\\w "+ allowed + "]+$","i"); // is other than numeric, apha & space
	if(regex.test(str))
		return true;
	 else {
		window.event.keyCode=-1;
		return false;
	}	
}

/***********************************************************************
* OnKeyPress event of text field to allow only alpha numeric charaters *
* without space. To allow any special characters, list of              *
* characters to be passed in single quotes.                            *
************************************************************************/
function allowAlphaNumWithoutSpace(allowed)
{	
	var str = String.fromCharCode(window.event.keyCode);
	if(!allowed)
		allowed=""; // if allowed is not passed convert undifined to ""	
	var regex=new RegExp("^[0-9A-Za-z"+ allowed + "]+$","i");  ///^[0-9A-Za-z]+$/; // is other than numeric, apha
	if(regex.test(str))
		return true;
	else {
		window.event.keyCode=-1;
		return false;
	}	
}

/***********************************************************************
* OnKeyPress event of text field to allow only numeric charaters.      *
* To allow any special characters, list of                             *
* characters to be passed in single quotes.                            *
************************************************************************/
function allowNum(allowed)
{	
	var str = String.fromCharCode(window.event.keyCode);
	if(!allowed)
		allowed=""; // if allowed is not passed convert undifined to ""	
	var regexNum=new RegExp("[0-9-"+ allowed + "][.]{0,1}[0-9-"+ allowed + "]"); // is other than numeric, apha & space
	var regexSign= new RegExp("[+]{0,1}");
	if( regexNum.test(str) && regexSign.test(str) )		
		return true;
	else {
		window.event.keyCode=-1;
		return false;
	}
}

/***********************************************************************
* Onblur event of text field to verify proper numeric format           *
* that might be floating or signed.                                    *
************************************************************************/
function chkNum(obj)
{
	var str = obj.value;
	str=str.Trim();
	var regexNum=  /^\s*(\+|-)?((\d+(\.\d+)?)|(\.\d+))\s*$/;
	if( regexNum.test(str) || str.length==0 ){		
		obj.style.backgroundColor=normalColor;
		obj.value=str;
		return true;
	} else {
		alert("Number Format Required!!") ;
		obj.style.backgroundColor=errorColor;
		obj.value=str;
		obj.focus();
		return false;
	}		
}

/******************************************************
* To check the that field cannot be empty.            *
******************************************************/
function chkblank(obj) 
{
	var msg="Input required for highlighted field...!";
	//alert('obj : '+ obj + ' \name is '+ obj.type);
	
	if((obj.type=='text')||(obj.type=='password')||(obj.type=='hidden')||(obj.type=='textarea'))	// Object is a textbox like
	{
		if(obj.value.length==0){
			obj.style.backgroundColor=errorColor;
			obj.focus();
			alert(msg);
			return false;
		}
		obj.style.backgroundColor=normalColor;
		return true;
	}
	else if(obj.type=='select-one')	//	Object is a Select option
	{
		if((obj.options[obj.selectedIndex].value).length==0){
			obj.style.backgroundColor=errorColor;
			obj.focus();
			alert(msg);
			return false;
		}
		obj.style.backgroundColor=normalColor;
		return true;
	}
	else if(obj.constructor==String) 	// Object passed is a string so use array control
	{
		var argcnt = arguments.length;
		var objArray=document.getElementsByName(obj);
		for(var j=objArray.length-1;j>=1;j--){
			msg="Input required for highlighted row...!";
			if(objArray[j].value.length==0){
				//for(var i=0;i<arguments.length;i++){
					//var temp = document.getElementsByName(arguments[i]);
					//if(temp[j].value.length!=0){
						objArray[j].style.backgroundColor=errorColor;
						objArray[j].focus();
						alert(msg);						
						return false;											
					//}
				//}							
			}
			objArray[j].style.backgroundColor=normalColor;					
		}
		return true;
	}
	else	
		alert("Cannot evaluate field type at runtime. Please Check...!");		
	return true;
}


/************************************************************************
* To check the that field cannot be empty. Changed as no need of label  *
************************************************************************/
function chkblankwithlabel(obj, label){	
	return chkblank(obj);
}

/****************************************************************
* To hide or display an HTML object using css display property  *
****************************************************************/
function hideunhide(obj,x){
	obj.style.display=x==true?'block':'none';
}

/****************************************************************
* To disable or enable an HTML object(s)                        *
****************************************************************/
function disabled(obj,x)	{
	if(obj.constructor!=String)	// Object is not a string so disable direct	
		obj.disabled=x;	
	else						// Object is string so disable array control
	{
		var objArray = document.getElementsByName(obj);
		for(var i=objArray.length-1;i>=0;i--){			
			objArray[i].disabled=x;
		}
	}
}

function chkPercent(obj){
	var x = parseFloat(obj.value);
	//alert(obj.value.length);
	if (obj.value.length>0 && (isNaN(x) || x < 0 || x > 100)) {
	    alert("Please enter valid percentage...!!!");
	    obj.focus();	    
	}
}

function chkPercentFin(obj){
	var x = parseFloat(obj.value);
	//alert(obj.value.length);
	if (obj.value.length>0 && (isNaN(x) || x < 0 || x > 150)) {
	    alert("Please enter valid percentage...!!!");
	    obj.focus();	    
	}
}

/***************************************************************
* To check for duplicate values                                *
****************************************************************/
function containsDuplicate(objname)	{
	var objArray = document.getElementsByName(objname);
	var msgobjname="";
	if(objname.toUpperCase()=="LINECODE")
		msgobjname="line";
	else if(objname.toUpperCase()=="STNCODE")
		msgobjname="station";
	else if(objname.toUpperCase()=="LOOPLINECODE")
		msgobjname="loop line";
	else
		msgobjname=objname;	
	for(var i=objArray.length-1;i>=0;i--){
		objArray[i].style.backgroundColor=normalColor;
	}
	for(var i=objArray.length-1;i>=0;i--){
		if(objArray[i].type=='text'){	
			for(var j=objArray.length-1;j>=i;j--){
				if(  (i!=j) && (objArray[i].value.length!=0) && (objArray[i].value.toUpperCase()==objArray[j].value.toUpperCase())){
					objArray[i].style.backgroundColor=errorColor;
					objArray[j].style.backgroundColor=errorColor;
					alert("Duplicate value found at line no " + i + " and "+j);
					objArray[i].focus();
					return false;
				}
			}
		}
		else if(objArray[i].type=='select-one'){			 	
			for(var j=objArray.length-1;j>=i;j--){						
				if(  (i!=j) && (objArray[i].options[objArray[i].selectedIndex].value.length!=0) && (objArray[i].options[objArray[i].selectedIndex].value==objArray[j].options[objArray[j].selectedIndex].value)){
					objArray[i].style.backgroundColor=errorColor;
					objArray[j].style.backgroundColor=errorColor;
					alert("Duplicate value found at line no " + i + " and "+j);
					objArray[i].focus();
					return false;
				}
			}
		}
	}
	return true;
}

/****************************************************************
* To clone the first row of a tbody and append it.              *
* Adds ADD ROW functionality to a table                         *
****************************************************************/
function addrow(targetobj)	{
	
	//alert(targetobj.childNodes.length);
	
	/*
	for(var i=0;i<targetobj.childNodes.length;i++)
	{
		alert(targetobj.childNodes[i].nodeType);
		if(targetobj.childNodes[i].nodeType=="3")
		{
			alert(targetobj.childNodes[i]);
			targetobj.removeChild(targetobj.childNodes[i]);
			break;
		}
	}
	
	var newtr = targetobj.childNodes[0].cloneNode(true);
	for(var i=0;i<newtr.childNodes.length;i++)
	{
		alert(newtr.childNodes[i].nodeType);
		if(newtr.childNodes[i].nodeType=="3")
		{
			alert(newtr.childNodes[i]);
			newtr.removeChild(newtr.childNodes[i]);
			break;
		}
	}
	alert(newtr);
	*/
	var isie = checkVersion();
	if(isie){
		var newtr = targetobj.firstChild.cloneNode(true);
		//alert(targetobj.firstChild.innerHTML);
	}
	else {
		var newtr = targetobj.firstElementChild.cloneNode(true);
		//alert(targetobj.firstElementChild.innerHTML);
	}
	
	newtr.style.display='block';

	newtr.className=targetobj.childNodes.length%2==0?'treven':'trodd';
	targetobj.appendChild(newtr);
	
	//alert('targetobj ' +targetobj);
	//alert('targetobj length' +targetobj.length);

}
function getInternetExplorerVersion()
{
  var rv = -1; // Return value assumes failure.
  if (navigator.appName == 'Microsoft Internet Explorer')
  {
    var ua = navigator.userAgent;
    var re  = new RegExp("MSIE ([0-9]{1,}[\.0-9]{0,})");
    if (re.exec(ua) != null)
      rv = parseFloat( RegExp.$1 );
  }
  return rv;
}

function checkVersion()
{
  var ver = getInternetExplorerVersion();
  if ( ver > -1 )
  {
    return true;
  }	else{
  	return false;
  }
}
function html5_cloneNode(element) {
	  var div = html5_createElement('div'); // create a HTML5-safe element

	  div.innerHTML = element.outerHTML; // set HTML5-safe element's innerHTML as input element's outerHTML

	  return div.firstChild; // return HTML5-safe element's first child, which is an outerHTML clone of the input element
	} 
function cloneNodeA(node) {
    // If the node is a text node, then re-create it rather than clone it
    var clone = node.nodeType == 3 ? document.createTextNode(node.nodeValue) : node.cloneNodeA(false);
 
    // Recurse     
    var child = node.firstChild;
    while(child) {
        clone.appendChild(cloneNodeA(child));
        child = child.nextSibling;
    }
     
    return clone;
}

/****************************************************************
* Padds the value of an HTML object(s)                          *
****************************************************************/
function padd(obj,type,padchar,padlength)	{
	var val;
	if(obj.constructor!=String)
		val = obj.value;
	else
		val = obj;
	if((val.length<padlength)&&(val.length!=0)){
		if((type=='r')||(type=='R')){
			while(val.length<padlength){
				val = val + padchar;
			}
		}else if((type=='l')||(type=='L')){
			while(val.length<padlength){
				val = padchar + val;
			}
		}else
			alert("Not a valid padding. Use on of (r, R, l, L) !!!");		
	}
	if(obj.constructor!=String)
		obj.value = val;
	else
		return val;
}

/****************************************************************
* Changes case of an HTML object(s) value                       *
****************************************************************/
function changecase(obj,c){ 
	var str = obj.value;
	str=str.Trim();
	obj.value = str;
	if((c=='u')||(c=='U')){
		obj.value=obj.value.toUpperCase();
	}else if((c=='l')||(c=='L')){
		obj.value=obj.value.toLowerCase();
	}else
		alert("Not a valid padding. Use one of (u, U, l, L) !!!");		
}

/****************************************************************
* Trim the Left and Right spaces *
****************************************************************/
function trimValue(obj){ 
	var str = obj.value;
	str=str.Trim();
	obj.value = str;
	return;	
} 

/****************************************************************
* Sort the fileds according to numeric format                   *
* Value should be like <TR><TD><INPUT 'obj'></TD></TR>          *
****************************************************************/
function sortnumeric(objname,type,header){	
	var objArray = document.getElementsByName(objname);
	if((type=='a')||(type=='A')){
		for(var i=0;i<objArray.length;i++){
			for(var j=0;j<=i;j++){
				if(parseInt(objArray[i].value,10)<parseInt(objArray[j].value,10)){
					var temprow1 	= objArray[i].parentNode.parentNode.cloneNode(true);
					var temprow2 	= objArray[j].parentNode.parentNode.cloneNode(true);						
					objArray[i].parentNode.parentNode.parentNode.replaceChild(temprow2,objArray[i].parentNode.parentNode);
					objArray[j].parentNode.parentNode.parentNode.replaceChild(temprow1,objArray[j].parentNode.parentNode);
				}
			}
		}
		header.setAttribute("onclick",function () {sortnumeric(objname,'d',this)});
		header.innerHTML = header.innerText + '<IMG border="0" src="images/darrow.gif" width="20" height="15">';
	}else if((type=='d')||(type=='D')){
		for(var i=0;i<objArray.length;i++){
			for(var j=0;j<=i;j++){
				if(parseInt(objArray[i].value,10)>parseInt(objArray[j].value,10)){
					var temprow1 	= objArray[i].parentNode.parentNode.cloneNode(true);
					var temprow2 	= objArray[j].parentNode.parentNode.cloneNode(true);					
					objArray[i].parentNode.parentNode.parentNode.replaceChild(temprow2,objArray[i].parentNode.parentNode);
					objArray[j].parentNode.parentNode.parentNode.replaceChild(temprow1,objArray[j].parentNode.parentNode);				
				}
			}
		}
		header.setAttribute("onclick",function () {sortnumeric(objname,'a',this)});
		header.innerHTML = header.innerText + '<IMG border="0" src="images/uarrow.gif" width="20" height="15">';
	}else
		alert("Not a valid sort order");		
}


/****************************************************************
* Sort the fileds according to alphanumeric format              *
* Value should be like <TR><TD><INPUT 'obj'></TD></TR>          *
****************************************************************/
function sortalpha(objname,type,header)	{		
	var objArray = document.getElementsByName(objname);
	if((type=='a')||(type=='A')){
		for(var i=0;i<objArray.length;i++){
			for(var j=0;j<=i;j++){
				if((objArray[i].value.toUpperCase()) < (objArray[j].value.toUpperCase())){
					var temprow1 	= objArray[i].parentNode.parentNode.cloneNode(true);
					var temprow2 	= objArray[j].parentNode.parentNode.cloneNode(true);						
					objArray[i].parentNode.parentNode.parentNode.replaceChild(temprow2,objArray[i].parentNode.parentNode);
					objArray[j].parentNode.parentNode.parentNode.replaceChild(temprow1,objArray[j].parentNode.parentNode);					
				}					
			}
		}
		header.setAttribute("onclick",function () {sortalpha(objname,'d',this)});
		header.innerHTML = header.innerText + '<IMG border="0" src="tms.images/darrow.gif" width="20" height="15">';
	}else if((type=='d')||(type=='D')){
		for(var i=0;i<objArray.length;i++){
			for(var j=0;j<=i;j++){
				if((objArray[i].value.toUpperCase())>(objArray[j].value.toUpperCase())){
					var temprow1 	= objArray[i].parentNode.parentNode.cloneNode(true);
					var temprow2 	= objArray[j].parentNode.parentNode.cloneNode(true);						
					objArray[i].parentNode.parentNode.parentNode.replaceChild(temprow2,objArray[i].parentNode.parentNode);
					objArray[j].parentNode.parentNode.parentNode.replaceChild(temprow1,objArray[j].parentNode.parentNode);						
				}					
			}
		}
		header.setAttribute("onclick",function () {sortalpha(objname,'a',this)});
		header.innerHTML = header.innerText + '<IMG border="0" src="tms.images/uarrow.gif" width="20" height="15">';
	}else
		alert("Not a valid sort order");		
}

/****************************************************************
* Move selected Item(s) of listbox  lstbxFrom to  lstbxTo       *
****************************************************************/

function fnMoveItems(lstbxFrom,lstbxTo)	{
	var varFromBox = document.all(lstbxFrom);
	var varToBox = document.all(lstbxTo);
	if ((varFromBox != null) && (varToBox != null)){
		if(varFromBox.length < 1){
			alert('There are no items in the source ListBox...!');
			return false;
		}
		if(varFromBox.options.selectedIndex == -1) // when no Item is selected the index will be -1
		{
			alert('Please select an Item to move...!');
			return false;
		}
		while ( varFromBox.options.selectedIndex >= 0 ){
			var newOption = new Option(); // Create a new instance of ListItem
			newOption.text = varFromBox.options[varFromBox.options.selectedIndex].text;
			newOption.value = varFromBox.options[varFromBox.options.selectedIndex].value;
			varToBox.options[varToBox.length] = newOption; //Append the item in Target Listbox
			varFromBox.remove(varFromBox.options.selectedIndex); //Remove the item from Source Listbox
		}
	}
	return false;
}

/****************************************************************
* Move all Items of listbox  lstbxFrom to  lstbxTo              *
****************************************************************/
function fnMoveAll(lstbxFrom,lstbxTo)	{
	var varFromBox = document.all(lstbxFrom);
	var varToBox = document.all(lstbxTo);
	if ((varFromBox != null) && (varToBox != null)){
		if(varFromBox.length < 1){
			alert('There are no items in the source ListBox...!');
			return false;
		}
		while ( varFromBox.length > 0 ){
			var newOption = new Option(); // Create a new instance of ListItem
			newOption.text = varFromBox.options[0].text;
			newOption.value = varFromBox.options[0].value;
			varToBox.options[varToBox.length] = newOption; //Append the item in Target Listbox
			varFromBox.remove(0); //Remove the item from Source Listbox
		}
	}
	return true;
}

/***************************************************************
* Select All items of a listbox. Use before submitting form    *
****************************************************************/
function fnSelectAll(lstbx)	{
	lstbx = document.all(lstbx);
	for(var i=0;i< lstbx.length ; i++ ){
		lstbx.options[i].selected = true;
	}
	return false;
}

/***************************************************************
* Swap items of a listbox at index1 and index2                 *
****************************************************************/
function fnSwapOptions(element,index1,index2){
	if (typeof element == 'string') { element = document.getElementById(element); }
    // Make sure the indexes are valid
    if (index1 != index2 &&
        index1 >= 0 && index1 < element.options.length &&
        index2 >= 0 && index2 < element.options.length) {
        // Save the selection state of all of the options because Opera
        // seems to forget them when we click the button
        var optionStates = new Array();
        for(i = 0; i < element.options.length; i++) {
            optionStates[i] = element.options[i].selected;
        }
        // Save the first option into a temporary variable
        var option = element.options[index1];
        // Copy the second option into the first option's place
        element.options[index1] =
        new Option(element.options[index2].text,
        		element.options[index2].value,
                element.options[index2].defaultSelected,
                element.options[index2].selected);

        // Copy the first option into the second option's place
        element.options[index2] = 
            new Option(option.text,
                       option.value,
                       option.defaultSelected,
                       option.selected);
        // Reset the selection states for Opera's benefit
        for(i = 0; i < element.options.length; i++) {
            element.options[i].selected = optionStates[i];
        }
        // Then select the ones we swapped, if they were selected before the swap
        element.options[index1].selected = optionStates[index2];
        element.options[index2].selected = optionStates[index1];
    }
}

/***************************************************************
* Move selected item(s) of a listbox up or down                *
****************************************************************/
function fnMoveUpDown(obj,lstbx,dir){	
	lstbx = document.all(lstbx);
	if(lstbx.length==0){
		alert("There is nothing to move...!");
		return false;
	}
	if( dir=="U" && lstbx.selectedIndex>0 ){
		for(var i=lstbx.selectedIndex-1;i< lstbx.length ; i++ ){
			if(lstbx.options[i].selected)
				fnSwapOptions(lstbx,i-1,i);			
		}
	}
	if( dir=="D" && lstbx.selectedIndex<lstbx.length ){
		for(var i=lstbx.length-1;i>=0; i-- ){
			if(lstbx.options[i].selected)
				fnSwapOptions(lstbx,i,i+1);
		}
	}
}

/**************************************
 * 	Generic search utility            *
 * 	id of the tbody should be content *
 **************************************/
function search(){
	if(((arguments.length)%2==0)&&(arguments.length>0)){
		var searchboxes		=	new Array();
		var searchcolumns	=	new Array();
		var columnsvalues	=	new Array();
		var tb = document.getElementById('content');
		var str="";
		for(var i=0;i<arguments.length;i=i+2){
			searchboxes[i/2]		=arguments[i];
			searchcolumns[i/2]	=arguments[i+1];
		}
		for(var i=0;i<searchcolumns.length;i++)		
			columnsvalues[i]=document.getElementsByName(searchcolumns[i]);
		//initialize
		for(var i=0;i<tb.childNodes.length;i++)
			tb.childNodes[i].style.display='block';				
		for(var i=0;i<searchboxes.length;i++){
			var searchstring  =	document.getElementById(searchboxes[i]).value;
			var temp = columnsvalues[i];								
				if(searchstring.length>0){					
					for(var x=temp.length-1; x>=0;x--){
						if(!((temp[x].value.toUpperCase()).match(searchstring.toUpperCase())))	
							tb.childNodes[x].style.display='none';
					}
				}
		}
	}else
		alert("Invalid arguments...!\n\n\nSearch function needs arguments in pair...!");	
}

/***************************************************************
* Get the index of object passed for an array control          *
****************************************************************/
function getIndex(obj){
	var x = document.getElementsByName(obj.name);
	for(var i=0;i<x.length;i++){
		if(obj==x[i])
		return i;
	}
}

function checkequal(obj1,obj2,x){
	if((obj1.value==obj2.value)==x)
		return true;
	else{
		alert("Equality for " + obj1.name + " and " + obj2.name);
		return !x;
	}
}

/************************************************************************
* Get the values object passed for an array control as javascript array *
*************************************************************************/
function getValueof(obj){
	if(obj.constructor==String) // Object passed is a string so use array control
	{
		var arr = new Array();
		var objArray=document.getElementsByName(obj);
		for(var i=0;i<objArray.length;i++){
			arr[i] = objArray[i].value;
		}
		return arr;
	}else
		alert("Can evaluate only for array control. Please Check...!");
}

/*****************************************
* Hide or Unhide used in jurisdiction    *
******************************************/
function hideunhideparentparent(thisobj, obj,x) {
	if((obj.type=='text')||(obj.type=='password')||(obj.type=='hidden')) // Object is a textbox
		obj.parentNode.parentNode.style.display=x;	
	else if(obj.constructor==String){
		var rind = thisobj.parentNode.parentNode.rowIndex;
		var totrows = thisobj.parentNode.parentNode.parentNode.getElementsByTagName('tr').length;
		if(x=='none'){
			for(var i=rind+1;i<totrows;i++){
				if(parseInt(thisobj.parentNode.parentNode.parentNode.childNodes[i].childNodes[0].childNodes[0].id)<=parseInt(thisobj.id))
					return true;
				else{
					thisobj.parentNode.parentNode.parentNode.childNodes[i].style.display='none';
					var temp =thisobj.parentNode.parentNode.parentNode.childNodes[i].childNodes[0].childNodes[0];
					temp.src=(temp.src.match('ftv2mnode.gif')|| temp.src.match('ftv2pnode.gif'))?'tms.images/ftv2pnode.gif':'tms.images/ftv2node.gif';
				}
			}
		}else{
			var objArray = document.getElementsByName(obj);
			for(var i=objArray.length-1;i>=0;i--){
				objArray[i].parentNode.parentNode.style.display='block';
			}
		}
	}else
		alert("Cannot evaluate field type at runtime. Please Check...!");	
}

function hidechilds(thisobj,obj)
{
	alert(thisobj.alt);
}

/**************************************************
 *  Checks if dateStr passed is a valid date      *
 * format can be one of MDY,mdy,mDy,DYM,YMD,...   *
 * seperators can be '-',    '/'   ,    '.'       *
 **************************************************/ 
function isValidDate(dateStrObj, format) {
	var dateStr = dateStrObj.value;
	dateStrObj.style.backgroundColor=normalColor;
	if (format == null) { format = "MDY"; }
	format = format.toUpperCase();   
	if(format=="YYYYMM"){
		if(dateStr.length<6)
			dateStrObj.style.backgroundColor=errorColor; return false;
		dateStr = dateStr.substring(0,4) + "-" + dateStr.substring(4,2) + "-01";
   		format="YMD";
   	}
	if (format.length != 3) { format = "MDY"; }
	if ( (format.indexOf("M") == -1) || (format.indexOf("D") == -1) || 
      (format.indexOf("Y") == -1) ) { format = "MDY"; }
	if (format.substring(0, 1) == "Y") { 
		// If the year is first
		var reg1 = /^\d{2}(\-|\/|\.)\d{1,2}\1\d{1,2}$/
		var reg2 = /^\d{4}(\-|\/|\.)\d{1,2}\1\d{1,2}$/
	} else if (format.substring(1, 2) == "Y") { 
		// If the year is second
		var reg1 = /^\d{1,2}(\-|\/|\.)\d{2}\1\d{1,2}$/
		var reg2 = /^\d{1,2}(\-|\/|\.)\d{4}\1\d{1,2}$/
	} else { 
		// The year must be third
		var reg1 = /^\d{1,2}(\-|\/|\.)\d{1,2}\1\d{2}$/
		var reg2 = /^\d{1,2}(\-|\/|\.)\d{1,2}\1\d{4}$/
	}
	// If it doesn't conform to the right format (with either a 2 digit year or 4 digit year), fail
	if ( (reg1.test(dateStr) == false) && (reg2.test(dateStr) == false) ) { dateStrObj.style.backgroundColor=errorColor; return false; }
	var parts = dateStr.split(RegExp.$1); // Split into 3 parts based on what the divider was
	// Check to see if the 3 parts end up making a valid date
	if (format.substring(0, 1) == "M") { var mm = parts[0]; } else 
		if (format.substring(1, 2) == "M") { var mm = parts[1]; } else { var mm = parts[2]; }
	if (format.substring(0, 1) == "D") { var dd = parts[0]; } else 
		if (format.substring(1, 2) == "D") { var dd = parts[1]; } else { var dd = parts[2]; }
	if (format.substring(0, 1) == "Y") { var yy = parts[0]; } else 
		if (format.substring(1, 2) == "Y") { var yy = parts[1]; } else { var yy = parts[2]; }
	if (parseFloat(yy) <= 50) { yy = (parseFloat(yy) + 2000).toString(); }
	if (parseFloat(yy) <= 99) { yy = (parseFloat(yy) + 1900).toString(); }
	var dt = new Date(parseFloat(yy), parseFloat(mm)-1, parseFloat(dd), 0, 0, 0, 0);
	if (parseFloat(dd) != dt.getDate()) { dateStrObj.style.backgroundColor=errorColor; return false; }
	if (parseFloat(mm)-1 != dt.getMonth()) { dateStrObj.style.backgroundColor=errorColor; return false; }
	return true;
}

/*****************************************************************
* Check that value of the HTML object is in the provided range   *
******************************************************************/
function checkRange(min,max,obj){
	var value=obj.value;
	if(min<=value && value <= max){
		obj.style.backgroundColor=normalColor;
		return true;
	}else{
		alert(obj.name + " ("+ value + ")" + " doesn't lie between Range: " + min + " and " + max);
		obj.style.backgroundColor=errorColor;
		return false;			
	}
}

/**************************************************************************
* Check that value of the HTML object is a valid time in 24 hour format   *
***************************************************************************/
function cktTime(obj){
	if(obj.value.length==0){
		return true;
	}
	
	var pattern = new RegExp("^([0-1][0-9]|[2][0-3])(:([0-5][0-9])){1,2}$");
	var value=obj.value;
	if(value.length<3 && value.length>0){
		alert("Time entered should be in hh24:mi format eg.( 22:10 )...!\nPlease check...!");
		obj.style.backgroundColor=errorColor;
		obj.focus();
		return false;		
	}else{
		var ind =value.indexOf(":") ;
		if(ind==-1)			
			value=value.substring(0,value.length-2)+":"+value.substring(value.length-2);		
		value = padd(value,'l','0',5);		
		if(!value.match(pattern)){
			alert("Time entered should be in hh24:mi format eg.( 22:10 )...!\nPlease check...!");
			obj.style.backgroundColor=errorColor;
			obj.focus();
			return false;		
		}
	}
	obj.style.backgroundColor=normalColor;
	obj.value=value;	
	return true;
}

/*****************************************************************
* Open the print dialog and hide the HTML object that called it  *
******************************************************************/
function printme(obj){
	obj.style.display="none";
	window.print();
}

Date.fromUKFormat = function(sUK) 
{   var A = sUK.split(/[\\\/]/); A = [A[1],A[0],A[2]];   return new Date(Date.parse(A.join('/'))); }

/***************************************************
* To check that date1 should be less than date2    *
****************************************************/
function compareDate(date1,date2){
	if((Date.parse(Date.fromUKFormat(date1)))<(Date.parse(Date.fromUKFormat(date2))))
		return true;
	else
		return false;	
}

function compareDateLTET(date1,date2){
	if((Date.parse(Date.fromUKFormat(date1)))<=(Date.parse(Date.fromUKFormat(date2))))
		return true;
	else
		return false;	
}

/*****************************************************************
* Check that HTML object fld has a valid date value              *
******************************************************************/
function validateDate(fld) {
    if((fld.value).length>0){
		var RegExPattern = /^((((0?[1-9]|[12]\d|3[01])[\.\-\/](0?[13578]|1[02])[\.\-\/]((1[6-9]|[2-9]\d)?\d{2}))|((0?[1-9]|[12]\d|30)[\.\-\/](0?[13456789]|1[012])[\.\-\/]((1[6-9]|[2-9]\d)?\d{2}))|((0?[1-9]|1\d|2[0-8])[\.\-\/]0?2[\.\-\/]((1[6-9]|[2-9]\d)?\d{2}))|(29[\.\-\/]0?2[\.\-\/]((1[6-9]|[2-9]\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)|00)))|(((0[1-9]|[12]\d|3[01])(0[13578]|1[02])((1[6-9]|[2-9]\d)?\d{2}))|((0[1-9]|[12]\d|30)(0[13456789]|1[012])((1[6-9]|[2-9]\d)?\d{2}))|((0[1-9]|1\d|2[0-8])02((1[6-9]|[2-9]\d)?\d{2}))|(2902((1[6-9]|[2-9]\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)|00))))$/;
	    var errorMessage = 'Please enter valid date as day, Month, and four digit year...!\nThe date must be a real date in dd/mm/yyyy format...!';
	    if ((fld.value.match(RegExPattern)) && (fld.value!='')) 
	        return true;
	    else {
	        alert(errorMessage);
	        fld.focus();
	        return false;
	    } 
	}	
}

/*****************************************************************
* Check that HTML object should have date value in MM/YYYY format *
******************************************************************/
function validateYrMn(obj){
	if((obj.value).length>0){
		var valdate=obj.value;
		var idx=valdate.indexOf("/");
		if(idx==-1){
			alert("Please enter in proper MM/YYYY format...!");
			obj.focus();
			return false;
		}
		var month=valdate.substring(0,valdate.indexOf("/"));
		var year=valdate.substring(valdate.indexOf("/")+1,valdate.length);
		if(month.length<1){
			alert("Month should be between 1 and 12 ...!");
			obj.focus();
			return false;
		}
		if(parseInt(month,10)>12){
			alert("Month can not be greater than 12 ...!");
			obj.focus();
			return false;
		}
		if(parseInt(month,10)==0){
			alert("Month can not be zero ...!");
			obj.focus();
			return false;
		}
		if(year.length<4 || year.length>4){
			alert("Year should be in YYYY format ...!");
			obj.focus();
			return false;
		}
		if(month.length<2)
			month="0"+month;		
		obj.value=month+"/"+year;
		return true;
	}
	return false;
}

// Position related functions
function f_clientWidth() {
	return f_filterResults (
		window.innerWidth ? window.innerWidth : 0,
		document.documentElement ? document.documentElement.clientWidth : 0,
		document.body ? document.body.clientWidth : 0
	);
}
function f_clientHeight() {
	return f_filterResults (
		window.innerHeight ? window.innerHeight : 0,
		document.documentElement ? document.documentElement.clientHeight : 0,
		document.body ? document.body.clientHeight : 0
	);
}
function f_scrollLeft() {
	return f_filterResults (
		window.pageXOffset ? window.pageXOffset : 0,
		document.documentElement ? document.documentElement.scrollLeft : 0,
		document.body ? document.body.scrollLeft : 0
	);
}
function f_scrollTop() {
	return f_filterResults (
		window.pageYOffset ? window.pageYOffset : 0,
		document.documentElement ? document.documentElement.scrollTop : 0,
		document.body ? document.body.scrollTop : 0
	);
}
function f_filterResults(n_win, n_docel, n_body) {
	var n_result = n_win ? n_win : 0;
	if (n_docel && (!n_result || (n_result > n_docel)))
		n_result = n_docel;
	return n_body && (!n_result || (n_result > n_body)) ? n_body : n_result;
}

/****************************************
* Add Trim to javascript String object  *
*****************************************/
String.prototype.Trim = function()
{
	return this.replace(/(^\s*)|(\s*$)/g, "");
}

/*********************************************
* Add Left trim to javascript String object  *
**********************************************/
String.prototype.LTrim = function()
{
	return this.replace(/(^\s*)/g, "");
}
 
/**********************************************
* Add Right trim to javascript String object  *
***********************************************/
String.prototype.RTrim = function()
{
	return this.replace(/(\s*$)/g, "");
}
// Ends Position Related functions


function roundNumber(num, dec) {
	var result = Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
	return result;
}

function checkDecimal(obj,s,p){
    if(chkNum(obj)){
	    var str = obj.value;
	    if(str.length>0){
		    str=str.Trim();
		    var str1 = str.substring(0,1)
		    if (str1 == '-' || str1 == '+'){
		    	str= str.substring(1,str.length);		
		    }else{
		    	str1="";
		    }
		    str=parseFloat(str)+"";
		    var teststr = str.split('.');
		    var a="";
		    var b="";
		    a=teststr[0];
		    if(teststr.length>1){	    	
		    	b=teststr[1];
		    }
			if(a.length>s){
				alert("Length of Integer part should not be greater than -> "+s);
				obj.focus();
				return false;
			}
			if(b.length>p){
				alert("Length of Decimal part should not be greater than -> "+p);
				obj.focus();
				return false;
			}
				str=a+"."+b;
			
			if(str.substring(str.length-1,str.length)=="."){
				str=str.substring(0,str.length-1);
			}
	//alert(str + "k" + str1);
		obj.value=str1+str;
		return true;
	  }
	}
    return false;
}

// kundan finished on 27/03/2010


//adds numberOfDaysToAdd of days to oldDate(DD/MM/YYYY) and returns new date in  (DD/MM/YYYY)
function addDaysToDate(oldDate, numberOfDaysToAdd)

{
	//alert('oldDate '+oldDate);
	 //alert('numberOfDaysToAdd '+numberOfDaysToAdd);
	
	var someDate = new Date();
	var dateArray = new Array();
	dateArray =  oldDate.split("/");
	
	var days = dateArray[0];
	var month = eval(dateArray[1]) - eval(1);
	var year = dateArray[2];
	
	someDate.setDate(days);
	someDate.setMonth(month);
	someDate.setYear(year);

	someDate.setDate(someDate.getDate() + eval(numberOfDaysToAdd));  
/// Formatting to dd/mm/yyyy :
	var dd = someDate.getDate(); 
	var mm = someDate.getMonth() + 1; 
	var y = someDate.getFullYear(); 
	var someFormattedDate = dd + '/'+ mm + '/'+ y; 
	//alert(someFormattedDate);
	
	return someFormattedDate;
}

// function to delete row in table
function deleteRow(thisObj, baseRowObj){
	//alert(' thisObj '+ thisObj);
	var baseRowLength =  baseRowObj.childNodes.length;
	var index = getIndex(thisObj);
	
	//alert(' index '+ index);
	
	if(baseRowLength > 1){
		if(confirm('Are you sure you want to delete?')){
			baseRowObj.removeChild(baseRowObj.childNodes[index]);
		}
	}	
	//finally reArrange the srno of row after deletion	
	reArrangeSrNo(index, baseRowObj);
}
function reArrangeSrNo(index, baseRowObj){
	var baseRowLength =  baseRowObj.childNodes.length;
	var outObjName = baseRowObj.childNodes[0].childNodes[0].childNodes[0].name;
	for(var i = index; i<baseRowLength; i++){
		window.document.getElementsByName(outObjName)[i].value = i;
	}
}

// set max length for textarea
function MaxLength(Object, MaxLen)
{
  return (Object.value.length <= MaxLen);
}

