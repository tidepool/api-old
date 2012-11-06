<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="http://d3js.org/d3.v2.js"></script>
<style type="text/css">
.link { stroke: #fff; }
.nodetext { pointer-events: none; font: 10px sans-serif; }
</style>
</head>
<body>

<script type="text/javascript">

var w = 1000,
    h = 600

var vis = d3.select("body").append("svg:svg")
    .attr("width", w)
    .attr("height", h);

d3.json("graph.json", function(json) {
    var force = self.force = d3.layout.force()
        .nodes(json.nodes)
        .links(json.links)
        .gravity(.01)
        .distance(200)
        .charge(-10)
        .size([w, h])
        .start();

     var link = vis.selectAll("line.link")
        .data(json.links)
        .enter().append("svg:line")
        .attr("class", "link")
        .attr("x1", function(d) { return d.source.x; })
        .attr("y1", function(d) { return d.source.y; })
        .attr("x2", function(d) { return d.target.x; })
        .attr("y2", function(d) { return d.target.y; }); 

    var node_drag = d3.behavior.drag()
        .on("dragstart", dragstart)
        .on("drag", dragmove)
        .on("dragend", dragend);

    function dragstart(d, i) {
    	console.log("start x,y: " + d.x + "," + d.y);
        force.stop() // stops the force auto positioning before you start dragging
    }

    function dragmove(d, i) {
        d.px += d3.event.dx;
        d.py += d3.event.dy;
        d.x += d3.event.dx;
        d.y += d3.event.dy; 
        console.log("drag x,y: " + d.x + "," + d.y);
        tick(); // this is the key to make it work together with updating both px,py,x,y on d !
    }

    function dragend(d, i) {
    	console.log("end x,y: " + d.x + "," + d.y);
    	d.fixed = true; // of course set the node to fixed so the force doesn't include the node in its auto positioning stuff
        tick();
        //force.resume();
    }


    var node = vis.selectAll("g.node")
        .data(json.nodes)
      .enter().append("svg:g")
        .attr("class", "node")
        .call(node_drag);

    node.append("svg:image")
        .data(json.nodes)
        .attr("class", "circle")
        .attr("xlink:href", function(d) { return d.name == 'YOU' ? '<c:url value="/resources/bootstrap/img/blue_circle.png" />' : '<c:url value="/resources/bootstrap/img/green_circle.png" />' ; })       
        .attr("x", function(d) { return d.name == 'YOU' ? '-120px' : '-30px' ; })
        .attr("y", function(d) { return d.name == 'YOU' ? '-150px' : '-70px' ; })
        .attr("width", function(d) { return d.name == 'YOU' ? '300px' : '150px' ; })
        .attr("height", function(d) { return d.name == 'YOU' ? '300px' : '150px' ; });

    node.append("svg:text")
        .attr("class", "nodetext")
        .attr("dx", 12)
        .attr("dy", ".35em")
        .text(function(d) { return d.name });

    force.on("tick", tick);

    function tick() {
      link.attr("x1", function(d) { return d.source.x; })
          .attr("y1", function(d) { return d.source.y; })
          .attr("x2", function(d) { return d.target.x; })
          .attr("y2", function(d) { return d.target.y; });

      node.attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; });
    };


});

</script>
</body>
</html>