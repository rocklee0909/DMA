<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="采集器名称" prop="collectorName">
        <el-input
          v-model="queryParams.collectorName"
          placeholder="请输入采集器名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="采集器类型" prop="collectorType">
        <el-input
          v-model="queryParams.collectorType"
          placeholder="请输入采集器类型"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="模块名称" prop="moduleName">
        <el-select v-model="queryParams.moduleName" placeholder="请选择模块名称" clearable>
          <el-option
            v-for="dict in dict.type.collection_module"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="组名称" prop="dmaGroup">
        <el-input
          v-model="queryParams.dmaGroup"
          placeholder="请输入组名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="主题" prop="dmaTopic">
        <el-input
          v-model="queryParams.dmaTopic"
          placeholder="请输入主题"
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
          v-hasPermi="['collection:collectorInfo:add']"
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
          v-hasPermi="['collection:collectorInfo:edit']"
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
          v-hasPermi="['collection:collectorInfo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['collection:collectorInfo:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="collectorInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="collectorId" />
      <el-table-column label="采集器名称" align="center" prop="collectorName" />
      <el-table-column label="采集器类型" align="center" prop="collectorType" />
      <el-table-column label="详细描述" align="center" prop="description" />
      <el-table-column label="模块名称" align="center" prop="moduleName">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.collection_module" :value="scope.row.moduleName"/>
        </template>
      </el-table-column>
      <el-table-column label="组名称" align="center" prop="dmaGroup" />
      <el-table-column label="主题" align="center" prop="dmaTopic" />
      <el-table-column label="限制接收数" align="center" prop="limitDataNum"/>
      <el-table-column label="有效数据点数量" align="center" prop="validDataNum" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['collection:collectorInfo:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['collection:collectorInfo:remove']"
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

    <!-- 添加或修改采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="采集器名称" prop="collectorName">
          <el-input v-model="form.collectorName" placeholder="请输入采集器名称" />
        </el-form-item>
        <el-form-item label="采集器类型" prop="collectorType">
          <el-input v-model="form.collectorType" placeholder="请输入采集器类型" />
        </el-form-item>
        <el-form-item label="详细描述" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="模块名称" prop="moduleName">
          <el-select v-model="form.moduleName" placeholder="请选择模块名称">
            <el-option
              v-for="dict in dict.type.collection_module"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="组名称" prop="dmaGroup">
          <el-input v-model="form.dmaGroup" placeholder="请输入组名称" />
        </el-form-item>
        <el-form-item label="主题" prop="dmaTopic">
          <el-input v-model="form.dmaTopic" placeholder="请输入主题" />
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
import { listCollectorInfo, getCollectorInfo, delCollectorInfo, addCollectorInfo, updateCollectorInfo } from "@/api/collection/collectorInfo";

export default {
  name: "CollectorInfo",
  dicts: ['collection_module'],
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
      // 采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等表格数据
      collectorInfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        collectorName: null,
        collectorType: null,
        description: null,
        moduleName: null,
        dmaGroup: null,
        dmaTopic: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        collectorName: [
          { required: true, message: "采集器名称不能为空", trigger: "blur" }
        ],
        collectorType: [
          { required: true, message: "采集器类型不能为空", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
        updateTime: [
          { required: true, message: "更新时间不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等列表 */
    getList() {
      this.loading = true;
      listCollectorInfo(this.queryParams).then(response => {
        this.collectorInfoList = response.rows;
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
        collectorId: null,
        collectorName: null,
        collectorType: null,
        description: null,
        moduleName: null,
        dmaGroup: null,
        dmaTopic: null,
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
      this.ids = selection.map(item => item.collectorId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const collectorId = row.collectorId || this.ids
      getCollectorInfo(collectorId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.collectorId != null) {
            updateCollectorInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCollectorInfo(this.form).then(response => {
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
      const collectorIds = row.collectorId || this.ids;
      this.$modal.confirm('是否确认删除采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等编号为"' + collectorIds + '"的数据项？').then(function() {
        return delCollectorInfo(collectorIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('collection/collectorInfo/export', {
        ...this.queryParams
      }, `collectorInfo_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
