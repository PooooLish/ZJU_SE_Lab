<template>
  <div class="container">
    <div class="filter-section">
      <input type="text" v-model="filterInput" placeholder="输入课程ID" @keyup.enter="filterRecords" class="filter-input"/>
      <button @click="filterRecords" class="filter-button">筛选</button>
    </div>
    <div class="table-wrapper">
      <table>
        <thead>
          <tr>
            <th>课程代码</th>
            <th>学生代码</th>
            <th>理由</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="record in filteredApprovals" :key="record.change_id">
            <td>{{ record.course_id }}</td>
            <td>{{ record.student_id }}</td>
            <td>{{ record.change_reason }}</td>
            <td>
              <button @click="approveChange(record.change_id)" class="approve-button">批准</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'teacherbyelection',
  data() {
    return {
      pendingApprovals: [],
      filterInput: '',
      filteredApprovals: []
    };
  },
  
  methods: {
    fetchPendingApprovals() {
      axios.get('/admin/pendingApprovals')
        .then(response => {
          this.pendingApprovals = response.data.data.pendingApprovals;
          this.filteredApprovals = [...this.pendingApprovals]; // 初始化筛选列表
        })
        .catch(error => {
          console.error('Error fetching pending approvals:', error);
        });
    },
    approveChange(changeId) {
      axios.post('/admin/approveChange', { change_id: changeId })
        .then(response => {
          alert(response.data.message); // 显示 "批准成功"
          this.fetchPendingApprovals(); // 重新获取待审批记录以更新列表
        })
        .catch(error => {
          console.error('Error approving change:', error);
        });
    },
    filterRecords() {
      const filterInput = this.filterInput.trim().toLowerCase();
      if (!filterInput) {
        this.filteredApprovals = [...this.pendingApprovals];
        return;
      }
      
      this.filteredApprovals = this.pendingApprovals.filter(record => 
        record.course_id.toString().includes(filterInput)
      );
    }
  },
  created() {
    this.fetchPendingApprovals();
  }
};
</script>

<style scoped>
.container {
  max-width: 800px;
  margin: auto;
  padding: 20px;
}

.filter-section {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.filter-input, .filter-button, .approve-button {
  padding: 10px;
  border: none;
  border-radius: 5px;
}

.filter-input {
  flex-grow: 1;
  margin-right: 10px;
}

.filter-button, .approve-button {
  cursor: pointer;
  background-color: #007bff;
  color: white;
  transition: background-color 0.3s;
}

.filter-button:hover, .approve-button:hover {
  background-color: #0056b3;
}

.table-wrapper {
  overflow-x: auto;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  border: 1px solid #ddd;
  text-align: left;
  padding: 8px;
}

th {
  background-color: #f2f2f2;
}
</style>