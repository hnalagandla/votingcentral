/*
Simple Image Trail script- By JavaScriptKit.com
Visit http://www.javascriptkit.com for this script and more
This notice must stay intact
*/

var ua = navigator.userAgent.toLowerCase();
var divw=0;
var divh=0;

if (document.getElementById || document.all)
	document.write('<div id="imgtrailer" style="position:absolute;visibility:hidden;"></div>')

function gettrailobject()
	{
	if (document.getElementById)
		return document.getElementById("imgtrailer")
	else if (document.all)
		return document.all.trailimagid
	}

function gettrailobj()
	{
	if (document.getElementById)
		return document.getElementById("imgtrailer").style
	else if (document.all)
		return document.all.trailimagid.style
	}

function truebody()
	{
	return (!window.opera && document.compatMode && document.compatMode!="BackCompat")? document.documentElement : document.body
	}

function trailOff()
	{
	document.onmousemove='';
	gettrailobj().visibility="hidden";
	}

function trailOn(thumbimg,imgtitle,username,imgid,imgsize,credit,level,thw,thh)
	{
	if(ua.indexOf('opera') == -1 && ua.indexOf('safari') == -1)
		{
		gettrailobj().left="-500px";
		divthw = parseInt(thw) + 2;
		//gettrailobject().innerHTML = '<div style="background-color: #b2a99d; layer-background-color: #b2a99d; border: 1px none #808080; padding:10px; width:'+divthw+'px;"><div style="background-color: #FFFFFF; layer-background-color: #FFFFFF; border: 1px solid #808080;background-image: url(./img/lloading.gif);"><img src="'+thumbimg+'" border="0" width="'+thw+'" height="'+thh+'"><div style="padding:3px">'+imgtitle+'<br>&copy; '+username+' | Dreamstime.com<br></div></div><div class=h2 style="padding:1px;">ID: '+imgid+' / Level: '+level+'<br>'+imgsize+'</div></div>';
		gettrailobject().innerHTML = '<div style="background-color: #b2a99d; layer-background-color: #b2a99d; border: 1px none #808080; padding:10px; width:'+divthw+'px;"><div style="background-color: #FFFFFF; layer-background-color: #FFFFFF; border: 1px solid #808080;background-image: url(./VotingCentral/scripts/img/lloading.gif);"><img src="'+thumbimg+'" border="0"><div style="padding:3px">'+imgtitle+'</div></div>';
		gettrailobj().visibility="visible";
		divw = parseInt(thw)+25;
		divh = parseInt(thh)+130;
		document.onmousemove=followmouse;
		}
	}

function followmouse(e)
	{
	var docwidth=document.all? truebody().scrollLeft+truebody().clientWidth : pageXOffset+window.innerWidth-15
	var docheight=document.all? Math.min(truebody().scrollHeight, truebody().clientHeight) : Math.min(document.body.offsetHeight, window.innerHeight)
if(typeof e != "undefined")
	{
	if(docwidth < 15+e.pageX+divw)
		xcoord = e.pageX-divw-5;
	else
		xcoord = 15+e.pageX;
	if(docheight < 15+e.pageY+divh)
		ycoord = 15+e.pageY-Math.max(0,(divh + e.pageY - docheight - truebody().scrollTop - 30));
	else
		ycoord = 15+e.pageY;
	}
else if (typeof window.event != "undefined")
	{
	if(docwidth < 15+truebody().scrollLeft+event.clientX+divw)
		xcoord = truebody().scrollLeft-5+event.clientX-divw;
	else
		xcoord = truebody().scrollLeft+15+event.clientX;

	if(docheight < 15+truebody().scrollTop+event.clientY+divh)
		ycoord = 15+truebody().scrollTop+event.clientY-Math.max(0,(divh + event.clientY - docheight - 30));
	else
		ycoord = truebody().scrollTop+15+event.clientY;
	}
	gettrailobj().left=xcoord+"px"
	gettrailobj().top=ycoord+"px"
	}
