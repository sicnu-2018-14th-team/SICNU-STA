(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-565bae64"],{"10e6":function(e,t,i){"use strict";i.r(t);var r=function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("div",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],staticStyle:{"font-size":"20px"}},[i("div",{staticStyle:{"margin-left":"10px"}},[i("p",{staticStyle:{"font-size":"30px",color:"black"}},[e._v("选择题")]),i("br"),i("p",[e._v("距离结束还有："+e._s(e.hourTime)+":"+e._s(e.minTime)+":"+e._s(e.secTime))])]),i("el-divider"),e._l(e.contest_problem,(function(t,r){return i("el-card",{key:r,staticStyle:{"min-height":"100px","text-align":"left","margin-top":"5px"}},[i("span",{staticStyle:{color:"black"},domProps:{innerHTML:e._s(r+1+"、"+t.title)}}),i("br"),i("el-radio",{staticStyle:{"margin-top":"20px","margin-bottom":"10px","margin-left":"20px"},attrs:{disabled:e.isDisabled,label:"选项A"},model:{value:e.userAnswer[r],callback:function(t){e.$set(e.userAnswer,r,t)},expression:"userAnswer[index]"}},[e._v(e._s("A:"+t.option_a))]),i("br"),i("el-radio",{staticStyle:{"margin-bottom":"10px","margin-left":"20px"},attrs:{disabled:e.isDisabled,label:"选项B"},model:{value:e.userAnswer[r],callback:function(t){e.$set(e.userAnswer,r,t)},expression:"userAnswer[index]"}},[e._v(e._s("B:"+t.option_b))]),i("br"),i("el-radio",{staticStyle:{"margin-bottom":"10px","margin-left":"20px"},attrs:{disabled:e.isDisabled,label:"选项C"},model:{value:e.userAnswer[r],callback:function(t){e.$set(e.userAnswer,r,t)},expression:"userAnswer[index]"}},[e._v(e._s("C:"+t.option_c))]),i("br"),i("el-radio",{staticStyle:{"margin-bottom":"10px","margin-left":"20px"},attrs:{disabled:e.isDisabled,label:"选项D"},model:{value:e.userAnswer[r],callback:function(t){e.$set(e.userAnswer,r,t)},expression:"userAnswer[index]"}},[e._v(e._s("D:"+t.option_d))]),i("br"),!0===e.isDisabled&&"答案错误"===e.rightAnswer[r]?i("p",{staticStyle:{color:"red","font-size":"15px"},domProps:{textContent:e._s(e.rightAnswer[r])}}):e._e(),!0===e.isDisabled&&"答案正确"===e.rightAnswer[r]?i("p",{staticStyle:{color:"#67C23A","font-size":"15px"},domProps:{textContent:e._s(e.rightAnswer[r])}}):e._e(),i("el-divider")],1)})),i("div",{staticStyle:{"text-align":"center","margin-top":"10px","margin-bottom":"10px"}},[i("el-button",{attrs:{type:"success"},on:{click:e.test}},[e._v("保存")])],1)],2)},n=[],s=(i("4160"),i("159b"),{data:function(){return{loading:!0,problemId:[],userAnswer:[],isDisabled:!1,id:this.$route.query.id,rightAnswer:[],rightNowTime:"",endTime:"",startTime:"",trueOrFalseList:[],radio:"1",contest_problem:[],timer:"",Timeclock:"",hourTime:1,minTime:1,secTime:1}},methods:{test:function(){var e=this,t=this.problemId;e.loading=!0;for(var i=0;i<t.length;i++)void 0===this.userAnswer[i]&&(this.userAnswer[i]="");var r={problemIds:this.problemId,userAnswers:this.userAnswer,contestId:this.id};axios.post(this.global.save_answers,r).then((function(t){e.loading=!1,200===t.data.code?(e.$message({message:"答案保存成功",type:"success"}),e.$router.push({path:"/problemhome",name:"problemhome",query:{id:e.id}})):e.$message.error("保存答案出错")}))},num:function(e){return e<10?"0"+e:""+e},add:function(){var e=this,t=window.setInterval((function(){0===e.secTime&&0!==e.minTime?(e.secTime=59,e.minTime-=1):0===e.minTime&&0===e.secTime&&0!==e.hourTime?(e.secTime=59,e.minTime=59,e.hourTime-=1):0===e.minTime&&0===e.secTime&&0===e.hourTime?(e.secTime=0,e.minTime=0,e.hourTime=0,e.isDisabled=!0,e.$message.error("考试结束！"),window.clearInterval(t),e.getRightAnswer(),e.$router.push({path:"/problemhome",query:{id:e.id}})):e.secTime-=1}),1e3)},query_contest_problem:function(){var e=this,t={contestId:this.id,typeId:2};axios.post(this.global.user_query_contest_problem,this.$qs.stringify(t)).then((function(t){e.loading=!1,e.contest_problem=t.data.data[1],e.endTime=t.data.data[0].end_time,e.startTime=t.data.data[0].start_time,new Date(e.rightNowTime)<new Date(e.endTime)?(e.Timeclock=new Date(e.endTime)-new Date(e.rightNowTime),e.hourTime=parseInt(e.Timeclock/1e3/60/60),e.minTime=parseInt(e.Timeclock/1e3/60%60),e.secTime=parseInt(e.Timeclock/1e3%60)):(e.hourTime=0,e.minTime=0,e.secTime=0),e.contest_problem.forEach((function(t,i){e.problemId.push(t.problem_id)}))}))},getRightNowTime:function(){var e=this;axios.post(this.global.user_get_time).then((function(t){e.rightNowTime=t.data.data}))},getLastAnswer:function(){var e=this,t={contestId:this.id,typeId:2};axios.post(this.global.query_last_answer,this.$qs.stringify(t)).then((function(t){null!=t.data.data[0]&&t.data.data.forEach((function(t,i){e.userAnswer.push(t.user_answer)}))}))},getRightAnswer:function(){console.log(1111);var e=this,t={contestId:this.id};e.rightAnswer=[],axios.post(this.global.query_result_objective,this.$qs.stringify(t)).then((function(t){console.log(t.data),t.data.data.forEach((function(t,i){e.rightAnswer.push(t.result)}))}))},goJudgeList:function(){this.$router.push({path:"/contest/problemhome/judge",query:{id:this.id}})},goChoiceList:function(){this.$router.push({path:"/contest/problemhome/choice",query:{id:this.id}})}},mounted:function(){this.add()},watch:{second:{handler:function(e){this.num(e)}},minute:{handler:function(e){this.num(e)}},hour:{handler:function(e){this.num(e)}}},computed:{second:function(){return this.num(this.secTime)},minute:function(){return this.num(this.minTime)},hour:function(){return this.num(this.hourTime)}},created:function(){this.query_contest_problem(),this.getLastAnswer(),this.getRightNowTime()}}),o=s,a=(i("7a0d"),i("2877")),c=Object(a["a"])(o,r,n,!1,null,null,null);t["default"]=c.exports},"159b":function(e,t,i){var r=i("da84"),n=i("fdbc"),s=i("17c2"),o=i("9112");for(var a in n){var c=r[a],u=c&&c.prototype;if(u&&u.forEach!==s)try{o(u,"forEach",s)}catch(l){u.forEach=s}}},"17c2":function(e,t,i){"use strict";var r=i("b727").forEach,n=i("a640"),s=i("ae40"),o=n("forEach"),a=s("forEach");e.exports=o&&a?[].forEach:function(e){return r(this,e,arguments.length>1?arguments[1]:void 0)}},4160:function(e,t,i){"use strict";var r=i("23e7"),n=i("17c2");r({target:"Array",proto:!0,forced:[].forEach!=n},{forEach:n})},6350:function(e,t,i){},"65f0":function(e,t,i){var r=i("861d"),n=i("e8b5"),s=i("b622"),o=s("species");e.exports=function(e,t){var i;return n(e)&&(i=e.constructor,"function"!=typeof i||i!==Array&&!n(i.prototype)?r(i)&&(i=i[o],null===i&&(i=void 0)):i=void 0),new(void 0===i?Array:i)(0===t?0:t)}},"7a0d":function(e,t,i){"use strict";i("6350")},a640:function(e,t,i){"use strict";var r=i("d039");e.exports=function(e,t){var i=[][e];return!!i&&r((function(){i.call(null,t||function(){throw 1},1)}))}},ae40:function(e,t,i){var r=i("83ab"),n=i("d039"),s=i("5135"),o=Object.defineProperty,a={},c=function(e){throw e};e.exports=function(e,t){if(s(a,e))return a[e];t||(t={});var i=[][e],u=!!s(t,"ACCESSORS")&&t.ACCESSORS,l=s(t,0)?t[0]:c,d=s(t,1)?t[1]:void 0;return a[e]=!!i&&!n((function(){if(u&&!r)return!0;var e={length:-1};u?o(e,1,{enumerable:!0,get:c}):e[1]=1,i.call(e,l,d)}))}},b727:function(e,t,i){var r=i("0366"),n=i("44ad"),s=i("7b0b"),o=i("50c4"),a=i("65f0"),c=[].push,u=function(e){var t=1==e,i=2==e,u=3==e,l=4==e,d=6==e,m=5==e||d;return function(h,f,p,b){for(var g,T,w=s(h),v=n(w),_=r(f,p,3),x=o(v.length),y=0,A=b||a,S=t?A(h,x):i?A(h,0):void 0;x>y;y++)if((m||y in v)&&(g=v[y],T=_(g,y,w),e))if(t)S[y]=T;else if(T)switch(e){case 3:return!0;case 5:return g;case 6:return y;case 2:c.call(S,g)}else if(l)return!1;return d?-1:u||l?l:S}};e.exports={forEach:u(0),map:u(1),filter:u(2),some:u(3),every:u(4),find:u(5),findIndex:u(6)}},e8b5:function(e,t,i){var r=i("c6b6");e.exports=Array.isArray||function(e){return"Array"==r(e)}},fdbc:function(e,t){e.exports={CSSRuleList:0,CSSStyleDeclaration:0,CSSValueList:0,ClientRectList:0,DOMRectList:0,DOMStringList:0,DOMTokenList:1,DataTransferItemList:0,FileList:0,HTMLAllCollection:0,HTMLCollection:0,HTMLFormElement:0,HTMLSelectElement:0,MediaList:0,MimeTypeArray:0,NamedNodeMap:0,NodeList:1,PaintRequestList:0,Plugin:0,PluginArray:0,SVGLengthList:0,SVGNumberList:0,SVGPathSegList:0,SVGPointList:0,SVGStringList:0,SVGTransformList:0,SourceBufferList:0,StyleSheetList:0,TextTrackCueList:0,TextTrackList:0,TouchList:0}}}]);
//# sourceMappingURL=chunk-565bae64.f9da0b45.js.map