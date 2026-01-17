package io.github.gaomingyuan666.datasource.config;

import io.github.gaomingyuan666.datasource.constants.DataSourceType;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author chenpeng
 */
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {
    private static final ThreadLocal<DataSourceType> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 构造函数
     *
     * @param defaultTargetDataSource 默认的数据源
     * @param targetDataSources       多数据源每个key对应一个数据源
     */
    public DynamicRoutingDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources) {
        // 设置默认数据源
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        // 设置多数据源. key value的形式
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }


    @Override
    protected Object determineCurrentLookupKey() {
        return getDataSource();
    }

    /**
     * 设置使用哪个数据源
     *
     * @param dataSource 数据源对应的名字
     */
    public static void setDataSource(DataSourceType dataSource) {
        CONTEXT_HOLDER.set(dataSource);
    }

    /**
     * 获取数据源对应的名字
     *
     * @return 数据源对应的名字
     */
    public static DataSourceType getDataSource() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * 清空掉
     */
    public static void clearDataSource() {
        CONTEXT_HOLDER.remove();
    }
}
