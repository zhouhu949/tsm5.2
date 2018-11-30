package com.qftx.tsm.execl;

import com.qftx.tsm.cust.bean.ResCustInfoBean;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User： bxl
 * Date： 2016/8/8
 * Time： 14:51
 */
public class TestDemoExeclData {
    public static float percent=0;
    public static void main(String[] args) throws IOException {
        // ExcelUtils.wirteWorkbookFile();
      //  OutputStream out = new FileOutputStream("d://a.xls");
        OutputStream out = new FileOutputStream("d://a.xlsx");
        String[] headers = { "姓名", "公司名称","电话号码", "性别", "出生日期"};
        String[] keyValue = {"name", "company", "mobilephone", "sex", "birthday"};
        List<Object> dataset = new ArrayList<Object>();
        TestDemoExeclData tt = new TestDemoExeclData();
        int ct=100*1;

        for (int i = 0; i <ct ; i++) {
            ResCustInfoBean user = new ResCustInfoBean();
            user.setName(TestDemoData.getName());
            user.setCompany(TestDemoData.getCompanyName());
            user.setMobilephone(TestDemoData.getTelPhoneNumber());
            user.setSex(TestDemoData.getRandom(0, 3) == 1 ? "男" : "女");
            user.setBirthday(TestDemoData.getRandomDate("1980-01-20", "2000-01-28"));
            dataset.add(user);

            float num= (float)(i+1)/ct*50;

            DecimalFormat df = new DecimalFormat("0.00");
            percent=num;
            System.out.println((df.format(num)));
        }
    //  tt.exportExcel(headers, keyValue, dataset, out, "yyyy-MM-dd HH:mm:ss");
      tt.exportExcel(headers, keyValue, dataset, out, "yyyy-MM-dd");
        out.close();
    }

    public  static void exportExcel(Collection dataset, OutputStream out) {
        exportExcel("EXCEL数据", null, null, dataset, out, "yyyy-MM-dd");
    }

    public static void exportExcel(String[] headers, String[] key, Collection dataset, OutputStream out) {
        exportExcel("EXCEL数据", headers, key, dataset, out, "yyyy-MM-dd");
    }

    public  static void exportExcel(String[] headers, String[] key, Collection dataset, OutputStream out, String pattern) {
        exportExcel("EXCEL数据", headers, key, dataset, out, pattern);
    }

    /**
     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
     *
     * @param title            表格标题名
     * @param headers          表格属性列名数组
     * @param dataset          需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *                         javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param out              与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param dataFormtPattern 如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
     */
    @SuppressWarnings("unchecked")
    public static void exportExcel(String title, String[] headers, String[] key, Collection dataset, OutputStream out, String dataFormtPattern) {
        // 声明一个工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 生成一个表格
        XSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 25);
        // 生成一个样式
        XSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        XSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 生成并设置另一个样式
        XSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体
        XSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);

        // 声明一个画图的顶级管理器
        XSSFDrawing patriarch = sheet.createDrawingPatriarch();
        // 定义注释的大小和位置,详见文档
        XSSFComment comment = patriarch.createCellComment(new XSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
        // 设置注释内容
        comment.setString(new XSSFRichTextString("可以在POI中添加注释！"));
        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
        comment.setAuthor("leno");
        //产生表格标题行
        XSSFRow row = sheet.createRow(0);
        for (short i = 0; i < headers.length; i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
           XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        //遍历集合数据，产生数据行
        Iterator it = dataset.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            float num= (float)(index)/dataset.size()*50;
            System.out.println(new DecimalFormat("0.00").format(percent+num));
            row = sheet.createRow(index);
            Object t = it.next();
            //利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
          //  Field[] fields = t.getClass().getDeclaredFields();
            for (short i = 0; i < key.length; i++) {
                XSSFCell cell = row.createCell(i);
                cell.setCellStyle(style2);
               // Field field = fields[i];
                String fieldName =key[i];
                String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                try {
                 //   if (getMethodName.endsWith("SerialVersionUID")) continue;
                   // System.out.println(getMethodName);
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                    Object value = getMethod.invoke(t, new Object[]{});
                  //  System.out.println(value);
                    //判断值的类型后进行强制类型转换
                    String textValue = null;
                    if (value != null) {
                        if (value instanceof Boolean) {
                            boolean bValue = (Boolean) value;
                            textValue = "男";
                            if (!bValue) {
                                textValue = "女";
                            }
                        } else if (value instanceof Date) {
                            Date date = (Date) value;
                            SimpleDateFormat sdf = new SimpleDateFormat(dataFormtPattern);
                            textValue = sdf.format(date);
                        } else if (value instanceof byte[]) {
                            // 有图片时，设置行高为60px;
                            row.setHeightInPoints(60);
                            // 设置图片所在列宽度为80px,注意这里单位的一个换算
                            sheet.setColumnWidth(i, (short) (35.7 * 80));
                            // sheet.autoSizeColumn(i);
                            byte[] bsValue = (byte[]) value;
                            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short) 6, index, (short) 6, index);
                            anchor.setAnchorType(2);
                            patriarch.createPicture(anchor, workbook.addPicture(
                                    bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
                        } else {
                            //其它数据类型都当作字符串简单处理
                            textValue = value.toString();
                        }
                    }
                    //如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                  //  System.out.println(textValue);
                    if (textValue != null) {
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            //是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            XSSFRichTextString richString = new XSSFRichTextString(textValue);
                            //HSSFFont font3 = workbook.createFont();
                          //  font3.setColor(HSSFColor.BLUE.index);
                           // richString.applyFont(font3);
                            cell.setCellValue(richString);
                        }
                    }

                } catch (Exception e){
                    e.printStackTrace();
                } finally {
                   ; //清理资源
                }
            }
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
