import java.io.File

val alfabeto = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
val balaoazul = "\u001b[34mϙ\u001b[0m"
val balaovermelho = "\u001b[31mϙ\u001b[0m"

fun validaTabuleiro(numlinhas: Int? = null,numcolunas: Int? = null):Boolean {
    if (numlinhas in 5..7 && numcolunas in 6..8){
        when{
            numlinhas == 5 && numcolunas == 6 -> return true
            numlinhas == 6 && numcolunas == 7 -> return true
            numlinhas == 7 && numcolunas == 8 -> return true
            else -> return false
        }
    }else{
        return false
    }
}

fun processaColuna(numcolunas: Int, letraColuna: String?): Int? {
    if (letraColuna != null) {
        if (letraColuna in "A".."Z") {
            if (alfabeto[numcolunas - 1] >= letraColuna.first()) {
                var posicao = 0
                while (alfabeto[posicao] != letraColuna.first()){
                    posicao++
                }
                return posicao
            } else {
                return null
            }
        } else {
            return null
        }
    }else{
        return null
    }
}

fun nomeValido(nome: String):Boolean{
    var letrasnome = 0
    val tamanhonome = nome.length
    if (tamanhonome == 0){
        return false
    }
    while(tamanhonome > letrasnome) {
        if (nome[letrasnome] == ' ') {
            return false
        } else if (tamanhonome > 12) {
            return false
        } else if (tamanhonome < 3) {
            return false
        }else{
            letrasnome++
        }
    }
    return true
}

fun criaTopoTabuleiro(coluna: Int): String{
    var teto = ""
    val tracos = "════"
    var count1 = 0
    while(count1 < coluna - 1){
        teto += tracos
        count1++
    }
    teto += "═══"
    val topo = "╔$teto╗"
    return topo
}

fun criaLegendaHorizontal(coluna: Int): String{
    var count = 0
    var legenda = "  "
    while(count < coluna - 1){
        legenda = legenda + alfabeto[count]
        legenda += " | "
        count++
    }
    val ultLetra = alfabeto[count] + "  "
    return legenda + ultLetra
}

fun criaTabuleiro(tabuleiro: Array<Array<String?>>, comLegenda: Boolean = true): String {
    val numLinhas = tabuleiro.size
    val numColunas = tabuleiro[0].size
    var resultado = ""
    val topo = criaTopoTabuleiro(numColunas)
    var linhaTabuleiro = ""
    for (linha in 0..< numLinhas) {
        linhaTabuleiro += "║ "
        for (coluna in 0 ..< numColunas) {
            linhaTabuleiro += tabuleiro[linha][coluna] ?: " "
            if (coluna < numColunas - 1) {
                linhaTabuleiro += " | "
            }
        }
        linhaTabuleiro += " ║"
        if (linha < numLinhas - 1) {
            linhaTabuleiro += "\n"
        }
    }
    val legenda = if (comLegenda) "\n" + criaLegendaHorizontal(numColunas) else ""
    resultado = topo + "\n" + linhaTabuleiro + legenda
    return resultado
}

fun criaTabuleiroVazio(linhas: Int, colunas: Int): Array<Array<String?>> {
    val tabuleiro = Array(linhas) { Array<String?>(colunas) { null } }
    return tabuleiro
}

fun contaBaloesLinha(tabuleiro: Array<Array<String?>>, linha: Int): Int {
    var contador = 0
    for (coluna in tabuleiro[linha]) {
        if (coluna != null && (coluna == balaoazul || coluna == balaovermelho)) {
            contador++
        }
    }
    return contador
}

fun contaBaloesColuna(tabuleiro: Array<Array<String?>>, coluna: Int): Int {
    var contador = 0
    for (linha in tabuleiro) {
        if (linha[coluna] != null &&(linha[coluna] == balaoazul || linha[coluna] == balaovermelho)) {
            contador++
        }
    }
    return contador
}

fun colocaBalao(tabuleiro: Array<Array<String?>>, coluna: Int, humano: Boolean): Boolean {
    var linha = 0
    var colunaMaisDireita = tabuleiro[0].size

    while (linha <= tabuleiro.size - 1) {
        if (tabuleiro[linha][colunaMaisDireita] == null) {
            if (humano){
                tabuleiro[linha][coluna] = balaovermelho
            } else{
                tabuleiro[linha][coluna] = balaoazul
            }
            return true
        }else{
            tabuleiro[linha][coluna] = null
        }
        linha++
    }
    return false
}

fun jogadaNormalComputador(tabuleiro: Array<Array<String?>>): Int {
    var linhas = 0
    var colunas = 0
    var tamanhadascolunas = tabuleiro[linhas].size
    var posicaovazia = -1
    do {
        if (tabuleiro[linhas][colunas] == null) {
            posicaovazia = colunas
        } else {
            colunas++
        }
        if (colunas >= tamanhadascolunas) {
            colunas = 0
            linhas++
        }
    } while (posicaovazia == -1 && linhas < tabuleiro.size)
    return posicaovazia
}

fun eVitoriaHorizontal(tabuleiro: Array<Array<String?>>): Boolean {
    for (linha in 0..tabuleiro.size - 1) {
        for (coluna in 0..tabuleiro[linha].size - 4) {
            if (tabuleiro[linha][coluna] == balaovermelho &&
                tabuleiro[linha][coluna + 1] == balaovermelho &&
                tabuleiro[linha][coluna + 2] == balaovermelho &&
                tabuleiro[linha][coluna + 3] == balaovermelho) {
                return true
            } else if (tabuleiro[linha][coluna] == balaoazul &&
                tabuleiro[linha][coluna + 1] == balaoazul &&
                tabuleiro[linha][coluna + 2] == balaoazul &&
                tabuleiro[linha][coluna + 3] == balaoazul) {
                return true
            }
        }
    }
    return false
}

fun eVitoriaVertical(tabuleiro: Array<Array<String?>>): Boolean {
    for (linha in 0..tabuleiro.size - 4) {
        for (coluna in 0..tabuleiro[linha].size - 1){
            if (tabuleiro[linha][coluna] == balaovermelho &&
                tabuleiro[linha + 1][coluna] == balaovermelho &&
                tabuleiro[linha + 2][coluna] == balaovermelho &&
                tabuleiro[linha + 3][coluna] == balaovermelho) {
                return true
            } else if (tabuleiro[linha][coluna] == balaoazul &&
                tabuleiro[linha + 1][coluna] == balaoazul &&
                tabuleiro[linha + 2][coluna] == balaoazul &&
                tabuleiro[linha + 3][coluna] == balaoazul) {
                return true
            }
        }
    }
    return false
}

fun eVitoriaDiagonal(tabuleiro: Array<Array<String?>>): Boolean {
    for (linha in 0..< tabuleiro.size - 3) {
        for (coluna in 0..< tabuleiro[0].size - 3) {
            if (tabuleiro[linha][coluna] == balaoazul &&
                tabuleiro[linha + 1][coluna + 1] == balaoazul &&
                tabuleiro[linha + 2][coluna + 2] == balaoazul &&
                tabuleiro[linha + 3][coluna + 3] == balaoazul) {
                return true
            } else if (tabuleiro[linha][coluna] == balaovermelho &&
                tabuleiro[linha + 1][coluna + 1] == balaovermelho &&
                tabuleiro[linha + 2][coluna + 2] == balaovermelho &&
                tabuleiro[linha + 3][coluna + 3] == balaovermelho){
                return true
            }
            if (tabuleiro[linha][coluna + 3] == balaoazul &&
                tabuleiro[linha + 1][coluna + 2] == balaoazul &&
                tabuleiro[linha + 2][coluna + 1] == balaoazul &&
                tabuleiro[linha + 3][coluna] == balaoazul &&
                tabuleiro[linha][coluna + 3] != null) {
                return true
            } else if (tabuleiro[linha][coluna + 3] == balaovermelho &&
                tabuleiro[linha + 1][coluna + 2] == balaovermelho &&
                tabuleiro[linha + 2][coluna + 1] == balaovermelho &&
                tabuleiro[linha + 3][coluna] == balaovermelho &&
                tabuleiro[linha][coluna + 3] != null) {
                return true
            }
        }
    }
    return false
}

fun ganhouJogo(tabuleiro: Array<Array<String?>>): Boolean{
    if (eVitoriaHorizontal(tabuleiro) == true || eVitoriaVertical(tabuleiro) == true || eVitoriaDiagonal(tabuleiro) == true){
        return true
    }else{
        return false
    }
}

fun eEmpate(tabuleiro: Array<Array<String?>>): Boolean {
    var linhas = tabuleiro.size
    var colunas = tabuleiro[0].size
    var count = 0

    if(linhas > colunas){
        return true
    }

    for(x in tabuleiro){
        for(y in x){
            if(y == balaoazul){
                count++
                if(count == 5){
                    return true
                }
            }
        }
    }

    for (linha in tabuleiro) {
        for (coluna in linha) {
            if (coluna == null) {
                return false
            }
        }
    }
    return true
}

fun explodeBalao(tabuleiro: Array<Array<String?>>, coordenadas: Pair<Int, Int>): Boolean {
    val linha = coordenadas.first
    val coluna = coordenadas.second
    if (tabuleiro[linha][coluna] == null) {
        return false
    }
    tabuleiro[linha][coluna] = null
    for (i in linha..<tabuleiro.size - 1) {
        tabuleiro[i][coluna] = tabuleiro[i + 1][coluna]
    }
    tabuleiro[tabuleiro.size - 1][coluna] = null

    return true
}

fun jogadaExplodirComputador(tabuleiro: Array<Array<String?>>): Pair<Int, Int> {
    val linhas = tabuleiro.size
    val colunas = tabuleiro[0].size
    for (x in 0 until linhas) {
        var count = 0
        for (y in 0 until colunas) {
            if (tabuleiro[x][y] == balaovermelho) {
                count++
                if (count == 3) {
                    return Pair(x, y - 2)
                }
            } else {
                count = 0
            }
        }
    }

    for (y in 0 until colunas) {
        var count = 0
        for (x in 0 until linhas) {
            if (tabuleiro[x][y] == balaovermelho) {
                count++
                if (count == 3) {
                    return Pair(x - 2, y)
                }
            } else {
                count = 0
            }
        }
    }

    val contagemPorColuna = IntArray(tabuleiro[0].size)
    for (linha in tabuleiro) {
        var colunaAtual = 0
        for (elemento in linha) {
            if (elemento == balaovermelho) {
                contagemPorColuna[colunaAtual]++
            }
            colunaAtual++
        }
    }

    var menor = contagemPorColuna[0]
    var atual = 0
    var posicao = 0
    for (valor in contagemPorColuna) {
        if (valor < menor) {
            menor = valor
            posicao = atual
        }
        atual++
    }

    var linhaatual = 0
    for (linha in tabuleiro) {
        val elemento = linha[posicao]
        if (elemento == balaovermelho){
            return Pair(linhaatual,atual)
        }
        linhaatual++
    }
    return Pair(-1,-1)
}

fun leJogo(nomedoficheiro: String): Pair<String, Array<Array<String?>>> {
    val lines = File(nomedoficheiro).readLines()
    val jogador = lines[0]
    val numeroDeLinhas = lines.size - 1
    val numeroDeColunas = lines[1].split(",").size
    val tabuleiro = Array(numeroDeLinhas) { arrayOfNulls<String>(numeroDeColunas) }
    for (i in 1..numeroDeLinhas) {
        val valores = lines[i].split(",")
        for (j in 0 until valores.size) {
            tabuleiro[i - 1][j] = when (valores[j]) {
                "C" -> balaoazul
                "H" -> balaovermelho
                else -> null
            }
        }
    }
    return Pair(jogador, tabuleiro)
}

fun gravaJogo(nomedoficheiro: String, tabuleiro: Array<Array<String?>>, nomedojogador: String) {
    val file = File(nomedoficheiro)
    val writer = file.printWriter()
    writer.println(nomedojogador)
    for (linha in tabuleiro) {
        var linhaFormatada = ""
        var contador = 0
        for (balao in linha) {
            linhaFormatada += when (balao) {
                balaoazul -> "C"
                balaovermelho -> "H"
                else -> ""
            }
            contador++
            if (contador < linha.size) {
                linhaFormatada += ","
            }
        }
        writer.println(linhaFormatada)
    }
    writer.close()
}

fun main() {
    println("")
    println("""Bem-vindo ao jogo "4 Baloes em Linha"!""")
    println("\n1. Novo Jogo\n2. Gravar Jogo\n3. Ler Jogo\n0. Sair\n")
    var comecoujogo = false
    var linhas = 0
    var coluna = 0
    var nome = ""
    var tabuleiro2 = Array(linhas) { arrayOfNulls<String>(coluna) }
    do {
        var saidaforcada = false
        var opcao = 0
        var letraColuna1 = ""
        var tamanholetracoluna = ""
        do {
            val verificaropcao = readln().toIntOrNull()
            if ( verificaropcao !in 0..9 || verificaropcao == null) {
                println("Opcao invalida. Por favor, tente novamente.")
            }else if (verificaropcao == 2 && comecoujogo == true){
                println("Introduza o nome do ficheiro (ex: jogo.txt)")
                val nomedoficheiro = readln()
                gravaJogo(nomedoficheiro,tabuleiro2,nome)
                println("Tabuleiro ${linhas}x${coluna} gravado com sucesso")
            }else if (verificaropcao == 2){
                println("Funcionalidade Gravar nao esta disponivel")
            }else {
                opcao = verificaropcao
            }
        } while (verificaropcao !in 0..9 || verificaropcao == 2)
        if (opcao == 0 || opcao == 9) {
            println("Prima enter para sair")
            var sairAgora = readln().toIntOrNull()
            if(sairAgora == null){
                println("A sair...")
                return
            }
            println("A sair...")
            return
        } else {
            if (opcao == 3) {
                println("Introduza o nome do ficheiro (ex: jogo.txt)")
                val nomedojogogravado = readln()
                val jogolido = leJogo(nomedojogogravado)
                println("Tabuleiro ${jogolido.second.size}x${jogolido.second[0].size} lido com sucesso!")
                nome = jogolido.first
                coluna = jogolido.second[0].size
                linhas = jogolido.second.size
                println(criaTabuleiro(jogolido.second))
            }
            if (opcao == 1) {
                do {
                    println("Numero de linhas:")
                    val numerodelinhas = readln().toIntOrNull()
                    if (numerodelinhas !in 5..7 || numerodelinhas == null) {
                        println("Numero invalido")
                    } else {
                        linhas = numerodelinhas
                        do {
                            println("Numero de colunas:")
                            val numerodecolunas = readln().toIntOrNull()
                            if (numerodecolunas == null || numerodecolunas < 0) {
                                println("Numero invalido")
                            } else if (validaTabuleiro(numerodelinhas, numerodecolunas) == false) {
                                println("Tamanho do tabuleiro invalido")
                            } else {
                                coluna = numerodecolunas
                            }
                        } while (numerodecolunas == null || numerodecolunas < 0)
                    }
                } while (validaTabuleiro(linhas, coluna) == false)
                do {
                    println("Nome do jogador 1:")
                    val nomejogador = readln()
                    nome = nomejogador
                    if (nomeValido(nomejogador) == false) {
                        println("Nome de jogador invalido")
                    }
                } while (nomeValido(nomejogador) == false)
            }
            val tabuleiro = criaTabuleiroVazio(linhas, coluna)
            if (opcao != 3){
                println(criaTabuleiro(tabuleiro))
            }
            println("\n${nome}: $balaovermelho\nTabuleiro ${linhas}X${coluna}")
            do {
                comecoujogo = true
                tabuleiro2 = tabuleiro
                var colunaainserirbalao: Int?
                do {
                    println("Coluna? (A..${alfabeto[coluna - 1]}):")
                    val letracoluna = readln()
                    val letracolunaverifica =  (letracoluna.lastOrNull()).toString()
                    letraColuna1 = letracoluna
                    tamanholetracoluna = letracolunaverifica
                    if (letracoluna != "Sair" && letracoluna != "Gravar" && letracolunaverifica !in "A".."${alfabeto[coluna - 1]}"){
                        println("Coluna invalida")
                    }
                }while (letracoluna != "Sair" && letracoluna != "Gravar" && letracolunaverifica !in "A".."${alfabeto[coluna - 1]}")
                when {
                    letraColuna1 == "Sair" -> {
                        saidaforcada = true
                    }
                    letraColuna1 == "Gravar" -> {
                        println("Introduza o nome do ficheiro (ex: jogo.txt)")
                        val nomedojogo = readln()
                        gravaJogo(nomedojogo, tabuleiro, nome)
                        println("Tabuleiro ${linhas}x${coluna} gravado com sucesso")
                        saidaforcada = true
                    }
                    processaColuna(coluna, tamanholetracoluna) == null || letraColuna1 == "Explodir" -> {
                        println("Coluna invalida")
                    }
                    contaBaloesLinha(tabuleiro, 0) < 2 && letraColuna1 == "Explodir $tamanholetracoluna" -> {
                        println("Funcionalidade Explodir nao esta disponivel")
                    }
                    contaBaloesLinha(tabuleiro, 0) >= 2 && letraColuna1 == "Explodir $tamanholetracoluna" -> {
                        val escolhaexplosao = processaColuna(coluna, tamanholetracoluna) ?: 0
                        if (tabuleiro[0][escolhaexplosao] == null) {
                            println("Coluna vazia")
                        } else {
                            println("Balao ${tamanholetracoluna} explodido!")
                            explodeBalao(tabuleiro, Pair(0, escolhaexplosao))
                            println(criaTabuleiro(tabuleiro))
                            println("Prima enter para continuar. O computador ira agora explodir um dos seus baloes")
                            readln()
                        }
                    }
                    else -> {
                        println("Coluna escolhida: $letraColuna1")
                        colunaainserirbalao = processaColuna(coluna, letraColuna1) ?: 0
                        colocaBalao(tabuleiro, colunaainserirbalao, true)
                        println(criaTabuleiro(tabuleiro))
                        if (ganhouJogo(tabuleiro)) {
                            println("\nParabens ${nome}! Ganhou!")
                            saidaforcada = true
                        } else {
                            println("\nComputador: $balaoazul\nTabuleiro ${linhas}X${coluna}")
                            val jogadacomputador = jogadaNormalComputador(tabuleiro)
                            colocaBalao(tabuleiro, jogadacomputador, false)
                            println("Coluna escolhida: ${alfabeto[jogadacomputador]}")
                            println(criaTabuleiro(tabuleiro))
                            if (ganhouJogo(tabuleiro)) {
                                println("\nPerdeu! Ganhou o Computador.")
                                saidaforcada = true
                            } else if (eEmpate(tabuleiro)) {
                                println("\nEmpate!")
                                saidaforcada = true
                            }else{
                                println("\n${nome}: $balaovermelho\nTabuleiro ${linhas}X${coluna}")
                            }
                        }
                    }
                }
            } while (!saidaforcada)
        }
        println("\n1. Novo Jogo\n2. Gravar Jogo\n3. Ler Jogo\n0. Sair\n")
    }while (true)
}