<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="工艺配方ID" prop="recipeId">
        <el-input
          v-model="queryParams.recipeId"
          placeholder="请输入工艺配方ID"
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
      <el-form-item label="步骤序号" prop="orderNum">
        <el-input
          v-model="queryParams.orderNum"
          placeholder="请输入步骤序号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="步骤名称" prop="flowName">
        <el-input
          v-model="queryParams.flowName"
          placeholder="请输入步骤名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="时间" prop="flowTime">
        <el-input
          v-model="queryParams.flowTime"
          placeholder="请输入时间"
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
          v-hasPermi="['system:processFlow:add']"
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
          v-hasPermi="['system:processFlow:edit']"
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
          v-hasPermi="['system:processFlow:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:processFlow:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="processFlowList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="配方ID" align="center" prop="flowId" />
      <el-table-column label="工艺配方ID" align="center" prop="recipeId" />
      <el-table-column label="配方编号" align="center" prop="recipeCode" />
      <el-table-column label="配方名称" align="center" prop="recipeName" />
      <el-table-column label="步骤序号" align="center" prop="orderNum" />
      <el-table-column label="步骤名称" align="center" prop="flowName" />
      <el-table-column label="时间" align="center" prop="flowTime" />
      <el-table-column label="描述" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:processFlow:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:processFlow:remove']"
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

    <!-- 添加或修改工艺流程信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="工艺配方ID" prop="recipeId">
          <el-input v-model="form.recipeId" placeholder="请输入工艺配方ID" />
        </el-form-item>
        <el-form-item label="配方编号" prop="recipeCode">
          <el-input v-model="form.recipeCode" placeholder="请输入配方编号" />
        </el-form-item>
        <el-form-item label="配方名称" prop="recipeName">
          <el-input v-model="form.recipeName" placeholder="请输入配方名称" />
        </el-form-item>
        <el-form-item label="步骤序号" prop="orderNum">
          <el-input v-model="form.orderNum" placeholder="请输入步骤序号" />
        </el-form-item>
        <el-form-item label="步骤名称" prop="flowName">
          <el-input v-model="form.flowName" placeholder="请输入步骤名称" />
        </el-form-item>
        <el-form-item label="时间" prop="flowTime">
          <el-input v-model="form.flowTime" placeholder="请输入时间" />
        </el-form-item>
        <el-form-item label="描述" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入描述" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listProcessFlow, getProcessFlow, delProcessFlow, addProcessFlow, updateProcessFlow } from "@/api/system/processFlow";

export default {
  name: "ProcessFlow",
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
      // 工艺流程信息表格数据
      processFlowList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        recipeId: null,
        recipeCode: null,
        recipeName: null,
        orderNum: null,
        flowName: null,
        flowTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        recipeCode: [
          { required: true, message: "配方编号不能为空", trigger: "blur" }
        ],
        flowTime: [
          { required: true, message: "时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询工艺流程信息列表 */
    getList() {
      this.loading = true;
      listProcessFlow(this.queryParams).then(response => {
        this.processFlowList = response.rows;
        this.total = response.total;
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
        flowId: null,
        recipeId: null,
        recipeCode: null,
        recipeName: null,
        orderNum: null,
        flowName: null,
        flowTime: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
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
      this.ids = selection.map(item => item.flowId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加工艺流程信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const flowId = row.flowId || this.ids
      getProcessFlow(flowId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改工艺流程信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.flowId != null) {
            updateProcessFlow(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProcessFlow(this.form).then(response => {
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
      const flowIds = row.flowId || this.ids;
      this.$modal.confirm('是否确认删除工艺流程信息编号为"' + flowIds + '"的数据项？').then(function() {
        return delProcessFlow(flowIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/processFlow/export', {
        ...this.queryParams
      }, `processFlow_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
