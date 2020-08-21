<template>
  <div class="app-container">
    <el-table :data="userList" ref="singleTable"
      height="500px" border size="mini" fit
      highlight-current-row
      v-loading.body="listLoading" element-loading-text="Loading now ..."
      style="width: 100%" stripe
      @current-change="handleCurrentSelected">
      <el-table-column prop="userId" label="Id">
      </el-table-column>
      <el-table-column prop="userName" label="Name">
      </el-table-column>
      <el-table-column label="Enabled">
        <template slot-scope="scope">
          <i v-if="scope.row.enabled===true" class="el-icon-check"></i>
          <i v-else class="el-icon-close"></i>
        </template>
      </el-table-column>
      <el-table-column prop="lastPasswordResetDate" label="Last password reset date"
        :formatter="dateFormat">
      </el-table-column>
      <el-table-column label="Role">
        <template slot-scope="scope">
          <el-tag type="success" size="small" v-if="scope.row.roleName==='ROLE_ADMIN'">Administrator</el-tag>
          <el-tag type="warning" size="small" v-else-if="scope.row.roleName==='ROLE_USER'">Normal user</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="Actions" v-if="hasPerm(['ROLE_ADMIN', 'ROLE_USER'])">
        <template slot-scope="scope">
          <el-button type="text" icon="el-icon-edit" @click="showUpdate(scope.$index)">Edit</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="listQuery.pageNum" :page-size="listQuery.pageRow" :total="totalCount"
      :page-sizes="[10, 20, 100]" layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <el-dialog title="Updating User Info:" :visible.sync="dialogFormVisible">
      <el-form :model="tempUser" class="small-space"
        label-position="left" label-width="80px"
        style="width:300px;margin-left:50px;">
        <el-form-item label="Id">
          <el-input type="text" v-model="tempUser.userId" disabled></el-input>
        </el-form-item>
        <el-form-item label="Name">
          <el-input type="text" v-model="tempUser.userName" disabled></el-input>
        </el-form-item>
        <el-form-item label="Password" required>
          <el-input type="password" v-model="tempUser.password"></el-input>
        </el-form-item>
        <el-form-item label="Role">
          <el-input type="text" v-model="tempUser.roleName"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible=false">Cancel</el-button>
        <el-button type="primary" @click="updateUser">Update</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { default as api } from '@/utils/api'

export default {
  created() {
    this.getAllUsers()
  },
  data() {
    return {
      userList: [],
      userListOrigin: [],
      listLoading: false,
      currentRow: null,
      totalCount: 0,
      listQuery: {
        pageNum: 1,
        pageRow: 10,
      },
      dialogFormVisible: false,
      tempUser: {
        userId: '',
        userName: '',
        password: '',
        enabled: false,
        roleName: ''
      }
    }
  },
  methods: {
    getAllUsers: function () {
      this.listLoading = true
      api({
        url: 'getallusers',
        method: 'get'
      }).then((data) => {
        this.totalCount = data.length
        this.userList = data
        this.userListOrigin = data
        this.showDisplayedRows()
        this.listLoading = false
      }).catch((error) => {
        console.log(error)
      })
    },
    handleCurrentSelected: function (val) {
      this.currentRow = val;
    },
    dateFormat: function (row, column) {
      return this.moment(row.lastPasswordResetDate).format("YYYY-MM-DD HH:mm:ss")
    },
    handleSizeChange: function (val) {
      this.listLoading = true
      this.listQuery.pageRow = val
      this.listQuery.pageNum = 1
      this.showDisplayedRows()
      this.listLoading = false
    },
    handleCurrentChange: function (val) {
      this.listLoading = true
      this.listQuery.pageNum = val
      this.showDisplayedRows()
      this.listLoading = false
    },
    showUpdate($index) {
      let user = this.userList[$index];
      this.tempUser.userId = user.userId;
      this.tempUser.userName = user.userName;
      this.tempUser.enabled = user.enabled;
      this.tempUser.roleName = user.roleName;
      this.tempUser.password = ''
      this.dialogFormVisible = true
    },
    showDisplayedRows: function () {
      this.userList = this.userListOrigin.slice((this.listQuery.pageNum - 1)*this.listQuery.pageRow, this.listQuery.pageNum*this.listQuery.pageRow)
    }
  }
}
</script>

<style>
.el-row {
  margin-bottom: 20px;
  &:last-child {
    margin-bottom: 0;
  }
}
.el-col {
  border-radius: 4px;
}
.row-bg {
  padding: 10px 0;
}
</style>
