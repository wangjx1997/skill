package wjx.skill.test;

import java.math.BigDecimal;

/**
 * @author WangJX
 * @date 2019/7/12 11:43
 * <p>
 * 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精
 * 确的浮点数运算，包括加减乘除和四舍五入。
 */
public class Arith {
    //默认除法运算精度
    private static final int DEF_DIV_SCALE = 10;

    //这个类不能实例化
    private Arith() {
    }

    /** */
    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }
    /** */
    /**
     * 提供精确的减法运算。
     * 26     * @param v1 被减数
     * 27     * @param v2 减数
     * 28     * @return 两个参数的差
     * 29
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }
    /** */
    /**
     * 36     * 提供精确的乘法运算。
     * 37     * @param v1 被乘数
     * 38     * @param v2 乘数
     * 39     * @return 两个参数的积
     * 40
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /** */
    /**
     * 48     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 49     * 小数点以后10位，以后的数字四舍五入。
     * 50     * @param v1 被除数
     * 51     * @param v2 除数
     * 52     * @return 两个参数的商
     * 53
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /** */
    /**
     * 59     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 60     * 定精度，以后的数字四舍五入。
     * 61     * @param v1 被除数
     * 62     * @param v2 除数
     * 63     * @param scale 表示表示需要精确到小数点以后几位。
     * 64     * @return 两个参数的商
     * 65
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /** */
    /**
     * 77     * 提供精确的小数位四舍五入处理。
     * 78     * @param v 需要四舍五入的数字
     * 79     * @param scale 小数点后保留几位
     * 80     * @return 四舍五入后的结果
     * 81
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static void main(String[] args) {
//        System.out.println(round(0.01301231,2));
        System.out.println(123.4567*123.4567);
        BigDecimal bd = new BigDecimal("123.4567");


        BigDecimal multiply = bd.multiply(bd);

        System.out.println(bd.multiply(bd));
    }
}
