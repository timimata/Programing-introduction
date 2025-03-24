import java.io.File

val dataInvalida = "Data invalida"
val menor = "Menor de idade nao pode jogar"
val respostaInvalida = "Resposta invalida"

fun criaMenu(): String {
    return "\n" + "Bem vindo ao jogo das tendas\n" + "\n" + "1 - Novo jogo\n" + "0 - Sair\n"
}

fun validaTamanhoMapa(numLinhas: Int, numColunas: Int): Boolean {

    if (numLinhas == 6 && numColunas == 5 || numLinhas == 6 && numColunas == 6) {
        return true
    } else if (numLinhas == 8 && numColunas == 8 || numLinhas == 8 && numColunas == 10) {
        return true
    } else if (numLinhas == 10 && numColunas == 8 || numLinhas == 10 && numColunas == 10) {
        return true
    }
    return false
}

fun validaDataNascimento(data: String?): String? {
    if (data == null || data.length != 10) {
        return dataInvalida
    } else if (data[2] != '-' && data[5] != '-') {
        return dataInvalida
    } else {
        val ano: String = data[6].toString() + data[7].toString() + data[8].toString() + data[9].toString()
        val anoInt: Int = ano.toInt()
        val mes: String = data[3].toString() + data[4].toString()
        val mesInt: Int = mes.toInt()
        val dia: String = data[0].toString() + data[1].toString()
        val diaInt: Int = dia.toInt()

        if (diaInt !in 0..31 || mesInt !in 1..12 || anoInt !in 1900..2021) {
            return dataInvalida
        } else if (((mes == "04" || mes == "06" || mes == "09" || mes == "11") && (dia == "31"))) {
            return dataInvalida
        } else if (mes == "02" && dia == "29" && !((anoInt % 4 == 0 && anoInt % 100 != 0) || anoInt % 400 == 0)) {
            return dataInvalida
        } else if (dia >= "01" && mes == "11" && anoInt == 2004 || mes == "12" && anoInt == 2004) {
            return menor
        } else if (anoInt > 2004) {
            return menor
        } else return null
    }
}

fun criaLegendaHorizontal(numColunas: Int): String {
    when {
        numColunas == 4 -> return "A | B | C | D"
        numColunas == 3 -> return "A | B | C"
        numColunas == 5 -> return "A | B | C | D | E"
        numColunas == 6 -> return "A | B | C | D | E | F"
        numColunas == 7 -> return "A | B | C | D | E | F | G"
        numColunas == 8 -> return "A | B | C | D | E | F | G | H"
        numColunas == 10 -> return "A | B | C | D | E | F | G | H | I | J"
    }
    return ""
}

fun processaCoordenadas(coordenadasStr: String?, numLines: Int, numColumns: Int): Pair<Int, Int>? {
    if (coordenadasStr == null || coordenadasStr.length < 3) {
        return null
    } else {
        val coordenadaLinhaDez = coordenadasStr[0].code
        val coordenadaColunaDez = coordenadasStr[2].code
        if (numColumns == 10 && coordenadasStr.length > 3 && coordenadasStr[2] != ','
            && coordenadaColunaDez !in 65..74 && coordenadaLinhaDez !in 49..57
        ) {
            return null
        } else if (numLines == 10 && coordenadasStr.length > 4 && coordenadasStr[2] != ','
            && coordenadasStr[0] != '1' && coordenadasStr[1] != '0'
        ) {
            return null
        } else {
            if (coordenadasStr[1] != ',' || coordenadasStr.length > 3) {
                return null
            } else {
                val coordenadaLinha = coordenadasStr[0].code
                val coordenadaColuna = coordenadasStr[2].code
                if (numLines == 8 && coordenadaLinha !in 49..56) {
                    return null
                } else if (numLines == 6 && coordenadaLinha !in 49..54) {
                    return null
                } else if (numColumns == 5 && coordenadaColuna !in 65..69) {
                    return null
                } else if (numColumns == 6 && coordenadaColuna !in 65..70) {
                    return null
                } else if (numColumns == 8 && coordenadaColuna !in 65..72) {
                    return null
                } else if (numLines == 1 && coordenadaLinha != 49) {
                    return null
                } else {
                    val partesCoordenada = coordenadasStr.split(",")
                    val coordLinha = partesCoordenada[0].toInt() - 1
                    var coordColuna = 0

                    if (partesCoordenada[1] == "A") {
                        coordColuna = 0
                    } else if (partesCoordenada[1] == "B") {
                        coordColuna = 1
                    } else if (partesCoordenada[1] == "C") {
                        coordColuna = 2
                    } else if (partesCoordenada[1] == "D") {
                        coordColuna = 3
                    } else if (partesCoordenada[1] == "E") {
                        coordColuna = 4
                    } else if (partesCoordenada[1] == "F") {
                        coordColuna = 5
                    } else if (partesCoordenada[1] == "G") {
                        coordColuna = 6
                    } else if (partesCoordenada[1] == "H") {
                        coordColuna = 7
                    } else if (partesCoordenada[1] == "I") {
                        coordColuna = 8
                    } else if (partesCoordenada[1] == "J") {
                        coordColuna = 9
                    }
                    return Pair(coordLinha, coordColuna)
                }
                return null
            }
        }
    }
}

fun criaLegendaContadoresHorizontal(contadoresHorizontal: Array<Int?>): String {
    var contador = ""
    for (x in 0..contadoresHorizontal.size - 1) {
        if (contadoresHorizontal[x] == null) {
            contador += "    "
        } else {
            contador += "${contadoresHorizontal[x]}   "
        }
    }
    return contador.substring(0, contador.length - 3)
}

fun leContadoresDoFicheiro(numLines: Int, numColumns: Int, verticais: Boolean): Array<Int?>? {
    var resultado: Array<Int?>? = null
    var legendavertical = -1
    if (verticais == true) {
        legendavertical = 0
    } else {
        legendavertical = 1
    }
    val linha: String = File("${numLines}x${numColumns}.txt").readLines()[legendavertical]

    val coluna = linha.split(",")
    resultado = arrayOfNulls<Int>(coluna.size)

    for (a in 0 until coluna.size) {

        if (coluna[a] == "0") {
            resultado[a] = null
        } else {
            resultado[a] = coluna[a].toInt()
        }
    }
    return resultado
}

fun leTerrenoDoFicheiro(numLines: Int, numColumns: Int): Array<Array<String?>> {
    val leituraFicheiro = File("${numLines}x${numColumns}.txt").readLines()

    val terrenoTabuleiro: Array<Array<String?>> = Array(numLines) { Array(numColumns) { null } }

    for (x in 2..leituraFicheiro.size - 1) {
        val linhaficheiro = leituraFicheiro[x]
        val partes = linhaficheiro.split(",")
        for (linha in 0 until terrenoTabuleiro.size) {
            if (linha == partes[0].toInt()) {
                for (y in 0 until terrenoTabuleiro[linha].size) {
                    if (partes[1].toInt() == y) {
                        terrenoTabuleiro[linha][y] = "A"
                    }
                }
            }
        }
    }
    return terrenoTabuleiro
}

fun criaTerreno(
    terreno: Array<Array<String?>>,
    contadoresVerticais: Array<Int?>?,
    contadoresHorizontais: Array<Int?>?,
    mostraLegendaHorizontal: Boolean,
    mostraLegendaVertical: Boolean
): String {

    var resultadoFinal = ""
    val terrenoLinha = terreno.size
    val terrenoDaColuna = terreno[terreno.size - 1].size

    if (mostraLegendaVertical && contadoresHorizontais!=null) {
        if (terrenoLinha == 6 && terrenoDaColuna == 5) {
            resultadoFinal += "       ${criaLegendaContadoresHorizontal(contadoresHorizontais!!)}\n"
        } else if (terrenoLinha == 6 && terrenoDaColuna == 6) {
            resultadoFinal += "       ${criaLegendaContadoresHorizontal(contadoresHorizontais!!)}\n"
        } else if (terrenoLinha == 8 && terrenoDaColuna == 8) {
            resultadoFinal += "       ${criaLegendaContadoresHorizontal(contadoresHorizontais!!)}\n"
        } else if (terrenoLinha == 8 && terrenoDaColuna == 10) {
            resultadoFinal += "       ${criaLegendaContadoresHorizontal(contadoresHorizontais!!)}\n"
        } else if (terrenoLinha == 10 && terrenoDaColuna == 8) {
            resultadoFinal += "       ${criaLegendaContadoresHorizontal(contadoresHorizontais!!)}\n"
        } else if (terrenoLinha == 10 && terrenoDaColuna == 10) {
            resultadoFinal += "       ${criaLegendaContadoresHorizontal(contadoresHorizontais!!)}\n"
        }
    }
/*    else{
            if(terrenoLinha==6 && terrenoDaColuna==5){
                resultadoFinal += "       ${criaLegendaContadoresHorizontal(contadoresHorizontais!!)}\n"
            }
            else if(terrenoLinha==6 && terrenoDaColuna==6 ){
                resultadoFinal += "       ${criaLegendaContadoresHorizontal(contadoresHorizontais!!)}\n"
            }
            else if(terrenoLinha==8 && terrenoDaColuna==8 ){
                resultadoFinal += "       ${criaLegendaContadoresHorizontal(contadoresHorizontais!!)}\n"
            }
            else if(terrenoLinha==8 && terrenoDaColuna==10 ){
                resultadoFinal += "       ${criaLegendaContadoresHorizontal(contadoresHorizontais!!)}\n"
            }
            else if(terrenoLinha==10 && terrenoDaColuna==8){
                resultadoFinal += "       ${criaLegendaContadoresHorizontal(contadoresHorizontais!!)}\n"
            }
            else if(terrenoLinha==10 && terrenoDaColuna==10){
                resultadoFinal += "       ${criaLegendaContadoresHorizontal(contadoresHorizontais!!)}\n"
            }
    }*/

    if (mostraLegendaHorizontal != false) {
        resultadoFinal += "     | ${criaLegendaHorizontal(terrenoDaColuna)}\n"
    }


    for (linha in 0..terreno.size - 1) {

        /*if(contadoresVerticais!![linha]==null) {
            resultadoFinal += "   ${linha + 1} "
        }
        else if(mostraLegendaHorizontal ==true && mostraLegendaVertical==true){

            resultadoFinal +="${contadoresVerticais!![linha]}  ${linha + 1} "
        }*/

        if(mostraLegendaVertical) {
            resultadoFinal += "   ${linha + 1} "
        } else {
                resultadoFinal += "     "
            }
        for (coluna in 0..terreno[linha].size) {
            if (terrenoDaColuna > coluna) {
                when {
                    terreno[linha][coluna] == "T" -> {
                        if (coluna == terrenoDaColuna - 1) {
                            resultadoFinal += "| T"
                        } else {
                            resultadoFinal += "| T "
                        }
                    }

                    terreno[linha][coluna] == "A" -> {
                        if (coluna == terrenoDaColuna - 1) {
                            resultadoFinal += "| △"
                        } else {
                            resultadoFinal += "| △ "
                        }
                    }

                    terreno[linha][coluna] == null -> {
                        if (coluna == terrenoDaColuna - 1) {
                            resultadoFinal += "|  "
                        } else {
                            resultadoFinal += "|   "
                        }
                    }
                }
            }
        }
        if (terrenoLinha == linha + 1) {
            resultadoFinal += ""
        } else {
            resultadoFinal += "\n"
        }
    }
    return resultadoFinal
}

fun temArvoreAdjacente(terreno: Array<Array<String?>>, coords: Pair<Int, Int>): Boolean {

    for (x in 0..terreno.size - 1) {
        for (y in 0..terreno[x].size) {
            if (coords.second + 1 < y) {
                if (terreno[coords.first][coords.second + 1] == "A") {
                    return true
                }
            } else if (coords.second - 1 >= y) {
                if (terreno[coords.first][coords.second - 1] == "A") {
                    return true
                }
            } else if (coords.first + 1 <= x) {
                if (terreno[coords.first + 1][coords.second] == "A") {
                    return true
                }
            } else if (coords.first - 1 >= x) {
                if (terreno[coords.first - 1][coords.second] == "A") {
                    return true
                }
            }
        }
    }

    return false
}

fun temTendaAdjacente(terreno: Array<Array<String?>>, coords: Pair<Int, Int>): Boolean {

    for (linha in 0..terreno.size - 1) {
        for (coluna in 0..terreno[linha].size) {
            if (coords.first + 1 <= linha && coords.second - 1 >= coluna) {
                if (terreno[coords.first + 1][coords.second - 1] == "T") {
                    return true
                }
            } else if (coords.first + 1 <= linha && coords.second + 1 < coluna) {
                if (terreno[coords.first + 1][coords.second + 1] == "T") {
                    return true
                }
            } else if (coords.first - 1 >= linha && coords.second + 1 < coluna) {
                if (terreno[coords.first - 1][coords.second + 1] == "T") {
                    return true
                }
            } else if (coords.first - 1 >= linha && coords.second - 1 >= coluna) {
                if (terreno[coords.first - 1][coords.second - 1] == "T") {
                    return true
                }
            } else if (coords.second + 1 < coluna) {
                if (terreno[coords.first][coords.second + 1] == "T") {
                    return true
                }
            } else if (coords.second - 1 >= coluna) {
                if (terreno[coords.first][coords.second - 1] == "T") {
                    return true
                }
            } else if (coords.first + 1 <= linha) {
                if (terreno[coords.first + 1][coords.second] == "T") {
                    return true
                }
            } else if (coords.first - 1 >= linha) {
                if (terreno[coords.first - 1][coords.second] == "T") {
                    return true
                }
            }
        }
    }
    return false
}

fun contaTendasColuna(terreno: Array<Array<String?>>, coluna: Int): Int {
    var resultado = 0
    for (x in 0 until terreno.size) {
        if (terreno[x][coluna] == "T") {
            resultado++
        }
    }
    return resultado
}

fun contaTendasLinha(terreno: Array<Array<String?>>, linha: Int): Int {
    var resultado = 0
    for (y in 0 until terreno[linha].size) {
        if (terreno[linha][y] == "T") {
            resultado++
        }
    }
    return resultado
}

fun colocaTenda(terreno: Array<Array<String?>>, coords: Pair<Int, Int>): Boolean {

    for (x in 0..terreno.size - 1) {
        if (x == coords.first)
            for (y in 0..terreno[x].size) {
                if (y == coords.second && temArvoreAdjacente(terreno, coords)) {
                    terreno[x][y] = "T"
                    return true
                }
            }
    }

    return false
}

fun terminouJogo(
    terreno: Array<Array<String?>>,
    contadoresVerticais: Array<Int?>,
    contadoresHorizontais: Array<Int?>
): Boolean {

    val contaTendaLinha = IntArray(terreno.size)
    val contaTendaColuna = IntArray(terreno[terreno.size - 1].size)
    var contaColuna = 0
    var contaLinha = 0

    for (x in 0..terreno.size - 1) {
        contaLinha = contaTendasLinha(terreno, x)
        contaTendaLinha[x] = contaLinha
        for (y in 0..terreno[x].size - 1) {
            if (temArvoreAdjacente(terreno, Pair(x, y))) {
                contaColuna = contaTendasColuna(terreno, y)
                contaTendaColuna[y] = contaColuna
            }
        }
    }
    if (contaTendaLinha.size == contadoresVerticais.size) {
        for (x in 0..contaTendaLinha.size - 1) {
            if (contaTendaLinha[x] != contadoresVerticais[x]) {
                return false
            }
        }
    }
    if (contaTendaColuna.size == contadoresHorizontais.size) {
        for (y in 0..contaTendaColuna.size - 1) {
            if (contaTendaColuna[y] != contadoresHorizontais[y]) {
                return false
            }
        }
    }
    return true
}

fun quantasLinhas() {
    println("Quantas linhas?")
}

fun quantasColunas() {
    println("Quantas colunas?")

}

fun main() {

//println(criaLegendaContadoresVertical(arrayOf(0,2,3,4,6,7)))
//println(criaTerreno(leTerrenoDoFicheiro(6,5), leContadoresDoFicheiro(6, 5, false), leContadoresDoFicheiro(6, 5, true), true, true))
//println(criaLegendaContadoresVertical(arrayOf(0,2,3,4,5)))

    println(criaMenu())
    var inicioJogo: Int? = -1
    if (inicioJogo == 0) {
        return
    } else {
        do {
            inicioJogo = readLine()?.toIntOrNull() ?: -1
            if (inicioJogo != 1 && inicioJogo != 0) {
                println("Opcao invalida")
                println(criaMenu())
            }
        } while (inicioJogo != 1 && inicioJogo != 0)
        if (inicioJogo == 0) {
            return
        } else {
            quantasLinhas()
            var numerolinhas = 1
            do {
                when {
                    numerolinhas !in 1..10 -> println(respostaInvalida + "\n" + "Quantas linhas?")
                }
                numerolinhas = readLine()?.toIntOrNull() ?: -1
            } while (numerolinhas !in 1..10)
            quantasColunas()
            var numerocolunas = 1
            do {
                when {
                    numerocolunas !in 1..10 -> println(respostaInvalida + "\n" + "Quantas colunas?")
                }
                numerocolunas = readLine()?.toIntOrNull() ?: -1
            } while (numerocolunas !in 1..10)
            val valida = validaTamanhoMapa(numerolinhas, numerocolunas)
            validaTamanhoMapa(numerolinhas, numerocolunas)
            val inicioJogo2: Int? = -1
            val linha2coluna2: Int? = -1
            while (valida != true && inicioJogo2 != 0) {
                println("Terreno invalido")
                main()
            }
            var datanascimento2 = "ola"
            var quebraCiclo = 0
            when {
                numerolinhas == 10 && numerocolunas == 10 || linha2coluna2 == 20 -> do {
                    println("Qual a sua data de nascimento? (dd-mm-yyyy)")
                    val datanascimento = readLine() ?: "ola"
                    datanascimento2 = datanascimento
                    if (validaDataNascimento(datanascimento2) == menor) {
                        quebraCiclo++
                    } else if (validaDataNascimento(datanascimento2) == dataInvalida) {
                        println(validaDataNascimento(datanascimento2))
                    }
                } while (validaDataNascimento(datanascimento2) != null && quebraCiclo == 0)
            }
            val quebraCiclo2 = 0
            when {
                (quebraCiclo != 0) -> do {
                    println(validaDataNascimento(datanascimento2))
                    main()
                } while (quebraCiclo2 != 0)
            }
            println()
            println(criaTerreno(leTerrenoDoFicheiro(numerolinhas,numerocolunas), leContadoresDoFicheiro(numerolinhas, numerocolunas, true), leContadoresDoFicheiro(numerolinhas, numerocolunas, true), true, true))
            do {
                println("Coordenadas da tenda? (ex: 1,B)")
                val coordenadasStrlidas: String? = readLine()
                if (processaCoordenadas(coordenadasStrlidas, numerolinhas, numerocolunas) == null) {
                    println("Coordenadas invalidas")
                }
            } while (processaCoordenadas(coordenadasStrlidas, numerolinhas, numerocolunas) == null)

        }
    }
}



