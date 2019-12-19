
var ExpressionWashTrans118 = function(arg) {
    var reg2 = /[1-9]\d+/;

if (arg!=''){
   return (reg2.exec(arg) || [''])[0];
}

return '';
 };
