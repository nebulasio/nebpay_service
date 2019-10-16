package io.nebulas.dapp.pay.common.mybatis;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.io.ResolverUtil;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.Resource;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: nathan
 * Date: 2018-04-26
 */
public class MybatisSqlSessionFactoryBean extends SqlSessionFactoryBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(MybatisSqlSessionFactoryBean.class);

    /**
     * base package path which contains enum handlers.
     * <p>
     * it can be specify more than one path,The separator is compatible with spring's package path separator
     * </p>
     */
    protected String enumBasePackages;

    protected SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();

    protected Resource[] mapperLocations;

    protected static final ConcurrentHashMap<Class<? extends IEnumValue>, EnumValueTypeHandler<?>> TYPE_HANDLER_CACHE = new ConcurrentHashMap<>();

    @Override
    public void setSqlSessionFactoryBuilder(SqlSessionFactoryBuilder sqlSessionFactoryBuilder) {
        super.setSqlSessionFactoryBuilder(sqlSessionFactoryBuilder);//because of sqlSessionFactoryBuilder in the parent class is private, so it must be manually specify it to the parent class once
        this.sqlSessionFactoryBuilder = sqlSessionFactoryBuilder;
    }

    /**
     * override this method for forbidden mapper locations are parsed by parent class
     */
    @Override
    public void setMapperLocations(Resource[] mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    public void setEnumBasePackages(String enumBasePackages) {
        this.enumBasePackages = enumBasePackages;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected SqlSessionFactory buildSqlSessionFactory() throws IOException {
        SqlSessionFactory oldSqlSessionFactory = super.buildSqlSessionFactory();
        Configuration configuration = oldSqlSessionFactory.getConfiguration();
        TypeHandlerRegistry registry = configuration.getTypeHandlerRegistry();

        String[] enumPackages = parseEnumBasePackage();
        if (null != enumPackages) {
            Set<Class<? extends IEnumValue>> enumClasses = doScanEnumClass(enumPackages);
            if (null != enumClasses) {
                for (Class<? extends IEnumValue> cls : enumClasses) {
                    registry.register(cls, getEnumValueTypeHandlerInstance(cls));
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("EnumValueTypeHandler is registered for type " + cls.getName());
                    }
                }
            }
        }

        if (!ObjectUtils.isEmpty(this.mapperLocations)) {
            for (Resource mapperLocation : this.mapperLocations) {
                if (mapperLocation == null) {
                    continue;
                }

                try {
                    XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(mapperLocation.getInputStream(),
                            configuration, mapperLocation.toString(), configuration.getSqlFragments());
                    xmlMapperBuilder.parse();
                } catch (Exception e) {
                    throw new NestedIOException("Failed to parse mapping resource: '" + mapperLocation + "'", e);
                } finally {
                    ErrorContext.instance().reset();
                }

                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Parsed mapper file: '" + mapperLocation + "'");
                }
            }
        } else {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Property 'mapperLocations' was not specified or no matching resources found");
            }
        }
        return this.sqlSessionFactoryBuilder.build(configuration);
    }

    /**
     * get and cache enum handler
     * <p>
     * it make sense when multiple config calls this method
     * </p>
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    protected EnumValueTypeHandler getEnumValueTypeHandlerInstance(Class<? extends IEnumValue> enumClass) {
        if (TYPE_HANDLER_CACHE.containsKey(enumClass)) {
            return TYPE_HANDLER_CACHE.get(enumClass);
        }

        EnumValueTypeHandler<?> handler = new EnumValueTypeHandler(enumClass);
        TYPE_HANDLER_CACHE.putIfAbsent(enumClass, handler);
        return handler;
    }

    /**
     * scan enum classes that implement the IEnum interface
     */
    protected Set<Class<? extends IEnumValue>> doScanEnumClass(String... enumBasePackages) {
        Set<Class<? extends IEnumValue>> filteredClasses = new HashSet<>();

        ResolverUtil<IEnumValue> resolverUtil = new ResolverUtil<>();
        resolverUtil.findImplementations(IEnumValue.class, enumBasePackages);

        Set<Class<? extends IEnumValue>> handlerSet = new HashSet<>(resolverUtil.getClasses());
        handlerSet.addAll(new JarScanner<IEnumValue>().findImplementations(IEnumValue.class, enumBasePackages));

        for (Class<? extends IEnumValue> type : handlerSet) {
            if (type.isEnum()) {
                filteredClasses.add(type);
            }
        }
        return filteredClasses;
    }

    protected String[] parseEnumBasePackage() {
        return StringUtils.tokenizeToStringArray(this.enumBasePackages, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
    }
}
