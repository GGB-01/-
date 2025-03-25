package Study1_Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) throws ParseException {
        Date d1=new Date();//获取当前系统时间
        System.out.println("当前时间="+d1);

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss E");
        String format=sdf.format(d1);//将日期转换为指定格式的字符串
        System.out.println("当前时间="+format);

        Date d2=new Date(9234567);//通过指定毫秒数得到时间
        String format2=sdf.format(d2);
        System.out.println("时间："+format2);//获取某个时间对应的毫秒数

        //把一个格式化的String 转成对应的Date
        String s="1996年01月01日 10:20:30 星期一";
        Date parse=sdf.parse(s);
        System.out.println("时间："+parse);
    }
}
