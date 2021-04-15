var CANVAS_WIDTH = 1000;
var CANVAS_HEIGHT = 800;
var BACKGROUND = [215, 215, 225];

function setup() {
    createCanvas(CANVAS_WIDTH, CANVAS_HEIGHT);
    background(color(BACKGROUND));
    noLoop();

    let server = new WebSocket("ws://localhost");
    server.onmessage = (event) => {
        if (event.data.substring(0, 4) === "draw") {
            let simulation = parseData(event.data.substring(4));
            drawScene(simulation);
        }
       
    }
}

function drawScene(scene) {
    background(color(BACKGROUND));
    for(particle of scene.particles) {
        fill(color(particle.color));
        strokeWeight(0);
        circle(particle.x, particle.y, particle.radius*2);
    }
    for(line in scene.lines) {

    }
}

function parseData(data) {
    return JSON.parse(data);
}
