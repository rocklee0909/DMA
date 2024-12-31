<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="设备信息ID" prop="equipmentInfoId">
        <el-input
          v-model="queryParams.equipmentInfoId"
          placeholder="请输入设备信息ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属设备编号" prop="equipmentInfoName">
        <el-input
          v-model="queryParams.equipmentInfoName"
          placeholder="请输入所属设备编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="子设备编号" prop="equipmentCode">
        <el-input
          v-model="queryParams.equipmentCode"
          placeholder="请输入子设备编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="子设备名称" prop="equipmentName">
        <el-input
          v-model="queryParams.equipmentName"
          placeholder="请输入子设备名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备型号" prop="equipmentModel">
        <el-input
          v-model="queryParams.equipmentModel"
          placeholder="请输入设备型号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备供应商" prop="supplier">
        <el-input
          v-model="queryParams.supplier"
          placeholder="请输入设备供应商"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备状态" prop="equipmentStatus">
        <el-select v-model="queryParams.equipmentStatus" placeholder="请选择设备状态" clearable>
          <el-option
            v-for="dict in dict.type.enable_status"
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
          v-hasPermi="['system:equipmentModule:add']"
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
          v-hasPermi="['system:equipmentModule:edit']"
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
          v-hasPermi="['system:equipmentModule:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:equipmentModule:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="equipmentModuleList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="子设备ID" align="center" prop="equipmentModuleId" />
      <el-table-column label="设备信息ID" align="center" prop="equipmentInfoId" />
      <el-table-column label="所属设备编号" align="center" prop="equipmentInfoName" />
      <el-table-column label="子设备编号" align="center" prop="equipmentCode" />
      <el-table-column label="子设备名称" align="center" prop="equipmentName" />
      <el-table-column label="设备型号" align="center" prop="equipmentModel" />
      <el-table-column label="设备类型" align="center" prop="equipmentType" />
      <el-table-column label="设备供应商" align="center" prop="supplier" />
      <el-table-column label="设备状态" align="center" prop="equipmentStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.enable_status" :value="scope.row.equipmentStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="设备主要功能" align="center" prop="mainFunction" />
      <el-table-column label="设备技术规格" align="center" prop="technicalSpecifications" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:equipmentModule:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:equipmentModule:remove']"
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

    <!-- 添加或修改设备模块信息-子设备对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="设备信息ID" prop="equipmentInfoId">
          <el-input v-model="form.equipmentInfoId" placeholder="请输入设备信息ID" />
        </el-form-item>
        <el-form-item label="所属设备编号" prop="equipmentInfoName">
          <el-input v-model="form.equipmentInfoName" placeholder="请输入所属设备编号" />
        </el-form-item>
        <el-form-item label="子设备编号" prop="equipmentCode">
          <el-input v-model="form.equipmentCode" placeholder="请输入子设备编号" />
        </el-form-item>
        <el-form-item label="子设备名称" prop="equipmentName">
          <el-input v-model="form.equipmentName" placeholder="请输入子设备名称" />
        </el-form-item>
        <el-form-item label="设备型号" prop="equipmentModel">
          <el-input v-model="form.equipmentModel" placeholder="请输入设备型号" />
        </el-form-item>
        <el-form-item label="设备供应商" prop="supplier">
          <el-input v-model="form.supplier" placeholder="请输入设备供应商" />
        </el-form-item>
        <el-form-item label="设备状态" prop="equipmentStatus">
          <el-radio-group v-model="form.equipmentStatus">
            <el-radio
              v-for="dict in dict.type.enable_status"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="设备主要功能" prop="mainFunction">
          <el-input v-model="form.mainFunction" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="设备技术规格" prop="technicalSpecifications">
          <el-input v-model="form.technicalSpecifications" type="textarea" placeholder="请输入内容" />
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
import { listEquipmentModule, getEquipmentModule, delEquipmentModule, addEquipmentModule, updateEquipmentModule } from "@/api/system/equipmentModule";

export default {
  name: "EquipmentModule",
  dicts: ['enable_status'],
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
      // 设备模块信息-子设备表格数据
      equipmentModuleList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        equipmentInfoId: null,
        equipmentInfoName: null,
        equipmentCode: null,
        equipmentName: null,
        equipmentModel: null,
        equipmentType: null,
        supplier: null,
        equipmentStatus: null,
        mainFunction: null,
        technicalSpecifications: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        equipmentName: [
          { required: true, message: "子设备名称不能为空", trigger: "blur" }
        ],
        equipmentStatus: [
          { required: true, message: "设备状态不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询设备模块信息-子设备列表 */
    getList() {
      this.loading = true;
      listEquipmentModule(this.queryParams).then(response => {
        this.equipmentModuleList = response.rows;
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
        equipmentModuleId: null,
        equipmentInfoId: null,
        equipmentInfoName: null,
        equipmentCode: null,
        equipmentName: null,
        equipmentModel: null,
        equipmentType: null,
        supplier: null,
        equipmentStatus: null,
        mainFunction: null,
        technicalSpecifications: null,
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
      this.ids = selection.map(item => item.equipmentModuleId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加设备模块信息-子设备";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const equipmentModuleId = row.equipmentModuleId || this.ids
      getEquipmentModule(equipmentModuleId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改设备模块信息-子设备";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.equipmentModuleId != null) {
            updateEquipmentModule(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addEquipmentModule(this.form).then(response => {
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
      const equipmentModuleIds = row.equipmentModuleId || this.ids;
      this.$modal.confirm('是否确认删除设备模块信息-子设备编号为"' + equipmentModuleIds + '"的数据项？').then(function() {
        return delEquipmentModule(equipmentModuleIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/equipmentModule/export', {
        ...this.queryParams
      }, `equipmentModule_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
