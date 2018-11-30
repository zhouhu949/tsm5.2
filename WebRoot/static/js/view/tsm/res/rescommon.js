/**
 * 
 */
function toCard(custId,custName){
	var custName = custName||"客户卡片";
	window.top.addTab(ctx+"/cust/card/custInfoCard?custId="+custId,custName);
}
