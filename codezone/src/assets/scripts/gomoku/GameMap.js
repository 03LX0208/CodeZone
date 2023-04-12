export class GameMap {
    constructor(ctx, parent) {
        this.ctx = ctx; // canvas画板
        this.parent = parent;   // 父元素句柄 为了确认坐标

        this.width = 620;   // 棋盘宽 不是正方形是因为 有些透明像素..
        this.height = 570;  // 棋盘高
        this.spaceX = 36;   // 棋子x轴跨度
        this.spaceX = 36;   // 棋子y轴跨度

        this.canvas = this.ctx.canvas;
        this.canvas.width = this.width;     // 画板宽
        this.canvas.height = this.height;   // 画板高

        this.loadImages();  // 把背景图片 棋子图片 加载好
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
        }
    }

    showBackgroundImage() {
        this.ctx.drawImage(this.backgroundImage, 0, 0, this.width, this.height);
    }
}