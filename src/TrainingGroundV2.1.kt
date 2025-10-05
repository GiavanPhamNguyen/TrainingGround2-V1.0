//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
class Player(val name: String, var stamina: Int, var maxStamina: Int, var hp: Int, val maxHp: Int, val regenFactor: Int){

    fun attack(enemy: Enemy, staminaCost: Int, damageMult: Int, hitChance: Int){
        if (stamina < staminaCost){
            println("The $name is too tired to attack!")
            return
        }
        val attackChance = (2..12).random()
        if (attackChance <= hitChance){
            println("$name rolled a(n) $attackChance. Missed!")
            stamina -= staminaCost
            println("The attack cost $staminaCost stamina points.")
            println()
        } else{
            println("$name rolled a(n) $attackChance. Success!")
            val damage = ((2..12).random()) * damageMult
            enemy.takeDamage(damage)
            println("$name did $damage points of damage to ${enemy.name}!")
            stamina -= staminaCost
            println("The attack cost $staminaCost stamina points.")
            println()
        }
    }

    fun isAlive(): Boolean = hp > 0

    fun takeDamage(amount: Int){
        hp -= amount
        println("$name has taken $amount points of damage!")
    }

    fun showStats(){
        if (hp < 0) hp = 0
        println("$name | STAMINA: $stamina/$maxStamina | HP: $hp/$maxHp | ")
    }

    fun heal(){
        val healAmount = (2..12).random()
        hp += healAmount
        if (hp > maxHp) hp = maxHp
        println("You rolled a(n) $healAmount!")
        println("$name healed for $healAmount points!")
        println()
    }

    fun quit(): Boolean{
        var input: String
        do{
            println("Are you sure? (Y/N)")
            input = readln().trim().lowercase()
        } while (input !in listOf("y", "n"))
        return input == "y"
    }
}

class Enemy(val name: String, var hp: Int, val maxHp: Int, val level: Int){
    fun attack(player: Player){
        if (isAlive()){
            println("The $name is attacking!")
            val attackChance = (2..12).random()
            if (attackChance <= 6) {
                println("The $name missed!")
            } else{
                val damageRange = (2..12).random() + level
                println("$name did $damageRange points of damage to the ${player.name.lowercase()}!")
                player.takeDamage(damageRange)
            }
        }
    }

    fun takeDamage(amount: Int){
        hp -= amount
        if (hp < 0) hp = 0
    }

    fun showStats(){
        println("$name | HP: $hp/$maxHp | ")
    }

    fun isAlive(): Boolean = hp > 0
}

fun main(){
    // Characters
    val party = arrayOf(
        Player("Warrior", 25, 25, 40, 40, 1),
        Player("Wizard", 40, 35, 25, 25, 2),
        Player("Elf", 30, 30, 30, 30, 1),
        Player("Dwarf", 25, 20, 50, 50, 2)
    )

    // Lesser enemies
    val lesserEnemies = arrayOf(
        Enemy("Warlike Goblin", 20, 20, 2),
        Enemy("Giant Spider", 25, 25, 3),
        Enemy("Howling Werewolf", 25, 25, 3),
        Enemy("Savage Orc", 30, 30, 4),
        Enemy("Barbaric Troll", 35, 35, 5),
        Enemy("Bloodthirsty Chimera", 35, 35, 5),
        Enemy("Vicious Ogre", 40, 40, 6),
        Enemy("Towering Giant", 45, 45, 7),
        Enemy("Terrifying Minotaur", 50, 50, 8)
    )

    // Boss enemies
    val bossEnemies = arrayOf(
        Enemy("Ferocious Wyvern", 100, 100, 9),
        Enemy("Elder Dragon", 120, 120, 10),
        Enemy("Ancient Creature of the Deep", 200, 200, 11),
    )

    // Helper function for stamina regen
    fun regenAllParty(){
        println()
        println("All party member recovered some stamina!")
        for (player in party){
            if (player.isAlive()){
                player.stamina = (player.stamina + player.regenFactor).coerceAtMost(player.maxStamina)
                println("${player.name} regained ${player.regenFactor} points of stamina.")
            }
        }
        println()
    }

    println("Welcome to the dungeon's gate! Before entering, please enter the player's name: ")
    val name = readln()
    println()
    println("* There are rumored to be 9 lesser creatures and 3 boss-level monsters within this dungeon.")
    println("* Your party consists of a warrior, a wizard, an elf, and a dwarf.")
    println("* Each party member specializes in specific weapons with different levels of accuracy and ranges of damage.")
    println("* The hit chance of each attack is determined by two six-sided dice roll.")
    println("* The damage done is also determined by two six-sided dice roll and a damage multiplier depending on the party member.")
    println("* You may also choose to heal each turn, and the amount is determined by two six-sided dice roll.")
    println("* The total stamina, stamina cost per attack, and stamina regeneration also vary for each party member.")
    println("* Here is the party members' stats: ")
    println()
    println("********************************************** Stats **********************************************")
    println("NAME    | DESCRIPTION   | HP | STAMINA | HIT CHANCE | DAMAGE RANGE  |  STAMINA COST | STAMINA REGEN")
    println("Warrior | Well-balanced | 40 |   25    |   58.3%    |     2-12      |      3        |       1      ")
    println("Wizard  | Glass-cannon  | 25 |   40    |   41.7%    |     4-24      |      4        |       2      ")
    println("Elf     |    Agile      | 30 |   30    |   41.7%    |     2-12      |      3        |       1      ")
    println("Dwarf   |    Tanky      | 50 |   25    |   16.7%    |     6-36      |      5        |       2      ")
    println()
    println("*WARNING: !THIS GAME UTILIZES PERMADEATH! Choose your actions carefully.")
    println("*BEST PLAYED WITH NUMPAD*")
    println()

    println("Are you ready to enter the dungeon? (Y/N)")
    val enter = readln().trim().lowercase()
    if (enter != "y" && enter != "yes"){
        println("Have a good day then!")
        return
    }

    var action: Int
    var index = 0
    var bossIndex = 0
    var turnCounter = 0
    var coins = 0
    var quit = false

    println("The dungeon's gate rumbled open. Your party advanced forward, into the mysterious dungeon. Suddenly, a hulking figure appeared. Your first enemy.")

    while (!quit && party.any { it.isAlive() } && (lesserEnemies.any { it.isAlive() } || bossEnemies.any { it.isAlive() })){

        // Lesser enemy combat
        if (index < lesserEnemies.size && lesserEnemies[index].isAlive()){
            println()
            println("Your party stands before the ${lesserEnemies[index].name} | HP: ${lesserEnemies[index].maxHp}")

            while (!quit && lesserEnemies[index].isAlive()){
                if (party.none { it.isAlive() }) {
                    quit = true
                    break
                }

                println()
                println("Which member of your party would you like to select? ")
                for (i in party.indices) {
                    println("[$i] ${party[i].name} - HP: ${party[i].hp.coerceAtLeast(0)}/${party[i].maxHp} | STAMINA: ${party[i].stamina.coerceAtMost(party[i].maxStamina)}/${party[i].maxStamina}")
                }
                println("[4] Quit")

                var partyChoice = readln().toInt()
                while (partyChoice !in 0..4){
                    println("Input invalid. Please try again.")
                    partyChoice = readln().toInt()
                }
                if (partyChoice == 4){
                    quit = true
                    break
                }
                while (!party[partyChoice].isAlive()){
                    println("That party member is unfortunately dead...")
                    println("Please select another!")
                    partyChoice = readln().toInt()
                }

                println("You have selected the ${party[partyChoice].name.lowercase()}!")
                println("What would you like the ${party[partyChoice].name.lowercase()} to do?")
                println("[1] Attack")
                println("[2] Heal")
                println("[3] Quit")

                action = readln().toInt()
                while (action !in 1..3){
                    println("Invalid input. Please try again.")
                    action = readln().toInt()
                }

                when (action){
                    1 -> {
                        // Attack values based on character
                        when (partyChoice) {
                            0 -> party[partyChoice].attack(lesserEnemies[index], 3, 1, 6)
                            1 -> party[partyChoice].attack(lesserEnemies[index], 4, 2, 7)
                            2 -> party[partyChoice].attack(lesserEnemies[index], 3, 1, 7)
                            3 -> party[partyChoice].attack(lesserEnemies[index], 5, 3, 9)
                        }
                        turnCounter++
                        lesserEnemies[index].attack(party[partyChoice])
                        regenAllParty()
                        party[partyChoice].showStats()
                        lesserEnemies[index].showStats()
                    }
                    2 -> {
                        party[partyChoice].heal()
                        turnCounter++
                        lesserEnemies[index].attack(party[partyChoice])
                        regenAllParty()
                        party[partyChoice].showStats()
                        lesserEnemies[index].showStats()
                    }
                    3 -> if (party[partyChoice].quit()) quit = true
                }
            }

            if (!quit && lesserEnemies[index].hp <= 0){
                println("The ${lesserEnemies[index].name} has been defeated!")
                val loot = (10..50).random()
                println("The ${lesserEnemies[index].name} dropped $loot coins!")
                coins += loot
                println("You now have $coins coins!")
                index++
            }
        }

        // Boss combat every 3 enemies
        if (!quit && index % 3 == 0 && bossIndex < bossEnemies.size){
            println()
            println("A boss appears! Your party encounters the ${bossEnemies[bossIndex].name}! | HP: ${bossEnemies[bossIndex].maxHp}")

            while (bossEnemies[bossIndex].isAlive() && !quit){
                if (party.none { it.isAlive() }){
                    quit = true
                    break
                }

                println()
                println("Which member of your party would you like to select? ")
                for (i in party.indices) {
                    println("[$i] ${party[i].name} - HP: ${party[i].hp.coerceAtLeast(0)}/${party[i].maxHp} | STAMINA: ${party[i].stamina.coerceAtMost(party[i].maxStamina)}/${party[i].maxStamina}")
                }
                println("[4] Quit")

                var partyChoice = readln().toInt()
                while (partyChoice !in 0..4){
                    println("Input invalid. Please try again.")
                    partyChoice = readln().toInt()
                }
                if (partyChoice == 4){
                    quit = true
                    break
                }
                while (!party[partyChoice].isAlive()){
                    println("That party member is unfortunately dead...")
                    println("Please select another!")
                    partyChoice = readln().toInt()
                }

                println("You have selected the ${party[partyChoice].name.lowercase()}!")
                println("What would you like the ${party[partyChoice].name.lowercase()} to do?")
                println("[1] Attack")
                println("[2] Heal")
                println("[3] Quit")

                action = readln().toInt()
                while (action !in 1..3) {
                    println("Invalid input. Please try again.")
                    action = readln().toInt()
                }

                when (action){
                    1 -> {
                        party[partyChoice].attack(bossEnemies[bossIndex], 5, 1, 6)
                        turnCounter++
                        bossEnemies[bossIndex].attack(party[partyChoice])
                        regenAllParty()
                        party[partyChoice].showStats()
                        bossEnemies[bossIndex].showStats()
                    }
                    2 -> {
                        party[partyChoice].heal()
                        turnCounter++
                        bossEnemies[bossIndex].attack(party[partyChoice])
                        regenAllParty()
                        party[partyChoice].showStats()
                        bossEnemies[bossIndex].showStats()
                    }
                    3 -> if (party[partyChoice].quit()) quit = true
                }
            }

            if (!quit && bossEnemies[bossIndex].hp <= 0){
                println("The ${bossEnemies[bossIndex].name} has been defeated!")
                val loot = when (bossIndex){
                    0 -> 100
                    1 -> 200
                    else -> 500
                }
                println("The ${bossEnemies[bossIndex].name} dropped $loot coins!\n")
                coins += loot
                println("You now have $coins coins!")
                bossIndex++
                index++
            }
        }
    }

    // Endgame stats
    println()
    if (party.none { it.isAlive() }){
        println("All party members have fallen! Game Over.")
    }else if (lesserEnemies.none { it.isAlive() } && bossEnemies.none { it.isAlive() }){
        println("All enemies have been defeated! Congratulations!")
    }else {
        println("$name have escaped the dungeon.")
    }

    println("Here are your party's final stats:")
    for (player in party) {
        println("${player.name} | HP: ${player.hp.coerceAtLeast(0)}/${player.maxHp} | Stamina: ${player.stamina.coerceAtMost(player.maxStamina)}/${player.maxStamina}")
    }
    println("$name lasted $turnCounter turns and collected $coins coins.")
}
