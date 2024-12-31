<template>
  <div class="app-container">
    <el-row :gutter="24" style="height: 800px">
      <splitpanes :horizontal="this.$store.getters.device === 'mobile'" class="default-theme">
        <!--工厂模型/设备数据-->
        <pane size="16">
          <el-col>
            <div class="head-container">
              <el-input v-model="modelName" placeholder="请输入工厂/设备名称" clearable size="small" prefix-icon="el-icon-search" style="margin-bottom: 20px" />
            </div>
            <div class="head-container">
              <el-tree :data="modelOptions" :props="defaultProps" :expand-on-click-node="false" :filter-node-method="filterNode" ref="tree" node-key="id" default-expand-all highlight-current @node-click="handleNodeClick" />
            </div>
          </el-col>
        </pane>
        <!-- 工艺配方流程信息 -->
        <pane size="84">
          <el-col>
            <splitpanes horizontal class="default-theme">
              <pane size="50">
                <el-row>
                  <el-col>
                    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="88px">
                      <el-form-item label="产品编码" prop="productCode">
                        <el-input
                          v-model="queryParams.productCode"
                          placeholder="请输入产品编码"
                          clearable
                          @keyup.enter.native="handleQuery"
                        />
                      </el-form-item>
                      <el-form-item label="配方编号" prop="recipeCode">
                        <el-input
                          v-model="queryParams.recipeCode"
                          placeholder="请输入配方编号"
                          clearable
                          @keyup.enter.native="handleQuery"
                        />
                      </el-form-item>
                      <el-form-item label="配方名称" prop="recipeName">
                        <el-input
                          v-model="queryParams.recipeName"
                          placeholder="请输入配方名称"
                          clearable
                          @keyup.enter.native="handleQuery"
                        />
                      </el-form-item>
                      <el-form-item>
                        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
                      </el-form-item>
                    </el-form>

                    <el-row :gutter="10" class="mb8">
                      <el-col :span="1.5">
                        <el-button
                          type="primary"
                          plain
                          icon="el-icon-plus"
                          size="mini"
                          @click="handleAdd"
                          v-hasPermi="['system:processRecipe:add']"
                        >新增</el-button>
                      </el-col>
                      <el-col :span="1.5">
                        <el-button
                          type="success"
                          plain
                          icon="el-icon-edit"
                          size="mini"
                          :disabled="single"
                          @click="handleUpdate"
                          v-hasPermi="['system:processRecipe:edit']"
                        >修改</el-button>
                      </el-col>
                      <el-col :span="1.5">
                        <el-button
                          type="danger"
                          plain
                          icon="el-icon-delete"
                          size="mini"
                          :disabled="multiple"
                          @click="handleDelete"
                          v-hasPermi="['system:processRecipe:remove']"
                        >删除</el-button>
                      </el-col>
                      <el-col :span="1.5">
                        <el-button
                          type="warning"
                          plain
                          icon="el-icon-download"
                          size="mini"
                          @click="handleExport"
                          v-hasPermi="['system:processRecipe:export']"
                        >导出</el-button>
                      </el-col>
                      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
                    </el-row>

                    <el-table v-loading="loading" :data="processRecipeList" @selection-change="handleSelectionChange">
                      <el-table-column type="selection" width="55" align="center" />
                      <el-table-column label="配方ID" align="center" prop="recipeId" />
                      <el-table-column label="设备ID" align="center" prop="equipmentInfoId" />
                      <el-table-column label="产品编码" align="center" prop="productCode" />
                      <el-table-column label="配方编号" align="center" prop="recipeCode" />
                      <el-table-column label="配方名称" align="center" prop="recipeName" />
                      <el-table-column label="设备类型" align="center" prop="equipmentType" />
                      <el-table-column label="配方状态" align="center" prop="recipeStatus" />
                      <el-table-column label="描述" align="center" prop="remark" />
                      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                        <template slot-scope="scope">
                          <el-button
                            size="mini"
                            type="text"
                            icon="el-icon-edit"
                            @click="handleUpdate(scope.row)"
                            v-hasPermi="['system:processRecipe:edit']"
                          >修改</el-button>
                          <el-button
                            size="mini"
                            type="text"
                            icon="el-icon-delete"
                            @click="handleDelete(scope.row)"
                            v-hasPermi="['system:processRecipe:remove']"
                          >删除</el-button>
                        </template>
                      </el-table-column>
                    </el-table>

                    <pagination
                      v-show="total>0"
                      :total="total"
                      :page.sync="queryParams.pageNum"
                      :limit.sync="queryParams.pageSize"
                      @pagination="getList"
                    />
                    
                  </el-col>
                </el-row>
              </pane>

              <pane size="50" v-if="showFlowPane">
                <div class="parent-container" style="width: 100%;">
                  <el-row style="width: 100%;margin-bottom: 5px;">
                    <el-col :span="20"><div class="childTitle">工艺流程</div></el-col>
                    <el-col :span="4"><div class="childOpertor"><i class="el-icon-close" @click="showFlowPane=false"></i></div></el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="24">
                      <el-table v-loading="loading" :data="equipmentModuleList">
                        <el-table-column label="配方ID" align="center" prop="flowId" />
                        <el-table-column label="工艺配方ID" align="center" prop="recipeId" />
                        <el-table-column label="配方编号" align="center" prop="recipeCode" />
                        <el-table-column label="配方名称" align="center" prop="recipeName" />
                        <el-table-column label="步骤序号" align="center" prop="orderNum" />
                        <el-table-column label="步骤名称" align="center" prop="flowName" />
                        <el-table-column label="时间" align="center" prop="flowTime" />
                        <el-table-column label="描述" align="center" prop="remark" />
                      </el-table>
                    </el-col>
                  </el-row>
                </div>
              </pane>
          </splitpanes>
          </el-col>
        </pane>
      </splitpanes>
    </el-row>

    <!-- 添加或修改工艺配方信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="归属设备模型" prop="factoryModelId">
              <treeselect v-model="form.equipmentInfoId" :options="modelOptions" :flat="true" :show-count="true" placeholder="请选择归属设备模型">
              </treeselect>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="产品编码" prop="productCode">
              <el-input v-model="form.productCode" placeholder="请输入产品编码" />
            </el-form-item>
          </el-col>
            <el-col :span="12">
              <el-form-item label="配方编号" prop="recipeCode">
                <el-input v-model="form.recipeCode" placeholder="请输入配方编号" />
              </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="配方名称" prop="recipeName">
              <el-input v-model="form.recipeName" placeholder="请输入配方名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="配方状态" prop="recipeStatus">
              <el-radio-group v-model="form.recipeStatus">
                <el-radio
                  v-for="dict in dict.type.process_recipe_status"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      <el-divider></el-divider>
      <el-row>
        <el-col :span="24">
          <div>
            <table style="width: 100%;">
              <thead>
                <tr>
                  <th style="width: 80px;">配方编号</th>
                  <th style="width: 80px;">配方名称</th>
                  <th>步骤序号</th>
                  <th>步骤名称</th>
                  <th>时间(s)</th>
                  <th>描述</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(row, index) in processFlowList" :key="index">
                  <td style="text-align: center;">{{ row.recipeCode }}</td>
                  <td style="text-align: center;">{{ row.recipeName }}</td>
                  <td style="text-align: center;">
                    <el-input-number v-model="row.orderNum" :step="1"></el-input-number>
                  </td>
                  <td><el-input v-model="row.flowName" placeholder="步骤名称" clearable/></td>
                  <td><el-input v-model="row.flowTime" placeholder="时间(s)" clearable/></td>
                  <td><el-input v-model="row.remark" placeholder="描述" clearable/></td>
                  <td>
                    <el-button type="primary" @click="removeRow(index)">删除行</el-button>
                  </td>
                </tr>
              </tbody>
            </table>
            <el-button type="primary" @click="addRow">添加行</el-button>
          </div>
        </el-col>
      </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listProcessRecipe, getProcessRecipe, delProcessRecipe, addProcessRecipe, updateProcessRecipe, modelTreeSelect } from "@/api/system/processRecipe";
import { listProcessFlow, getProcessFlow, delProcessFlow, addProcessFlow, updateProcessFlow } from "@/api/system/processFlow";

import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { Splitpanes, Pane } from "splitpanes";
import "splitpanes/dist/splitpanes.css";
import { normalize } from "quill/modules/keyboard";

export default {
  name: "ProcessRecipe",
  dicts: ['process_recipe_status'],
  components: { Treeselect, Splitpanes, Pane },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 工艺配方信息表格数据
      processRecipeList: [],
      // 工艺流程信息表格数据
      processFlowList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 工厂模型名称
      modelName: undefined,
      // 工厂模型树选项
      modelOptions: undefined,
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 是否显示设备子集列表
      showFlowPane: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        equipmentInfoId: null,
        productCode: null,
        recipeCode: null,
        recipeName: null,
        equipmentType: null,
        recipeStatus: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        productCode: [
          { required: true, message: "产品编码不能为空", trigger: "blur" }
        ],
        recipeCode: [
          { required: true, message: "配方编号不能为空", trigger: "blur" }
        ],
        recipeName: [
          { required: true, message: "配方名称不能为空", trigger: "blur" }
        ],
        recipeStatus: [
          { required: true, message: "配方状态不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList()
    this.getFactoryModelTree()
  },
  methods: {
    /** 查询设备基本信息列表 */
    getList() {
      this.loading = true;
      listProcessRecipe(this.queryParams).then(response => {
        this.processRecipeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询设备模块信息-子设备列表 */
    getFlowList(recipeId) {
      this.loading = true;

      let flowQueeryParams = {
        pageNum: 1,
        pageSize: 1000,
        recipeId: recipeId
      }
      
      listProcessFlow(flowQueeryParams).then(response => {
        this.equipmentModuleList = response.rows;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        recipeId: null,
        equipmentInfoId: null,
        productCode: null,
        recipeCode: null,
        recipeName: null,
        equipmentType: null,
        recipeStatus: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.processFlowList = []
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.equipmentInfoId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加工艺信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const recipeId = row.recipeId || this.ids
      getProcessRecipe(recipeId).then(response => {
        this.form = response.data.processRecipe;
        this.processFlowList = response.data.flows
        this.open = true;
        this.title = "修改工艺信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          let params = {"processRecipe":this.form,"flows": this.processFlowList}
          if (this.form.recipeId != null) {
            updateProcessRecipe(params).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProcessRecipe(params).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const recipeIds = row.recipeId || this.ids;
      this.$modal.confirm('是否确认删除设备基本信息编号为"' + recipeIds + '"的数据项？').then(function() {
        return delProcessRecipe(recipeIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/processRecipe/export', {
        ...this.queryParams
      }, `processRecipe_${new Date().getTime()}.xlsx`)
    },
    /** 查询工厂模型下拉树结构 */
    getFactoryModelTree() {
      modelTreeSelect().then(response => {
        console.log(response.data)
        this.modelOptions = response.data;
      });
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      if(!data.isClick){
        return
      }
      this.showChildPane = false
      this.queryParams.equipmentInfoId = data.id
      this.handleQuery();
    },
    // 查询子设备信息
    handleInfoClick(row){
      this.showFlowPane=!this.showFlowPane
      this.getFlowList(row.recipeId)
    },
    addRow() {
      if(!this.form.recipeCode || !this.form.recipeName){
        this.$message({
          message: '请先录入【配方编号】及【配方名称】',
          type: 'warning'
        });
        return;
      }

      let orderNum = 1
      if(this.processFlowList.length>0){
        orderNum = this.processFlowList[this.processFlowList.length-1].orderNum + 1
      }

      let form = {
        flowId: null,
        recipeId: null,
        recipeCode: this.form.recipeCode,
        recipeName: this.form.recipeName,
        orderNum: orderNum,
        flowName: null,
        flowTime: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      }
      this.processFlowList.push(form);
    },
    removeRow(index) {
      if(this.processFlowList[index].flowId){
        const flowIds = this.processFlowList[index].flowId;
        this.$modal.confirm('是否确认删除工艺流程编号为"' + flowIds + '"的数据项？').then(function() {
          return delProcessFlow(flowIds);
        }).then(() => {
          this.processFlowList.splice(index, 1);
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      }else{
        this.processFlowList.splice(index, 1);
      }
    }
  }
};
</script>

<style scoped>
.splitpanes__pane {
  display: flex;
}

.childTitle{
  font-size: 16px;
  font-weight: bold;
}

.childOpertor{
  float: right;
  margin-right: 10px;
}

.clear-div {
  clear: both;
  width: 200px;
  height: 200px;
  background-color: lightgreen;
}

.parent-container {
  display: flex;
  flex-direction: column;
}

</style>


