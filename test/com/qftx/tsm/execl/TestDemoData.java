package com.qftx.tsm.execl;

import com.qftx.base.util.RoundUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User： bxl
 * Date： 2016/8/8
 * Time： 15:42
 */
public class TestDemoData {


    /**
     * 生成随机时间
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static Date getRandomDate(String beginDate, String endDate) {

        try {

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            Date start = format.parse(beginDate);//构造开始日期

            Date end = format.parse(endDate);//构造结束日期

//getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。

            if (start.getTime() >= end.getTime()) {

                return null;

            }

            long date = getRandom(start.getTime(), end.getTime());

            return new Date(date);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;

    }

    public static long getRandom(long begin, long end) {

        long rtn = begin + (long) (Math.random() * (end - begin));
//如果返回的是开始时间和结束时间，则递归调用本函数查找随机值

        if (rtn == begin || rtn == end) {

            return getRandom(begin, end);

        }

        return rtn;

    }


    public static String getName() {
        String str = "";
        str += "白玉芬，仓春莲，仓红，陈超云，陈高，陈国祥，陈宏柳，陈金娣，陈丽丽，陈丽丽，陈平，陈向东，陈晓冬，陈小荣，";
        str += "陈秀芬，陈艳华，陈兆国，成秀山，仇腊梅，戴金辉，邓海燕，翟蕾，丁德明，丁素琴，董荣柱，范惠，冯霞，伏严成，皋德华，皋歌，高艳，顾其兰，";
        str += "管小云，何佳丽，侯志玲，黄慧，黄伟，惠志刚，季赛花，季铜然，纪海燕，蒋雯，康海霞，乐峰，李金凤，李军，李晓慧，刘琳，马丽，孟亚军，明汉琴，";
        str += " 倪燕，潘杰，钱萍，邱文干，沈亚杰，盛静，施伟，施志胜，石磊，时扬，束长元，宋欣，孙新东，孙正兰，汤丽丽，唐新胜，陶椿燕，陶晓雷，";
        str += "王丹，王丹，王霞，王晓雷，王雪梅，吴长凤，吴凤祥，吴红兵，吴华强，吴萍，夏祥，肖丽丽，谢莉萍，谢艳，徐海莲，徐进，徐丽云，徐萍，徐小勇，徐泽民，严健华，颜刚，杨梅，杨雄，杨月华，虞凯捷，袁刚，章丽丽，张德梅，张芳，张红芳，张珊珊，赵勇，赵哲明，";
        str += "赵志雷，郑恒华，郑永军，周风，周娟娟，周鹿屏，周芹，周荣锋，周玄，朱建明，朱亚，庄海霞，邹丽丽，左晓姝，刘艳，白丽华，柏传红，柏林，柏月中，";
        str += "卞红巧，蔡坤，蔡银华，仓莉，曹彬，曹华，曹指南，陈晨，陈翠梅，陈国华，陈海艳，陈海璐，陈娟，陈林，陈玲，陈玲结，陈玲玲，陈茂东，陈晓静，陈迅，陈亚勤，陈燕，陈云高，成瑶，程呈，";
        str += "程进，戴启霞，邓丽，邓正同，狄黎明，董志娟，符铁屏，葛书敏，顾爱华，顾建华，顾龙霞，顾雨平，顾月华，郭荣兰，郭皖宁，郭筱，郝其栋，洪秀，胡泽夫，黄剑，江林萍，蒋星萍，蒋兆红，李海燕，李红深，李伟，李文艳，";
        str += "李有梅，李云，林卫峰，凌风芹，凌红，刘冬梅，刘梅，刘荣蔚，刘小丽，刘以红，刘缨，刘玮，卢小荣，陆青，陆维娜，吕霞";
        String[] list = str.split("，");
        return list[RoundUtil.getRandom(0, list.length - 1)];
    }

    public static String getCompanyName() {
        String str = "";
        str += "万科 中国海外中海 保利 绿地 恒大 绿城 合生创展 富力 金地 首都开发首开" +
                "龙湖 远洋 大华 中信地产 首创置业 招商局 北辰实业 珠江 金科 SOHO 金融街 复地 阳光100 苏宁环球 " +
                "农工商 融侨集团 金隅嘉业 合景泰富 佳兆业 杭州滨江 蓝光和骏 深业集团 金都 鑫苑 沿海绿色家园 " +
                "天津市房地产开发(天津房开) 宝龙 星惠誉 上海城开 浙江广厦 海尔 重庆隆鑫 方圆地产 协信 三盛宏业" +
                " 禹洲 武汉地产开发投资(武开) 花样年 福建正荣 上海爱家豪庭 栖霞建设 深圳富通 上海中房置业 永泰 " +
                "百步亭 深圳香江控股 正商置业 旭辉集团 浙江昆仑置业 广州颐和 武汉三江航天 上海上投房地产 浙江佳源 " +
                "云南俊发 广西东方航洋 天津塘沽贻成实业 海亮地产 重庆光华控股 上海三湘股份 广州市海伦堡 青岛伟东置业" +
                " 奥宸地产 联发集团 天津津滨发展 杭州都房地产 广东利海 深圳合正 广州百嘉信 力高 上海中锐 中骏置业 众安 " +
                "大发集团 重庆长安 宁波奥克斯 莱蒙鹏源 重庆市兴茂 浙江祥生 江西恒茂 上海凯迪 莱茵达置业 福建华辰 华润置地 " +
                "世茂 碧桂园 万达 雅居乐 世纪金源 星河湾 瑞安 中华企业 越秀城建 新湖中宝 上置集团 江苏新城 方兴地产 凯德置地 " +
                "万通 中铁置业 鸿荣源 陆家嘴 卓越置业 珠海华发 融科智地 上海中星 泛海建设 亿达 浙江省商业集团（浙商集团） 厦门建发 " +
                "新世界 建业 河北卓达 雅戈尔 上海实业（上实） 天津市房地产（天房） 金隅股份 荣盛  北京城建 恒盛 上海城投置地 上海建工 " +
                "上海宝华企业集团 青岛海信 北京住总 安徽元一集团 宁波银亿 中粮地产 上海证大 亿城集团 奥园地产 天津泰达 华侨城";
        String[] list = str.split(" ");
        String name = list[RoundUtil.getRandom(0, list.length - 1)];
        if (name.trim().length() == 0) {
            name = getCompanyName();
        }
        return name;
    }


    public static String getTelPhoneNumber() {
        String str = "1";
        str += "" + RoundUtil.getRandom(1, 9);
        str += "" + RoundUtil.getRandom(2, 8);
        str += "" + RoundUtil.getRandom(0, 9);
        str += "" + RoundUtil.getRandom(0, 9);
        str += "" + RoundUtil.getRandom(0, 9);
        str += "" + RoundUtil.getRandom(0, 9);
        str += "" + RoundUtil.getRandom(0, 9);
        str += "" + RoundUtil.getRandom(0, 9);
        str += "" + RoundUtil.getRandom(0, 9);
        str += "" + RoundUtil.getRandom(0, 9);

        return str;
    }

    public static void main(String[] args) {
        for (int i = 0; i <20 ; i++) {
            System.out.println(getRandom(0,3));
        }

    }
}
