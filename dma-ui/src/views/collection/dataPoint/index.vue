<template>
  <div class="app-container">
    <el-row :gutter="20">
      <splitpanes :horizontal="this.$store.getters.device === 'mobile'" class="default-theme">
        <!--采集器数据-->
        <pane size="16">
          <el-col>
            <div class="head-container">
              <el-input v-model="collectorName" placeholder="请输入采集器名称" clearable size="small" prefix-icon="el-icon-search"
                style="margin-bottom: 20px" />
            </div>
            <div class="head-container">
              <el-tree :data="collectorOptions" :props="defaultProps" :expand-on-click-node="false"
                :filter-node-method="filterNode" ref="tree" node-key="id" default-expand-all highlight-current
                @node-click="handleNodeClick" />
            </div>
          </el-col>
        </pane>

        <!--数据点位数据-->
        <pane size="84">
          <el-col>
            <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
              label-width="88px">
              <el-form-item label="表名" prop="tableName">
                <el-input v-model="queryParams.tableName" placeholder="请输入表名" clearable
                  @keyup.enter.native="handleQuery" />
              </el-form-item>
              <el-form-item label="数据点名称" prop="pointName">
                <el-input v-model="queryParams.pointName" placeholder="请输入数据点名称" clearable
                  @keyup.enter.native="handleQuery" />
              </el-form-item>
              <el-form-item label="数据点编码" prop="pointCode">
                <el-input v-model="queryParams.pointCode" placeholder="请输入数据点位编码" clearable
                  @keyup.enter.native="handleQuery" />
              </el-form-item>
              <el-form-item label="数据类型" prop="dataType">
                <el-select v-model="queryParams.dataType" placeholder="请选择数据类型" clearable>
                  <el-option v-for="dict in dict.type.data_type" :key="dict.value" :label="dict.label"
                    :value="dict.value" />
                </el-select>
              </el-form-item>
              <el-form-item label="数据单位" prop="unit">
                <el-input v-model="queryParams.unit" placeholder="请输入数据单位" clearable
                  @keyup.enter.native="handleQuery" />
              </el-form-item>
              <el-form-item label="激活状态" prop="isActive">
                <el-select v-model="queryParams.isActive" placeholder="请选择激活状态" clearable>
                  <el-option
                    v-for="dict in dict.type.active_status"
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
                <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                  v-hasPermi="['collection:dataPoint:add']">新增</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
                  v-hasPermi="['collection:dataPoint:edit']">修改</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
                  @click="handleDelete" v-hasPermi="['collection:dataPoint:remove']">删除</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
                  v-hasPermi="['collection:dataPoint:export']">导出</el-button>
              </el-col>
              <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
            </el-row>

            <el-table v-loading="loading" :data="dataPointList" @selection-change="handleSelectionChange">
              <el-table-column type="selection" width="55" align="center" />
              <el-table-column label="ID" align="center" prop="pointId" />
              <el-table-column label="主题" align="center" prop="dmaTopic" />
              <el-table-column label="表名" align="center" prop="tableName" />
              <el-table-column label="数据点名称" align="center" prop="pointName" />
              <el-table-column label="数据点位编码" align="center" prop="pointCode" />
              <el-table-column label="数据点位详细描述" align="center" prop="description" />
              <el-table-column label="数据类型" align="center" prop="dataType">
                <template slot-scope="scope">
                  <dict-tag :options="dict.type.data_type" :value="scope.row.dataType" />
                </template>
              </el-table-column>
              <el-table-column label="数据单位" align="center" prop="unit" />
              <el-table-column label="激活状态" align="center" prop="isActive">
                <template slot-scope="scope">
                  <dict-tag :options="dict.type.active_status" :value="scope.row.isActive"/>
                </template>
              </el-table-column>
              <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                  <el-button size="mini" type="text" icon="el-icon-edit" @click="handleMove(scope.row)"
                    v-hasPermi="['collection:dataPoint:edit']">移动</el-button>
                  <el-button size="mini" type="text" icon="el-icon-edit" @click="handleCopy(scope.row)"
                    v-hasPermi="['collection:dataPoint:edit']">复制</el-button>
                  <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                    v-hasPermi="['collection:dataPoint:edit']">修改</el-button>
                  <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                    v-hasPermi="['collection:dataPoint:remove']">删除</el-button>
                </template>
              </el-table-column>
            </el-table>

            <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum"
              :limit.sync="queryParams.pageSize" @pagination="getList" />
          </el-col>
        </pane>
      </splitpanes>
    </el-row>

    <!-- 添加或修改数据点位配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="140px">
        <el-form-item label="归属采集器[主题]" prop="collectorId">
          <treeselect v-model="form.collectorId" :options="collectorOptions" :show-count="true" placeholder="请选择归属采集器[主题]" />
        </el-form-item>
        <el-form-item label="数据点名称" prop="pointName">
          <el-input v-model="form.pointName" placeholder="请输入数据点名称" />
        </el-form-item>
        <el-form-item label="数据点位编码" prop="pointCode">
          <el-input v-model="form.pointCode" placeholder="请输入数据点位编码" />
        </el-form-item>
        <el-form-item label="数据点位详细描述" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="数据类型" prop="dataType">
          <el-select v-model="form.dataType" placeholder="请选择数据类型">
            <el-option v-for="dict in dict.type.data_type" :key="dict.value" :label="dict.label"
              :value="dict.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="数据单位" prop="unit">
          <el-input v-model="form.unit" placeholder="请输入数据单位" />
        </el-form-item>
        <el-form-item label="激活状态" prop="isActive">
          <el-select v-model="form.isActive" placeholder="请选择激活状态">
            <el-option
              v-for="dict in dict.type.active_status"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加或修改数据点位配置对话框 -->
    <el-dialog :title="title" :visible.sync="moveOpen" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="140px">
        <el-form-item label="移动到采集器" prop="collectorId">
          <treeselect v-model="form.collectorId" :options="collectorOptions" :show-count="true" placeholder="请选择需要移动到采集器..." />
        </el-form-item>
        <el-form-item label="数据点名称" prop="pointName">
          {{ form.pointName }}
        </el-form-item>
        <el-form-item label="数据点位编码" prop="pointCode">
          {{ form.pointCode }}
        </el-form-item>
        <el-form-item label="数据点位详细描述" prop="description">
          {{ form.description }}
        </el-form-item>
        <el-form-item label="数据类型" prop="dataType">
          {{ dict.value }}
        </el-form-item>
        <el-form-item label="数据单位" prop="unit">
          {{form.unit}}
        </el-form-item>
        <el-form-item label="激活状态" prop="isActive">
          {{ form.isActive }}
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
import { listDataPoint, getDataPoint, delDataPoint, addDataPoint, updateDataPoint,moveDataPoint, collectorTreeSelect } from "@/api/collection/dataPoint";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { Splitpanes, Pane } from "splitpanes";
import "splitpanes/dist/splitpanes.css";

export default {
  name: "DataPoint",
  dicts: ['data_type','active_status'],
  components: { Treeselect,Splitpanes, Pane },
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
      //tree搜索条件: 采集器名称
      collectorName: null,
      //采集器树形菜单
      collectorOptions:[],
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 数据点位配置表格数据
      dataPointList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示移动弹出层
      moveOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tableName: null,
        pointName: null,
        pointCode: null,
        description: null,
        dataType: null,
        unit: null,
        isActive: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        collectorId:[
          { required: true, message: "归属采集器不能为空", trigger: "change" }
        ],
        pointName: [
          { required: true, message: "数据点名称不能为空", trigger: "blur" }
        ],
        pointCode: [
          { required: true, message: "数据点编号不能为空", trigger: "blur" },
          {
            validator: (rule, value, callback) => {
              if (/[^a-zA-Z0-9]/.test(value)) {
                callback(new Error('Point Code cannot contain special characters'));
              } else {
                callback();
              }
            },
            trigger: 'blur'
          }
        ],
        dataType: [
          { required: true, message: "数据类型不能为空", trigger: "change" }
        ],
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
        updateTime: [
          { required: true, message: "更新时间不能为空", trigger: "blur" }
        ],
        isActive: [
          { required: true, message: "激活状态不能为空", trigger: "change" }
        ]
      }
    };
  },
  watch: {
    // 根据名称筛选部门树
    collectorName(val) {
      this.$refs.tree.filter(val);
    }
  },
  created() {
    this.getList();
    this.getCollectorTree();
  },
  methods: {
    /** 查询数据点位配置列表 */
    getList() {
      this.loading = true;
      listDataPoint(this.queryParams).then(response => {
        this.dataPointList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    //查询采集器树形菜单数据信息
    getCollectorTree(){
      collectorTreeSelect().then(response => {
        this.collectorOptions = response.data;
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
        pointId: null,
        tableName: null,
        pointName: null,
        pointCode: null,
        description: null,
        dataType: null,
        unit: null,
        isActive: null,
        createTime: null,
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
      this.ids = selection.map(item => item.pointId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加数据点位配置";
    },
    handleMove(row){
      this.reset();
      const pointId = row.pointId || this.ids
      getDataPoint(pointId).then(response => {
        this.form = response.data;
        this.moveOpen = true;
        this.title = "移动数据点位配置";
      });
    },
    handleCopy(row){
      this.reset();
      const pointId = row.pointId || this.ids
      getDataPoint(pointId).then(response => {
        this.form = response.data;
        this.form.pointId = null;
        this.open = true;
        this.title = "复制数据点位配置";
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const pointId = row.pointId || this.ids
      getDataPoint(pointId).then(response => {
        this.form = response.data;
        console.log(this.form)
        this.open = true;
        this.title = "修改数据点位配置";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if(moveOpen){
            if (this.form.pointId != null) {
              updateDataPoint(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addDataPoint(this.form).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            }
          } else {
            moveDataPoint(this.form).then(response => {
                this.$modal.msgSuccess("移动成功");
                this.moveOpen = false;
                this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const pointIds = row.pointId || this.ids;
      this.$modal.confirm('是否确认删除数据点位配置编号为"' + pointIds + '"的数据项？').then(function () {
        return delDataPoint(pointIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('collection/dataPoint/export', {
        ...this.queryParams
      }, `dataPoint_${new Date().getTime()}.xlsx`)
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.dmaTopic = data.dmaTopic;
      this.handleQuery();
    }
  }
};
</script>
