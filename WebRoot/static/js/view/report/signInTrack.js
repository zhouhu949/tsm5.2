$(function() {
	// 日期初始化今天
	$("#data_02").val(moment().format('YYYY-MM-DD'));
	$("#start").val(moment().format('YYYY-MM-DD'));

	$("#tt1").tree({
				url : ctx + '/orgGroup/get_group_user_json',
				checkbox : true,
				onLoadSuccess : function(node, data) {
					var ids = "";
					var roots = $("#tt1").tree("getRoot");
					var childs = $("#tt1").tree("getChildren", roots);
					for (var i = 0; i < childs.length; i++) {
						var item = childs[i];
						ids += item.id + ",";
					}
					if (ids != '') {
						ids = ids.substring(0, ids.length - 1);
					}
					$("#userAcc").val(ids);
				},
				onCheck : function(node, isCheck) {
					var nodes = $('#tt1').tree('getChecked');
					if (nodes.length > 0) {
						$("input[name=seeWhat]").iCheck('uncheck');
					} else if ($("input[name=seeWhat]:checked").length == 0) {
						$("input[name=seeWhat]:eq(0)").iCheck('check');
					}
				}
			});

	$("input[type='radio']").iCheck({
				radioClass : 'iradio_minimal'
			});
	$("input[type='radio']").on('ifClicked', function() {
				var $this = $(this);
				var nodes = $('#tt1').tree('getChecked');
				if (nodes.length > 0) {
					$.each(nodes, function(index, obj) {
								$('#tt1').tree("uncheck", obj.target);
							});
				}
				var ids = ""
				if ($this.hasClass("all")) {
					var roots = $("#tt1").tree("getRoot");
					var childs = $("#tt1").tree("getChildren", roots);
					for (var i = 0; i < childs.length; i++) {
						var item = childs[i];
						ids += item.id + ",";
					}
					if (ids != '') {
						ids = ids.substring(0, ids.length - 1);
					}
					$("#userAcc").val(ids);
				} else {
					var currAcc = $("#curr_user_account").val();
					$("#userAcc").val(currAcc);
				}
			});
	$(".manage-drop").click(function(e) {
				e.stopPropagation();
			})
	$(document).click(function(e) {
				e.stopPropagation();
				$(".manage-drop").hide();
			});

	$("#today").click(function() {
				// var startDate = moment().day("Sunday").format('YYYY-MM-DD');
				var endDate = moment().format('YYYY-MM-DD');
				// $("#data_01").val(startDate);
				$("#data_02").val(endDate);
				$("#start").val(endDate);
				$(".silent-week,.silent-month").removeClass("color_grey");
				$(this).addClass("color_grey")
				$("#searchBtn").click();
			});

	$("#yesterday").click(function() {
				// var startDate =
				// moment().startOf('month').format('YYYY-MM-DD');
				var endDate = moment().subtract(1, 'days').format('YYYY-MM-DD');
				// $("#data_01").val(startDate);
				$("#data_02").val(endDate);
				$("#start").val(endDate);
				$(".silent-week,.silent-month").removeClass("color_grey");
				$(this).addClass("color_grey")
				$("#searchBtn").click();
			});

	$("#tree-true-btn").click(function(e) {
				e.stopPropagation();
				var ids = '';
				var names = '';
				$($("#tt1").tree('getChecked', 'checked')).each(
						function(index, node) {
							ids += node.id + ",";
							names += node.text + ",";
						});

				if (ids != '') {
					ids = ids.substring(0, ids.length - 1);
					names = names.substring(0, names.length - 1);
					$("#userAcc").val(ids);
					$("#groupNameStr").val(names == '' ? '-请选择-' : names);
				} else {
					var radios = $("input[name=seeWhat]:checked");
					if (radios.hasClass("all")) {
						names = "查看全部";
					} else {
						names = "查看自己";
					}
					$("#groupNameStr").val(names);
				}

				$(".manage-drop").hide();
			})
	$("#tree-cancle-btn").click(function(e) {
				e.stopPropagation();
				var nodes = $('#tt1').tree('getChecked', 'checked');
				$.each(nodes, function(index, obj) {
							$('#tt1').tree("uncheck", obj.target);
						});
			})

	$("#searchBtn").click(function(e) {
				e.stopPropagation();
				doQuery()
				getMap()
			})
	$("body").delegate(".img-details", "click", function(e) {
		e.stopPropagation();
		var b = new Base64();
		var params = b.encode($(this).data("url"));
		pubDivShowIframe("look_at_img", ctx
						+ "/report/workSign/imgView?params=" + params, "查看图片",
				700, 500)
	})
	$("body").delegate(".play-record", "click", function(e) {
		e.stopPropagation();
		var $this = $(this);
		var plength = $this.data("lens").toString();
		var url = $this.data("url");
		var code = $this.data("code");
		signLogPlayRecords(url,code , plength, "1");
		})
	$("#searchBtn").click();
});

function timeChange() {
	$("#start").val($(this).val());
}

function mapInit(result) {

	var markerArr = mapData(result.list);
	initMap(markerArr);// 创建和初始化地图
}
function doQuery() {
	$.ajax({
				url : ctx + "/report/workSign/listData",
				data : $("#signForm").serialize(),
				success : function(data) {
					renderTable(data)
				}
			})
}

function mapData(list) {
	var isState = $("#isState").val();
	var isOpen = 0;
	var icon = {
		w : 28,
		h : 41,
		l : 0,
		t : 0
	};
	var markerArr = [];
	for (var i = 0, len = list.length; i < len; i++) {
		var json = {};
		var point = {};
		if (isState == 1) {
			json.title = list[i].name;
		} else {
			if (list[i].name) {
				json.title = list[i].name;
			} else if (list[i].toucherName) {
				json.title = list[i].toucherName;
			}else{
				json.title = "";
			}
		}
		json.content = list[i].userName + "," + list[i].signDate;
		point.lat = list[i].lat;
		point.lon = list[i].lon;
		json.point = point;
		json.icon = icon;
		json.isOpen = isOpen;
		markerArr.push(json);
	}
	return markerArr;
}
function getMap() {
	$.ajax({
				url : ctx + "/report/workSign/mapData",
				data : $("#signForm").serialize(),
				success : function(result) {
					if (result.list.length > 0) {
						var mapMaker = mapData(result.list)
						initMap(mapMaker)
					} else {
						initMap();
					}
				}
			})
}
function renderTable(data) {
	if (data.list.length == 0) {
		var myTemplate = Handlebars.compile($("#templateNoData").html());
		$(".sign-in-track-foot-table-box>table>tbody").html(myTemplate(data));
		var column = $(".sign-in-track-foot-table-box>table").find("thead")
				.find("th").length;
		$(".no_data_tr>td").attr("colspan", column);
	} else {
		var myTemplate = Handlebars.compile($("#template").html());
		$(".sign-in-track-foot-table-box>table>tbody").html(myTemplate(data));
	}
}
// 创建和初始化地图函数：
function initMap(markerArr) {
	if (markerArr) {
		var lon = markerArr[0].point.lon;
		var lat = markerArr[0].point.lat;
		createMap(lon, lat);// 创建地图
		addMarkers(markerArr)
	} else {
		createMap(120.1760906621, 30.2513888164);
	}
	setMapEvent();// 设置地图事件
	addMapControl();// 向地图添加控件
	// addMarker();//向地图中添加marker
	/*
	 * var geolocation = new BMap.Geolocation();
	 * geolocation.getCurrentPosition(function(r){ if(this.getStatus() ==
	 * BMAP_STATUS_SUCCESS){ var mk = new BMap.Marker(r.point); //
	 * console.log(r.point) map.addOverlay(mk);
	 * //alert('您的位置：'+r.point.lon+','+r.point.lat); } else {
	 * alert('failed'+this.getStatus()); } },{enableHighAccuracy: true})
	 */
}

// 创建地图函数：
function createMap(lon, lat) {
	var map = new BMap.Map("dituContent");// 在百度地图容器中创建一个地图
	var point = new BMap.Point(lon, lat);// 定义一个中心点坐标
	setCenterPoint(point);
	window.map = map;// 将map变量存储在全局

}
// 转换并设置中心点
function setCenterPoint(point) {
	var convertor = new BMap.Convertor();
	translateCallback = function(data) {
		if (data.status === 0) {
			var points = data.points[0];
			map.centerAndZoom(points, 14);// 设定地图的中心点和坐标并将地图显示在地图容器中
		}
	}
	convertor.translate([point], 3, 5, translateCallback)
}
// 地图事件设置函数：
function setMapEvent() {
	map.reset();// 重新设置地图
	map.enableDragging();// 启用地图拖拽事件，默认启用(可不写)
	map.enableScrollWheelZoom();// 启用地图滚轮放大缩小
	map.enableDoubleClickZoom();// 启用鼠标双击放大，默认启用(可不写)
	map.enableKeyboard();// 启用键盘上下左右键移动地图
}

// 地图控件添加函数：
function addMapControl() {
	// 向地图中添加比例尺控件
	var ctrl_sca = new BMap.ScaleControl({
				anchor : BMAP_ANCHOR_BOTTOM_LEFT
			});
	map.addControl(ctrl_sca);
}

// 标注点数组

// 批量创建markers
function addMarkers(markerArr) {
	for (var i = 0; i < markerArr.length; i++) {
		var json = markerArr[i];
		var lon = json.point.lon;
		var lat = json.point.lat;
		var point = new BMap.Point(lon, lat);
		var iconImg = createIcon(json.icon);
		if (json.title || json.title == "") {
			var label = new BMap.Label(json.title, {
						"offset" : new BMap.Size(-json.icon.w / 2, -json.icon.h)
					});
			label.setStyle({// 这是 我的标记在地图上的标识样式
				borderColor : "#e2e2e2",
				color : "#000",
				padding : "10px",
				cursor : "pointer"
			});
		}
		// convertor.translate([point], 3, 5, translateCallback)
		addMarker(point, iconImg, label, i, json);
	}
}
// 创建marker
function addMarker(point, iconImg, label, i, json) {
	var convertor = new BMap.Convertor();
	translateCallback = function(data) {
		if (data.status === 0) {
			var marker = new BMap.Marker(data.points[0], {
						icon : iconImg
					});
			var iw = createInfoWindow(i, json);
			marker.setLabel(label);
			map.addOverlay(marker);

			(function() {
				var index = i;
				var _iw = createInfoWindow(i, json);
				var _marker = marker;
				_marker.addEventListener("click", function() {
							this.openInfoWindow(_iw);
							var lat = this.point.lat;
							var lon = this.point.lng;
							map.setCenter(this.point)
						});
				_iw.addEventListener("open", function() {
							_marker.getLabel().hide();
						})
				_iw.addEventListener("close", function() {
							_marker.getLabel().show();
						})
				label.addEventListener("click", function() {
							_marker.openInfoWindow(_iw);
						})
				if (!!json.isOpen) {
					label.hide();
					_marker.openInfoWindow(_iw);
				}
			})()
		}
	}
	convertor.translate([point], 3, 5, translateCallback)
}
// 创建InfoWindow
function createInfoWindow(i, json) {
	var iw = new BMap.InfoWindow("<b class='iw_poi_title' title='" + json.title
			+ "'>" + json.title + "</b><div class='iw_poi_content'>"
			+ json.content + "</div>");
	return iw;
}
// 创建一个Icon
function createIcon(json) {
	var icon = new BMap.Icon(ctx + "/static/images/icon_state_mark.png",
			new BMap.Size(json.w, json.h), {
				imageOffset : new BMap.Size(-json.l, -json.t),// 控制背景图片的偏移
																// 类似back
																// position
				infoWindowOffset : new BMap.Size(json.w, 0),
				offset : new BMap.Size(json.w / 2, json.h)
				// 控制图片的位置 anchor
		})
	return icon;
}