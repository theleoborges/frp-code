goog.provide('example.hello');
goog.require('cljs.core');
goog.require('jayq.core');
goog.require('crate.core');
goog.require('shoreleave.remote');
goog.require('clojure.browser.repl');
goog.require('jayq.core');
example.hello.feed = "http://data.dev.ninemsn.com.au/Services/Service.axd?ServiceName=Highlight&ServiceAction=GetWithEndDate&SiteID=6670393&SectionID=0&SubSectionID=0&GroupID=6746725&ServiceFormat=jsonp&callback=?";
example.hello.fetch_stuff = (function fetch_stuff(){
var G__18016 = (new cljs.core.Keyword("\uFDD0:bind")).call(null,jayq.core.deferred_m);
var G__18017 = (new cljs.core.Keyword("\uFDD0:return")).call(null,jayq.core.deferred_m);
var G__18018 = (new cljs.core.Keyword("\uFDD0:zero")).call(null,jayq.core.deferred_m);
return G__18016.call(null,$.getJSON(example.hello.feed),(function (a){
return G__18016.call(null,$.getJSON(example.hello.feed),(function (b){
return G__18017.call(null,(function (){console.log("a: ",a);
return console.log("b: ",b);
})());
}));
}));
});
example.hello.rx_stuff = (function rx_stuff(){
return Rx.Observable.fromArray(cljs.core.clj__GT_js.call(null,cljs.core.PersistentVector.fromArray([1,2,3], true))).subscribe((function (v){
return console.log("Received value: ",v);
}));
});
example.hello.results_observable = (function results_observable(){
return Rx.Observable.create((function (observer){
shoreleave.remotes.http_rpc.remote_callback.call(null,"poll-results",cljs.core.PersistentVector.EMPTY,(function (resp){
return observer.onNext(resp);
}));
return (function (){
return console.log("Disposed");
});
}));
});
var group__3375__auto___18027 = cljs.core.swap_BANG_.call(null,crate.core.group_id,cljs.core.inc);
example.hello.results_partial = (function results_partial(results){
var elem__3376__auto__ = crate.core.html.call(null,cljs.core.PersistentVector.fromArray(["\uFDD0:ul",(function (){var iter__2611__auto__ = (function iter__18023(s__18024){
return (new cljs.core.LazySeq(null,false,(function (){
var s__18024__$1 = s__18024;
while(true){
var temp__4092__auto__ = cljs.core.seq.call(null,s__18024__$1);
if(temp__4092__auto__)
{var xs__4579__auto__ = temp__4092__auto__;
var vec__18026 = cljs.core.first.call(null,xs__4579__auto__);
var label = cljs.core.nth.call(null,vec__18026,0,null);
var votes = cljs.core.nth.call(null,vec__18026,1,null);
return cljs.core.cons.call(null,cljs.core.PersistentVector.fromArray(["\uFDD0:li",[cljs.core.str(label),cljs.core.str(" - "),cljs.core.str(votes)].join('')], true),iter__18023.call(null,cljs.core.rest.call(null,s__18024__$1)));
} else
{return null;
}
break;
}
}),null));
});
return iter__2611__auto__.call(null,results);
})()], true));
elem__3376__auto__.setAttribute("crateGroup",group__3375__auto___18027);
return elem__3376__auto__;
});
example.hello.results_partial.prototype._crateGroup = group__3375__auto___18027;
example.hello.render_poll = (function render_poll(p__18028){
var map__18030 = p__18028;
var map__18030__$1 = ((cljs.core.seq_QMARK_.call(null,map__18030))?cljs.core.apply.call(null,cljs.core.hash_map,map__18030):map__18030);
var question = cljs.core._lookup.call(null,map__18030__$1,"\uFDD0:question",null);
var results = cljs.core._lookup.call(null,map__18030__$1,"\uFDD0:results",null);
jayq.core.inner.call(null,jayq.core.$.call(null,"\uFDD0:h1"),question);
return jayq.core.inner.call(null,jayq.core.$.call(null,"#poll-results"),example.hello.results_partial.call(null,results));
});
example.hello.compute_results_key = (function compute_results_key(resp){
return clojure.string.join.call(null,"",cljs.core.map.call(null,(function (p__18033){
var vec__18034 = p__18033;
var k = cljs.core.nth.call(null,vec__18034,0,null);
var v = cljs.core.nth.call(null,vec__18034,1,null);
return [cljs.core.str(k),cljs.core.str(v)].join('');
}),(new cljs.core.Keyword("\uFDD0:results")).call(null,resp)));
});
example.hello.results_connectable = (function (){var obs = Rx.Observable.interval(2000).selectMany(example.hello.results_observable).publish().refCount();
var obs_1 = obs.skip(1);
return obs.zip(obs_1,(function (prev,curr){
return cljs.core.ObjMap.fromObject(["\uFDD0:prev","\uFDD0:curr"],{"\uFDD0:prev":prev,"\uFDD0:curr":curr});
}));
})();
example.hello.countdown_and_do = (function countdown_and_do(n,f){
if((n > 0))
{console.log("Countdown: ",n);
return window.setTimeout((function (){
return countdown_and_do.call(null,(n - 1),f);
}),1000);
} else
{console.log("All done.");
return f.call(null);
}
});
example.hello.start = (function start(){
console.log("Starting.");
var tk = example.hello.results_connectable.subscribe((function (resp){
if(cljs.core._EQ_.call(null,(new cljs.core.Keyword("\uFDD0:id")).call(null,(new cljs.core.Keyword("\uFDD0:curr")).call(null,resp)),(new cljs.core.Keyword("\uFDD0:id")).call(null,(new cljs.core.Keyword("\uFDD0:prev")).call(null,resp))))
{console.log("Value is: ",cljs.core.clj__GT_js.call(null,resp));
return example.hello.render_poll.call(null,(new cljs.core.Keyword("\uFDD0:curr")).call(null,resp));
} else
{console.log("New poll. Dispose and countdown");
tk.dispose();
return example.hello.countdown_and_do.call(null,10,start);
}
}));
return null;
});
example.hello.tk = example.hello.results_connectable.subscribe((function (resp){
if(cljs.core._EQ_.call(null,(new cljs.core.Keyword("\uFDD0:id")).call(null,(new cljs.core.Keyword("\uFDD0:curr")).call(null,resp)),(new cljs.core.Keyword("\uFDD0:id")).call(null,(new cljs.core.Keyword("\uFDD0:prev")).call(null,resp))))
{console.log("Value is: ",cljs.core.clj__GT_js.call(null,resp));
return example.hello.render_poll.call(null,(new cljs.core.Keyword("\uFDD0:curr")).call(null,resp));
} else
{return null;
}
}));
example.hello.tk.dispose();
window.setTimeout((function (){
return console.log("should restart here");
}),1000);
jayq.core.document_ready.call(null,(function (){
jayq.core.inner.call(null,jayq.core.css.call(null,jayq.core.$.call(null,"\uFDD0:h1"),cljs.core.ObjMap.fromObject(["\uFDD0:background"],{"\uFDD0:background":"blue"})),"Yo dawg, I heard you like Clojure so I put Clojure in your javascript so you can FP while you JS!");
example.hello.rx_stuff.call(null);
return clojure.browser.repl.connect.call(null,"http://localhost:9000/repl");
}));
