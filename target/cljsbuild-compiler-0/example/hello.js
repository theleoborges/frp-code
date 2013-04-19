goog.provide('example.hello');
goog.require('cljs.core');
goog.require('jayq.core');
goog.require('jayq.core');
example.hello.feed = "http://data.dev.ninemsn.com.au/Services/Service.axd?ServiceName=Highlight&ServiceAction=GetWithEndDate&SiteID=6670393&SectionID=0&SubSectionID=0&GroupID=6746725&ServiceFormat=jsonp&callback=?";
example.hello.fetch_stuff = (function fetch_stuff(){
var G__11560 = (new cljs.core.Keyword("\uFDD0'bind")).call(null,jayq.core.deferred_m);
var G__11561 = (new cljs.core.Keyword("\uFDD0'return")).call(null,jayq.core.deferred_m);
var G__11562 = (new cljs.core.Keyword("\uFDD0'zero")).call(null,jayq.core.deferred_m);
return G__11560.call(null,$.getJSON(example.hello.feed),(function (a){
return G__11560.call(null,$.getJSON(example.hello.feed),(function (b){
return G__11561.call(null,(function (){console.log("a: ",a);
console.log("b: ",b);
return console.log(Rx.Observable);
})());
}));
}));
});
example.hello.rx_stuff = (function rx_stuff(){
return Rx.Observable.fromArray(cljs.core.clj__GT_js.call(null,cljs.core.PersistentVector.fromArray([1,2,3], true))).subscribe((function (v){
return console.log("Received value: ",v);
}));
});
jayq.core.document_ready.call(null,(function (){
jayq.core.inner.call(null,jayq.core.css.call(null,jayq.core.$.call(null,"\uFDD0'h1"),cljs.core.ObjMap.fromObject(["\uFDD0'background"],{"\uFDD0'background":"blue"})),"Yo dawg, I heard you like Clojure so I put Clojure in your javascript so you can FP while you JS!");
example.hello.rx_stuff.call(null);
return example.hello.fetch_stuff.call(null);
}));
