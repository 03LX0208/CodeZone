<template>
  <div class="content">
    <NavBar/>
    <div class="container">
      <div class="row">
        <div class="col-2"></div>
        <div class="col-8">
          <md-editor
              :previewOnly="true"
              v-model="code"
          />
        </div>
        <div class="col-2"></div>
      </div>
    </div>
  </div>
</template>

<script>
import NavBar from "@/components/NavBar";
import MdEditor from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';

export default {
  components: {
    MdEditor,
    NavBar,
  },
  setup() {
    let code =
        "```python\n"  +
        "\"\"\"\n" +
        "    一共226个字符\n" +
        "    第一个字符表示turnID turnID从1~225 所以 如果你的turnID是奇数 表示你是黑棋 反之是白棋\n" +
        "    之后225个字符是棋盘的15 * 15 0表示没有棋子 1是黑棋 2是白棋\n" +
        "    下面只是一个随机下的示例 希望对你的输入输出有帮助\n" +
        "\"\"\"\n" +
        "import random\n" +
        "\n" +
        "a = input().split()  # len(_)一定为226\n" +
        "turnID, index = int(a[0]), 1\n" +
        "myColor = 1 if turnID & 1 else 2  # 如果你的turnID是奇数 表示你是黑棋 反之是白棋\n" +
        "\n" +
        "board = [[0] * 15 for _ in range(15)]  # 初始化15*15棋盘\n" +
        "for i in range(15):\n" +
        "    for j in range(15):\n" +
        "        board[i][j] = int(a[index])\n" +
        "        index += 1\n" +
        "\n" +
        "# 随机下的一个示例\n" +
        "while True:\n" +
        "    x, y = random.randint(0, 14), random.randint(0, 14)\n" +
        "    if not board[x][y]:\n" +
        "        print(x, y)  # 输出格式一定是x y，中间一个空格，不要有多余输出\n" +
        "        exit(0)\n"
        +"\n```";

    return {
      code,
    }
  }
}
</script>

<style scoped>
.content {
  min-height: 100vh;
  min-width: 100vh;
  background-color: rgb(247,248,250);
}
</style>