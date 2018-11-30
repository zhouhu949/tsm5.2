<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="rank-botto-left fl_l">
    <div class="cont-amount-rank">
        <ul class="clearfix">
            <li>
                <span class="cont-amount-title">回款金额</span>
                <span class="cont-small-title">排行榜</span>
            </li>
            <li>
                <span class="champion-img">
                	<!-- 头像 -->
                	<img src="${empty saleTop3['1']['imgUrl'] ? ctx : ''}${empty saleTop3['1']['imgUrl'] ? '/static/images/header.png' : saleTop3['1']['imgUrl']}" class="sale-head-pic"/>
                </span>
                <span class="cont-small-title">${!empty saleTop3['1'] ? saleTop3['1']['userName'] : '' }</span>
            </li>
            <li>
            	<c:set var="rankSecond">${!empty saleTop3['2'] ? saleTop3['2']['rank'] : 2 }</c:set>
                <span class="${rankSecond eq 1 ? 'champion-img' : 'runner-up' }">
                	<c:if test="${!empty saleTop3['2'] }">
	                	<!-- 头像 -->
	                	<img src="${empty saleTop3['2']['imgUrl'] ? ctx : ''}${empty saleTop3['2']['imgUrl'] ? '/static/images/header.png' : saleTop3['2']['imgUrl']}" class="sale-head-pic"/>
                	</c:if>
                </span>
                <span class="cont-small-title">${!empty saleTop3['2'] ? saleTop3['2']['userName'] : '' }</span>
            </li>
            <li>
            	<c:set var="rankThird">${!empty saleTop3['3'] ? saleTop3['3']['rank'] : 3 }</c:set>
            	<c:choose>
            		<c:when test="${rankThird eq 1 }">
            			<span class="champion-img">
            				<c:if test="${!empty saleTop3['3'] }">
	            				<!-- 头像 -->
	                			<img src="${empty saleTop3['3']['imgUrl'] ? ctx : ''}${empty saleTop3['3']['imgUrl'] ? '/static/images/header.png' : saleTop3['3']['imgUrl']}" class="sale-head-pic"/>
            				</c:if>
            			</span>
            		</c:when>
            		<c:when test="${rankThird eq 2 }">
            			<span class="runner-up">
            				<c:if test="${!empty saleTop3['3'] }">
	            				<!-- 头像 -->
	                			<img src="${empty saleTop3['3']['imgUrl'] ? ctx : ''}${empty saleTop3['3']['imgUrl'] ? '/static/images/header.png' : saleTop3['3']['imgUrl']}" class="sale-head-pic"/>
            				</c:if>
            			</span>
            		</c:when>
            		<c:otherwise>
            		 	<span class="${rankSecond eq 1 ? 'runner-up' : 'third-place' }">
            		 		<c:if test="${!empty saleTop3['3'] }">
	            				<!-- 头像 -->
	                			<img src="${empty saleTop3['3']['imgUrl'] ? ctx : ''}${empty saleTop3['3']['imgUrl'] ? '/static/images/header.png' : saleTop3['3']['imgUrl']}" class="sale-head-pic"/>
            				</c:if>
            		 	</span>
            		</c:otherwise>
            	</c:choose>
                <span class="cont-small-title">${!empty saleTop3['3'] ? saleTop3['3']['userName'] : '' }</span>
            </li>
        </ul>
    </div>
    <div class="cont-custom-rank">
        <ul class="clearfix">
            <li>
                <span class="cont-custom-title">新增签约</span>
                <span class="cont-custom-small">排行榜</span>
            </li>
            <li>
                <label class="cont-custom-card01">
                    <span class="first-number">NO.1</span>
                    <span class="custom-card-name">${!empty signTop3['1'] ? signTop3['1']['userName'] : '' }</span>
                    <span class="custom-card-line"></span>
                    <span class="custom-card-bottom"></span>
                </label>
            </li>
            <li>
               	<c:set var="signRankSecond">${!empty signTop3['2'] ? signTop3['2']['rank'] : 2 }</c:set>
                <label class="cont-custom-card0${signRankSecond }">
                    <span class="first-number">NO.${signRankSecond }</span>
                    <span class="custom-card-name">${!empty signTop3['2'] ? signTop3['2']['userName'] : '' }</span>
                    <span class="custom-card-line"></span>
                    <span class="custom-card-bottom"></span>
                </label>
            </li>
            <li>
            	<c:set var="signRankThird">${!empty signTop3['3'] ? signTop3['3']['rank'] : 0 }</c:set>
                <c:if test="${signRankThird eq 0 }">
	                <c:choose>
	                	<c:when test="${signRankSecond eq 1 }">
	                		<c:set var="signRankThird">2</c:set>
	                	</c:when>
	                	<c:otherwise>
	                		<c:set var="signRankThird">3</c:set>
	                	</c:otherwise>
	                </c:choose>
                </c:if>
                <label class="cont-custom-card0${signRankThird }">
                    <span class="first-number">NO.${signRankThird }</span>
                    <span class="custom-card-name">${!empty signTop3['3'] ? signTop3['3']['userName'] : '' }</span>
                    <span class="custom-card-line"></span>
                    <span class="custom-card-bottom"></span>
                </label>
            </li>
        </ul>
    </div>
    <div class="will-custom-rank" style="border-top:0">
        <ul class="clearfix">
            <li>
                <span class="cont-custom-title">新增意向</span>
                <span class="cont-custom-small">排行榜</span>
            </li>
            <li>
                <label class="cont-custom-card01">
                    <span class="first-number">NO.1</span>
                    <span class="custom-card-name">${!empty willTop3['1'] ? willTop3['1']['userName'] : '' }</span>
                    <span class="custom-card-line"></span>
                    <span class="custom-card-bottom"></span>
                </label>
            </li>
            <li>
               	<c:set var="willRankSecond">${!empty willTop3['2'] ? willTop3['2']['rank'] : 2 }</c:set>
                <label class="cont-custom-card0${willRankSecond }">
                    <span class="first-number">NO.${willRankSecond }</span>
                    <span class="custom-card-name">${!empty willTop3['2'] ? willTop3['2']['userName'] : '' }</span>
                    <span class="custom-card-line"></span>
                    <span class="custom-card-bottom"></span>
                </label>
            </li>
            <li>
            	<c:set var="willRankThird">${!empty willTop3['3'] ? willTop3['3']['rank'] : 0 }</c:set>
                <c:if test="${willRankThird eq 0 }">
	                <c:choose>
	                	<c:when test="${willRankSecond eq 1 }">
	                		<c:set var="willRankThird">2</c:set>
	                	</c:when>
	                	<c:otherwise>
	                		<c:set var="willRankThird">3</c:set>
	                	</c:otherwise>
	                </c:choose>
                </c:if>
                <label class="cont-custom-card0${willRankThird }">
                    <span class="first-number">NO.${willRankThird }</span>
                    <span class="custom-card-name">${!empty willTop3['3'] ? willTop3['3']['userName'] : '' }</span>
                    <span class="custom-card-line"></span>
                    <span class="custom-card-bottom"></span>
                </label>
            </li>
        </ul>
    </div>
    <div class="exhal-breath-rank" style="border-top:0">
        <ul class="clearfix">
            <li>
                <span class="cont-custom-title">呼出时长</span>
                <span class="cont-custom-small">排行榜</span>
            </li>
            <li>
                <label class="cont-custom-card01">
                    <span class="first-number">NO.1</span>
                    <span class="custom-card-name">${!empty callTop3['1'] ? callTop3['1']['userName'] : '' }</span>
                    <span class="custom-card-line"></span>
                    <span class="custom-card-bottom"></span>
                </label>
            </li>
            <li>
                <c:set var="callRankSecond">${!empty callTop3['2'] ? callTop3['2']['rank'] : 2 }</c:set>
                <label class="cont-custom-card0${callRankSecond }">
                    <span class="first-number">NO.${callRankSecond }</span>
                    <span class="custom-card-name">${!empty callTop3['2'] ? callTop3['2']['userName'] : '' }</span>
                    <span class="custom-card-line"></span>
                    <span class="custom-card-bottom"></span>
                </label>
            </li>
            <li>
               	<c:set var="callRankThird">${!empty callTop3['3'] ? callTop3['3']['rank'] : 0 }</c:set>
                <c:if test="${callRankThird eq 0 }">
	                <c:choose>
	                   	<c:when test="${callRankSecond eq 1 }">
	                   		<c:set var="callRankThird">2</c:set>
	                   	</c:when>
	                 	<c:otherwise>
	                   		<c:set var="callRankThird">3</c:set>
	                   	</c:otherwise>
	                </c:choose>
                </c:if>
                <label class="cont-custom-card0${callRankThird }">
                    <span class="first-number">NO.${callRankThird }</span>
                    <span class="custom-card-name">${!empty callTop3['3'] ? callTop3['3']['userName'] : '' }</span>
                    <span class="custom-card-line"></span>
                    <span class="custom-card-bottom"></span>
                </label>
            </li>
        </ul>
    </div>
    <div class="call-out-rank" style="border-top:0">
        <ul class="clearfix">
            <li>
                <span class="cont-custom-title">呼出次数</span>
                <span class="cont-custom-small">排行榜</span>
            </li>
            <li>
                <label class="cont-custom-card01">
                    <span class="first-number">NO.1</span>
                    <span class="custom-card-name">${!empty callOutTop3['1'] ? callOutTop3['1']['userName'] : '' }</span>
                    <span class="custom-card-line"></span>
                    <span class="custom-card-bottom"></span>
                </label>
            </li>
            <li>
                <c:set var="callOutRankSecond">${!empty callOutTop3['2'] ? callOutTop3['2']['rank'] : 2 }</c:set>
                <label class="cont-custom-card0${callOutRankSecond }">
                    <span class="first-number">NO.${callOutRankSecond }</span>
                    <span class="custom-card-name">${!empty callOutTop3['2'] ? callOutTop3['2']['userName'] : '' }</span>
                    <span class="custom-card-line"></span>
                    <span class="custom-card-bottom"></span>
                </label>
            </li>
            <li>
               	<c:set var="callOutRankThird">${!empty callOutTop3['3'] ? callOutTop3['3']['rank'] : 0 }</c:set>
                <c:if test="${callOutRankThird eq 0 }">
	                <c:choose>
	                   	<c:when test="${callOutRankThird eq 1 }">
	                   		<c:set var="callOutRankThird">2</c:set>
	                   	</c:when>
	                 	<c:otherwise>
	                   		<c:set var="callOutRankThird">3</c:set>
	                   	</c:otherwise>
	                </c:choose>
                </c:if>
                <label class="cont-custom-card0${callOutRankThird }">
                    <span class="first-number">NO.${callOutRankThird }</span>
                    <span class="custom-card-name">${!empty callOutTop3['3'] ? callOutTop3['3']['userName'] : '' }</span>
                    <span class="custom-card-line"></span>
                    <span class="custom-card-bottom"></span>
                </label>
            </li>
        </ul>
    </div>
</div>
<div class="rank-botto-right fl_l">
    <div id="saleRank" class="all-rember-rank">
        <ul>
            <li class="all-rember-title">全部成员排行</li>
            <li class="all-rember-th clearfix"><span class="rank fl_l">排名</span><span class="all-rember-name">姓名</span><span class="all-rember-money">金额(元)</span></li>
            <c:forEach items="${saleDtos }" var="sale" varStatus="vs">
            	<c:choose>
            		<c:when test="${sale.rank eq 1 }">
            			<li class="${sale.userAccount eq 'curr_user_account' ? 'all-rember-tdme' : 'all-rember-td02' } clearfix">
							<span>
								<span class="rember-td01-gold"></span>
							</span>
			                <span class="rember-td01-name" title="${sale.userName }">${sale.userName }</span>
			                <span class="rember-td01-money">${sale.saleNum }</span>
			            </li>
            		</c:when>
            		<c:when test="${sale.rank eq 2 }">
            			<li class="${sale.userAccount eq 'curr_user_account' ? 'all-rember-tdme' : 'all-rember-td02' } clearfix">
							<span>
								<span class="rember-td01-sliver"></span>
							</span>
			                <span class="rember-td01-name" title="${sale.userName }">${sale.userName }</span>
			                <span class="rember-td01-money">${sale.saleNum }</span>
			            </li>
            		</c:when>
            		<c:when test="${sale.rank eq 3 }">
            			<li class="${sale.userAccount eq 'curr_user_account' ? 'all-rember-tdme' : 'all-rember-td02' } clearfix">
							<span>
								<span class="rember-td01-third"></span>
							</span>
			                <span class="rember-td01-name" title="${sale.userName }">${sale.userName }</span>
			                <span class="rember-td01-money">${sale.saleNum }</span>
			            </li>
            		</c:when>
            		<c:when test="${empty sale.rank}">
            			<li class="all-rember-avager cleafix">
			                <span class="rember-td01-text">平均值</span>
			                <span class="rember-td01-name">平均值</span>
			                <span class="rember-td01-money">${sale.saleNum }</span>
			            </li>
            		</c:when>
            		<c:otherwise>
            			<li class="${sale.userAccount eq 'curr_user_account' ? 'all-rember-tdme' : 'all-rember-td02' } clearfix">
			                <span class="rember-td01-text">${sale.rank }</span>
			                <span class="rember-td01-name" title="${sale.userName }">${sale.userName }</span>
			                <span class="rember-td01-money">${sale.saleNum }</span>
			            </li>
            		</c:otherwise>
            	</c:choose>
            </c:forEach>
        </ul>
    </div>
    
    <!-- 签约客户 -->
    <div id="signRank" class="all-rember-rank com-none">
        <ul>
            <li class="all-rember-title">全部成员排行</li>
            <li class="all-rember-th clearfix"><span class="rank fl_l">排名</span><span class="all-rember-name">姓名</span><span class="all-rember-money">数量(个)</span></li>
            <c:forEach items="${signDtos }" var="sign" varStatus="vs">
            	<c:choose>
            		<c:when test="${sign.rank eq 1 }">
            			<li class="${sign.userAccount eq 'curr_user_account' ? 'all-rember-tdme' : 'all-rember-td02' } clearfix">
							<span>
								<span class="rember-td01-gold"></span>
							</span>
			                <span class="rember-td01-name" title="${sign.userName }">${sign.userName }</span>
			                <span class="rember-td01-money">${sign.signNum }</span>
			            </li>
            		</c:when>
            		<c:when test="${sign.rank eq 2 }">
            			<li class="${sign.userAccount eq 'curr_user_account' ? 'all-rember-tdme' : 'all-rember-td02' } clearfix">
							<span>
								<span class="rember-td01-sliver"></span>
							</span>
			                <span class="rember-td01-name" title="${sign.userName }">${sign.userName }</span>
			                <span class="rember-td01-money">${sign.signNum }</span>
			            </li>
            		</c:when>
            		<c:when test="${sign.rank eq 3 }">
            			<li class="${sign.userAccount eq 'curr_user_account' ? 'all-rember-tdme' : 'all-rember-td02' } clearfix">
							<span>
								<span class="rember-td01-third"></span>
							</span>
			                <span class="rember-td01-name" title="${sign.userName }">${sign.userName }</span>
			                <span class="rember-td01-money">${sign.signNum }</span>
			            </li>
            		</c:when>
            		<c:when test="${empty sign.rank}">
            			<li class="all-rember-avager cleafix">
			                <span class="rember-td01-text">平均值</span>
			                <span class="rember-td01-name">平均值</span>
			                <span class="rember-td01-money">${sign.signNum }</span>
			            </li>
            		</c:when>
            		<c:otherwise>
            			<li class="${sign.userAccount eq 'curr_user_account' ? 'all-rember-tdme' : 'all-rember-td02' } clearfix">
			                <span class="rember-td01-text">${sign.rank }</span>
			                <span class="rember-td01-name" title="${sign.userName }">${sign.userName }</span>
			                <span class="rember-td01-money">${sign.signNum }</span>
			            </li>
            		</c:otherwise>
            	</c:choose>
            </c:forEach>
        </ul>
    </div>
    
    <!-- 意向客户 -->
    <div id="willRank" class="all-rember-rank com-none">
        <ul>
            <li class="all-rember-title">全部成员排行</li>
            <li class="all-rember-th clearfix"><span class="rank fl_l">排名</span><span class="all-rember-name">姓名</span><span class="all-rember-money">数量(个)</span></li>
            <c:forEach items="${willDtos }" var="will" varStatus="vs">
            	<c:choose>
            		<c:when test="${will.rank eq 1 }">
            			<li class="${will.userAccount eq 'curr_user_account' ? 'all-rember-tdme' : 'all-rember-td02' } clearfix">
							<span>
								<span class="rember-td01-gold"></span>
							</span>
			                <span class="rember-td01-name" title="${will.userName }">${will.userName }</span>
			                <span class="rember-td01-money">${will.willNum }</span>
			            </li>
            		</c:when>
            		<c:when test="${will.rank eq 2 }">
            			<li class="${will.userAccount eq 'curr_user_account' ? 'all-rember-tdme' : 'all-rember-td02' } clearfix">
							<span>
								<span class="rember-td01-sliver"></span>
							</span>
			                <span class="rember-td01-name" title="${will.userName }">${will.userName }</span>
			                <span class="rember-td01-money">${will.willNum }</span>
			            </li>
            		</c:when>
            		<c:when test="${will.rank eq 3 }">
            			<li class="${will.userAccount eq 'curr_user_account' ? 'all-rember-tdme' : 'all-rember-td02' } clearfix">
							<span>
								<span class="rember-td01-third"></span>
							</span>
			                <span class="rember-td01-name" title="${will.userName }">${will.userName }</span>
			                <span class="rember-td01-money">${will.willNum }</span>
			            </li>
            		</c:when>
            		<c:when test="${empty will.rank}">
            			<li class="all-rember-avager cleafix">
			                <span class="rember-td01-text">平均值</span>
			                <span class="rember-td01-name">平均值</span>
			                <span class="rember-td01-money">${will.willNum }</span>
			            </li>
            		</c:when>
            		<c:otherwise>
            			<li class="${will.userAccount eq 'curr_user_account' ? 'all-rember-tdme' : 'all-rember-td02' } clearfix">
			                <span class="rember-td01-text">${will.rank }</span>
			                <span class="rember-td01-name" title="${will.userName }">${will.userName }</span>
			                <span class="rember-td01-money">${will.willNum }</span>
			            </li>
            		</c:otherwise>
            	</c:choose>
            </c:forEach>
        </ul>
    </div>
    
    <!-- 通话时长 -->
    <div id="callRank" class="all-rember-rank com-none">
        <ul>
            <li class="all-rember-title">全部成员排行</li>
            <li class="all-rember-th clearfix"><span class="rank fl_l">排名</span><span class="all-rember-name">姓名</span><span class="all-rember-money">时长</span></li>
            <c:forEach items="${callDtos }" var="call" varStatus="vs">
            	<c:choose>
            		<c:when test="${call.rank eq 1 }">
            			<li class="${call.userAccount eq 'curr_user_account' ? 'all-rember-tdme' : 'all-rember-td02' } clearfix">
							<span>
								<span class="rember-td01-gold"></span>
							</span>
			                <span class="rember-td01-name" title="${call.userName }">${call.userName }</span>
			                <span class="rember-td01-money">${call.callTime }</span>
			            </li>
            		</c:when>
            		<c:when test="${call.rank eq 2 }">
            			<li class="${call.userAccount eq 'curr_user_account' ? 'all-rember-tdme' : 'all-rember-td02' } clearfix">
							<span>
								<span class="rember-td01-sliver"></span>
							</span>
			                <span class="rember-td01-name" title="${call.userName }">${call.userName }</span>
			                <span class="rember-td01-money">${call.callTime }</span>
			            </li>
            		</c:when>
            		<c:when test="${call.rank eq 3 }">
            			<li class="${call.userAccount eq 'curr_user_account' ? 'all-rember-tdme' : 'all-rember-td02' } clearfix">
							<span>
								<span class="rember-td01-third"></span>
							</span>
			                <span class="rember-td01-name" title="${call.userName }">${call.userName }</span>
			                <span class="rember-td01-money">${call.callTime }</span>
			            </li>
            		</c:when>
            		<c:when test="${empty call.rank}">
            			<li class="all-rember-avager cleafix">
			                <span class="rember-td01-text">平均值</span>
			                <span class="rember-td01-name">平均值</span>
			                <span class="rember-td01-money">${call.callTime }</span>
			            </li>
            		</c:when>
            		<c:otherwise>
            			<li class="${call.userAccount eq 'curr_user_account' ? 'all-rember-tdme' : 'all-rember-td02' } clearfix">
			                <span class="rember-td01-text">${call.rank }</span>
			                <span class="rember-td01-name" title="${call.userName }">${call.userName }</span>
			                <span class="rember-td01-money">${call.callTime }</span>
			            </li>
            		</c:otherwise>
            	</c:choose>
            </c:forEach>
        </ul>
    </div>
    
    <!-- 呼出次数 -->
    <div id="callOutRank" class="all-rember-rank com-none">
        <ul>
            <li class="all-rember-title">全部成员排行</li>
            <li class="all-rember-th clearfix"><span class="rank fl_l">排名</span><span class="all-rember-name">姓名</span><span class="all-rember-money">次数</span></li>
            <c:forEach items="${calloutDtos }" var="call" varStatus="vs">
            	<c:choose>
            		<c:when test="${call.rank eq 1 }">
            			<li class="${call.userAccount eq 'curr_user_account' ? 'all-rember-tdme' : 'all-rember-td02' } clearfix">
							<span>
								<span class="rember-td01-gold"></span>
							</span>
			                <span class="rember-td01-name" title="${call.userName }">${call.userName }</span>
			                <span class="rember-td01-money">${call.callOutTotal }</span>
			            </li>
            		</c:when>
            		<c:when test="${call.rank eq 2 }">
            			<li class="${call.userAccount eq 'curr_user_account' ? 'all-rember-tdme' : 'all-rember-td02' } clearfix">
							<span>
								<span class="rember-td01-sliver"></span>
							</span>
			                <span class="rember-td01-name" title="${call.userName }">${call.userName }</span>
			                <span class="rember-td01-money">${call.callOutTotal }</span>
			            </li>
            		</c:when>
            		<c:when test="${call.rank eq 3 }">
            			<li class="${call.userAccount eq 'curr_user_account' ? 'all-rember-tdme' : 'all-rember-td02' } clearfix">
							<span>
								<span class="rember-td01-third"></span>
							</span>
			                <span class="rember-td01-name" title="${call.userName }">${call.userName }</span>
			                <span class="rember-td01-money">${call.callOutTotal }</span>
			            </li>
            		</c:when>
            		<c:when test="${empty call.rank}">
            			<li class="all-rember-avager cleafix">
			                <span class="rember-td01-text">平均值</span>
			                <span class="rember-td01-name">平均值</span>
			                <span class="rember-td01-money">${call.callOutTotal }</span>
			            </li>
            		</c:when>
            		<c:otherwise>
            			<li class="${call.userAccount eq 'curr_user_account' ? 'all-rember-tdme' : 'all-rember-td02' } clearfix">
			                <span class="rember-td01-text">${call.rank }</span>
			                <span class="rember-td01-name" title="${call.userName }">${call.userName }</span>
			                <span class="rember-td01-money">${call.callOutTotal }</span>
			            </li>
            		</c:otherwise>
            	</c:choose>
            </c:forEach>
        </ul>
    </div>
    <div class="arrow-point">
        <label class="arrow"><em>◆</em><span>◆</span></label>
    </div>
</div>
