/* Opens a popup window and centers it.
 * Pass 1 for true and 0 for false for the features.
 */
var popwin;
function openPopup(url, name, w, h, sbar, tbar, mbar, resize, status)
{ 
	l = (screen.availWidth - w) / 2; 
	t = (screen.availHeight - h) / 2; 
	
	var features = "width="+w+",height="+h+",left="+l+",top="+t; 
	features += ",screenX="+l+",screenY="+t;
	features += ",scrollbars="+sbar+",resizable="+resize;	
	features += ",menubar="+mbar+",toobar="+tbar+",status="+status;	
	
	if(!popwin || popwin.closed)
	{
		popwin = window.open(url, name, features);
	}
	else
	{
		popwin.close();
		popwin = window.open(url, name, features);
	}
}