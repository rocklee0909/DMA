<template>
  <div class="app-container home">
    <el-row :gutter="20">
      <el-col :sm="24" :lg="12" style="padding-left: 20px">
        <h2>数据监控及分析</h2>
        <p>
          数据监控及分析在当今各个领域都具有至关重要的意义。它不仅能够帮助我们实时了解系统运行状态，还能从大量数据中提取有价值的信息，为决策提供有力支持。以下将从数据监控的方法、数据分析的重要性以及不同领域的应用等方面进行详细阐述。
        </p>
        <p>
          <b>当前版本:</b> <span>v{{ version }}</span>
        </p>

        <p>
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-cloudy"
            plain
            @click="goTarget('https://gitee.com/arthur_lee/dma')"
            >访问码云</el-button
          >
          <el-button
            size="mini"
            icon="el-icon-s-home"
            plain
            @click="goTarget('https://gitee.com/arthur_lee/dma')"
            >访问主页</el-button
          >
        </p>
      </el-col>

    </el-row>
    <el-divider />
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>数据分析的方法与挑战</span>
          </div>
          <div class="body">
            <p>
              数据分析方法：数据分析方法多种多样，如在教育研究中，可以采用创造性的数据分析方法，包括考虑身体在数据分析中的潜在作用、创造性地分析视觉数据、使用基于艺术的技术创造性地分析数据、创造性地分析多模态数据以及分析数据的伦理方面等。在二维液相色谱（2DLC）中，有从预处理平滑算法到几何方法因子分析等多种方法来处理数据，理解这些方法以及原始数据的格式是 2DLC 工作流程的重要组成部分
            </p>
            <p>
              数据分析的挑战：数据分析有时感觉像是研究的 “黑暗艺术”，它常常被人们理解得不好、教授得很少且解释得不够充分。然而，分析是我们作为研究人员工作的核心。在大数据时代，数据的获取已经不是问题，但如何对得到的数据进行分析却千差万别。例如，在超大规模系统中，与当前的云、大数据和高性能计算系统相比，将常规包含数十万个节点并生成数百万个事件，在这种规模下，硬件故障和异常行为不仅更有可能发生，而且是可以预期的。因此，需要描述超大规模监控解决方案的架构以及事件检测组件，以处理大量潜在事件，并指出需要进行的主要缺乏的研究，使事件检测在实际的超大规模系统中可行
            </p>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>数据分析的重要性</span>
          </div>
          <div class="body">
            <p>
              挖掘数据价值：在综合数据中心智能运维监控平台中，通过运维全景监控获取运维态势整体感知，日志智能分析挖掘数据价值，集中配置管理构建智能运维基石，智能运维进行异常识别、故障预测，可视化技术呈现全景运维态势，为整个系统的平稳运行提供保障。
            </p>
            <p>
              优化决策：数据分析可以用适当的统计分析方法对收集来的大量数据进行分析，将它们加以汇总和理解并消化，以求最大化地开发数据的功能，发挥数据的作用。但同样的数据，不同人分析的结果可能大相径庭。
            </p>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>数据监控的方法</span>
          </div>
          <div class="body">
            <p>
              传感器监控：在很多领域，如温室数据监控系统中，采用 AT89C52 单芯片微机作为主要模块，连接 DHT11 温度和湿度传感器，通过 LCD1602 显示，单片机对温度和湿度传感器测量的数据进行处理并显示在屏幕上，同时设置上下限，当超出范围时通过蜂鸣器报警以及红绿灯模拟温湿度上下限空气干燥机的启停和洒水器的开关，工作人员也可根据警报进行手动维护
            </p>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "Index",
  data() {
    return {
      // 版本号
      version: "3.6.5",
    };
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
  },
};
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>

