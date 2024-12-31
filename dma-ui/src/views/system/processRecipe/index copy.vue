<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="设备ID" prop="equipmentInfoId">
        <el-input
          v-model="queryParams.equipmentInfoId"
          placeholder="请输入设备ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
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

    <!-- 添加或修改工艺配方信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="设备ID" prop="equipmentInfoId">
          <el-input v-model="form.equipmentInfoId" placeholder="请输入设备ID" />
        </el-form-item>
        <el-form-item label="产品编码" prop="productCode">
          <el-input v-model="form.productCode" placeholder="请输入产品编码" />
        </el-form-item>
        <el-form-item label="配方编号" prop="recipeCode">
          <el-input v-model="form.recipeCode" placeholder="请输入配方编号" />
        </el-form-item>
        <el-form-item label="配方名称" prop="recipeName">
          <el-input v-model="form.recipeName" placeholder="请输入配方名称" />
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
import { listProcessRecipe, getProcessRecipe, delProcessRecipe, addProcessRecipe, updateProcessRecipe } from "@/api/system/processRecipe";

export default {
  name: "ProcessRecipe",
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
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
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
        recipeCode: [
          { required: true, message: "配方编号不能为空", trigger: "blur" }
        ],
        recipeStatus: [
          { required: true, message: "配方状态不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询工艺配方信息列表 */
    getList() {
      this.loading = true;
      listProcessRecipe(this.queryParams).then(response => {
        this.processRecipeList = response.rows;
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
      this.ids = selection.map(item => item.recipeId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加工艺配方信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const recipeId = row.recipeId || this.ids
      getProcessRecipe(recipeId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改工艺配方信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.recipeId != null) {
            updateProcessRecipe(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProcessRecipe(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除工艺配方信息编号为"' + recipeIds + '"的数据项？').then(function() {
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
    }
  }
};
</script>
