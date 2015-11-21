define(function () { return function (__fest_context){"use strict";var __fest_self=this,__fest_buf="",__fest_chunks=[],__fest_chunk,__fest_attrs=[],__fest_select,__fest_if,__fest_iterator,__fest_to,__fest_fn,__fest_html="",__fest_blocks={},__fest_params,__fest_element,__fest_debug_file="",__fest_debug_line="",__fest_debug_block="",__fest_htmlchars=/[&<>"]/g,__fest_htmlchars_test=/[&<>"]/,__fest_short_tags = {"area":true,"base":true,"br":true,"col":true,"command":true,"embed":true,"hr":true,"img":true,"input":true,"keygen":true,"link":true,"meta":true,"param":true,"source":true,"wbr":true},__fest_element_stack = [],__fest_htmlhash={"&":"&amp;","<":"&lt;",">":"&gt;","\"":"&quot;"},__fest_jschars=/[\\'"\/\n\r\t\b\f<>]/g,__fest_jschars_test=/[\\'"\/\n\r\t\b\f<>]/,__fest_jshash={"\"":"\\\"","\\":"\\\\","/":"\\/","\n":"\\n","\r":"\\r","\t":"\\t","\b":"\\b","\f":"\\f","'":"\\'","<":"\\u003C",">":"\\u003E"},___fest_log_error;if(typeof __fest_error === "undefined"){___fest_log_error = (typeof console !== "undefined" && console.error) ? function(){return Function.prototype.apply.call(console.error, console, arguments)} : function(){};}else{___fest_log_error=__fest_error};function __fest_log_error(msg){___fest_log_error(msg+"\nin block \""+__fest_debug_block+"\" at line: "+__fest_debug_line+"\nfile: "+__fest_debug_file)}function __fest_replaceHTML(chr){return __fest_htmlhash[chr]}function __fest_replaceJS(chr){return __fest_jshash[chr]}function __fest_extend(dest, src){for(var i in src)if(src.hasOwnProperty(i))dest[i]=src[i];}function __fest_param(fn){fn.param=true;return fn}function __fest_call(fn, params,cp){if(cp)for(var i in params)if(typeof params[i]=="function"&&params[i].param)params[i]=params[i]();return fn.call(__fest_self,params)}function __fest_escapeJS(s){if (typeof s==="string") {if (__fest_jschars_test.test(s))return s.replace(__fest_jschars,__fest_replaceJS);} else if (typeof s==="undefined")return "";return s;}function __fest_escapeHTML(s){if (typeof s==="string") {if (__fest_htmlchars_test.test(s))return s.replace(__fest_htmlchars,__fest_replaceHTML);} else if (typeof s==="undefined")return "";return s;}var data=__fest_context;__fest_blocks.LoggedBar=function(params){var __fest_buf="";__fest_buf+=("<div class=\"logout\"><div class=\"logout__info\">Вы вошли, как: <span>");try{__fest_buf+=(__fest_escapeHTML(data.username))}catch(e){__fest_log_error(e.message + "4");}__fest_buf+=("</span></div>; <div class=\"logout__info\">Ваш счет : <span>");try{__fest_buf+=(__fest_escapeHTML(data.score))}catch(e){__fest_log_error(e.message + "7");}__fest_buf+=("</span></div><br/><button type=\"button\" class=\"button button_long smallMargTop logout__button\" data-href=\"#logout\">Logout</button></div>");return __fest_buf;};__fest_buf+=("<div class=\"menu-container\"><div class=\"header\"><div class=\"logo\"><div class=\"logo__top\"><div class=\"logo__top__line\"></div></div><div class=\"logo__center\"><span class=\"logo__beam\">Beam</span><span class=\"logo__divider\"></span><span class=\"logo__balls\">Balls</span></div><div class=\"logo__bottom\"><div class=\"logo__bottom__line\"></div></div></div>");try{__fest_if=data.logged}catch(e){__fest_if=false;__fest_log_error(e.message);}if(__fest_if){__fest_select="LoggedBar";__fest_params={};__fest_chunks.push(__fest_buf,{name:__fest_select,params:__fest_params,cp:false});__fest_buf="";}else{__fest_buf+=("<div class=\"dontLogged\">Вы не авторизованы</div>");}__fest_buf+=("</div><div class=\"line line_blue mediumMargTop\"></div><div class=\"menu\"><div class=\"container-game-btn\">");try{__fest_attrs[0]=__fest_escapeHTML(data.logged ? '' : 'container-game-btn__button_disabled')}catch(e){__fest_attrs[0]=""; __fest_log_error(e.message);}__fest_buf+=("<button class=\"button container-game-btn__button container-game-btn__button_game\n                " + __fest_attrs[0] + "\" data-href=\"#rooms\">Game</button></div><div class=\"container-game-btn\"><button class=\"button container-game-btn__button container-game-btn__button_scoreboard\" data-href=\"#scoreboard\">Scoreboard</button></div><div class=\"container-game-btn\">");try{__fest_if=data.logged}catch(e){__fest_if=false;__fest_log_error(e.message);}if(__fest_if){__fest_buf+=("<button class=\"button container-game-btn__button container-game-btn__button_log\" data-href=\"#logout\">Logout</button>");}else{__fest_buf+=("<button class=\"button container-game-btn__button container-game-btn__button_log\" data-href=\"#login\">Login</button>");}__fest_buf+=("</div><div class=\"menu__msg-game\"></div></div><div class=\"line line_blue\"></div></div>");__fest_to=__fest_chunks.length;if (__fest_to) {__fest_iterator = 0;for (;__fest_iterator<__fest_to;__fest_iterator++) {__fest_chunk=__fest_chunks[__fest_iterator];if (typeof __fest_chunk==="string") {__fest_html+=__fest_chunk;} else {__fest_fn=__fest_blocks[__fest_chunk.name];if (__fest_fn) __fest_html+=__fest_call(__fest_fn,__fest_chunk.params,__fest_chunk.cp);}}return __fest_html+__fest_buf;} else {return __fest_buf;}} ; });