console.log("hello world");

let webSocket = new WebSocket("ws://localhost");

webSocket.onmessage = function (event) {
    console.log(event.data);
}
//webSocket.send("Here is some dumb fucking text.");