package pl.allegro.follower.util

fun String.strPriceToFloat():Float{
    var newStr :String= this.replace(" ","")
    newStr = newStr.replace(",",".")
    newStr = newStr.replace("[^0-9.]".toRegex(),"")
    return newStr.toFloat()
}
