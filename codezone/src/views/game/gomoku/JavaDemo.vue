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
        "```java\n"  +
        "import java.util.Random;\n" +
        "import java.util.Scanner;\n" +
        "\n" +
        "public class Main {\n" +
        "    public static void main(String[] args) {\n" +
        "        /*\n" +
        "        \t   一共226个字符\n" +
        "                第一个字符表示turnID turnID从1~225 所以 如果你的turnID是奇数 表示你是黑棋 反之是白棋\n" +
        "                之后225个字符是棋盘的15 * 15 0表示没有棋子 1是黑棋 2是白棋\n" +
        "                下面只是一个随机下的示例 希望对你的输入输出有帮助\n" +
        "         */\n" +
        "        // 读入一整行\n" +
        "        Scanner sc = new Scanner(System.in);\n" +
        "        // 按空格切片\n" +
        "        String[] a = sc.nextLine().split(\" \");\n" +
        "        // 如果你的turnID是奇数 表示你是黑棋 反之是白棋\n" +
        "        int turnID = Integer.parseInt(a[0]), idx = 1;\n" +
        "\n" +
        "        int[][] board = new int[15][15];\n" +
        "        for (int i = 0; i < 15; i++) {\n" +
        "            for (int j = 0; j < 15; j++) {\n" +
        "                board[i][j] = Integer.parseInt(a[idx++]);\n" +
        "            }\n" +
        "        }\n" +
        "\n" +
        "        Random random = new Random();\n" +
        "        while (true) {\n" +
        "            int x = random.nextInt(15), y = random.nextInt(15);\n" +
        "            if (board[x][y] == 0) {\n" +
        "                System.out.println(x + \" \" + y);\n" +
        "                return;\n" +
        "            }\n" +
        "        }\n" +
        "    }\n" +
        "}"
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