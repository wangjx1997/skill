package wjx.skill.util;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.apache.poi.poifs.filesystem.FileMagic;

import java.io.*;
import java.util.List;

/**
 * @author WangJX
 * @date 2019/10/23 17:39
 */
public class ExcleUtil {

    /**
     * 从Excel中读取文件，读取的文件是一个DTO类，该类必须继承BaseRowModel
     * 具体实例参考 ： MemberMarketDto.java
     * 参考：https://github.com/alibaba/easyexcel
     * 字符流必须支持标记，FileInputStream 不支持标记，可以使用BufferedInputStream 代替
     * BufferedInputStream bis = new BufferedInputStream(new FileInputStream(...));
     *
     * @param inputStream 文件输入流
     * @param clazz       继承该类必须继承BaseRowModel的类
     * @return 读取完成的list
     */
    public static <T extends BaseRowModel> List<T> readExcel(final InputStream inputStream, final Class<? extends BaseRowModel> clazz) {
        if (null == inputStream) {
            throw new NullPointerException("the inputStream is null!");
        }
        AnalysisEventListener listener = new ExcelListener();
        //读取xls 和 xlxs格式
        //如果POI版本为3.17，可以如下声明
        ExcelReader reader = new ExcelReader(inputStream, null, listener);
        //判断格式，针对POI版本低于3.17
        //ExcelTypeEnum excelTypeEnum = valueOf(inputStream);
        //ExcelReader reader = new ExcelReader(inputStream, excelTypeEnum, null, listener);
        reader.read(new com.alibaba.excel.metadata.Sheet(1, 1, clazz));

        return ((ExcelListener) listener).getData();
    }

    /**
     * 需要写入的Excel，有模型映射关系
     *
     * @param file  需要写入的Excel，格式为xlsx
     * @param list 写入Excel中的所有数据，继承于BaseRowModel
     */
    public static void writeExcel(final File file, List<? extends BaseRowModel> list) {
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            //写第一个sheet,  有模型映射关系
            Class t = list.get(0).getClass();
            com.alibaba.excel.metadata.Sheet sheet = new com.alibaba.excel.metadata.Sheet(1, 0, t);
            writer.write(list, sheet);
            writer.finish();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 根据输入流，判断为xls还是xlsx，该方法原本存在于easyexcel 1.1.0 的ExcelTypeEnum中。
     * 如果POI版本为3.17以下，则FileMagic会报错，找不到该类，此时去到POI 3.17中将FileMagic抽取出来
     */
    public static ExcelTypeEnum valueOf(InputStream inputStream) {
        try {
            FileMagic fileMagic = FileMagic.valueOf(inputStream);
            if (FileMagic.OLE2.equals(fileMagic)) {
                return ExcelTypeEnum.XLS;
            }
            if (FileMagic.OOXML.equals(fileMagic)) {
                return ExcelTypeEnum.XLSX;
            }
            throw new IllegalArgumentException("excelTypeEnum can not null");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
