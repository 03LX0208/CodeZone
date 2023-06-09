export class GameMap {
    constructor(ctx, parent, store) {
        this.store = store;

        this.ctx = ctx; // canvas画板
        this.parent = parent;   // 父元素句柄 为了确认坐标

        this.width = 620;   // 棋盘宽 不是正方形是因为 有些透明像素..
        this.height = 570;  // 棋盘高
        this.spaceX = 36;   // 棋子x轴跨度
        this.spaceY = 36;   // 棋子y轴跨度
        this.eps = 12;      // 玩家下棋时允许的误差

        this.canvas = this.ctx.canvas;
        this.canvas.width = this.width;     // 画板宽
        this.canvas.height = this.height;   // 画板高

        this.loadImages();  // 把背景图片 棋子图片 加载好

        this.turnID = 1;    // 当前是第几回合 从1开始 最多到225
        this.pieces = [];   // 已经下过的棋，装到列表里
        this.board = new Array(15); // 初始化 15 15 棋盘 1是白棋 2是黑棋
        for (let i = 0; i < 15; i++) this.board[i] = new Array(15);
        for (let i = 0; i < 15; i++) {
            for (let j = 0; j < 15; j++) this.board[i][j] = 0;
        }

        this.run();
    }

    loadImages() {
        // 棋盘
        this.backgroundImage = new Image();
        this.backgroundImage.src = "https://codezone-1313033191.cos.ap-beijing.myqcloud.com/game/gomoku/board.png";

        // 黑棋
        this.blackImage = new Image();
        this.blackImage.src = "https://codezone-1313033191.cos.ap-beijing.myqcloud.com/game/gomoku/black.png";

        // 白棋
        this.whiteImage = new Image();
        this.whiteImage.src = "https://codezone-1313033191.cos.ap-beijing.myqcloud.com/game/gomoku/white.png";

        // 棋子醒目边框
        this.paneImage = new Image();
        this.paneImage.src = "https://codezone-1313033191.cos.ap-beijing.myqcloud.com/game/gomoku/pane.png";
    }

    // 通过外部调用 运行游戏
    run() {
        this.backgroundImage.onload = () => {
            this.showBackgroundImage();
            this.canvas.addEventListener("click", (event) => {
                const rect = this.canvas.getBoundingClientRect();
                // 获得相对于canvas左上角的lx ty
                const lx = event.clientX - rect.left;
                const ty = event.clientY - rect.top;
                if (this.store.state.gomoku.my_turn === true) {
                    const [x, y] = this.getClickPoint(lx, ty);
                    if (x >= 0 && x <= 14 && y >= 0 && y <= 14) {
                        this.store.state.gomoku.socket.send(JSON.stringify({
                            event: "move",
                            x,
                            y
                        }));
                    }
                }
            });
        }
    }

    showBackgroundImage() {
        this.ctx.drawImage(this.backgroundImage, 0, 0, this.width, this.height);
    }

    // 获得棋盘格位置
    getClickPoint(lx, ty) {
        let x = lx - 58, y = ty - 28.8; // 相对棋盘左上角
        let row = Math.round(x / this.spaceX), col = Math.round(y / this.spaceY);
        if (Object.is(row, -0)) row = 0;
        if (Object.is(col, -0)) col = 0;
        if (Math.abs(x - row * this.spaceX) <= this.eps && Math.abs(y - col * this.spaceY) <= this.eps) {
            return [Number(row), Number(col)];
        } else return [Number(-1), Number(-1)]; // 不合法就返回两个-1;
    }

    // 画一颗棋子
    showOnePiece(x, y, color) {
        let nx = 58 + x * this.spaceX - 19;
        let ny = 28.8 + y * this.spaceY - 19;
        if (color % 2 === 1) {
            this.ctx.drawImage(this.blackImage, nx, ny, 42, 42);
        } else this.ctx.drawImage(this.whiteImage, nx, ny, 42, 42);
    }
}