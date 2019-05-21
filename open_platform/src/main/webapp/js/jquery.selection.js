(function($){
	/**
	 * jquery扩展，用来获取文本框和文本域选中的光标开始位置、结束位置和选中的内容
	 * 例如：开始位置：$("#description").selection().start
	 * 	   结束位置：$("#description").selection().end
	 *     选中内容：$("#description").selection().text
	 * 未选择则start表示光标位置
	 */
    $.fn.selection = function(){
        var s,e,range,stored_range;
        if(this[0].selectionStart == undefined){
            var selection=document.selection;
            if (this[0].tagName.toLowerCase() != "textarea") {
                var val = this.val();
                range = selection.createRange().duplicate();
                range.moveEnd("character", val.length);
                s = (range.text == "" ? val.length:val.lastIndexOf(range.text));
                range = selection.createRange().duplicate();
                range.moveStart("character", -val.length);
                e = range.text.length;
            }else {
                range = selection.createRange(),
                stored_range = range.duplicate();
                stored_range.moveToElementText(this[0]);
                stored_range.setEndPoint('EndToEnd', range);
                s = stored_range.text.length - range.text.length;
                e = s + range.text.length;
            }
        }else{
            s=this[0].selectionStart,
            e=this[0].selectionEnd;
        }
        var te=this[0].value.substring(s,e);
        return {start:s,end:e,text:te}
    };

    /**
     * jquery扩展，获取文本框的光标位置
     * 例如：$("#description").position();
     */
	$.fn.extend({
	    position1:function( value ){
	        var elem = this[0];
	        if (elem&&(elem.tagName=="TEXTAREA"||elem.type.toLowerCase()=="text" || elem.type.toLowerCase()=="password")) {
	           if($.browser.msie){
	                   var rng;
	                   if(elem.tagName == "TEXTAREA"){ 
	                        rng = event.srcElement.createTextRange();
	                        rng.moveToPoint(event.x,event.y);
	                   }else{ 
	                        rng = document.selection.createRange();
	                   }
	                   if( value === undefined ){
	                     rng.moveStart("character",-event.srcElement.value.length);
	                     return  rng.text.length;
	                   }else if(typeof value === "number" ){
	                     var index=this.position();
	                     index>value?( rng.moveEnd("character",value-index)):(rng.moveStart("character",value-index))
	                     rng.select();
	                   }
	            }else{
	                if( value === undefined ){
	                	return elem.selectionStart;
	                }else if(typeof value === "number" ){
	                	elem.selectionEnd = value;
	                	elem.selectionStart = value;
	                }
	            }
	        }else{
	            if( value === undefined )
	               return undefined;
	        }
	    }
	});
	$.fn.setCursorPosition = function(position){
	    if(this.lengh == 0) return this;
	    return $(this).setSelection(position, position);
	};

	$.fn.setSelection = function(selectionStart, selectionEnd) {
	    if(this.lengh == 0) return this;
	    input = this[0];

	    if (input.createTextRange) {
	        var range = input.createTextRange();
	        range.collapse(true);
	        range.moveEnd('character', selectionEnd);
	        range.moveStart('character', selectionStart);
	        range.select();
	    } else if (input.setSelectionRange) {
	        input.focus();
	        input.setSelectionRange(selectionStart, selectionEnd);
	    }

	    return this;
	};

	$.fn.focusEnd = function(){
	    this.setCursorPosition(this.val().length);
	};
})(jQuery);

