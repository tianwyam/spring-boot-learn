$(function() {

    var websocket;


    // 首先判断是否 支持 WebSocket
    if('WebSocket' in window) {
        websocket = new WebSocket("ws://localhost:8080/ws/chat");
    } else if('MozWebSocket' in window) {
        websocket = new MozWebSocket("ws://localhost:8080/ws/chat");
    } else {
        websocket = new SockJS("http://localhost:8080/ws/chat");
    }

    // 打开时
    websocket.onopen = function(event) {
        console.log("  websocket.onopen  ");
        console.log(event);
        $("#msg").append("<p>(<font color='blue'>欢迎加入聊天群</font>)</p>");
    };


    // 处理消息时
    websocket.onmessage = function(event) {
    	console.log(event);
        $("#msg").append("<p>(<font color='red'>" + event.data + "</font>)</p>");
        console.log("  websocket.onmessage   ");
    };


    websocket.onerror = function(event) {
        console.log("  websocket.onerror  ");
    };

    websocket.onclose = function(event) {
        console.log("  websocket.onclose  ");
    };


    // 点击了发送消息按钮的响应事件
    $("#TXBTN").click(function(){

        // 获取消息内容
        var text = $("#tx").val();

        // 判断
        if(text == null || text == ""){
            alert(" content  can not empty!!");
            return false;
        }

//        var msg = {
//            msgContent: text,
//            postsId: 1,
//            sendTime: new Date()
//        };

        // 发送消息
        websocket.send(text);
        $("#tx").val('');
    });


});
