<template>
  <div class="app-container">
    <div class="css-mcvwy6">
      <!--top tool-->
      <div class="css-16hl5pe">
        <el-row :gutter="20">
          <el-col :span="18">表格看板</el-col>
          <el-col :span="6" style="text-align: right;padding-right: 20px;">
            <el-dropdown split-button type="primary" @click="handleRefresh">
              刷新
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>AUTO</el-dropdown-item>
                <el-dropdown-item>1S</el-dropdown-item>
                <el-dropdown-item>5S</el-dropdown-item>
                <el-dropdown-item>10S</el-dropdown-item>
                <el-dropdown-item>30S</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </el-col>
        </el-row>
      </div>
      <!--预览内容-->
      <div class="css-content">
        <splitpanes :horizontal="true" class="default-theme" style="height: 1000px;">
          <pane size="30">
            <el-table :data="tableData" style="width: 100%" :default-sort="{ prop: 'date', order: 'descending' }">
              <el-table-column prop="date" label="日期" sortable width="180">
              </el-table-column>
              <el-table-column prop="name" label="姓名" sortable width="180">
              </el-table-column>
              <el-table-column prop="address" label="地址" :formatter="formatter">
              </el-table-column>
            </el-table>
          </pane>
          <pane size="70">
            <el-tabs type="border-card">
              <el-tab-pane>
                <span slot="label"><i class="el-icon-date"></i> 查询</span>
                
                <el-collapse v-model="activeNames" @change="handleChange">
                  <el-collapse-item title="查询1" name="1">
                    <el-form ref="form1" :model="selectForm" label-width="180px">
                      <el-form-item label="编辑器类型">
                        <el-button-group>
                          <el-button type="primary" @click="editorType='SQLEditor'">SQL查询器</el-button>
                          <el-button type="primary" @click="editorType='SELECTEditor'">查询编辑器</el-button>
                        </el-button-group>
                      </el-form-item>
                      <!--SQL编辑器-->
                      <div v-show="editorType=='SQLEditor'">
                        <el-input
                          type="textarea"
                          placeholder="请输入内容"
                          v-model="textarea"
                          maxlength="30"
                          show-word-limit
                        >
                        </el-input>
                      </div>
                      <!--查询编辑器-->
                      <div v-show="editorType=='SELECTEditor'">
                        <el-row>
                          <el-col :span="12">
                            <el-form-item label="数据库">
                              <el-select v-model="value" placeholder="请选择Database">
                                <el-option
                                  v-for="item in databaseOptions"
                                  :key="item.value"
                                  :label="item.label"
                                  :value="item.value">
                                </el-option>
                              </el-select>
                            </el-form-item>
                          </el-col>
                          <el-col :span="12">
                            <el-form-item label="表名">
                              <el-select v-model="value" placeholder="请选择Table">
                                <el-option
                                  v-for="item in databaseOptions"
                                  :key="item.value"
                                  :label="item.label"
                                  :value="item.value">
                                </el-option>
                              </el-select>
                            </el-form-item>
                          </el-col>
                        </el-row>

                        <el-form-item label="构建模式">
                          <el-button-group>
                            <el-button type="primary" @click="builderMode='Simple'">简单查询</el-button>
                            <el-button type="primary" @click="builderMode='Aggregate'">统计函数</el-button>
                          </el-button-group>
                        </el-form-item>

                        <el-form-item label="查询列">
                          <el-select v-model="value1" multiple placeholder="请选择需要查询的列">
                            <el-option
                              v-for="item in columnsOptions"
                              :key="item.value"
                              :label="item.label"
                              :value="item.value">
                            </el-option>
                          </el-select>
                        </el-form-item>

                        <el-form-item label="排序">
                          <el-select v-model="value1" multiple placeholder="请选择需要查询的列">
                            <el-option
                              v-for="item in columnsOptions"
                              :key="item.value"
                              :label="item.label"
                              :value="item.value">
                            </el-option>
                          </el-select>
                        </el-form-item>

                        <el-form-item label="Limit">
                          <el-select v-model="value1" multiple placeholder="请选择需要查询的列">
                            <el-option
                              v-for="item in columnsOptions"
                              :key="item.value"
                              :label="item.label"
                              :value="item.value">
                            </el-option>
                          </el-select>
                        </el-form-item>

                        <el-form-item label="Filters">
                          <el-select v-model="value1" multiple placeholder="请选择需要查询的列">
                            <el-option
                              v-for="item in columnsOptions"
                              :key="item.value"
                              :label="item.label"
                              :value="item.value">
                            </el-option>
                          </el-select>
                        </el-form-item>

                        <el-form-item label="SQL预览">
                          <el-select v-model="value1" multiple placeholder="请选择需要查询的列">
                            <el-option
                              v-for="item in columnsOptions"
                              :key="item.value"
                              :label="item.label"
                              :value="item.value">
                            </el-option>
                          </el-select>
                        </el-form-item>

                      </div>
                    </el-form>
                  </el-collapse-item>
                </el-collapse>

              </el-tab-pane>
              <el-tab-pane label="数据转换">数据转换</el-tab-pane>
            </el-tabs>
          </pane>
        </splitpanes>


      </div>
    </div>
  </div>
</template>

<script>
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { Splitpanes, Pane } from "splitpanes";
import "splitpanes/dist/splitpanes.css";

export default {
  name: "Explore",
  components: { Treeselect, Splitpanes, Pane },
  data() {
    return {
      // 表单参数
      selectForm: {},
      // SQL编辑器类型 SQLEditor / SELECTEditor
      editorType: 'SELECTEditor', 
      builderMode: 'Simple',

      value1: '',
      columnsOptions: [{
          value: '选项1',
          label: '黄金糕'
        }, {
          value: '选项2',
          label: '双皮奶'
        }, {
          value: '选项3',
          label: '蚵仔煎'
        }, {
          value: '选项4',
          label: '龙须面'
        }, {
          value: '选项5',
          label: '北京烤鸭'
        }],
      value: '',
      databaseOptions: [{
          value: '选项1',
          label: '黄金糕'
        }, {
          value: '选项2',
          label: '双皮奶'
        }, {
          value: '选项3',
          label: '蚵仔煎'
        }, {
          value: '选项4',
          label: '龙须面'
        }, {
          value: '选项5',
          label: '北京烤鸭'
        }],

      textarea: "",
      tableData: [{
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-04',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1517 弄'
      }, {
        date: '2016-05-01',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1519 弄'
      }, {
        date: '2016-05-03',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1516 弄'
      }],
      activeNames: ['1']
    };
  },
  watch: {

  },
  created() {

  },
  methods: {
    handleSelect() {

    },
    handleRefresh() {
      this.handleSelect()
    },
    formatter(row, column) {
      return row.address;
    },
    handleChange(val) {
      console.log(val);
    }
  }
};
</script>

<style scoped>
.app-container {
  flex-grow: 0.75;
  min-width: min-content;
  position: relative;
  flex-basis: 0px;
  -webkit-box-flex: 1;
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  min-height: 0px;
}

.css-mcvwy6 {
  -webkit-tap-highlight-color: rgba(0, 0, 0, 0);
  font-kerning: normal;
  color-scheme: light;
  color: rgb(36, 41, 46);
  font-family: Inter, Helvetica, Arial, sans-serif;
  font-weight: 400;
  font-size: 1rem;
  line-height: 1.57143;
  letter-spacing: 0.01071em;
  box-sizing: inherit;
  display: grid;
  grid-template: "controls" "panels" 1fr / 1fr;
  height: 100%;
}

.css-16hl5pe {
  -webkit-tap-highlight-color: rgba(0, 0, 0, 0);
  font-kerning: normal;
  color-scheme: light;
  color: rgb(36, 41, 46);
  font-family: Inter, Helvetica, Arial, sans-serif;
  font-weight: 400;
  font-size: 1rem;
  line-height: 1.57143;
  letter-spacing: 0.01071em;
  box-sizing: inherit;
  display: flex;
  flex-direction: column;
  -webkit-box-flex: 0;
  flex-grow: 0;
  grid-area: controls;
  padding: 16px 0px 16px 16px;
  background-color: rgb(244, 245, 245);
}

.css-content {
  -webkit-tap-highlight-color: rgba(0, 0, 0, 0);
  font-kerning: normal;
  color-scheme: light;
  color: rgb(36, 41, 46);
  font-family: Inter, Helvetica, Arial, sans-serif;
  font-weight: 400;
  font-size: 1rem;
  line-height: 1.57143;
  letter-spacing: 0.01071em;
  box-sizing: inherit;
  display: flex;
  flex-direction: column;
  padding: 4px 16px;
  flex-basis: 100%;
  grid-area: panels;
  -webkit-box-flex: 1;
  flex-grow: 1;
  min-width: 0px;
  background-color: rgb(244, 245, 245);
}
</style>
