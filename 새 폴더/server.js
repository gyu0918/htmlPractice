const express = require("express");
const http = require("http");
const { Server } = require("socket.io");

const app = express();
const server = http.createServer(app);
const io = new Server(server);
let list=[];
app.use(express.static("public"));

io.on("connection", (socket) => {
  console.log("A user connected");

  // socket.on("chat message", (msg) => {
  //   io.emit("chat message", msg); // 모든 클라이언트에게 메시지 전송
  // });

  socket.on("chat message", ({ room, message }) => {
    io.to(room).emit("chat message", {
      from: socket.id,
      message,
    });
  });

  socket.on("disconnect", () => {
    console.log("User disconnected");
  });

  socket.on("qqqqq", (to,jsonData) => {
    let memberData = JSON.parse(jsonData);
    socket.join(memberData.room);
    list.push(memberData)
    console.log(memberData);
    let aaaa=memberData.room

    io.to(to).emit("qqqqq", {
      aaaa,
    });
  });
});

const PORT = 3000;
server.listen(PORT, () => {
  console.log(`Server listening on http://localhost:${PORT}`);
});

app.get('/aaa',(e,ee)=>{
  ee.sendFile(__dirname+'/index.html')
});

