<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="工厂模型" prop="factoryModelId">
          <treeselect @select="handleQuery" @deselect="handleQuery" v-model="queryParams.factoryModelId" :options="modelOptions" :show-count="true" placeholder="请选择归属工厂模型" style="width: 180px;" />
      </el-form-item>
      <el-form-item label="预处理名称" prop="pretreatmentName">
        <el-input
          v-model="queryParams.pretreatmentName"
          placeholder="请输入预处理名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="目标数据点名称" prop="targetDataPointName">
        <el-input
          v-model="queryParams.targetDataPointName"
          placeholder="请输入目标数据点名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="目标数据点编码" prop="targetDataPointCode">
        <el-input
          v-model="queryParams.targetDataPointCode"
          placeholder="请输入目标数据点编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预处理方法" prop="pretreatmentMethod">
        <el-input
          v-model="queryParams.pretreatmentMethod"
          placeholder="请输入预处理方法"
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
          v-hasPermi="['collection:dataPretreatmentConfig:add']"
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
          v-hasPermi="['collection:dataPretreatmentConfig:edit']"
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
          v-hasPermi="['collection:dataPretreatmentConfig:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['collection:dataPretreatmentConfig:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="dataPretreatmentConfigList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="规则id" align="center" prop="pretreatmentId" />
      <el-table-column label="工厂模型id" align="center" prop="factoryModelId" />
      <el-table-column label="预处理名称" align="center" prop="pretreatmentName" />
      <el-table-column label="数据点id" align="center" prop="pointId" />
      <el-table-column label="目标数据点名称" align="center" prop="targetDataPointName" />
      <el-table-column label="目标数据点编码" align="center" prop="targetDataPointCode" />
      <el-table-column label="预处理方法" align="center" prop="pretreatmentMethod" />
      <el-table-column label="最新处理时间" align="center" prop="handleTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.handleTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['collection:dataPretreatmentConfig:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['collection:dataPretreatmentConfig:remove']"
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

    <!-- 添加或修改预处理规则配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="归属工厂模型" prop="factoryModelId">
          <treeselect v-model="form.factoryModelId" :options="modelOptions" :show-count="true" placeholder="请选择归属工厂模型" />
        </el-form-item>
        <el-form-item label="预处理名称" prop="pretreatmentName">
          <el-input v-model="form.pretreatmentName" placeholder="请输入预处理名称" />
        </el-form-item>
        <el-form-item label="数据点id" prop="pointId">
          <el-input v-model="form.pointId" placeholder="请输入数据点id" />
        </el-form-item>
        <el-form-item label="目标数据点名称" prop="targetDataPointName">
          <el-input v-model="form.targetDataPointName" placeholder="请输入目标数据点名称" />
        </el-form-item>
        <el-form-item label="目标数据点编码" prop="targetDataPointCode">
          <el-input v-model="form.targetDataPointCode" placeholder="请输入目标数据点编码" />
        </el-form-item>
        <el-form-item label="预处理方法" prop="pretreatmentMethod">
          <el-input v-model="form.pretreatmentMethod" placeholder="请输入预处理方法" />
        </el-form-item>
        <el-form-item label="最新处理时间" prop="handleTime">
          <el-date-picker clearable
            v-model="form.handleTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择最新处理时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="删除标志" prop="delFlag">
          <el-input v-model="form.delFlag" placeholder="请输入删除标志" />
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
import { listDataPretreatmentConfig, getDataPretreatmentConfig, delDataPretreatmentConfig, addDataPretreatmentConfig, updateDataPretreatmentConfig, modelTreeSelect } from "@/api/collection/dataPretreatmentConfig";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { Splitpanes, Pane } from "splitpanes";
import "splitpanes/dist/splitpanes.css";

export default {
  name: "DataPretreatmentConfig",
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
      // 预处理规则配置表格数据
      dataPretreatmentConfigList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 工厂模型树选项
      modelOptions: undefined,
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        factoryModelId: null,
        pretreatmentName: null,
        targetDataPointName: null,
        targetDataPointCode: null,
        pretreatmentMethod: null,
        handleTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
    this.getFactoryModelTree();
  },
  methods: {
    /** 查询预处理规则配置列表 */
    getList() {
      this.loading = true;
      listDataPretreatmentConfig(this.queryParams).then(response => {
        this.dataPretreatmentConfigList = response.rows;
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
        pretreatmentId: null,
        factoryModelId: null,
        pretreatmentName: null,
        pointId: null,
        targetDataPointName: null,
        targetDataPointCode: null,
        pretreatmentMethod: null,
        handleTime: null,
        delFlag: null,
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
      this.ids = selection.map(item => item.pretreatmentId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加预处理规则配置";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const pretreatmentId = row.pretreatmentId || this.ids
      getDataPretreatmentConfig(pretreatmentId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改预处理规则配置";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.pretreatmentId != null) {
            updateDataPretreatmentConfig(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDataPretreatmentConfig(this.form).then(response => {
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
      const pretreatmentIds = row.pretreatmentId || this.ids;
      this.$modal.confirm('是否确认删除预处理规则配置编号为"' + pretreatmentIds + '"的数据项？').then(function() {
        return delDataPretreatmentConfig(pretreatmentIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('collection/dataPretreatmentConfig/export', {
        ...this.queryParams
      }, `dataPretreatmentConfig_${new Date().getTime()}.xlsx`)
    },
    /** 查询工厂模型下拉树结构 */
    getFactoryModelTree() {
      modelTreeSelect().then(response => {
        this.modelOptions = response.data;
      });
    },
  }
};
</script>
