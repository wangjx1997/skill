package wjx.skill.config.datesource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @Author wjx
 * @Description 动态数据源
 * AbstractRoutingDataSource(每执行一次数据库，动态获取DataSource)
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDataSourceType();
    }
}
