const express = require("express");
const http = require("http");
const { Server } = require("socket.io");

const app = express();
const server = http.createServer(app);
const io = new Server(server);

let roomList = [];

//소켓 연결 처음할떄 이 폴더 이름은 무조건 index.html 기준으로 화면뿌린다 처음에
//app.use(express.static("public"));

//index.html로 뿌리기 싫으면 이렇게 바꾸면된다.
app.use("/", express.static("public", { index: "init.html" }));

io.on("connection", (socket) => {
  console.log("A user connected");
  console.log(socket.id);
  // socket.on("chat message", (msg) => {
  //   io.emit("chat message", msg); // 모든 클라이언트에게 메시지 전송
  // });

  // socket.on("chat message", ({ room, message }) => {
  //   io.to(room).emit("chat message", {
  //     from: socket.id,
  //     message,
  //   });
  // });

  //로그인
  socket.on("login", (data) => {
    console.log(`[로그인] 소켓ID: ${socket.id}, 닉네임: ${data.name}`);
    // 필요한 처리를 해줘도 됨 (예: 대기방 배열에 추가 등)
  });

  //대기방리스트를 보내는 기능(json으로 받을지는 아직 미정)
  socket.on("roomList", () => {
    //모든 클라이언트에게 방 리스트 전송
    io.emit("roomList", roomList);
  });
  // 방 참가
  socket.on("joinRoom", ({ username, roomNum }) => {
    // roomList.push(roomNum);
    socket.join(roomNum);
    // console.log(`${username} joined room ${room}`);

    // 방에 있는 사람들에게 알림
    socket.to(roomNum).emit("msg", {
      user: "System",
      text: `${username} has joined the room.`,
    });

    // 개인 환영 메시지
    socket.emit("messages", {
      user: "System",
      text: `Welcome to room ${roomNum}, ${username}!`,
    });

    // 메시지 처리 (room안에 있는 소켓들에게 메시지 던지기 )
    socket.on("chatMessage", (message) => {
      io.to(roomNum).emit("chatMessage", {
        from: username,
        message,
      });
    });

    socket.on("leaveRoom", ({ username, roomNum }) => {
      socket.leave(roomNum);
      // 클라이언트에 나갔다고 알림
      socket.emit("leaveRoom");

      // 방에 남은 유저 수 확인
      const room = io.sockets.adapter.rooms.get(roomNum);
      if (!room || room.size === 0) {
        // 방이 비었으면 리스트에서 제거
        roomList = roomList.filter((r) => r !== roomNum);
        io.emit("roomList", roomList);
      }

      // 방에 있는 다른 유저에게 알림
      socket.to(roomNum).emit("msg", {
        user: "System",
        text: `${username} has left the room.`,
      });
    });

    //소켓연결 종료
    socket.on("disconnect", () => {
      console.log("User disconnected");
    });

    // socket.on("qqqqq", (to,jsonData) => {
    //   let memberData = JSON.parse(jsonData);
    //   socket.join(memberData.room);
    //   list.push(memberData)
    //   console.log(memberData);
    //   let aaaa=memberData.room

    //   io.to(to).emit("qqqqq", {
    //     aaaa,
    //   });
    // });
  });

  // 새로운 방 생성 요청
  socket.on("createRoom", (roomName) => {
    if (roomList.includes(roomName)) {
      // 중복 방 이름 거부할 수도 있고, 그냥 무시해도 됩니다.
      return socket.emit("message", {
        user: "System",
        text: "이미 있는 방입니다.",
      });
    }
    roomList.push(roomName);

    // 모든 클라이언트에 새로운 방 목록 뿌려줌
    io.emit("roomList", roomList);
  });
});

//서버 유지
const PORT = 3000;
server.listen(PORT, () => {
  // console.log(`Server listening on http://localhost:${PORT}`);
});

// get요청으로 (/chat) 으로 들어오면 chat.html을 던져준다.
app.get("/chat", (e, ee) => {
  ee.sendFile(__dirname + "/chat.html");
});

// get 요청으로 (/init) 으로 들어오면 init.html을 던져준다.
app.get("/stay", (e, ee) => {
  ee.sendFile(__dirname + "/public/stay.html");
});
