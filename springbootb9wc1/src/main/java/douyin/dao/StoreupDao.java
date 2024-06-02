package douyin.dao;

import douyin.entity.StoreupEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import douyin.entity.vo.StoreupVO;
import douyin.entity.view.StoreupView;
import org.springframework.stereotype.Repository;

/**
 * 收藏表
 */
@Repository
public interface StoreupDao extends BaseMapper<StoreupEntity> {

	List<StoreupVO> selectListVO(@Param("ew") Wrapper<StoreupEntity> wrapper);

	StoreupVO selectVO(@Param("ew") Wrapper<StoreupEntity> wrapper);

	List<StoreupView> selectListView(@Param("ew") Wrapper<StoreupEntity> wrapper);

	List<StoreupView> selectListView(Page<StoreupView> page, @Param("ew") Wrapper<StoreupEntity> wrapper);

	StoreupView selectView(@Param("ew") Wrapper<StoreupEntity> wrapper);
}
