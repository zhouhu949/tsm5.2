function SelCity(obj,e,fn) {
    var ths = obj;
    var dal = '<div class="_citys"><span title="确认" id="cClick" >确认</span><span title="清空" id="cClear" >清空</span><span title="关闭" id="cColse" >×</span><ul id="_citysheng" class="_citys0"><li class="citySel">省份</li><li>地市</li><li>区县</li></ul><div id="_citys0" class="_citys1"></div><div style="display:none" id="_citys1" class="_citys1"></div><div style="display:none" id="_citys2" class="_citys1"></div></div>';
    Iput.show({ id: ths, event: e, content: dal,width:"470"});
    $("#cColse,#cClick").click(function () {
        Iput.colse();
    });
    $("#cClear").click(function () {
    	if (document.getElementById("hproper") != null) {
    		$("#hproper").remove();
    	}
	    if (document.getElementById("hcity") != null) {
	    	$("#hcity").remove();
		}
		if (document.getElementById("harea") != null) {
			$("#harea").remove();	
    	}
		fn("");
        Iput.colse();
    });
    var tb_province = [];
    $.ajax({
    	url:ctx+"/common/area/province",
    	success:function(data){
    		var b = data;
    		 for (var i = 0, len = b.length; i < len; i++) {
    		        tb_province.push('<a data-level="0" data-id="' + b[i]['pid'] + '" data-name="' + b[i]['pname'] + '" title="'+b[i]['pname']+'">' + b[i]['pname'] + '</a>');
    		    }
    		    $("#_citys0").append(tb_province.join(""));
    		    $("#_citys0 a").click(function (e) {
    		         getCity($(this),ths,fn);
    		         e.stopPropagation();
                });
    		    $("#_citysheng li").click(function (e) {
    		    	e.stopPropagation();
    		        $("#_citysheng li").removeClass("citySel");
    		        $(this).addClass("citySel");
    		        var s = $("#_citysheng li").index(this);
    		        $("._citys1").hide();
    		        $("._citys1:eq(" + s + ")").show();
    		    });
    	}
    })
    //var b = province;
   
}

function getCity(obj,ths,fn) {
    var c = obj.data('id');
    var g = '';
    $.ajax({
    	url:ctx+"/common/area/city",
    	data:{
    		pid:c
    	},
    	success:function(data){
    		var f=data;
    	    for (var j = 0, clen = f.length; j < clen; j++) {
    	        g += '<a data-level="1" data-id="' + f[j]['cid'] + '" data-name="' + f[j]['cname'] + '" title="' + f[j]['cname'] + '">' + f[j]['cname'] + '</a>'
    	    }
    	    
    	    $("#_citysheng li").removeClass("citySel");
    	    $("#_citysheng li:eq(1)").addClass("citySel");
    	    $("#_citys1 a").remove();
	        $("#_citys1").append(g);
	        $("._citys1").hide();
	        $("._citys1:eq(1)").show();
	        $("#_citys0 a,#_citys1 a,#_citys2 a").removeClass("AreaS");
	        $(this).addClass("AreaS");
	        var lev = obj.data("name");
	        fn(obj.data("name"))
	        //ths.value = obj.data("name");
	        if (document.getElementById("hcity") == null) {
	            var hcitys = $('<input>', {
	                type: 'hidden',
	                name: "provinceId",
	                "data-id": obj.data("id"),
	                "data-name":obj.data("name"),
	                id: "hcity",
	                val: obj.data("id")
	            });
	            $(ths).after(hcitys);
	        }
	        else {
	            $("#hcity").val(obj.data("id"));
	            $("#hcity").data("id", obj.data("id"));
	            $("#hcity").data("name", obj.data("name"));
	        }
	        $("#hproper").val("");
	        $("#harea").val("");
	        $("#_citys1 a").click(function (e) {
                e.stopPropagation();
	            $("#_citys1 a,#_citys2 a").removeClass("AreaS");
	            $(this).addClass("AreaS");
	            var lev =  $(this).data("name");
	            if (document.getElementById("hproper") == null) {
	                var hcitys = $('<input>', {
	                    type: 'hidden',
	                    name: "cityId",
	                    "data-id": $(this).data("id"),
	                    "data-name":$(this).data("name"),
	                    id: "hproper",
	                    val: $(this).data("id")
	                });
	                $(ths).after(hcitys);
	            }
	            else {
	                $("#hproper").data("id", $(this).data("id"));
	                $("#hproper").data("name", $(this).data("name"));
	                $("#hproper").val($(this).data("id"));
	            }
	            var bc = $("#hcity").data("name");
	            //ths.value = bc+ "-" + $(this).data("name");
	            fn(bc+ "-" + $(this).data("name"))
	            getArea($(this),ths,fn);
	        });
    	}
    })
}
function getArea(obj,ths,fn) {
    var c = obj.data('id');
    var ar = "";
    $.ajax({
    	url:ctx+"/common/area/county",
    	data:{
    		cid:c
    	},
    	success:function(data){
    		var f = data;
    		console.log(data)
    		 for (var j = 0, clen = f.length; j < clen; j++) {
		        ar += '<a data-level="1" data-id="' + f[j]['oid'] + '" data-name="' + f[j]['oname'] + '" title="' + f[j]['oname'] + '">' + f[j]['oname'] + '</a>'
		    }
    		 $("#_citysheng li").removeClass("citySel");
		    $("#_citysheng li:eq(2)").addClass("citySel");
		    
		    $("#_citys2 a").remove();
            $("#_citys2").append(ar);
            $("._citys1").hide();
            $("._citys1:eq(2)").show();

            $("#_citys2 a").click(function (e) {
            	e.stopPropagation();
                $("#_citys2 a").removeClass("AreaS");
                $(this).addClass("AreaS");
                var lev = $(this).data("name");
                if (document.getElementById("harea") == null) {
                    var hcitys = $('<input>', {
                        type: 'hidden',
                        name: "countyId",
                        "data-id": $(this).data("id"),
                        "data-name":$(this).data("name"),
                        id: "harea",
                        val: $(this).data("id")
                    });
                    $(ths).after(hcitys);
                }
                else {
                    $("#harea").val($(this).data("id"));
                    $("#harea").data("id", $(this).data("id"));
                    $("#harea").data("name", $(this).data("name"));
                }
                var bc = $("#hcity").data("name");
                var bp = $("#hproper").data("name");
                //ths.value = bc + "-" + bp + "-" + $(this).data("name");
                fn(bc + "-" + bp + "-" + $(this).data("name"))
                Iput.colse();
            });
    	}
    })
}
