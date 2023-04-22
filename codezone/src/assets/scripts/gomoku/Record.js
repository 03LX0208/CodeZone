export class Record {
    constructor(ctx, steps, callback) {
        this.ctx = ctx; // canvas画板
        this.width = 620;   // 棋盘宽 不是正方形是因为 有些透明像素..
        this.height = 570;  // 棋盘高
        this.spaceX = 36;   // 棋子x轴跨度
        this.spaceY = 36;   // 棋子y轴跨度
        this.canvas = this.ctx.canvas;
        this.canvas.width = this.width;     // 画板宽
        this.canvas.height = this.height;   // 画板高

        this.loadImages();
        this.steps = steps.split(" ");
        this.show(callback);
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

    show(callback) {
        let promises = [];

        this.backgroundImage.onload = () => {
            this.ctx.drawImage(this.backgroundImage, 0, 0, this.width, this.height);
            let n = this.steps.length;
            let color = true; // true为黑 false为白
            for (let i = 0; i < n; i += 2) {
                let x = Number(this.steps[i]), y = Number(this.steps[i + 1]);
                let promise = new Promise((resolve) => {
                    if (color) {
                        setTimeout(() => {
                            this.drawBlackPiece(x, y);
                            resolve();
                        }, 500 * (i + 1)/2);
                    } else {
                        setTimeout(() => {
                            this.drawWhitePiece(x, y);
                            resolve();
                        }, 500 * (i + 1)/2);
                    }
                });
                promises.push(promise);
                color = !color;
            }
            Promise.all(promises).then(() => {
                callback(); // 所有任务完成后调用回调函数
            });
        }
    }

    drawBlackPiece(x, y) {
        let nx = 58 + x * this.spaceX - 19;
        let ny = 28.8 + y * this.spaceY - 19;
        this.ctx.drawImage(this.blackImage, nx, ny, 42, 42);
    }

    drawWhitePiece(x, y) {
        let nx = 58 + x * this.spaceX - 19;
        let ny = 28.8 + y * this.spaceY - 19;
        this.ctx.drawImage(this.whiteImage, nx, ny, 42, 42);
    }
}
