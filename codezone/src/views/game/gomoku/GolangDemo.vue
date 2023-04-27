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
        "```go\n"  +
        "package main\n\nimport (\n	\"bufio\"\n	\"fmt\"\n	\"math/rand\"\n	\"os\"\n	\"strconv\"\n	\"strings\"\n	\"time\"\n)\n\nfunc main() {\n	/*\n	   一共226个字符\n	    第一个字符表示turnID turnID从1~225 所以 如果你的turnID是奇数 表示你是黑棋 反之是白棋\n	    之后225个字符是棋盘的15 * 15 0表示没有棋子 1是黑棋 2是白棋\n	    下面只是一个随机下的示例 希望对你的输入输出有帮助\n	*/\n\n	// 读入一整行\n	scanner := bufio.NewScanner(os.Stdin)\n	scanner.Scan()\n	line := scanner.Text()\n	// 切片数组\n	a := strings.Split(line, \" \")\n\n	/*\n		如果你的turnID是奇数 表示你是黑棋 反之是白棋\n		turnID, _ := strconv.Atoi(a[0])\n		var myColor int\n		if turnID%2 == 1 {\n			myColor = 1\n		} else {\n			myColor = 2\n		}\n	*/\n\n	// 创建棋盘\n	board, index := make([][]int, 15), 1\n	for i := range board {\n		one := make([]int, 0)\n		for j := 0; j < 15; j++ {\n			num, _ := strconv.Atoi(a[index])\n			index++\n			one = append(one, num)\n		}\n		board[i] = one\n	}\n\n	// 这里只是一个随机下的示例\n	for {\n		// 使用当前时间作为种子\n		rand.Seed(time.Now().UnixNano())\n		x, y := rand.Intn(15), rand.Intn(15)\n		if board[x][y] == 0 {\n			fmt.Println(x, y)\n			os.Exit(0)\n		}\n	}\n}\n"
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