// Init section
   zakroma = "";
   obj_float_div = false;
   active_img_mark = null;
   show_delay = null;
   preloads = new Object;
   gags = new Array;
 
   window.onerror = function() { return true; }
   window.onload = function(e) { if (document.getElementById && document.createElement) tooltip.define(); }

function run_after_body() {
   document.write('<textarea id="gate_to_clipboard" style="display:none;"></textarea>');
   document.onmousemove = document_onmousemove;
   if (window.onscroll) window.onscroll = hide_div();
   document.write('<div class="float" id="div_200" style="left: -3000px; background: #ffffff;"><img id="img_200" class="border_b" border="5"></div>');
   setInterval("changer();",333);
   for (var i = 4; i <= 4+7; i += 1)  {
      var rnd_200 = i; rnd_200 = (rnd_200 < 10) ? '0' + rnd_200 : rnd_200;
      preloads['loader_'+i] = new Image;
      //preloads['loader_'+i].src = zakroma + "/main/200x150/" + rnd_200 + ".gif";
      preloads['loader_'+i].src = "./images/lloading.gif";
      //preloads['loader_'+i].src = "http://localhost:8080/VotingCentral/images/vc-logo-beta.gif";
      preloads['loader_'+i].width = 200;
      preloads['loader_'+i].height = 150;
   }
}

function get_obj(id_name) {
   if (document.getElementById) {
      return document.getElementById(id_name);
   } else if (document.all) {
      return document.all[id_name];
   } else {
      return null;
   }
}

function document_onmousemove(e) {

   if ( !obj_float_div ) return;

   var pos_X = 0, pos_Y = 0;
   if ( !e ) e = window.event;
   if ( e ) {
      if ( typeof(e.pageX) == 'number' ) {
         pos_X = e.pageX; pos_Y = e.pageY;
      } else if ( typeof(e.clientX) == 'number' ) {
         pos_X = e.clientX; pos_Y = e.clientY;
         if ( document.body && ( document.body.scrollTop || document.body.scrollLeft ) && !( window.opera || window.debug || navigator.vendor == 'KDE' ) ) {
            pos_X += document.body.scrollLeft; pos_Y += document.body.scrollTop;
         } else if ( document.documentElement && ( document.documentElement.scrollTop || document.documentElement.scrollLeft ) && !( window.opera || window.debug || navigator.vendor == 'KDE' ) ) {
            pos_X += document.documentElement.scrollLeft; pos_Y += document.documentElement.scrollTop;
         }
      }
   }
 
   var scroll_X = 0, scroll_Y = 0;
   if ( document.body && ( document.body.scrollTop || document.body.scrollLeft ) && !( window.debug || navigator.vendor == 'KDE' ) ) {
      scroll_X = document.body.scrollLeft; scroll_Y = document.body.scrollTop;
   } else if ( document.documentElement && ( document.documentElement.scrollTop || document.documentElement.scrollLeft ) && !( window.debug || navigator.vendor == 'KDE' ) ) {
      scroll_X = document.documentElement.scrollLeft; scroll_Y = document.documentElement.scrollTop;
   }
 
   var win_size_X = 0, win_size_Y = 0;
   if (window.innerWidth && window.innerHeight) {
      win_size_X = window.innerWidth; win_size_Y = window.innerHeight;
   } else if (document.documentElement && document.documentElement.clientWidth && document.documentElement.clientHeight) {
      win_size_X = document.documentElement.clientWidth; win_size_Y = document.documentElement.clientHeight;
   } else if (document.body && document.body.clientWidth && document.body.clientHeight) {
      win_size_X = document.body.clientWidth; win_size_Y = document.body.clientHeight;
   }
 
   pos_X += 15; pos_Y += 15;
 
   if (obj_float_div.offsetWidth && obj_float_div.offsetHeight) {
      if (pos_X - scroll_X + obj_float_div.offsetWidth + 5 > win_size_X) pos_X -= (obj_float_div.offsetWidth + 25);
      if (pos_Y - scroll_Y + obj_float_div.offsetHeight + 5 > win_size_Y) pos_Y -= (obj_float_div.offsetHeight + 20);
   }

   obj_float_div.style.left = pos_X + "px"; obj_float_div.style.top = pos_Y + "px";
 
}

function show_200(img_src,wp_id,img_w,img_h) {
   if (show_delay) {
      clearTimeout(show_delay); show_delay = null;
   } else {
      obj_float_div = get_obj('div_200');
      show_delay = setTimeout('show_200("' + img_src + '","' + wp_id + '",' + img_w + ',' + img_h+ ');', 400);
      return;
   }
   var img_mark = 'img_wp_id_' + wp_id;
   active_img_mark = img_mark;
   if (preloads[img_mark] && preloads[img_mark].complete) {
      swap_img(img_mark);
   } else {
      var rnd_200 = Math.round(Math.random()*4) + 7;
      swap_img('loader_' + rnd_200);
   }
   if ( ! preloads[img_mark] ) {
      preloads[img_mark] = new Image;
      preloads[img_mark].src = zakroma + img_src;
      //preloads[img_mark].src ="img/lloading.gif";
      //preloads[img_mark].width = img_w;
      //preloads[img_mark].height = img_h;
      preloads[img_mark].onerror = function() { gag(preloads[img_mark],200); }
   }
   show_div('div_200');
}

function changer() {
   if ( !obj_float_div || !preloads[active_img_mark] || !get_obj('img_200')) return;
   if ( get_obj('img_200').src != preloads[active_img_mark].src && preloads[active_img_mark].complete ) swap_img(active_img_mark);
}

function swap_img(img_mark) {
   var obj_base_img = get_obj('img_200');
   if (!obj_base_img) return;
   obj_base_img.src    = preloads[img_mark].src;
   obj_base_img.width  = preloads[img_mark].width;
   obj_base_img.height = preloads[img_mark].height;
}

function show_div(div_mark) {
   if (show_delay) {
      clearTimeout(show_delay); show_delay = null;
   } else {
      obj_float_div = get_obj(div_mark);
      show_delay = setTimeout('show_div("' + div_mark + '");',0);
      return;
   }
   if ( ! obj_float_div ) return;
   if (obj_float_div.offsetWidth) {
      obj_float_div.style.width = "auto";
      obj_float_div.style.height = "auto";
      if (obj_float_div.offsetWidth > 300) obj_float_div.style.width = "300px";
   }
   document_onmousemove;
   obj_float_div.style.visibility = 'visible';
}

function hide_div() {
   //var rnd_200 = Math.round(Math.random()*4) + 7; swap_img('loader_' + rnd_200);
   clearTimeout(show_delay); show_delay = null;
   if ( ! obj_float_div ) return;
   obj_float_div.style.visibility = 'hidden';
   obj_float_div.style.left = "-3000px";
   obj_float_div = false;
}

function append_to_div(div_mark,doc) {
   var obj_box = get_obj(div_mark);
   if ( obj_box && typeof(obj_box.innerHTML) == 'string' ) obj_box.innerHTML += doc;
}

function gag(object,type) {
   if (gags[object.src]) return;
   if (type == 100) {
      object.src = zakroma + "/main/200x150/gag_100.gif";
      //object.src = "img/lloading.gif";
      object.height = 75; // для Opera
      object.style.height = "75px"; // для Gecko, IE
   } else {
      object.src = zakroma + "/main/200x150/gag_200.gif";
      //object.src = "img/lloading.gif";
      object.height = 150;
      object.style.height = "150px";
   }
   object.style.display = "block"; // для Gecko
   gags[object.src] = true;
}