function toggle_lines(line1, line2, action)
{
    oL1 = document.getElementById(line1);
    oL2 = document.getElementById(line2);

    oL1.style.visibility = action;
    oL2.style.visibility = action;
}

var photos = new Array();

photos[0] = "/images/cont_photo.jpg";
photos[1] = "/images/cont_photo_off.jpg";
photos[2] = "/images/cont_photo_x.jpg";
photos[3] = "/images/cont_photo_y.jpg";

var newPhoto = 0;
var totalPhotos = photos.length;

function homepage_rotation()
{
	newPhoto++;
	if (newPhoto == totalPhotos) 
	{
		newPhoto = 0;
	}
	
	document.getElementById('homepage_image').src = photos[newPhoto];
	setTimeout("homepage_rotation()", .5*5000);
}

/*
 * label2value
 * jquery based script for using form labels as text field values
 * more info on http://cssglobe.com/post/1500/using-labels- 
 *
 * Copyright (c) 2008 Alen Grakalic (cssglobe.com)
 * Dual licensed under the MIT (MIT-LICENSE.txt)
 * and GPL (GPL-LICENSE.txt) licenses.
 *
 */

this.label2value = function(){	

	// CSS class names
	// put any class name you want
	// define this in external css (example provided)
	var inactive = "inactive";
	var active = "active";
	var focused = "focused";
	
	// function
	$("label").each(function(){		
		obj = document.getElementById($(this).attr("for"));
		if(($(obj).attr("type") == "text") || (obj.tagName.toLowerCase() == "textarea")){			
			$(obj).addClass(inactive);			
			var text = $(this).text();
			$(this).css("display","none");			
			$(obj).val(text);
			$(obj).focus(function(){	
				$(this).addClass(focused);
				$(this).removeClass(inactive);
				$(this).removeClass(active);								  
				if($(this).val() == text) $(this).val("");
			});	
			$(obj).blur(function(){	
				$(this).removeClass(focused);													 
				if($(this).val() == "") {
					$(this).val(text);
					$(this).addClass(inactive);
				} else {
					$(this).addClass(active);		
				};				
			});				
		};	
	});		
};

