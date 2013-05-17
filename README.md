# Taming asynchronous workflows with FRP

This repository contains the sample project I used during my FRP talk at YOW! LambdaJam Australia 2013. 

Slides are in the root directory.
To play around with this example project, you will first need
[Leiningen][1] installed.

## Running the App

Set up and start the server like this:

    $ lein cljsbuild once
    $ lein ring server-headless 3000

Now, point your web browser at `http://localhost:3000`, and see the web app in action!


## What to look for

There's a number of files you might be interested in looking at:

On the client side (`src-cljs` directory):

- `hello.cljs` - This is our entry point.
- `observables.cljs` - Contains most of the interactions with [RxJS][2]

On the server side (`src-clj` directory):

- `routes.clj`   - The server entry point
- `services.clj` - Our dummy server call that returns random results on each request


[1]: https://github.com/technomancy/leiningen
[2]: https://github.com/Reactive-Extensions/RxJS

Copyright © 2012 [Leonardo Borges](http://www.leonardoborges.com)