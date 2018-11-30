package com.qftx.common.page;

/**
 * User： bxl
 * Date： 2016/12/7
 * Time： 9:19
 */
public class TestJavaScriptPage {
    public static void main(String[] args) {
        String str="<div class=\"com-page fl_r\">\n" +
                "\t\t\t<div class=\"com-page fl_r\">\n" +
                "<a href=\"javascript:nextPage(1)\">首页</a><a href=\"javascript:nextPage(2)\">上页</a><input type=\"text\" class=\"pa\" maxlength=\"5\" onchange=\"return inputSubmit(this);\" onkeydown=\"if(event.keyCode==13){return inputSubmit(this);}\" value=\"3\"><label>/4</label><a href=\"javascript:nextPage(4)\">下页</a><a href=\"javascript:nextPage(4)\">尾页</a><select onchange=\"return selectSubmit(this);\"><option value=\"10\" selected=\"\">10</option><option value=\"20\">20</option><option value=\"30\">30</option><option value=\"50\">50</option><option value=\"100\">100</option></select><label>条/页</label>&nbsp;&nbsp;共&nbsp;<span><b>34</b></span>&nbsp;条&nbsp;</div>\n" +
                "<input type=\"hidden\" id=\"page.showCount\" name=\"page.showCount\" value=\"10\"><input type=\"hidden\" id=\"page.currentPage\" name=\"page.currentPage\" value=\"3\"><input type=\"hidden\" id=\"page.totalResult\" name=\"page.totalResult\" value=\"34\"><script type=\"text/javascript\">\n" +
                "function nextPage(val){ \n" +
                "  document.getElementById(\"page.currentPage\").value=val;\n" +
                "  document.forms[0].submit();\n" +
                "} \n" +
                "function selectSubmit(obj){ \n" +
                "  document.getElementById(\"page.showCount\").value=obj.value;\n" +
                "  document.forms[0].submit();\n" +
                "} \n" +
                "function inputSubmit(obj){ \n" +
                "\t\tvar va = obj.value; \n" +
                "  \tif(va==\"\"||isNaN(va)){ \n" +
                "  \t\talert('请输入数字!');\n" +
                "  \t\tobj.value=3;\n" +
                "  \t\treturn false;\n" +
                "  \t}else if(va > 4 || va < 1){ \n" +
                "  \t\talert('输入页码不存在！');\n" +
                "  \t\tobj.value=3;\n" +
                "  \t\treturn false;\n" +
                "  \t}\n" +
                "  \tdocument.getElementById(\"page.currentPage\").value=va;\n" +
                "     document.forms[0].submit();\n" +
                "} \n" +
                "</script>\n" +
                "</div>";
        System.out.println(str);
    }
}
