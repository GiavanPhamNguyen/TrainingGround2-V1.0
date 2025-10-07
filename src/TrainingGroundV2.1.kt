class Player(val name: String, var stamina: Int, var maxStamina: Int, var hp: Int, val maxHp: Int, val regenFactor: Int){ //Create a player class with all the capabilities and functions used by an object in this class

    fun attack(enemy: Enemy, staminaCost: Int, damageMult: Int, hitChance: Int){ //attacking function
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

    fun isAlive(): Boolean = hp > 0 //checking whether the player, or in this case, party members, are still alive

    fun takeDamage(amount: Int){ //function for computing damage being done by the enemy class
        hp -= amount
        println("$name has taken $amount points of damage!")
    }

    fun showStats(){ //function for showing stats
        if (hp < 0) hp = 0
        println("$name | STAMINA: $stamina/$maxStamina | HP: $hp/$maxHp | ")
    }

    fun heal(){ //function for healing
        val healAmount = (2..12).random()
        hp += healAmount
        if (hp > maxHp) hp = maxHp
        println("You rolled a(n) $healAmount!")
        println("$name healed for $healAmount points!")
        println()
    }

    fun staminaRegen(){  // stamina regen function for every party member that is alive for every turn/action taken
        if (isAlive()){
            stamina = (stamina + regenFactor).coerceAtMost(maxStamina)
            println("${name} regained ${regenFactor} points of stamina.")
        }
    }

    fun quit(): Boolean{ //for quitting and throwing an additional check to ensure the user wants to quit
        var input: String
        do{
            println("Are you sure? (Y/N)")
            input = readln().trim().lowercase()
        } while (input !in listOf("y", "n"))
        return input == "y"
    }
}

class Enemy(val name: String, var hp: Int, val maxHp: Int, val level: Int){ //class for enemy types that will be the template for enemies used in this game
    fun attack(player: Player){ //function for attacking by the enemy, including damage being done to the player
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

    fun takeDamage(amount: Int){ //function for the enemy to take damage, very crucial
        hp -= amount
        if (hp < 0) hp = 0
    }

    fun showStats(){ //function to show the HP of the enemy
        println("$name | HP: $hp/$maxHp | ")
    }

    fun isAlive(): Boolean{ //function for checking if object of enemy class is still alive
        return hp > 0
    }
}

fun main(){ //main function
    // This game will be different in that instead of the player attacking directly, the player will be in charge of a group of characters, created using the "Player" class, preset with stamina, HP, and other factors
    //Array of preset characters for the party
    val party = arrayOf(
        Player("Warrior", 25, 25, 40, 40, 1),
        Player("Wizard", 40, 35, 25, 25, 2),
        Player("Elf", 30, 30, 30, 30, 1),
        Player("Dwarf", 25, 20, 50, 50, 2)
    )

    // Created an array of "lesser" enemies, using the Enemy class, preset with health and level (to be used in conjunction with the attack function)
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

    // Created an array of "boss" enemies, still using the Enemy class
    val bossEnemies = arrayOf(
        Enemy("Ferocious Wyvern", 100, 100, 9),
        Enemy("Elder Dragon", 120, 120, 10),
        Enemy("Ancient Creature of the Deep", 200, 200, 11),
    )

    //introducing the user to the game, along with stats and info
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
    //initializing index to move along the lesser enemies array and a boss index for the boss enemies array, and coins to keep track of total gold won by user
    var action: Int
    var index = 0
    var bossIndex = 0
    var turnCounter = 0
    var coins = 0
    var quit = false

    println("The dungeon's gate rumbled open. Your party advanced forward into the mysterious dungeon. Suddenly, a hulking figure appeared. Your first enemy.")
    //while any member of the party is alive or (any lesser or boss enemy is alive) the quit flag has not been thrown, this loop will keep the game running
    while (!quit && party.any { it.isAlive() } && (lesserEnemies.any { it.isAlive() } || bossEnemies.any { it.isAlive() })){

        // Lesser enemy combat, while the current lesser enemy is alive and the array has not been exhausted, the loop will continue
        if (index < lesserEnemies.size && lesserEnemies[index].isAlive()){
            println()
            println("Your party stands before the ${lesserEnemies[index].name}! | HP: ${lesserEnemies[index].maxHp}")

            while (!quit && lesserEnemies[index].isAlive()){
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
                while (action !in 1..3){
                    println("Invalid input. Please try again.")
                    action = readln().toInt()
                }

                when (action){
                    1 -> {
                        // attack chance, damage multiplier and stamina cost based on character
                        when (partyChoice) {
                            0 -> party[partyChoice].attack(lesserEnemies[index], 3, 1, 6)
                            1 -> party[partyChoice].attack(lesserEnemies[index], 4, 2, 7)
                            2 -> party[partyChoice].attack(lesserEnemies[index], 3, 1, 7)
                            3 -> party[partyChoice].attack(lesserEnemies[index], 5, 3, 9)
                        }
                        //updating turn to show at the end of the game how many turns the user lasted
                        turnCounter++
                        lesserEnemies[index].attack(party[partyChoice])
                        println()
                        println("All party member recovered some stamina!")
                        for (player in party){
                            player.staminaRegen()
                        }
                        println()
                        party[partyChoice].showStats()
                        lesserEnemies[index].showStats()
                    }
                    2 -> {
                        party[partyChoice].heal()
                        turnCounter++
                        lesserEnemies[index].attack(party[partyChoice])
                        println()
                        println("All party member recovered some stamina!")
                        for (player in party){
                            player.staminaRegen()
                        }
                        println()
                        party[partyChoice].showStats()
                        lesserEnemies[index].showStats()
                    }
                    3 -> if (party[partyChoice].quit()) quit = true
                }
            }
            //when a lesser enemy is defeated, award loot and updating index to continue to the next lesser enemy in the array
            if (!quit && lesserEnemies[index].hp <= 0){
                println("The ${lesserEnemies[index].name} has been defeated!")
                val loot = (10..50).random()
                println("The ${lesserEnemies[index].name} dropped $loot coins!")
                coins += loot
                println("You now have $coins coins!")
                index++
            }
        }

        // Boss combat will initiate every 3 lesser enemies
        if (!quit && index % 3 == 0 && bossIndex < bossEnemies.size){
            println()
            println("A boss appears! Your party encounters the ${bossEnemies[bossIndex].name}! | HP: ${bossEnemies[bossIndex].maxHp}")

            //This loop will run while the current boss is alive and the user has not chosen to quit
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
                        println()
                        println("All party member recovered some stamina!")
                        for (player in party){
                            player.staminaRegen()
                        }
                        println()
                        party[partyChoice].showStats()
                        bossEnemies[bossIndex].showStats()
                    }
                    2 -> {
                        party[partyChoice].heal()
                        turnCounter++
                        bossEnemies[bossIndex].attack(party[partyChoice])
                        println()
                        println("All party member recovered some stamina!")
                        for (player in party){
                            player.staminaRegen()
                        }
                        println()
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

    // Endgame stats, active whether all members of the party is dead, all enemies has been defeated, or the user has quit the game.
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
