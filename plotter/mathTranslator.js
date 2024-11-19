// mathTranslator.js

function translateMathExpression(expression) {
    const mathFunctions = {
        sin: "Math.sin",
        cos: "Math.cos",
        tan: "Math.tan",
        sqrt: "Math.sqrt",
        log: "Math.log",
        exp: "Math.exp",
        atan: "Math.atan",
        asin: "Math.asin",
        acos: "Math.acos",
        abs: "Math.abs",
        ceil: "Math.ceil",
        floor: "Math.floor",
        round: "Math.round",
        min: "Math.min",
        max: "Math.max",
        pow: "Math.pow"
    };

    expression = expression.replace(/\b(sin|cos|tan|sqrt|log|exp|atan|asin|acos|abs|ceil|floor|round|min|max|pow)\b/g, (match) => mathFunctions[match]);

    expression = expression
        .replace(/\|([^\|]+)\|/g, "Math.abs($1)") // absolute value |x|
        .replace(/\^/g, "**"); // exponentiation **

    return expression;
}