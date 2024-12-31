<template>
  <div class="app-container">
    <el-row :gutter="20">
      <splitpanes :horizontal="this.$store.getters.device === 'mobile'" class="default-theme">
        <!--采集器数据-->
        <pane size="16">
          <el-col>
            <div class="head-container">
              <el-input v-model="collectorName" placeholder="请输入采集器名称" clearable size="small"
                prefix-icon="el-icon-search" style="margin-bottom: 20px" />
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
              <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
              </el-form-item>
            </el-form>

            <el-row :gutter="10" class="mb8">
              <el-col :span="1.5">
                <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                  v-hasPermi="['collection:dataTypePoint:add']">新增</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
                  v-hasPermi="['collection:dataTypePoint:edit']">修改</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
                  @click="handleDelete" v-hasPermi="['collection:dataTypePoint:remove']">删除</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
                  v-hasPermi="['collection:dataTypePoint:export']">导出</el-button>
              </el-col>
              <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
            </el-row>

            <el-table v-loading="loading" :data="dataTypePointList" @selection-change="handleSelectionChange">
              <el-table-column type="selection" width="55" align="center" />
              <el-table-column label="类型ID" align="center" prop="typeId" />
              <el-table-column label="点位ID" align="center" prop="pointId" />
              <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                  <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                    v-hasPermi="['collection:dataTypePoint:edit']">修改</el-button>
                  <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                    v-hasPermi="['collection:dataTypePoint:remove']">删除</el-button>
                </template>
              </el-table-column>
            </el-table>

            <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum"
              :limit.sync="queryParams.pageSize" @pagination="getList" />
          </el-col>
        </pane>
      </splitpanes>
    </el-row>
    <!-- 添加或修改数据类型点位关联对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listDataTypePoint, getDataTypePoint, delDataTypePoint, addDataTypePoint, updateDataTypePoint } from "@/api/collection/dataTypePoint";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { Splitpanes, Pane } from "splitpanes";
import "splitpanes/dist/splitpanes.css";

export default {
  name: "DataTypePoint",
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
      // 数据类型点位关联表格数据
      dataTypePointList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
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
    /** 查询数据类型点位关联列表 */
    getList() {
      this.loading = true;
      listDataTypePoint(this.queryParams).then(response => {
        this.dataTypePointList = response.rows;
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
        typeId: null,
        pointId: null,
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
      this.ids = selection.map(item => item.typeId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加数据类型点位关联";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const typeId = row.typeId || this.ids
      getDataTypePoint(typeId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改数据类型点位关联";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.typeId != null) {
            updateDataTypePoint(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDataTypePoint(this.form).then(response => {
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
      const typeIds = row.typeId || this.ids;
      this.$modal.confirm('是否确认删除数据类型点位关联编号为"' + typeIds + '"的数据项？').then(function () {
        return delDataTypePoint(typeIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('collection/dataTypePoint/export', {
        ...this.queryParams
      }, `dataTypePoint_${new Date().getTime()}.xlsx`)
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
