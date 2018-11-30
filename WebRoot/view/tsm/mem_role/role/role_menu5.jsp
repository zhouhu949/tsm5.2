<%@ page language="java" pageEncoding="UTF-8"%>
  <form id="myForm_5" method="post">
   <input type="hidden" id="curStep5" name="curStep5" value="5">
   <div class="menu-title">时间设置</div>
				<div class="work-time-set" style="margin-top:20px;">
					<p class="clearfix">
						<span class="sp sty-borcolor-b skin-minimal" style="float:left;margin-top:3px;"><input type="checkbox" class="check" id="workDay1" name="roleTimeControls[1].workDay"  value="1"  ${map[1].workDay==1?'checked':''} /></span><span class="work-time-week fl_l">星期一</span><input class="fl_l" type="text" id="startTime1" name="roleTimeControls[1].startTime" value="${map[1].startTime}" onclick="WdatePicker({dateFmt:'HH:mm:ss',minDate:'00:00:00',maxDate:'#F{$dp.$D(\'endTime1\')||\'23:59:59\'}'})"><span class="time-line fl_l"></span><input class="fl_l" type="text" id="endTime1" name="roleTimeControls[1].endTime" value="${map[1].endTime}" onclick="WdatePicker({dateFmt:'HH:mm:ss',minDate:'#F{$dp.$D(\'startTime1\')||\'00:00:00\'}',maxDate:'23:59:59'})">
					</p>
				</div>
				<div class="work-time-set">
					<p class="clearfix">
						<span class="sp sty-borcolor-b skin-minimal" style="float:left;margin-top:3px;"><input type="checkbox"  class="check" id="workDay2" name="roleTimeControls[2].workDay"  value="2"  ${map[2].workDay==2?'checked':''} /></span><span class="work-time-week fl_l">星期二</span><input class="fl_l" type="text" id="startTime2" name="roleTimeControls[2].startTime" value="${map[2].startTime}" onclick="WdatePicker({dateFmt:'HH:mm:ss',minDate:'00:00:00',maxDate:'#F{$dp.$D(\'endTime2\')||\'23:59:59\'}'})"><span class="time-line fl_l"></span><input class="fl_l" type="text" id="endTime2" name="roleTimeControls[2].endTime" value="${map[2].endTime}" onclick="WdatePicker({dateFmt:'HH:mm:ss',minDate:'#F{$dp.$D(\'startTime2\')||\'00:00:00\'}',maxDate:'23:59:59'})">
					</p>
				</div>
				<div class="work-time-set">
					<p class="clearfix">
						<span class="sp sty-borcolor-b skin-minimal" style="float:left;margin-top:3px;"><input type="checkbox"  class="check" id="workDay3" name="roleTimeControls[3].workDay"  value="3"  ${map[3].workDay==3?'checked':''} /></span><span class="work-time-week fl_l">星期三</span><input class="fl_l" type="text" id="startTime3" name="roleTimeControls[3].startTime" value="${map[3].startTime}" onclick="WdatePicker({dateFmt:'HH:mm:ss',minDate:'00:00:00',maxDate:'#F{$dp.$D(\'endTime3\')||\'23:59:59\'}'})"><span class="time-line fl_l"></span><input class="fl_l" type="text" id="endTime3" name="roleTimeControls[3].endTime" value="${map[3].endTime}" onclick="WdatePicker({dateFmt:'HH:mm:ss',minDate:'#F{$dp.$D(\'startTime3\')||\'00:00:00\'}',maxDate:'23:59:59'})">
					</p>
				</div>
				<div class="work-time-set">
					<p class="clearfix">
						<span class="sp sty-borcolor-b skin-minimal" style="float:left;margin-top:3px;"><input type="checkbox"  class="check" id="workDay4" name="roleTimeControls[4].workDay"  value="4"  ${map[4].workDay==4?'checked':''} /></span><span class="work-time-week fl_l">星期四</span><input class="fl_l" type="text" id="startTime4" name="roleTimeControls[4].startTime" value="${map[4].startTime}" onclick="WdatePicker({dateFmt:'HH:mm:ss',minDate:'00:00:00',maxDate:'#F{$dp.$D(\'endTime4\')||\'23:59:59\'}'})"><span class="time-line fl_l"></span><input class="fl_l" type="text" id="endTime4" name="roleTimeControls[4].endTime" value="${map[4].endTime}" onclick="WdatePicker({dateFmt:'HH:mm:ss',minDate:'#F{$dp.$D(\'startTime4\')||\'00:00:00\'}',maxDate:'23:59:59'})">
					</p>
				</div>
				<div class="work-time-set">
					<p class="clearfix">
						<span class="sp sty-borcolor-b skin-minimal" style="float:left;margin-top:3px;"><input type="checkbox"  class="check" id="workDay5" name="roleTimeControls[5].workDay"  value="5"  ${map[5].workDay==5?'checked':''}/></span><span class="work-time-week fl_l">星期五</span><input class="fl_l" type="text" id="startTime5" name="roleTimeControls[5].startTime" value="${map[5].startTime}" onclick="WdatePicker({dateFmt:'HH:mm:ss',minDate:'00:00:00',maxDate:'#F{$dp.$D(\'endTime5\')||\'23:59:59\'}'})"><span class="time-line fl_l"></span><input class="fl_l" type="text" id="endTime5" name="roleTimeControls[5].endTime" value="${map[5].endTime}" onclick="WdatePicker({dateFmt:'HH:mm:ss',minDate:'#F{$dp.$D(\'startTime5\')||\'00:00:00\'}',maxDate:'23:59:59'})">
					</p>
				</div>
				<div class="work-time-set">
					<p class="clearfix">
						<span class="sp sty-borcolor-b skin-minimal" style="float:left;margin-top:3px;"><input type="checkbox"  class="check"  id="workDay6" name="roleTimeControls[6].workDay"  value="6"  ${map[6].workDay==6?'checked':''}/></span><span class="work-time-week fl_l">星期六</span><input class="fl_l" type="text" id="startTime6" name="roleTimeControls[6].startTime" value="${map[6].startTime}" onclick="WdatePicker({dateFmt:'HH:mm:ss',minDate:'00:00:00',maxDate:'#F{$dp.$D(\'endTime6\')||\'23:59:59\'}'})"><span class="time-line fl_l"></span><input class="fl_l" type="text" id="endTime6" name="roleTimeControls[6].endTime" value="${map[6].endTime}" onclick="WdatePicker({dateFmt:'HH:mm:ss',minDate:'#F{$dp.$D(\'startTime6\')||\'00:00:00\'}',maxDate:'23:59:59'})">
					</p>
				</div>
				<div class="work-time-set">
					<p class="clearfix">
						<span class="sp sty-borcolor-b skin-minimal" style="float:left;margin-top:3px;"><input type="checkbox"  class="check"  id="workDay0" name="roleTimeControls[0].workDay"  value="0"  ${map[0].workDay==0?'checked':''}/></span><span class="work-time-week fl_l">星期日</span><input class="fl_l" type="text" id="startTime0" name="roleTimeControls[0].startTime" value="${map[0].startTime}" onclick="WdatePicker({dateFmt:'HH:mm:ss',minDate:'00:00:00',maxDate:'#F{$dp.$D(\'endTime0\')||\'23:59:59\'}'})"><span class="time-line fl_l"></span><input class="fl_l" type="text" id="endTime0" name="roleTimeControls[0].endTime" value="${map[0].endTime}" onclick="WdatePicker({dateFmt:'HH:mm:ss',minDate:'#F{$dp.$D(\'startTime0\')||\'00:00:00\'}',maxDate:'23:59:59'})">
					</p>
				</div>
				</form>
