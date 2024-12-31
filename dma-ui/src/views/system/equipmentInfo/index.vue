<template>
  <div class="app-container">
    <el-row :gutter="24" style="height: 800px">
      <splitpanes :horizontal="this.$store.getters.device === 'mobile'" class="default-theme">
        <!--工厂模型数据-->
        <pane size="16">
          <el-col>
            <div class="head-container">
              <el-input v-model="modelName" placeholder="请输入工厂模型名称" clearable size="small" prefix-icon="el-icon-search" style="margin-bottom: 20px" />
            </div>
            <div class="head-container">
              <el-tree :data="modelOptions" :props="defaultProps" :expand-on-click-node="false" :filter-node-method="filterNode" ref="tree" node-key="id" default-expand-all highlight-current @node-click="handleNodeClick" />
            </div>
          </el-col>
        </pane>
        <!-- 设备信息 -->
        <pane size="84">
          <el-col>
            <splitpanes horizontal class="default-theme">
              <pane size="50">
                <el-row>
                  <el-col>
                      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="88px">
                      <el-form-item label="设备名称" prop="equipmentName">
                        <el-input
                          v-model="queryParams.equipmentName"
                          placeholder="请输入设备名称"
                          clearable
                          @keyup.enter.native="handleQuery"
                        />
                      </el-form-item>
                      
                      <el-form-item label="设备状态" prop="equipmentStatus">
                        <el-select v-model="queryParams.equipmentStatus" placeholder="请选择设备状态" clearable>
                          <el-option
                            v-for="dict in dict.type.equipment_status"
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
                          v-hasPermi="['system:equipmentInfo:add']"
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
                          v-hasPermi="['system:equipmentInfo:edit']"
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
                          v-hasPermi="['system:equipmentInfo:remove']"
                        >删除</el-button>
                      </el-col>
                      <el-col :span="1.5">
                        <el-button
                          type="warning"
                          plain
                          icon="el-icon-download"
                          size="mini"
                          @click="handleExport"
                          v-hasPermi="['system:equipmentInfo:export']"
                        >导出</el-button>
                      </el-col>
                      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
                    </el-row>

                    <el-table v-loading="loading" :data="equipmentInfoList" @selection-change="handleSelectionChange">
                      <el-table-column type="selection" width="55" align="center" />
                      <el-table-column label="设备ID" align="center" prop="equipmentInfoId" />
                      <el-table-column label="设备名称" align="center" prop="equipmentName">
                        <template slot-scope="scope">
                          <el-link type="primary" @click="handleInfoClick(scope.row)">{{ scope.row.equipmentName }}</el-link>
                        </template>
                      </el-table-column>
                      <el-table-column label="设备型号" align="center" prop="equipmentModel" />
                      <el-table-column label="设备类型" align="center" prop="equipmentType" />
                      <el-table-column label="子设备数" align="center" prop="childEquipmentNum">
                        <template slot-scope="scope">
                          <el-tag @click="handleInfoClick(scope.row)">{{scope.row.childEquipmentNum}}</el-tag>
                        </template>
                      </el-table-column>
                      <el-table-column label="设备供应商" align="center" prop="supplier" />
                      <el-table-column label="设备状态" align="center" prop="equipmentStatus">
                        <template slot-scope="scope">
                          <dict-tag :options="dict.type.equipment_status" :value="scope.row.equipmentStatus"/>
                        </template>
                      </el-table-column>
                      <el-table-column label="设备描述" align="center" prop="equipmentDescription" />
                      <el-table-column label="主要功能" align="center" prop="mainFunction" />
                      <el-table-column label="技术规格" align="center" prop="technicalSpecifications" />
                      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="100">
                        <template slot-scope="scope">
                          <el-button
                            size="mini"
                            type="text"
                            icon="el-icon-edit"
                            @click="handleUpdate(scope.row)"
                            v-hasPermi="['system:equipmentInfo:edit']"
                          >修改</el-button>
                          <el-button
                            size="mini"
                            type="text"
                            icon="el-icon-delete"
                            @click="handleDelete(scope.row)"
                            v-hasPermi="['system:equipmentInfo:remove']"
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

              <pane size="50" v-if="showChildPane">
                <div class="parent-container" style="width: 100%;">
                  <el-row style="width: 100%;margin-bottom: 5px;">
                    <el-col :span="20"><div class="childTitle">子设备列表</div></el-col>
                    <el-col :span="4"><div class="childOpertor"><i class="el-icon-close" @click="showChildPane=false"></i></div></el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="24">
                      <el-table v-loading="loading" :data="equipmentModuleList">
                        <el-table-column label="子设备ID" align="center" prop="equipmentModuleId" />
                        <el-table-column label="子设备编号" align="center" prop="equipmentCode" />
                        <el-table-column label="子设备名称" align="center" prop="equipmentName" />
                        <el-table-column label="所属设备编号" align="center" prop="equipmentInfoName" />
                        <el-table-column label="设备供应商" align="center" prop="supplier" />
                        <el-table-column label="设备状态" align="center" prop="equipmentStatus">
                          <template slot-scope="scope">
                            <dict-tag :options="dict.type.enable_status" :value="scope.row.equipmentStatus"/>
                          </template>
                        </el-table-column>
                        <el-table-column label="设备主要功能" align="center" prop="mainFunction" />
                        <el-table-column label="设备技术规格" align="center" prop="technicalSpecifications" />
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

    <!-- 添加或修改设备基本信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="归属工厂模型" prop="factoryModelId">
              <treeselect v-model="form.factoryModelId" :options="modelOptions" :show-count="true" placeholder="请选择归属工厂模型" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="设备名称" prop="equipmentName">
              <el-input v-model="form.equipmentName" placeholder="请输入设备名称" />
            </el-form-item>
          </el-col>
            <el-col :span="12">
            <el-form-item label="设备型号" prop="equipmentModel">
              <el-input v-model="form.equipmentModel" placeholder="请输入设备型号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="设备供应商" prop="supplier">
              <el-input v-model="form.supplier" placeholder="请输入设备供应商" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="设备状态" prop="equipmentStatus">
              <el-radio-group v-model="form.equipmentStatus">
                <el-radio
                  v-for="dict in dict.type.equipment_status"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="设备描述" prop="equipmentDescription">
              <el-input v-model="form.equipmentDescription" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="设备主要功能" prop="mainFunction">
              <el-input v-model="form.mainFunction" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="设备技术规格" prop="technicalSpecifications">
              <el-input v-model="form.technicalSpecifications" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
        </el-row>
      <el-divider></el-divider>
      <el-row>
        <el-col :span="24">
          <div>
            <table>
              <thead>
                <tr>
                  <th>子设备编号</th>
                  <th>子设备名称</th>
                  <th style="width: 200px;">子设备状态</th>
                  <th>子设备供应商</th>
                  <th>设备主要功能</th>
                  <th>设备技术规格</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(row, index) in equipmentModuleList" :key="index">
                  <td><el-input v-model="row.equipmentCode" placeholder="子设备编号" clearable/></td>
                  <td><el-input v-model="row.equipmentName" placeholder="子设备名称" clearable/></td>
                  <td style="text-align: center;">
                    <el-switch
                      style="display: block"
                      v-model="row.equipmentStatus"
                      active-color="#13ce66"
                      inactive-color="#ff4949"
                      active-text="启用"
                      inactive-text="禁用"
                      active-value="1"
                      inactive-value="0">
                    </el-switch>
                  </td>
                  <td><el-input v-model="row.supplier" placeholder="子设备供应商" clearable/></td>
                  <td><el-input v-model="row.mainFunction" placeholder="设备主要功能" clearable/></td>
                  <td><el-input v-model="row.technicalSpecifications" placeholder="设备技术规格" clearable/></td>
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
import { listEquipmentInfo, getEquipmentInfo, delEquipmentInfo, addEquipmentInfo, updateEquipmentInfo,modelTreeSelect } from "@/api/system/equipmentInfo";
import { listEquipmentModule, getEquipmentModule, delEquipmentModule, addEquipmentModule, updateEquipmentModule } from "@/api/system/equipmentModule";

import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { Splitpanes, Pane } from "splitpanes";
import "splitpanes/dist/splitpanes.css";

export default {
  name: "EquipmentInfo",
  dicts: ['equipment_status','enable_status'],
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
      // 设备基本信息表格数据
      equipmentInfoList: [],
      // 设备模块信息-子设备表格数据
      equipmentModuleList: [],
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
      showChildPane: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        equipmentName: null,
        equipmentModel: null,
        equipmentType: null,
        childEquipmentNum: null,
        supplier: null,
        equipmentStatus: null,
        equipmentDescription: null,
        mainFunction: null,
        technicalSpecifications: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        equipmentName: [
          { required: true, message: "设备名称不能为空", trigger: "blur" }
        ],
        equipmentStatus: [
          { required: true, message: "设备状态不能为空", trigger: "change" }
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
      listEquipmentInfo(this.queryParams).then(response => {
        this.equipmentInfoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询设备模块信息-子设备列表 */
    getModuleList(equipmentInfoId) {
      this.loading = true;

      let moduleQueeryParams = {
        pageNum: 1,
        pageSize: 1000,
        equipmentInfoId: equipmentInfoId
      }
      
      listEquipmentModule(moduleQueeryParams).then(response => {
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
        equipmentInfoId: null,
        equipmentName: null,
        equipmentModel: null,
        equipmentType: null,
        childEquipmentNum: null,
        supplier: null,
        equipmentStatus: null,
        equipmentDescription: null,
        mainFunction: null,
        technicalSpecifications: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.equipmentModuleList = []
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
      this.title = "添加设备基本信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const equipmentInfoId = row.equipmentInfoId || this.ids
      getEquipmentInfo(equipmentInfoId).then(response => {
        this.form = response.data.equipmentInfo;
        this.equipmentModuleList = response.data.modules
        this.open = true;
        this.title = "修改设备基本信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.childEquipmentNum = this.equipmentModuleList.length
          let params = {"equipmentInfo":this.form,"modules": this.equipmentModuleList}
          if (this.form.equipmentInfoId != null) {
            updateEquipmentInfo(params).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addEquipmentInfo(params).then(response => {
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
      const equipmentInfoIds = row.equipmentInfoId || this.ids;
      this.$modal.confirm('是否确认删除设备基本信息编号为"' + equipmentInfoIds + '"的数据项？').then(function() {
        return delEquipmentInfo(equipmentInfoIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/equipmentInfo/export', {
        ...this.queryParams
      }, `equipmentInfo_${new Date().getTime()}.xlsx`)
    },
    /** 查询工厂模型下拉树结构 */
    getFactoryModelTree() {
      modelTreeSelect().then(response => {
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
      this.showChildPane = false
      this.queryParams.factoryModelId = data.id
      this.handleQuery();
    },
    // 查询子设备信息
    handleInfoClick(row){
      this.showChildPane=!this.showChildPane
      this.getModuleList(row.equipmentInfoId)
    },
    addRow() {
      let form = {
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
      }
      this.equipmentModuleList.push(form);
    },
    removeRow(index) {
      if(this.equipmentModuleList[index].equipmentModuleId){
        const equipmentModuleIds = this.equipmentModuleList[index].equipmentModuleId;
        this.$modal.confirm('是否确认删除子设备信息编号为"' + equipmentModuleIds + '"的数据项？').then(function() {
          return delEquipmentModule(equipmentModuleIds);
        }).then(() => {
          this.equipmentModuleList.splice(index, 1);
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      }else{
        this.equipmentModuleList.splice(index, 1);
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


