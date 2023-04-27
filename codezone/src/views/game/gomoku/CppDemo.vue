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
        "```c++\n"  +
        "#include <iostream>\n" +
        "#include <array>\n" +
        "#include <vector>\n" +
        "#include <sstream>\n" +
        "#include <random>\n" +
        "\n" +
        "enum {\n" +
        "    BLACK = 1,\n" +
        "    WHITE = 2\n" +
        "};\n" +
        "\n" +
        "std::string solve(const std::array<std::array<int, 15>, 15>& board, unsigned MyColor) {\n" +
        "    // 生成随机数\n" +
        "    std::mt19937 rnd(std::random_device{}());\n" +
        "    while (true) {\n" +
        "        unsigned x = rnd() % 15, y = rnd() % 15;\n" +
        "        if (board[x][y]) continue;\n" +
        "        return std::to_string(x) + \" \" + std::to_string(y);\n" +
        "    }\n" +
        "}\n" +
        "\n" +
        "int main() {\n" +
        "    // 读入输入\n" +
        "    std::string _s;\n" +
        "    std::getline(std::cin, _s);\n" +
        "\n" +
        "    std::vector<int> _;     // 切片数组\n" +
        "    // 按空格切片\n" +
        "    std::stringstream _ss(_s);\n" +
        "    while (_ss >> _s) _.push_back(std::stoi(_s));\n" +
        "\n" +
        "    /*\n" +
        "     * _[0]是turnID(turnID从1~225)  _[1~225]是棋盘棋子 0是没有 1是黑棋 2是白棋\n" +
        "     * 所以如果turnID是奇数 表示你是黑棋 turnID是偶数 表示你是白棋\n" +
        "     */\n" +
        "    unsigned MyColor = (_[0] & 1 ? BLACK : WHITE), index = 1;\n" +
        "    // 读入棋盘\n" +
        "    std::array<std::array<int, 15>, 15> board{};\n" +
        "    for (int i = 0; i < 15; i++) {\n" +
        "        for (int j = 0; j < 15; j++) {\n" +
        "            board[i][j] = _[index++];\n" +
        "        }\n" +
        "    }\n" +
        "\n" +
        "    // 下面实现你的逻辑 这里只是一个随机下的示例\n" +
        "    auto res = solve(board, MyColor);\n" +
        "\n" +
        "    // 输出结果 注意 一定是 \"x y\" 的格式哦 不要输出多余的东西\n" +
        "    std::cout << res;\n" +
        "\n" +
        "    return 0;\n" +
        "}\n" + "```";

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