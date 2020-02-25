package pl.allegro.follower.util

fun textToFloat(str:String):Float{

    var new_str :String= str.replace(" ","")
    new_str = new_str.replace(",",".")
    new_str = new_str.replace("[^0-9.]".toRegex(),"")


    return new_str.toFloat()
}