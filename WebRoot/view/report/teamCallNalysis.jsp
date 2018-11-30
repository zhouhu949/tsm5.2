<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <%@ include file="/common/include.jsp" %>
    <title>团队通话效率分析</title>
    <style>
        .com-table table thead tr  th{font-weight:normal;font-size:12px;}
		.person-todata-warm span{line-height:25px;}
		.color_grey{color:grey !important;}
    </style>
    <script type="text/javascript">
	    var toWeekFrom="${toWeekFrom}";
		var toWeekTo="${toWeekTo}";
		var toDayFrom="${toDayFrom}";
		var toDayTo="${toDayTo}";
		/**
		 * 日期范围限制*/

		/*起始日期*/
		function pickedFunc1(){
			$("#dateType").val("");
			var fromDateStr = $("#data_01").val();
			var toDateStr = $("#data_02").val();			
			var fromDate=new Date(fromDateStr);
			var toDate=new Date(toDateStr);
			fromDate.setDate(fromDate.getDate()+30);
			if(toDate>=fromDate){
				var m=fromDate.getMonth()+1;
				var d=fromDate.getDate();
				if(m<10) m="0"+m;
				if(d<10) d="0"+d;
				$("#data_02").val(fromDate.getFullYear()+"-"+m+"-"+d);
			}
		}

		/*截止日期*/
		function pickedFunc2(){
			$("#dateType").val("");
			var fromDateStr = $("#data_01").val();
			var toDateStr = $("#data_02").val();			
			var fromDate=new Date(fromDateStr);
			var toDate=new Date(toDateStr);
			toDate.setDate(toDate.getDate()-30);
			if(fromDate<toDate){
				var m=toDate.getMonth()+1;
				var d=toDate.getDate();
				if(m<10) m="0"+m;
				if(d<10) d="0"+d;
				$("#data_01").val(toDate.getFullYear()+"-"+m+"-"+d);
			}
		}
        window.onload=function(){
            var height = $(".person-static-max").height()+30;
            window.parent.$("#iframepage").css({"height":height+"px"});
        };
        
        
        function toWeekFn(){
        	$("#dateType").val("2");
        	$("#data_01").val(toWeekFrom);
        	$("#data_02").val(toWeekTo);
        	$("#myForm").submit();
        }
        
        function toDayFn(){
        	$("#dateType").val("1");
        	$("#data_01").val(toDayFrom);
        	$("#data_02").val(toDayTo);
        	$("#myForm").submit();
        }
    </script>
</head>
<body style="overflow:hidden;">
<input type="hidden" id="inputAccs" value="${inputAccs }">
<input type="hidden" id="times" value="${times }">
<input type="hidden" id="nums" value="${nums }">
<div class="person-static-max">
	<form id="myForm" action="${ctx}/report/teamCallNalysis" method="post">
	    <div class="hyx-silent-p clearfix">
	      <div class="com-search-report fl_l" style="margin-top:0;min-height:0">
		            <label class="fl_l" for="" >部门名称：</label>
		            <input type="hidden" id="groupId" name="groupId" value="${groupId}" />
		            <dl class="select fl_l" style="position:relative;z-index:555;width:145px;margin:0 auto;margin-right:10px;">
						<dt id="groupName">-全部-</dt>
						<dd>
							<ul id="tt1">
									
							</ul>
						</dd>
					</dl>
		        </div>
	        <input type="hidden" id="dateType" name="dateType" value="${dateType}" }>
	        <label class="fl_l" for="">选择日期：</label>
			<span class="fl_l" style="display: inline-block; position: relative;">
				<input  name="startDate" id="data_01" value="${startDate }"  type="text" class="selec-year-inpu com-form-data fl_l" onclick="WdatePicker({onpicked:pickedFunc1,dateFmt:'yyyy-MM-dd',isShowClear:false,readOnly:true,maxDate:'#F{$dp.$D(\'data_02\')}'})" />
				<span class="silent-custo-span fl_l">&nbsp;—&nbsp;</span>
				<input name="endDate" id="data_02" value="${endDate }"  type="text" class="selec-year-inpu com-form-data fl_l" onclick="WdatePicker({onpicked:pickedFunc2,dateFmt:'yyyy-MM-dd',isShowClear:false,readOnly:true,minDate:'#F{$dp.$D(\'data_01\');}',maxDate:'%y-%M-{%d-1}'})" />
			</span>
			<a href="###" class="silent-week fl_l ${dateType eq 1?'color_grey':''}"  onclick="toDayFn();">当天</a>
			<a href="###" class="silent-month fl_l ${dateType eq 2?'color_grey':''}" onclick="toWeekFn();">最近一周</a>
			<a href="###" class="com-btna fl_l" onclick="document.forms[0].submit();"><i class="min-search"></i><label class="lab-mar" >查询</label></a>
	    </div>
    </form>
    <div class="tip" style="width:100%;">
        <div class="tip-box" id="main" style="width:100%;height:500px;"></div>
    </div>
    <div class="person-todata-warm clearfix">
        <h2 class="fl_l">温馨提示：</h2>
        <span class="fl_l">通话次数越少，通话时间越久说明通话效率越高。</span>
    </div>
</div>
<script type="text/javascript" src="${ctx}/static/thirdparty/echarts-3.3.2/echarts.min.js${_v}"></script>
<script type="text/javascript" type="text/javascript">
$(function() {

    $("#tt1").tree({
        url: ctx + '/orgGroup/get_parent_group_json',
        onLoadSuccess: function (node, data) {
            var groupId = $("#groupId").val();
            if (groupId != null && groupId != '') {
                var text = '';
                $.each(groupId.split(','), function (index, obj) {
                    var n = $("#tt1").tree("find", obj);
                    if (n != null) {
                        text += n.text;
                    }
                });
                $("#groupName").text(text);
            }
        }, onClick: function (node) {
            $("#groupId").val(node.id);
            $("#groupName").text(node.text);
            $(".select dd").hide();
        }
    });


    var inputAccs = $("#inputAccs").val();
    var nums = $("#nums").val();
    var times = $("#times").val();
    var inputAccArray = new Array();
    var numArray = new Array();
    var timeArray = new Array();
    if (inputAccs != null) {
        var $inputAccs = inputAccs.split(",");
        for (var i = 0; i < $inputAccs.length; i++) {
            inputAccArray.push($inputAccs[i]);
        }
    }
    if (nums != null) {
        var $nums = nums.split(",");
        for (var j = 0; j < $nums.length; j++) {
            numArray.push($nums[j]);
        }
    }
    if (times != null) {
        var $times = times.split(",");
        for (var k = 0; k < $times.length; k++) {
            timeArray.push($times[k]);
        }
    }

    var ec = echarts;

    myChart = ec.init(document.getElementById('main'));
    myChart.setOption({
        title: {
            text: '团队通话效率统计分析图',
            x: 'center'
        },
        tooltip: {
            trigger: 'axis'
        },
        calculable: true,
        legend: {
            x: 'center',
            y: 'bottom',
            selectedMode: false,
            data: ['通话总时长', '通话次数']
        },
        xAxis: [
            {
                type: 'category',
                splitLine: {
                    show: false
                },
                data: inputAccArray
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '分钟',
                axisLabel: {
                    formatter: '{value}'
                }

            },
            {
                type: 'value',
                name: '次数',
                axisLabel: {
                    formatter: '{value}'
                }
            }
        ],
        series: [

            {
                name: '通话总时长',
                type: 'bar',
                barWidth: 20,
                data: timeArray
            },
            {
                name: '通话次数',
                type: 'line',
                yAxisIndex: 1,
                itemStyle: {
                    normal: {
                        lineStyle: {
                            width: 0
                        },
                        color: '#87cefa',
                    }
                },
                data: numArray
            }
        ]
    });
    window.onresize = myChart.resize;
});



</script>
</body>
</html>
