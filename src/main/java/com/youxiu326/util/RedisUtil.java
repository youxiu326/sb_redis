package com.youxiu326.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Redis工具类
 * </p>
 *
 * 根据key的格式分页获取Redis存在的key列表
 */
@Component
public class RedisUtil {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static Logger log = LoggerFactory.getLogger(RedisUtil.class);

    /**
     * 分页获取指定格式key，使用 scan 命令代替 keys 命令，在大数据量的情况下可以提高查询效率
     *
     * @param patternKey  key格式
     * @param currentPage 当前页码
     * @param pageSize    每页条数
     * @return 分页获取指定格式key
     */
    public PageResult<String> findKeysForPage(String patternKey, int currentPage, int pageSize) {
        ScanOptions options = ScanOptions.scanOptions()
                .match(patternKey)
                .build();
        RedisConnectionFactory factory = stringRedisTemplate.getConnectionFactory();
        RedisConnection rc = factory.getConnection();
        Cursor<byte[]> cursor = rc.scan(options);

        List<String> result = new ArrayList<>();

        long tmpIndex = 0;
        int startIndex = (currentPage - 1) * pageSize;
        int end = currentPage * pageSize;
        while (cursor.hasNext()) {
            String key = new String(cursor.next());
            if (tmpIndex >= startIndex && tmpIndex < end) {
                result.add(key);
            }
            tmpIndex++;
        }

        try {
            cursor.close();
            RedisConnectionUtils.releaseConnection(rc, factory);
        } catch (Exception e) {
            log.warn("Redis连接关闭异常，", e);
        }

        return new PageResult<>(result, tmpIndex);
    }
}