package douyin.controller;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

import douyin.utils.ValidatorUtils;
import douyin.service.StoreupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import douyin.entity.StoreupEntity;
import douyin.entity.view.StoreupView;

import douyin.utils.PageUtils;
import douyin.utils.R;

/**
 * 收藏表
 * 后端接口
 */
@RestController
@RequestMapping("/storeup")
public class StoreupController {
    @Autowired
    private StoreupService storeupService;


    /**
     * 前端列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, StoreupEntity storeup,
                  HttpServletRequest request){
        if (!"管理员".equals(request.getSession().getAttribute("role"))) {
            storeup.setUserid((Long) request.getSession().getAttribute("userId"));
        }

        QueryWrapper<StoreupEntity> wrapper = new QueryWrapper<>(storeup);
        PageUtils page = storeupService.queryPage(params, wrapper);

        return R.ok().put("data", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/lists")
    public R list(StoreupEntity storeup){
        QueryWrapper<StoreupEntity> wrapper = new QueryWrapper<>(storeup);
        return R.ok().put("data", storeupService.selectListView(wrapper));
    }

    /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(StoreupEntity storeup){
        QueryWrapper<StoreupEntity> wrapper = new QueryWrapper<>(storeup);
        StoreupView storeupView = storeupService.selectView(wrapper);
        return R.ok("查询收藏表成功").put("data", storeupView);
    }


    /**
     * 前端详情
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        StoreupEntity storeup = storeupService.getById(id);
        return R.ok().put("data", storeup);
    }
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody StoreupEntity storeup, HttpServletRequest request){
        storeup.setId(new Date().getTime() + (long) (Math.random() * 1000));
        ValidatorUtils.validateEntity(storeup);
        if (!"管理员".equals(request.getSession().getAttribute("role"))) {
            storeup.setUserid((Long) request.getSession().getAttribute("userId"));
        }
        storeupService.save(storeup);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody StoreupEntity storeup){
        ValidatorUtils.validateEntity(storeup);
        storeupService
                .updateById(storeup);//全部更新
        return R.ok();
    }


    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        storeupService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 提醒接口
     */
    @RequestMapping("/remind/{columnName}/{type}")
    public R remindCount(@PathVariable("columnName") String columnName, HttpServletRequest request,
                         @PathVariable("type") String type,@RequestParam Map<String, Object> map) {
        map.put("column", columnName);
        map.put("type", type);

        if("2".equals(type)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            Date remindStartDate = null;
            Date remindEndDate = null;
            if(map.get("remindstart")!=null) {
                Integer remindStart = Integer.parseInt(map.get("remindstart").toString());
                c.setTime(new Date());
                c.add(Calendar.DAY_OF_MONTH,remindStart);
                remindStartDate = c.getTime();
                map.put("remindstart", sdf.format(remindStartDate));
            }
            if(map.get("remindend")!=null) {
                Integer remindEnd = Integer.parseInt(map.get("remindend").toString());
                c.setTime(new Date());
                c.add(Calendar.DAY_OF_MONTH,remindEnd);
                remindEndDate = c.getTime();
                map.put("remindend", sdf.format(remindEndDate));
            }
        }

        QueryWrapper<StoreupEntity> wrapper = new QueryWrapper<>();
        if(map.get("remindstart")!=null) {
            wrapper.ge(columnName, map.get("remindstart"));
        }
        if(map.get("remindend")!=null) {
            wrapper.le(columnName, map.get("remindend"));
        }
        if (!"管理员".equals(request.getSession().getAttribute("role"))) {
            wrapper.eq("userid", (Long)request.getSession().getAttribute("userId"));
        }

        long count = storeupService.count(wrapper);
        return R.ok().put("count", count);
    }
}
