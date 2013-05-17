# Taming asynchronous workflows with FRP

This repository contains the sample project I used during my FRP talk at YOW! LambdaJam Australia 2013. 

Slides are in the root directory.
To play around with this example project, you will first need
[Leiningen][4] installed.

## Running the App

Set up and start the server like this:

    $ lein deps
    $ lein cljsbuild once
    $ lein ring server-headless 3000

Now, point your web browser at `http://localhost:3000`, and see the web app in action!

[4]: https://github.com/technomancy/leiningen
