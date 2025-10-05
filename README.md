# TrainingGroundV2

Welcome to TrainingGroundV2, updated into a turn-based dungeon crawler where your party of four faces off against powerful monsters and legendary bosses. This game features strategic choices, stamina management, as well as permadeath

## Game Overview

- Your party consists of **Warrior, Wizard, Elf, and Dwarf**, each with unique abilities, damage ranges, and stamina management.
- Battles are turn-based, with **two six-sided dice** determining hit chance and damage.
- Each turn, party members can **attack, heal, or quit**.
- Stamina regenerates every turn according to each character's regen factor.
- Defeat enemies to earn coins. Beware: **permadeath is enabled** — once a character falls, they are gone for good.
- Boss encounters appear after every 3 lesser enemies.

## Party Members

| Name    | Description    | HP  | Stamina | Hit Chance | Damage Range | Stamina Cost | Stamina Regen |
|---------|----------------|-----|---------|------------|--------------|--------------|---------------|
| Warrior | Well-balanced   | 40  | 25      | 58.3%      | 2-12         | 3            | 1             |
| Wizard  | Glass-cannon    | 25  | 40      | 41.7%      | 4-24         | 4            | 2             |
| Elf     | Agile           | 30  | 30      | 41.7%      | 2-12         | 3            | 1             |
| Dwarf   | Tanky           | 50  | 25      | 16.7%      | 6-36         | 5            | 2             |

## Enemies

- **Lesser Enemies:** 9 in total, including goblins, spiders, and orcs.
- **Bosses:** 3 in total, including the Ferocious Wyvern, Elder Dragon, and Ancient Creature of the Deep.

## How to Play

1. Run the game in a Kotlin environment or IDE.
2. Enter your player name.
3. Select which party member to act each turn.
4. Choose to **attack**, **heal**, or **quit**.
5. Manage stamina carefully and strategize to defeat all enemies.

**Features:**  
- Four playable characters: Warrior, Wizard, Elf, Dwarf  
- Turn-based combat with dice-based attack, damage, and healing  
- Stamina management system with regeneration  
- 9 Lesser Enemies + 3 Boss Enemies  
- Permadeath for all party members  
- Coin loot system, with coins awarded after defeating enemies  


## Getting Started

Clone the repository:
```bash
git clone https://github.com/username/repo-name.git
cd repo-name
open project in IntelliJ IDEA or Kotlin-compatible IDE
run main() in TrainingGroundV2.1.kt

## Possible Future Improvements

- **Additional Party Members/Classes:** Introduce more playable characters with unique abilities, stats, and playstyles.  
- **Status Effects:** Implement conditions like Poisoned, Paralyzed, or Burned that affect combat.  
- **Special Abilities & Spells:** Add character-specific attacks or spells for more strategic options.  
- **Expanded Enemy Roster:** Include a wider variety of enemies to increase challenge and diversity.  
- **Random Events:** Introduce environmental hazards, traps, or random events that can influence battles for both players and enemies.  
- **Leveling System:** Allow party members to level up after defeating enemies, enhancing stats or unlocking new abilities.  
- **Shop System:** Add a shop where players can spend coins earned from battles to buy upgrades, tools, or consumables.  
- **Progress Tracking:** Track high scores, past runs, or achievements to improve replayability and challenge.  

## License
This project is open source and available under the MIT license