<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="工厂模型" prop="factoryModelId">
          <treeselect @select="handleQuery" @deselect="handleQuery" v-model="queryParams.factoryModelId" :options="modelOptions" :show-count="true" placeholder="请选择归属工厂模型" style="width: 180px;" />
      </el-form-item>
      <el-form-item label="告警名称" prop="alertName">
        <el-input
          v-model="queryParams.alertName"
          placeholder="请输入告警名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="事件状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择事件状态" clearable>
          <el-option
            v-for="dict in dict.type.startup_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['collection:dataAlarmConfig:add']"
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
          v-hasPermi="['collection:dataAlarmConfig:edit']"
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
          v-hasPermi="['collection:dataAlarmConfig:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['collection:dataAlarmConfig:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="dataAlarmConfigList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="告警id" align="center" prop="alertId" />
      <el-table-column label="告警名称" align="center" prop="alertName" />
      <el-table-column label="触发条件" align="center" prop="triggerCondition" />
      <el-table-column label="最新序列号ms" align="center" prop="latestSerial" />
      <el-table-column label="检查频率" align="center" prop="checkRate" />
      <el-table-column label="最新检查时间" align="center" prop="checkTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.checkTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="检查设备数量" align="center" prop="checkEquipmentNum" />
      <el-table-column label="事件状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.startup_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['collection:dataAlarmConfig:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['collection:dataAlarmConfig:remove']"
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

    <!-- 添加或修改告警配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="归属工厂模型" prop="factoryModelId">
          <treeselect v-model="form.factoryModelId" :options="modelOptions" :show-count="true" placeholder="请选择归属工厂模型" />
        </el-form-item>
        <el-form-item label="告警名称" prop="alertName">
          <el-input v-model="form.alertName" placeholder="请输入告警名称" />
        </el-form-item>
        <el-form-item label="触发条件" prop="triggerCondition">
          <el-input v-model="form.triggerCondition" placeholder="请输入触发条件" />
        </el-form-item>
        <el-form-item label="最新序列号ms" prop="latestSerial">
          <el-input v-model="form.latestSerial" placeholder="请输入最新序列号ms" />
        </el-form-item>
        <el-form-item label="检查频率" prop="checkRate">
          <el-input v-model="form.checkRate" placeholder="请输入检查频率" />
        </el-form-item>
        <el-form-item label="最新检查时间" prop="checkTime">
          <el-date-picker clearable
            v-model="form.checkTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择最新检查时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="检查设备数量" prop="checkEquipmentNum">
          <el-input v-model="form.checkEquipmentNum" placeholder="请输入检查设备数量" />
        </el-form-item>
        <el-form-item label="事件状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择事件状态">
            <el-option
              v-for="dict in dict.type.startup_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
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
import { listDataAlarmConfig, getDataAlarmConfig, delDataAlarmConfig, addDataAlarmConfig, updateDataAlarmConfig, modelTreeSelect } from "@/api/collection/dataAlarmConfig";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { Splitpanes, Pane } from "splitpanes";
import "splitpanes/dist/splitpanes.css";

export default {
  name: "DataAlarmConfig",
  dicts: ['startup_status'],
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
      // 告警配置表格数据
      dataAlarmConfigList: [],
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
        alertName: null,
        status: null,
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
    /** 查询告警配置列表 */
    getList() {
      this.loading = true;
      listDataAlarmConfig(this.queryParams).then(response => {
        this.dataAlarmConfigList = response.rows;
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
        alertId: null,
        factoryModelId: null,
        alertName: null,
        triggerCondition: null,
        latestSerial: null,
        checkRate: null,
        checkTime: null,
        checkEquipmentNum: null,
        status: null,
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
      this.ids = selection.map(item => item.alertId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加告警配置";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const alertId = row.alertId || this.ids
      getDataAlarmConfig(alertId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改告警配置";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.alertId != null) {
            updateDataAlarmConfig(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDataAlarmConfig(this.form).then(response => {
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
      const alertIds = row.alertId || this.ids;
      this.$modal.confirm('是否确认删除告警配置编号为"' + alertIds + '"的数据项？').then(function() {
        return delDataAlarmConfig(alertIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('collection/dataAlarmConfig/export', {
        ...this.queryParams
      }, `dataAlarmConfig_${new Date().getTime()}.xlsx`)
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
