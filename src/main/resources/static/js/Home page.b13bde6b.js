(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["Home page"],{8873:function(e,t,i){},bb51:function(e,t,i){"use strict";i.r(t);var n=function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("main",[i("div",{staticClass:"container_my content"},[i("div",{directives:[{name:"show",rawName:"v-show",value:!0===e.isAdmin,expression:"isAdmin===true"}],staticClass:"add_news"},[i("article",{directives:[{name:"show",rawName:"v-show",value:e.newsId<=0,expression:"newsId<=0"}],staticClass:"add_news__top"},[i("a",{attrs:{href:"#"},on:{click:function(t){return t.preventDefault(),e.addNewsHandler(t)}}},[i("i",{staticClass:"fas fa-plus  fa-3x add_news_icon"})]),i("span",{on:{click:function(t){return t.preventDefault(),e.addNewsHandler(t)}}},[i("b",[e._v("+")]),e._v("Добавить новость ")])])]),i("div",{directives:[{name:"show",rawName:"v-show",value:-1!==e.newsId&&e.isAdmin,expression:"newsId!==-1&&isAdmin"}],staticClass:"add_news"},[i("h4",[e._v(" Image preview ")]),i("img",{attrs:{alt:"image preview",src:""}}),i("form",{staticClass:"r-flex",attrs:{name:"add_news",method:"post",action:"#"}},[i("input",{directives:[{name:"model",rawName:"v-model",value:e.editingNews.title,expression:"editingNews.title"}],staticClass:"inputshadow",attrs:{type:"text",placeholder:"Введите название новости",name:"news title"},domProps:{value:e.editingNews.title},on:{input:function(t){t.target.composing||e.$set(e.editingNews,"title",t.target.value)}}}),i("textarea",{directives:[{name:"model",rawName:"v-model",value:e.editingNews.fullText,expression:"editingNews.fullText"}],staticClass:"inputshadow",attrs:{placeholder:"Введите описание новости"},domProps:{value:e.editingNews.fullText},on:{input:function(t){t.target.composing||e.$set(e.editingNews,"fullText",t.target.value)}}}),i("div",{staticClass:"add_news_controls r-flex"},[i("div",{staticClass:"add_news_controls_left"},[i("input",{directives:[{name:"model",rawName:"v-model",value:e.editingNews.pinned,expression:"editingNews.pinned"}],staticClass:"inputshadow",attrs:{type:"checkbox",name:"fixed"},domProps:{checked:Array.isArray(e.editingNews.pinned)?e._i(e.editingNews.pinned,null)>-1:e.editingNews.pinned},on:{change:function(t){var i=e.editingNews.pinned,n=t.target,s=!!n.checked;if(Array.isArray(i)){var a=null,d=e._i(i,a);n.checked?d<0&&e.$set(e.editingNews,"pinned",i.concat([a])):d>-1&&e.$set(e.editingNews,"pinned",i.slice(0,d).concat(i.slice(d+1)))}else e.$set(e.editingNews,"pinned",s)}}}),i("label",{attrs:{for:"fixed"}},[e._v("Закрепить новость")]),i("input",{directives:[{name:"model",rawName:"v-model",value:e.editingNews.visible,expression:"editingNews.visible"}],staticClass:"inputshadow",attrs:{type:"checkbox",name:"public"},domProps:{checked:Array.isArray(e.editingNews.visible)?e._i(e.editingNews.visible,null)>-1:e.editingNews.visible},on:{change:function(t){var i=e.editingNews.visible,n=t.target,s=!!n.checked;if(Array.isArray(i)){var a=null,d=e._i(i,a);n.checked?d<0&&e.$set(e.editingNews,"visible",i.concat([a])):d>-1&&e.$set(e.editingNews,"visible",i.slice(0,d).concat(i.slice(d+1)))}else e.$set(e.editingNews,"visible",s)}}}),i("label",{attrs:{for:"public"}},[e._v("Публичная новость")])]),i("div",{staticClass:"add_news_controls_right"},[i("input",{attrs:{type:"button",name:"put image",value:"Отменить"},on:{click:function(t){return t.preventDefault(),e.cancleHandler(t)}}}),i("input",{attrs:{type:"submit",name:"submit news",value:"Опубликовать"},on:{click:function(t){return t.preventDefault(),e.publishHandler(e.editingNews)}}})])])])]),e._l(e.news,(function(t){return i("NewsItem",{key:t.id,attrs:{news:t,editingId:e.newsId},on:{editing:function(t){return e.editingHandler(t)}}})})),i("p",{staticClass:"news_archive"})],2)])},s=[],a=i("5530"),d=function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("article",{directives:[{name:"show",rawName:"v-show",value:e.news.id!==e.editingId,expression:"news.id!==editingId"}],class:[e.card_news,e.news.pinned?e.pinned:""]},[i("img",{attrs:{src:"https://via.placeholder.com/165X120",alt:"news foto"}}),i("div",{staticClass:"card_news__body"},[i("div",{class:["card_header",e.news.pinned?"card_header__impotant":""]},[i("h3",[e._v(" "+e._s(e.news.title)+" ")]),e.cut?i("p",[e._v(" "+e._s(e.news.fullText.substr(0,60))+" ")]):i("p",[e._v(" "+e._s(e.news.fullText)+" ")])]),i("div",{staticClass:"card_news__controls r-flex"},[e.cut?i("a",{attrs:{href:"#"},on:{click:function(t){t.preventDefault(),e.cut=!e.cut}}},[e._v(" подробнее > ")]):i("a",{attrs:{href:"#"},on:{click:function(t){t.preventDefault(),e.cut=!e.cut}}},[e._v(" < меньше ")]),i("div",{directives:[{name:"show",rawName:"v-show",value:!0===e.isAdmin,expression:"isAdmin === true"}]},[i("a",{attrs:{href:"#"},on:{click:function(t){return t.preventDefault(),e.$emit("editing",e.news.id)}}},[e._v(" Редактировать ")])])])]),i("div",{staticClass:"card_news__date r-flex"},[i("p",[e._v(" "+e._s(e.news.creationDate)+" ")]),i("p",{directives:[{name:"show",rawName:"v-show",value:e.isAdmin,expression:"isAdmin"}]})])])},r=[],c=i("2f62"),l={name:"NewsItem",props:["news","editingId"],data:function(){return{card_news:"card_news r-flex",pinned:"pinned",important:"card_header__impotant",card_header:"card_header",cut:!0}},computed:Object(a["a"])({},Object(c["c"])(["isAdmin"])),mounted:function(){}},o=l,w=(i("d26e"),i("2877")),u=Object(w["a"])(o,d,r,!1,null,"63a2dd6e",null),p=u.exports,v={name:"home",data:function(){return{editingNews:{title:"",fulltext:"",pinned:!1,hidden:!1,visible:!0},newsId:-1}},components:{NewsItem:p},computed:Object(a["a"])(Object(a["a"])({},Object(c["c"])(["isAdmin","getNews"])),Object(c["d"])(["news"])),methods:Object(a["a"])(Object(a["a"])({},Object(c["b"])(["fetchNews","createNews","updateNews"])),{},{cancleHandler:function(){this.newsId=-1,this.editingNews={title:"",fulltext:"",pinned:!1,hidden:!1,visible:!0}},editingHandler:function(e){this.newsId=e,this.editingNews=this.getNews(e)},publishHandler:function(e){var t=this;e.id?this.updateNews(e).then((function(e){t.newsId=-1,t.editingNews={title:"",fulltext:"",pinned:!1,hidden:!1,visible:!0}})):this.createNews(e).then((function(e){t.newsId=-1,t.editingNews={title:"",fulltext:"",pinned:!1,hidden:!1,visible:!0}}))},addNewsHandler:function(){this.newsId=0,this.editingNews={}}}),mounted:function(){this.fetchNews().then((function(e){}))}},m=v,f=Object(w["a"])(m,n,s,!1,null,null,null);t["default"]=f.exports},d26e:function(e,t,i){"use strict";var n=i("8873"),s=i.n(n);s.a}}]);
//# sourceMappingURL=Home page.b13bde6b.js.map