package converter

enum class unit(val measure: List<String>, val rate: Double, val type: String) {
    M(listOf("m", "meter", "meters"), 1.0, "Length"),
    KM(listOf("km", "kilometer", "kilometers"), 1000.0, "Length"),
    CM(listOf("cm", "centimeter", "centimeters"), 0.01, "Length"),
    MM(listOf("mm", "millimeter", "millimeters"), 0.001, "Length"),
    MI(listOf("mi", "mile", "miles"), 1609.35, "Length"),
    YD(listOf("yd", "yard", "yards"), 0.9144, "Length"),
    FT(listOf("ft", "foot", "feet"), 0.3048, "Length"),
    IN(listOf("in", "inch", "inches"), 0.0254, "Length"),
    G(listOf("g", "gram", "grams"), 1.0, "Weight"),
    KG(listOf("kg", "kilogram", "kilograms"), 1000.0, "Weight"),
    MG(listOf("mg", "milligram", "milligrams"), 0.001, "Weight"),
    LB(listOf("lb", "pound", "pounds"), 453.592, "Weight"),
    OZ(listOf("oz", "ounce", "ounces"), 28.3495, "Weight"),
    C(listOf("c", "degree celsius", "degrees celsius", "dc", "celsius"), 0.0, "Temperature"),
    F(listOf("f", "degree fahrenheit", "degrees fahrenheit", "df", "fahrenheit"), 0.0, "Temperature"),
    K(listOf("k", "kelvin", "kelvins"), 0.0, "Temperature")
}

fun CToF(celsius: Double) = celsius * (9.0/5.0) + 32
fun FToC(fahrenheit: Double) = (fahrenheit - 32) * (5.0/9.0)
fun KToC(kelvin: Double) = kelvin - 273.15
fun CToK(celsius: Double) = celsius + 273.15
fun FToK(fahrenheit: Double) = (fahrenheit + 459.67) * (5.0/9.0)
fun KToF(kelvin: Double) = kelvin * (9.0/5.0) - 459.67

fun printTemperature(unitOne: unit, unitTwo: unit, number: Double, result: Double) {
    println("$number ${if (number == 1.0) unitOne.measure[1] else unitOne.measure[2]} is $result ${if (result == 1.0) unitTwo.measure[1] else unitTwo.measure[2]}")
}

fun main() {
    while (true) {
        var foundMeasure = false
        var foundMeasure2 = false
        lateinit var unitOne: unit
        lateinit var unitTwo: unit
        lateinit var numberT: String
        lateinit var measure: String
        lateinit var measure2: String

        print("Enter what you want to convert (or exit): ")
        val lines = readln()
        if (lines == "exit") {
            break
        }
        val splitedLines = lines.split(" ")
        if (splitedLines.size == 5) {
            numberT = splitedLines[0]
            if (numberT.toDoubleOrNull() == null) {
                println("Parse error")
                continue
            }
            if (splitedLines[3].lowercase() in listOf("in", "to")) {
                measure = "${splitedLines[1]} ${splitedLines[2]}"
                measure2 = splitedLines.last()
            } else {
                measure = splitedLines[1]
                measure2 = "${splitedLines[3]} ${splitedLines[4]}"
            }

        } else if (splitedLines.size == 4){
            numberT = splitedLines[0]
            if (numberT.toDoubleOrNull() == null) {
                println("Parse error")
                continue
            }
            measure = splitedLines[1]
            measure2 = splitedLines.last()
        } else if (splitedLines.size == 6) {
            numberT = splitedLines[0]
            if (numberT.toDoubleOrNull() == null) {
                println("Parse error")
                continue
            }
            if (splitedLines[3].lowercase() in listOf("in", "to")) {
                measure = "${splitedLines[1]} ${splitedLines[2]}"
                measure2 = "${splitedLines[4]} ${splitedLines[5]}"
            } else {
                println("Parse Error")
                continue
            }
        } else {
            println("Parse error")
            continue
        }
        
        for (unit1 in unit.values()) {
            if (measure.lowercase() in unit1.measure) {
                foundMeasure = true
                unitOne = unit1
            }
        }
        for (unit2 in unit.values()) {
            if (measure2.lowercase() in unit2.measure) {
                foundMeasure2 = true
                unitTwo = unit2
            }
        }

        if (!foundMeasure || !foundMeasure2) {
            println("Conversion from ${if (foundMeasure) unitOne.measure[2] else "???"} to ${if (foundMeasure2) unitTwo.measure[2] else "???"} is impossible")
            continue
        }

        if (unitOne.type == unitTwo.type) {
            if (unitOne.type == "Temperature") {
                if (unitOne.measure[0] == "c" && unitTwo.measure[0] == "k") printTemperature(unitOne, unitTwo, numberT.toDouble(), CToK(numberT.toDouble()))
                if (unitOne.measure[0] == "c" && unitTwo.measure[0] == "f") printTemperature(unitOne, unitTwo, numberT.toDouble(), CToF(numberT.toDouble()))
                if (unitOne.measure[0] == "k" && unitTwo.measure[0] == "c") printTemperature(unitOne, unitTwo, numberT.toDouble(), KToC(numberT.toDouble()))
                if (unitOne.measure[0] == "k" && unitTwo.measure[0] == "f") printTemperature(unitOne, unitTwo, numberT.toDouble(), KToF(numberT.toDouble()))
                if (unitOne.measure[0] == "f" && unitTwo.measure[0] == "c") printTemperature(unitOne, unitTwo, numberT.toDouble(), FToC(numberT.toDouble()))
                if (unitOne.measure[0] == "f" && unitTwo.measure[0] == "k") printTemperature(unitOne, unitTwo, numberT.toDouble(), FToK(numberT.toDouble()))
                if (unitOne.measure[0] == "c" && unitTwo.measure[0] == "c") printTemperature(unitOne, unitTwo, numberT.toDouble(), numberT.toDouble())
                if (unitOne.measure[0] == "f" && unitTwo.measure[0] == "f") printTemperature(unitOne, unitTwo, numberT.toDouble(), numberT.toDouble())
                if (unitOne.measure[0] == "k" && unitTwo.measure[0] == "k") printTemperature(unitOne, unitTwo, numberT.toDouble(), numberT.toDouble())
            } else {
                var number = numberT.toDouble()
                if (number < 0) {
                    println("${unitOne.type} shouldn't be negative.")
                    continue
                }
                var result = number * unitOne.rate / unitTwo.rate
                println("$number ${if (number == 1.0) unitOne.measure[1] else unitOne.measure[2]} is $result ${if (result == 1.0) unitTwo.measure[1] else unitTwo.measure[2]}")
            }
        } else {
            println("Conversion from ${unitOne.measure[2]} to ${unitTwo.measure[2]} is impossible")
            continue
        }
        
    } 
}
