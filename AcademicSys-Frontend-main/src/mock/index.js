// 使用mockjs模拟后端发回的数据，用于调试前端
import Mock from 'mockjs'
Mock.mock('http://localhost:8088/user/Navigation/HomePage', {
    "ret":0,
    "data|4":
        [{
            "mtime": "@datetime",//随机生成日期时间
            "score|1-800": 1,//随机生成1-800的数字
            "rank|1-100": 1,//随机生成1-100的数字
            "stars|1-5": 1,//随机生成1-5的数字
            "nickname": "@cname",//随机生成中文名字
            //生成图片
            "img":"@image('200x100', '#ffcc33', '#FFF', 'png', 'Fast Mock')",
            "number":"@natural(1, 100)",
            "username":"@cname",
            "password":"@string(6, 10)"
        }]
});
