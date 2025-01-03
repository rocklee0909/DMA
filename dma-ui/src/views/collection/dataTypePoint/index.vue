<template>
  <div class="app-container">
    <el-row :gutter="20">
      <splitpanes :horizontal="this.$store.getters.device === 'mobile'" class="default-theme">
        <!--采集器数据-->
        <pane size="20">
          <el-col>
            <div style="margin: 10px;">采集器模型</div>
            <splitpanes :horizontal="true" class="default-theme" style="height: 800px;">
              <pane size="50">
                <div class="head-container">
                  <el-input v-model="collectorName" placeholder="请输入采集器名称" clearable size="small"
                    prefix-icon="el-icon-search" style="margin-bottom: 20px" />
                </div>
                <div class="head-container" style="overflow-y: auto;">
                  <el-tree :data="collectorOptions" :props="defaultProps" :expand-on-click-node="false"
                    :filter-node-method="filterNode" ref="tree" node-key="id" default-expand-all highlight-current
                    @node-click="handleNodeClick" />
                </div>
              </pane>
              <pane size="50" style="overflow-y: auto;">
                <div style="margin: 10px;">参数分类</div>
                <el-collapse v-model="activeNames" @change="handleChange">
                  <el-collapse-item v-for="(option, index) in dataTypeOptions" v-if="option.typeGroup == 'group'"
                    :key="index" :title="option.typeName" :name="index">
                    <template slot="title">
                      {{ option.typeName }}<span class="badge">{{ option.dataNum }}</span>
                    </template>
                    <div class="group-type" @click="handleClick(child)" @mouseover="handleMouseOver(child)"
                      @mouseleave="handleMouseLeave(child)" v-for="(child, chIndex) in option.children">{{ child.typeName
                      }}<span class="badge_type">{{ child.dataNum }}</span></div>
                  </el-collapse-item>
                </el-collapse>
                <div class="group-typeGroup" @click="handleClick(option)" @mouseover="handleMouseOver(option)"
                  @mouseleave="handleMouseLeave(option)" v-for="(option, index) in dataTypeOptions"
                  v-if="option.typeGroup !== 'group'" :key="index" :title="option.typeName" :name="index">{{
                  option.typeName }}<span class="badge_type">{{ option.dataNum
                    }}</span></div>
              </pane>
            </splitpanes>
          </el-col>
        </pane>

        <!--数据点位数据-->
        <pane size="80">
          <el-col>
            <div style="margin: 10px;padding-left: 22px;">参数列表</div>
            <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
              label-width="88px">
              <el-form-item label="采集器" prop="collectorName">
                <treeselect v-model="collectorId" :options="collectorOptions" @select="treeselectChange"
                  :show-count="true" style="width: 250px;" placeholder="请选择归属采集器[主题]" />
              </el-form-item>
              <el-form-item label="参数分类" prop="collectorName">
                <treeselect v-model="queryParams.dataTypeId" :options="dataTypeOptions" :normalizer="normalizer"
                  :show-count="true" style="width: 250px;" :flat="true" placeholder="请选择分类参数" />
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
              <el-table-column label="ID" align="center" prop="pointId" />
              <el-table-column label="主题" align="center" prop="dmaTopic"/>
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
                  <dict-tag :options="dict.type.active_status" :value="scope.row.isActive" />
                </template>
              </el-table-column>
              <el-table-column label="类型ID" align="center" prop="typeId" />
              <el-table-column label="点位ID" align="center" prop="pointId" />
              <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                  <el-button size="mini" type="text" icon="el-icon-delete" @click="handleClear(scope.row)"
                    v-hasPermi="['collection:dataTypePoint:remove']">清除关联</el-button>
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
    <el-dialog :title="title" :visible.sync="open" width="1300px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="分配类型" prop="typeIds">
          <treeselect v-model="form.typeIds" :multiple="true" :options="dataTypeOptions" :flat="true"
            :normalizer="normalizer" :show-count="true" placeholder="请选择数据类型"></treeselect>
        </el-form-item>
        <el-form-item label="分配数据点" prop="pointIds">
          <el-row :gutter="20">
            <el-col :span="6">
              <div class="head-container">
                <el-input v-model="collectorName" placeholder="请输入采集器名称" clearable size="small"
                  prefix-icon="el-icon-search" style="margin-bottom: 20px" />
              </div>
              <div class="head-container" style="overflow-y: auto;">
                <el-tree :data="collectorOptions" :props="defaultProps" :expand-on-click-node="false"
                  :filter-node-method="filterNode" ref="tree" node-key="id" default-expand-all highlight-current
                  @node-click="handleNodeEditClick" />
              </div>
            </el-col>
            <el-col :span="17">
              <el-transfer style="text-align: left; display: inline-block" v-model="value" filterable
                :filter-method="filterMethod" :render-content="renderFunc" :titles="['所有数据点', '选中数据点']" :format="{
                  noChecked: '${total}',
                  hasChecked: '${checked}/${total}'
                }" :props="{ key: 'pointId', lable: 'pointCode' }" @change="handleChange" :data="dataTypePointEditList">
              </el-transfer>
            </el-col>
          </el-row>
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
import { listDataTypePoint, getDataTypePoint, delDataTypePoint, addDataTypePoint, updateDataTypePoint, collectorTreeSelect, clearDataTypePoint } from "@/api/collection/dataTypePoint";
import { listDataType } from "@/api/collection/dataType";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { Splitpanes, Pane } from "splitpanes";
import "splitpanes/dist/splitpanes.css";

export default {
  name: "DataTypePoint",
  dicts: ['data_type', 'active_status'],
  components: { Treeselect, Splitpanes, Pane },
  data() {
    const generateData = _ => {
      const data = [];
      for (let i = 1; i <= 15; i++) {
        data.push({
          key: i,
          label: `备选项 ${i}`
        });
      }
      return data;
    };
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
      collectorId: null,
      collectorOptions: [],
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 数据类型点位关联表格数据
      dataTypePointList: [],
      dataTypePointEditList: [],
      // 数据类型配置树选项
      dataTypeOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        dataTypeId: null,
        dmaTopic: null,
        pointName: null,
        pointCode: null,
        dataType: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      activeNames: ['1'],
      data: generateData(),
      value: [],
      renderFunc(h, option) {
        return <span>{option.pointCode} - {option.pointName} [{option.dataType}]</span>;
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
    this.getTreeselect();
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
    getCollectorTree() {
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
        typeIds: null,
        pointIds: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      if (!this.collectorId) {
        this.queryParams.dmaTopic = null
      }

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
      this.title = "绑定数据类型";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const typeId = row.typeId || this.ids
      getDataTypePoint(typeId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "取消数据类型";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.pointIds = this.value
          addDataTypePoint(this.form).then(response => {
            this.$modal.msgSuccess("新增成功");
            this.open = false;
            this.getList();
            this.getTreeselect();
          });
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
    /** 删除按钮操作 */
    handleClear(row) {
      const pointIds = row.pointId || this.ids;
      this.$modal.confirm('是否确认清除数据点位关联类型,编号为"' + pointIds + '"的数据项？').then(function () {
        return clearDataTypePoint(pointIds);
      }).then(() => {
        this.getList();
        this.getTreeselect();
        this.$modal.msgSuccess("清除成功");
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
      this.collectorId = data.id;
      this.queryParams.dmaTopic = data.dmaTopic;
      this.handleQuery();
    },
    handleNodeEditClick(data) {
      this.queryParams = {
        pageNum: 1,
        pageSize: 10000,
        dataTypeId: null,
        dmaTopic: data.dmaTopic,
        pointName: null,
        pointCode: null,
        dataType: null
      }
      listDataTypePoint(this.queryParams).then(response => {
        this.dataTypePointEditList = response.rows;
        this.total = response.total;
      });
    },
    handleChange(val) {
      console.log(val);
    },
    /** 查询数据类型配置下拉树结构 */
    getTreeselect() {
      listDataType().then(response => {
        this.dataTypeOptions = [];
        const data = this.handleTree(response.data, "typeId", "parentId");
        this.dataTypeOptions = data
      });
    },
    treeselectChange(item) {
      this.queryParams.dmaTopic = item.dmaTopic;
      this.$refs.tree.setCurrentNode(item)
      this.handleQuery()
    },
    handleMouseOver(item) {
      item.hover = true;
    },
    handleMouseLeave(item) {
      item.hover = false;
    },
    handleClick(item) {
      item.clicked = !item.clicked;
      if (item.clicked) {
        this.queryParams.dataTypeId = item.typeId
      } else {
        this.queryParams.dataTypeId = null
      }
    },
    /** 转换数据类型配置数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.typeId,
        isDisabled: node.typeGroup === 'group' ? true : false,
        label: node.typeName,
        children: node.children
      };
    },
    filterMethod(query, item) {
      return item.pointCode.indexOf(query) > -1 || item.pointName.indexOf(query) > -1 || item.dataType.indexOf(query) > -1;
    }
  }
};
</script>
<style scoped>
.group-type {
  height: 30px;
  font-size: 12px;
  font-weight: 500;
  background-color: #FFFFFF;
  color: #303133;
  display: flex;
  align-items: center;
  /* 垂直居中 */
  justify-content: left;
  /* 水平居中，可根据需要选择是否使用 */
}

.group-typeGroup {
  height: 47px;
  font-size: 13px;
  font-weight: 500;
  background-color: #FFFFFF;
  color: #303133;
  display: flex;
  align-items: center;
  /* 垂直居中 */
  justify-content: left;
  /* 水平居中，可根据需要选择是否使用 */
  border-bottom: 1px solid #e6ebf5;
}

.badge {
  background-color: #ff4949;
  border-radius: 10px;
  color: #FFFFFF;
  display: inline-block;
  font-size: 12px;
  height: 18px;
  line-height: 18px;
  padding: 0 6px;
  text-align: center;
  white-space: nowrap;
  border: 1px solid #FFFFFF;
}

.badge_type {
  background-color: #409EFF;
  border-radius: 10px;
  color: #FFFFFF;
  display: inline-block;
  font-size: 12px;
  height: 18px;
  line-height: 18px;
  padding: 0 6px;
  text-align: center;
  white-space: nowrap;
  border: 1px solid #FFFFFF;
}


/* 穿透scoped样式以覆盖Element UI的el-transfer样式 */
::v-deep .el-transfer {
  /* 你可以直接设置el-transfer的宽度，但通常我们需要设置内部面板的宽度 */
  /* 注意：直接设置el-transfer的宽度可能不会生效，因为它只是一个包裹容器 */
}

::v-deep .el-transfer-panel {
  width: 300px;
  /* 设置穿梭框内部面板的宽度 */
  height: 600px;
  /* 直接设置高度可能不会按预期工作，因为高度通常由内容决定 */
}

/* 如果你需要调整内容区的高度（即列表的高度），可以尝试调整以下样式 */
::v-deep .el-transfer-panel__body {
  height: 600px;
  /* 设置穿梭框内部内容区（列表）的高度 */
  overflow-y: auto;
  /* 如果内容超出高度，则显示滚动条 */
}

::v-deep .el-transfer-panel__list.is-filterable {
  height: 534px;
}

/* 如果你还想要调整头部和底部的高度，也可以这样做 */
::v-deep .el-transfer-panel__header,
::v-deep .el-transfer-panel__footer {
  /* 调整头部和底部的高度（如果需要的话） */
}
</style>