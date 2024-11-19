// main.js

let measureX = 0.60;
let measureY = 0.80;

let canvasWidth = window.innerWidth * measureX;
let canvasHeight = window.innerHeight * measureY;

let scaleX = canvasWidth / 20;
let scaleY = canvasHeight / 20;

let posX = canvasWidth / scaleX / 2;
let posY = canvasHeight / scaleY / 2;

let lineWidth = 0.1;
let color = $("#color").val();

const canvas = $("#canvas")[0];
const ctx = canvas.getContext("2d");

canvas.width = canvasWidth;
canvas.height = canvasHeight;
ctx.lineWidth = lineWidth;
ctx.scale(scaleX, scaleY);

Array.prototype.interval = function (start, step, end) {
    for (let i = 0; i <= (end - start) / step; i++) {
        this[i] = start + step * i;
    }
};

let xInterval = $("#x-interval").val().split(":");
let xStart = parseFloat(xInterval[0]);
let xStep = parseFloat(xInterval[1]);
let xEnd = parseFloat(xInterval[2]);

let x = [];
let y = [];

x.interval(xStart, xStep, xEnd);

function evalFunction(x, y, f) {
    x.forEach((x, i) => {
        y[i] = -(eval(f));
    });
}

function clearCanvas() {
    ctx.clearRect(0, 0, canvasWidth, canvasHeight);
}

function drawCross(x, y) {
    ctx.lineWidth = lineWidth * 0.1;
    ctx.beginPath();
    ctx.moveTo(x, 0);
    ctx.lineTo(x, canvasHeight);
    ctx.stroke();
    ctx.beginPath();
    ctx.moveTo(0, y);
    ctx.lineTo(canvasWidth, y);
    ctx.stroke();
}

function renderPlot(x, y) {
    ctx.strokeStyle = color;
    ctx.lineWidth = lineWidth;
    ctx.beginPath();
    ctx.moveTo(x[0] + posX, y[0] + posY);
    for (let i = 1; i < x.length; i++) {
        ctx.lineTo(x[i] + posX, y[i] + posY);
    }
    ctx.stroke();
}

$("#input").on("change", () => {
    let translatedExpression = translateMathExpression($("#input").val());
    evalFunction(x, y, translatedExpression);
    renderPlot(x, y);
});

$("#color").on("input", () => {
    color = $("#color").val();
    clearCanvas();
    renderPlot(x, y);
    drawCross(posX, posY);
});

let leftMouseButton = false;

$("#canvas").on("mousedown", (event) => {
    leftMouseButton = !leftMouseButton;
});

$("#canvas").on("mousemove", (event) => {
    if (leftMouseButton) {
        posX = event.offsetX / scaleX;
        posY = event.offsetY / scaleY;

        canvas.style.cursor = "move";
    } else {
        canvas.style.cursor = "pointer";
    }

    clearCanvas();
    renderPlot(x, y);
    drawCross(posX, posY);
});

$("#x-interval").on("change", () => {
    xInterval = $("#x-interval").val().split(":");
    xStart = parseFloat(xInterval[0]);
    xStep = parseFloat(xInterval[1]);
    xEnd = parseFloat(xInterval[2]);

    x = [];
    x.interval(xStart, xStep, xEnd);

    clearCanvas();
    evalFunction(x, y, translateMathExpression($("#input").val()));
    renderPlot(x, y);
});

$("#width").on("input", event => {
    canvasWidth = parseInt(event.target.value);
    scaleX = canvasWidth / 20;
    posX = canvasWidth / scaleX / 2;
    canvas.width = canvasWidth;
    ctx.scale(scaleX, scaleY);

    clearCanvas();
    renderPlot(x, y);
    drawCross(posX, posY);
});

$("#height").on("input", event => {
    canvasHeight = parseInt(event.target.value);
    scaleY = canvasHeight / 20;
    posY = canvasHeight / scaleY / 2;
    canvas.height = canvasHeight;
    ctx.scale(scaleX, scaleY);

    clearCanvas();
    renderPlot(x, y);
    drawCross(posX, posY);
});

$(window).on("resize", () => {
    canvasWidth = window.innerWidth * measureX;
    canvasHeight = window.innerHeight * measureY;
    scaleX = canvasWidth / 20;
    scaleY = canvasHeight / 20;
    posX = canvasWidth / scaleX / 2;
    posY = canvasHeight / scaleY / 2;

    canvas.width = canvasWidth;
    canvas.height = canvasHeight;
    ctx.scale(scaleX, scaleY);

    clearCanvas();
    renderPlot(x, y);
    drawCross(posX, posY);
});

$(document).ready(() => {
    drawCross(posX, posY);
    $("#width").val(canvasWidth);
    $("#height").val(canvasHeight);
});