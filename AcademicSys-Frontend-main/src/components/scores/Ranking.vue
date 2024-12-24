<template>
  <el-container direction="vertical">
    <div class="bg-container" id="head-container">
      <p>平均绩点与排名统计</p>
    </div>
    <div class="bg-container" id="table-container">
      <el-table :data="tableData" :span-method="objectSpanMethod">
        <el-table-column prop="year" label="学年"></el-table-column>
        <el-table-column prop="semester" label="学期"></el-table-column>
        <el-table-column prop="current_grade" label="平均绩点"></el-table-column>
        <el-table-column prop="rank" label="排名"></el-table-column>
      </el-table>
    </div>
  </el-container>
</template>

<script>
  export default {
    data() {
      return {
        spanArr: [],
        // tableData: []
        // mock data
        tableData: [
          { year: '2021', semester: '秋冬', current_grade: 4.2, rank: 3 }, 
          { year: '2021', semester: '春夏', current_grade: 4.2, rank: 3 }, 
          { year: '2021', semester: '总计', current_grade: 4.2, rank: 3 }, 
          { year: '2022', semester: '秋冬', current_grade: 4.2, rank: 3 }
        ]
      }
    },

    // no paramter for this function. ranking during every period and total ranking should be returned
    mounted: function() {
      this.$http({
        url: "",    // TODO: fill in the url
        method: "GET",
        }).then(function(res) {
          this.tableData = res.data.data;   // TODO: to validate the data structure
        })
      this.getSpan(this.tableData);
    },

    methods: {
      getSpan(data) {
        var pos = 0;
        for (var i = 0; i < data.length; i++) {
          if (i === 0) {
            this.spanArr.push(1);
            pos = 0;
          } 
          else {
            if (data[i].year === data[i - 1].year) {
              this.spanArr[pos] += 1;
              this.spanArr.push(0);
            } 
            else {
              this.spanArr.push(1);
              pos = i;
            }
          }
          console.log(this.spanArr);
        }
      },

      objectSpanMethod({ row, column, rowIndex, columnIndex }) {
        if (columnIndex === 0) {
          const _row = this.spanArr[rowIndex];
          const _col = _row > 0 ? 1 : 0;
          console.log(`rowspan:${_row} colspan:${_col}`);
          return {
            // [0,0] 表示这一行不显示， [2,1]表示行的合并数
            rowspan: _row,
            colspan: _col
          }
        }
      }
    }
  }
</script>

<style scoped src="./scores.css"></style>
<style scoped>
#head-container {
  height: 7%;
  width: 45%;
  position: absolute;
  top: 15%;
  left: 35%;
}
#table-container {
  height: 60%;
  width: 65%;
  position: absolute;
  top: 30%;
  left: 25%;
}

p {
  position: absolute;
  left: 40%;
  top: -25%;
  font-size: large;
}
</style>