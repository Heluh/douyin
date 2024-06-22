<template>
    <div class="comment-section">
        <h2 style="text-align: center">评论区</h2>
        <!-- 只有在用户登录时才显示评论输入框 -->
        <van-field
            v-if="isUserLoggedIn"
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
                <van-button v-if="comment.username === currentUser && isUserLoggedIn" @click="deleteComment(comment.id)" type="danger" size="mini">删除</van-button>
            </div>
        </div>
        <!-- 如果未登录，显示提示登录信息 -->
        <div v-if="!isUserLoggedIn" class="login-prompt">
            您需要<a href="#" @click="promptLogin">登录</a>才能发表评论。
        </div>
    </div>
</template>

<script>
import { Field, Button } from 'vant';

export default {
    name: 'CommentSection',
    components: {
        vanField: Field,
        vanButton: Button
    },
    data() {
        return {
            commentContent: '',
            comments: [
                { id: 1, username: 'zjt', content: '这是一条评论！' },
                { id: 2, username: 'lzq', content: '很棒的视频！' }
            ],
            currentUser: localStorage.getItem('curUser') || '',
            isUserLoggedIn: !!localStorage.getItem('token')
        };
    },
    methods: {
        submitComment() {
            if (!this.commentContent.trim()) return;
            const newComment = {
                id: this.comments.length + 1,
                username: this.currentUser,
                content: this.commentContent,
            };
            this.comments.push(newComment);
            this.commentContent = ''; // 清空输入框
        },
        deleteComment(commentId) {
            this.comments = this.comments.filter(comment => comment.id !== commentId);
        },
        promptLogin() {
            this.$emit('login-required'); // 触发登录要求事件
        }
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
.login-prompt {
    text-align: center;
    margin-top: 10px;
}
</style>
