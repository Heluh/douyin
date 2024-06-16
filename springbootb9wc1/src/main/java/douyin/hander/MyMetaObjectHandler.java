package douyin.hander;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;

public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createdAt", Timestamp.from(Instant.now()), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 如果有需要填充更新时间戳字段，可以在这里实现
    }
}
