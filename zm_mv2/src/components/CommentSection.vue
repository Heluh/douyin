<template>
    <div class="comment-section">
        <h2 style="text-align: center">评论区</h2>
        <van-field
            v-model="commentContent"
            placeholder="发表评论..."
            right-icon="checked"
            @click-right-icon="submitComment"
            class="comment-input"
            style="margin-left: 5px; margin-right: 15px;"
        />
        <div class="comment-list">
            <div v-if="comments.length === 0" class="no-comments">
                暂无评论
            </div>
            <div v-for="comment in comments" :key="comment.id" class="comment-item">
                <div class="comment-user">@{{ comment.username }}</div>
                <div class="comment-content">{{ comment.content }}</div>
            </div>
        </div>
    </div>
</template>

<script>
import { Field, Icon } from 'vant';

export default {
    name: 'CommentSection',
    components: {
        vanField: Field,
        vanIcon: Icon
    },
    data() {
        return {
            commentContent: '',
            comments: [
                { id: 1, username: '用户1', content: '这是一条评论！' },
                { id: 2, username: '用户2', content: '很棒的视频！' }
            ],
        };
    },
    methods: {
        submitComment() {
            if (!this.commentContent.trim()) return;
            const newComment = {
                id: this.comments.length + 1,
                username: '新用户',
                content: this.commentContent,
            };
            this.comments.push(newComment);
            this.commentContent = ''; // 清空输入框
        },
    },
};
</script>

<style scoped>
.comment-section {
    padding: 10px;
    height: calc(100% - 50px); /* 减去输入框的高度 */
    background-color: transparent;
}
.comment-input {
    margin-bottom: 5px;
}
.comment-list {
    overflow-y: auto; /* 允许纵向滚动 */
    max-height: 100%; /* 设置最大高度为父容器高度 */
}
.no-comments {
    text-align: center;
    color: #999;
}
.comment-item {
    border-bottom: 1px solid #eee;
    padding: 10px 0;
}
.comment-user {
    font-weight: bold;
}
.comment-content {
    margin-top: 5px;
}
</style>
