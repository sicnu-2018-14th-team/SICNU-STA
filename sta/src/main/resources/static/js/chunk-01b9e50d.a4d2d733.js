(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-01b9e50d"],{"0f68":function(t,a,e){"use strict";e("4f17")},"4f17":function(t,a,e){},"9ad8":function(t,a,e){"use strict";e.r(a);var n=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",[e("el-card",[e("el-table",{attrs:{data:t.ranktable,"row-class-name":t.tableRowClassName,stripe:"",border:""}},[e("el-table-column",{attrs:{type:"index",label:"#"}}),e("el-table-column",{attrs:{label:"排名",prop:"rank"}}),e("el-table-column",{attrs:{label:"用户",prop:"userName"}}),e("el-table-column",{attrs:{label:"分数",prop:"score"}})],1),e("el-pagination",{attrs:{background:"",layout:"prev, pager, next","page-size":t.pagesize,total:t.total},on:{"current-change":t.getrank}})],1)],1)},l=[],r={data:function(){return{id:this.$route.query.id,ranktable:[],pagesize:10,total:0}},methods:{getrank:function(t){var a=this,e={contestId:this.id,page:t,pageSize:this.pagesize};axios.post(this.global.query_contest_rank,this.$qs.stringify(e)).then((function(t){console.log(t.data),200===t.data.code&&(a.ranktable=t.data.data.data,a.total=t.data.data.total,console.log(a.ranktable))}))},tableRowClassName:function(t){t.row;var a=t.rowIndex;return 0===a?"warning-row":""}},created:function(){this.getrank(1)}},o=r,s=(e("0f68"),e("2877")),i=Object(s["a"])(o,n,l,!1,null,null,null);a["default"]=i.exports}}]);
//# sourceMappingURL=chunk-01b9e50d.a4d2d733.js.map