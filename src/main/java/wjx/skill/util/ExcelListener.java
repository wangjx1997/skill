package wjx.skill.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WangJX
 * @date 2019/10/23 20:10
 */
public class ExcelListener<T extends BaseRowModel> extends AnalysisEventListener<T> {
    /**
     * 自定义用于暂时存储data。
     * 可以通过实例获取该值
     */
    private final List<T> data = new ArrayList<>();

    @Override
    public void invoke(T object, AnalysisContext context) {
        //数据存储
        data.add(object);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }

    public List<T> getData() {
        return data;
    }

}
