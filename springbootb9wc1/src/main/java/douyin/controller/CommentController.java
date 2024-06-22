package douyin.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import douyin.annotation.IgnoreAuth;
import douyin.entity.view.CommentView;
import douyin.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import douyin.entity.CommentEntity;

import douyin.utils.PageUtils;
import douyin.utils.R;
import douyin.utils.MPUtil;

/**
 * 视频信息评论表
 * 后端接口
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 列表
     */
    @IgnoreAuth
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params,
                  CommentEntity comment,
                  @RequestParam Long videoId) {
        QueryWrapper<CommentEntity> qw = new QueryWrapper<>();
        qw.eq("refid", videoId);
        PageUtils page = commentService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(qw, comment), params), params));
        return R.ok().put("data", page);
    }

    /**
     * 返回评论数量
     */
    @IgnoreAuth
    @GetMapping("/count")
    public R count(@RequestParam Long videoId) {
        QueryWrapper<CommentEntity> qw = new QueryWrapper<>();
        qw.eq("refid", videoId);
        List<CommentEntity> list = commentService.list(qw);
        return R.ok().put("data", list.size());
    }

    /**
     * 列表
     */
    @RequestMapping("/lists")
    public R list(CommentEntity comment) {
        QueryWrapper<CommentEntity> qw = new QueryWrapper<>();
        qw.allEq(MPUtil.allEQMapPre(comment, "comment"));
        return R.ok().put("data", commentService.selectListView(qw));
    }

    /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(CommentEntity comment) {
        QueryWrapper<CommentEntity> qw = new QueryWrapper<>();
        qw.allEq(MPUtil.allEQMapPre(comment, "comment"));
        CommentView commentView = commentService.selectView(qw);
        return R.ok("查询视频信息评论表成功").put("data", commentView);
    }

    /**
     * 详情
     */
    @IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id) {
        CommentEntity comment = commentService.getById(id);
        return R.ok().put("data", comment);
    }


    /**
     * 保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody CommentEntity comment, HttpServletRequest request) {
        comment.setId(System.currentTimeMillis() + (long) (Math.random() * 1000));
        commentService.save(comment);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody CommentEntity comment, HttpServletRequest request) {
        commentService.updateById(comment);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        commentService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
