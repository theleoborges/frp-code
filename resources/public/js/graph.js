var chart = d3.select("#graph").append("svg").attr("width", "600").attr("height", "300");

// Create the bar chart which consists of ten SVG rectangles, one for each piece of data
var rects = chart.selectAll('rect').data([0, 0, 0])
                 .enter().append('rect')
                 .attr("stroke", "none").attr("fill", "rgb(7, 130, 180)")
                 .attr("x", 0)
                 .attr("y", function(d, i) { return 25 * i; } )
                 .attr("width", function(d) { return 20 * d; } )
                 .attr("height", "20");

var updateGraph = function(newData) {
  rects.data(newData)
    .transition().duration(1000).delay(200)
    .attr("width", function(d) { return d * 20; } );
}
